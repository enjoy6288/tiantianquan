package support.base.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import support.base.dao.mapper.FrontMapper;
import support.base.pojo.po.FrontProduct;
import support.base.pojo.po.FrontTopic;
import support.base.pojo.vo.FrontQueryVo;
import support.base.pojo.vo.PageQuery;
import support.base.pojo.vo.SweetCollectVo;
import support.base.process.result.FrontDataInfo;
import support.base.service.FrontService;
import support.base.util.CommonUse;

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
		if (!StringUtils.isEmpty(queryProductType)) {
			if (queryProductType.equals("today")) {
				vo.setBeginTime(nowTime);
				vo.setEndTime(nowTime);
			}
			if (queryProductType.equals("more")) {
				// 设置一个月前的日期
				cl.add(Calendar.MONTH, -1);
				Date beginTime = cl.getTime();
				vo.setBeginTime(beginTime);
				vo.setEndTime(nowTime);
				nextQuery.put("queryProductType", "more");

			}
		}
		String topicId = vo.getTopicId();
		Map data = new HashMap<String, List<Object>>();
		// 在查询主题商品的时候查询出主题信息
		if (!StringUtils.isEmpty(topicId)) {
			nextQuery.put("topicId", topicId);
			// 避免每次分页查询的时候从数据库查询
			Object cache = CommonUse.getCache(topicId);
			if (cache == null) {
				List<FrontTopic> topList = frontMapper.queryTopics(vo);
				if (topList != null && topList.size() > 0) {
					data.put("topic", topList.get(0));
					CommonUse.addCache(topicId, topList.get(0));
				}
			} else {
				data.put("topic", cache);
			}
		}
		nextQuery.put("startPage", vo.getStartPage() + 1);
		nextQuery.put("pageSize", vo.getPageSize());
		vo.setStartPage(vo.getStartPage() * vo.getPageSize());

		List<FrontProduct> newProducts = frontMapper.queryNewProducts(vo);
		FrontDataInfo info = new FrontDataInfo();
		if(newProducts.size()==0){
			newProducts=null;
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
		//以startpage作为是否是专题列表分页 不查询专题banner和推荐专题
		if (vo.getStartPage()==0) {
			vo.setDispalyPosition("轮播banner");
			//待设置vo.setPageSize(pageSize);
			List<FrontTopic> topList = frontMapper.queryTopics(vo);
			data.put("topList", topList);

			vo.setDispalyPosition("推荐专题");
			//待设置vo.setPageSize(pageSize);
			List<FrontTopic> middleList = frontMapper.queryTopics(vo);
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
		List<FrontTopic> bottomList = frontMapper.queryTopics(vo);
		FrontDataInfo info = new FrontDataInfo();
		if(bottomList.size()==0){
			bottomList=null;
		}
		data.put("bottomList", bottomList);
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
		if(topicCollect.size()==0){
			topicCollect=null;
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
		if(productCollect.size()==0){
			productCollect=null;
		}
		data.put("productCollectList", productCollect);
		data.put("nextQuery", nextQuery);
		info.setData(data);
		return info;
	}

}
