package com.zg.beans;

/*
* @author gez
* @version 0.1
*/

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
