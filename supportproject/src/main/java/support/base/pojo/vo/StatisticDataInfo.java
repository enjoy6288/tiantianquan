package support.base.pojo.vo;

public class StatisticDataInfo {
	public String date;
	//专题总浏览量
	public long PV;
	//专题独立访客
	public long UV;
	//专题商品总点击率
	public long CTR;
	public long getPV() {
		return PV;
	}
	public void setPV(long pV) {
		PV = pV;
	}
	public long getUV() {
		return UV;
	}
	public void setUV(long uV) {
		UV = uV;
	}
	public long getCTR() {
		return CTR;
	}
	public void setCTR(long cTR) {
		CTR = cTR;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
