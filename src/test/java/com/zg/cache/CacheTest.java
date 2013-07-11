package com.zg.cache;


import java.util.List;

import net.sf.ehcache.Ehcache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zg.common.util.EncacheCacheConfigUtils;
import com.zg.common.util.SpringUtils;
import com.zg.entity.Product;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-cache.xml"})
public class CacheTest {
	
	@Autowired
	private CacheMethod m;
	
	@Autowired
	private EhCacheCacheManager cacheManager;
	
	
	
	@Test
	public void testCache(){
		m.getValue();
		m.getValue();
		System.out.println(m.getValue());
		m.deleteValue();
		System.out.println(m.getValue());

		
		
	}
	
	@Test
	public void testEvictTwoKeys(){
		m.getValue1();
		m.getValue2();
		m.getValue1();
		m.getValue2();
		m.deleteValues();
		m.getValue1();
		m.getValue2();
		System.out.println(m.getValue1());
		System.out.println(m.getValue2());

		
		
	}
	
	@Test
	public void testMethod(){
		Product p = new Product();
		p.setId("001");
		m.method(p);
		m.method(p);
		dumpCache();
	}
	
	@Test
	public void testMethod2(){

		m.method("abc");
		m.method("abc");
		dumpCache();
	}



	public CacheMethod getM() {
		return m;
	}



	public void setM(CacheMethod m) {
		this.m = m;
	}
	
	//@Test
	public void dumpCache(){
		
		//EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringUtil.getBean(EncacheCacheConfigUtil.GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		//Cache cache = cacheManager.getCache("caching");
		//Ehcache c = cacheManager.getCacheManager().getEhcache("caching");
		
		Cache cache = cacheManager.getCache("caching");
		
		Ehcache c = (Ehcache)cache.getNativeCache();
		List l = c.getKeys();
		for(Object o : l){
			System.out.println(o.toString() + " : " + c.get(o));
			
		}
		
	}

	public EhCacheCacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(EhCacheCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
