package com.zg.action.admin;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.beans.Pager.OrderType;
import com.zg.entity.DeliveryCorp;
import com.zg.service.DeliveryCorpService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.UrlValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 物流公司
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class DeliveryCorpAction extends BaseAdminAction {

	private static final long serialVersionUID = -5162370084764617443L;

	private DeliveryCorp deliveryCorp;

	@Resource
	private DeliveryCorpService deliveryCorpService;
	
	protected Pager<DeliveryCorp> pager;
	
	public Pager<DeliveryCorp> getPager() {
		return pager;
	}

	public void setPager(Pager<DeliveryCorp> pager) {
		this.pager = pager;
	}
	
	// 是否已存在 ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = deliveryCorp.getName();
		if (deliveryCorpService.isUnique("name", oldValue, newValue)) {
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
		deliveryCorp = deliveryCorpService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		if (pager == null) {
			pager = new Pager<DeliveryCorp>();
			pager.setOrderType(OrderType.ASC);
			pager.setOrderBy("orderList");
		}
		pager = deliveryCorpService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		deliveryCorpService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "deliveryCorp.name", message = "物流公司名称不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "deliveryCorp.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "deliveryCorp.orderList", min = "0", message = "排序必须为零或正整数!")
		},
		urls = {
			@UrlValidator(fieldName = "deliveryCorp.url", message = "网址格式错误!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		deliveryCorp.setDeliveryTypeSet(null);
		deliveryCorpService.save(deliveryCorp);
		redirectionUrl = "delivery_corp!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "deliveryCorp.name", message = "物流公司名称不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "deliveryCorp.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "deliveryCorp.orderList", min = "0", message = "排序必须为零或正整数!")
		},
		urls = {
			@UrlValidator(fieldName = "deliveryCorp.url", message = "网址格式错误!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		DeliveryCorp persistent = deliveryCorpService.load(id);
		BeanUtils.copyProperties(deliveryCorp, persistent, new String[]{"id", "createDate", "modifyDate", "deliveryTypeSet"});
		deliveryCorpService.update(persistent);
		redirectionUrl = "delivery_corp!list.action";
		return SUCCESS;
	}

	public DeliveryCorp getDeliveryCorp() {
		return deliveryCorp;
	}

	public void setDeliveryCorp(DeliveryCorp deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}

}