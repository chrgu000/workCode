package com.whty.dmp.utils.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置全局变量
 * @author cjp  2016年6月23日
 */
public class SysConfig {
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = new HashMap<String, String>();
	
	private static final String[] filePaths = {"data_exchange.properties"};
	
	private static PropertiesReader propertiesReader = new PropertiesReader(filePaths);
	
	public static String getConfig(String key){
		String value = map.get(key);
		if(value == null){
			value = propertiesReader.getValue(key);
			map.put(key, value != null ? value : "");
		}
		return value;
	}
	
}
