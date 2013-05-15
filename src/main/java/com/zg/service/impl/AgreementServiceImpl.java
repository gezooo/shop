package com.zg.service.impl;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.zg.dao.AgreementDao;
import com.zg.entity.Agreement;
import com.zg.service.AgreementService;

public class AgreementServiceImpl extends BaseServiceImpl<Agreement, String> implements AgreementService {

	@Resource
	private AgreementDao agreementDao;
	
	@Resource
	public void setBaseDao(AgreementDao agreementDao) {
		super.setBaseDao(agreementDao);
	}
	
	@Cacheable("caching")
	@Override
	public Agreement getAgreement() {
		Agreement agreement = agreementDao.getAgreement();
		Hibernate.initialize(agreement);
		return agreement;
	}
	
	@Override
	@CacheEvict("caching")
	public void delete(Agreement entity) {
		agreementDao.delete(entity);
	}
	
	@Override
	@CacheEvict("caching")
	public void delete(String id) {
		agreementDao.delete(id);
	}

	@Override
	@CacheEvict("caching")
	public void delete(String[] ids) {
		agreementDao.delete(ids);
	}

	@Override
	@CacheEvict("caching")
	public String save(Agreement entity) {
		return agreementDao.save(entity);
	}

	@Override
	@CacheEvict("caching")
	public void update(Agreement entity) {
		agreementDao.update(entity);
	}

}
