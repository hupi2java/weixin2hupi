package com.hp.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.hp.domain.AccessToken;
import com.hp.domain.trans.TransResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WeiXinUtils {

	private static final String APPID = "wxfdf9f9259719ad4c";//微信ID
	private static final String APPSECRET = "cc30d6758f33ee0def60d926a7941339";//微信key
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	private static final String APIKEY = "a8f18688d0dc0d7cec3283cab9d0a676";//百度 APIStore key
	/**
	 * 获取JSON格式的token信息
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject doGet(String url) throws ClientProtocolException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		JSONObject jsonObjet = null;
		if(entity != null){
			String result = EntityUtils.toString(entity, "utf-8");
			jsonObjet = JSONObject.fromObject(result);
		}
		return jsonObjet;
	}
	
	/**
	 *查询ID的doget方法， 必须 填入apikey到HTTP header
	 */
	public static JSONObject doGet(String url,String apikey) throws ClientProtocolException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("apikey", apikey);
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		JSONObject jsonObjet = null;
		if(entity != null){
			String result = EntityUtils.toString(entity, "utf-8");
			jsonObjet = JSONObject.fromObject(result);
		}
		return jsonObjet;
	}
	
	/**
	 * 获取token
	 * @return
	 */
	public static AccessToken getAccessToken(){
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		AccessToken token = new AccessToken();
		try {
			JSONObject jsonObject = doGet(url);
			System.out.println(jsonObject);
			if(jsonObject != null){
				token.setAccess_token(jsonObject.getString("access_token"));
				token.setExpires_in(jsonObject.getString("expires_in"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return token;
	}
	
	private static final Random random = new Random();
	/**
	 * 百度翻译 返回原文和译文
	 * @param source
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String translate(String source) throws ClientProtocolException, IOException{
		String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?q=SOURCE&from=aoto&to=auto&appid=APPID&salt=SALT&sign=SIGN";
		int salt = random.nextInt(10000);
		String appid = "20160309000015114";//百度翻译id
		String key = "TyzhiaVEV1B99GF70Plf";//百度翻译key
		String sign = DigestUtils.md5Hex(appid+source+salt+key);
		String newUrl = url.replace("SOURCE", URLEncoder.encode(source, "UTF-8")).replace("APPID", appid).replace("SALT", String.valueOf(salt)).replace("SIGN", sign);
	
		JSONObject jsonObject = doGet(newUrl);
		JSONArray arr = jsonObject.getJSONArray("trans_result");
		JSONObject transResult = arr.getJSONObject(0);
		
		String s = "原文:"+transResult.getString("src")+"\n"+"译文:"+transResult.getString("dst");
		return s;
	}
	
	/**
	 * 身份证查询
	 * @param id
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String queryID(String id) throws ClientProtocolException, IOException{
		String url = "http://apis.baidu.com/apistore/idservice/id?id=ID";
		String newUrl = url.replace("ID", id);
		
		JSONObject jsonObject = doGet(newUrl,APIKEY);
		if("身份证号码不合法!".equals(jsonObject.getString("retMsg"))){
			return "身份证号不存在";
		}

		JSONObject info = jsonObject.getJSONObject("retData");

		StringBuffer result= new StringBuffer();
		if(!info.isNullObject()){
			String sex = null;
			if(info.getString("sex").equals("M")){
				sex = "男";
			}else if(info.getString("sex").equals("F")){
				sex = "女";
			}else {
				sex = "未知";
			}
			String birthday = info.getString("birthday");
			String address = info.getString("address");
			result.append("性别："+sex+"\n");
			result.append("生日："+birthday+"\n");
			result.append("地址："+address+"\n");
		}
		return result.toString();
	}
	
	/**
	 * 电话归属地查询
	 * @param phone
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String queryMobilenumber(String phone) throws ClientProtocolException, IOException{
		String url = "http://apis.baidu.com/apistore/mobilenumber/mobilenumber?phone=PHONE";
		String newUrl = url.replace("PHONE", phone);
		
		JSONObject jsonObject = doGet(newUrl, APIKEY);
		if("-1".equals(jsonObject.getString("errNum"))){
			return "电话号码不存在";
		}
		JSONObject info = jsonObject.getJSONObject("retData");
		
		StringBuffer result = new StringBuffer();
		if(!info.isNullObject()){
			String province = info.getString("province");
			String city = info.getString("city");
			String supplier = info.getString("supplier");
			String suit = info.getString("suit");
			result.append("号码："+phone+"\n");
			result.append("城市："+province+" "+city+"\n");
			result.append("运营商："+supplier+"\n");
		}
		return result.toString();
	}
	
	/**
	 * 天气查询
	 * @param city
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject queryWeather(String city) throws ClientProtocolException, IOException{
		String url = "http://apis.baidu.com/heweather/weather/free?city=CITY";
		String newUrl = url.replace("CITY", city);
		JSONObject jsonObject = doGet(newUrl, APIKEY);
		return jsonObject;
	}
}
