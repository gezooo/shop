package com.zg.common.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* @author gez
* @version 0.1
*/

public class DateUtils {
	
	 public static final String DATE_TIME_FOMART_IE = "yyyy-MM-dd HH:mm:ss";  
	  
	 public static final String DATE_TIME_FOMART_FF = "yy/MM/dd hh:mm:ss"; 
	 
	 public static final String DEFAULT_DATE_FORMART = "yyyy-MM-dd";
	 public static final String DEFAULT_DATE_TIME_FORMART = "yyyy-MM-dd HH:mm:ss";
	 
	 private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	 
	 
	 public static String formatDate(Date date){
			return formatDate(date, DEFAULT_DATE_FORMART);
		}
		
	public static Date parserDateString(String dateStr){
		return parserDateString(dateStr, DEFAULT_DATE_FORMART);

	}
	
	public static String formatDateTime(Date date){
		return formatDate(date, DEFAULT_DATE_TIME_FORMART);
	}
	
	public static Date parserDateTimeString(String dateTimeStr){
		return parserDateString(dateTimeStr, DEFAULT_DATE_TIME_FORMART);
	}
	 
	 public static String formatDate(Date date, String formatStr){
			Assert.assertNotNull("date can not be null", date);
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			return format.format(date);
		}
		
	public static Date parserDateString(String dateStr, String formatStr){
		Assert.assertNotNull("dateTimeStr can not be null", dateStr);
		Assert.assertNotNull("formatStr can not be null", dateStr);
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			logger.error(CommonUtils.displayMessage(" parserDateString '"+ dateStr+ "' failed", null));
		}
		return null;
	}
	
	
	
	
	
	


}
