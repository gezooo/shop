package com.zg.dao;

import com.zg.entity.Admin;

/*
* @author gez
* @version 0.1
*/

public interface AdminDao extends BaseDao<Admin, String> {
	
	public boolean isExistByUsername(String username);
	
	public Admin getAdminByUsername(String username);

}
