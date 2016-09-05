package support.base.pojo.po;

import java.util.Date;

import support.base.util.Constant;
import support.base.util.SpringPropertyUtil;

public class Topic {
	private String id;

	private Byte goingTo;
	
	private String linkUrl;

	private String dispalyPosition;

	private String topicName;

	private String topicViceName;

	private String topicDesc;

	private String bannerOutterimg;

	private String bannerInnerimg;

	private Date shelvesTime;

	private Long collectActually;

	private Long collectManual;

	private Long scanActually;

	private Long scanManual;

	private Integer sortValue;

	private Long categoryId;
	// 附加字段分类名称
	private String categoryName;

	private String remarks;

	private Integer status;

	private Date updateTime;

	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Byte getGoingTo() {
		return goingTo;
	}

	public void setGoingTo(Byte goingTo) {
		this.goingTo = goingTo;
	}

	public String getDispalyPosition() {
		return dispalyPosition;
	}

	public void setDispalyPosition(String dispalyPosition) {
		this.dispalyPosition = dispalyPosition;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName == null ? null : topicName.trim();
	}

	public String getTopicViceName() {
		return topicViceName;
	}

	public void setTopicViceName(String topicViceName) {
		this.topicViceName = topicViceName == null ? null : topicViceName.trim();
	}

	public String getTopicDesc() {
		return topicDesc;
	}

	public void setTopicDesc(String topicDesc) {
		this.topicDesc = topicDesc == null ? null : topicDesc.trim();
	}

	public String getBannerOutterimg() {
		return  bannerOutterimg;
	}

	public void setBannerOutterimg(String bannerOutterimg) {
		this.bannerOutterimg = bannerOutterimg == null ? null : bannerOutterimg.trim();
	}

	public String getBannerInnerimg() {
		return  bannerInnerimg;
	}

	public void setBannerInnerimg(String bannerInnerimg) {
		this.bannerInnerimg = bannerInnerimg == null ? null : bannerInnerimg.trim();
	}

	public Date getShelvesTime() {
		return shelvesTime;
	}

	public void setShelvesTime(Date shelvesTime) {
		this.shelvesTime = shelvesTime;
	}

	public Long getCollectActually() {
		return collectActually;
	}

	public void setCollectActually(Long collectActually) {
		this.collectActually = collectActually;
	}

	public Long getCollectManual() {
		return collectManual;
	}

	public void setCollectManual(Long collectManual) {
		this.collectManual = collectManual;
	}

	public Long getScanActually() {
		return scanActually;
	}

	public void setScanActually(Long scanActually) {
		this.scanActually = scanActually;
	}

	public Long getScanManual() {
		return scanManual;
	}

	public void setScanManual(Long scanManual) {
		this.scanManual = scanManual;
	}

	public Integer getSortValue() {
		return sortValue;
	}

	public void setSortValue(Integer sortValue) {
		this.sortValue = sortValue;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
}