package com.zg.dao.impl;

import com.zg.beans.Pager;
import com.zg.dao.DepositDao;
import com.zg.entity.Deposit;
import com.zg.entity.Member;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class DepositDaoImpl extends BaseDaoImpl<Deposit, String> implements DepositDao {

	public Pager getDepositPager(Member member, Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		if (pager.getPageSize() == null) {
			pager.setPageSize(Deposit.DEFAULT_DEPOSIT_LIST_PAGE_SIZE);
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Deposit.class);
		detachedCriteria.add(Restrictions.eq("member", member));
		return super.findByPager(pager, detachedCriteria);
	}
	
}