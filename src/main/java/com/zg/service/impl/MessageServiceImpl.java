package com.zg.service.impl;

import javax.annotation.Resource;


import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import com.zg.beans.Pager;
import com.zg.dao.MessageDao;
import com.zg.entity.Member;
import com.zg.entity.Message;
import com.zg.service.MessageService;

/*
* @author gez
* @version 0.1
*/

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService {
	
	@Resource
	private MessageDao messageDao;
	
	@Resource
	public void setBaseDao(MessageDao messageDao) {
		super.setBaseDao(messageDao);
	}
	
	public Pager<Message> getMemberInboxPager(Member member, Pager<Message> pager) {
		return messageDao.getMemberInboxPager(member, pager);
	}
	
	public Pager<Message> getMemberOutboxPager(Member member, Pager<Message> pager) {
		return messageDao.getMemberOutboxPager(member, pager);
	}
	
	public Pager<Message> getMemberDraftboxPager(Member member, Pager<Message> pager) {
		return messageDao.getMemberDraftboxPager(member, pager);
	}
	
	public Pager<Message> getAdminInboxPager(Pager<Message> pager) {
		return messageDao.getAdminInboxPager(pager);
	}
	
	public Pager<Message> getAdminOutboxPager(Pager<Message> pager) {
		return messageDao.getAdminOutboxPager(pager);
	}
	
	public Long getUnreadMessageCount(Member member) {
		return messageDao.getUnreadMessageCount(member);
	}
	
	public Long getUnreadMessageCount() {
		return messageDao.getUnreadMessageCount();
	}

	
}