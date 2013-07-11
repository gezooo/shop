package com.zg.dao;

import com.zg.entity.Agreement;

/*
* @author gez
* @version 0.1
*/

public interface AgreementDao extends BaseDao<Agreement, String> {

	public Agreement getAgreement();

}
