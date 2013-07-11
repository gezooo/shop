package com.zg.action.admin;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 菜单
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class MenuAction extends BaseAdminAction {

	private static final long serialVersionUID = 6465259795235263493L;

	// 商店配置
	public String setting() {
		return "setting";
	}
	
	// 会员
	public String member() {
		return "member";
	}
	
	// 商品
	public String product() {
		return "product";
	}
	
	// 页面管理
	public String content() {
		return "content";
	}
	
	// 订单管理
	public String order() {
		return "order";
	}
	
	// 管理员
	public String admin() {
		return "admin";
	}

	// 主体
	public String main() {
		return "main";
	}

	// 常用
	public String common() {
		return "common";
	}

	// 头部
	public String header() {
		return "header";
	}

}