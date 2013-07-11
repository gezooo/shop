package com.zg.dao.impl;

import java.util.Set;

import com.zg.dao.MemberDao;
import com.zg.entity.Member;
import com.zg.entity.Order;

import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class MemberDaoImpl extends BaseDaoImpl<Member, String> implements MemberDao {

	@SuppressWarnings("unchecked")
	public boolean isExistByUsername(String username) {
		String hql = "from Member members where lower(members.username) = lower(?)";
		Member member = (Member) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
		if (member != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Member getMemberByUsername(String username) {
		String hql = "from Member members where lower(members.username) = lower(?)";
		return (Member) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
	}
	
	// 关联处理
	@Override
	public void delete(Member member) {
		Set<Order> orderSet = member.getOrderSet();
		if (orderSet != null) {
			for (Order order : orderSet) {
				order.setMember(null);
			}
		}
		super.delete(member);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Member member = load(id);
		this.delete(member);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Member member = load(id);
			this.delete(member);
		}
	}

}