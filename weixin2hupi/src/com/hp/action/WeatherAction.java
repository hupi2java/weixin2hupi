package com.hp.action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;

import com.hp.domain.trans.Forecast;
import com.hp.domain.trans.Index;
import com.hp.domain.trans.TransResult;
import com.hp.utils.WeiXinUtils;
import com.opensymphony.xwork2.ActionContext;

public class WeatherAction {
	private String city = null;
	
	public String weatherInfo() throws JSONException, ClientProtocolException, IOException{

		JSONObject jsonObject = WeiXinUtils.queryWeatherByFutrue(city);
		
		TransResult transResult  = (TransResult) JSONObject.toBean(jsonObject, TransResult.class);
		//转化天气预报信息
		JSONArray jsonForecast = jsonObject.getJSONObject("retData").getJSONArray("forecast");
		List<Forecast> forecast = (List<Forecast>) JSONArray.toCollection(jsonForecast, Forecast.class);
		//转化当天的天气信息
		JSONArray jsonIndex = jsonObject.getJSONObject("retData").getJSONObject("today").getJSONArray("index");
		List<Index> index = (List<Index>) JSONArray.toCollection(jsonIndex, Index.class);
		ActionContext.getContext().put("today", transResult.getRetData().getToday());
		ActionContext.getContext().put("weatherInfo", index);
		ActionContext.getContext().put("forecast", forecast);
		return "weatherInfo";
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
