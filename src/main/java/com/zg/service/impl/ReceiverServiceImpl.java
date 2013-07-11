package com.zg.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.zg.dao.ReceiverDao;
import com.zg.entity.Receiver;
import com.zg.service.ReceiverService;

/*
* @author gez
* @version 0.1
*/

@Service
public class ReceiverServiceImpl extends BaseServiceImpl<Receiver, String> implements ReceiverService {

	@Resource
	public void setBaseDao(ReceiverDao receiverDao) {
		super.setBaseDao(receiverDao);
	}

}