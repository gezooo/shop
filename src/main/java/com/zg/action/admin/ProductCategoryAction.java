package com.zg.action.admin;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.zg.entity.Product;
import com.zg.entity.ProductCategory;
import com.zg.service.ProductCategoryService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 商品分类
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class ProductCategoryAction extends BaseAdminAction {

	private static final long serialVersionUID = 3066159260207583127L;
    public static final Logger logger = LoggerFactory.getLogger(ProductCategoryAction.class);

	private String parentId;
	private ProductCategory productCategory;
	private List<ProductCategory> productCategoryTreeList;

	@Resource
	private ProductCategoryService productCategoryService;

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		productCategory = productCategoryService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		return LIST;
	}

	// 删除
	public String delete() {
		ProductCategory productCategory = productCategoryService.load(id);
		Set<ProductCategory> childrenProductCategorySet = productCategory.getChildren();
		redirectionUrl = "product_category!list.action";
		if (childrenProductCategorySet != null && childrenProductCategorySet.size() > 0) {
			addActionError("此商品分类存在下级分类，删除失败!");
			return ERROR;
		}
		Set<Product> productSet = productCategory.getProductSet();
		if (productSet != null && productSet.size() > 0) {
			addActionError("此商品分类下存在商品，删除失败!");
			return ERROR;
		}
		productCategoryService.delete(id);
		return SUCCESS;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "productCategory.name", message = "分类名称不允许为空!")
		}, 
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "productCategory.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "productCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		logger.debug("save called");
		if (StringUtils.isNotEmpty(parentId)) {
			ProductCategory parent = productCategoryService.load(parentId);
			productCategory.setParent(parent);
		} else {
			productCategory.setParent(null);
		}
		logger.debug("productCategoryService save called");
		productCategoryService.save(productCategory);
		logger.debug("after productCategoryService save called");
		redirectionUrl = "product_category!list.action";
		logger.debug("save end");
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "productCategory.name", message = "分类名称不允许为空!")
		}, 
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "productCategory.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "productCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		ProductCategory persistent = productCategoryService.load(id);
		BeanUtils.copyProperties(productCategory, persistent, new String[]{"id", "createDate", "modifyDate", "path", "parent", "children", "productSet"});
		productCategoryService.update(persistent);
		redirectionUrl = "product_category!list.action";
		return SUCCESS;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<ProductCategory> getProductCategoryTreeList() {
		logger.debug("getProductCategoryTreeList called");
		productCategoryTreeList = productCategoryService.getProductCategoryTreeList();
		return productCategoryTreeList;
	}

	public void setProductCategoryTreeList(List<ProductCategory> productCategoryTreeList) {
		this.productCategoryTreeList = productCategoryTreeList;
	}

}