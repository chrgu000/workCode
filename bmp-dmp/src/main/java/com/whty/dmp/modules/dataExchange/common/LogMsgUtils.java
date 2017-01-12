package com.whty.dmp.modules.dataExchange.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class LogMsgUtils {

	/**
	 * 截取发布数据出错时返回的错误信息中的message
	 * @param errorMsg
	 * @return
	 */
	public static String SubErrorMsg(String errorMsg){
		if(StringUtils.isBlank(errorMsg)){
			return null;
		}
		String str = null;
		//Matcher m = Pattern.compile("<message>(.*?)</message>").matcher(errorMsg);
		Matcher m = Pattern.compile("<desc>.*?}+?.*?}(.*?)</desc>").matcher(errorMsg);
		while(m.find()){
			str = m.group(1);
		}
		return str;
	}
	
	public static void main(String[] args){
		String errorMsg = "<desc>{aa:{sef:sdf,sefs:sefse,s}dfd:sfsd}测试测试</desc>";
		System.out.println(LogMsgUtils.SubErrorMsg(errorMsg));
		//System.out.println("返回"+SubErrorMsg(null));
	}
}
