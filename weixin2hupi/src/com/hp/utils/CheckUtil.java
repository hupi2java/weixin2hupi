package com.hp.utils;

import java.util.Arrays;

public class CheckUtil {

	public static final String token = "hupi";
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		//排序
		String[] arr = new String[]{token,timestamp,nonce};
 		Arrays.sort(arr);
 		
 		//拼接字符串
 		StringBuffer cont = new StringBuffer();
 		for(String s : arr){
 			cont.append(s);
 		}
 		
 		//sha1加密
 		String temp = HashKit.sha1(cont.toString());
 		
		return signature.equalsIgnoreCase(temp);
	}
}
