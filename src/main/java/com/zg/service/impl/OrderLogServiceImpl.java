package com.zg.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.zg.dao.OrderLogDao;
import com.zg.entity.OrderLog;
import com.zg.service.OrderLogService;

/*
* @author gez
* @version 0.1
*/

@Service
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLog, String> implements OrderLogService {

	@Resource
	public void setBaseDao(OrderLogDao orderLogDao) {
		super.setBaseDao(orderLogDao);
	}

}