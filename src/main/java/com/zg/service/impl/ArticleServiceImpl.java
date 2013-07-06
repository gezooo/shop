package com.zg.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.struts2.ServletActionContext;

import org.hibernate.Transaction;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zg.beans.HtmlConfig;
import com.zg.beans.Pager;
import com.zg.dao.ArticleDao;
import com.zg.entity.Article;
import com.zg.entity.ArticleCategory;
import com.zg.search.HibernateSearchTemplate;
import com.zg.search.SearchCallback;
import com.zg.search.SearchCriterial;
import com.zg.service.ArticleService;
import com.zg.service.HtmlService;
import com.zg.util.CommonUtil;
import com.zg.util.TemplateConfigUtil;


@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article, String> implements ArticleService {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Resource
	private ArticleDao articleDao;
	
	@Resource
	private HtmlService htmlService;
	
	@Resource
	private HibernateSearchTemplate hibernateSearchTemplate;
	
	@Resource
	public void setBaseDao(ArticleDao articleDao) {
		super.setBaseDao(articleDao);
	}
	
	public List<Article> getArticleList(ArticleCategory articleCategory) {
		return articleDao.getArticleList(articleCategory);
	}
	
	public List<Article> getArticleList(int firstResult, int maxResults) {
		return articleDao.getArticleList(firstResult, maxResults);
	}
	
	public List<Article> getArticleList(Date beginDate, Date endDate, int firstResult, int maxResults) {
		return articleDao.getArticleList(beginDate, endDate, firstResult, maxResults);
	}
	
	public List<Article> getArticleList(ArticleCategory articleCategory, int firstResult, int maxResults) {
		return articleDao.getArticleList(articleCategory, firstResult, maxResults);
	}
	
	public Pager<Article> getArticlePager(ArticleCategory articleCategory, Pager<Article> pager) {
		return articleDao.getArticlePager(articleCategory, pager);
	}
	
	public List<Article> getRecommendArticleList(int maxResults) {
		return articleDao.getRecommendArticleList(maxResults);
	}

	public List<Article> getRecommendArticleList(ArticleCategory articleCategory, int maxResults) {
		return articleDao.getRecommendArticleList(articleCategory, maxResults);
	}
	
	public List<Article> getHotArticleList(int maxResults) {
		return articleDao.getHotArticleList(maxResults);
	}

	public List<Article> getHotArticleList(ArticleCategory articleCategory, int maxResults) {
		return articleDao.getHotArticleList(articleCategory, maxResults);
	}
	
	public List<Article> getNewArticleList(int maxResults) {
		return articleDao.getNewArticleList(maxResults);
	}

	public List<Article> getNewArticleList(ArticleCategory articleCategory, int maxResults) {
		return articleDao.getNewArticleList(articleCategory, maxResults);
	}
	
	public Pager<Article> search(final Pager<Article> pager) {
		
		QueryBuilder qb = hibernateSearchTemplate.getQueryBuilder(Article.class);

		org.apache.lucene.search.Query query = qb.bool()
				.must(qb.keyword().onField("isPublication").matching(true).createQuery())
				.must(qb.keyword().onField("title").matching(pager.getKeywords()).createQuery()).createQuery();
				 
		
		SearchCallback<Article> searchCallBack = new SearchCallback<Article>() {

			@Override
			public void onFinishSearch(List<Article> results, int totalResultSize) {
				pager.setList(results);
				pager.setTotalCount(totalResultSize);
			}
			
		};
		List<Sort> sorts = new ArrayList<Sort>();
		Sort isTopSort = new Sort(
			    new SortField("isTop", SortField.STRING, true));
		sorts.add(isTopSort);
		Sort hitsSort = new Sort(
			    new SortField("hits", SortField.INT));
		sorts.add(hitsSort);
		int firstResult = (pager.getPageNumber() - 1) * pager.getPageSize();
		hibernateSearchTemplate.search(query, sorts, firstResult, pager.getPageSize(), searchCallBack, Article.class);
		
		return pager;
		
	}
	
	public void delete(Article article) {
		List<String> htmlFilePathList = article.getHtmlFilePathList();
		if(htmlFilePathList != null && htmlFilePathList.size() > 0) {
			for(String htmlFilePath : htmlFilePathList) {
				File htmlFile = new File(ServletActionContext.getServletContext().getRealPath(htmlFilePath));
				if(htmlFile.exists()) {
					htmlFile.delete();
				}
			}
		}
		articleDao.delete(article);
	} 
	
	@Override
	public void delete(String id) {
		Article article = articleDao.load(id);
		this.delete(article);
	}
	
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}
	
	public String save(Article article) {
		logger.debug(CommonUtil.displayMessage("Called", null));
		
		article.setPageCount(0);
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ARTICLE_CONTENT);
		logger.debug(CommonUtil.displayMessage(" htmlConfig " + htmlConfig.getHtmlFilePath(), null));
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		logger.debug(CommonUtil.displayMessage(" htmlConfig " + htmlConfig.getHtmlFilePath(), null));
		article.setHtmlFilePath(htmlFilePath);
		
		String id = articleDao.save(article);
		articleDao.flush();
		articleDao.evict(article);
		article = articleDao.load(id);
		if (article.getIsPublication()) {
			
			logger.debug(CommonUtil.displayMessage(" articleContentBuildHtml ", null));

			htmlService.articleContentBuildHtml(article);
			logger.debug(CommonUtil.displayMessage(" articleContentBuildHtml finished ", null));

		}
		
	
		return id;
		
	}
	
	// 重写方法，更新对象的同时重新生成HTML静态文件
	@Override
	public void update(Article article) {
		List<String> htmlFilePathList = article.getHtmlFilePathList();
		if (htmlFilePathList != null && htmlFilePathList.size() > 0) {
			for (String htmlFilePath : htmlFilePathList) {
				File htmlFile = new File(ServletActionContext.getServletContext().getRealPath(htmlFilePath));
				if (htmlFile.exists()) {
					htmlFile.delete();
				}
			}
		}
		String id = article.getId();
		articleDao.update(article);
		articleDao.flush();
		articleDao.evict(article);
		article = articleDao.load(id);
		if (article.getIsPublication()) {
			htmlService.articleContentBuildHtml(article);
		}
	}
}
