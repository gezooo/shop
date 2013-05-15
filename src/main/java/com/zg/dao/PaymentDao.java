package com.zg.dao;

import com.zg.entity.Payment;

public interface PaymentDao extends BaseDao<Payment, String> {

	public String getLastPaymentSn();

	public Payment getPaymentByPaymentSn(String paymentSn);

}
