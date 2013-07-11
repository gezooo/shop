package com.zg.dao;

import java.util.List;

import com.zg.entity.ArticleCategory;

/*
* @author gez
* @version 0.1
*/

public interface ArticleCategoryDao extends BaseDao<ArticleCategory, String> {

	public List<ArticleCategory> getRootArticleCategoryList();
	
	public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory);
	
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory);



}
