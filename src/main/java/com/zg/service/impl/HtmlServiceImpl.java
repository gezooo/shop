package com.zg.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.stereotype.Service;

import com.zg.beans.SystemConfig;
import com.zg.dao.ArticleDao;
import com.zg.dao.ProductDao;
import com.zg.entity.Article;
import com.zg.entity.Product;
import com.zg.service.ArticleCategoryService;
import com.zg.service.FooterService;
import com.zg.service.FriendLinkService;
import com.zg.service.HtmlService;
import com.zg.service.NavigationService;
import com.zg.service.ProductCategoryService;
import com.zg.util.SystemConfigUtil;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class HtmlServiceImpl implements HtmlService {

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
		ServletContext servletContext = ServletActionContext.getServletContext();
		Configuration configuration = freemarkerManager.getConfiguration(servletContext);
		try {
			Template template = configuration.getTemplate(templateFilePath);
			File htmlFile = new File(servletContext.getRealPath(htmlFilePath));
			File htmlDirectory = htmlFile.getParentFile();
			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		
	}
	
	public Map<String, Object> getCommonData() {
		Map<String, Object> commonData = new HashMap<String, Object>();
		ServletContext servletContext = ServletActionContext.getServletContext();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
		ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		
		String priceCurrencyFormat = SystemConfigUtil.getPriceCurrencyFormat();
		String priceUnitCurrencyFormat = SystemConfigUtil.getPriceUnitCurrencyFormat();
		
		String orderCurrencyFormat = SystemConfigUtil.getOrderCurrencyFormat();
		String orderUnitCurrencyFormat = SystemConfigUtil.getOrderUnitCurrencyFormat();
		
		commonData.put("bundle", resourceBundleModel);
		commonData.put("base", servletContext.getContextPath());
		commonData.put("systemConfig", systemConfig);
		commonData.put("priceCurrencyFormat", priceCurrencyFormat);
		commonData.put("priceUnitCurrencyFormat", priceUnitCurrencyFormat);
		commonData.put("orderCurrencyFormat", orderCurrencyFormat);
		commonData.put("orderUnitCurrencyFormat", orderUnitCurrencyFormat);
		commonData.put("topNavigationList", navigationService.getTopNavigationList());
		commonData.put("middleNavigationList", navigationService.getMiddleNavigationList());
		commonData.put("bottomNavigationList", navigationService.getBottomNavigationList());
		commonData.put("friendLinkList", friendLinkService.getAll());
		commonData.put("pictureFriendLinkList", friendLinkService.getPictureFriendLinkList());
		commonData.put("textFriendLinkList", friendLinkService.getTextFriendLinkList());
		commonData.put("footer", footerService.getFooter());
		return commonData;
	}
	
	@Override
	public void baseJavascriptBuildHtml() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void indexBuildHtml() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void loginBuildHtml() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void articleContentBuildHtml(Article article) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void productContentBuildHtml(Product product) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void errorPageBuildHtml() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void errorPageAccessDeniedBuildHtml() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void errorPage500BuildHtml() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void errorPage404BuildHtml() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void errorPage403BuildHtml() {
		// TODO Auto-generated method stub
		
	}
	
	
}
