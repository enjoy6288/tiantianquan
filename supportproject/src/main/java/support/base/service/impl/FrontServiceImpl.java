package support.base.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import support.base.dao.mapper.FrontMapper;
import support.base.dao.mapper.SweetUserMapper;
import support.base.pojo.po.FrontProduct;
import support.base.pojo.po.FrontTopic;
import support.base.pojo.po.SweetCollect;
import support.base.pojo.vo.FrontQueryVo;
import support.base.pojo.vo.PageQuery;
import support.base.pojo.vo.PhoneParamVo;
import support.base.pojo.vo.SweetCollectVo;
import support.base.process.result.FrontDataInfo;
import support.base.service.FrontService;
import support.base.util.Constant;
import support.base.util.RedisUtil;

@Service
public class FrontServiceImpl implements FrontService {
	@Autowired
	private FrontMapper frontMapper;
	@Autowired
	private SweetUserMapper userMapper;

	@Override
	public FrontDataInfo queryNewProducts(FrontQueryVo vo, PhoneParamVo phoneVo) {
		// 设置查询单品
		vo.setType(Constant.PRODUCT_TYPE);
		String queryProductType = vo.getQueryProductType();
		Calendar cl = Calendar.getInstance();
		Date nowTime = new Date();
		cl.setTime(nowTime);
		JSONObject nextQuery = new JSONObject();
		List<FrontProduct> newProducts = new ArrayList<>();
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		// 获取用户收藏的ID
		Set<String> collectId = getUserCollectId(phoneVo, jedis, Constant.PRODUCT_TYPE);
		if (!StringUtils.isEmpty(queryProductType)) {
			// 查询当天的
			if (queryProductType.equals("today")) {
				vo.setBeginTime(nowTime);
				vo.setEndTime(nowTime);
				// 商品总浏览量
				jedis.incr("home:pv");
				// 商品独立访客
				if (!StringUtils.isEmpty(phoneVo.getImei())) {
					jedis.sadd("home:uv", phoneVo.getImei());
				}
				String productsJson = jedis.get("product:today");
				if (StringUtils.isEmpty(productsJson)) {
					newProducts = frontMapper.queryNewProducts(vo);
					productsJson = JSONObject.toJSONString(newProducts);
					jedis.set("product:today", productsJson);
				} else {
					newProducts = JSONObject.parseArray(productsJson, FrontProduct.class);
				}
			}
			// 查询最近一个月的
			if (queryProductType.equals("more")) {
				cl.add(Calendar.MONTH, -1);
				Date beginTime = cl.getTime();
				vo.setBeginTime(beginTime);
				vo.setEndTime(nowTime);
				String productsJson = jedis.get("product:more:" + vo.getStartPage());
				if (StringUtils.isEmpty(productsJson)) {
					newProducts = frontMapper.queryNewProducts(vo);
					productsJson = JSONObject.toJSONString(newProducts);
					jedis.set("product:more:" + vo.getStartPage(), productsJson);
				} else {
					newProducts = JSONObject.parseArray(productsJson, FrontProduct.class);
				}
				nextQuery.put("queryProductType", "more");
			}
		}

		// 在查询主题商品的时候查询出主题信息
		String topicId = vo.getTopicId();
		Map data = new HashMap<String, List<Object>>();
		if (!StringUtils.isEmpty(topicId)) {
			// 单个专题浏览量
			jedis.incr("subject:pv:" + topicId);
			// 单个主题独立访客
			if (!StringUtils.isEmpty(phoneVo.getImei())) {
				jedis.sadd("subject:uv:" + topicId, phoneVo.getImei());
			}
			nextQuery.put("topicId", topicId);
			FrontTopic topic = null;
			vo.setType(Constant.TOPIC_TYPE);
			String topicJson = jedis.get("topic:" + topicId);
			if (StringUtils.isEmpty(topicJson)) {
				List<FrontTopic> topList = frontMapper.queryTopics(vo);
				topic = topList.get(0);
				topicJson = JSONObject.toJSONString(topic);
				jedis.set("topic:" + topicId, topicJson);
			} else {
				topic = JSONObject.parseObject(topicJson, FrontTopic.class);
			}
			data.put("topic", topic);
			// 根据主题的ID查询该主题的商品
			String productsJson = jedis.get("topic:" + topicId + ":product:" + vo.getStartPage());
			if (StringUtils.isEmpty(productsJson)) {
				newProducts = frontMapper.queryNewProducts(vo);
				productsJson = JSONObject.toJSONString(newProducts);
				jedis.set("topic:" + topicId + ":product:" + vo.getStartPage(), productsJson);
			} else {
				newProducts = JSONObject.parseArray(productsJson, FrontProduct.class);
			}
		}
		redisUtil.closeRedis();
		// 分页参数设置
		nextQuery.put("startPage", vo.getStartPage() + 1);
		nextQuery.put("pageSize", vo.getPageSize());
		vo.setStartPage(vo.getStartPage() * vo.getPageSize());

		// 设置收藏状态
		if (collectId.size() != 0) {
			for (FrontProduct product : newProducts) {
				String id = product.getId();
				if (collectId.contains(id)) {
					product.setCollected(true);
				}
			}
		}

		FrontDataInfo info = new FrontDataInfo();
		if (newProducts.size() == 0) {
			newProducts = null;
			nextQuery = null;
		}
		data.put("productList", newProducts);
		data.put("nextQuery", nextQuery);
		info.setData(data);
		return info;
	}

