package com.zg.service;

import java.util.List;

import com.zg.entity.MemberAttribute;

public interface MemberAttributeService extends BaseService<MemberAttribute, String> {
	
	public List<MemberAttribute> getEnabledMemberAttributeList();

}
