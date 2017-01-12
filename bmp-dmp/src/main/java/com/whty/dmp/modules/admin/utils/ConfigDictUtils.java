package com.whty.dmp.modules.admin.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whty.dmp.core.spring.SpringContextHolder;
import com.whty.dmp.modules.admin.dao.ConfigDictDao;
import com.whty.dmp.modules.admin.entity.ConfigDictVo;
import com.whty.dmp.utils.cache.EhCacheConstants;
import com.whty.dmp.utils.cache.EhCacheUtils;

/**
 * 配置字典工具
 * @author cjp
 * @date 2016年9月21日
 */
public class ConfigDictUtils {
	
	private static ConfigDictDao configDictDao = SpringContextHolder.getBean("configDictDao");
	/**
	 * 缓存key
	 */
	private static final String CACHE_KEY = "dictMapKey";
	
	/**
	 * 清理缓存
	 * @author cjp 
	 */
	public static void remove(){
		EhCacheUtils.remove(EhCacheConstants.SYS_CACHE, CACHE_KEY);
	}
	
	/**
	 * 根据字典配置的type获取,优先全部查缓存
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<ConfigDictVo> getConfigDictList(String type){
		Map<String,List<ConfigDictVo>> dictMap = (Map<String, List<ConfigDictVo>>) EhCacheUtils.get(EhCacheConstants.SYS_CACHE, CACHE_KEY);
		if(dictMap == null){
			dictMap = Maps.newHashMap();
			List<ConfigDictVo> allList = configDictDao.selectList(new ConfigDictVo());
			if(allList != null && allList.size() > 0){
				for(ConfigDictVo dictVo:allList){
					List<ConfigDictVo> tempList = dictMap.get(dictVo.getType());
					if(tempList != null){
						tempList.add(dictVo);
					}else {
						dictMap.put(dictVo.getType(), Lists.newArrayList(dictVo));
					}
				}
			}
			EhCacheUtils.put(EhCacheConstants.SYS_CACHE, CACHE_KEY, dictMap);
		}
		List<ConfigDictVo> list = dictMap.get(type);
		if(list == null){
			list = configDictDao.selectList(new ConfigDictVo(type));
			if(list == null){
				list = Lists.newArrayList();
			}
		}
		return list;
	}
	
	/**
	 * 根据type和值获取数据项名称
	 * @param value
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public static String getDictName(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			List<ConfigDictVo> list = getConfigDictList(type);
			if(list != null && list.size() > 0){
				for (ConfigDictVo dictVo : list){
					if (type.equals(dictVo.getType()) && value.equals(dictVo.getdValue())){
						return dictVo.getName();
					}
				}
			}
		}
		return defaultValue;
	}
	
	/**
	 * 获取数据字典值
	 * @param key
	 * @param type
	 * @return
	 */
	public static String getDictValue(String key,String type){
		String value = "";
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(type)){
			List<ConfigDictVo> list = getConfigDictList(type);
			if(list != null && list.size() > 0){
				for (ConfigDictVo dictVo : list){
					if (type.equals(dictVo.getType()) && key.equals(dictVo.getdKey())){
						return dictVo.getdValue();
					}
				}
			}
		}
		return value;
	}
	
}	
