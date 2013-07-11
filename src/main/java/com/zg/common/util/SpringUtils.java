package com.zg.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

/*
* @author gez
* @version 0.1
*/

@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据Bean名称获取实例
	 * 
	 * @param name
	 *            Bean注册名称
	 * 
	 * @return bean实例
	 * 
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}
	
	// 刷新SpringSecurity权限信息
	public static void flushSpringSecurity() {
		try {
			FactoryBean factoryBean = (FactoryBean)SpringUtils.getBean("&adminSecurityDefinitionSource");
			FilterInvocationSecurityMetadataSource filterInvocationDefinitionSource = (FilterInvocationSecurityMetadataSource) factoryBean.getObject();
		    FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor) SpringUtils.getBean("filterSecurityInterceptor");
		    filterSecurityInterceptor.setSecurityMetadataSource(filterInvocationDefinitionSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}