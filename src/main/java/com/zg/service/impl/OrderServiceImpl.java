package com.zg.service.impl;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.zg.beans.Pager;
import com.zg.common.util.SerialNumberUtils;
import com.zg.dao.OrderDao;
import com.zg.entity.Member;
import com.zg.entity.Order;
import com.zg.service.OrderService;

/*
* @author gez
* @version 0.1
*/

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {
	
	@Resource
	private OrderDao orderDao;

	@Resource
	public void setBaseDao(OrderDao orderDao) {
		super.setBaseDao(orderDao);
	}
	
	public String getLastOrderSn() {
		return orderDao.getLastOrderSn();
	}
	
	public Pager<Order> getOrderPager(Member member, Pager<Order> pager) {
		return orderDao.getOrderPager(member, pager);
	}
	
	public Long getUnprocessedOrderCount() {
		return orderDao.getUnprocessedOrderCount();
	}
	
	public Long getPaidUnshippedOrderCount() {
		return orderDao.getPaidUnshippedOrderCount();
	}

	// 重写对象，保存时自动设置订单编号
	@Override
	public String save(Order order) {
		order.setOrderSn(SerialNumberUtils.buildOrderSn());
		return super.save(order);
	}

}