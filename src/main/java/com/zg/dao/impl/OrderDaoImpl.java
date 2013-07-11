package com.zg.dao.impl;

import java.util.List;

import com.zg.beans.Pager;
import com.zg.dao.OrderDao;
import com.zg.entity.Member;
import com.zg.entity.Order;
import com.zg.entity.Order.OrderStatus;
import com.zg.entity.Order.PaymentStatus;
import com.zg.entity.Order.ShippingStatus;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order, String> implements OrderDao {
	
	@SuppressWarnings("unchecked")
	public String getLastOrderSn() {
		String hql = "from Order as order order by order.createDate desc";
		List<Order> orderList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (orderList != null && orderList.size() > 0) {
			return orderList.get(0).getOrderSn();
		} else {
			return null;
		}
	}
	
	public Pager getOrderPager(Member member, Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		if (pager.getPageSize() == null) {
			pager.setPageSize(Order.DEFAULT_ORDER_LIST_PAGE_SIZE);
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Order.class);
		detachedCriteria.add(Restrictions.eq("member", member));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Long getUnprocessedOrderCount() {
		String hql = "select count(*) from Order as order where order.orderStatus = ?";
		return (Long) getSession().createQuery(hql).setParameter(0, OrderStatus.UNPROCESSED).uniqueResult();
	}
	
	public Long getPaidUnshippedOrderCount() {
		String hql = "select count(*) from Order as order where order.paymentStatus = ? and order.shippingStatus = ? and order.orderStatus != ? and order.orderStatus != ?";
		return (Long) getSession().createQuery(hql).setParameter(0, PaymentStatus.PAID).setParameter(1, ShippingStatus.UNSHIPPED).setParameter(2, OrderStatus.COMPLETED).setParameter(3, OrderStatus.INVALID).uniqueResult();
	}
	
}