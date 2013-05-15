package com.zg.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zg.dao.RoleDao;
import com.zg.entity.Role;
import com.zg.service.RoleService;
import com.zg.util.SpringUtil;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements RoleService {

	@Resource
	RoleDao roleDao;

	@Resource
	public void setBaseDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
	}
	
	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(Role role) {
		roleDao.delete(role);
		roleDao.flush();
		SpringUtil.flushSpringSecurity();
	}

	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(String id) {
		Role role = roleDao.load(id);
		this.delete(role);
	}

	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Role role = roleDao.load(id);
			roleDao.delete(role);
		}
		roleDao.flush();
		SpringUtil.flushSpringSecurity();
	}

	// 重写方法，保存时刷新SpringSecurity权限信息
	@Override
	public String save(Role role) {
		String id = roleDao.save(role);
		roleDao.flush();
		roleDao.clear();
		SpringUtil.flushSpringSecurity();
		return id;
	}

	// 重写方法，更新时刷新SpringSecurity权限信息
	@Override
	public void update(Role role) {
		roleDao.update(role);
		roleDao.flush();
		SpringUtil.flushSpringSecurity();
	}
}