	@Override
	public FrontDataInfo queryTopics(FrontQueryVo vo, PhoneParamVo phoneVo) {
		String position = vo.getDispalyPosition();
		Map data = new HashMap<String, List<Object>>();
		JSONObject nextQuery = new JSONObject();
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		// 专题总浏览量
		jedis.incr("subject:pv");
		// 主题独立访客
		if (!StringUtils.isEmpty(phoneVo.getImei())) {
			jedis.sadd("subject:uv", phoneVo.getImei());
		}
		// 以startpage作为是否是专题列表分页 不查询专题banner和推荐专题
		if (vo.getStartPage() == 0) {
			vo.setDispalyPosition("轮播banner");
			// 待设置vo.setPageSize(pageSize);
			String topListJson = jedis.get("topList:topic");
			// topListJson=null;
			List<FrontTopic> topList = new ArrayList<>();
			if (StringUtils.isEmpty(topListJson)) {
				topList = frontMapper.queryTopics(vo);
				topListJson = JSONObject.toJSONString(topList);
				jedis.set("topList:topic", topListJson);
			} else {
				topList = JSONObject.parseArray(topListJson, FrontTopic.class);
			}
			data.put("topList", topList);

			vo.setDispalyPosition("推荐专题");
			// 待设置vo.setPageSize(pageSize);
			String middleListJson = jedis.get("middleList:topic");
			// middleListJson=null;
			List<FrontTopic> middleList = new ArrayList<>();
			if (StringUtils.isEmpty(middleListJson)) {
				middleList = frontMapper.queryTopics(vo);
				middleListJson = JSONObject.toJSONString(middleList);
				jedis.set("middleList:topic", middleListJson);
			} else {
				middleList = JSONObject.parseArray(middleListJson, FrontTopic.class);
			}
			data.put("middleList", middleList);

		}

		// 获取一个月的数据
		vo.setDispalyPosition("专题列表");
		nextQuery.put("startPage", vo.getStartPage() + 1);
		nextQuery.put("pageSize", vo.getPageSize());
		vo.setStartPage(vo.getStartPage() * vo.getPageSize());
		Calendar cl = Calendar.getInstance();
		Date nowTime = new Date();
		cl.setTime(nowTime);
		cl.add(Calendar.MONTH, -1);
		Date beginTime = cl.getTime();
		vo.setBeginTime(beginTime);
		vo.setEndTime(nowTime);

		String bottomListJson = jedis.get("bottomList:topic:" + vo.getStartPage());
		List<FrontTopic> bottomList = new ArrayList<>();
		if (StringUtils.isEmpty(bottomListJson)) {
			bottomList = frontMapper.queryTopics(vo);
			bottomListJson = JSONObject.toJSONString(bottomList);
			jedis.set("bottomList:topic:" + vo.getStartPage(), bottomListJson);
		} else {
			bottomList = JSONObject.parseArray(bottomListJson, FrontTopic.class);
		}
		redisUtil.closeRedis();
		data.put("bottomList", bottomList);
		// 获取用户收藏的ID
		Set<String> collectId = getUserCollectId(phoneVo, jedis, Constant.TOPIC_TYPE);
		// 实时更新收藏数量
		for (FrontTopic topic : bottomList) {
			String id = topic.getId();
			String num = jedis.get("incr:" + Constant.TOPIC_TYPE + ":" + id);
			if (!StringUtils.isEmpty(num)) {
				long collect = topic.getCollect();
				collect = collect + Long.parseLong(num);
				topic.setCollect(collect);
			}
		}
		// 设置收藏状态
		if (collectId.size() != 0) {
			for (FrontTopic topic : bottomList) {
				String id = topic.getId();
				if (collectId.contains(id)) {
					topic.setCollected(true);
				}
			}
		}

		if (bottomList.size() == 0) {
			bottomList = null;
			nextQuery = null;
		}
		FrontDataInfo info = new FrontDataInfo();
		data.put("nextQuery", nextQuery);
		info.setData(data);
		return info;
	}

