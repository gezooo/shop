package com.zg.dao;

import java.util.List;

import com.zg.entity.PaymentConfig;

/*
* @author gez
* @version 0.1
*/

public interface PaymentConfigDao extends BaseDao<PaymentConfig, String> {

	public List<PaymentConfig> getNonDepositPaymentConfigList();

	public List<PaymentConfig> getNonDepositOfflinePaymentConfigList();

}
