package com.wh.tms.result;

import java.io.Serializable;

/**
 * 
* @ClassName: BaseResult 
* @Description: 公共数据返回模型 
* @author Huqk
* @date 2018年2月26日 下午10:28:16
 */
public class Result implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -1914186711967999374L;
	
	private int code;//0:成功，-1失败
	private String msg;
	private long count;
	private Object data;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
