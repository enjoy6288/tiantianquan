package support.base.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import support.base.dao.mapper.BackgroundUserMapper;
import support.base.pojo.po.BackgroundUser;
import support.base.process.context.Config;
import support.base.process.result.ResultUtil;
import support.base.process.result.SubmitResultInfo;

@Controller
public class FirstAction {
	@Autowired
	private BackgroundUserMapper mapper;

	// 后台登录
	@RequestMapping(value = "/backGroundLogin")
	public @ResponseBody
	SubmitResultInfo backGroundLogin(BackgroundUser vo, HttpServletRequest request) throws Exception {
		// 校验验证码
		HttpSession session = request.getSession();// 获取session
		String randomcode_session = (String) session.getAttribute("validateCode");// 从session获取正确的验证码
		if (!randomcode_session.equals(vo.getRandomcode())) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 113, null));
		}
		int login = mapper.login(vo);
		if (login == 0) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 306, null));
		}
		session.setAttribute("logined", vo.getUserName());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 107,null));
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

	/**
	 * 登录页面显示
	 */
	@RequestMapping("/login")
	public String login() throws Exception {
		return "/base/login";
	}

}
