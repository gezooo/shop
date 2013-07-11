package com.zg.action.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.zg.beans.Pager;
import com.zg.entity.ProductAttribute;
import com.zg.entity.ProductType;
import com.zg.entity.ProductAttribute.AttributeType;
import com.zg.service.ProductAttributeService;
import com.zg.service.ProductTypeService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 商品属性
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class ProductAttributeAction extends BaseAdminAction {

	private static final long serialVersionUID = -7712786079159509171L;

	private ProductAttribute productAttribute;
	private List<String> attributeOptionList;
	private String productTypeId;
	private ProductType productType;
	
	protected Pager<ProductAttribute> pager;

	@Resource
	private ProductAttributeService productAttributeService;
	@Resource
	private ProductTypeService productTypeService;
	
	// 根据productTypeId获取已启用的商品属性JSON数据
	public String ajaxProductAttribute() {
		ProductType productType = productTypeService.load(productTypeId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "createDate", "modifyDate", "productType" });
		JSONArray jsonArray = JSONArray.fromObject(productType.getEnabledProductAttributeList(), jsonConfig);
		return ajaxJson(jsonArray.toString());
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		productAttribute = productAttributeService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		if (StringUtils.isNotEmpty(productTypeId)) {
			productType = productTypeService.load(productTypeId);
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductAttribute.class);
			detachedCriteria.add(Restrictions.eq("productType", productType));
			pager = productAttributeService.findByPager(pager, detachedCriteria);
		} else {
			pager = productAttributeService.findByPager(pager);
		}
		return LIST;
	}

	// 删除
	public String delete() {
		productAttributeService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "productAttribute.name", message = "商品属性名称不允许为空!"), 
			@RequiredStringValidator(fieldName = "productAttribute.productType.id", message = "商品类型不允许为空!") 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "productAttribute.attributeType", message = "商品属性类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "productAttribute.isRequired", message = "是否必填不允许为空!"),
			@RequiredFieldValidator(fieldName = "productAttribute.isEnabled", message = "是否启用不允许为空!"),
			@RequiredFieldValidator(fieldName = "productAttribute.orderList", message = "排序不允许为空!") 
		}, 
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "productAttribute.orderList", min = "0", message = "排序必须为零或正整数!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		ProductAttribute pa = productAttributeService.getProductAttribute(productAttribute.getProductType(), productAttribute.getName());
		if (pa != null) {
			addActionError("商品属性名称在此商品分类中已存在!");
			return ERROR;
		}
		if (productAttribute.getAttributeType() == AttributeType.SELECT || productAttribute.getAttributeType() == AttributeType.CHECKBOX) {
			if(attributeOptionList == null || attributeOptionList.size() < 1) {
				addActionError("请至少填写一个选项内容!");
				return ERROR;
			}
			Iterator<String> iterator = attributeOptionList.iterator(); 
			while (iterator.hasNext()) {
				String attributeOption = (String) iterator.next();
				if (StringUtils.isEmpty(attributeOption)) {
					iterator.remove();
				}
			}
			productAttribute.setAttributeOptionList(attributeOptionList);
		} else {
			productAttribute.setAttributeOptionList(null);
		}
		productAttributeService.save(productAttribute);
		if (StringUtils.isNotEmpty(productTypeId)) {
			redirectionUrl = "product_attribute!list.action?productTypeId=" + productAttribute.getProductType().getId();
		} else {
			redirectionUrl = "product_attribute!list.action";
		}
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "productAttribute.name", message = "商品属性名称不允许为空!"), 
			@RequiredStringValidator(fieldName = "productAttribute.productType.id", message = "商品类型不允许为空!") 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "productAttribute.attributeType", message = "商品属性类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "productAttribute.isEnabled", message = "是否启用不允许为空!"),
			@RequiredFieldValidator(fieldName = "productAttribute.isRequired", message = "是否必填不允许为空!"),
			@RequiredFieldValidator(fieldName = "productAttribute.orderList", message = "排序不允许为空!") }, 
			intRangeFields = { 
			@IntRangeFieldValidator(fieldName = "productAttribute.orderList", min = "0", message = "排序必须为零或正整数!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		ProductAttribute persistent = productAttributeService.load(id);
		ProductAttribute pa = productAttributeService.getProductAttribute(productAttribute.getProductType(), productAttribute.getName());
		if (pa != null && pa != persistent) {
			addActionError("商品属性名称在此商品分类中已存在!");
			return ERROR;
		}
		if (productAttribute.getAttributeType() == AttributeType.SELECT || productAttribute.getAttributeType() == AttributeType.CHECKBOX) {
			if(attributeOptionList == null || attributeOptionList.size() < 1) {
				addActionError("请至少填写一个选项内容!");
				return ERROR;
			}
			Iterator<String> iterator = attributeOptionList.iterator(); 
			while (iterator.hasNext()) {
				String attributeOption = (String) iterator.next();
				if (StringUtils.isEmpty(attributeOption)) {
					iterator.remove();
				}
			}
			productAttribute.setAttributeOptionList(attributeOptionList);
		} else {
			productAttribute.setAttributeOptionList(null);
		}
		BeanUtils.copyProperties(productAttribute, persistent, new String[] {"id", "createDate", "modifyDate"});
		productAttributeService.update(persistent);
		if (StringUtils.isNotEmpty(productTypeId)) {
			redirectionUrl = "product_attribute!list.action?productTypeId=" + productAttribute.getProductType().getId();
		} else {
			redirectionUrl = "product_attribute!list.action";
		}
		return SUCCESS;
	}

	public ProductAttribute getProductAttribute() {
		return productAttribute;
	}

	public void setProductAttribute(ProductAttribute productAttribute) {
		this.productAttribute = productAttribute;
	}
	
	public List<String> getAttributeOptionList() {
		return attributeOptionList;
	}

	public void setAttributeOptionList(List<String> attributeOptionList) {
		this.attributeOptionList = attributeOptionList;
	}

	// 获取所有商品类型
	public List<ProductType> getAllProductType() {
		return productTypeService.getAll();
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	// 获取所有商品属性类型
	public List<AttributeType> getAllAttributeType() {
		List<AttributeType> allAttributeType = new ArrayList<AttributeType>();
		for (AttributeType attributeType : AttributeType.values()) {
			allAttributeType.add(attributeType);
		}
		return allAttributeType;
	}
	
	public Pager<ProductAttribute> getPager() {
		return pager;
	}

	public void setPager(Pager<ProductAttribute> pager) {
		this.pager = pager;
	}

}