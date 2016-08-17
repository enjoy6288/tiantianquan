package support.base.process.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据查询列表结果
 * @author Thinkpad
 *
 */
public class FrontDataInfo {
	//结果集
	private Map data=new HashMap<String,List<Object>>();
	private int code=0;
	private String info="操作成功";
	public Map getData() {
		return data;
	}
	public void setData(Map data) {
		this.data = data;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
