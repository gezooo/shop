package com.zg.service;

import com.zg.entity.Reship;

/*
* @author gez
* @version 0.1
*/

public interface ReshipService extends BaseService<Reship, String> {

	public String getLastReshipSn();

}
