package com.zg.dao.impl;

import java.util.List;

import com.zg.dao.ShippingDao;
import com.zg.entity.Shipping;

import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class ShippingDaoImpl extends BaseDaoImpl<Shipping, String> implements ShippingDao {
	
	@SuppressWarnings("unchecked")
	public String getLastShippingSn() {
		String hql = "from Shipping as shipping order by shipping.createDate desc";
		List<Shipping> shippingList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (shippingList != null && shippingList.size() > 0) {
			return shippingList.get(0).getShippingSn();
		} else {
			return null;
		}
	}

}