package support.base.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import support.base.dao.mapper.TopicMapper;
import support.base.pojo.po.Topic;
import support.base.pojo.vo.TopicVo;
import support.base.process.context.Config;
import support.base.process.result.ResultUtil;
import support.base.service.TopicService;
import support.base.util.CommonUtil;
import support.base.util.Constant;

@Service
public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicMapper topicMapper;

	@Override
	public void saveTopic(TopicVo vo, MultipartFile out, MultipartFile inner)
			throws Exception {
		String topicName = vo.getTopicName();
		String topicViceName = vo.getTopicViceName();
		String shelvesTime = vo.getShelvesTime();
		String sortValue = vo.getSortValue();
		boolean flag = StringUtils.isEmpty(topicName)
				|| StringUtils.isEmpty(topicViceName) || out == null
				|| StringUtils.isEmpty(shelvesTime)
				|| StringUtils.isEmpty(sortValue);
		if (flag) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 100,
					null));
		}
		Topic topic = new Topic();
		CommonUtil.VoToPo(vo, topic);
		topic.setId(CommonUtil.generateId());
		topic.setLinkUrl(vo.getGoingTo()+vo.getLinkUrl());
		// 外部banner
		if (out != null) {
			topic.setBannerOutterimg(CommonUtil.upload(out,Constant.TOPIC));
		}
		// 内部banner
		if (inner != null) {
			topic.setBannerInnerimg(CommonUtil.upload(inner,Constant.TOPIC));
		}
		topicMapper.saveTopic(topic);
	}

	@Override
	public List<Topic> queryTopics(TopicVo vo) {
		// TODO Auto-generated method stub
		return topicMapper.queryTopics(vo);
	}

	@Override
	public void updateTopic(Topic record) {
		// TODO Auto-generated method stub
		topicMapper.updateTopic(record);
	}

	@Override
	public Integer queryTopicsNum(TopicVo vo) {
		return topicMapper.queryTopicsNum(vo);
	}

	@Override
	public void updateTopics(List<Topic> topics) {
		topicMapper.updateTopics(topics);
	}

}
