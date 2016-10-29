package support.base.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import support.base.dao.mapper.BackgroundUserMapper;
import support.base.pojo.po.BackgroundUser;
import support.base.process.context.Config;
import support.base.process.result.ResultInfo;
import support.base.process.result.ResultUtil;
import support.base.util.CommonUtil;

@Controller
public class FirstAction {
	@Autowired
	private BackgroundUserMapper mapper;


	// 后台登录
	@RequestMapping(value = "/backGroundLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject backGroundLogin(RequestEntity<JSONObject> data) throws Exception {
		JSONObject body = data.getBody();
		BackgroundUser vo = JSONObject.toJavaObject(body, BackgroundUser.class);
		int login = mapper.login(vo);
		if (login > 0) {
			return ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
		}
		return ResultUtil.createJSONPObject(Config.MESSAGE, 202, ResultInfo.TYPE_RESULT_FAIL);
	}

	// 首页
	@RequestMapping(value = "/first")
	public String first() throws Exception {
		return "/base/first";
	}
	

	// 欢迎页面
	@RequestMapping("/welcome")
	public String welcome() {
		return "/base/welcome";
	}

}
