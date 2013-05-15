package com.zg.service;

import java.util.List;

import com.zg.entity.LogConfig;

public interface LogConfigService extends BaseService<LogConfig, String> {
	
	public List<LogConfig> getLogConfigList(String actionClassName);


}
