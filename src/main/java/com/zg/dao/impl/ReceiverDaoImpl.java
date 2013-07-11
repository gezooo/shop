package com.zg.dao.impl;

import java.util.List;

import com.zg.dao.ReceiverDao;
import com.zg.entity.Receiver;

import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
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