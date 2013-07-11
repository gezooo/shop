package com.zg.dao.impl;

import java.util.List;

import com.zg.dao.LogConfigDao;
import com.zg.entity.LogConfig;

import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class LogConfigDaoImpl extends BaseDaoImpl<LogConfig, String> implements LogConfigDao {

	@SuppressWarnings("unchecked")
	public List<LogConfig> getLogConfigList(String actionClassName) {
		String hql = "from LogConfig as logConfig where logConfig.actionClassName = ?";
		return getSession().createQuery(hql).setParameter(0, actionClassName).list();
	}

}
