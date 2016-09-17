package support.base.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import support.base.pojo.vo.PhoneParamVo;
import support.base.pojo.vo.SweetCollectVo;
import support.base.pojo.vo.SweetUserVo;
import support.base.service.UserService;

@RestController
@RequestMapping("/user")
public class UserAction {
	@Autowired
	private UserService userService;

	// 发送短信
	@RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
	public @ResponseBody
	JSONObject sendMsg(SweetUserVo vo) {
		return userService.sendMsg(vo);
	}

	// 验证码
	@RequestMapping(value = "/checkPhoneCode", method = RequestMethod.POST)
	public @ResponseBody
	JSONObject checkPhoneCode(SweetUserVo vo) {
		return userService.checkPhoneCode(vo);
	}

	// 保存用户
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public @ResponseBody
	JSONObject saveUser(SweetUserVo vo, MultipartFile img) {
		return userService.saveUser(vo);
	}

	// 登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	JSONObject login(SweetUserVo vo) {
		return userService.login(vo);
	}

	// 退出登录
	@RequestMapping(value = "/loginOut", method = RequestMethod.POST)
	public @ResponseBody
	JSONObject loginOut(SweetUserVo vo) {
		return userService.loginOut(vo);
	}

	// 更新个人信息 包含重置密码功能
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public @ResponseBody
	JSONObject updateUser(SweetUserVo vo, MultipartFile avatarImg) {
		return userService.updateUser(vo, avatarImg);
	}

	// 收藏
	@RequestMapping(value = "/collect", method = RequestMethod.POST)
	public @ResponseBody
	JSONObject collect(SweetCollectVo vo,PhoneParamVo phoneVo) {
		vo.setScoUserId(phoneVo.getUserId());
		return userService.collect(vo);
	}
	
	// 删除收藏
	@RequestMapping(value = "/delCollect", method = RequestMethod.POST)
	public @ResponseBody
	JSONObject delCollect(SweetCollectVo vo) {
		return userService.delCollect(vo);
	}
	
	// 删除收藏
		@RequestMapping(value = "/saveC", method = RequestMethod.POST)
		public @ResponseBody
		JSONObject saveC(SweetCollectVo vo) {
			userService.updateCollectNum();
			return null;
		}
	
	
	
}
