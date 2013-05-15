package com.zg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.zg.beans.Pager;
import com.zg.dao.AdminDao;
import com.zg.entity.Admin;
import com.zg.service.AdminService;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, String> implements AdminService{

	@Resource
	private AdminDao adminDao;
	
	@Resource
	public void setBaseDao(AdminDao adminDao) {
		super.setBaseDao(adminDao);
	}
	
	@Override
	public Admin getLoginAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if(principal  == null || !(principal instanceof Admin)) {
			return null;
		} else {
			return (Admin) principal;
		}
	}

	@Override
	public Admin loadLoginAdmin() {
		Admin admin = getLoginAdmin();
		if(admin == null) {
			return null;
		} else {
			return adminDao.load(admin.getId());
		}
	}

	@Override
	public boolean isExistByUsername(String username) {
		return adminDao.isExistByUsername(username);
	}

	@Override
	public Admin getAdminByUserName(String username) {
		return adminDao.getAdminByUsername(username);
	}

	
	

}
