package com.zg.dao.impl;

import java.util.List;

import com.zg.dao.MemberRankDao;
import com.zg.entity.MemberRank;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员分类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX1FC43548A8F1563DB9E55475E0AE0011
 * ============================================================================
 */

@Repository
public class MemberRankDaoImpl extends BaseDaoImpl<MemberRank, String> implements MemberRankDao {
	
	public MemberRank getDefaultMemberRank() {
		String hql = "from MemberRank as memberRank where memberRank.isDefault = ?";
		MemberRank defaultMemberRank = (MemberRank) getSession().createQuery(hql).setParameter(0, true).uniqueResult();
		if(defaultMemberRank == null) {
			hql = "from MemberRank as memberRank order by memberRank.createDate asc";
			defaultMemberRank = (MemberRank) getSession().createQuery(hql).setMaxResults(1).uniqueResult();
		}
		return defaultMemberRank;
	}
	
	public MemberRank getMemberRankByPoint(Integer point) {
		String hql = "from MemberRank as memberRank where memberRank.point = ?";
		return (MemberRank) getSession().createQuery(hql).setParameter(0, point).uniqueResult();
	}
	
	public MemberRank getUpMemberRankByPoint(Integer point) {
		String hql = "from MemberRank as memberRank where memberRank.point <= ? order by memberRank.point desc";
		return (MemberRank) getSession().createQuery(hql).setParameter(0, point).setMaxResults(1).uniqueResult();
	}
	
	// 重写方法，保存时若对象isDefault=true，则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public String save(MemberRank memberRank) {
		if (memberRank.getIsDefault()) {
			String hql = "from MemberRank memberRank where memberRank.isDefault = ?";
			List<MemberRank> memberRankList = getSession().createQuery(hql).setParameter(0, true).list();
			if (memberRankList != null) {
				for (MemberRank r : memberRankList) {
					r.setIsDefault(false);
				}
			}
		}
		return super.save(memberRank);
	}

	// 重写方法，更新时若对象isDefault=true，则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public void update(MemberRank memberRank) {
		if (memberRank.getIsDefault()) {
			String hql = "from MemberRank memberRank where memberRank.isDefault = ? and memberRank != ?";
			List<MemberRank> memberRankList = getSession().createQuery(hql).setParameter(0, true).setParameter(1, memberRank).list();
			if (memberRankList != null) {
				for (MemberRank r : memberRankList) {
					r.setIsDefault(false);
				}
			}
		}
		super.update(memberRank);
	}

}