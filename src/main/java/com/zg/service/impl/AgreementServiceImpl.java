package com.zg.service.impl;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.dao.AgreementDao;
import com.zg.entity.Agreement;
import com.zg.service.AgreementService;

/*
* @author gez
* @version 0.1
*/

@Service
public class AgreementServiceImpl extends BaseServiceImpl<Agreement, String> implements AgreementService {

	@Resource
	private AgreementDao agreementDao;
	
	@Resource
	public void setBaseDao(AgreementDao agreementDao) {
		super.setBaseDao(agreementDao);
	}
	
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	@Override
	public Agreement getAgreement() {
		Agreement agreement = agreementDao.getAgreement();
		Hibernate.initialize(agreement);
		return agreement;
	}
	
	@Override
	@CacheEvict(value="caching", allEntries=true)
	public void delete(Agreement entity) {
		agreementDao.delete(entity);
	}
	
	@Override
	@CacheEvict(value="caching", allEntries=true)
	public void delete(String id) {
		agreementDao.delete(id);
	}

	@Override
	@CacheEvict(value="caching", allEntries=true)
	public void delete(String[] ids) {
		agreementDao.delete(ids);
	}

	@Override
	@CacheEvict(value="caching", allEntries=true)
	public String save(Agreement entity) {
		return agreementDao.save(entity);
	}

	@Override
	@CacheEvict(value="caching", allEntries=true)
	public void update(Agreement entity) {
		agreementDao.update(entity);
	}

}