	@Override
	public FrontDataInfo queryTopicCollect(SweetCollectVo vo, PhoneParamVo phoneVo) {
		JSONObject nextQuery = new JSONObject();
		nextQuery.put("startPage", vo.getStartPage() + 1);
		nextQuery.put("pageSize", vo.getPageSize());
		vo.setStartPage(vo.getStartPage() * vo.getPageSize());

		// 先把收藏的内容保存到数据库
		List<SweetCollect> scs = new ArrayList<>();
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		Set<String> values = jedis.smembers("userCollect:" + phoneVo.getUserId() + ":" + Constant.TOPIC_TYPE);
		SweetCollect sc = null;
		for (String value : values) {
			String[] split = value.split(":");
			sc = new SweetCollect();
			sc.setScoUserId(split[0]);
			sc.setScoCollectId(split[1]);
			sc.setScoCollectType(new Integer(split[2]));
			scs.add(sc);
			jedis.incr("incr:" + split[2] + ":" + split[1]);
		}
		if (scs.size() > 0) {
			jedis.del("userCollect:" + phoneVo.getUserId() + ":" + Constant.TOPIC_TYPE);
			redisUtil.closeRedis();
			userMapper.saveCollect(scs);
		} else {
			redisUtil.closeRedis();
		}

		vo.setScoUserId(phoneVo.getUserId());
		List<FrontTopic> topicCollect = frontMapper.queryTopicCollect(vo);
		FrontDataInfo info = new FrontDataInfo();
		Map data = new HashMap<String, List<Object>>();
		if (topicCollect.size() == 0) {
			topicCollect = null;
			nextQuery = null;
		}
		data.put("topicCollectList", topicCollect);
		data.put("nextQuery", nextQuery);
		info.setData(data);
		return info;
	}

	@Override
	public FrontDataInfo queryProductCollect(SweetCollectVo vo, PhoneParamVo phoneVo) {
		JSONObject nextQuery = new JSONObject();
		nextQuery.put("startPage", vo.getStartPage() + 1);
		nextQuery.put("pageSize", vo.getPageSize());
		vo.setStartPage(vo.getStartPage() * vo.getPageSize());

		// 先把收藏的内容保存到数据库
		List<SweetCollect> scs = new ArrayList<>();
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		Set<String> values = jedis.smembers("userCollect:" + phoneVo.getUserId() + ":" + Constant.PRODUCT_TYPE);
		SweetCollect sc = null;
		for (String value : values) {
			String[] split = value.split(":");
			sc = new SweetCollect();
			sc.setScoUserId(split[0]);
			sc.setScoCollectId(split[1]);
			sc.setScoCollectType(new Integer(split[2]));
			scs.add(sc);
			jedis.incr("incr:" + split[2] + ":" + split[1]);
		}
		if (scs.size() > 0) {
			redisUtil.closeRedis();
			jedis.del("userCollect:" + phoneVo.getUserId() + ":" + Constant.PRODUCT_TYPE);
			userMapper.saveCollect(scs);
		}else {
			redisUtil.closeRedis();
		}
		vo.setScoUserId(phoneVo.getUserId());
		List<FrontProduct> productCollect = frontMapper.queryProductCollect(vo);
		FrontDataInfo info = new FrontDataInfo();
		Map data = new HashMap<String, List<Object>>();
		if (productCollect.size() == 0) {
			productCollect = null;
			nextQuery = null;
		}
		data.put("productCollectList", productCollect);
		data.put("nextQuery", nextQuery);
		info.setData(data);
		return info;
	}

	/**
	 * 获取用户的收藏ID
	 * 
	 * @param phoneVo
	 * @param jedis
	 * @param type类型
	 *            0商品 1主题
	 * @return
	 */
	private Set<String> getUserCollectId(PhoneParamVo phoneVo, Jedis jedis, String type) {
		Set<String> collectId = new HashSet<>();
		if (!StringUtils.isEmpty(phoneVo.getUserId())) {
			Set<String> smembers = jedis.smembers("userCollect:" + phoneVo.getUserId() + ":" + type);
			if (smembers != null && smembers.size() > 0) {
				for (String member : smembers) {
					String[] split = member.split(":");
					collectId.add(split[1]);
				}
			}
			SweetCollectVo vo = new SweetCollectVo();
			vo.setScoUserId(phoneVo.getUserId());
			List<SweetCollect> collects = frontMapper.queryCollect(vo);
			for (SweetCollect collect : collects) {
				collectId.add(collect.getScoCollectId());
			}
		}
		return collectId;
	}

}
