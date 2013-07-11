package com.zg.service;

import com.zg.entity.Payment;

/*
* @author gez
* @version 0.1
*/

public interface PaymentService extends BaseService<Payment, String> {

	public String getLastPaymentSn();

	public Payment getPaymentByPaymentSn(String paymentSn);

}
