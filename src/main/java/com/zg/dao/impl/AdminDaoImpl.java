package com.zg.dao.impl;

import com.zg.dao.AdminDao;
import com.zg.entity.Admin;

import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao {
	
	@SuppressWarnings("unchecked")
	public boolean isExistByUsername(String username) {
		String hql = "from Admin as admin where lower(admin.username) = lower(?)";
		Admin admin = (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
		if (admin != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Admin getAdminByUsername(String username) {
		String hql = "from Admin as admin where lower(admin.username) = lower(?)";
		return (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
	}
	
}