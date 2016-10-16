package support.base.pojo.po;

import java.util.Date;

import support.base.util.Constant;
import support.base.util.SpringPropertyUtil;

public class Product {
	private String id;
	private String topicId;
	private Long categoryId;
	// 商品类型 0首页商品 1为专题商品
	private Byte type;
	// 附加字段映射分类名称
	private String categoryName;
	private String goingTo;
	private String linkUrl;
	private String taobaoId;
	private String title;
	private String productDesc;
	private String img;
	private String priceNow;
	private String priceOld;
	private Long collectActually;
	private Long collectManual;
	private Date shelvesTime;
	private Integer sortValue;
	private Integer status;
	private String remarks;
	private Long pv;
    private Long uv;
	private Date createTime;
	private Date updateTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl == null ? null : linkUrl.trim();
	}

	public String getTaobaoId() {
		return taobaoId;
	}

	public void setTaobaoId(String taobaoId) {
		this.taobaoId = taobaoId == null ? null : taobaoId.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img == null ? null : img.trim();
	}

	public String getPriceNow() {
		return priceNow;
	}

	public void setPriceNow(String priceNow) {
		this.priceNow = priceNow == null ? null : priceNow.trim();
	}

	public String getPriceOld() {
		return priceOld;
	}

	public void setPriceOld(String priceOld) {
		this.priceOld = priceOld == null ? null : priceOld.trim();
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

	public Date getShelvesTime() {
		return shelvesTime;
	}

	public void setShelvesTime(Date shelvesTime) {
		this.shelvesTime = shelvesTime;
	}

	public Integer getSortValue() {
		return sortValue;
	}

	public void setSortValue(Integer sortValue) {
		this.sortValue = sortValue;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Long getPv() {
		return pv;
	}

	public void setPv(Long pv) {
		this.pv = pv;
	}

	public Long getUv() {
		return uv;
	}

	public void setUv(Long uv) {
		this.uv = uv;
	}

	public String getGoingTo() {
		return goingTo;
	}

	public void setGoingTo(String goingTo) {
		this.goingTo = goingTo;
	}
}