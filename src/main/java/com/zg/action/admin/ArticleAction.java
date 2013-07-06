package com.zg.action.admin;

import java.util.List;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.entity.Article;
import com.zg.entity.ArticleCategory;
import com.zg.service.ArticleCategoryService;
import com.zg.service.ArticleService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 文章
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX8DDD613CBE6FE3988F4CE19EAC8724CB
 * ============================================================================
 */

@ParentPackage("admin")
public class ArticleAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;
	
	


	private Article article;
	private List<ArticleCategory> articleCategoryTreeList;
	
	@Resource
	private CacheManager cacheManager;

	@Resource
	private ArticleService articleService;
	@Resource
	private ArticleCategoryService articleCategoryService;
	
	protected Pager<Article> pager;
	
	public Pager<Article> getPager() {
		return pager;
	}

	public void setPager(Pager<Article> pager) {
		this.pager = pager;
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		article = articleService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = articleService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() throws Exception {
		articleService.delete(ids);
		flushCache();
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
			@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!"),
			@RequiredStringValidator(fieldName = "article.content", message = "文章内容不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "article.isPublication", message = "是否发布不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isTop", message = "是否置顶不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isRecommend", message = "是否推荐不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		article.setHits(0);
		articleService.save(article);
		flushCache();
		redirectionUrl = "article!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
			@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!"),
			@RequiredStringValidator(fieldName = "article.content", message = "文章内容不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "article.isPublication", message = "是否发布不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isTop", message = "是否置顶不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isRecommend", message = "是否推荐不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Article persistent = articleService.load(id);
		BeanUtils.copyProperties(article, persistent, new String[] {"id", "createDate", "modifyDate", "pageCount", "htmlFilePath", "hits"});
		articleService.update(persistent);
		flushCache();
		redirectionUrl = "article!list.action";
		return SUCCESS;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<ArticleCategory> getArticleCategoryTreeList() {
		articleCategoryTreeList = articleCategoryService.getArticleCategoryTreeList();
		return articleCategoryTreeList;
	}

	public void setArticleCategoryTreeList(List<ArticleCategory> articleCategoryTreeList) {
		this.articleCategoryTreeList = articleCategoryTreeList;
	}
	
	// 更新页面缓存
	private void flushCache() {
		Cache cache = cacheManager.getCache("SimplePageCachingFilter");
		cache.clear();
	}

}