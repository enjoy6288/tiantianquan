package support.base.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import support.base.pojo.vo.SweetCollectVo;
import support.base.pojo.vo.SweetUserVo;
import support.base.service.UserService;

@RestController
@RequestMapping("/user")
public class UserAction {
	@Autowired
	private UserService userService;

	// 发送短信
	@RequestMapping(value = "/sendMsg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JSONObject sendMsg(RequestEntity<JSONObject> data, HttpSession session) {
		JSONObject body = data.getBody();
		SweetUserVo vo = JSONObject.toJavaObject(body, SweetUserVo.class);
		return userService.sendMsg(vo, session);
	}

	// 验证码
	@RequestMapping(value = "/checkPhoneCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JSONObject checkPhoneCode(RequestEntity<JSONObject> data,HttpSession session) {
		JSONObject body = data.getBody();
		SweetUserVo vo = JSONObject.toJavaObject(body, SweetUserVo.class);
		return userService.checkPhoneCode(vo,session);
	}

	// 保存用户
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JSONObject saveUser(RequestEntity<JSONObject> data) {
		JSONObject body = data.getBody();
		SweetUserVo vo = JSONObject.toJavaObject(body, SweetUserVo.class);
		return userService.saveUser(vo);
	}

	// 登录
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JSONObject login(RequestEntity<JSONObject> data, HttpSession session) {
		JSONObject body = data.getBody();
		SweetUserVo vo = JSONObject.toJavaObject(body, SweetUserVo.class);
		return userService.login(vo, session);
	}

	// 更新个人信息 包含重置密码功能
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JSONObject updateUser(RequestEntity<JSONObject> data) {
		JSONObject body = data.getBody();
		SweetUserVo vo = JSONObject.toJavaObject(body, SweetUserVo.class);
		return userService.updateUser(vo);
	}

	// 收藏
	@RequestMapping(value = "/collect", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JSONObject collect(RequestEntity<JSONObject> data, HttpSession session) {
		JSONObject body = data.getBody();
		SweetCollectVo vo = JSONObject.toJavaObject(body, SweetCollectVo.class);
		return userService.collect(vo);
	}
}
