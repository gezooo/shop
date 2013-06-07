package com.zg.service.impl;


import java.util.Set;

import org.springframework.stereotype.Service;

import com.zg.dao.ResourceDao;
import com.zg.entity.Resource;
import com.zg.entity.Role;
import com.zg.service.ResourceService;
import com.zg.util.SpringUtil;

@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource, String> implements ResourceService {

	@javax.annotation.Resource
	ResourceDao resourceDao;

	@javax.annotation.Resource
	public void setBaseDao(ResourceDao resourceDao) {
		super.setBaseDao(resourceDao);
	}
	
	// 重写方法，删除时刷新SpringSecurity权限信息
		@Override
		public void delete(Resource resource) {
			resourceDao.delete(resource);
			resourceDao.flush();
			SpringUtil.flushSpringSecurity();
		}

		// 重写方法，删除时刷新SpringSecurity权限信息
		@Override
		public void delete(String id) {
			Resource resource = resourceDao.load(id);
			this.delete(resource);
		}

		// 重写方法，删除时刷新SpringSecurity权限信息
		@Override
		public void delete(String[] ids) {
			for (String id : ids) {
				Resource resource = resourceDao.load(id);
				resourceDao.delete(resource);
			}
			resourceDao.flush();
			SpringUtil.flushSpringSecurity();
		}

		// 重写方法，保存时刷新SpringSecurity权限信息
		@Override
		public String save(Resource resource) {
			String id = resourceDao.save(resource);
			resourceDao.flush();
			SpringUtil.flushSpringSecurity();
			return id;
		}

		// 重写方法，更新时刷新SpringSecurity权限信息
		@Override
		public void update(Resource resource) {
			resourceDao.update(resource);
			resourceDao.flush();
			SpringUtil.flushSpringSecurity();
		}
		
		@Override
		public Set<Role> getRoleSet(Resource resource) {
			return resource.getRoleSet();
		}

		
}
