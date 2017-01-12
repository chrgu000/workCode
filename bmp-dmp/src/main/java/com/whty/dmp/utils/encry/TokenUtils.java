package com.whty.dmp.utils.encry;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.whty.dmp.modules.admin.utils.ConfigDictUtils;
import com.whty.dmp.modules.dataExchange.common.DataConstants;
import com.whty.dmp.utils.HttpUtils;
import com.whty.dmp.utils.JsonUtils;

/**
 * 网关，开放平台获取token工具类
 * @author cjp
 * @date 2016年9月12日
 */
public class TokenUtils {
	
	private static Logger logger = LoggerFactory.getLogger(TokenUtils.class);
	
	private static final String appId = ConfigDictUtils.getDictValue(DataConstants.opengate_appid,DataConstants.opengate);
	
	private static final String appKey = ConfigDictUtils.getDictValue(DataConstants.opengate_appkey,DataConstants.opengate);
	
	private static final String httpUrl = ConfigDictUtils.getDictValue(DataConstants.url_token,DataConstants.url);
	
	private static Map<String,Object> tokenMap = Maps.newHashMap();
	/**
	 * 根据appid和appkey获取访问参数
	 * @param appid
	 * @param appkey
	 * @return
	 */
	public static Map<String,Object> generateTokenParams(String appid,String appkey){
		long timestamp = System.currentTimeMillis();
		String keyInfo = appid + appkey + String.valueOf(timestamp);//三者相加
		byte[] hmacSHA = EncryptionUtils.getHmacSHA1(keyInfo, appkey);
		keyInfo = EncryptionUtils.bytesToHexString(hmacSHA).toUpperCase();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("appid", appid);
		resultMap.put("timestamp", timestamp);
		resultMap.put("keyinfo", keyInfo);
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	public static String getAccessToken(){
		String json = JsonUtils.ojbTojson(generateTokenParams(appId, appKey));
		String accessToken = "";
		//由于token存在两小时缓存，所以2小时候再进行获取
		if(tokenMap.containsKey("validTime")){
			long validTime = Long.valueOf(tokenMap.get("validTime").toString());
			if(validTime > System.currentTimeMillis()){
				return (String) tokenMap.get("accessToken");
			}else{
				logger.info("token时间超时，正在重新获取token");
			}
		}
		try {
			String result = HttpUtils.getInstance().httpPost(httpUrl, json);
			Map<String,Object> resultMap = (Map<String, Object>) JsonUtils.jsonToObj(result, Map.class);
			if(resultMap != null && "000000".equals(resultMap.get("retCode"))){
				tokenMap = (Map<String, Object>) resultMap.get("tokenInfo");
				accessToken = (String) tokenMap.get("accessToken");
				logger.info("远程获取token成功--->"+accessToken);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取token失败，请查看开放平台的相关配置！");
			return null;
		}
		return accessToken;
	}
	
	
}
