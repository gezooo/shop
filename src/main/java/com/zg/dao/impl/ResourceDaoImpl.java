package com.zg.dao.impl;

import java.util.List;
import java.util.Set;

import com.zg.beans.Pager;
import com.zg.beans.Pager.OrderType;
import com.zg.dao.ResourceDao;
import com.zg.entity.Resource;
import com.zg.entity.Role;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class ResourceDaoImpl extends BaseDaoImpl<Resource, String> implements ResourceDao {

	// 处理关联，忽略isSystem=true的对象
	@Override
	public void delete(Resource resource) {
		if (resource.getIsSystem()) {
			return;
		}
		Set<Role> roleSet = resource.getRoleSet();
		if (roleSet != null) {
			for (Role role : roleSet) {
				role.getResourceSet().remove(resource);
			}
		}
		super.delete(resource);
	}
	
	// 处理关联，忽略isSystem=true的对象
	@Override
	public void delete(String id) {
		Resource resource = load(id);
		this.delete(resource);
	}

	// 处理关联，忽略isSystem=true的对象。
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	// 设置isSystem=false。
	@Override
	public String save(Resource resource) {
		resource.setIsSystem(false);
		return super.save(resource);
	}

	// 忽略isSystem=true的对象。
	@Override
	public void update(Resource resource) {
		if (resource.getIsSystem()) {
			return;
		}
		super.update(resource);
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> getAll() {
		String hql = "from Resource resource order by resource.orderList asc, resource.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<Resource> getList(String propertyName, Object value) {
		String hql = "from Resource resource where resource." + propertyName + "=? order by resource.orderList asc, resource.createDate desc";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}
	
	// 根据orderList排序
	@Override
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
			pager.setOrderBy("orderList");
			pager.setOrderType(OrderType.ASC);
		}
		return super.findByPager(pager, detachedCriteria);
	}

	// 根据orderList排序
	@Override
	public Pager findByPager(Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Resource.class);
		return this.findByPager(pager, detachedCriteria);
	}

}