package com.zg.dao;

import com.zg.entity.Payment;

/*
* @author gez
* @version 0.1
*/

public interface PaymentDao extends BaseDao<Payment, String> {

	public String getLastPaymentSn();

	public Payment getPaymentByPaymentSn(String paymentSn);

}
