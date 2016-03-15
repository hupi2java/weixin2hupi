package com.hp.domain.trans;

import java.util.List;

public class RetData {

		private String city; //城市名
		private String cityid;//城市代码
		private Today today;//今日天气信息
		private List<Forecast> forecast;//天气预报
		private List<History> history;//历史天气
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCityid() {
			return cityid;
		}
		public void setCityid(String cityid) {
			this.cityid = cityid;
		}
		public Today getToday() {
			return today;
		}
		public void setToday(Today today) {
			this.today = today;
		}
		public List<Forecast> getForecast() {
			return forecast;
		}
		public void setForecast(List<Forecast> forecast) {
			this.forecast = forecast;
		}
		public List<History> getHistory() {
			return history;
		}
		public void setHistory(List<History> history) {
			this.history = history;
		}

}
