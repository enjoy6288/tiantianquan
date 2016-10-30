package support.base.pojo.po;

public class BackgroundUser {
    private String id;

    private String userName;

    private String passwd;
    //验证码
    private String randomcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

	public String getRandomcode() {
		return randomcode;
	}

	public void setRandomcode(String randomcode) {
		this.randomcode = randomcode;
	}
}