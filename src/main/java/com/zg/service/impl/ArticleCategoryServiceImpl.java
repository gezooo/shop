package com.zg.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zg.action.admin.AdminAction;
import com.zg.common.util.CommonUtils;
import com.zg.dao.ArticleCategoryDao;
import com.zg.entity.Article;
import com.zg.entity.ArticleCategory;
import com.zg.service.ArticleCategoryService;

/*
* @author gez
* @version 0.1
*/

@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategory, String> implements ArticleCategoryService {

    public static final Logger logger = LoggerFactory.getLogger(ArticleCategoryServiceImpl.class);

	
	@Resource
	private ArticleCategoryDao articleCategoryDao;

	@Resource
	public void setBaseDao(ArticleCategoryDao articleCategoryDao) {
		super.setBaseDao(articleCategoryDao);
	}
	
	@Override
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public List<ArticleCategory> getRootArticleCategoryList() {
		logger.debug(CommonUtils.displayMessage(" called", null));
		List<ArticleCategory> rootArticleCategoryList = articleCategoryDao.getRootArticleCategoryList();
		if (rootArticleCategoryList != null) {
			for (ArticleCategory rootArticleCategory : rootArticleCategoryList) {
				Hibernate.initialize(rootArticleCategory);
			}
		}
		return rootArticleCategoryList;
	}

	@Override
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName + #articleCategory.id")
	public List<ArticleCategory> getParentArticleCategoryList(
			ArticleCategory articleCategory) {
		logger.debug(CommonUtils.displayMessage(" called", null));
		List<ArticleCategory> parentArticleCategoryList = articleCategoryDao.getParentArticleCategoryList(articleCategory);
		if (parentArticleCategoryList != null) {
			for (ArticleCategory parentArticleCategory : parentArticleCategoryList) {
				Hibernate.initialize(parentArticleCategory);
			}
		}
		return parentArticleCategoryList;
	}

	@Override
	public List<ArticleCategory> getParentArticleCategoryList(Article article) {
		ArticleCategory articleCategory = article.getArticleCategory();
		List<ArticleCategory> articleCategoryList = new ArrayList<ArticleCategory>();
		articleCategoryList.addAll(this.getParentArticleCategoryList(articleCategory));
		articleCategoryList.add(articleCategory);
		return articleCategoryList;
	}

	@Override
	public List<ArticleCategory> getArticleCategoryPathList(
			ArticleCategory articleCategory) {
		List<ArticleCategory> articleCategoryPathList = new ArrayList<ArticleCategory>();
		articleCategoryPathList.addAll(this.getParentArticleCategoryList(articleCategory));
		articleCategoryPathList.add(articleCategory);
		return articleCategoryPathList;
	}

	@Override
	public List<ArticleCategory> getArticleCategoryPathList(Article article) {
		ArticleCategory articleCategory = article.getArticleCategory();
		List<ArticleCategory> articleCategoryList = new ArrayList<ArticleCategory>();
		articleCategoryList.addAll(this.getParentArticleCategoryList(articleCategory));
		articleCategoryList.add(articleCategory);
		return articleCategoryList;
	}

	@Override
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName + #articleCategory.id")
	public List<ArticleCategory> getChildrenArticleCategoryList(
			ArticleCategory articleCategory) {
		logger.debug(CommonUtils.displayMessage(" called", null));
		List<ArticleCategory> childrenArticleCategoryList = articleCategoryDao.getChildrenArticleCategoryList(articleCategory);
		if (childrenArticleCategoryList != null) {
			for (ArticleCategory childrenArticleCategory : childrenArticleCategoryList) {
				Hibernate.initialize(childrenArticleCategory);
			}
		}
		return childrenArticleCategoryList;
	}

	@Override
	public List<ArticleCategory> getChildrenArticleCategoryList(Article article) {
		ArticleCategory articleCategory = article.getArticleCategory();
		List<ArticleCategory> articleCategoryList = getChildrenArticleCategoryList(articleCategory);
		if (articleCategoryList == null) {
			articleCategoryList = new ArrayList<ArticleCategory>();
		}
		articleCategoryList.add(articleCategory);
		return articleCategoryList;
	}

	@Override
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public List<ArticleCategory> getArticleCategoryTreeList() {
		logger.debug(CommonUtils.displayMessage(" called", null));
		List<ArticleCategory> allArticleCategoryList = this.getAll();
		return recursivArticleCategoryTreeList(allArticleCategoryList, null, null);
	}
	
	private List<ArticleCategory> recursivArticleCategoryTreeList(List<ArticleCategory> allArticleCategoryList, 
			ArticleCategory p, List<ArticleCategory> temp) {
		if (temp == null) {
			temp = new ArrayList<ArticleCategory>();
		}
		for (ArticleCategory articleCategory : allArticleCategoryList) {
			ArticleCategory parent = articleCategory.getParent();
			if ((p == null && parent == null) || (articleCategory != null && parent == p)) {
				temp.add(articleCategory);
				if (articleCategory.getChildren() != null && articleCategory.getChildren().size() > 0) {
					recursivArticleCategoryTreeList(allArticleCategoryList, articleCategory, temp);
				}
			}
		}
		return temp;
	}
	
	@Override
	@Cacheable(value="caching", key="#root.targetClass.name + #root.methodName")
	public List<ArticleCategory> getAll() {
		logger.debug(CommonUtils.displayMessage(" called", null));
		List<ArticleCategory> allArticleCategoryList = articleCategoryDao.getAll();
		if (allArticleCategoryList != null) {
			for (ArticleCategory articleCategory : allArticleCategoryList) {
				Hibernate.initialize(articleCategory);
			}
		}
		return allArticleCategoryList;
	}
	
	@Override
	@CacheEvict(value="caching", allEntries=true)
	public void delete(ArticleCategory articleCategory) {
		articleCategoryDao.delete(articleCategory);
	}

	@Override
	@CacheEvict(value="caching", allEntries=true)
	public void delete(String id) {
		articleCategoryDao.delete(id);
	}

	@Override
	@CacheEvict(value="caching", allEntries=true)
	public void delete(String[] ids) {
		articleCategoryDao.delete(ids);
	}

	@Override
	@CacheEvict(value="caching", allEntries=true)
	public String save(ArticleCategory articleCategory) {
		return articleCategoryDao.save(articleCategory);
	}

	@Override
	@CacheEvict(value="caching", allEntries=true)
	public void update(ArticleCategory articleCategory) {
		articleCategoryDao.update(articleCategory);
	}

}
