package com.zg.service;

import com.zg.entity.MemberRank;

public interface MemberRankService extends BaseService<MemberRank, String> {

	public MemberRank getDefaultMemberRank();

	public MemberRank getMemberRankByPoint(Integer point);
	
	public MemberRank getUpMemberRankByPoint(Integer point);


}
