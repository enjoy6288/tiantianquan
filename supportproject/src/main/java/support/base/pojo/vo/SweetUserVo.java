package support.base.pojo.vo;

public class SweetUserVo {
	//发送短信的方式，区分注册还是修改密码等
	private String sendMsgType;
	//原来的头像
	private String oldAvatarUrl;
	private String id;
	private String token;
	private String nickName;
	private String passwd;
	private String phoneNum;
    private String openId;
	private String loginTime;
	private String loginName;
	private String avatarUrl;
    private String sex;
    private String useAble;
	// 手机验证码
	private String phoneCode;
	//新密码
	private String newPasswd;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNewPasswd() {
		return newPasswd;
	}

	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}

	public String getUseAble() {
		return useAble;
	}

	public void setUseAble(String useAble) {
		this.useAble = useAble;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOldAvatarUrl() {
		return oldAvatarUrl;
	}

	public void setOldAvatarUrl(String oldAvatarUrl) {
		this.oldAvatarUrl = oldAvatarUrl;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSendMsgType() {
		return sendMsgType;
	}

	public void setSendMsgType(String sendMsgType) {
		this.sendMsgType = sendMsgType;
	}

}