package com.zg.common.struts2.typeconverter;


import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zg.common.util.CommonUtils;
import com.zg.common.util.DateUtils;

/*
* @author gez
* @version 0.1
*/

public class DateConverter extends StrutsTypeConverter{
	
	 
	 private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if(logger.isDebugEnabled()){
			logger.debug(CommonUtils.displayMessage(" called", null));
		}
		Date date = null;
		String dateString = null;
		if(values != null & values.length > 0){
			dateString = values[0];
			date = DateUtils.parserDateString(dateString);
			if(logger.isDebugEnabled()){
				logger.debug("dateString : " + dateString);

			}
			
		}
		return date;
	}

	@Override
	public String convertToString(Map context, Object o) {
		if(logger.isDebugEnabled()){
			logger.debug(CommonUtils.displayMessage(" called", null));
		}
		Date date = (Date) o;  
        String dateTimeString = DateUtils.formatDate(date);  
        return dateTimeString;
	}

}
