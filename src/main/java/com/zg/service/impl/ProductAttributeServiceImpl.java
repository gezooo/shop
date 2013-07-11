package com.zg.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.dao.ProductAttributeDao;
import com.zg.entity.ProductAttribute;
import com.zg.entity.ProductType;
import com.zg.service.ProductAttributeService;


/*
* @author gez
* @version 0.1
*/

@Service
public class ProductAttributeServiceImpl extends BaseServiceImpl<ProductAttribute, String> implements ProductAttributeService {

	@Resource
	private ProductAttributeDao productAttributeDao;
	
	@Resource
	public void setBaseDao(ProductAttributeDao productAttributeDao) {
		super.setBaseDao(productAttributeDao);
	}
	
	@Cacheable(value = "caching", key="'ProductAttributeServiceImpl.getEnabledProductAttributeList'")
	public List<ProductAttribute> getEnabledProductAttributeList() {
		List<ProductAttribute> enabledProductAttributeList = productAttributeDao.getEnabledProductAttributeList();
		if (enabledProductAttributeList != null) {
			for (ProductAttribute enabledProductAttribute : enabledProductAttributeList) {
				Hibernate.initialize(enabledProductAttribute);
			}
		}
		return enabledProductAttributeList;
	}
	
	@Cacheable(value = "caching", key="'ProductAttributeServiceImpl.getEnabledProductAttributeList' + #productType.id")
	public List<ProductAttribute> getEnabledProductAttributeList(ProductType productType) {
		List<ProductAttribute> enabledProductAttributeList = productAttributeDao.getEnabledProductAttributeList(productType);
		if (enabledProductAttributeList != null) {
			for (ProductAttribute enabledProductAttribute : enabledProductAttributeList) {
				Hibernate.initialize(enabledProductAttribute);
			}
		}
		return enabledProductAttributeList;
	}
	
	public ProductAttribute getProductAttribute(ProductType productType, String name) {
		return productAttributeDao.getProductAttribute(productType, name);
	}

	@Override
	@CacheEvict(value = "caching", allEntries = true)
	public void delete(ProductAttribute productAttribute) {
		productAttributeDao.delete(productAttribute);
	}

	@Override
	@CacheEvict(value = "caching", allEntries = true)
	public void delete(String id) {
		productAttributeDao.delete(id);
	}

	@Override
	@CacheEvict(value = "caching", allEntries = true)
	public void delete(String[] ids) {
		productAttributeDao.delete(ids);
	}

	@Override
	@CacheEvict(value = "caching", allEntries = true)
	public String save(ProductAttribute productAttribute) {
		return productAttributeDao.save(productAttribute);
	}

	@Override
	@CacheEvict(value = "caching", allEntries = true)
	public void update(ProductAttribute productAttribute) {
		productAttributeDao.update(productAttribute);
	}

}