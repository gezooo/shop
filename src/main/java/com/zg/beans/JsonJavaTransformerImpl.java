package com.zg.beans;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class JsonJavaTransformerImpl implements JsonJavaTransformer {

	@Override
	public <T> List<T> json2JavaList(String jsons, Class<T> clazz) {
        JSONArray jsonArray=(JSONArray)JSONSerializer.toJSON(jsons);
        JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(clazz);
		return (List<T>) JSONSerializer.toJava(jsonArray, jsonConfig);
	}

	@Override
	public <T> String javaList2json(List<T> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}

}
