package com.zg.test.web;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ServletContextInitializingBean implements InitializingBean{

	@Resource
	private ServletContext servletContext;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		servletContext.setAttribute("S" + "H" + "O" + "P" + "X" + "X" + "_" + "K" + "E" + "Y", "s" + "h" + "o" + "p" + "x" + "x");
	}

}
