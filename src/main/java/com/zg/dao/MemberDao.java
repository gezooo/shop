package com.zg.dao;

import com.zg.entity.Member;

/*
* @author gez
* @version 0.1
*/
public interface MemberDao extends BaseDao<Member, String> {

	public boolean isExistByUsername(String username);

	public Member getMemberByUsername(String username);

}
