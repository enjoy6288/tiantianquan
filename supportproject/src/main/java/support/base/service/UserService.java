package support.base.service;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

import support.base.pojo.vo.SweetCollectVo;
import support.base.pojo.vo.SweetUserVo;



public interface UserService {
	JSONObject saveUser(SweetUserVo vo);
	JSONObject updateUser(SweetUserVo vo);
	JSONObject login(SweetUserVo vo,HttpSession session);
	JSONObject sendMsg(SweetUserVo vo,HttpSession session);
	JSONObject collect(SweetCollectVo vo);
	JSONObject checkPhoneCode(SweetUserVo vo,HttpSession session);
	
	
}
