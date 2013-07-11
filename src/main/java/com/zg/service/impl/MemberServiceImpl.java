package com.zg.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zg.common.util.CommonUtils;
import com.zg.dao.MemberDao;
import com.zg.entity.Member;
import com.zg.service.MemberService;

/*
* @author gez
* @version 0.1
*/

@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, String> implements MemberService {
	
	@Resource
	private MemberDao memberDao;

	@Resource
	public void setBaseDao(MemberDao userDao) {
		super.setBaseDao(userDao);
	}
	
	public boolean isExistByUsername(String username) {
		return memberDao.isExistByUsername(username);
	}
	
	public Member getMemberByUsername(String username) {
		return memberDao.getMemberByUsername(username);
	}
	
	public boolean verifyMember(String username, String password) {
		Member member = getMemberByUsername(username);
		if(member != null && member.getPassword().equals(DigestUtils.md5Hex(password))) {
			return true;
		}
		return false;
	}
	
	public String buildPasswordRecoverKey() {
		return System.currentTimeMillis() + Member.PASSWORD_RECOVER_KEY_SEPARATOR + CommonUtils.getUUID() + DigestUtils.md5Hex(CommonUtils.getRandomString(10));
	}
	
	public Date getPasswordRecoverKeyBuildDate(String passwordRecoverKey) {
		long time = Long.valueOf(StringUtils.substringBefore(passwordRecoverKey, Member.PASSWORD_RECOVER_KEY_SEPARATOR));
		return new Date(time);
	}

}
