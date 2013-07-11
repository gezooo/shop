package com.zg.service;

import java.util.Set;

import com.zg.entity.Resource;
import com.zg.entity.Role;

/*
* @author gez
* @version 0.1
*/

public interface ResourceService extends BaseService<Resource, String> {
	
	public Set<Role> getRoleSet(Resource resource);
	
}
