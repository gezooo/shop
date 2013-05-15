package com.zg.test.entity;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.zg.entity.PaymentConfig;
import com.zg.entity.PaymentConfig.PaymentFeeType;

public class PaymentConfigTest {
	
	PaymentConfig paymentConfig;
	
	@Before
	public void buildPaymentConfig(){
		this.paymentConfig = new PaymentConfig();
		this.paymentConfig.setPaymentFeeType(PaymentFeeType.SCALE);
	} 
	
	@Test
	public void testGetPaymentFee() {
		//BigDecimal paymentFee = this.paymentConfig.getPaymentFee(new BigDecimal(20));
		//System.out.println(paymentFee.toString());
		BigDecimal paymentFee = new BigDecimal("0").divide(new BigDecimal("100"));
		System.out.println("paymentFee: " + paymentFee.toString());
	}

}
