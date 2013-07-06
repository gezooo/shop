package com.zg.beans;

import java.util.List;

public interface JsonJavaTransformer {
	
	public <T> List<T> json2JavaList(String json, Class<T> clazz);
	public <T> String javaList2json(List<T> list);

}
