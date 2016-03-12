package com.hp.utils;

public class windUtil {

	public static String getWind(Integer sc){
		if(sc != null){
			switch(sc){
				case 0 : return "无风";
				case 1 : return "软风";
				case 2 : return "轻风";
				case 3 : return "微风";
				case 4 : return "和风";
				case 5 : return "轻劲风";
				case 6 : return "强风";
				case 7 : return "疾风";
				case 8 : return "大风";
				case 9 : return "烈风";
				case 10:return "狂风";
				case 11:return "暴风";
				case 12:return "台风";
				default :return "未知";
			}
		}
		return "未知";
	}
	
}
