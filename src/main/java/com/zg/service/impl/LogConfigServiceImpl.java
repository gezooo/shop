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


/**
 * Service实现类 - 日志设置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX9A2C2331D9A269C32708F5802158E24D
 * ============================================================================
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