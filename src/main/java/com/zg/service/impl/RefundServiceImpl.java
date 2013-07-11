package com.zg.service.impl;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.zg.common.util.SerialNumberUtils;
import com.zg.dao.RefundDao;
import com.zg.entity.Refund;
import com.zg.service.RefundService;

/**
 * Service实现类 - 退款
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX33368CD07557B60629179EF8EDB49808
 * ============================================================================
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