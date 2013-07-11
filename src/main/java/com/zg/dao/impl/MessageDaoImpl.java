package com.zg.dao.impl;

import com.zg.beans.Pager;
import com.zg.dao.MessageDao;
import com.zg.entity.Member;
import com.zg.entity.Message;
import com.zg.entity.Message.DeleteStatus;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message, String> implements MessageDao{
	
	public Pager<Message> getMemberInboxPager(Member member, Pager<Message> pager) {
		if (pager == null) {
			pager = new Pager<Message>();
			pager.setPageSize(Message.DEFAULT_MESSAGE_LIST_PAGE_SIZE);
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
		detachedCriteria.add(Restrictions.eq("toMember", member));
		detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
		detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.TO_DELETE));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Pager<Message> getMemberOutboxPager(Member member, Pager<Message> pager) {
		if (pager == null) {
			pager = new Pager<Message>();
		}
		if (pager.getPageSize() == null) {
			pager.setPageSize(Message.DEFAULT_MESSAGE_LIST_PAGE_SIZE);
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
		detachedCriteria.add(Restrictions.eq("fromMember", member));
		detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
		detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.FROM_DELETE));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Pager<Message> getMemberDraftboxPager(Member member, Pager<Message> pager) {
		if (pager == null) {
			pager = new Pager<Message>();
		}
		if (pager.getPageSize() == null) {
			pager.setPageSize(Message.DEFAULT_MESSAGE_LIST_PAGE_SIZE);
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
		detachedCriteria.add(Restrictions.eq("fromMember", member));
		detachedCriteria.add(Restrictions.eq("isSaveDraftbox", true));
		detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.FROM_DELETE));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Pager<Message> getAdminInboxPager(Pager<Message> pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
		detachedCriteria.add(Restrictions.isNull("toMember"));
		detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
		detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.TO_DELETE));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Pager<Message> getAdminOutboxPager(Pager<Message> pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
		detachedCriteria.add(Restrictions.isNull("fromMember"));
		detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
		detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.FROM_DELETE));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Long getUnreadMessageCount(Member member) {
		String hql = "select count(*) from Message as message where message.toMember = ? and message.isRead = ? and message.isSaveDraftbox = ? and message.deleteStatus != ?";
		return (Long) getSession().createQuery(hql).setParameter(0, member).setParameter(1, false).setParameter(2, false).setParameter(3, DeleteStatus.TO_DELETE).uniqueResult();
	}
	
	public Long getUnreadMessageCount() {
		String hql = "select count(*) from Message as message where message.toMember is null and message.isRead = ? and message.isSaveDraftbox = ? and message.deleteStatus != ?";
		return (Long) getSession().createQuery(hql).setParameter(0, false).setParameter(1, false).setParameter(2, DeleteStatus.TO_DELETE).uniqueResult();
	}

}