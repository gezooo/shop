package com.zg.service;

import com.zg.entity.Admin;

public interface AdminService extends BaseService<Admin, String> {
	
	/**
	 * @return the login {@link Admin}, if no admin login, then {@Code null} will be return
	 */
	public Admin getLoginAdmin();
	
	/**
	 * @return the login {@link Admin} from database, if no admin login, then {@Code null} will be return
	 */
	public Admin loadLoginAdmin();
	
	
	/**
	 * @param userName the userName of the Admin
	 * @return check if the Admin exist with the specified userName
	 */
	public boolean isExistByUsername(String username);
	
	/**
	 * @param userName the userName of the Admin
	 * @return the Admin whit the specified userName
	 */
	public Admin getAdminByUserName(String userName);

}
