package com.zg.service;

import java.util.List;

import com.zg.entity.Navigation;

/*
* @author gez
* @version 0.1
*/

public interface NavigationService extends BaseService<Navigation, String> {

	/**
	 * @return the Navigation list which display on the top of the page, and only when isVisible = true;
	 */
	public List<Navigation> getTopNavigationList();
	
	public List<Navigation> getMiddleNavigationList();

	public List<Navigation> getBottomNavigationList();


}
