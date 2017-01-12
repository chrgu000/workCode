package com.whty.dmp.utils.cache;

import com.whty.dmp.core.spring.SpringContextHolder;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * EhCacheUtils工具类
 * 方便手动管理缓存
 * @author cjp
 * @date 2016年9月21日
 */
public class EhCacheUtils {
	
	/**
	 * 初始化时加载的是ehcache-local.xml配置的缓存项目，可以配置超时
	 */
	private static  CacheManager cacheManager = SpringContextHolder.getBean("ehCacheManagerFactoryBean");
	

	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName,String key){
		Element element = getCache(cacheName).get(key);
		return element == null ? null:element.getObjectValue();
	}
	
	/**
	 * 写入缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName,String key,Object value){
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}
	
	/**
	 * 移出缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void remove(String cacheName,String key){
		getCache(cacheName).remove(key);
	}
	
	
	
	/**
	 * 根据cacheName获取缓存，否就直接创建默认永不超时的缓存
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName){
		Cache cache = cacheManager.getCache(cacheName);
		if(cache == null){
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			//默认false，true为设置缓存永恒不超时
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public static void setCacheManager(CacheManager cacheManager) {
		EhCacheUtils.cacheManager = cacheManager;
	}
}
