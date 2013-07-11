package com.zg.service.impl;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.zg.dao.BrandDao;
import com.zg.entity.Brand;
import com.zg.service.BrandService;

/*
* @author gez
* @version 0.1
*/

@Service
public class BrandServiceImpl extends BaseServiceImpl<Brand, String> implements BrandService {

	@Resource
	public void setBaseDao(BrandDao brandDao) {
		super.setBaseDao(brandDao);
	}

}