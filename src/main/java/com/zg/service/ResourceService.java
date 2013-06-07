package com.zg.service;

import java.util.Set;

import com.zg.entity.Resource;
import com.zg.entity.Role;

/**
 * Service接口 - 资源
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXF72AD67E390C6D5A8C6FF43A757ACE11
 * ============================================================================
 */

public interface ResourceService extends BaseService<Resource, String> {
	
	public Set<Role> getRoleSet(Resource resource);
	
}
