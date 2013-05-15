package com.zg.service;

import com.zg.entity.Refund;

public interface RefundService extends BaseService<Refund, String> {

	public String getLastRefundSn();

	public Refund getRefundByRefundSn(String refundSn);

}
