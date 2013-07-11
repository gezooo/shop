package com.zg.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.zg.dao.LogDao;
import com.zg.entity.Log;
import com.zg.service.LogService;

/*
* @author gez
* @version 0.1
*/

@Service
public class LogServiceImpl extends BaseServiceImpl<Log, String> implements LogService {

	@Resource
	public void setBaseDao(LogDao logDao) {
		super.setBaseDao(logDao);
	}

}
