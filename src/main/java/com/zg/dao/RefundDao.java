package com.zg.dao;

import com.zg.entity.Refund;

public interface RefundDao extends BaseDao<Refund, String> {

	public String getLastRefundSn();

	public Refund getRefundByRefundSn(String refundSn);

}
