package com.whty.dmp.modules.dataExchange.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whty.dmp.modules.admin.utils.ConfigDictUtils;
import com.whty.dmp.utils.HttpUtils;
import com.whty.dmp.utils.JsonUtils;
import com.whty.dmp.utils.encry.TokenUtils;

/**
 * 数据交换转换工具
 * @author cjp
 * @date 2016年9月12日
 */
public class DataMsgUtils {
	
	private static Logger log = LoggerFactory.getLogger(DataMsgUtils.class);
	
	public static final String url_publish = ConfigDictUtils.getDictValue(DataConstants.url_publish, DataConstants.url);
	
	public static final String url_subscribe = ConfigDictUtils.getDictValue(DataConstants.url_subscribe, DataConstants.url);
	/**
	 * 从HttpRequest中获取数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static DataMsgVo getDataFromRequest(HttpServletRequest request,Class<?> clazz){
		 	InputStream inputStream = null;
		 	DataMsgVo msgVo = null;
		 	String requestStr = "";
			try {
				inputStream = request.getInputStream();
				requestStr = IOUtils.toString(inputStream, "utf-8");
				if(StringUtils.isNotBlank(requestStr)){
					msgVo = (DataMsgVo) JsonUtils.jsonToObj(requestStr, DataMsgVo.class);
					if(msgVo != null){
						Object obj = clazz.newInstance();
						BeanUtils.populate(obj, (Map<String, ? extends Object>) msgVo.getObj());
						msgVo.setObj(obj);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error("解析HttpServletRequest的数据失败---->"+requestStr);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return msgVo;
	}
	
	/**
	 * 批量发布
	 * @param list
	 * @param operatorType
	 * @param serviceCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String publishToData(List<?> list,int operatorType,String serviceCode){
		String result = "";
		List<DataMsgVo> messageList = Lists.newArrayList();
		if(null == list  || list.size() == 0){
			return "数据为空！";
		}
		for(Object obj : list){
			DataMsgVo dataMsgVo = new DataMsgVo(obj,operatorType,DataConstants.platCode);
			messageList.add(dataMsgVo);
		}
		Map<String,String> resultMap = Maps.newHashMap();
		resultMap.put("messageList",JsonUtils.ojbTojson(messageList) );
		resultMap.put("serviceCode",serviceCode);
		try {
			result = HttpUtils.getInstance().httpPost(url_publish, resultMap);
			log.info(serviceCode + "进行数据交换返回结果--->"+result);
		} catch (Exception e) {
			e.printStackTrace();
			return "异常错误！";
		}
		return result;
	}
	
	/**
	 * 单独发布
	 * @param obj
	 * @param operatorType
	 * @param serviceCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String publishToData(Object obj,int operatorType,String serviceCode){
		String result = "";
		List<DataMsgVo> messageList = Lists.newArrayList();
		DataMsgVo dataMsgVo = new DataMsgVo(obj,operatorType,DataConstants.platCode);
		messageList.add(dataMsgVo);
		Map<String,String> resultMap = Maps.newHashMap();
		resultMap.put("messageList",JsonUtils.ojbTojson(messageList) );
		resultMap.put("serviceCode",serviceCode);
		try {
			result = HttpUtils.getInstance().httpPost(url_publish, resultMap);
			log.info(serviceCode + "进行数据交换返回结果--->"+result);
		} catch (Exception e) {
			e.printStackTrace();
			return "异常错误！";
		}
		return result;
	}
	
	/**
	 * 从数据交换订阅数据
	 * @param serviceCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String subscribeData(String serviceCode){
		String result = "";
		Map<String,String> resultMap = Maps.newHashMap();
		resultMap.put("serviceCode",serviceCode);
		resultMap.put("token", TokenUtils.getAccessToken());
		try {
			result = HttpUtils.getInstance().httpPost(url_subscribe, resultMap);
			log.info(serviceCode + "进行数据交换返回结果--->"+result);
		} catch (Exception e) {
			e.printStackTrace();
			return "异常错误！";
		}
		return result;
	}
}
