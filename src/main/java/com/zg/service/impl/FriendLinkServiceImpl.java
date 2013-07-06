package com.zg.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;



import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.dao.FriendLinkDao;
import com.zg.entity.FriendLink;
import com.zg.service.FriendLinkService;


/**
 * Service实现类 - 友情链接
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXD710B9580A96A2B957C605ADE37619E2
 * ============================================================================
 */

@Service
public class FriendLinkServiceImpl extends BaseServiceImpl<FriendLink, String> implements FriendLinkService {
	
	@Resource
	FriendLinkDao friendLinkDao;

	@Resource
	public void setBaseDao(FriendLinkDao friendLinkDao) {
		super.setBaseDao(friendLinkDao);
	}
	
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public List<FriendLink> getPictureFriendLinkList() {
		List<FriendLink> pictureFriendLinkList = friendLinkDao.getPictureFriendLinkList();
		if (pictureFriendLinkList != null) {
			for (FriendLink pictureFriendLink : pictureFriendLinkList) {
				Hibernate.initialize(pictureFriendLink);
			}
		}
		return pictureFriendLinkList;
	}
	
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public List<FriendLink> getTextFriendLinkList() {
		List<FriendLink> textFriendLinkList = friendLinkDao.getTextFriendLinkList();
		if (textFriendLinkList != null) {
			for (FriendLink textFriendLink : textFriendLinkList) {
				Hibernate.initialize(textFriendLink);
			}
		}
		return textFriendLinkList;
	}
	
	@Override
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public List<FriendLink> getAll() {
		List<FriendLink> allFriendLink = friendLinkDao.getAll();
		if (allFriendLink != null) {
			for (FriendLink friendLink : allFriendLink) {
				Hibernate.initialize(friendLink);
			}
		}
		return allFriendLink;
	}

	// 重写方法，删除同时删除Logo文件
	@Override
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName + #friendLink.id")
	public void delete(FriendLink friendLink) {
		if (friendLink.getLogo() != null) {
			File logoFile = new File(ServletActionContext.getServletContext().getRealPath(friendLink.getLogo()));
			if (logoFile.exists()) {
				logoFile.delete();
			}
		}
		friendLinkDao.delete(friendLink);
	}

	// 重写方法，删除同时删除Logo文件
	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(String id) {
		FriendLink friendLink = friendLinkDao.load(id);
		this.delete(friendLink);
	}

	// 重写方法，删除同时删除Logo文件
	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public String save(FriendLink friendLink) {
		return friendLinkDao.save(friendLink);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void update(FriendLink friendLink) {
		friendLinkDao.update(friendLink);
	}

}