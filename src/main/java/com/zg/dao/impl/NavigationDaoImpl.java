package com.zg.dao.impl;

import java.util.List;

import com.zg.beans.Pager;
import com.zg.beans.Pager.OrderType;
import com.zg.dao.NavigationDao;
import com.zg.entity.Navigation;
import com.zg.entity.Navigation.Position;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 导航
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX873CAD06D89CFF2EE966FE1FAC8850B3
 * ============================================================================
 */

@Repository
public class NavigationDaoImpl extends BaseDaoImpl<Navigation, String> implements NavigationDao {

	@SuppressWarnings("unchecked")
	public List<Navigation> getTopNavigationList() {
		String hql = "from Navigation as navigation where position = ? and isVisible = ? order by navigation.orderList asc";
		return getSession().createQuery(hql).setParameter(0, Position.TOP).setParameter(1, true).list();
	}

	@SuppressWarnings("unchecked")
	public List<Navigation> getMiddleNavigationList() {
		String hql = "from Navigation as navigation where position = ? and isVisible = ? order by navigation.orderList asc";
		return getSession().createQuery(hql).setParameter(0, Position.MIDDLE).setParameter(1, true).list();
	}

	@SuppressWarnings("unchecked")
	public List<Navigation> getBottomNavigationList() {
		String hql = "from Navigation as navigation where position = ? and isVisible = ? order by navigation.orderList asc";
		return getSession().createQuery(hql).setParameter(0, Position.BOTTOM).setParameter(1, true).list();
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<Navigation> getAll() {
		String hql = "from Navigation navigation order by navigation.orderList asc navigation.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<Navigation> getList(String propertyName, Object value) {
		String hql = "from Navigation navigation where navigation." + propertyName + "=? order by navigation.orderList asc navigation.createDate desc";
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Navigation.class);
		return this.findByPager(pager, detachedCriteria);
	}

}
