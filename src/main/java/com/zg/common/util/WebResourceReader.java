package com.zg.common.util;

import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

/*
* @author gez
* @version 0.1
*/

@Component
public class WebResourceReader {

	@Resource
	private ServletContext servletContext;

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public InputStream getResourceAsStream(String path){

		return servletContext.getResourceAsStream(path);
	} 
	
}
