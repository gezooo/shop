package com.zg.service;

import java.util.List;

import com.zg.entity.FriendLink;

/*
* @author gez
* @version 0.1
*/

public interface FriendLinkService extends BaseService<FriendLink, String> {
	
	public List<FriendLink> getPictureFriendLinkList();
	
	public List<FriendLink> getTextFriendLinkList();



}
