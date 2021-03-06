package com.zg.common.util;

import java.util.List;

import com.zg.beans.JsonJavaTransformerFactory;

/*
* @author gez
* @version 0.1
*/

public class JsonUtils {
	
	
	public static <T> List<T> json2JavaList(String jsons, Class<T> clazz){
		
		return JsonJavaTransformerFactory.getJsonJavaTransformer().json2JavaList(jsons, clazz);
	}
	
	public static <T> String javaList2Json(List<T> list){
		
		return JsonJavaTransformerFactory.getJsonJavaTransformer().javaList2json(list);
	}
	
	public static <T> List<T> json2Java(String jsons, Class<T> clazz){
		
		return JsonJavaTransformerFactory.getJsonJavaTransformer().json2JavaList(jsons, clazz);
	}
	
	public static <T> String java2Json(T obj){
		
		return JsonJavaTransformerFactory.getJsonJavaTransformer().java2json(obj);
	}

}
