package com.zg.dao;

import com.zg.entity.Member;

public interface MemberDao extends BaseDao<Member, String> {

	public boolean isExistByUsername(String username);

	public Member getMemberByUsername(String username);

}
