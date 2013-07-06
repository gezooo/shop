package com.zg.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zg.beans.HtmlConfig;
import com.zg.beans.SystemConfig;
import com.zg.dao.ArticleDao;
import com.zg.dao.ProductDao;
import com.zg.entity.Article;
import com.zg.entity.ArticleCategory;
import com.zg.entity.Product;
import com.zg.entity.ProductCategory;
import com.zg.service.ArticleCategoryService;
import com.zg.service.FooterService;
import com.zg.service.FriendLinkService;
import com.zg.service.HtmlService;
import com.zg.service.NavigationService;
import com.zg.service.ProductCategoryService;
import com.zg.util.CommonUtil;
import com.zg.util.SystemConfigUtil;
import com.zg.util.TemplateConfigUtil;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class HtmlServiceImpl implements HtmlService {
	
	private static final Logger logger = LoggerFactory.getLogger(HtmlServiceImpl.class);

	@Resource
	private FreemarkerManager freemarkerManager;
	@Resource
	private NavigationService navigationService;
	@Resource
	private FriendLinkService friendLinkService;
	@Resource
	private FooterService footerService;
	@Resource
	private ArticleCategoryService articleCategoryService;
	@Resource
	private ArticleDao articleDao;
	@Resource
	ProductCategoryService productCategoryService;
	@Resource
	private ProductDao productDao;
	
	
	@Override
	public void buildHtml(String templateFilePath, String htmlFilePath,
			Map<String, Object> data) {
		logger.debug(CommonUtil.displayMessage("Called", null));
		ServletContext servletContext = ServletActionContext.getServletContext();
		logger.debug(CommonUtil.displayMessage("ServletActionContext.getServletContext()", null));
		logger.debug(CommonUtil.displayMessage(servletContext.getContextPath(), null));


		Configuration configuration = freemarkerManager.getConfiguration(servletContext);
		logger.debug(CommonUtil.displayMessage("freemarkerManager.getConfiguration", null));

		try {
			Template template = configuration.getTemplate(templateFilePath);
			logger.debug(CommonUtil.displayMessage("configuration.getTemplate", null));

			File htmlFile = new File(servletContext.getRealPath(htmlFilePath));
			logger.debug(CommonUtil.displayMessage("File(servletContext", null));

			File htmlDirectory = htmlFile.getParentFile();
			logger.debug(CommonUtil.displayMessage("htmlFile.getParentFile", null));

			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			logger.debug(CommonUtil.displayMessage("htmlDirectory.mkdirs", null));

			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			logger.debug(CommonUtil.displayMessage("new BufferedWriter", null));

			template.process(data, out);
			logger.debug(CommonUtil.displayMessage("template.process", null));

			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("IOException" + e.getMessage());
		} catch (TemplateException e) {
			logger.error("TemplateException" + e.getMessage());
		}

		
	}
	
	public Map<String, Object> getCommonData() {
		Map<String, Object> commonData = new HashMap<String, Object>();
		ServletContext servletContext = ServletActionContext.getServletContext();
		logger.debug(CommonUtil.displayMessage("Called 1", null));

		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
		logger.debug(CommonUtil.displayMessage("Called 2", null));

		ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		logger.debug(CommonUtil.displayMessage("Called 3", null));

		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		
		logger.debug(CommonUtil.displayMessage("Called 4", null));

		
		String priceCurrencyFormat = SystemConfigUtil.getPriceCurrencyFormat();
		
		logger.debug(CommonUtil.displayMessage("Called 5", null));

		String priceUnitCurrencyFormat = SystemConfigUtil.getPriceUnitCurrencyFormat();
		
		logger.debug(CommonUtil.displayMessage("Called 6", null));

		
		String orderCurrencyFormat = SystemConfigUtil.getOrderCurrencyFormat();
		
		logger.debug(CommonUtil.displayMessage("Called 7", null));

		String orderUnitCurrencyFormat = SystemConfigUtil.getOrderUnitCurrencyFormat();
		
		logger.debug(CommonUtil.displayMessage("Called 8", null));

		
		commonData.put("bundle", resourceBundleModel);
		commonData.put("base", servletContext.getContextPath());
		logger.debug(CommonUtil.displayMessage("Called 9", null));

		commonData.put("systemConfig", systemConfig);
		commonData.put("priceCurrencyFormat", priceCurrencyFormat);
		commonData.put("priceUnitCurrencyFormat", priceUnitCurrencyFormat);
		commonData.put("orderCurrencyFormat", orderCurrencyFormat);
		commonData.put("orderUnitCurrencyFormat", orderUnitCurrencyFormat);
		commonData.put("topNavigationList", navigationService.getTopNavigationList());
		
		logger.debug(CommonUtil.displayMessage("Called 10", null));

		commonData.put("middleNavigationList", navigationService.getMiddleNavigationList());
		
		logger.debug(CommonUtil.displayMessage("Called 11", null));

		commonData.put("bottomNavigationList", navigationService.getBottomNavigationList());
		logger.debug(CommonUtil.displayMessage("Called 12", null));

		commonData.put("friendLinkList", friendLinkService.getAll());
		
		logger.debug(CommonUtil.displayMessage("Called 13", null));

		commonData.put("pictureFriendLinkList", friendLinkService.getPictureFriendLinkList());
		logger.debug(CommonUtil.displayMessage("Called 14", null));

		commonData.put("textFriendLinkList", friendLinkService.getTextFriendLinkList());
		logger.debug(CommonUtil.displayMessage("Called 15", null));

		commonData.put("footer", footerService.getFooter());
		logger.debug(CommonUtil.displayMessage("Called 16", null));

		return commonData;
	}
	
	@Override
	public void baseJavascriptBuildHtml() {
		Map<String, Object> data = getCommonData();
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.BASE_JAVASCRIPT);
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);		
	}
	
	@Override
	public void indexBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.INDEX);
		Map<String, Object> data = getCommonData();
		data.put("rootProductCategoryList", productCategoryService.getRootProductCategoryList());
		data.put("bestProductList", productDao.getBestProductList(Product.MAX_BEST_PRODUCT_LIST_COUNT));
		data.put("hotProductList", productDao.getHotProductList(Product.MAX_HOT_PRODUCT_LIST_COUNT));
		data.put("newProductList", productDao.getNewProductList(Product.MAX_NEW_PRODUCT_LIST_COUNT));
		List<ProductCategory> allProductCategory = productCategoryService.getAll();
		data.put("allProductCategoryList", allProductCategory);
		Map<String, List<ProductCategory>> productCategoryMap = new HashMap<String, List<ProductCategory>>();
		Map<String, List<Product>> bestProductMap = new HashMap<String, List<Product>>();
		Map<String, List<Product>> hotProductMap = new HashMap<String, List<Product>>();
		Map<String, List<Product>> newProductMap = new HashMap<String, List<Product>>();
		for (ProductCategory productCategory : allProductCategory) {
			productCategoryMap.put(productCategory.getId(), productCategoryService.getChildrenProductCategoryList(productCategory));
			bestProductMap.put(productCategory.getId(), productDao.getBestProductList(productCategory, Product.MAX_BEST_PRODUCT_LIST_COUNT));
			hotProductMap.put(productCategory.getId(), productDao.getHotProductList(productCategory, Product.MAX_HOT_PRODUCT_LIST_COUNT));
			newProductMap.put(productCategory.getId(), productDao.getNewProductList(productCategory, Product.MAX_NEW_PRODUCT_LIST_COUNT));
		}
		data.put("productCategoryMap", productCategoryMap);
		data.put("bestProductMap", bestProductMap);
		data.put("hotProductMap", hotProductMap);
		data.put("newProductMap", newProductMap);
		
		data.put("rootArticleCategoryList", articleCategoryService.getRootArticleCategoryList());
		data.put("recommendArticleList", articleDao.getRecommendArticleList(Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT));
		data.put("hotArticleList", articleDao.getHotArticleList(Article.MAX_HOT_ARTICLE_LIST_COUNT));
		data.put("newArticleList", articleDao.getNewArticleList(Article.MAX_NEW_ARTICLE_LIST_COUNT));
		List<ArticleCategory> allArticleCategory = articleCategoryService.getAll();
		data.put("allArticleCategoryList", allArticleCategory);
		Map<String, List<ArticleCategory>> articleCategoryMap = new HashMap<String, List<ArticleCategory>>();
		Map<String, List<Article>> recommendArticleMap = new HashMap<String, List<Article>>();
		Map<String, List<Article>> hotArticleMap = new HashMap<String, List<Article>>();
		Map<String, List<Article>> newArticleMap = new HashMap<String, List<Article>>();
		for (ArticleCategory articleCategory : allArticleCategory) {
			articleCategoryMap.put(articleCategory.getId(), articleCategoryService.getChildrenArticleCategoryList(articleCategory));
			recommendArticleMap.put(articleCategory.getId(), articleDao.getRecommendArticleList(articleCategory, Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT));
			hotArticleMap.put(articleCategory.getId(), articleDao.getHotArticleList(articleCategory, Article.MAX_HOT_ARTICLE_LIST_COUNT));
			newArticleMap.put(articleCategory.getId(), articleDao.getNewArticleList(articleCategory, Article.MAX_NEW_ARTICLE_LIST_COUNT));
		}
		data.put("articleCategoryMap", articleCategoryMap);
		data.put("recommendArticleMap", recommendArticleMap);
		data.put("hotArticleMap", hotArticleMap);
		data.put("newArticleMap", newArticleMap);
		
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
		
	}
	
	@Override
	public void loginBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.LOGIN);
		Map<String, Object> data = getCommonData();
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);		
	}
	
	@Override
	public void articleContentBuildHtml(Article article) {
		logger.debug(CommonUtil.displayMessage("Called", null));
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ARTICLE_CONTENT);
		logger.debug(CommonUtil.displayMessage("after TemplateConfigUtil.getHtmlConfig", null));

		ArticleCategory articleCategory = article.getArticleCategory();
		logger.debug(CommonUtil.displayMessage("after article.getArticleCategory", null));

		Map<String, Object> data = getCommonData();
		logger.debug(CommonUtil.displayMessage("after getCommonData", null));

		data.put("article", article);
		data.put("pathList", articleCategoryService.getArticleCategoryPathList(article));
		logger.debug(CommonUtil.displayMessage("after pathList", null));

		data.put("rootArticleCategoryList", articleCategoryService.getRootArticleCategoryList());
		logger.debug(CommonUtil.displayMessage("after rootArticleCategoryList", null));

		data.put("recommendArticleList", articleDao.getRecommendArticleList(articleCategory, Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT));
		logger.debug(CommonUtil.displayMessage("after recommendArticleList", null));

		data.put("hotArticleList", articleDao.getHotArticleList(articleCategory, Article.MAX_HOT_ARTICLE_LIST_COUNT));
		logger.debug(CommonUtil.displayMessage("after hotArticleList", null));

		data.put("newArticleList", articleDao.getNewArticleList(articleCategory, Article.MAX_NEW_ARTICLE_LIST_COUNT));
		logger.debug(CommonUtil.displayMessage("after newArticleList", null));

		String htmlFilePath = article.getHtmlFilePath();
		logger.debug(CommonUtil.displayMessage("after getHtmlFilePath", null));

		String prefix = StringUtils.substringBeforeLast(htmlFilePath, ".");
		logger.debug(CommonUtil.displayMessage("after prefix", null));

		String extension = StringUtils.substringAfterLast(htmlFilePath, ".");
		logger.debug(CommonUtil.displayMessage("after extension", null));

		
		List<String> pageContentList = article.getPageContentList();
		logger.debug(CommonUtil.displayMessage("after pageContentList", null));

		article.setPageCount(pageContentList.size());
		
		logger.debug(CommonUtil.displayMessage("after article.setPageCount", null));

		articleDao.update(article);
		
		logger.debug(CommonUtil.displayMessage("after articleDao.update", null));

		articleDao.flush();
		logger.debug(CommonUtil.displayMessage("after articleDao.flush", null));

		for (int i = 0; i < pageContentList.size(); i++) {
			data.put("content", pageContentList.get(i));
			data.put("pageNumber", i + 1);
			data.put("pageCount", pageContentList.size());
			String templateFilePath = htmlConfig.getTemplateFilePath();
			logger.debug(CommonUtil.displayMessage("after getTemplateFilePath", null));

			String currentHtmlFilePath = null;
			if (i == 0) {
				currentHtmlFilePath = htmlFilePath;
			} else {
				currentHtmlFilePath = prefix + "_" + (i + 1) + "." + extension;
			}
			logger.debug(CommonUtil.displayMessage("after currentHtmlFilePath" + currentHtmlFilePath, null));

			buildHtml(templateFilePath, currentHtmlFilePath, data);
			logger.debug(CommonUtil.displayMessage("after buildHtml" + currentHtmlFilePath, null));

		}		
	}
	
	@Override
	public void productContentBuildHtml(Product product) {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.PRODUCT_CONTENT);
		ProductCategory productCategory = product.getProductCategory();
		Map<String, Object> data = getCommonData();
		data.put("product", product);
		data.put("pathList", productCategoryService.getProductCategoryPathList(product));
		data.put("rootProductCategoryList", productCategoryService.getRootProductCategoryList());
		data.put("bestProductList", productDao.getBestProductList(productCategory, Product.MAX_BEST_PRODUCT_LIST_COUNT));
		data.put("hotProductList", productDao.getHotProductList(productCategory, Product.MAX_HOT_PRODUCT_LIST_COUNT));
		data.put("newProductList", productDao.getNewProductList(productCategory, Product.MAX_NEW_PRODUCT_LIST_COUNT));
		String htmlFilePath = product.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);		
	}
	
	@Override
	public void errorPageBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ERROR_PAGE);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "ç³»ç»Ÿå‡ºçŽ°å¼‚å¸¸ï¼Œè¯·ä¸Žç®¡ç�†å‘˜è�”ç³»ï¼�");
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
		
	}
	
	@Override
	public void errorPageAccessDeniedBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ERROR_PAGE);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "æ‚¨æ— æ­¤è®¿é—®æ�ƒé™�ï¼�");
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
		
	}
	
	@Override
	public void errorPage500BuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ERROR_PAGE_500);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "ç³»ç»Ÿå‡ºçŽ°å¼‚å¸¸ï¼Œè¯·ä¸Žç®¡ç�†å‘˜è�”ç³»ï¼�");
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
		
	}
	
	@Override
	public void errorPage404BuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ERROR_PAGE_404);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "æ‚¨è®¿é—®çš„é¡µé�¢ä¸�å­˜åœ¨ï¼�");
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}
	
	@Override
	public void errorPage403BuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ERROR_PAGE_403);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "ç³»ç»Ÿå‡ºçŽ°å¼‚å¸¸ï¼Œè¯·ä¸Žç®¡ç�†å‘˜è�”ç³»ï¼�");
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}
	
	
}
