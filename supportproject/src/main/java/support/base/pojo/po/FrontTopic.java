package support.base.pojo.po;

import java.io.Serializable;
import java.util.Date;

import support.base.util.Constant;
import support.base.util.SpringPropertyUtil;

public class FrontTopic implements Serializable{
	private String id;
	private String linkUrl;
	private String scoId;
	private String topicName;
	private String topicViceName;
	private String topicDesc;
	private String bannerOutterimg;
	private String bannerInnerimg;
	private Date shelvesTime;
	private long collect;
	private long scan;
	private boolean collected;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTopicViceName() {
		return topicViceName;
	}

	public void setTopicViceName(String topicViceName) {
		this.topicViceName = topicViceName;
	}

	public String getTopicDesc() {
		return topicDesc;
	}

	public void setTopicDesc(String topicDesc) {
		this.topicDesc = topicDesc;
	}

	public String getBannerOutterimg() {
		return bannerOutterimg;
	}

	public void setBannerOutterimg(String bannerOutterimg) {
		this.bannerOutterimg = bannerOutterimg;
	}

	public String getBannerInnerimg() {
		return bannerInnerimg;
	}

	public void setBannerInnerimg(String bannerInnerimg) {
		this.bannerInnerimg = bannerInnerimg;
	}


	public long getCollect() {
		return collect;
	}

	public void setCollect(long collect) {
		this.collect = collect;
	}

	public long getScan() {
		return scan;
	}

	public void setScan(long scan) {
		this.scan = scan;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getScoId() {
		return scoId;
	}

	public void setScoId(String scoId) {
		this.scoId = scoId;
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	public Date getShelvesTime() {
		return shelvesTime;
	}

	public void setShelvesTime(Date shelvesTime) {
		this.shelvesTime = shelvesTime;
	} 
}