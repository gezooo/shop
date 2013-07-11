package com.zg.common;

import java.io.InputStream;

/*
* @author gez
* @version 0.1
*/

public class ClassLoadUtil {
	
	public static InputStream getResourceAsStream(String path){
    	InputStream is = ClassLoadUtil.class.getClassLoader().getResourceAsStream(path);
    	return is;
	}

}
