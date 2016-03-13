package com.hp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import com.hp.utils.CheckUtil;
import com.hp.utils.MessageUtil;
import com.hp.utils.WeiXinUtils;

public class WeiXinServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//设置编码方式
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter out = resp.getWriter();
		
		try {
			Map<String,String> map = MessageUtil.xmlToMap(req);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			String msgType = map.get("MsgType");//信息类型
			
			
			String message = null;
			
			if("text".equals(msgType)){
				String content = map.get("Content");//文本内容
				if("?".equals(content) || "？".equals(content) || "帮助".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.helpMenu());
				}else if("1".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.translateMenu());
				}else if("2".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.queryIdMenu());
				}else if("3".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.queryPhoneMenu());
				}else if("4".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.queryWeatherMenu());
				}else if("10".equals(content)){
					message = MessageUtil.initNews(toUserName, fromUserName);
				}else if(content.startsWith("翻译")){
					String word = content.replaceAll("^翻译", "").trim();
					if(word != "")
						message = MessageUtil.initText(toUserName, fromUserName, WeiXinUtils.translate(word));
				}else if(content.startsWith("身份证查询")){
					String id = content.replaceAll("^身份证查询", "").trim();
					if(id != "")
						message = MessageUtil.initText(toUserName, fromUserName, WeiXinUtils.queryID(id));
				}else if(content.startsWith("电话归属地查询")){
					String phone = content.replaceAll("^电话归属地查询", "").trim();
					if(phone != "")
						message = MessageUtil.initText(toUserName, fromUserName, WeiXinUtils.queryMobilenumber(phone));
				}else if(content.startsWith("天气查询")){
					String city = content.replaceAll("^天气查询", "").trim();
					if(city != "")
						message = MessageUtil.initWeatherNews(toUserName, fromUserName, WeiXinUtils.queryWeatherByFutrue(city), city);
				}else{
						message = MessageUtil.initText(toUserName, fromUserName, "你说的我不懂呀！请发送 ？ 或 帮助 ！");
				}
			}else if("event".equals(msgType)){
				String eventType = map.get("Event");//事件
				if("subscribe".equals(eventType)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.subscribeMenu());
				}
			}
			out.print(message);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}

}
