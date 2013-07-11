package com.zg.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.zg.dao.DeliveryItemDao;
import com.zg.entity.DeliveryItem;
import com.zg.service.DeliveryItemService;

/*
* @author gez
* @version 0.1
*/

@Service
public class DeliveryItemServiceImpl extends BaseServiceImpl<DeliveryItem, String> implements DeliveryItemService {

	@Resource
	public void setBaseDao(DeliveryItemDao deliveryItemDao) {
		super.setBaseDao(deliveryItemDao);
	}

}