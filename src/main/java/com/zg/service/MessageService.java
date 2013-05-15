package com.zg.service;

import com.zg.beans.Pager;
import com.zg.entity.Member;
import com.zg.entity.Message;

public interface MessageService extends BaseService<Message, String>{

	public Pager<Message> getMemberInboxPager(Member member, Pager<Message> pager);
	
	public Pager<Message> getMemberOutboxPager(Member member, Pager<Message> pager);
	
	public Pager<Message> getMemberDraftboxPager(Member member, Pager<Message> pager);
	
	public Pager<Message> getAdminInboxPager(Pager<Message> pager);
	
	public Pager<Message> getAdminOutboxPager(Pager<Message> pager);
	
	public Long getUnreadMessageCount(Member member);
	
	public Long getUnreadMessageCount();






	
	

}
