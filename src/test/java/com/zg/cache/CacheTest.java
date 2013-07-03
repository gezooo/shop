package com.zg.cache;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-cache.xml"})
public class CacheTest {
	
	@Autowired
	private CacheMethod m;
	
	
	
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



	public CacheMethod getM() {
		return m;
	}



	public void setM(CacheMethod m) {
		this.m = m;
	}

}
