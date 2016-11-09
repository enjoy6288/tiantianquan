package support.base.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import support.base.dao.mapper.FrontMapper;
import support.base.dao.mapper.ProductMapper;
import support.base.dao.mapper.StatisticsMapper;
import support.base.dao.mapper.SweetUserMapper;
import support.base.dao.mapper.TopicMapper;
import support.base.pojo.po.Product;
import support.base.pojo.po.SweetCollect;
import support.base.pojo.po.SweetStatistics;
import support.base.pojo.po.SweetUser;
import support.base.pojo.po.Topic;
import support.base.pojo.vo.SweetCollectVo;
import support.base.pojo.vo.SweetUserVo;
import support.base.process.context.Config;
import support.base.process.result.ResultInfo;
import support.base.process.result.ResultUtil;
import support.base.service.UserService;
import support.base.util.CommonUtil;
import support.base.util.Constant;
import support.base.util.RedisUtil;
import support.base.util.SpringPropertyUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SweetUserMapper userMapper;
	@Autowired
	private FrontMapper frontMapper;
	@Autowired
	private TopicMapper topicMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private StatisticsMapper statisticsMapper;

	@Override
	public JSONObject thirdPlatformSave(SweetUserVo vo) {
		SweetUser sweetUser = userMapper.queryUser(vo);
		JSONObject jsonObject = null;
		if (sweetUser == null) {
			jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
			sweetUser = new SweetUser();
			CommonUtil.VoToPo(vo, sweetUser);
			sweetUser.setId(CommonUtil.generateId());

			Map<String, Object> userMap = new HashMap<>();
			String token = UUID.randomUUID().toString();
			sweetUser.setToken(token);
			userMap.put("user", sweetUser);
			jsonObject.put("data", userMap);
			RedisUtil.set("token:" + token, token);
			userMapper.saveUser(sweetUser);
		} else {
			// 如果用户有更新第三方帐号信息同时更新
			if (!sweetUser.getNickName().equals(vo.getNickName()) || !sweetUser.getSex().equals(vo.getSex())
					|| !sweetUser.getAvatarUrl().equals(vo.getAvatarUrl())) {
				userMapper.updateUser(vo);
			}
			jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
			Map<String, Object> userMap = new HashMap<>();
			userMap.put("user", sweetUser);
			jsonObject.put("data", userMap);
			String token = sweetUser.getToken();
			RedisUtil.set("token:" + token, token);
		}
		return jsonObject;
	}

	@Override
	public JSONObject login(SweetUserVo vo) {
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
		// 如果是手机号
		if (StringUtils.isNumeric(loginName)) {
			vo.setPhoneNum(loginName);
		} else {
			vo.setNickName(loginName);
		}
		SweetUser user = userMapper.queryUser(vo);
		JSONObject jsonObject = null;
		if (user != null) {// 操作成功
			jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
			user.setPasswd("");
			Map<String, Object> userMap = new HashMap<>();
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			userMap.put("user", user);
			jsonObject.put("data", userMap);
			
			RedisUtil.set("token:" + token, token);
			
		} else {// 用户或密码不正确
			jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 306, ResultInfo.TYPE_RESULT_FAIL);
		}
		return jsonObject;
	}

	@Override
	public JSONObject loginOut(SweetUserVo vo) {
		String token = vo.getToken();
		if (!StringUtils.isEmpty(token)) {
			RedisUtil.del("token:" + token);
		}
		return ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
	}

	@Override
	public JSONObject sendMsg(SweetUserVo vo) {
		String phoneNum = vo.getPhoneNum();
		// 电话号码是否有误
		Pattern p = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
		Matcher m = p.matcher(phoneNum);
		boolean flag = m.matches();
		if (!flag) {
			return ResultUtil.createJSONPObject(Config.MESSAGE, 301, ResultInfo.TYPE_RESULT_FAIL);
		}
		// 验证用户是否存在
		SweetUser user = userMapper.queryUser(vo);
		String sendMsgType = vo.getSendMsgType();
		// 如果是注册 先判断该记录是否有
		if ("register".equals(sendMsgType)) {
			if (user != null) {
				return ResultUtil.createJSONPObject(Config.MESSAGE, 302, ResultInfo.TYPE_RESULT_FAIL);
			}
		}
		// 如果是重置密码 如果用户没有注册 先注册
		if ("resetPasswd".equals(sendMsgType)) {
			if (user == null) {
				return ResultUtil.createJSONPObject(Config.MESSAGE, 311, ResultInfo.TYPE_RESULT_FAIL);
			}
		}
		// 发送验证码 验证码存入session里面 验证验证码
		String phoneCode = (new Random().nextInt(9999) + 1000) + "";
		String msg = "同事您好，感谢您对此次测试的配合。[" + phoneCode + "]";
		CommonUtil.sendMsg(phoneNum, msg);
		RedisUtil.setKeyWithExpire("phoneCode:" + phoneNum, 300, phoneCode);
		return ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
	}

	@Override
	public JSONObject checkPhoneCode(SweetUserVo vo) {
		String phoneNum = vo.getPhoneNum();
		String phoneCode = vo.getPhoneCode();
		if (!StringUtils.isEmpty(phoneNum)) {
			String serverPhoneCode = RedisUtil.get("phoneCode:" + phoneNum);
			if (phoneCode.equals(serverPhoneCode)) {
				return ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
			} else {// 验证码有误
				return ResultUtil.createJSONPObject(Config.MESSAGE, 309, ResultInfo.TYPE_RESULT_FAIL);
			}
		}
		return null;
	}

	@Override
	public JSONObject saveUser(SweetUserVo vo) {
		String phoneNum = vo.getPhoneNum();
		vo.setNickName(phoneNum);
		// 输入密码
		SweetUser sweetUser = new SweetUser();
		CommonUtil.VoToPo(vo, sweetUser);
		sweetUser.setPasswd(vo.getNewPasswd());
		sweetUser.setId(CommonUtil.generateId());
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
		String userId = vo.getScoUserId();
		String value = userId + ":" + vo.getScoCollectId() + ":" + vo.getScoCollectType();
		// 先查数据库是否收藏有
		Set<String> partCid = RedisUtil.smembers("uc:" + userId);
		if (partCid == null || partCid.size() == 0) {
			List<SweetCollect> collects = frontMapper.queryCollect(vo);
			for (SweetCollect sweetCollect : collects) {
				RedisUtil.sadd("uc:" + userId, sweetCollect.getScoCollectId());
			}
		}
		boolean dbExist = RedisUtil.sismember("uc:" + userId, vo.getScoCollectId());
		if (dbExist) {
			return ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
		}
		// 再查当前收藏是否有
		boolean redisExist = RedisUtil.sismember("collecting:" + userId + ":" + vo.getScoCollectType(), value);
		if (redisExist) {
			RedisUtil.srem("collecting:" + userId + ":" + vo.getScoCollectType(), value);
		} else {
			RedisUtil.sadd("collecting:" + userId + ":" + vo.getScoCollectType(), value);
		}
		return ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
	}

	@Override
	public void saveCollect() {
		List<SweetCollect> scs = new ArrayList<>();
		Set<String> collecting = RedisUtil.keys("collecting*");
		String[] collectingKeys = collecting.toArray(new String[collecting.size()]);
		if (collectingKeys.length > 0) {
			Set<String> values = RedisUtil.sunion(collectingKeys);
			SweetCollect sc = null;
			for (String value : values) {
				String[] split = value.split(":");
				sc = new SweetCollect();
				sc.setScoUserId(split[0]);
				sc.setScoCollectId(split[1]);
				sc.setScoCollectType(new Integer(split[2]));
				scs.add(sc);
				RedisUtil.incr("incr:" + split[2] + ":" + split[1]);
			}
			if (scs.size() > 0) {
				//删除收藏中的信息
				RedisUtil.del(collectingKeys);
				//删除用户收藏缓存信息
				Set<String> dbuc = RedisUtil.keys("dbuc*");
				String[] dbucKeys = dbuc.toArray(new String[dbuc.size()]);
				if (dbucKeys.length > 0) {
					RedisUtil.del(dbucKeys);
				}
				//更新数据库
				userMapper.saveCollect(scs);
			}
		}
	}

	@Override
	public JSONObject updateUser(SweetUserVo vo, MultipartFile avatarImg) {
		// TODO 头像图片上传
		JSONObject jsonObject = new JSONObject();
		if (avatarImg != null) {
			// 删除原来的文件
			String oldImg = vo.getOldAvatarUrl();
			if (oldImg != null) {
				String imgPrefix = SpringPropertyUtil.getContextProperty(Constant.IMG_PREFIX);
				String partOldImg = oldImg.substring(imgPrefix.length());
				oldImg = SpringPropertyUtil.getContextProperty(Constant.FILE_PATH_PREFIX) + partOldImg;
				File file = new File(oldImg);
				if (file.exists()) {
					file.delete();
				}
			}
			vo.setAvatarUrl(CommonUtil.upload(avatarImg, Constant.AVATAR));
		}
		Map<String, Object> userMap = new HashMap<>();
		SweetUser user = new SweetUser();
		user.setAvatarUrl(vo.getAvatarUrl());
		userMap.put("user", user);
		jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
		jsonObject.put("data", userMap);

		// 通过原密码重置密码(新旧密码都会填写)
		String passwd = vo.getPasswd();
		String newPasswd = vo.getNewPasswd();
		if (!StringUtils.isEmpty(passwd) && !StringUtils.isEmpty(newPasswd)) {
			user = userMapper.queryUser(vo);
			if (user == null) {// 密码不正确
				return jsonObject = ResultUtil.createJSONPObject(Config.MESSAGE, 310, ResultInfo.TYPE_RESULT_FAIL);
			} else {// 重置密码
				vo.setPasswd(newPasswd);
			}
		}
		// 通过手机号码重置密码(只填写新密码)
		else if (StringUtils.isEmpty(passwd) && !StringUtils.isEmpty(newPasswd)) {
			vo.setPasswd(newPasswd);
		}
		userMapper.updateUser(vo);
		return jsonObject;
	}

	@Override
	public JSONObject delCollect(SweetCollectVo vo) {
		String scoIds = vo.getScoIds();
		List<String> ids = new ArrayList<>();
		String[] idString = {};
		if (!StringUtils.isEmpty(scoIds)) {
			idString = scoIds.split(",");
			ids = Arrays.asList(idString);
		}
		String userId = vo.getScoUserId();
		RedisUtil.del("uc:" + userId);
		userMapper.delCollect(ids);
		return ResultUtil.createJSONPObject(Config.MESSAGE, 201, ResultInfo.TYPE_RESULT_SUCCESS);
	}

	@Override
	public void delCache() {
		Set<String> keys = RedisUtil.keys("*topic*");
		String[] arrayKeys = keys.toArray(new String[keys.size()]);
		if (arrayKeys.length > 0) {
			RedisUtil.del(arrayKeys);
		}

		keys = RedisUtil.keys("*product*");
		arrayKeys = keys.toArray(new String[keys.size()]);
		if (arrayKeys.length > 0) {
			RedisUtil.del(arrayKeys);
		}
	}

	@Override
	public void updateCollectNum() {
		Set<String> keys = RedisUtil.keys("incr:" + Constant.TOPIC_TYPE + "*");
		List<Topic> topics = new ArrayList<>();
		for (String key : keys) {
			Topic topic = new Topic();
			String num = RedisUtil.get(key);
			key = key.substring(key.lastIndexOf(":") + 1);
			topic.setId(key);
			topic.setCollectActually(Long.parseLong(num));
			topics.add(topic);
			topic = null;
		}
		if (topics.size() > 0) {
			topicMapper.changeTopicCollects(topics);
		}

		keys = RedisUtil.keys("incr:" + Constant.PRODUCT_TYPE + "*");
		List<Product> products = new ArrayList<>();
		for (String key : keys) {
			Product product = new Product();
			String num = RedisUtil.get(key);
			key = key.substring(key.lastIndexOf(":") + 1);
			product.setId(key);
			product.setCollectActually(Long.parseLong(num));
			products.add(product);
			product = null;
		}
		if (products.size() > 0) {
			productMapper.changeProductsCollects(products);
		}

		keys = RedisUtil.keys("incr*");
		String[] arrayKeys = keys.toArray(new String[keys.size()]);
		if (arrayKeys.length > 0) {
			RedisUtil.del(arrayKeys);
		}

	}

	@Override
	public void statistics() {
		// 主页总浏览量
		String homePv = RedisUtil.get("home:pv");
		// 主页独立访客
		Long homeUv = RedisUtil.scard("home:uv");
		Set<String> smembers = RedisUtil.smembers("home:uv");
		SweetStatistics statistics = new SweetStatistics();
		statistics.setId(CommonUtil.generateId());
		statistics.setType(0);
		statistics.setPv(homePv);
		statistics.setUv(homeUv + "");
		statisticsMapper.saveStatistics(statistics);
		RedisUtil.del("home:pv");
		RedisUtil.del("home:uv");

		// 专题总浏览量
		String subjectPv = RedisUtil.get("subject:pv");
		// 专题独立访客
		Long subjectUv = RedisUtil.scard("subject:uv");
		statistics = new SweetStatistics();
		statistics.setId(CommonUtil.generateId());
		statistics.setType(1);
		statistics.setPv(subjectPv);
		statistics.setUv(subjectUv + "");
		statisticsMapper.saveStatistics(statistics);
		RedisUtil.del("subject:pv");
		RedisUtil.del("subject:uv");

		// 单个专题浏览量
		Set<String> pvKeys = RedisUtil.keys("subject:pv:*");
		// 单个专题独立访客
		Set<String> uvKeys = RedisUtil.keys("subject:uv:*");
		List<Topic> topics = new ArrayList<>();
		Map<String, Topic> topicMap = new HashMap<>();
		for (String key : pvKeys) {
			String singleSubjectPv = RedisUtil.get(key);
			key = key.substring(11);
			Topic topic = new Topic();
			topic.setId(key);
			topic.setPv(Long.parseLong(singleSubjectPv));
			topicMap.put(key, topic);
		}
		for (String key : uvKeys) {
			long singleSubjectUv = RedisUtil.scard(key);
			key = key.substring(11);
			Topic topic = topicMap.get(key);
			topic.setUv(singleSubjectUv);
		}
		List<Topic> values = new ArrayList<>();
		Set<Entry<String, Topic>> entrySet = topicMap.entrySet();
		for (Entry<String, Topic> entry : entrySet) {
			values.add(entry.getValue());
		}
		topicMapper.updateTopicStatistics(values);

		String[] arrayKeys = pvKeys.toArray(new String[pvKeys.size()]);
		if (arrayKeys.length > 0) {
			RedisUtil.del(arrayKeys);
		}

		arrayKeys = uvKeys.toArray(new String[uvKeys.size()]);
		if (arrayKeys.length > 0) {
			RedisUtil.del(arrayKeys);
		}

	}

}
