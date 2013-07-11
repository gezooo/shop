package com.zg.service;

import java.util.List;

import com.zg.entity.Article;
import com.zg.entity.ArticleCategory;

/*
* @author gez
* @version 0.1
*/

public interface ArticleCategoryService extends BaseService<ArticleCategory, String> {

	public List<ArticleCategory> getRootArticleCategoryList();
	
	public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory);
	
	public List<ArticleCategory> getParentArticleCategoryList(Article article);
	
	public List<ArticleCategory> getArticleCategoryPathList(ArticleCategory articleCategory);
	
	public List<ArticleCategory> getArticleCategoryPathList(Article article);
	
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory);

	public List<ArticleCategory> getChildrenArticleCategoryList(Article article);

	public List<ArticleCategory> getArticleCategoryTreeList();
	
	
	
	
	
	
}
