package support.base.dao.mapper;

import java.util.List;

import support.base.pojo.po.Topic;
import support.base.pojo.vo.ProductVo;
import support.base.pojo.vo.TopicVo;

public interface TopicMapper {
	void saveTopic(Topic record);

	List<Topic> queryTopics(TopicVo vo);

	Integer queryTopicsNum(TopicVo vo);

	// 查询已经存在的排序值
	List<Integer> querySortValue(String shelvesTime);

	// 判断是否存在该值
	int sortValueExist(ProductVo vo);

	void updateTopic(Topic record);

	void updateTopics(List<Topic> topics);

	// 批量修改收藏量
	void changeTopicCollects(List<Topic> topics);

	// 批量更新topic的PV和UV
	void updateTopicStatistics(List<Topic> topics);
}