package com.zg.dao;

import java.util.List;

import com.zg.entity.ProductAttribute;
import com.zg.entity.ProductType;

/*
* @author gez
* @version 0.1
*/

public interface ProductAttributeDao extends BaseDao<ProductAttribute, String> {

	public List<ProductAttribute> getEnabledProductAttributeList();

	public List<ProductAttribute> getEnabledProductAttributeList(ProductType productType);

	public ProductAttribute getProductAttribute(ProductType productType, String name);

}
