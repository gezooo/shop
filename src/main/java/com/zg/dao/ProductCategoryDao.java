package com.zg.dao;

import java.util.List;

import com.zg.entity.ProductCategory;

/*
* @author gez
* @version 0.1
*/

public interface ProductCategoryDao extends BaseDao<ProductCategory, String> {

	public List<ProductCategory> getRootProductCategoryList();

	public List<ProductCategory> getParentProductCategoryList(ProductCategory productCategory);

	public List<ProductCategory> getChildrenProductCategoryList(ProductCategory productCategory);

}
