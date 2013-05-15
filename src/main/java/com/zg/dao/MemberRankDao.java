package com.zg.dao;

import com.zg.entity.MemberRank;

public interface MemberRankDao extends BaseDao<MemberRank, String> {

	public MemberRank getDefaultMemberRank();

	public MemberRank getMemberRankByPoint(Integer point);

	public MemberRank getUpMemberRankByPoint(Integer point);

}
