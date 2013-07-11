package com.zg.service;

import java.util.Date;
import java.util.List;

import com.zg.beans.Pager;
import com.zg.entity.Article;
import com.zg.entity.ArticleCategory;

/*
* @author gez
* @version 0.1
*/

public interface ArticleService extends BaseService<Article, String> {
	
	public List<Article> getArticleList(ArticleCategory articleCategory);
	
	public List<Article> getArticleList(int firstResult, int maxResults);

	public List<Article> getArticleList(ArticleCategory articleCategory, int firstResult, int maxResults);

	public List<Article> getArticleList(Date beginDate, Date endDate, int firstResult, int maxResults);

	public Pager<Article> getArticlePager(ArticleCategory articleCategory, Pager<Article> pager);

	public List<Article> getRecommendArticleList(int maxResults);

	public List<Article> getRecommendArticleList(ArticleCategory articleCategory, int maxResults);

	public List<Article> getHotArticleList(int maxResults);

	public List<Article> getHotArticleList(ArticleCategory articleCategory, int maxResults);

	public List<Article> getNewArticleList(int maxResults);
	
	public List<Article> getNewArticleList(ArticleCategory articleCategory, int maxResults);
	
	public Pager<Article> search(Pager<Article> pager);




}
