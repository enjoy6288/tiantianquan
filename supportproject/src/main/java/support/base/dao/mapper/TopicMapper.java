package support.base.dao.mapper;


import java.util.List;

import support.base.pojo.po.Topic;
import support.base.pojo.vo.TopicVo;

public interface TopicMapper {
    void saveTopic(Topic record);
    List<Topic> queryTopics(TopicVo vo);
    Integer queryTopicsNum(TopicVo vo);
    void updateTopic(Topic record);
    void updateTopics(List<Topic> topics);
}