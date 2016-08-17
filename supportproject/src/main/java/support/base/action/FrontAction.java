package support.base.action;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import support.base.dao.mapper.FrontMapper;
import support.base.pojo.po.FrontProduct;
import support.base.pojo.po.FrontTopic;
import support.base.pojo.vo.FrontQueryVo;
import support.base.pojo.vo.SweetCollectVo;
import support.base.process.result.FrontDataInfo;
import support.base.service.FrontService;

@Controller
@RequestMapping("/front")
public class FrontAction {
	@Autowired
	private FrontService frontService;
	Logger logger = Logger.getLogger(FrontAction.class);

	@RequestMapping(value = "/queryNewProducts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	FrontDataInfo queryNewProducts(RequestEntity<JSONObject> param) {
		JSONObject body = param.getBody();
		FrontQueryVo vo = JSONObject.toJavaObject(body, FrontQueryVo.class);
		return  frontService.queryNewProducts(vo);
	}

	@RequestMapping(value = "/queryTopics", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	FrontDataInfo queryTopics(RequestEntity<JSONObject> param) {
		JSONObject body = param.getBody();
		FrontQueryVo vo = new FrontQueryVo();//JSONObject.toJavaObject(body, FrontQueryVo.class);
		return frontService.queryTopics(vo);
	}

	@RequestMapping(value = "/queryTopicCollect", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	FrontDataInfo queryTopicCollect(RequestEntity<JSONObject> param) {
		JSONObject body = param.getBody();
		SweetCollectVo vo = JSONObject.toJavaObject(body, SweetCollectVo.class);
		return frontService.queryTopicCollect(vo);
	}

	@RequestMapping(value = "/queryProductCollect", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	FrontDataInfo queryProductCollect(RequestEntity<JSONObject> param) {
		JSONObject body = param.getBody();
		SweetCollectVo vo = JSONObject.toJavaObject(body, SweetCollectVo.class);
		return frontService.queryProductCollect(vo);
	}

}
