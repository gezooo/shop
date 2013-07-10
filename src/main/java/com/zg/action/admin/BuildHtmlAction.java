package com.zg.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zg.entity.Article;
import com.zg.entity.ArticleCategory;
import com.zg.entity.Product;
import com.zg.entity.ProductCategory;
import com.zg.service.ArticleCategoryService;
import com.zg.service.ArticleService;
import com.zg.service.HtmlService;
import com.zg.service.ProductCategoryService;
import com.zg.service.ProductService;
import com.zg.util.EncacheCacheConfigUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.ParentPackage;


/**
 * 后台Action类 - 生成静态
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX5130C538F3343650399C2A81DC78CB1F
 * ============================================================================
 */

@ParentPackage("admin")
public class BuildHtmlAction extends BaseAdminAction {

	private static final long serialVersionUID = -2197609380142883572L;

	@Resource
	private ArticleService articleService;
	@Resource
	private ArticleCategoryService articleCategoryService;
	@Resource
	private ProductService productService;
	@Resource
	private ProductCategoryService productCategoryService;
	@Resource
	private HtmlService htmlService;
	
	private String buildType;// 更新类型
	private String buildContent;// 更新内容
	private Integer maxResults;// 每次更新数
	private Integer firstResult;// 更新文章起始结果数
	private Date beginDate;// 开始日期
	private Date endDate;// 结束日期
	
	public String allInput() {
		return "all_input";
	}
	
