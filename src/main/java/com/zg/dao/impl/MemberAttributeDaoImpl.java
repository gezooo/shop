package com.zg.dao.impl;

import java.util.List;
import java.util.Map;

import com.zg.beans.Pager;
import com.zg.beans.Pager.OrderType;
import com.zg.dao.MemberAttributeDao;
import com.zg.entity.Member;
import com.zg.entity.MemberAttribute;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员属性
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX15EFBDB5E1B211F3D9CD7D7726AA204F
 * ============================================================================
 */

@Repository
public class MemberAttributeDaoImpl extends BaseDaoImpl<MemberAttribute, String> implements MemberAttributeDao {

	@SuppressWarnings("unchecked")
	public List<MemberAttribute> getEnabledMemberAttributeList() {
		String hql = "from MemberAttribute as memberAttribute where memberAttribute.isEnabled = ? order by memberAttribute.orderList asc";
		return getSession().createQuery(hql).setParameter(0, true).list();
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAttribute> getAll() {
		String hql = "from MemberAttribute memberAttribute order by memberAttribute.orderList asc memberAttribute.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<MemberAttribute> getList(String propertyName, Object value) {
		String hql = "from MemberAttribute memberAttribute where memberAttribute." + propertyName + "=? order by memberAttribute.orderList asc memberAttribute.createDate desc";
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MemberAttribute.class);
		return this.findByPager(pager, detachedCriteria);
	}
	
	// 重写方法，删除的同时清除关联
	@SuppressWarnings("unchecked")
	@Override
	public void delete(MemberAttribute memberAttribute) {
		String hql = "from Member as members join members.memberAttributeMapStore memberAttributeMapStore where index(memberAttributeMapStore) = :key";
		List<Member> memberList = getSession().createQuery(hql).setParameter("key", memberAttribute).list();
		for (Member member : memberList) {
			Map<MemberAttribute, String> memberAttributeMapStore = member.getMemberAttributeMapStore();
			memberAttributeMapStore.remove(memberAttribute);
		}
		super.delete(memberAttribute);
	}

	// 重写方法，删除的同时清除关联
	@Override
	public void delete(String id) {
		MemberAttribute memberAttribute = load(id);
		this.delete(memberAttribute);
	}

	// 重写方法，删除的同时清除关联
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

}