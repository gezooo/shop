package com.zg.dao;

import java.util.List;

import com.zg.entity.Area;

/*
* @author gez
* @version 0.1
*/

public interface AreaDao extends BaseDao<Area, String> {

	public List<Area> getRootAreaList();
	
	public List<Area> getParentAreaList(Area area);
	
	public List<Area> getChildrenAreaList(Area area);
	
	public Boolean isNameUnique(String parentId, String oldName, String newName);




}
