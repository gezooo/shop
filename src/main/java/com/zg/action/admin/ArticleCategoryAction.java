package com.zg.action.admin;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.zg.entity.Article;
import com.zg.entity.ArticleCategory;
import com.zg.service.ArticleCategoryService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 文章分类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX3BAD225777A5BDFDC6BF0F6474A05010
 * ============================================================================
 */

@ParentPackage("admin")
public class ArticleCategoryAction extends BaseAdminAction {

	private static final long serialVersionUID = -7786508966240073537L;

	private String parentId;
	private ArticleCategory articleCategory;
	private List<ArticleCategory> articleCategoryTreeList;

	@Resource
	private ArticleCategoryService articleCategoryService;

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		articleCategory = articleCategoryService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		return LIST;
	}

	// 删除
	public String delete() {
		ArticleCategory articleCategory = articleCategoryService.load(id);
		Set<ArticleCategory> childrenArticleCategoryList = articleCategory.getChildren();
		if (childrenArticleCategoryList != null && childrenArticleCategoryList.size() > 0) {
			addActionError("此文章分类存在下级分类，删除失败!");
			return ERROR;
		}
		Set<Article> articleSet = articleCategory.getArticleSet();
		if (articleSet != null && articleSet.size() > 0) {
			addActionError("此文章分类下存在文章，删除失败!");
			return ERROR;
		}
		articleCategoryService.delete(id);
		redirectionUrl = "article_category!list.action";
		return SUCCESS;
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "articleCategory.name", message = "分类名称不允许为空!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "articleCategory.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "articleCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (StringUtils.isNotEmpty(parentId)) {
			ArticleCategory parent = articleCategoryService.load(parentId);
			articleCategory.setParent(parent);
		} else {
			articleCategory.setParent(null);
		}
		articleCategoryService.save(articleCategory);
		redirectionUrl = "article_category!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "articleCategory.name", message = "分类名称不允许为空!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "articleCategory.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "articleCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		ArticleCategory persistent = articleCategoryService.load(id);
		BeanUtils.copyProperties(articleCategory, persistent, new String[]{"id", "createDate", "modifyDate", "path", "parent", "children", "articleSet"});
		articleCategoryService.update(persistent);
		redirectionUrl = "article_category!list.action";
		return SUCCESS;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	public List<ArticleCategory> getArticleCategoryTreeList() {
		articleCategoryTreeList = articleCategoryService.getArticleCategoryTreeList();
		return articleCategoryTreeList;
	}

	public void setArticleCategoryTreeList(List<ArticleCategory> articleCategoryTreeList) {
		this.articleCategoryTreeList = articleCategoryTreeList;
	}

}