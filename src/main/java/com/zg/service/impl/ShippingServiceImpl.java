package com.zg.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zg.common.util.SerialNumberUtils;
import com.zg.dao.ShippingDao;
import com.zg.entity.Shipping;
import com.zg.service.ShippingService;

/*
* @author gez
* @version 0.1
*/

@Service
public class ShippingServiceImpl extends BaseServiceImpl<Shipping, String> implements ShippingService {
	
	@Resource
	private ShippingDao shippingDao;

	@Resource
	public void setBaseDao(ShippingDao shippingDao) {
		super.setBaseDao(shippingDao);
	}
	
	public String getLastShippingSn() {
		return shippingDao.getLastShippingSn();
	}

	// 重写对象，保存时自动设置发货编号
	@Override
	public String save(Shipping shipping) {
		shipping.setShippingSn(SerialNumberUtils.buildShippingSn());
		return super.save(shipping);
	}

}