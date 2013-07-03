package com.zg.cache;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CacheMethod {
	
	@Cacheable(value = "caching")
	public String getValue() {
		System.out.println("getValue called");
		return "abc";
	}
	
	@CacheEvict(value = "caching", allEntries=true)
	public void deleteValue() {
		System.out.println("deleteValue called");
	}
	
	@Cacheable(value = "caching", key="'key1'")
	public String getValue1() {
		System.out.println("getValue1 called");
		return "value1";
	}
	
	@Cacheable(value = "caching", key="'key2'")
	public String getValue2() {
		System.out.println("getValue2 called");
		return "value2";
	}
	
	@CacheEvict(value = "caching", key="'key1'")
	public void deleteValues() {
		System.out.println("deleteValues called");
	}

}
