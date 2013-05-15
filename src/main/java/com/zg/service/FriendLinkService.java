package com.zg.service;

import java.util.List;

import com.zg.entity.FriendLink;

public interface FriendLinkService extends BaseService<FriendLink, String> {
	
	public List<FriendLink> getPictureFriendLinkList();
	
	public List<FriendLink> getTextFriendLinkList();



}
