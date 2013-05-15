package com.zg.dao;

import java.util.List;

import com.zg.entity.LogConfig;

public interface LogConfigDao extends BaseDao<LogConfig, String> {

	public List<LogConfig> getLogConfigList(String actionClassName);

}
