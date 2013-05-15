package com.zg.dao;

import java.util.List;

import com.zg.entity.FriendLink;

public interface FriendLinkDao extends BaseDao<FriendLink, String> {

	public List<FriendLink> getPictureFriendLinkList();

	public List<FriendLink> getTextFriendLinkList();

}
