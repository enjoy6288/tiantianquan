package support.base.service;


import support.base.pojo.vo.FrontQueryVo;
import support.base.pojo.vo.SweetCollectVo;
import support.base.process.result.FrontDataInfo;



public interface FrontService {
	FrontDataInfo queryNewProducts(FrontQueryVo vo);
	FrontDataInfo queryTopics(FrontQueryVo vo);
	FrontDataInfo queryTopicCollect(SweetCollectVo vo);
	FrontDataInfo queryProductCollect(SweetCollectVo vo);
}
