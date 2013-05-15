package com.zg.service;

import com.zg.entity.Shipping;

public interface ShippingService extends BaseService<Shipping, String> {

	public String getLastShippingSn();

}
