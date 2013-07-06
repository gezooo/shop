package com.zg.beans;

public class JsonJavaTransformerFactory {
	

	/**
	 * hardcode to return JsonJavaTransformerImpl, we will change it to make it configurable.
	 * @return
	 */
	public static JsonJavaTransformer getJsonJavaTransformer(){
		return Holder.instance;
	}
	
	 private static class Holder {  
	      private static final JsonJavaTransformer instance = new JsonJavaTransformerImpl();  
	  } 

}
