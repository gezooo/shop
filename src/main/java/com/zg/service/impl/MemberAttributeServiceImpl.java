package com.zg.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.dao.MemberAttributeDao;
import com.zg.entity.MemberAttribute;
import com.zg.service.MemberAttributeService;


/*
* @author gez
* @version 0.1
*/

@Service
public class MemberAttributeServiceImpl extends BaseServiceImpl<MemberAttribute, String> implements MemberAttributeService {

	@Resource
	private MemberAttributeDao memberAttributeDao;
	
	@Resource
	public void setBaseDao(MemberAttributeDao memberAttributeDao) {
		super.setBaseDao(memberAttributeDao);
	}
	
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public List<MemberAttribute> getEnabledMemberAttributeList() {
		List<MemberAttribute> enabledMemberAttributeList = memberAttributeDao.getEnabledMemberAttributeList();
		if (enabledMemberAttributeList != null) {
			for (MemberAttribute enabledMemberAttribute : enabledMemberAttributeList) {
				Hibernate.initialize(enabledMemberAttribute);
			}
		}
		return enabledMemberAttributeList;
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(MemberAttribute memberAttribute) {
		super.delete(memberAttribute);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(String[] ids) {
		super.delete(ids);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public String save(MemberAttribute memberAttribute) {
		return super.save(memberAttribute);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void update(MemberAttribute memberAttribute) {
		super.update(memberAttribute);
	}

}