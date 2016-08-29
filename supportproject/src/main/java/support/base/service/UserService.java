package support.base.service;


import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import support.base.pojo.vo.SweetCollectVo;
import support.base.pojo.vo.SweetUserVo;



public interface UserService {
	JSONObject saveUser(SweetUserVo vo);
	JSONObject updateUser(SweetUserVo vo,MultipartFile avatarImg);
	JSONObject login(SweetUserVo vo);
	JSONObject loginOut(SweetUserVo vo);
	JSONObject sendMsg(SweetUserVo vo);
	JSONObject collect(SweetCollectVo vo);
	JSONObject checkPhoneCode(SweetUserVo vo);
	void saveCollect();
	JSONObject delCollect(SweetCollectVo vo);
	
	
}
