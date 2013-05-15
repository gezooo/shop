package com.zg.test.spring.security;

import java.util.Iterator;
import java.util.Map;


import org.junit.Test;

import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

//import com.sun.image.codec.jpeg.*;

public class AuthenticationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.getProperties().list(System.out);
		System.out.print(System.getenv());
		
		Map envs = System.getenv();
		Iterator it = envs.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			System.out.println(entry.getKey() + "-->" + entry.getValue());
		}
		
		org.springframework.orm.hibernate4.HibernateTransactionManager m;
		
	}
	
	@Test
	public void test() {
		
	}

}
