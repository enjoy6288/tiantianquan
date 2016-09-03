package support.base.pojo.vo;

import java.util.List;

public class SweetCollectVo {
    private String id;
    private String userId;
    private String collectProduct;
    private String collectTopic;
    private String collectType;
    //删除时传递的id集合
    private String collectIds;
    //起始页
  	private int startPage;
  	//页数
  	private int pageSize=10;
    
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((collectProduct == null) ? 0 : collectProduct.hashCode());
		result = prime * result
				+ ((collectTopic == null) ? 0 : collectTopic.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SweetCollectVo other = (SweetCollectVo) obj;
		if (collectProduct == null) {
			if (other.collectProduct != null)
				return false;
		} else if (!collectProduct.equals(other.collectProduct))
			return false;
		if (collectTopic == null) {
			if (other.collectTopic != null)
				return false;
		} else if (!collectTopic.equals(other.collectTopic))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

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

	public String getCollectIds() {
		return collectIds;
	}

	public void setCollectIds(String collectIds) {
		this.collectIds = collectIds;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}