package com.zg.action.shop;

import java.util.Set;

import javax.annotation.Resource;

import com.zg.entity.Member;
import com.zg.entity.Receiver;
import com.zg.service.AreaService;
import com.zg.service.ReceiverService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 收货地址
 * @author gez
 * @version 0.1
 */

@ParentPackage("member")
public class ReceiverAction extends BaseShopAction {

	private static final long serialVersionUID = 5947142304957196520L;
	
	private Receiver receiver;
	private Set<Receiver> receiverSet;

	@Resource
	private AreaService areaService;
	@Resource
	private ReceiverService receiverService;

	// 收货地址添加
	public String add() {
		Member loginMember = getLoginMember();
		Set<Receiver> receiverSet = loginMember.getReceiverSet();
		if (receiverSet != null && Receiver.MAX_RECEIVER_COUNT != null && receiverSet.size() >= Receiver.MAX_RECEIVER_COUNT) {
			addActionError("只允许添加最多" + Receiver.MAX_RECEIVER_COUNT + "项收货地址!");
			return ERROR;
		}
		return INPUT;
	}
	
	// 收货地址编辑
	public String edit() {
		receiver = receiverService.load(id);
		if(receiver.getMember() != getLoginMember()) {
			addActionError("参数错误!");
			return ERROR;
		}
		return INPUT;
	}
	
	// 收货地址删除
	public String delete() {
		Receiver receiver = receiverService.load(id);
		if(receiver.getMember() != getLoginMember()) {
			addActionError("参数错误!");
			return ERROR;
		}
		receiverService.delete(receiver);
		redirectionUrl = "receiver!list.action";
		return SUCCESS;
	}
	
	// 收货地址列表
	public String list() {
		return LIST;
	}
	
	// 收货地址保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "receiver.name", message = "收货人不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.areaPath", message = "地区不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.address", message = "联系地址不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.zipCode", message = "邮编不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "receiver.isDefault", message = "是否默认不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (StringUtils.isEmpty(receiver.getPhone()) && StringUtils.isEmpty(receiver.getMobile())) {
			addActionError("联系电话、联系手机必须填写其中一项!");
			return ERROR;
		}
		if (!areaService.isAreaPath(receiver.getAreaPath())) {
			addActionError("地区错误!");
			return ERROR;
		}
		Member loginMember = getLoginMember();
		Set<Receiver> receiverSet = loginMember.getReceiverSet();
		if (receiverSet != null && Receiver.MAX_RECEIVER_COUNT != null && receiverSet.size() >= Receiver.MAX_RECEIVER_COUNT) {
			addActionError("只允许添加最多" + Receiver.MAX_RECEIVER_COUNT + "项收货地址!");
			return ERROR;
		}
		receiver.setMember(loginMember);
		receiverService.save(receiver);
		redirectionUrl = "receiver!list.action";
		return SUCCESS;
	}
	
	// 收货地址更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "receiver.name", message = "收货人不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.areaPath", message = "地区不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.address", message = "联系地址不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.zipCode", message = "邮编不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "receiver.isDefault", message = "是否默认不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		if (StringUtils.isEmpty(receiver.getMobile()) && StringUtils.isEmpty(receiver.getPhone())) {
			addActionError("联系手机、联系电话必须填写其中一项!");
			return ERROR;
		}
		if (!areaService.isAreaPath(receiver.getAreaPath())) {
			addActionError("地区错误!");
			return ERROR;
		}
		Receiver persistent = receiverService.load(id);
		if(persistent.getMember() != getLoginMember()) {
			addActionError("参数错误!");
			return ERROR;
		}
		BeanUtils.copyProperties(receiver, persistent, new String[] {"id", "createDate", "modifyDate", "member"});
		receiverService.update(persistent);
		redirectionUrl = "receiver!list.action";
		return SUCCESS;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public Set<Receiver> getReceiverSet() {
		this.receiverSet = getLoginMember().getReceiverSet();
		return receiverSet;
	}

	public void setReceiverSet(Set<Receiver> receiverSet) {
		this.receiverSet = receiverSet;
	}

}