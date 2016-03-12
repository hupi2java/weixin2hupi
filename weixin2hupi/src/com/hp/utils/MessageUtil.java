package com.hp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hp.domain.News;
import com.hp.domain.NewsMessage;
import com.hp.domain.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

	/**
	 * 将接受的xml格式信息转换成map
	 * @param req
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		InputStream is = req.getInputStream();
		Document doc = reader.read(is);
		//获取根元素
		Element root = doc.getRootElement();
		//获取所有元素
		List<Element> list = root.elements();
		for(Element e : list){
			map.put(e.getName(), e.getText());
		}
		//关闭输入流
		is.close();
		return map;
	}
	
	/**
	 * 将 TextMessage 对象  转为xml格式
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 将newsMessage 对象 转为xml格式
	 * @param newsMesssage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMesssage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMesssage.getClass());
		xstream.alias("item", News.class);
		return xstream.toXML(newsMesssage);
	}
	
	/**
	 * 组装文本消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setMsgType("text");
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setContent(content);
		return textMessageToXml(textMessage);
	}
	
	/**
	 * 组装图文消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNews(String toUserName,String fromUserName){
		NewsMessage newsMessage = new NewsMessage();
		List<News> list = new ArrayList<News>();
		News new1 = new News();
		new1.setDescription("曾经多美，如今多毁");
		new1.setPicUrl("http://1f45g94395.iask.in/weixin2hupi/images/DSC07049.JPG");
		new1.setTitle("胡辟的大学时光");
		new1.setUrl("http://www.hao123.com");
		News new2 = new News();
		new2.setTitle("几十再青春");
		new2.setDescription("何时能重逢");
		new2.setPicUrl("http://1f45g94395.iask.in/weixin2hupi/images/20120427082.jpg");
		new2.setUrl("http://www.hao123.com");
		list.add(new1);
		list.add(new2);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType("news");
		newsMessage.setArticleCount(list.size());
		newsMessage.setArticles(list);
		return newsMessageToXml(newsMessage);
	}
	
	/**
	 * 关注后的响应菜单
	 * @return
	 */
	public static String subscribeMenu(){
		StringBuffer str = new StringBuffer();
		str.append("谢谢您的关注!\n");
		str.append("您可以使用以下功能:\n");
		str.append("1.翻译\n");
		str.append("2.身份证查询\n");
		str.append("3.电话归属地查询\n");
		str.append("4.天气查询\n");
		return str.toString();
	}
	
	/**
	 * 帮助菜单
	 * @return
	 */
	public static String helpMenu(){
		StringBuffer str = new StringBuffer();
		str.append("你可以使用以下功能:\n");
		str.append("1.翻译\n");
		str.append("2.身份证查询\n");
		str.append("3.电话归属地查询\n");
		str.append("4.天气查询\n");
		return str.toString();
	}
	
	/**
	 * 翻译菜单
	 * @return
	 */
	public static String translateMenu(){
		StringBuffer str = new StringBuffer();
		str.append("你可以使用翻译功能:\n");
		str.append("发送格式：翻译+内容\n");
		str.append("如：翻译足球\n");
		str.append("返回主菜单请发送：？或 帮助\n");
		return str.toString();
	}
	
	/**
	 * 身份证查询菜单
	 * @return
	 */
	public static String queryIdMenu(){
		StringBuffer str = new StringBuffer();
		str.append("你可以使用身份证查询功能:\n");
		str.append("发送格式：身份证查询+身份证号\n");
		str.append("如：身份证查询429006199009031111\n");
		str.append("返回主菜单请发送：？或 帮助\n");
		return str.toString();
	}
	
	/**
	 * 电话归属地查询
	 * @return
	 */
	public static String queryPhoneMenu(){
		StringBuffer str = new StringBuffer();
		str.append("你可以使用电话归属地查询功能:\n");
		str.append("发送格式：电话归属地查询+内容\n");
		str.append("如：电话归属地查询18721521111\n");
		str.append("返回主菜单请发送：？或 帮助\n");
		return str.toString();
	}
	
	/**
	 * 天气查询帮助
	 * @return
	 */
	public static String queryWeatherMenu(){
		StringBuffer str = new StringBuffer();
		str.append("你可以使用天气功能:\n");
		str.append("发送格式：天气查询+内容\n");
		str.append("如：天气查询上海\n");
		str.append("返回主菜单请发送：？或 帮助\n");
		return str.toString();
	}
	
	/**
	 * 天气信息组装
	 * @param toUserName
	 * @param fromUserName
	 * @param jsonObject
	 * @return
	 */
	public static String initWeatherNews(String toUserName,String fromUserName,JSONObject jsonObject){
		
		String status = jsonObject.getJSONArray("HeWeather data service 3.0").getJSONObject(0).getString("status");
		if("unknown city".equals(status)){
			return initText(toUserName, fromUserName,"城市名错误 ，请检查城市名，并重新发送");
		}

		NewsMessage newsMessage = new NewsMessage();
		List<News> list = new ArrayList<News>();
		News new1 = new News();
		News new2 = new News();
				
		String today = initDailyForecast(3,jsonObject);
		String tomorrow = initDailyForecast(4, jsonObject);
		
		new1.setTitle(today);
		new1.setDescription("今日天气");
		
		new2.setTitle(tomorrow);
		new2.setDescription("明日天气");
		
		list.add(new1);
		list.add(new2);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setArticleCount(list.size());
		newsMessage.setArticles(list);
		newsMessage.setMsgType("news");
		return newsMessageToXml(newsMessage);
		
	}

	public static String initDailyForecast(int targetDate,JSONObject jsonObject){
		JSONObject now = jsonObject.getJSONArray("HeWeather data service 3.0").getJSONObject(0).getJSONObject("now");
		JSONArray daily_forecast = jsonObject.getJSONArray("HeWeather data service 3.0").getJSONObject(0).getJSONArray("daily_forecast");
		
		String nowTmp = now.getString("tmp");//体感温度
		String txt = now.getJSONObject("cond").getString("txt");//天气情况
		JSONObject today = daily_forecast.getJSONObject(targetDate);
		String date = today.getString("date");//日期
		String month = date.split("-")[1];
		String day = date.split("-")[2];
		JSONObject tmp = today.getJSONObject("tmp");//气温
		String maxTmp = tmp.getString("max"); 
		String minTmp = tmp.getString("min");
		JSONObject wind = today.getJSONObject("wind");
		String dir = wind.getString("dir");//风向
		String sc = wind.getString("sc");//风力
		if(targetDate == 3){
			return "今日 "+month+"月"+day+"日"+"(实时："+nowTmp+"℃) "+txt+" "+sc+" "+minTmp+"~"+maxTmp+"℃";
		}else{
			return month+"月"+day+"日 "+txt+" "+sc+" "+minTmp+"~"+maxTmp+"℃";
		}
		
	}
}
