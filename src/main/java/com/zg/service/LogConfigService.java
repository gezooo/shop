package com.zg.service;

import java.util.List;

import com.zg.entity.LogConfig;

/*
* @author gez
* @version 0.1
*/

public interface LogConfigService extends BaseService<LogConfig, String> {
	
	public List<LogConfig> getLogConfigList(String actionClassName);


}
