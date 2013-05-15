package com.zg.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.dao.NavigationDao;
import com.zg.entity.Navigation;
import com.zg.service.NavigationService;


/**
 * Service实现类 - 导航
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXD7D7FC7B8EF1162D69F6EBC8BBFEEC6C
 * ============================================================================
 */

@Service
public class NavigationServiceImpl extends BaseServiceImpl<Navigation, String> implements NavigationService {

	@Resource
	private NavigationDao navigationDao;

	@Resource
	public void setBaseDao(NavigationDao navigationDao) {
		super.setBaseDao(navigationDao);
	}

	@Cacheable(value = "caching")
	public List<Navigation> getTopNavigationList() {
		List<Navigation> topNavigationList = navigationDao.getTopNavigationList();
		if (topNavigationList != null) {
			for (Navigation topNavigation : topNavigationList) {
				Hibernate.initialize(topNavigation);
			}
		}
		return topNavigationList;
	}
	
	@Cacheable(value = "caching")
	public List<Navigation> getMiddleNavigationList() {
		List<Navigation> middleNavigationList = navigationDao.getMiddleNavigationList();
		if (middleNavigationList != null) {
			for (Navigation middleNavigation : middleNavigationList) {
				Hibernate.initialize(middleNavigation);
			}
		}
		return middleNavigationList;
	}
	
	@Cacheable(value = "caching")
	public List<Navigation> getBottomNavigationList() {
		List<Navigation> bottomNavigationList = navigationDao.getBottomNavigationList();
		if (bottomNavigationList != null) {
			for (Navigation bottomNavigation : bottomNavigationList) {
				Hibernate.initialize(bottomNavigation);
			}
		}
		return bottomNavigationList;
	}

	@Override
	@CacheEvict(value = "caching")
	public void delete(Navigation entity) {
		navigationDao.delete(entity);
	}

	@Override
	@CacheEvict(value = "caching")
	public void delete(String id) {
		navigationDao.delete(id);
	}

	@Override
	@CacheEvict(value = "caching")
	public void delete(String[] ids) {
		navigationDao.delete(ids);
	}

	@Override
	@CacheEvict(value = "caching")
	public String save(Navigation entity) {
		return navigationDao.save(entity);
	}

	@Override
	@CacheEvict(value = "caching")
	public void update(Navigation entity) {
		navigationDao.update(entity);
	}

}
