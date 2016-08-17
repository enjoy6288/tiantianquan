package support.base.pojo.po;

public class SweetCollect {
    private String id;

    private String userId;

    private String collectProduct;

    private String collectTopic;
    private String collectType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCollectProduct() {
        return collectProduct;
    }

    public void setCollectProduct(String collectProduct) {
        this.collectProduct = collectProduct == null ? null : collectProduct.trim();
    }

    public String getCollectTopic() {
        return collectTopic;
    }

    public void setCollectTopic(String collectTopic) {
        this.collectTopic = collectTopic == null ? null : collectTopic.trim();
    }

	public String getCollectType() {
		return collectType;
	}

	public void setCollectType(String collectType) {
		this.collectType = collectType;
	}
}