package support.base.pojo.vo;


public class SweetStatisticsVo {
    private Integer type;
    private PageQuery pageQuery;// 分页参数
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public PageQuery getPageQuery() {
		return pageQuery;
	}
	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}
}