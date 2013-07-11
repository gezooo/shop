package com.zg.service;

import java.util.List;

import com.zg.entity.Area;

/*
* @author gez
* @version 0.1
*/

public interface AreaService extends BaseService<Area, String> {
	
	public List<Area> getRootAreaList();
	
	public List<Area> getParentAreaList(Area area);
	
	public List<Area> getChildrenAreaList(Area area);
	
	public boolean isNameUnique(String parentId, String oldName, String newName);
	
	public boolean isAreaPath(String areaPath);
	
	public String getAreaString(Area area);
	
	public String getAreaString(String areaPath);

}
