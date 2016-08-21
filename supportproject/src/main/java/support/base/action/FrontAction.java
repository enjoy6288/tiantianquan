package support.base.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



import support.base.pojo.vo.FrontQueryVo;
import support.base.pojo.vo.PhoneParamVo;
import support.base.pojo.vo.SweetCollectVo;
import support.base.process.result.FrontDataInfo;
import support.base.service.FrontService;

@Controller
@RequestMapping("/front")
public class FrontAction {
	@Autowired
	private FrontService frontService;
	Logger logger = Logger.getLogger(FrontAction.class);

	@RequestMapping(value = "/queryNewProducts", method = RequestMethod.POST)
	public @ResponseBody
	FrontDataInfo queryNewProducts(FrontQueryVo vo,PhoneParamVo phoneVo) {
		return  frontService.queryNewProducts(vo);
	}

	@RequestMapping(value = "/queryTopics", method = RequestMethod.POST)
	public @ResponseBody
	FrontDataInfo queryTopics(FrontQueryVo vo) {
		return frontService.queryTopics(vo);
	}

	@RequestMapping(value = "/queryTopicCollect", method = RequestMethod.POST)
	public @ResponseBody
	FrontDataInfo queryTopicCollect(SweetCollectVo vo) {
		return frontService.queryTopicCollect(vo);
	}

	@RequestMapping(value = "/queryProductCollect", method = RequestMethod.POST)
	public @ResponseBody
	FrontDataInfo queryProductCollect(SweetCollectVo vo) {
		return frontService.queryProductCollect(vo);
	}

}
