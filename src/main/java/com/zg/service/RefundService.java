package com.zg.service;

import com.zg.entity.Refund;

/*
* @author gez
* @version 0.1
*/

public interface RefundService extends BaseService<Refund, String> {

	public String getLastRefundSn();

	public Refund getRefundByRefundSn(String refundSn);

}
