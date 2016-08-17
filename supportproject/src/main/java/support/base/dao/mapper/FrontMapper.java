package support.base.dao.mapper;

import java.util.List;

import support.base.pojo.po.FrontProduct;
import support.base.pojo.po.FrontTopic;
import support.base.pojo.po.SweetCollect;
import support.base.pojo.vo.FrontQueryVo;
import support.base.pojo.vo.SweetCollectVo;

public interface FrontMapper {
	List<FrontProduct> queryNewProducts(FrontQueryVo vo);

	List<FrontTopic> queryTopics(FrontQueryVo vo);

	// 查询收藏的主题
	List<FrontTopic> queryTopicCollect(SweetCollectVo vo);

	// 查询收藏的商品
	List<FrontProduct> queryProductCollect(SweetCollectVo vo);

	// 根据条件查询收藏的内容
	List<SweetCollect> queryCollect(SweetCollectVo vo);

}