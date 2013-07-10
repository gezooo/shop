package com.zg.action.shop;

import javax.annotation.Resource;

import com.zg.service.MemberService;
import com.zg.service.MessageService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 前台Action类 - 会员中心
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX456CA553F200C7A4CFEC6788B8F51192
 * ============================================================================
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