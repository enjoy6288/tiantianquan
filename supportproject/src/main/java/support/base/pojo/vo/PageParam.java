package support.base.pojo.vo;

public class PageParam {
	//当前页
	public int startPage=0;
	//个数
	public int pageSize=10;
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
