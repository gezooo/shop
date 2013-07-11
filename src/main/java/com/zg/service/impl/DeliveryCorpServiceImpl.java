package com.zg.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.zg.dao.DeliveryCorpDao;
import com.zg.entity.DeliveryCorp;
import com.zg.service.DeliveryCorpService;

/*
* @author gez
* @version 0.1
*/

@Service
public class DeliveryCorpServiceImpl extends BaseServiceImpl<DeliveryCorp, String> implements DeliveryCorpService {

	@Resource
	public void setBaseDao(DeliveryCorpDao deliveryCorpDao) {
		super.setBaseDao(deliveryCorpDao);
	}

}
