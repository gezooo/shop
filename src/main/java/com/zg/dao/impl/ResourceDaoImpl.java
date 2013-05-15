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

/**
 * Dao实现类 - 资源
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXAE53DE4E24A64B122A4E5EB9045C2A61
 * ============================================================================
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
		String hql = "from Resource resource order by resource.orderList asc resource.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<Resource> getList(String propertyName, Object value) {
		String hql = "from Resource resource where resource." + propertyName + "=? order by resource.orderList asc resource.createDate desc";
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