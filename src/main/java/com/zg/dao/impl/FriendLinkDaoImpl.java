package com.zg.dao.impl;

import java.util.List;

import com.zg.beans.Pager;
import com.zg.beans.Pager.OrderType;
import com.zg.dao.FriendLinkDao;
import com.zg.entity.FriendLink;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 友情链接
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX101A4BDAB567AF3A03292AE07AFE4253
 * ============================================================================
 */

@Repository
public class FriendLinkDaoImpl extends BaseDaoImpl<FriendLink, String> implements FriendLinkDao {

	@SuppressWarnings("unchecked")
	public List<FriendLink> getPictureFriendLinkList() {
		String hql = "from FriendLink friendLink where friendLink.logo is not null order by friendLink.orderList asc friendLink.createDate desc";
		return getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<FriendLink> getTextFriendLinkList() {
		String hql = "from FriendLink friendLink where friendLink.logo is null order by friendLink.orderList asc friendLink.createDate desc";
		return getSession().createQuery(hql).list();
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<FriendLink> getAll() {
		String hql = "from FriendLink friendLink order by friendLink.orderList asc friendLink.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<FriendLink> getList(String propertyName, Object value) {
		String hql = "from FriendLink friendLink where friendLink." + propertyName + "=? order by friendLink.orderList asc friendLink.createDate desc";
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FriendLink.class);
		return this.findByPager(pager, detachedCriteria);
	}
	
}