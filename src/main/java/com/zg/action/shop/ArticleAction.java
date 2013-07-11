package com.zg.action.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.entity.Article;
import com.zg.entity.ArticleCategory;
import com.zg.service.ArticleCategoryService;
import com.zg.service.ArticleService;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 文章
 * @author gez
 * @version 0.1
 */

@ParentPackage("shop")
public class ArticleAction extends BaseShopAction {

	private static final long serialVersionUID = -25541236985328967L;
	
	private ArticleCategory articleCategory;
	private List<Article> recommendArticleList;
	private List<Article> hotArticleList;
	private List<Article> newArticleList;
	private List<ArticleCategory> pathList;

	@Resource
	private ArticleService articleService;
	@Resource
	private ArticleCategoryService articleCategoryService;

	// 文章分类列表
	@InputConfig(resultName = "error")
	public String list() {
		articleCategory = articleCategoryService.load(id);
		recommendArticleList = articleService.getRecommendArticleList(articleCategory, Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT);
		hotArticleList = articleService.getHotArticleList(articleCategory, Article.MAX_HOT_ARTICLE_LIST_COUNT);
		newArticleList = articleService.getNewArticleList(articleCategory, Article.MAX_NEW_ARTICLE_LIST_COUNT);
		pathList = articleCategoryService.getArticleCategoryPathList(articleCategory);
		
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Article.DEFAULT_ARTICLE_LIST_PAGE_SIZE);
		}
		pager.setProperty(null);
		pager.setKeywords(null);
		
		pager = articleService.getArticlePager(articleCategory, pager);
		return "list";
	}
	
	// 文章搜索
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "pager.keyword", message = "搜索关键词不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String search() throws Exception {
		recommendArticleList = articleService.getRecommendArticleList(Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT);
		hotArticleList = articleService.getHotArticleList(Article.MAX_HOT_ARTICLE_LIST_COUNT);
		newArticleList = articleService.getNewArticleList(Article.MAX_NEW_ARTICLE_LIST_COUNT);
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Article.DEFAULT_ARTICLE_LIST_PAGE_SIZE);
		}
		pager = articleService.search(pager);
		return "search";
	}
	
	// 文章点击统计
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "id", message = "文章ID不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String ajaxCounter() {
		Article article =  articleService.load(id);
		if (!article.getIsPublication()) {
			addActionError("您访问的文章尚未发布！");
			return ERROR;
		}
		Integer hits = article.getHits() + 1;
		article.setHits(hits);
		articleService.update(article);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put("hits", hits.toString());
		return ajaxJson(jsonMap);
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	public List<Article> getRecommendArticleList() {
		return recommendArticleList;
	}

	public void setRecommendArticleList(List<Article> recommendArticleList) {
		this.recommendArticleList = recommendArticleList;
	}

	public List<Article> getHotArticleList() {
		return hotArticleList;
	}

	public void setHotArticleList(List<Article> hotArticleList) {
		this.hotArticleList = hotArticleList;
	}

	public List<Article> getNewArticleList() {
		return newArticleList;
	}

	public void setNewArticleList(List<Article> newArticleList) {
		this.newArticleList = newArticleList;
	}

	public List<ArticleCategory> getPathList() {
		return pathList;
	}

	public void setPathList(List<ArticleCategory> pathList) {
		this.pathList = pathList;
	}

}