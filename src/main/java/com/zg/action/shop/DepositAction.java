package com.zg.action.shop;

import java.util.List;

import javax.annotation.Resource;

import com.zg.entity.PaymentConfig;
import com.zg.service.DepositService;
import com.zg.service.PaymentConfigService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 前台Action类 - 预存款
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX5FA5DE6C97F725ADBB195DC008BFB613
 * ============================================================================
 */

@ParentPackage("member")
public class DepositAction extends BaseShopAction {

	private static final long serialVersionUID = -3091246496095700007L;
	
	@Resource
	private DepositService depositService;
	@Resource
	private PaymentConfigService paymentConfigService;
	
	// 预存款列表
	public String list() {
		pager = depositService.getDepositPager(getLoginMember(), pager);
		return "list";
	}
	
	// 预存款充值
	public String recharge() {
		return "recharge";
	}
	
	// 获取支付配置（不包含预存款、线下支付方式）
	public List<PaymentConfig> getNonDepositOfflinePaymentConfigList() {
		return paymentConfigService.getNonDepositOfflinePaymentConfigList();
	}

}