package com.zg.dao;

import java.util.List;

import com.zg.entity.FriendLink;

/*
* @author gez
* @version 0.1
*/

public interface FriendLinkDao extends BaseDao<FriendLink, String> {

	public List<FriendLink> getPictureFriendLinkList();

	public List<FriendLink> getTextFriendLinkList();

}
