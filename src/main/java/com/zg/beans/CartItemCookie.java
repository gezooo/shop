package com.zg.beans;


/**
 * Bean类 - 购物车项Cookie
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX530AAB5FAD249C7132EB7A9E7D3B17B0
 * ============================================================================
 */

public class CartItemCookie {
	
	public static final String CART_ITEM_LIST_COOKIE_NAME = "cartItemList";// 保存未登录会员购物车项集合的Cookie名称
	public static final int CART_ITEM_LIST_COOKIE_MAX_AGE = 86400;// 保存未登录会员购物车项集合的Cookie最大有效时间（单位：秒）
	
	private String i;// 商品ID
	private Integer q;// 商品购买数量
	
	public String getI() {
		return i;
	}
	
	public void setI(String i) {
		this.i = i;
	}

	public Integer getQ() {
		return q;
	}

	public void setQ(Integer q) {
		this.q = q;
	}
	
}