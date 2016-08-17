package support.base.pojo.vo;



public class ProductVo {
	private String id;
	private String topicId;
	//商品类型 0首页商品 1为专题商品
    private String type;
	private String categoryId;
	private String goingTo;
	private String linkUrl;
	private String taobaoId;
	private String title;
	private String productDesc;
	private String priceNow;
	private String priceOld;
	private String collectActually;
	private String collectManual;
	private String shelvesTime;
	private String sortValue;
	private String status;
	private String remarks;
	
	private PageQuery pageQuery;// 分页参数
	private String begin;
	private String end;
	private String scheduleTask;
	private String oldImg;//修改时删除图片用


	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getGoingTo() {
		return goingTo;
	}

	public void setGoingTo(String goingTo) {
		this.goingTo = goingTo;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getTaobaoId() {
		return taobaoId;
	}

	public void setTaobaoId(String taobaoId) {
		this.taobaoId = taobaoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getPriceNow() {
		return priceNow;
	}

	public void setPriceNow(String priceNow) {
		this.priceNow = priceNow;
	}

	public String getPriceOld() {
		return priceOld;
	}

	public void setPriceOld(String priceOld) {
		this.priceOld = priceOld;
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

	public String getShelvesTime() {
		return shelvesTime;
	}

	public void setShelvesTime(String shelvesTime) {
		this.shelvesTime = shelvesTime;
	}

	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOldImg() {
		return oldImg;
	}

	public void setOldImg(String oldImg) {
		this.oldImg = oldImg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}