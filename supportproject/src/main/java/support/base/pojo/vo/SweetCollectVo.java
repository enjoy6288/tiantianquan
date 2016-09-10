package support.base.pojo.vo;

import java.util.Date;

public class SweetCollectVo extends PageParam {
	//删除时候传递的收藏ID集合
	private String scoIds;
	
	private String scoId;

    private String scoUserId;

    private String scoCollectId;

    private Integer scoCollectType;

    private Date scoCollectTime;

    public String getScoId() {
        return scoId;
    }

    public void setScoId(String scoId) {
        this.scoId = scoId == null ? null : scoId.trim();
    }

    public String getScoUserId() {
        return scoUserId;
    }

    public void setScoUserId(String scoUserId) {
        this.scoUserId = scoUserId == null ? null : scoUserId.trim();
    }

    public String getScoCollectId() {
        return scoCollectId;
    }

    public void setScoCollectId(String scoCollectId) {
        this.scoCollectId = scoCollectId == null ? null : scoCollectId.trim();
    }

    public Integer getScoCollectType() {
        return scoCollectType;
    }

    public void setScoCollectType(Integer scoCollectType) {
        this.scoCollectType = scoCollectType;
    }

    public Date getScoCollectTime() {
        return scoCollectTime;
    }

    public void setScoCollectTime(Date scoCollectTime) {
        this.scoCollectTime = scoCollectTime;
    }

	public String getScoIds() {
		return scoIds;
	}

	public void setScoIds(String scoIds) {
		this.scoIds = scoIds;
	}
}