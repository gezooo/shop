package com.zg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.common.util.CommonUtils;
import com.zg.dao.DeliveryTypeDao;
import com.zg.entity.DeliveryType;
import com.zg.service.DeliveryTypeService;

/*
* @author gez
* @version 0.1
*/

@Service
public class DeliveryTypeServiceImpl extends BaseServiceImpl<DeliveryType, String> implements DeliveryTypeService {

	private static final Logger logger = LoggerFactory.getLogger(DeliveryTypeServiceImpl.class);
	
	@Resource
	DeliveryTypeDao deliveryTypeDao;
	
	@Resource
	public void setBaseDao(DeliveryTypeDao deliveryTypeDao) {
		super.setBaseDao(deliveryTypeDao);
	}
	
	@Override
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public List<DeliveryType> getAll() {
		logger.debug(CommonUtils.displayMessage(" called", null));
		return deliveryTypeDao.getAll();
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(DeliveryType deliveryType) {
		deliveryTypeDao.delete(deliveryType);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(String id) {
		deliveryTypeDao.delete(id);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(String[] ids) {
		deliveryTypeDao.delete(ids);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public String save(DeliveryType deliveryType) {
		return deliveryTypeDao.save(deliveryType);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void update(DeliveryType deliveryType) {
		deliveryTypeDao.update(deliveryType);
	}
}
