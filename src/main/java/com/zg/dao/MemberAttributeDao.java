package com.zg.dao;

import java.util.List;

import com.zg.entity.MemberAttribute;

public interface MemberAttributeDao extends BaseDao<MemberAttribute, String> {

	public List<MemberAttribute> getEnabledMemberAttributeList();

}
