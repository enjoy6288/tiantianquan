package support.base.pojo.po;

import java.util.Date;

public class SweetStatistics {
    private String id;

    private String uv;

    private String pv;

    private Integer type;
    
    private String desc;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv == null ? null : uv.trim();
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv == null ? null : pv.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}