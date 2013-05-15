package com.zg.dao.impl;

import java.util.List;

import com.zg.dao.ReceiverDao;
import com.zg.entity.Receiver;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 收货地址
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXFDB0C29D95AE89A58BB8DA83E757D119
 * ============================================================================
 */

@Repository
public class ReceiverDaoImpl extends BaseDaoImpl<Receiver, String> implements ReceiverDao {

	// 重写方法，保存时若对象isDefault=true，则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public String save(Receiver receiver) {
		if (receiver.getIsDefault()) {
			String hql = "from Receiver receiver where receiver.member = ? and receiver.isDefault = ?";
			List<Receiver> receiverList = getSession().createQuery(hql).setParameter(0, receiver.getMember()).setParameter(1, true).list();
			if (receiverList != null) {
				for (Receiver r : receiverList) {
					r.setIsDefault(false);
				}
			}
		}
		return super.save(receiver);
	}

	// 重写方法，更新时若对象isDefault=true，则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public void update(Receiver receiver) {
		if (receiver.getIsDefault()) {
			String hql = "from Receiver receiver where receiver.member = ? and receiver.isDefault = ? and receiver != ?";
			List<Receiver> receiverList = getSession().createQuery(hql).setParameter(0, receiver.getMember()).setParameter(1, true).setParameter(2, receiver).list();
			if (receiverList != null) {
				for (Receiver r : receiverList) {
					r.setIsDefault(false);
				}
			}
		}
		super.update(receiver);
	}

}