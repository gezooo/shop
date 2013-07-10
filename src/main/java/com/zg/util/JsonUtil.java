package com.zg.util;

import java.util.List;

import com.zg.beans.JsonJavaTransformerFactory;

public class JsonUtil {
	
	
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
