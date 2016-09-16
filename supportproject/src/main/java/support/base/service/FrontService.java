package support.base.service;


import support.base.pojo.vo.FrontQueryVo;
import support.base.pojo.vo.PhoneParamVo;
import support.base.pojo.vo.SweetCollectVo;
import support.base.process.result.FrontDataInfo;



public interface FrontService {
	FrontDataInfo queryNewProducts(FrontQueryVo vo,PhoneParamVo phoneVo);
	FrontDataInfo queryTopics(FrontQueryVo vo,PhoneParamVo phoneVo);
	FrontDataInfo queryTopicCollect(SweetCollectVo vo,PhoneParamVo phoneVo);
	FrontDataInfo queryProductCollect(SweetCollectVo vo,PhoneParamVo phoneVo);
}
