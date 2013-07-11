package com.zg.dao;

import com.zg.entity.Refund;

/*
* @author gez
* @version 0.1
*/

public interface RefundDao extends BaseDao<Refund, String> {

	public String getLastRefundSn();

	public Refund getRefundByRefundSn(String refundSn);

}
