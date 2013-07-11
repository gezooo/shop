package com.zg.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.zg.beans.Pager;
import com.zg.dao.DepositDao;
import com.zg.entity.Deposit;
import com.zg.entity.Member;
import com.zg.service.DepositService;

/*
* @author gez
* @version 0.1
*/

@Service
public class DepositServiceImpl extends BaseServiceImpl<Deposit, String> implements DepositService {

	@Resource
	private DepositDao depositDao;
	
	@Resource
	public void setBaseDao(DepositDao depositDao) {
		super.setBaseDao(depositDao);
	}
	
	public Pager<Deposit> getDepositPager(Member member, Pager<Deposit> pager) {
		return depositDao.getDepositPager(member, pager);
	}

}