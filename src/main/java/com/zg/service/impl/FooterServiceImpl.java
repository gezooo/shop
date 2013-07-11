package com.zg.service.impl;

import javax.annotation.Resource;



import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.dao.FooterDao;
import com.zg.entity.Footer;
import com.zg.service.FooterService;


/*
* @author gez
* @version 0.1
*/

@Service
public class FooterServiceImpl extends BaseServiceImpl<Footer, String> implements FooterService {

	@Resource
	private FooterDao footerDao;

	@Resource
	public void setBaseDao(FooterDao footerDao) {
		super.setBaseDao(footerDao);
	}

	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public Footer getFooter() {
		Footer footer = footerDao.getFooter();
		Hibernate.initialize(footer);
		return footer;
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(Footer entity) {
		footerDao.delete(entity);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(String id) {
		footerDao.delete(id);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(String[] ids) {
		footerDao.delete(ids);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public String save(Footer entity) {
		return footerDao.save(entity);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void update(Footer entity) {
		footerDao.update(entity);
	}

}
