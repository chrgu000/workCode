package com.whty.dmp.utils;


import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json工具类
 * 
 * @author chenjp
 * @date 2015年6月19日
 */
public class JsonUtils {
	
	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	private static ObjectMapper mapper;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static synchronized ObjectMapper getMapperInstance() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.setDateFormat(dateFormat);
		}
		return mapper;
	}

	/**
	 * 将json字符串转换为java对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object jsonToObj(String json, Class<?> clazz) {
		Object obj = null;
		try {
			ObjectMapper objectMapper = getMapperInstance();
			obj = objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String ojbTojson(Object obj) {
		String json = "";
		try {
			ObjectMapper objectMapper = getMapperInstance();
			json = objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 判断String是否是json格式
	 * @param str
	 * @return
	 */
	public static boolean isJson(String str){
		boolean result = false;
		if(StringUtils.isBlank(str)){
			return result;
		}
		try {
			ObjectMapper objectMapper = getMapperInstance();
			JsonNode jsonNode = objectMapper.readTree(str);
			if(jsonNode != null){
				result = true;
			}
		} catch (IOException e) {
//			e.printStackTrace();
			logger.error(">>>>>>>>> String value: "+str+" can not parse to json");
		}
		return result;
	}
	
	
	public static void main(String[] args) {
//		String setr = "[{\"safddddddddd\":\"gfgg\"}]";
		String setr = "{}";
		
		System.out.println(isJson(setr));
//		jsonToObj(setr, Map.class);
	}
	

}
