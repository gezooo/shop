package com.zg.action.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.beans.Pager.OrderType;
import com.zg.entity.DeliveryCorp;
import com.zg.entity.DeliveryType;
import com.zg.entity.DeliveryType.DeliveryMethod;
import com.zg.entity.Product.WeightUnit;
import com.zg.service.DeliveryCorpService;
import com.zg.service.DeliveryTypeService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 配送方式
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX5AC22C1AA1942CA43D5DCBD481F91367
 * ============================================================================
 */

@ParentPackage("admin")
public class DeliveryTypeAction extends BaseAdminAction {

	private static final long serialVersionUID = -2431663334945495069L;

	private DeliveryType deliveryType;

	@Resource
	private DeliveryTypeService deliveryTypeService;
	@Resource
	private DeliveryCorpService deliveryCorpService;
	
	protected Pager<DeliveryType> pager;
	
	public Pager<DeliveryType> getPager() {
		return pager;
	}

	public void setPager(Pager<DeliveryType> pager) {
		this.pager = pager;
	}
	
	// 是否已存在 ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = deliveryType.getName();
		if (deliveryTypeService.isUnique("name", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		deliveryType = deliveryTypeService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		if (pager == null) {
			pager = new Pager<DeliveryType>();
			pager.setOrderType(OrderType.ASC);
			pager.setOrderBy("orderList");
		}
		pager = deliveryTypeService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		long totalCount = deliveryTypeService.getTotalCount();
		if (ids.length >= totalCount) {
			return ajaxJsonErrorMessage("删除失败!必须至少保留一个配送方式");
		}
		deliveryTypeService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "deliveryType.name", message = "配送方式名称不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "deliveryType.deliveryMethod", message = "配送类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.firstWeight", message = "首重量不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.continueWeight", message = "续重量不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.firstWeightUnit", message = "首重单位不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.continueWeightUnit", message = "续重单位不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.firstWeightPrice", message = "首重价格不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.continueWeightPrice", message = "续重价格不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "deliveryType.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (deliveryType.getFirstWeightPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("首重价格不允许小于0");
			return ERROR;
		}
		if (deliveryType.getContinueWeightPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("续重价格不允许小于0");
			return ERROR;
		}
		if (StringUtils.isEmpty(deliveryType.getDefaultDeliveryCorp().getId())) {
			deliveryType.setDefaultDeliveryCorp(null);
		}
		deliveryTypeService.save(deliveryType);
		redirectionUrl = "delivery_type!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "deliveryType.name", message = "配送方式名称不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "deliveryType.deliveryMethod", message = "配送类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.firstWeight", message = "首重量不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.continueWeight", message = "续重量不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.firstWeightUnit", message = "首重单位不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.continueWeightUnit", message = "续重单位不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.firstWeightPrice", message = "首重价格不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.continueWeightPrice", message = "续重价格不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.orderList", message = "排序不允许为空!")},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "deliveryType.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		if (deliveryType.getFirstWeightPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("首重价格不允许小于0");
			return ERROR;
		}
		if (deliveryType.getContinueWeightPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("续重价格不允许小于0");
			return ERROR;
		}
		if (StringUtils.isEmpty(deliveryType.getDefaultDeliveryCorp().getId())) {
			deliveryType.setDefaultDeliveryCorp(null);
		}
		DeliveryType persistent = deliveryTypeService.load(id);
		BeanUtils.copyProperties(deliveryType, persistent, new String[]{"id", "createDate", "modifyDate", "orderSet", "shippingSet", "reshipSet"});
		deliveryTypeService.update(persistent);
		redirectionUrl = "delivery_type!list.action";
		return SUCCESS;
	}

	// 获取所有物流公司
	public List<DeliveryCorp> getAllDeliveryCorp() {
		return deliveryCorpService.getAll();
	}

	// 获取所有配送类型
	public List<DeliveryMethod> getAllDeliveryMethod() {
		List<DeliveryMethod> allDeliveryMethod = new ArrayList<DeliveryMethod>();
		for (DeliveryMethod deliveryMethod : DeliveryMethod.values()) {
			allDeliveryMethod.add(deliveryMethod);
		}
		return allDeliveryMethod;
	}

	// 获取所有重量单位
	public List<WeightUnit> getAllWeightUnit() {
		List<WeightUnit> allWeightUnit = new ArrayList<WeightUnit>();
		for (WeightUnit weightUnit : WeightUnit.values()) {
			allWeightUnit.add(weightUnit);
		}
		return allWeightUnit;
	}
	
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

}