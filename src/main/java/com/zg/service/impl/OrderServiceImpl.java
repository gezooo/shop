package com.zg.service.impl;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.zg.beans.Pager;
import com.zg.common.util.SerialNumberUtils;
import com.zg.dao.OrderDao;
import com.zg.entity.Member;
import com.zg.entity.Order;
import com.zg.service.OrderService;

/**
 * Service实现类 - 订单
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXDD7076EB801A97081A271D1D0D7AF8F7
 * ============================================================================
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