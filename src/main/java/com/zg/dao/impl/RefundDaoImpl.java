package com.zg.dao.impl;

import java.util.List;

import com.zg.dao.RefundDao;
import com.zg.entity.Refund;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 退款
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX87BA9D2CA8BCC7CF406CEAC791678E86
 * ============================================================================
 */

@Repository
public class RefundDaoImpl extends BaseDaoImpl<Refund, String> implements RefundDao {
	
	@SuppressWarnings("unchecked")
	public String getLastRefundSn() {
		String hql = "from Refund as refund order by refund.createDate desc";
		List<Refund> refundList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (refundList != null && refundList.size() > 0) {
			return refundList.get(0).getRefundSn();
		} else {
			return null;
		}
	}
	
	public Refund getRefundByRefundSn(String refundSn) {
		String hql = "from Refund as refund where refund.refundSn = ?";
		return (Refund) getSession().createQuery(hql).setParameter(0, refundSn).uniqueResult();
	}

}