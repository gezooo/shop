package com.zg.dao;

import com.zg.entity.Shipping;

/*
* @author gez
* @version 0.1
*/

public interface ShippingDao extends BaseDao<Shipping, String> {

	public String getLastShippingSn();

}
