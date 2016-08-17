package support.base.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import support.base.pojo.po.Topic;
import support.base.pojo.vo.TopicVo;

public interface TopicService {
	void saveTopic(TopicVo vo, MultipartFile out, MultipartFile inner)throws Exception;
    List<Topic> queryTopics(TopicVo vo);
    Integer queryTopicsNum(TopicVo vo);
    void updateTopic(Topic record);
    void updateTopics(List<Topic> topics);
}
