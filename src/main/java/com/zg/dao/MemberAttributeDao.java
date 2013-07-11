package com.zg.dao;

import java.util.List;

import com.zg.entity.MemberAttribute;

/*
* @author gez
* @version 0.1
*/
public interface MemberAttributeDao extends BaseDao<MemberAttribute, String> {

	public List<MemberAttribute> getEnabledMemberAttributeList();

}
