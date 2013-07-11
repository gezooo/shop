package com.zg.dao.impl;

import java.util.Set;

import com.zg.dao.BrandDao;
import com.zg.entity.Brand;
import com.zg.entity.Product;

import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class BrandDaoImpl extends BaseDaoImpl<Brand, String> implements BrandDao {
	
	// 关联处理
	@Override
	public void delete(Brand brand) {
		Set<Product> productSet = brand.getProductSet();
		if (productSet != null) {
			for (Product product : productSet) {
				product.setBrand(null);
			}
		}
		super.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Brand brand = load(id);
		this.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Brand brand = load(id);
			this.delete(brand);
		}
	}
	
}