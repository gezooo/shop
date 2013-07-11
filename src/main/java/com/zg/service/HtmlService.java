package com.zg.service;

import java.util.Map;

import com.zg.entity.Article;
import com.zg.entity.Product;

/*
* @author gez
* @version 0.1
*/

public interface HtmlService {
	
	/**
	 * @param templateFilePath the path of the template file
	 * @param htmlFilePath the path to store the generated html file
	 * @param data the map data which will be used in the template file.
	 */
	public void buildHtml(String templateFilePath, String htmlFilePath, Map<String, Object> data);
	
	public void baseJavascriptBuildHtml();

	public void indexBuildHtml();

	public void loginBuildHtml();

	public void articleContentBuildHtml(Article article);

	public void productContentBuildHtml(Product product);

	public void errorPageBuildHtml();

	public void errorPageAccessDeniedBuildHtml();

	public void errorPage500BuildHtml();

	public void errorPage404BuildHtml();

	public void errorPage403BuildHtml();



}
