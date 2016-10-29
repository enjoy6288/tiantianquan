package support.base.pojo.vo;

import java.util.Date;

import support.base.util.Constant;
import support.base.util.SpringPropertyUtil;



public class FrontQueryVo {
	//手机公共参数
	private PhoneParamVo phoneVo;
	
	private String topicId;
	//查询单品类型 
	private String queryProductType;
	//商品类型(主题商品还是纯单品)
	private String type;
	//展示位置:  轮播banner 推荐专题 专题列表
	private String dispalyPosition;
	//上架开始时间
	private Date beginTime;
	//上架结束时间
	private Date endTime;
	//起始页
	private int startPage;
	//页数
	private int pageSize=Integer.parseInt(SpringPropertyUtil.getContextProperty(Constant.PAGE_SIZE));
	
	
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	
	public String getQueryProductType() {
		return queryProductType;
	}
	public void setQueryProductType(String queryProductType) {
		this.queryProductType = queryProductType;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getDispalyPosition() {
		return dispalyPosition;
	}
	public void setDispalyPosition(String dispalyPosition) {
		this.dispalyPosition = dispalyPosition;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public PhoneParamVo getPhoneVo() {
		return phoneVo;
	}
	public void setPhoneVo(PhoneParamVo phoneVo) {
		this.phoneVo = phoneVo;
	}
	
}