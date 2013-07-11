package com.zg.dao.impl;

import java.util.List;

import com.zg.dao.PaymentDao;
import com.zg.entity.Payment;

import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class PaymentDaoImpl extends BaseDaoImpl<Payment, String> implements PaymentDao {
	
	@SuppressWarnings("unchecked")
	public String getLastPaymentSn() {
		String hql = "from Payment as payment order by payment.createDate desc";
		List<Payment> paymentList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (paymentList != null && paymentList.size() > 0) {
			return paymentList.get(0).getPaymentSn();
		} else {
			return null;
		}
	}
	
	public Payment getPaymentByPaymentSn(String paymentSn) {
		String hql = "from Payment as payment where payment.paymentSn = ?";
		return (Payment) getSession().createQuery(hql).setParameter(0, paymentSn).uniqueResult();
	}

}