package com.zg.common.util;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;

/*
* @author gez
* @version 0.1
*/

public class EncacheCacheConfigUtils {
	
	public static final String GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME = "cacheManager";// GeneralCacheAdministrator注入Bean名称
	
	/**
	 * 根据Key读取缓存
	 * 
	 * @return 缓存对象
	 */
	public static Object getFromCache(String key) {
		EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringUtils.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		Cache cache = cacheManager.getCache("caching");
		ValueWrapper  value = cache.get(key);
		if(value == null) {
			return null;
		}
		return value.get();
	}
	
	/**
	 * 加入对象到缓存
	 * 
	 */
	public static void putInCache(String key, Object object) {
		EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringUtils.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		Cache cache = cacheManager.getCache("caching");
		cache.put(key, object);
	}
	
	/**
	 * 根据Key刷新缓存对象
	 * 
	 */
	public static void flushEntry(String key) {
		EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringUtils.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		Cache cache = cacheManager.getCache("caching");
		cache.evict(key);
	}
	
	public static void flushAll(){
		EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringUtils.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		Collection<String>  cacheNames = cacheManager.getCacheNames();
		for(String cacheName : cacheNames){
			Cache cache = cacheManager.getCache(cacheName);
			cache.clear();
		}
	}

}