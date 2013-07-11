package com.zg.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.struts2.ServletActionContext;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

import com.zg.beans.HtmlConfig;
import com.zg.beans.Pager;
import com.zg.beans.Pager.OrderType;
import com.zg.beans.ProductImage;
import com.zg.common.util.TemplateConfigUtils;
import com.zg.dao.ProductDao;
import com.zg.entity.Article;
import com.zg.entity.Member;
import com.zg.entity.Product;
import com.zg.entity.ProductCategory;
import com.zg.search.HibernateSearchTemplate;
import com.zg.search.SearchCallback;
import com.zg.service.HtmlService;
import com.zg.service.ProductService;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService {

	@Resource
	private ProductDao productDao;
	@Resource
	private HibernateSearchTemplate hibernateSearchTemplate;
	@Resource
	private HtmlService htmlService;

	@Resource
	public void setBaseDao(ProductDao productDao) {
		super.setBaseDao(productDao);
	}
	
	public List<Product> getProductList(ProductCategory productCategory) {
		return productDao.getProductList(productCategory);
	}
	
	public List<Product> getProductList(int firstResult, int maxResults) {
		return productDao.getProductList(firstResult, maxResults);
	}
	
	public List<Product> getProductList(ProductCategory productCategory, int firstResult, int maxResults) {
		return productDao.getProductList(productCategory, firstResult, maxResults);
	}
	
	public Pager<Product> getProductPager(ProductCategory productCategory, Pager<Product> pager) {
		return productDao.getProductPager(productCategory, pager);
	}
	
	public List<Product> getBestProductList(int maxResults) {
		return productDao.getBestProductList(maxResults);
	}

	public List<Product> getBestProductList(ProductCategory productCategory, int maxResults) {
		return productDao.getBestProductList(productCategory, maxResults);
	}
	
	public List<Product> getHotProductList(int maxResults) {
		return productDao.getHotProductList(maxResults);
	}

	public List<Product> getHotProductList(ProductCategory productCategory, int maxResults) {
		return productDao.getHotProductList(productCategory, maxResults);
	}
	
	public List<Product> getNewProductList(int maxResults) {
		return productDao.getNewProductList(maxResults);
	}

	public List<Product> getNewProductList(ProductCategory productCategory, int maxResults) {
		return productDao.getNewProductList(productCategory, maxResults);
	}
	
	public List<Product> getProductList(Date beginDate, Date endDate, int firstResult, int maxResults) {
		return productDao.getProductList(beginDate, endDate, firstResult, maxResults);
	}
	
	/*
	public Pager<Product> search(Pager<Product> pager) {
		
		//it need to be optimized, get the compassHit in the cache if it already exit.
		Compass compass = compassTemplate.getCompass();
		CompassSession compassSession = compass.openSession();
		CompassQueryBuilder compassQueryBuilder = compassSession.queryBuilder();
		CompassBooleanQueryBuilder compassBooleanQueryBuilder = compassQueryBuilder.bool();
		CompassQuery compassQuery = compassBooleanQueryBuilder.addMust(compassQueryBuilder.term("isMarketable", true))
				.addMust(compassQueryBuilder.queryString("name:*" + pager.getKeywords() + "*").toQuery()).toQuery();

		if(StringUtils.isEmpty(pager.getOrderBy()) || pager.getOrderType() == null) {
			compassQuery.addSort("isBest", SortPropertyType.STRING, SortDirection.REVERSE)
			.addSort("isNew", SortPropertyType.STRING, SortDirection.REVERSE)
			.addSort("isHot", SortPropertyType.STRING, SortDirection.REVERSE);
		} else {
			if (pager.getOrderType() == OrderType.ASC) {
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "price")) {
					compassQuery.addSort("price", SortPropertyType.FLOAT);
				}
			} else {
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "price")) {
					compassQuery.addSort("price", SortPropertyType.FLOAT, SortDirection.REVERSE);
				}
			}
		}
		
		CompassHits compassHits = compassQuery.hits();
		List<Product> productList = new ArrayList<Product>();

		int firstResult = (pager.getPageNumber() - 1) * pager.getPageSize();
		int maxReasults = pager.getPageSize();
		int totalCount = compassHits.length();

		int end = Math.min(totalCount, firstResult + maxReasults);
		for (int i = firstResult; i < end; i++) {
			Product product = (Product) compassHits.data(i);
			productList.add(product);
		}
		compassSession.close();
		pager.setList(productList);
		pager.setTotalCount(totalCount);
		return pager;

	}
	
	*/
	
	
	public Pager<Product> search(final Pager<Product> pager) {
		
		QueryBuilder qb = hibernateSearchTemplate.getQueryBuilder(Article.class);
		
		org.apache.lucene.search.Query query = qb.bool()
				.must(qb.keyword().onField("isMarketable").matching(true).createQuery())
				.must(qb.keyword().onField("name").matching(pager.getKeywords()).createQuery()).createQuery();
				 
		
		SearchCallback<Product> searchCallBack = new SearchCallback<Product>() {

			@Override
			public void onFinishSearch(List<Product> results, int totalResultSize) {
				pager.setList(results);
				pager.setTotalCount(totalResultSize);
			}
			
		};
		
		List<Sort> sorts = new ArrayList<Sort>();
		
		if(StringUtils.isEmpty(pager.getOrderBy()) || pager.getOrderType() == null) {
			
			Sort isBestSort = new Sort(
				    new SortField("isBest", SortField.STRING, true));
			sorts.add(isBestSort);
			Sort isNewSort = new Sort(
				    new SortField("isNew", SortField.STRING, true));
			sorts.add(isNewSort);
			Sort isHotSort = new Sort(
				    new SortField("isHot", SortField.STRING, true));
			sorts.add(isHotSort);
			
		} else {
			
			if (pager.getOrderType() == OrderType.ASC) {
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "price")) {
					Sort priceSort = new Sort(
						    new SortField("price", SortField.FLOAT));
					sorts.add(priceSort);
				}
			} else {
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "price")) {
					Sort priceSort = new Sort(
						    new SortField("price", SortField.FLOAT, true));
					sorts.add(priceSort);
				}
			}
		}

		int firstResult = (pager.getPageNumber() - 1) * pager.getPageSize();
		hibernateSearchTemplate.search(query, sorts, firstResult, pager.getPageSize(), searchCallBack, Product.class);
		
		return pager;

	}
	
	public Pager<Product> getFavoriteProductPager(Member member, Pager<Product> pager) {
		return productDao.getFavoriteProductPager(member, pager);
	}
	
	public Long getStoreAlertCount() {
		return productDao.getStoreAlertCount();
	}
	
	public Long getMarketableProductCount() {
		return productDao.getMarketableProductCount();
	}
	
	public Long getUnMarketableProductCount() {
		return productDao.getUnMarketableProductCount();
	}
	
	// 重写方法，删除对象的同时删除已生成的HTML静态文件、商品图片文件
		@Override
		public void delete(Product product) {
			File htmlFile = new File(ServletActionContext.getServletContext().getRealPath(product.getHtmlFilePath()));
			if (htmlFile.exists()) {
				htmlFile.delete();
			}
			List<ProductImage> productImageList = product.getProductImageList();
			if (productImageList != null) {
				for (ProductImage productImage : productImageList) {
					File sourceProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getSourceProductImagePath()));
					if (sourceProductImageFile.exists()) {
						sourceProductImageFile.delete();
					}
					File bigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getBigProductImagePath()));
					if (bigProductImageFile.exists()) {
						bigProductImageFile.delete();
					}
					File smallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getSmallProductImagePath()));
					if (smallProductImageFile.exists()) {
						smallProductImageFile.delete();
					}
					File thumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getThumbnailProductImagePath()));
					if (thumbnailProductImageFile.exists()) {
						thumbnailProductImageFile.delete();
					}
				}
			}
			productDao.delete(product);
		}

		// 重写方法，删除对象的同时删除已生成的HTML静态文件、商品图片文件
		@Override
		public void delete(String id) {
			Product product = productDao.load(id);
			this.delete(product);
		}

		// 重写方法，删除对象的同时删除已生成的HTML静态文件、商品图片文件
		@Override
		public void delete(String[] ids) {
			for (String id : ids) {
				this.delete(id);
			}
		}

		// 重写方法，保存对象的同时处理价格精度并生成HTML静态文件
		@Override
		public String save(Product product) {
			HtmlConfig htmlConfig = TemplateConfigUtils.getHtmlConfig(HtmlConfig.PRODUCT_CONTENT);
			String htmlFilePath = htmlConfig.getHtmlFilePath();
			product.setHtmlFilePath(htmlFilePath);
			String id = productDao.save(product);
			productDao.flush();
			productDao.evict(product);
			product = productDao.load(id);
			if (product.getIsMarketable()) {
				htmlService.productContentBuildHtml(product);
			}
			return id;
		}

		// 重写方法，更新对象的同时处理价格精度并重新生成HTML静态文件
		@Override
		public void update(Product product) {
			String id = product.getId();
			File htmlFile = new File(ServletActionContext.getServletContext().getRealPath(product.getHtmlFilePath()));
			if (htmlFile.exists()) {
				htmlFile.delete();
			}
			productDao.update(product);
			productDao.flush();
			productDao.evict(product);
			product = productDao.load(id);
			if (product.getIsMarketable()) {
				htmlService.productContentBuildHtml(product);
			}
		}
}