	@SuppressWarnings("deprecation")
	public String all() throws Exception {
		flushCache();
		if (StringUtils.isEmpty(buildType)) {
			buildType = "all";
		}
		if (StringUtils.isEmpty(buildContent)) {
			buildContent = "baseJavascript";
		}
		if (maxResults == null || maxResults < 0) {
			maxResults = 50;
		}
		if (firstResult == null || firstResult < 0) {
			firstResult = 0;
		}
		if (buildContent.equalsIgnoreCase("baseJavascript")) {
			htmlService.baseJavascriptBuildHtml();
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, "baseJavascriptFinish");
			jsonMap.put("buildTotal", "1");
			return ajaxJson(jsonMap);
		}
		if (buildContent.equalsIgnoreCase("errorPage")) {
			htmlService.errorPageBuildHtml();
			htmlService.errorPageAccessDeniedBuildHtml();
			htmlService.errorPage500BuildHtml();
			htmlService.errorPage404BuildHtml();
			htmlService.errorPage403BuildHtml();
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, "errorPageFinish");
			jsonMap.put("buildTotal", "1");
			return ajaxJson(jsonMap);
		}
		if (buildContent.equalsIgnoreCase("index")) {
			htmlService.indexBuildHtml();
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, "indexFinish");
			jsonMap.put("buildTotal", "1");
			return ajaxJson(jsonMap);
		}
		if (buildContent.equalsIgnoreCase("login")) {
			htmlService.loginBuildHtml();
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, "loginFinish");
			jsonMap.put("buildTotal", "1");
			return ajaxJson(jsonMap);
		}
		if (buildContent.equalsIgnoreCase("article")) {
			List<Article> articleList = null;
			if (buildType.equalsIgnoreCase("all")) {
				articleList = articleService.getArticleList(firstResult, maxResults);
			} else if(buildType.equalsIgnoreCase("date")) {
				if (beginDate != null) {
					beginDate.setHours(0);
					beginDate.setMinutes(0);
					beginDate.setSeconds(0);
				}
				if (endDate != null) {
					endDate.setHours(23);
					endDate.setMinutes(59);
					endDate.setSeconds(59);
				}
				articleList = articleService.getArticleList(beginDate, endDate, firstResult, maxResults);
			}
			if (articleList != null && articleList.size() > 0) {
				for (Article article : articleList) {
					htmlService.articleContentBuildHtml(article);
				}
			}
			if (articleList != null && articleList.size() == maxResults) {
				Map<String, String> jsonMap = new HashMap<String, String>();
				int nextFirstResult = firstResult + articleList.size();
				jsonMap.put(STATUS, "articleBuilding");
				jsonMap.put("firstResult", String.valueOf(nextFirstResult));
				return ajaxJson(jsonMap);
			} else {
				Map<String, String> jsonMap = new HashMap<String, String>();
				int buildTotal = firstResult + 1 + articleList.size();
				jsonMap.put(STATUS, "articleFinish");
				jsonMap.put("buildTotal", String.valueOf(buildTotal));
				return ajaxJson(jsonMap);
			}
		}
		if (buildContent.equalsIgnoreCase("product")) {
			List<Product> productList = null;
			if (buildType.equalsIgnoreCase("all")) {
				productList = productService.getProductList(firstResult, maxResults);
			} else if(buildType.equalsIgnoreCase("date")) {
				if (beginDate != null) {
					beginDate.setHours(0);
					beginDate.setMinutes(0);
					beginDate.setSeconds(0);
				}
				if (endDate != null) {
					endDate.setHours(23);
					endDate.setMinutes(59);
					endDate.setSeconds(59);
				}
				productList = productService.getProductList(beginDate, endDate, firstResult, maxResults);
			}
			if (productList != null && productList.size() > 0) {
				for (Product product : productList) {
					htmlService.productContentBuildHtml(product);
				}
			}
			if (productList != null && productList.size() == maxResults) {
				Map<String, String> jsonMap = new HashMap<String, String>();
				int nextFirstResult = firstResult + productList.size();
				jsonMap.put(STATUS, "productBuilding");
				jsonMap.put("firstResult", String.valueOf(nextFirstResult));
				return ajaxJson(jsonMap);
			} else {
				Map<String, String> jsonMap = new HashMap<String, String>();
				int buildTotal = firstResult + 1 + productList.size();
				jsonMap.put(STATUS, "productFinish");
				jsonMap.put("buildTotal", String.valueOf(buildTotal));
				return ajaxJson(jsonMap);
			}
		}
		return null;
	}
	
	public String articleInput() {
		return "article_input";
	}
	
	public String article() {
		flushCache();
		if (maxResults == null || maxResults < 0) {
			maxResults = 50;
		}
		if (firstResult == null || firstResult < 0) {
			firstResult = 0;
		}
		List<Article> articleList = new ArrayList<Article>();
		if (StringUtils.isEmpty(id)) {
			articleList = articleService.getArticleList(firstResult, maxResults);
		} else {
			ArticleCategory articleCategory = articleCategoryService.load(id);
			articleList = articleService.getArticleList(articleCategory, firstResult, maxResults);
		}
		if (articleList != null && articleList.size() > 0) {
			for (Article article : articleList) {
				htmlService.articleContentBuildHtml(article);
			}
		}
		if (articleList != null && articleList.size() == maxResults) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			int nextFirstResult = firstResult + articleList.size();
			jsonMap.put(STATUS, "ARTICLE_BUILDING");
			jsonMap.put("firstResult", String.valueOf(nextFirstResult));
			return ajaxJson(jsonMap);
		} else {
			Map<String, String> jsonMap = new HashMap<String, String>();
			int buildTotal = firstResult + 1 + articleList.size();
			jsonMap.put(STATUS, "ARTICLE_FINISH");
			jsonMap.put("buildTotal", String.valueOf(buildTotal));
			return ajaxJson(jsonMap);
		}
	}
	
	public String productInput() {
		return "product_input";
	}
	
	public String product() {
		flushCache();
		if (maxResults == null || maxResults < 0) {
			maxResults = 50;
		}
		if (firstResult == null || firstResult < 0) {
			firstResult = 0;
		}
		List<Product> productList = new ArrayList<Product>();
		if (StringUtils.isEmpty(id)) {
			productList = productService.getProductList(firstResult, maxResults);
		} else {
			ProductCategory productCategory = productCategoryService.load(id);
			productList = productService.getProductList(productCategory, firstResult, maxResults);
		}
		if (productList != null && productList.size() > 0) {
			for (Product product : productList) {
				htmlService.productContentBuildHtml(product);
			}
		}
		if (productList != null && productList.size() == maxResults) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			int nextFirstResult = firstResult + productList.size();
			jsonMap.put(STATUS, "PRODUCT_BUILDING");
			jsonMap.put("firstResult", String.valueOf(nextFirstResult));
			return ajaxJson(jsonMap);
		} else {
			Map<String, String> jsonMap = new HashMap<String, String>();
			int buildTotal = firstResult + 1 + productList.size();
			jsonMap.put(STATUS, "PRODUCT_FINISH");
			jsonMap.put("buildTotal", String.valueOf(buildTotal));
			return ajaxJson(jsonMap);
		}
	}
	
	// 更新页面缓存
	private void flushCache() {
		EncacheCacheConfigUtil.flushAll();
	}
	
	// 获取文章分类树
	public List<ArticleCategory> getArticleCategoryTreeList() {
		return articleCategoryService.getArticleCategoryTreeList();
	}
	
	// 获取商品分类树
	public List<ProductCategory> getProductCategoryTreeList() {
		return productCategoryService.getProductCategoryTreeList();
	}
	
	// 获取默认开始日期
	public Date getDefaultBeginDate() {
		return DateUtils.addDays(new Date(), -7);
	}

	// 获取默认结束日期
	public Date getDefaultEndDate() {
		return new Date();
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	public String getBuildContent() {
		return buildContent;
	}

	public void setBuildContent(String buildContent) {
		this.buildContent = buildContent;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	
	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}