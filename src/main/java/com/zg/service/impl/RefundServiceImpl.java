package com.zg.service.impl;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.zg.common.util.SerialNumberUtils;
import com.zg.dao.RefundDao;
import com.zg.entity.Refund;
import com.zg.service.RefundService;

/*
* @author gez
* @version 0.1
*/

@Service
public class RefundServiceImpl extends BaseServiceImpl<Refund, String> implements RefundService {
	
	@Resource
	private RefundDao refundDao;

	@Resource
	public void setBaseDao(RefundDao refundDao) {
		super.setBaseDao(refundDao);
	}
	
	public String getLastRefundSn() {
		return refundDao.getLastRefundSn();
	}
	
	public Refund getRefundByRefundSn(String refundSn) {
		return refundDao.getRefundByRefundSn(refundSn);
	}

	// 重写对象，保存时自动设置退款编号
	@Override
	public String save(Refund refund) {
		refund.setRefundSn(SerialNumberUtils.buildRefundSn());
		return super.save(refund);
	}

}