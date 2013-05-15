package com.zg.service.impl;

import javax.annotation.Resource;


import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import com.zg.beans.Pager;
import com.zg.dao.MessageDao;
import com.zg.entity.Member;
import com.zg.entity.Message;
import com.zg.service.MessageService;

/**
 * Service实现类 - 消息
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX65B94A1C020124AC5E38C179CA8D1CD0
 * ============================================================================
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