package support.base.pojo.vo;


public class TopicVo {
    private String id;

    private String goingTo;
    
    private String linkUrl;

    private String dispalyPosition;

    private String topicName;

    private String topicViceName;

    private String topicDesc;

    private String bannerOutterimg;

    private String bannerInnerimg;

    private String shelvesTime;

    private String collectActually;

    private String collectManual;

    private String scanActually;

    private String scanManual;

    private String sortValue;

    private String categoryId;

    private String remarks;

    private String status;
    
    private PageQuery pageQuery;//分页
    private String scheduleTask;//用于区分定时任务时使用的
    private String begin;
	private String end;
	private String oldOut;//原来的图片
	private String oldInner;
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoingTo() {
		return goingTo;
	}

	public void setGoingTo(String goingTo) {
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

	public String getShelvesTime() {
		return shelvesTime;
	}

	public void setShelvesTime(String shelvesTime) {
		this.shelvesTime = shelvesTime;
	}

	public String getCollectActually() {
		return collectActually;
	}

	public void setCollectActually(String collectActually) {
		this.collectActually = collectActually;
	}

	public String getCollectManual() {
		return collectManual;
	}

	public void setCollectManual(String collectManual) {
		this.collectManual = collectManual;
	}

	public String getScanActually() {
		return scanActually;
	}

	public void setScanActually(String scanActually) {
		this.scanActually = scanActually;
	}

	public String getScanManual() {
		return scanManual;
	}

	public void setScanManual(String scanManual) {
		this.scanManual = scanManual;
	}

	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PageQuery getPageQuery() {
		return pageQuery;
	}

	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}

	public String getScheduleTask() {
		return scheduleTask;
	}

	public void setScheduleTask(String scheduleTask) {
		this.scheduleTask = scheduleTask;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getOldOut() {
		return oldOut;
	}

	public void setOldOut(String oldOut) {
		this.oldOut = oldOut;
	}

	public String getOldInner() {
		return oldInner;
	}

	public void setOldInner(String oldInner) {
		this.oldInner = oldInner;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
}