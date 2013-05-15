package com.zg.dao;

import com.zg.entity.Admin;


public interface AdminDao extends BaseDao<Admin, String> {
	
	public boolean isExistByUsername(String username);
	
	public Admin getAdminByUsername(String username);

}
