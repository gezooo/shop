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


/**
 * Service实现类 - 商品分类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX9C63255F0E5AAEE7DD3D83FB323FC00D
 * ============================================================================
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

	@Cacheable(value = "caching", key="'ProductCategoryServiceImpl.getRootProductCategoryList'")
	public List<ProductCategory> getRootProductCategoryList() {
		List<ProductCategory> rootProductCategoryList = productCategoryDao.getRootProductCategoryList();
		if (rootProductCategoryList != null) {
			for (ProductCategory rootProductCategory : rootProductCategoryList) {
				Hibernate.initialize(rootProductCategory);
			}
		}
		return rootProductCategoryList;
	}
	
	@Cacheable(value = "caching", key="'ProductCategoryServiceImpl.getParentProductCategoryList' + #productCategory.id")
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
	
	@Cacheable(value = "caching", key="'ProductCategoryServiceImpl.getChildrenProductCategoryList' + #productCategory.id")
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
	
	@Cacheable(value = "caching", key="'ProductCategoryServiceImpl.getProductCategoryTreeList'")
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
	@Cacheable(value = "caching", key="'ProductCategoryServiceImpl.getAll'")
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