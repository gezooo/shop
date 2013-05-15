package com.zg.service;

import com.zg.beans.Pager;
import com.zg.entity.Member;
import com.zg.entity.Order;

public interface OrderService extends BaseService<Order, String> {
	
	public String getLastOrderSn();
	
	public Pager<Order> getOrderPager(Member member, Pager<Order> pager);
	
	public Long getUnprocessedOrderCount();
	
	public Long getPaidUnshippedOrderCount();

}
