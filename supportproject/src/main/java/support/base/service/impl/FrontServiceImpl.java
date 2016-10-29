package support.base.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
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
	public FrontDataInfo queryNewProducts(FrontQueryVo vo) {
		String queryProductType = vo.getQueryProductType();
		// 设置查询单品
		vo.setType(Constant.PRODUCT_TYPE);
		FrontDataInfo info = new FrontDataInfo();
		// 获取用户收藏的ID
		Set<String> collectId = getUserCollectId(vo, Constant.PRODUCT_TYPE);
		if (!StringUtils.isEmpty(queryProductType)) {
			// 查询当天的内容
			if (queryProductType.equals("today")) {
				info = queryToday(vo, info, collectId);
			}
			// 查询最近一个月的
			if (queryProductType.equals("more")) {
				info = queryMore(vo, info, collectId);
			}
		}
		// 在查询主题商品的时候查询出主题信息
		if (StringUtils.isNotEmpty(vo.getTopicId())) {
			info = queryTopicProducts(vo, info, collectId);
		}
		return info;
	}

	private FrontDataInfo queryTopicProducts(FrontQueryVo vo, FrontDataInfo info, Set<String> collectId) {
		JSONObject nextQuery = new JSONObject();
		Map data = new HashMap<String, List<Object>>();
		String topicId = vo.getTopicId();
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();

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
		// 根据主题的ID查询该主题的商品
		List<FrontProduct> topicProducts = null;
		String productsJson = jedis.get("topic:" + topicId + ":product:" + vo.getStartPage());
		if (StringUtils.isEmpty(productsJson)) {
			topicProducts = frontMapper.queryNewProducts(vo);
			productsJson = JSONObject.toJSONString(topicProducts);
			jedis.set("topic:" + topicId + ":product:" + vo.getStartPage(), productsJson);
		}
		topicProducts = JSONObject.parseArray(productsJson, FrontProduct.class);
		redisUtil.closeRedis();

		Map<Long, List<FrontProduct>> groupMap = new HashMap<>();
		for (FrontProduct product : topicProducts) {
			// 设置收藏状态
			if (collectId.contains(product.getId())) {
				product.setCollected(true);
			}
			long groupName = product.getShelvesTime().getTime();
			List<FrontProduct> list = groupMap.get(groupName);
			if (list == null) {
				list = new ArrayList<>();
				list.add(product);
				groupMap.put(groupName, list);
			}
			list.add(product);
		}
		if (topicProducts == null) {
			nextQuery = null;
		}
		JSONArray group = new JSONArray();
		Set<Entry<Long, List<FrontProduct>>> entrySet = groupMap.entrySet();
		for (Entry<Long, List<FrontProduct>> entry : entrySet) {
			JSONObject object = new JSONObject();
			object.put("groupName", entry.getKey());
			object.put("productList", entry.getValue());
			group.add(object);
		}
		nextQuery.put("startPage", (vo.getStartPage() + 1) * vo.getPageSize());
		nextQuery.put("pageSize", vo.getPageSize());
		nextQuery.put("topicId", topicId);
		data.put("group", group);
		data.put("topic", topic);
		info.setData(data);
		return info;
	}

	private FrontDataInfo queryMore(FrontQueryVo vo, FrontDataInfo info, Set<String> collectId) {
		JSONObject nextQuery = new JSONObject();
		Map data = new HashMap<String, List<Object>>();
		List<FrontProduct> moreProducts = null;

		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		String productsJson = jedis.get("product:more:" + vo.getStartPage());
		if (StringUtils.isEmpty(productsJson)) {
			Calendar cl = Calendar.getInstance();
			cl.add(Calendar.MONTH, -1);
			Date beginTime = cl.getTime();
			vo.setBeginTime(beginTime);
			vo.setEndTime(new Date());
			moreProducts = frontMapper.queryNewProducts(vo);
			productsJson = JSONObject.toJSONString(moreProducts);
			jedis.set("product:more:" + vo.getStartPage(), productsJson);
		} else {
			moreProducts = JSONObject.parseArray(productsJson, FrontProduct.class);
		}
		// 先分组
		Map<Long, List<FrontProduct>> groupMap = new HashMap<>();
		for (FrontProduct product : moreProducts) {
			// 设置收藏状态
			if (collectId.contains(product.getId())) {
				product.setCollected(true);
			}
			long groupName = product.getShelvesTime().getTime();
			List<FrontProduct> list = groupMap.get(groupName);
			if (list == null) {
				list = new ArrayList<>();
				list.add(product);
				groupMap.put(groupName, list);
			}
			list.add(product);
		}
		// 组装数据格式
		JSONArray group = new JSONArray();
		Set<Entry<Long, List<FrontProduct>>> entrySet = groupMap.entrySet();
		for (Entry<Long, List<FrontProduct>> entry : entrySet) {
			JSONObject object = new JSONObject();
			object.put("groupName", entry.getKey());
			object.put("productList", entry.getValue());
			group.add(object);
		}
		if (moreProducts == null) {
			nextQuery = null;
		}
		nextQuery.put("queryProductType", "more");
		nextQuery.put("startPage", (vo.getStartPage() + 1) * vo.getPageSize());
		nextQuery.put("pageSize", vo.getPageSize());

		data.put("group", group);
		data.put("nextQuery", nextQuery);
		info.setData(data);
		return info;
	}

	private FrontDataInfo queryToday(FrontQueryVo vo, FrontDataInfo info, Set<String> collectId) {
		JSONObject nextQuery = new JSONObject();
		Map data = new HashMap<String, List<Object>>();

		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		List<FrontProduct> todayProducts = null;
		String productsJson = jedis.get("product:today");
		if (StringUtils.isEmpty(productsJson)) {
			Date nowTime = new Date();
			vo.setBeginTime(nowTime);
			vo.setEndTime(nowTime);
			todayProducts = frontMapper.queryNewProducts(vo);
			productsJson = JSONObject.toJSONString(todayProducts);
			jedis.set("product:today", productsJson);
		} else {
			todayProducts = JSONObject.parseArray(productsJson, FrontProduct.class);
		}
		// 设置收藏状态
		if (collectId.size() != 0) {
			for (FrontProduct product : todayProducts) {
				String id = product.getId();
				if (collectId.contains(id)) {
					product.setCollected(true);
				}
			}
		}
		if (todayProducts == null) {
			nextQuery = null;
		}
		nextQuery.put("startPage", (vo.getStartPage() + 1) * vo.getPageSize());
		nextQuery.put("pageSize", vo.getPageSize());

		data.put("productList", todayProducts);
		data.put("nextQuery", nextQuery);
		info.setData(data);
		return info;
	}

	@Override
	public FrontDataInfo queryTopics(FrontQueryVo vo) {
		String position = vo.getDispalyPosition();
		PhoneParamVo phoneVo = vo.getPhoneVo();
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
		Set<String> collectId = getUserCollectId(vo, Constant.TOPIC_TYPE);
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
	public FrontDataInfo queryTopicCollect(SweetCollectVo vo) {
		String userId = vo.getScoUserId();
		JSONObject nextQuery = new JSONObject();
		nextQuery.put("startPage", vo.getStartPage() + 1);
		nextQuery.put("pageSize", vo.getPageSize());
		vo.setStartPage(vo.getStartPage() * vo.getPageSize());

		// 先把收藏的内容保存到数据库
		List<SweetCollect> scs = new ArrayList<>();
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		Set<String> values = jedis.smembers("collecting:" + userId + ":" + Constant.TOPIC_TYPE);
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
			jedis.del("collecting:" + userId + ":" + Constant.TOPIC_TYPE);
			jedis.del("uc:" + userId);
			userMapper.saveCollect(scs);
		}

		redisUtil.closeRedis();
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
	public FrontDataInfo queryProductCollect(SweetCollectVo vo) {
		JSONObject nextQuery = new JSONObject();
		nextQuery.put("startPage", vo.getStartPage() + 1);
		nextQuery.put("pageSize", vo.getPageSize());
		vo.setStartPage(vo.getStartPage() * vo.getPageSize());
		String userId = vo.getScoUserId();
		// 先把收藏的内容保存到数据库
		List<SweetCollect> scs = new ArrayList<>();
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		Set<String> values = jedis.smembers("collecting:" + userId + ":" + Constant.PRODUCT_TYPE);
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
			jedis.del("collecting:" + userId + ":" + Constant.PRODUCT_TYPE);
			jedis.del("uc:" + userId);
			userMapper.saveCollect(scs);
		}
		redisUtil.closeRedis();
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
	private Set<String> getUserCollectId(FrontQueryVo vo, String type) {
		PhoneParamVo phoneVo = vo.getPhoneVo();
		String userId = phoneVo.getUserId();
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		Set<String> collectId = new HashSet<>();
		if (!StringUtils.isEmpty(userId)) {
			Set<String> smembers = jedis.smembers("collecting:" + userId + ":" + type);
			if (smembers != null && smembers.size() > 0) {
				for (String member : smembers) {
					String[] split = member.split(":");
					collectId.add(split[1]);
				}
			}
			// 先查询redis 没有再查数据库
			Set<String> partCid = jedis.smembers("uc:" + userId);
			if (partCid != null && partCid.size() == 0) {
				SweetCollectVo svo = new SweetCollectVo();
				svo.setScoUserId(userId);
				List<SweetCollect> collects = frontMapper.queryCollect(svo);
				for (SweetCollect collect : collects) {
					collectId.add(collect.getScoCollectId());
				}
				for (String cid : collectId) {
					jedis.sadd("uc:" + userId, cid);
				}
			} else {
				// 部分收藏ID
				collectId.addAll(partCid);
			}
		}
		return collectId;
	}

}
