package support.base.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import support.base.dao.mapper.FrontMapper;
import support.base.pojo.po.FrontProduct;
import support.base.pojo.po.FrontTopic;
import support.base.pojo.vo.FrontQueryVo;
import support.base.pojo.vo.SweetCollectVo;
import support.base.process.result.FrontDataInfo;
import support.base.service.FrontService;

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
		if (!StringUtils.isEmpty(queryProductType)) {
			if (queryProductType.equals("today")) {
				vo.setBeginTime(nowTime);
				vo.setEndTime(nowTime);
				vo.setLimitNum("10");
			}
			if (queryProductType.equals("more")) {
				// 设置一个月前的日期
				cl.add(Calendar.MONTH, -1);
				Date beginTime = cl.getTime();
				vo.setBeginTime(beginTime);
				vo.setEndTime(nowTime);
			}
		}
		String topicId = vo.getTopicId();
		Map data = new HashMap<String, List<Object>>();
		//在查询主题商品的时候查询出主题信息
		if(!StringUtils.isEmpty(topicId)){
			List<FrontTopic> topList = frontMapper.queryTopics(vo);
			if(topList!=null&&topList.size()>0){
				data.put("topic", topList.get(0));
			}
		}
		List<FrontProduct> newProducts = frontMapper.queryNewProducts(vo);
		FrontDataInfo info = new FrontDataInfo();
		data.put("productList", newProducts);
		info.setData(data);
		return info;
	}

	@Override
	public FrontDataInfo queryTopics(FrontQueryVo vo) {
		vo.setDispalyPosition("轮播banner");
		vo.setLimitNum("10");
		List<FrontTopic> topList = frontMapper.queryTopics(vo);
		
		vo.setDispalyPosition("推荐专题");
		vo.setLimitNum("10");
		List<FrontTopic> middleList = frontMapper.queryTopics(vo);
		
		// 获取一个月的数据
		vo.setDispalyPosition("专题列表");
		vo.setLimitNum(null);
		Calendar cl = Calendar.getInstance();
		Date nowTime = new Date();
		cl.setTime(nowTime);
		cl.add(Calendar.MONTH, -1);
		Date beginTime = cl.getTime();
		vo.setBeginTime(beginTime);
		vo.setEndTime(nowTime);
		List<FrontTopic> bottomList = frontMapper.queryTopics(vo);
		
		FrontDataInfo info = new FrontDataInfo();
		Map data = new HashMap<String, List<Object>>();
		data.put("topList", topList);
		data.put("middleList", middleList);
		data.put("bottomList", bottomList);
		info.setData(data);
		return info;
	}

	@Override
	public FrontDataInfo queryTopicCollect(SweetCollectVo vo) {
		List<FrontTopic> topicCollect = frontMapper.queryTopicCollect(vo);
		FrontDataInfo info = new FrontDataInfo();
		Map data = new HashMap<String, List<Object>>();
		data.put("topicCollectList", topicCollect);
		info.setData(data);
		return info;
	}

	@Override
	public FrontDataInfo queryProductCollect(SweetCollectVo vo) {
		List<FrontProduct> productCollect = frontMapper.queryProductCollect(vo);
		FrontDataInfo info = new FrontDataInfo();
		Map data = new HashMap<String, List<Object>>();
		data.put("productCollectList", productCollect);
		info.setData(data);
		return info;
	}


}
