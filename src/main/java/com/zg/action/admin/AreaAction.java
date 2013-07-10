package com.zg.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zg.entity.Area;
import com.zg.service.AreaService;
import com.zg.util.JsonUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 地区
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXAECC1B974FB9EE5128F3B8A118CCEC03
 * ============================================================================
 */

@ParentPackage("admin")
public class AreaAction extends BaseAdminAction {

	private static final long serialVersionUID = 6254431866456845575L;

	private Area area;
	private String parentId;
	private Area parent;
	private List<Area> areaList = new ArrayList<Area>();

	@Resource
	private AreaService areaService;

	// 是否已存在ajax验证
	public String checkName() {
		String oldName = getParameter("oldValue");
		String parentId = getParameter("parentId");
		String newName = area.getName();
		if (areaService.isNameUnique(parentId, oldName, newName)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 添加
	public String add() {
		if (StringUtils.isNotEmpty(parentId)) {
			parent = areaService.load(parentId);
		}
		return INPUT;
	}

	// 编辑
	public String edit() {
		area = areaService.load(id);
		parent = area.getParent();
		return INPUT;
	}

	// 列表
	public String list() {
		if (StringUtils.isNotEmpty(parentId)) {
			parent = areaService.load(parentId);
			areaList = new ArrayList<Area>(parent.getChildren());
		} else {
			areaList = areaService.getRootAreaList();
		}
		return LIST;
	}

	// 删除
	public String delete() {
		areaService.delete(id);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "area.name", message = "地区名称不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		String newName = area.getName();
		if (!areaService.isNameUnique(parentId, null, newName)) {
			addActionError("地区名称已存在!");
			return ERROR;
		}
		if (StringUtils.isNotEmpty(parentId)) {
			area.setParent(areaService.load(parentId));
		} else {
			area.setParent(null);
		}
		areaService.save(area);
		redirectionUrl = "area!list.action?parentId=" + parentId;
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "area.name", message = "地区名称不允许为空!")
		}
	)
	public String update() {
		Area persistent = areaService.load(id);
		Area parent = persistent.getParent();
		if (parent != null) {
			parentId = parent.getId();
		}
		if (!areaService.isNameUnique(parentId, persistent.getName(), area.getName())) {
			addActionError("地区名称已存在!");
			return ERROR;
		}
		persistent.setName(area.getName());
		areaService.update(persistent);
		redirectionUrl = "area!list.action?parentId=" + parentId;
		return SUCCESS;
	}
	
	// 根据地区Path值获取下级地区JSON数据
	public String ajaxChildrenArea() {
		String path = getParameter("path");
		if (StringUtils.contains(path,  Area.PATH_SEPARATOR)) {
			id = StringUtils.substringAfterLast(path, Area.PATH_SEPARATOR);
		} else {
			id = path;
		}
		List<Area> childrenAreaList = new ArrayList<Area>();
		if (StringUtils.isEmpty(id)) {
			childrenAreaList = areaService.getRootAreaList();
		} else {
			childrenAreaList = new ArrayList<Area>(areaService.load(id).getChildren());
		}
		List<Map<String, String>> optionList = new ArrayList<Map<String, String>>();
		for (Area area : childrenAreaList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", area.getName());
			map.put("value", area.getPath());
			optionList.add(map);
		}
		//JSONArray jsonArray = JSONArray.fromObject(optionList);
		
		return ajaxJson(JsonUtil.javaList2Json(optionList));
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

}