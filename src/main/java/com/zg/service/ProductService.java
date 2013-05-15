package com.zg.service;

import java.util.Date;
import java.util.List;

import com.zg.beans.Pager;
import com.zg.entity.Member;
import com.zg.entity.Product;
import com.zg.entity.ProductCategory;

public interface ProductService extends BaseService<Product, String> {

	public List<Product> getProductList(ProductCategory productCategory);

	public List<Product> getProductList(int firstResult, int maxResults);

	public List<Product> getProductList(ProductCategory productCategory, int firstResult, int maxResults);

	public List<Product> getProductList(Date beginDate, Date endDate, int firstResult, int maxResults);

	public Pager<Product> getProductPager(ProductCategory productCategory, Pager<Product> pager);
	
	public List<Product> getBestProductList(int maxResults);
	
	public List<Product> getBestProductList(ProductCategory productCategory, int maxResults);

	public List<Product> getHotProductList(int maxResults);

	public List<Product> getHotProductList(ProductCategory productCategory, int maxResults);

	public List<Product> getNewProductList(int maxResults);

	public List<Product> getNewProductList(ProductCategory productCategory, int maxResults);

	/**
	 * @param pager
	 * @return
	 */
	public Pager<Product> search(Pager<Product> pager);

	public Pager<Product> getFavoriteProductPager(Member member, Pager<Product> pager);
	
	public Long getStoreAlertCount();
	
	public Long getMarketableProductCount();

	public Long getUnMarketableProductCount();





}
