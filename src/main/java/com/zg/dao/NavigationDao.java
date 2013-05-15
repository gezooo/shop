package com.zg.dao;

import java.util.List;

import com.zg.entity.Navigation;

public interface NavigationDao extends BaseDao<Navigation, String> {

	public List<Navigation> getTopNavigationList();

	public List<Navigation> getMiddleNavigationList();

	public List<Navigation> getBottomNavigationList();

}
