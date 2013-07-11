package com.zg.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zg.entity.PaymentConfig;

/*
* @author gez
* @version 0.1
*/

public interface PaymentConfigService extends BaseService<PaymentConfig, String> {
	
	public List<PaymentConfig> getNonDepositPaymentConfigList();
	
	public List<PaymentConfig> getNonDepositOfflinePaymentConfigList();

	public String buildParameterString(Map<String, String> parameterMap);

	public String buildTenpayDirectPaymentUrl(PaymentConfig paymentConfig, String paymentSn, BigDecimal totalAmount, String description, String ip);

	public String buildTenpayDirectQueryUrl(PaymentConfig paymentConfig, String transactionId, String paymentSn);

	public String buildTenpayPartnerPaymentUrl(PaymentConfig paymentConfig, String paymentSn, BigDecimal totalAmount, String description);

}
