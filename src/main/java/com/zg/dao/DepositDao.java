package com.zg.dao;

import com.zg.beans.Pager;
import com.zg.entity.Deposit;
import com.zg.entity.Member;

public interface DepositDao extends BaseDao<Deposit, String> {

	public Pager<Deposit> getDepositPager(Member member, Pager<Deposit> pager);

}
