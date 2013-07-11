package com.zg.action.shop;

import javax.annotation.Resource;

import com.zg.service.MemberService;
import com.zg.service.MessageService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 前台Action类 - 会员中心
 * @author gez
 * @version 0.1
 */

@ParentPackage("member")
public class MemberCenterAction extends BaseShopAction {

	private static final long serialVersionUID = -3568504222758246021L;
	
	@Resource
	MemberService memberService;
	@Resource
	MessageService messageService;
	
	// 会员中心首页
	public String index() {
		return "index";
	}

	// 获取未读消息数量
	public Long getUnreadMessageCount() {
		return messageService.getUnreadMessageCount(getLoginMember());
	}
	
}