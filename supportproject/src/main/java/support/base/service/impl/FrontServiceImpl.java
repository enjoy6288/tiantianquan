package support.base.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import support.base.dao.mapper.FrontMapper;
import support.base.pojo.po.FrontProduct;
import support.base.pojo.po.FrontTopic;
import support.base.pojo.vo.FrontQueryVo;
import support.base.pojo.vo.PageQuery;
import support.base.pojo.vo.SweetCollectVo;
import support.base.process.result.FrontDataInfo;
import support.base.service.FrontService;
import support.base.util.RedisUtil;

@Service
public class FrontServiceImpl implements FrontService {
	@Autowired
	private FrontMapper frontMapper;

	@Override
	public FrontDataInfo queryNewProducts(FrontQueryVo vo) {
		String queryProductType = vo.getQueryProductType();
		Calendar cl = Calendar.getInstance();
		Date nowTime = new Date();
		cl.setTime(nowTime);
		JSONObject nextQuery = new JSONObject();
		List<FrontProduct> newProducts = new ArrayList<>();
		if (!StringUtils.isEmpty(queryProductType)) {
			// 查询当天的
			if (queryProductType.equals("today")) {
				vo.setBeginTime(nowTime);
				vo.setEndTime(nowTime);
				RedisUtil redisUtil = new RedisUtil();
				Jedis jedis = redisUtil.getJedis();
				String productsJson = jedis.get("product:today:" + vo.getStartPage());
				if (StringUtils.isEmpty(productsJson)) {
					newProducts = frontMapper.queryNewProducts(vo);
					productsJson = JSONObject.toJSONString(newProducts);
					jedis.set("product:today:" + vo.getStartPage(), productsJson);
				} else {
					newProducts = JSONObject.parseArray(productsJson, FrontProduct.class);
				}
				redisUtil.closeRedis();

			}
			// 查询最近一个月的
			if (queryProductType.equals("more")) {
				cl.add(Calendar.MONTH, -1);
				Date beginTime = cl.getTime();
				vo.setBeginTime(beginTime);
				vo.setEndTime(nowTime);
				RedisUtil redisUtil = new RedisUtil();
				Jedis jedis = redisUtil.getJedis();
				String productsJson = jedis.get("product:more:" + vo.getStartPage());
				if (StringUtils.isEmpty(productsJson)) {
					newProducts = frontMapper.queryNewProducts(vo);
					productsJson = JSONObject.toJSONString(newProducts);
					jedis.set("product:more:" + vo.getStartPage(), productsJson);
				} else {
					newProducts = JSONObject.parseArray(productsJson, FrontProduct.class);
				}
				redisUtil.closeRedis();
				nextQuery.put("queryProductType", "more");
			}
		}

		// 在查询主题商品的时候查询出主题信息
		String topicId = vo.getTopicId();
		Map data = new HashMap<String, List<Object>>();
		if (!StringUtils.isEmpty(topicId)) {
			nextQuery.put("topicId", topicId);
			FrontTopic topic = null;

			RedisUtil redisUtil = new RedisUtil();
			Jedis jedis = redisUtil.getJedis();
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
			// 根据主题的ID该主题的商品
			String productsJson = jedis.get("topic:" + topicId + ":product:" + vo.getStartPage());
			if (StringUtils.isEmpty(productsJson)) {
				newProducts = frontMapper.queryNewProducts(vo);
				productsJson = JSONObject.toJSONString(newProducts);
				jedis.set("topic:" + topicId + ":product:" + vo.getStartPage(), productsJson);
			} else {
				newProducts = JSONObject.parseArray(productsJson, FrontProduct.class);
			}
			redisUtil.closeRedis();
		}
		nextQuery.put("startPage", vo.getStartPage() + 1);
		nextQuery.put("pageSize", vo.getPageSize());
		vo.setStartPage(vo.getStartPage() * vo.getPageSize());

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
	public FrontDataInfo queryTopics(FrontQueryVo vo) {
		String position = vo.getDispalyPosition();
		Map data = new HashMap<String, List<Object>>();
		JSONObject nextQuery = new JSONObject();
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		// 以startpage作为是否是专题列表分页 不查询专题banner和推荐专题
		if (vo.getStartPage() == 0) {
			vo.setDispalyPosition("轮播banner");
			// 待设置vo.setPageSize(pageSize);
			String topListJson = jedis.get("topList:topic:");
			List<FrontTopic> topList = new ArrayList<>();
			if (StringUtils.isEmpty(topListJson)) {
				topList = frontMapper.queryTopics(vo);
				topListJson = JSONObject.toJSONString(topList);
				jedis.set("topList:topic:", topListJson);
			} else {
				topList = JSONObject.parseArray(topListJson, FrontTopic.class);
			}
			data.put("topList", topList);

			vo.setDispalyPosition("推荐专题");
			// 待设置vo.setPageSize(pageSize);
			String middleListJson = jedis.get("middleList:topic:");
			List<FrontTopic> middleList = new ArrayList<>();
			if (StringUtils.isEmpty(middleListJson)) {
				middleList = frontMapper.queryTopics(vo);
				middleListJson = JSONObject.toJSONString(topList);
				jedis.set("middleList:topic:", middleListJson);
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
		data.put("bottomList", bottomList);
		redisUtil.closeRedis();
		
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
		JSONObject nextQuery = new JSONObject();
		nextQuery.put("startPage", vo.getStartPage() + 1);
		nextQuery.put("pageSize", vo.getPageSize());
		vo.setStartPage(vo.getStartPage() * vo.getPageSize());
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

}
