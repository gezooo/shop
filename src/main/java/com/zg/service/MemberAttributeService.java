package com.zg.service;

import java.util.List;

import com.zg.entity.MemberAttribute;

/*
* @author gez
* @version 0.1
*/

public interface MemberAttributeService extends BaseService<MemberAttribute, String> {
	
	public List<MemberAttribute> getEnabledMemberAttributeList();

}
