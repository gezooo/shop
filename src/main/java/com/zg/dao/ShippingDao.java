package com.zg.dao;

import com.zg.entity.Shipping;

public interface ShippingDao extends BaseDao<Shipping, String> {

	public String getLastShippingSn();

}
