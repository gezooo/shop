package com.zg.service;

import com.zg.beans.Pager;
import com.zg.entity.Deposit;
import com.zg.entity.Member;

/*
* @author gez
* @version 0.1
*/

public interface DepositService extends BaseService<Deposit, String> {
	
	Pager<Deposit> getDepositPager(Member member, Pager<Deposit> pager);

}
