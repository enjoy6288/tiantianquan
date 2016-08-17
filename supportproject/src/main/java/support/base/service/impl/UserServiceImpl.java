package support.base.service.impl;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import support.base.dao.mapper.FrontMapper;
import support.base.dao.mapper.SweetUserMapper;
import support.base.pojo.po.SweetCollect;
import support.base.pojo.po.SweetUser;
import support.base.pojo.vo.SweetCollectVo;
import support.base.pojo.vo.SweetUserVo;
import support.base.process.context.Config;
import support.base.process.result.ResultInfo;
import support.base.process.result.ResultUtil;
import support.base.service.UserService;
import support.base.util.CommonUtil;
import support.base.util.Constant;
import support.base.util.DesPasswd;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SweetUserMapper userMapper;
	@Autowired
	private FrontMapper frontMapper;

	@Override
	public JSONObject login(SweetUserVo vo,HttpSession session) {
		String loginName = vo.getLoginName();
		// 用户名为空
		if (StringUtils.isEmpty(loginName)) {
			return ResultUtil.createJSONPObject(Config.MESSAGE, 304, ResultInfo.TYPE_RESULT_FAIL);
		}
		// 密码为空
		String passwd = vo.getPasswd();
		if (StringUtils.isEmpty(passwd)) {
			return ResultUtil.createJSONPObject(Config.MESSAGE, 305, ResultInfo.TYPE_RESULT_FAIL);
		}
		vo.setPasswd(DesPasswd.strEnc(passwd));
		//如果是手机号
		if(StringUtils.isNumeric(loginName)){
			vo.setPhoneNum(loginName);
		}else {
			vo.setUserName(loginName);
		}
		SweetUser user = userMapper.queryUser(vo);
		JSONObject jsonObject = null;
		if (user !=null ) {// 操作成功
			jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
			session.setAttribute("loginName", loginName);
		} else {// 用户或密码不正确
			jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 306, ResultInfo.TYPE_RESULT_FAIL);
		}
		return jsonObject;
	}

	@Override
	public JSONObject sendMsg(SweetUserVo vo, HttpSession session) {
		String phoneNum = vo.getPhoneNum();
		// 电话号码是否有误
		Pattern p = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
		Matcher m = p.matcher(phoneNum);
		boolean flag = m.matches();
		if (!flag) {
			return ResultUtil.createJSONPObject(Config.MESSAGE, 301, ResultInfo.TYPE_RESULT_FAIL);
		}
		// 发送验证码 验证码存入session里面 验证验证码
		String phoneCode=new Random().nextInt(9999)+"";
		String msg="同事您好，感谢您对此次测试的配合。["+phoneCode+"]";
		CommonUtil.sendMsg(phoneNum, msg);
		session.setAttribute(phoneNum, phoneCode);
		return ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
	}
	
	@Override
	public JSONObject checkPhoneCode(SweetUserVo vo, HttpSession session) {
		String phoneNum = vo.getPhoneNum();
		String phoneCode = vo.getPhoneCode();
		if(!StringUtils.isEmpty(phoneNum)){
			String sessionCode = (String)session.getAttribute(phoneNum);
			if(phoneCode.equals(sessionCode)){
				return ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
			}else {//验证码有误
				return ResultUtil.createJSONPObject(Config.MESSAGE, 309, ResultInfo.TYPE_RESULT_FAIL);
			}
		}
		return null;
	}

	@Override
	public JSONObject saveUser(SweetUserVo vo) {
		// 验证用户是否存在
		SweetUser user = userMapper.queryUser(vo);
		if (user !=null ) {
			return ResultUtil.createJSONPObject(Config.MESSAGE, 303, ResultInfo.TYPE_RESULT_FAIL);
		}
		String phoneNum = vo.getPhoneNum();
		vo.setUserName(phoneNum);
		// 输入密码
		SweetUser sweetUser = new SweetUser();
		CommonUtil.VoToPo(vo, sweetUser);
		sweetUser.setPasswd(DesPasswd.strEnc(vo.getNewPasswd()));
		UUID randomUUID = UUID.randomUUID();
		sweetUser.setId(randomUUID.toString());
		int result = userMapper.saveUser(sweetUser);
		JSONObject jsonObject = null;
		if (result > 0) {// 操作成功
			jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
		} else {// 操作失败
			jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 202, ResultInfo.TYPE_RESULT_FAIL);
		}
		return jsonObject;
	}

	@Override
	public JSONObject collect(SweetCollectVo vo) {
		List<SweetCollect> collect = frontMapper.queryCollect(vo);
		if(collect!=null&&collect.size()>0){
			return ResultUtil.createJSONPObject(Config.MESSAGE, 308, ResultInfo.TYPE_RESULT_FAIL);
		}
		SweetCollect sweetCollect=new SweetCollect();
		CommonUtil.VoToPo(vo, sweetCollect);
		String collectTopic = sweetCollect.getCollectTopic();
		//0为商品 1为主题
		if(StringUtils.isEmpty(collectTopic)){
			sweetCollect.setCollectType("0");
		}else {
			sweetCollect.setCollectType("1");
		}
		int result=userMapper.saveCollect(sweetCollect);
		JSONObject jsonObject = null;
		if (result > 0) {// 操作成功
			jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
		} else {// 操作失败
			jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 202, ResultInfo.TYPE_RESULT_FAIL);
		}
		return jsonObject;
	}

	

	@Override
	public JSONObject updateUser(SweetUserVo vo) {
		//vo.setAvatarUrl(CommonUtil.upload(null,Constant.AVATAR_FILE_PATH));
		
		//通过原密码重置密码(新旧密码都会填写)
		String passwd = vo.getPasswd();
		String newPasswd = vo.getNewPasswd();
		if(!StringUtils.isEmpty(passwd)&&!StringUtils.isEmpty(newPasswd)){
			vo.setPasswd(DesPasswd.strEnc(vo.getPasswd()));
			SweetUser user = userMapper.queryUser(vo);
			JSONObject jsonObject = null;
			if (user ==null ) {// 密码不正确
				jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 310, ResultInfo.TYPE_RESULT_FAIL);
			} else {// 重置密码
				vo.setPasswd(DesPasswd.strEnc(newPasswd));
			}
		}
		//通过手机号码重置密码(只填写新密码)
		else if(StringUtils.isEmpty(passwd)&&!StringUtils.isEmpty(newPasswd)) {
			vo.setPasswd(DesPasswd.strEnc(newPasswd));
		}
		
		//TODO 头像图片上传
		
		userMapper.updateUser(vo);
		return ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
	}

}
