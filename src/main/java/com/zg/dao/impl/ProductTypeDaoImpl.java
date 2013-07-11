package com.zg.dao.impl;

import java.util.Set;

import com.zg.dao.ProductTypeDao;
import com.zg.entity.Product;
import com.zg.entity.ProductType;

import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class ProductTypeDaoImpl extends BaseDaoImpl<ProductType, String> implements ProductTypeDao {

	// 关联处理
	@Override
	public void delete(ProductType productType) {
		Set<Product> productSet = productType.getProductSet();
		for (Product product : productSet) {
			product.setProductType(null);
			product.setProductAttributeMap(null);
		}
		super.delete(productType);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		ProductType productType = super.load(id);
		this.delete(productType);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			ProductType productType = super.load(id);
			this.delete(productType);
		}
	}

}