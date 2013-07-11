package com.zg.dao.impl;

import java.util.List;

import com.zg.dao.RefundDao;
import com.zg.entity.Refund;

import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class RefundDaoImpl extends BaseDaoImpl<Refund, String> implements RefundDao {
	
	@SuppressWarnings("unchecked")
	public String getLastRefundSn() {
		String hql = "from Refund as refund order by refund.createDate desc";
		List<Refund> refundList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (refundList != null && refundList.size() > 0) {
			return refundList.get(0).getRefundSn();
		} else {
			return null;
		}
	}
	
	public Refund getRefundByRefundSn(String refundSn) {
		String hql = "from Refund as refund where refund.refundSn = ?";
		return (Refund) getSession().createQuery(hql).setParameter(0, refundSn).uniqueResult();
	}

}