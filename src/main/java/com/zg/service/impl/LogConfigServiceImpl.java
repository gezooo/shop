package com.zg.service.impl;

import java.util.List;

import javax.annotation.Resource;



import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.dao.LogConfigDao;
import com.zg.entity.LogConfig;
import com.zg.service.LogConfigService;


/*
* @author gez
* @version 0.1
*/

@Service
public class LogConfigServiceImpl extends BaseServiceImpl<LogConfig, String> implements LogConfigService {
	
	@Resource
	private LogConfigDao logConfigDao;

	@Resource
	public void setBaseDao(LogConfigDao logConfigDao) {
		super.setBaseDao(logConfigDao);
	}
	
	public List<LogConfig> getLogConfigList(String actionClassName) {
		return logConfigDao.getLogConfigList(actionClassName);
	}
	
	@Override
	@Cacheable(value = "caching", key="'LogConfigServiceImp.getAll'")
	public List<LogConfig> getAll() {
		List<LogConfig> allLogConfig = logConfigDao.getAll();
		if (allLogConfig != null) {
			for (LogConfig logConfig : allLogConfig) {
				Hibernate.initialize(logConfig);
			}
		}
		return allLogConfig;
	}

	@Override
	@CacheEvict(value = "caching", key="'LogConfigServiceImp.getAll'")
	public void delete(LogConfig entity) {
		logConfigDao.delete(entity);
	}

	@Override
	@CacheEvict(value = "caching", key="'LogConfigServiceImp.getAll'")
	public void delete(String id) {
		logConfigDao.delete(id);
	}

	@Override
	@CacheEvict(value = "caching", key="'LogConfigServiceImp.getAll'")
	public void delete(String[] ids) {
		logConfigDao.delete(ids);
	}

	@Override
	@CacheEvict(value = "caching", key="'LogConfigServiceImp.getAll'")
	public String save(LogConfig entity) {
		return logConfigDao.save(entity);
	}

	@Override
	@CacheEvict(value = "caching", key="'LogConfigServiceImp.getAll'")
	public void update(LogConfig entity) {
		logConfigDao.update(entity);
	}

}