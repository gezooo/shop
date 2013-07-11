package com.zg.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.zg.dao.OrderItemDao;
import com.zg.entity.OrderItem;
import com.zg.service.OrderItemService;

/*
* @author gez
* @version 0.1
*/

@Service
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, String> implements OrderItemService {

	@Resource
	public void setBaseDao(OrderItemDao orderItemDao) {
		super.setBaseDao(orderItemDao);
	}

}