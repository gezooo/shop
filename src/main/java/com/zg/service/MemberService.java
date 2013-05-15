package com.zg.service;

import java.util.Date;

import com.zg.entity.Member;

public interface MemberService extends BaseService<Member, String> {
	
	public boolean isExistByUsername(String username);
	
	public Member getMemberByUsername(String username);

	public boolean verifyMember(String username, String password);

	public String buildPasswordRecoverKey();

	public Date getPasswordRecoverKeyBuildDate(String passwordRecoverKey);


}
