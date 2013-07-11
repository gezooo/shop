package com.zg.service;

import com.zg.entity.Agreement;

/*
* @author gez
* @version 0.1
*/

public interface AgreementService extends BaseService<Agreement, String> {
	
	public Agreement getAgreement();

}
