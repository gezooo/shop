package com.zg.util;

import org.slf4j.LoggerFactory;


public class LoggerUtil {
	
	public static void info(Class<?> clazz, String message) {
	    LoggerFactory.getLogger(clazz).info(message);

	}
	
	public static void debug(Class<?> clazz, String message) {
	    LoggerFactory.getLogger(clazz).debug(message);

	}
	
	public static void error(Class<?> clazz, String message) {
	    LoggerFactory.getLogger(clazz).error(message);

	}
	
	public static void warn(Class<?> clazz, String message) {
	    LoggerFactory.getLogger(clazz).warn(message);

	}

}
