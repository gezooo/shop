package com.zg.common;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import sun.misc.BASE64Decoder;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Initializing implements InitializingBean {
	
	private String keyFile = "a2V5ZmlsZ";
	
	@Resource
	private ServletContext servletContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (servletContext != null) {
			BASE64Decoder bASE64Decoder = new BASE64Decoder();
			//keyfile -->  a2V5ZmlsZQ==
			keyFile = new String(bASE64Decoder.decodeBuffer(keyFile + "Q=="));
			System.out.println("keyFile: " + keyFile);
			Method readKey = Class.forName("com.zg.common.Key").getMethod("readKeyFile", String.class);
			String content = (String) readKey.invoke(null, keyFile);
			//WkdTSE9QX0tFWQ== --->   ZGSHOP_KEY
			servletContext.setAttribute(new String(bASE64Decoder.decodeBuffer("WkdTSE9QX0tFWQ==")), content);
		}		
	}
	
	public String getKeyFile() {
		return keyFile;
	}

	public void setKeyFile(String keyFile) {
		this.keyFile = keyFile;
	}

}
