package support.base.pojo.po;



public class FrontProduct {
	//收藏表的ID
	private String scoId;
    private String id;
    private String categoryName;
    private String title;
    private String productDesc;
    private String img;
    private String priceNow;
    private String linkUrl;
    private long collect;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPriceNow() {
		return priceNow;
	}
	public void setPriceNow(String priceNow) {
		this.priceNow = priceNow;
	}
	public long getCollect() {
		return collect;
	}
	public void setCollect(long collect) {
		this.collect = collect;
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
    
}