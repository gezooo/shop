package com.zg.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationManager {
	
	public static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);
	//public final String _BASE_PATH = File.separator + "opt" + File.separator + "shop" + File.separator;
	
	//for windows
	public static final String _BASE_PATH = "C:\\Users\\zhangge\\shop\\";
	
	public static final String _TEMPLATE_PATH = _BASE_PATH;
	
	public static final String CONF_FILE = "shop.properties";
	
	public static boolean refresh = false;
	
	private static Map<String, String> cache = new HashMap<String, String>();
	
	
	public static InputStream getTempalteFilePath(){
		return ClassLoadUtil.getResourceAsStream(CONF_FILE);
	}
	
	public static String getConfigProperties(String key){
		if(refresh || !cache.containsKey(key)){
			cache = reload();	
		}
		return cache.get(key);
	}
	
	public static Map<String, String> reload(){
		InputStream in = ClassLoadUtil.getResourceAsStream(CONF_FILE);;
		return PropertiesStoreFactory.getStore().load(in);
	}
	
	public static class PropertiesStoreFactory {
		
		public static Store getStore(){
			return StoreHolder.instance;
		}
		
		public static class StoreHolder{
			public static Store instance = new PropertiesStore();
		}
		
	}
	
	public static interface Store{
		public Map<String, String> load(InputStream in);
	}
	
	public static class PropertiesStore implements Store {
		
		public Map<String, String>  load(InputStream in){
			Map<String, String> map = new HashMap<String, String>();
			Properties prop = new Properties();
			try {
				prop.load(in);
				Iterator<Entry<Object, Object>> it = prop.entrySet().iterator();
				while(it.hasNext()){
					Entry<Object, Object> entry = it.next();
					map.put((String)entry.getKey(), (String)entry.getValue());
				}
			} catch (IOException e) {
				logger.error("load properties failed: " + e.getMessage());
			}
			
			return map;
		}
	}

}
