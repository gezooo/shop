package com.zg.service;

import java.util.List;

import com.zg.entity.Product;
import com.zg.entity.ProductCategory;

public interface ProductCategoryService extends BaseService<ProductCategory, String> {

	public List<ProductCategory> getRootProductCategoryList();
	
	public List<ProductCategory> getParentProductCategoryList(ProductCategory productCategory);

	public List<ProductCategory> getParentProductCategoryList(Product product);

	public List<ProductCategory> getProductCategoryPathList(ProductCategory productCategory);

	public List<ProductCategory> getProductCategoryPathList(Product product);
	
	public List<ProductCategory> getChildrenProductCategoryList(ProductCategory productCategory);

	public List<ProductCategory> getChildrenProductCategoryList(Product product);
	
	public List<ProductCategory> getProductCategoryTreeList();



}
