package com.zg.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;



import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.action.admin.AdminAction;
import com.zg.dao.ProductCategoryDao;
import com.zg.entity.Product;
import com.zg.entity.ProductCategory;
import com.zg.service.ProductCategoryService;


/*
* @author gez
* @version 0.1
*/

@Service
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory, String> implements
		ProductCategoryService {
    
	public static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

	@Resource
	private ProductCategoryDao productCategoryDao;
	
	@Resource
	public void setBaseDao(ProductCategoryDao productCategoryDao) {
		super.setBaseDao(productCategoryDao);
	}

	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public List<ProductCategory> getRootProductCategoryList() {
		List<ProductCategory> rootProductCategoryList = productCategoryDao.getRootProductCategoryList();
		if (rootProductCategoryList != null) {
			for (ProductCategory rootProductCategory : rootProductCategoryList) {
				Hibernate.initialize(rootProductCategory);
			}
		}
		return rootProductCategoryList;
	}
	
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName + #productCategory.id")
	public List<ProductCategory> getParentProductCategoryList(ProductCategory productCategory) {
		List<ProductCategory> parentProductCategoryList = productCategoryDao.getParentProductCategoryList(productCategory);
		if (parentProductCategoryList != null) {
			for (ProductCategory parentProductCategory : parentProductCategoryList) {
				Hibernate.initialize(parentProductCategory);
			}
		}
		return parentProductCategoryList;
	}
	
	public List<ProductCategory> getParentProductCategoryList(Product product) {
		ProductCategory productCategory = product.getProductCategory();
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.addAll(this.getParentProductCategoryList(productCategory));
		productCategoryList.add(productCategory);
		return productCategoryList;
	}
	
	public List<ProductCategory> getProductCategoryPathList(ProductCategory productCategory) {
		List<ProductCategory> productCategoryPathList = new ArrayList<ProductCategory>();
		productCategoryPathList.addAll(this.getParentProductCategoryList(productCategory));
		productCategoryPathList.add(productCategory);
		return productCategoryPathList;
	}
	
	public List<ProductCategory> getProductCategoryPathList(Product product) {
		ProductCategory productCategory = product.getProductCategory();
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.addAll(this.getParentProductCategoryList(productCategory));
		productCategoryList.add(productCategory);
		return productCategoryList;
	}
	
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName + #productCategory.id")
	public List<ProductCategory> getChildrenProductCategoryList(ProductCategory productCategory) {
		List<ProductCategory> childrenProductCategoryList = productCategoryDao.getChildrenProductCategoryList(productCategory);
		if (childrenProductCategoryList != null) {
			for (ProductCategory childrenProductCategory : childrenProductCategoryList) {
				Hibernate.initialize(childrenProductCategory);
			}
		}
		return childrenProductCategoryList;
	}
	
	public List<ProductCategory> getChildrenProductCategoryList(Product product) {
		ProductCategory productCategory = product.getProductCategory();
		List<ProductCategory> productCategoryList = getChildrenProductCategoryList(productCategory);
		if (productCategoryList == null) {
			productCategoryList = new ArrayList<ProductCategory>();
		}
		productCategoryList.add(productCategory);
		return productCategoryList;
	}
	
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public List<ProductCategory> getProductCategoryTreeList() {
		logger.debug("getProductCategoryTreeList called");
		List<ProductCategory> allProductCategoryList = this.getAll();
		return recursivProductCategoryTreeList(allProductCategoryList, null, null);
	}
	
	// 递归父类排序分类树
	private List<ProductCategory> recursivProductCategoryTreeList(List<ProductCategory> allProductCategoryList, ProductCategory p, List<ProductCategory> temp) {
		logger.debug("recursivProductCategoryTreeList called");
		
		if (temp == null) {
			temp = new ArrayList<ProductCategory>();
		}
		
		for (ProductCategory productCategory : allProductCategoryList) {
			ProductCategory parent = productCategory.getParent();
			if ((p == null && parent == null) || (productCategory != null && parent == p)) {
				temp.add(productCategory);
				if (productCategory.getChildren() != null && productCategory.getChildren().size() > 0) {
					recursivProductCategoryTreeList(allProductCategoryList, productCategory, temp);
				}
			}
		}
		return temp;
	}

	@Override
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public List<ProductCategory> getAll() {
		logger.debug("getAll called");
		List<ProductCategory> allProductCategory = productCategoryDao.getAll();
		if (allProductCategory != null) {
			for (ProductCategory productCategory : allProductCategory) {
				Hibernate.initialize(productCategory);
			}
		}
		logger.debug("getAll end");
		return allProductCategory;
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(ProductCategory productCategory) {
		productCategoryDao.delete(productCategory);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(String id) {
		productCategoryDao.delete(id);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void delete(String[] ids) {
		productCategoryDao.delete(ids);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public String save(ProductCategory productCategory) {
		return productCategoryDao.save(productCategory);
	}

	@Override
	@CacheEvict(value = "caching", allEntries=true)
	public void update(ProductCategory productCategory) {
		productCategoryDao.update(productCategory);
	}

}