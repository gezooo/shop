package com.zg.util;

import java.util.List;

import com.zg.beans.JsonJavaTransformerFactory;

public class JsonJavaTransformerUtil {
	
	
	public static <T> List<T> json2JavaList(String jsons, Class<T> clazz){
		
		return JsonJavaTransformerFactory.getJsonJavaTransformer().json2JavaList(jsons, clazz);
	}

}
