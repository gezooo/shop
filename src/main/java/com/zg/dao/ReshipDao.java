package com.zg.dao;

import com.zg.entity.Reship;

public interface ReshipDao extends BaseDao<Reship, String> {

	public String getLastReshipSn();

}
