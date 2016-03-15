package com.hp.domain.trans;

import java.util.List;

public class Today extends BaseInfo {


	private String curTemp;//当前温度
	private String aqi;//pm值

	private List<Index> index;//各项指数

	public String getCurTemp() {
		return curTemp;
	}
	public void setCurTemp(String curTemp) {
		this.curTemp = curTemp;
	}
	public String getAqi() {
		return aqi;
	}
	public void setAqi(String aqi) {
		this.aqi = aqi;
	}
	
	public List<Index> getIndex() {
		return index;
	}
	public void setIndex(List<Index> index) {
		this.index = index;
	}
}
