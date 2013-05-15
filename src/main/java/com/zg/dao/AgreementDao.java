package com.zg.dao;

import com.zg.entity.Agreement;

public interface AgreementDao extends BaseDao<Agreement, String> {

	public Agreement getAgreement();

}
