package com.zg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.dao.DeliveryTypeDao;
import com.zg.entity.DeliveryType;
import com.zg.service.DeliveryTypeService;

@Service
public class DeliveryTypeServiceImpl extends BaseServiceImpl<DeliveryType, String> implements DeliveryTypeService {

	@Resource
	DeliveryTypeDao deliveryTypeDao;
	
	@Resource
	public void setBaseDao(DeliveryTypeDao deliveryTypeDao) {
		super.setBaseDao(deliveryTypeDao);
	}
	
	@Override
	@Cacheable(value = "caching")
	public List<DeliveryType> getAll() {
		return deliveryTypeDao.getAll();
	}

	@Override
	@CacheEvict(value = "caching")
	public void delete(DeliveryType deliveryType) {
		deliveryTypeDao.delete(deliveryType);
	}

	@Override
	@CacheEvict(value = "caching")
	public void delete(String id) {
		deliveryTypeDao.delete(id);
	}

	@Override
	@CacheEvict(value = "caching")
	public void delete(String[] ids) {
		deliveryTypeDao.delete(ids);
	}

	@Override
	@CacheEvict(value = "caching")
	public String save(DeliveryType deliveryType) {
		return deliveryTypeDao.save(deliveryType);
	}

	@Override
	@CacheEvict(value = "caching")
	public void update(DeliveryType deliveryType) {
		deliveryTypeDao.update(deliveryType);
	}
}
