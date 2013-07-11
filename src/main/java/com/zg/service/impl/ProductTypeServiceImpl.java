package com.zg.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.zg.dao.ProductTypeDao;
import com.zg.entity.ProductType;
import com.zg.service.ProductTypeService;

/*
* @author gez
* @version 0.1
*/

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, String> implements
		ProductTypeService {

	@Resource
	public void setBaseDao(ProductTypeDao productTypeDao) {
		super.setBaseDao(productTypeDao);
	}

}
