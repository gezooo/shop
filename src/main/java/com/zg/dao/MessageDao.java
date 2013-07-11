package com.zg.dao;

import com.zg.beans.Pager;
import com.zg.entity.Member;
import com.zg.entity.Message;

/*
* @author gez
* @version 0.1
*/

public interface MessageDao extends BaseDao<Message, String> {

	public Pager<Message> getMemberInboxPager(Member member, Pager<Message> pager);
	
	public Pager<Message>  getMemberOutboxPager(Member member, Pager<Message>  pager);
	
	public Pager<Message> getMemberDraftboxPager(Member member, Pager<Message> pager);
	
	public Pager<Message> getAdminInboxPager(Pager<Message> pager);
	
	public Pager<Message> getAdminOutboxPager(Pager<Message> pager);

	public Long getUnreadMessageCount(Member member);

	public Long getUnreadMessageCount();





}
