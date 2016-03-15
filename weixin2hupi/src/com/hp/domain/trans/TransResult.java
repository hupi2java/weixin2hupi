package com.hp.domain.trans;

public class TransResult {
	private String errNum; //信息码
	private String errMsg; //信息
	private RetData retData;//天气内容
	public String getErrNum() {
		return errNum;
	}
	public void setErrNum(String errNum) {
		this.errNum = errNum;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public RetData getRetData() {
		return retData;
	}
	public void setRetData(RetData retData) {
		this.retData = retData;
	}

}
