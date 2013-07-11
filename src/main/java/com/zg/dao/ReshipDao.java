package com.zg.dao;

import com.zg.entity.Reship;

/*
* @author gez
* @version 0.1
*/

public interface ReshipDao extends BaseDao<Reship, String> {

	public String getLastReshipSn();

}
