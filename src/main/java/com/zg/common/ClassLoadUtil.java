package com.zg.common;

import java.io.InputStream;

public class ClassLoadUtil {
	
	public static InputStream getResourceAsStream(String path){
    	InputStream is = ClassLoadUtil.class.getClassLoader().getResourceAsStream(path);
    	return is;
	}

}
