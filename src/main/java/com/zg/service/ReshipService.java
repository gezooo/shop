package com.zg.service;

import com.zg.entity.Reship;

public interface ReshipService extends BaseService<Reship, String> {

	public String getLastReshipSn();

}
