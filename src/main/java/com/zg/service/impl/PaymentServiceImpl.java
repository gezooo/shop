package com.zg.service.impl;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.zg.common.util.SerialNumberUtils;
import com.zg.dao.PaymentDao;
import com.zg.entity.Payment;
import com.zg.service.PaymentService;

/*
* @author gez
* @version 0.1
*/

@Service
public class PaymentServiceImpl extends BaseServiceImpl<Payment, String> implements PaymentService {
	
	@Resource
	private PaymentDao paymentDao;

	@Resource
	public void setBaseDao(PaymentDao paymentDao) {
		super.setBaseDao(paymentDao);
	}
	
	public String getLastPaymentSn() {
		return paymentDao.getLastPaymentSn();
	}
	
	public Payment getPaymentByPaymentSn(String paymentSn) {
		return paymentDao.getPaymentByPaymentSn(paymentSn);
	}

	// 重写对象，保存时自动设置支付编号
	@Override
	public String save(Payment payment) {
		payment.setPaymentSn(SerialNumberUtils.buildPaymentSn());
		return super.save(payment);
	}

}