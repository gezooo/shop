package com.zg.dao.impl;

import com.zg.dao.AgreementDao;
import com.zg.entity.Agreement;

import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class AgreementDaoImpl extends BaseDaoImpl<Agreement, String> implements AgreementDao {

	public Agreement getAgreement() {
		return load(Agreement.AGREEMENT_ID);
	}

}