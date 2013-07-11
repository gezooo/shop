package com.zg.service;

import com.zg.entity.Shipping;

/*
* @author gez
* @version 0.1
*/

public interface ShippingService extends BaseService<Shipping, String> {

	public String getLastShippingSn();

}
