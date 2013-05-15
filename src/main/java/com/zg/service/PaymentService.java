package com.zg.service;

import com.zg.entity.Payment;

public interface PaymentService extends BaseService<Payment, String> {

	public String getLastPaymentSn();

	public Payment getPaymentByPaymentSn(String paymentSn);

}
