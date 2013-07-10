package com.zg.util;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;

/**
 * 工具类 - 缓存
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX36E03F145A16F1CAF1C6DBCE956C081E
 * ============================================================================
 */

public class EncacheCacheConfigUtil {
	
	public static final String GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME = "cacheManager";// GeneralCacheAdministrator注入Bean名称
	
	/**
	 * 根据Key读取缓存
	 * 
	 * @return 缓存对象
	 */
	public static Object getFromCache(String key) {
		EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringUtil.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
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
		EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringUtil.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		Cache cache = cacheManager.getCache("caching");
		cache.put(key, object);
	}
	
	/**
	 * 根据Key刷新缓存对象
	 * 
	 */
	public static void flushEntry(String key) {
		EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringUtil.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		Cache cache = cacheManager.getCache("caching");
		cache.evict(key);
	}
	
	public static void flushAll(){
		EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringUtil.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		Collection<String>  cacheNames = cacheManager.getCacheNames();
		for(String cacheName : cacheNames){
			Cache cache = cacheManager.getCache(cacheName);
			cache.clear();
		}
	}

}