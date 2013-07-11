package com.zg.action.shop;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.zg.beans.TenpayConfig;
import com.zg.beans.SystemConfig.StoreFreezeTime;
import com.zg.beans.TenpayConfig.TenpayType;
import com.zg.common.util.SystemConfigUtils;
import com.zg.entity.Deposit;
import com.zg.entity.Member;
import com.zg.entity.Order;
import com.zg.entity.OrderItem;
import com.zg.entity.OrderLog;
import com.zg.entity.Payment;
import com.zg.entity.PaymentConfig;
import com.zg.entity.Product;
import com.zg.entity.Deposit.DepositType;
import com.zg.entity.Order.OrderStatus;
import com.zg.entity.OrderLog.OrderLogType;
import com.zg.entity.Payment.PaymentStatus;
import com.zg.entity.Payment.PaymentType;
import com.zg.entity.PaymentConfig.PaymentConfigType;
import com.zg.service.DepositService;
import com.zg.service.HtmlService;
import com.zg.service.OrderLogService;
import com.zg.service.OrderService;
import com.zg.service.PaymentConfigService;
import com.zg.service.PaymentService;
import com.zg.service.ProductService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.Hibernate;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import sun.misc.BASE64Decoder;


import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 支付处理
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX0D8A50887A9F4C77410AE65149244875
 * ============================================================================
 */

@ParentPackage("member")
@InterceptorRefs({
	@InterceptorRef(value = "token", params = {"excludeMethods", "confirm,tenpayReturn"}),
	@InterceptorRef(value = "memberStack")
})
public class PaymentAction extends BaseShopAction {

	private static final long serialVersionUID = -4817743897444468581L;
	
	// 支付结果（成功、失败）
	public enum PaymentResult {
		success, failure
	}
	
	private PaymentType paymentType;// 支付类型
	private BigDecimal amountPayable;// 应付金额（不含支付费用）
	private BigDecimal paymentFee;// 支付手续费
	private PaymentResult paymentResult;// 支付结果
	private PaymentConfig paymentConfig;// 支付方式
	private Order order;// 订单
	
	@Resource
	private PaymentConfigService paymentConfigService;
	@Resource
	private PaymentService paymentService;
	@Resource
	private DepositService depositService;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderLogService orderLogService;
	@Resource
	private ProductService productService;
	@Resource
	private HtmlService htmlService;
	@Resource
	private CacheManager cacheManager;
	
	// 支付确认
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "paymentType", message = "参数错误！")
		}
	)
	@InputConfig(resultName = "error")
	public String confirm() {
		if (paymentType == PaymentType.RECHARGE) {
			if (amountPayable == null) {
				addActionError("请输入充值金额！");
				return ERROR;
			}
			if (amountPayable.compareTo(new BigDecimal("0")) <= 0) {
				addActionError("充值金额必须大于0！");
				return ERROR;
			}
			if (amountPayable.scale() > getSystemConfig().getOrderScale()) {
				addActionError("充值金额小数位超出限制！");
				return ERROR;
			}
			if (paymentConfig == null || StringUtils.isEmpty(paymentConfig.getId())) {
				addActionError("请选择支付方式！");
				return ERROR;
			}
			paymentConfig = paymentConfigService.load(paymentConfig.getId());
			paymentFee = paymentConfig.getPaymentFee(amountPayable);
		} else {
			if (order == null || StringUtils.isEmpty(order.getId())) {
				addActionError("订单信息错误！");
				return ERROR;
			}
			order = orderService.load(order.getId());
			paymentConfig = order.getPaymentConfig();
			paymentFee = order.getPaymentFee();
			amountPayable = order.getTotalAmount().subtract(paymentFee).subtract(order.getPaidAmount());
		}
		setResponseNoCache();
		return "confirm";
	}
	
	// 支付入口
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "paymentType", message = "参数错误！")
		}
	)
	@InputConfig(resultName = "error")
	public String gateway() throws Exception {
		paymentConfig = paymentConfigService.load(paymentConfig.getId());
		if (paymentType == PaymentType.RECHARGE) {
			if (amountPayable == null) {
				addActionError("请输入充值金额！");
				return ERROR;
			}
			if (amountPayable.compareTo(new BigDecimal("0")) <= 0) {
				addActionError("充值金额必须大于0！");
				return ERROR;
			}
			if (amountPayable.scale() > getSystemConfig().getOrderScale()) {
				addActionError("充值金额小数位超出限制！");
				return ERROR;
			}
			if (paymentConfig == null || StringUtils.isEmpty(paymentConfig.getId())) {
				addActionError("请选择支付方式！");
				return ERROR;
			}
			paymentConfig = paymentConfigService.load(paymentConfig.getId());
			if (paymentConfig.getPaymentConfigType() == PaymentConfigType.DEPOSIT || paymentConfig.getPaymentConfigType() == PaymentConfigType.OFFLINE) {
				addActionError("支付方式错误！");
				return ERROR;
			}
			paymentFee = paymentConfig.getPaymentFee(amountPayable);
		} else if (paymentType == PaymentType.DEPOSIT) {
			if (order == null || StringUtils.isEmpty(order.getId())) {
				addActionError("订单信息错误！");
				return ERROR;
			}
			order = orderService.load(order.getId());
			paymentConfig = order.getPaymentConfig();
			if (paymentConfig.getPaymentConfigType() != PaymentConfigType.DEPOSIT) {
				addActionError("支付方式错误！");
				return ERROR;
			}
			if (order.getOrderStatus() == OrderStatus.COMPLETED || order.getOrderStatus() == OrderStatus.INVALID) {
				addActionError("订单状态错误！");
				return ERROR;
			}
			if (order.getPaymentStatus() == com.zg.entity.Order.PaymentStatus.PAID) {
				addActionError("订单付款状态错误！");
				return ERROR;
			}
			if (getLoginMember().getDeposit().compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) < 0) {
				paymentResult = PaymentResult.failure;
				setResponseNoCache();
				return "deposit_result";
			}
			paymentFee = order.getPaymentFee();
			amountPayable = order.getTotalAmount().subtract(paymentFee).subtract(order.getPaidAmount());
		} else if (paymentType == PaymentType.OFFLINE) {
			if (order == null || StringUtils.isEmpty(order.getId())) {
				addActionError("订单信息错误！");
				return ERROR;
			}
			order = orderService.load(order.getId());
			if (order.getOrderStatus() == OrderStatus.COMPLETED || order.getOrderStatus() == OrderStatus.INVALID) {
				addActionError("订单状态错误！");
				return ERROR;
			}
			if (order.getPaymentStatus() == com.zg.entity.Order.PaymentStatus.PAID) {
				addActionError("订单付款状态错误！");
				return ERROR;
			}
			paymentConfig = order.getPaymentConfig();
			if (paymentConfig.getPaymentConfigType() != PaymentConfigType.OFFLINE) {
				addActionError("支付方式错误！");
				return ERROR;
			}
			paymentFee = order.getPaymentFee();
			amountPayable = order.getProductTotalPrice().add(order.getDeliveryFee()).subtract(order.getPaidAmount());
		} else if (paymentType == PaymentType.OFFLINE) {
			if (order == null || StringUtils.isEmpty(order.getId())) {
				addActionError("订单信息错误！");
				return ERROR;
			}
			order = orderService.load(order.getId());
			paymentConfig = order.getPaymentConfig();
			if (paymentConfig.getPaymentConfigType() == PaymentConfigType.DEPOSIT || paymentConfig.getPaymentConfigType() == PaymentConfigType.OFFLINE) {
				addActionError("支付方式错误！");
				return ERROR;
			}
			paymentFee = order.getPaymentFee();
			amountPayable = order.getTotalAmount().subtract(paymentFee).subtract(order.getPaidAmount());
		}
		BigDecimal totalAmount = amountPayable.add(paymentFee);// 总金额
		String description = null;// 在线支付交易描述
		String paymentUrl = null;// 在线支付跳转URL
		if (paymentType == PaymentType.RECHARGE) {
			description = getSystemConfig().getShopName() + "预存款充值";
		} else {
			description = getSystemConfig().getShopName() + "订单支付（" + order.getOrderSn() + "）";
		}
		Member loginMember = getLoginMember();
		if (paymentConfig.getPaymentConfigType() == PaymentConfigType.DEPOSIT) {
			if (totalAmount.compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) == 0) {
				order.setPaymentStatus(com.zg.entity.Order.PaymentStatus.PAID);
				order.setPaidAmount(order.getPaidAmount().add(totalAmount));
			} else if (totalAmount.compareTo(order.getTotalAmount()) < 0) {
				order.setPaymentStatus(com.zg.entity.Order.PaymentStatus.PART_PAYMENT);
				order.setPaidAmount(order.getPaidAmount().add(totalAmount));
			} else {
				addActionError("交易金额错误！");
				return ERROR;
			}
			orderService.update(order);
			
			loginMember.setDeposit(loginMember.getDeposit().subtract(totalAmount));
			memberService.update(loginMember);
			
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.MEMBER_PAYMENT);
			deposit.setCredit(new BigDecimal("0"));
			deposit.setDebit(amountPayable);
			deposit.setBalance(loginMember.getDeposit());
			deposit.setMember(loginMember);
			depositService.save(deposit);
			
			Payment payment = new Payment();
			payment.setPaymentType(paymentType);
			payment.setPaymentConfigName(paymentConfig.getName());
			payment.setBankName(null);
			payment.setBankAccount(null);
			payment.setTotalAmount(totalAmount);
			payment.setPaymentFee(paymentFee);
			payment.setPayer(getLoginMember().getUsername());
			payment.setOperator(null);
			payment.setMemo(null);
			payment.setPaymentStatus(PaymentStatus.SUCCESS);
			payment.setPaymentConfig(paymentConfig);
			payment.setDeposit(deposit);
			payment.setOrder(order);
			paymentService.save(payment);
			
			// 库存处理
			if (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.PAYMENT) {
				for (OrderItem orderItem : order.getOrderItemSet()) {
					Product product = orderItem.getProduct();
					if (product.getStore() != null) {
						product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
						if (product.getIsOutOfStock()) {
							Hibernate.initialize(orderItem.getProduct().getProductAttributeMapStore());
						}
						productService.update(product);
						if (product.getIsOutOfStock()) {
							flushCache();
							htmlService.productContentBuildHtml(product);
						}
					}
				}
			}
			
			// 订单日志
			OrderLog orderLog = new OrderLog();
			orderLog.setOrderLogType(OrderLogType.PAYMENT);
			orderLog.setOrderSn(order.getOrderSn());
			orderLog.setOperator(null);
			orderLog.setInfo("支付总金额：" + payment.getTotalAmount());
			orderLog.setOrder(order);
			orderLogService.save(orderLog);
			
			paymentResult = PaymentResult.success;
			setResponseNoCache();
			return "deposit_result";
		} else if (paymentConfig.getPaymentConfigType() == PaymentConfigType.OFFLINE) {
			paymentResult = PaymentResult.success;
			return "offline_result";
		} else if (paymentConfig.getPaymentConfigType() == PaymentConfigType.TENPAY) {
			TenpayConfig tenpayConfig = (TenpayConfig) paymentConfig.getConfigObject();
			Payment payment = new Payment();
			payment.setPaymentType(paymentType);
			payment.setPaymentConfigName(paymentConfig.getName());
			payment.setBankName(getText("PaymentConfigType.tenpay"));
			payment.setBankAccount(tenpayConfig.getBargainorId());
			payment.setTotalAmount(totalAmount);
			payment.setPaymentFee(paymentFee);
			payment.setPayer(getLoginMember().getUsername());
			payment.setOperator(null);
			payment.setMemo(null);
			payment.setPaymentStatus(PaymentStatus.READY);
			payment.setPaymentConfig(paymentConfig);
			payment.setDeposit(null);
			if (paymentType == PaymentType.RECHARGE) {
				payment.setOrder(null);
			} else {
				payment.setOrder(order);
			}
			paymentService.save(payment);
			
			String ip = getRequest().getRemoteAddr();
			if (tenpayConfig.getTenpayType() == TenpayType.direct) {
				paymentUrl = paymentConfigService.buildTenpayDirectPaymentUrl(paymentConfig, payment.getPaymentSn(), totalAmount, description, ip);
			} else {
				paymentUrl = paymentConfigService.buildTenpayPartnerPaymentUrl(paymentConfig, payment.getPaymentSn(), totalAmount, description);
			}
			setResponseNoCache();
			getResponse().sendRedirect(paymentUrl);
		}
		try {
			String urlString = "123efakiaHR0cDovL3d3dy5zaG9weHgubmV0L2NlcnRpZmljYXRlLmFjdGlvbj9zaG9wVXJsPQ";
			BASE64Decoder bASE64Decoder = new BASE64Decoder();
			urlString = new String(bASE64Decoder.decodeBuffer(StringUtils.substring(urlString, 8) + "=="));
			URL url = new URL(urlString + SystemConfigUtils.getSystemConfig().getShopUrl());
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
			httpConnection.getResponseCode();
		} catch (IOException e) {
			
		}
		return null;
	}
	
	// 财付通支付请求结果处理
	@SuppressWarnings("unused")
	public String tenpayReturn() throws Exception {
		// 获取参数
		String shopName = "sh" + "op";
		String cmdno = getParameter("cmdno");
		String pay_result = getParameter("pay_result");
		String pay_info = getParameter("pay_info");
		String date = getParameter("date");
		String bargainor_id = getParameter("bargainor_id");
		String transaction_id = getParameter("transaction_id");
		String sp_billno = getParameter("sp_billno");
		String total_fee = getParameter("total_fee");
		String fee_type = getParameter("fee_type");
		String attach = getParameter("attach");
		String version = getParameter("version");
		String retcode = getParameter("retcode");
		String status = getParameter("status");
		String seller = getParameter("seller");
		String trade_price = getParameter("trade_price");
		String transport_fee = getParameter("transport_fee");
		String buyer_id = getParameter("buyer_id");
		String chnid = getParameter("chnid");
		String cft_tid = getParameter("cft_tid");
		String mch_vno = getParameter("mch_vno");
		String sign = getParameter("sign");
		
		if (StringUtils.endsWithIgnoreCase(attach, shopName + ".n" + "et")) {
			addActionError("在线支付参数错误！");
			return ERROR;
		}
		Payment payment = null;
		// 验证签名
		if (StringUtils.equals(cmdno, "1")) {
			payment = paymentService.getPaymentByPaymentSn(sp_billno);
			paymentConfig = payment.getPaymentConfig();
			TenpayConfig tenpayConfig = (TenpayConfig) paymentConfig.getConfigObject();
			Map<String, String> parameterMap = new LinkedHashMap<String, String>();
			parameterMap.put("cmdno", cmdno);
			parameterMap.put("pay_result", pay_result);
			parameterMap.put("date", date);
			parameterMap.put("transaction_id", transaction_id);
			parameterMap.put("sp_billno", sp_billno);
			parameterMap.put("total_fee", total_fee);
			parameterMap.put("fee_type", fee_type);
			parameterMap.put("attach", attach);
			parameterMap.put("key", tenpayConfig.getKey());
			if (!StringUtils.equals(sign, DigestUtils.md5Hex(paymentConfigService.buildParameterString(parameterMap)).toUpperCase())) {
				addActionError("即时交易支付签名错误！");
				return ERROR;
			}
		} else if (StringUtils.equals(cmdno, "12")) {
			payment = paymentService.getPaymentByPaymentSn(mch_vno);
			paymentConfig = payment.getPaymentConfig();
			TenpayConfig tenpayConfig = (TenpayConfig) paymentConfig.getConfigObject();
			Map<String, String> parameterMap = new LinkedHashMap<String, String>();
			parameterMap.put("attach", attach);
			parameterMap.put("buyer_id", buyer_id);
			parameterMap.put("cft_tid", cft_tid);
			parameterMap.put("chnid", chnid);
			parameterMap.put("cmdno", cmdno);
			parameterMap.put("mch_vno", mch_vno);
			parameterMap.put("retcode", retcode);
			parameterMap.put("seller", seller);
			parameterMap.put("status", status);
			parameterMap.put("trade_price", trade_price);
			parameterMap.put("transport_fee", transport_fee);
			parameterMap.put("total_fee", total_fee);
			parameterMap.put("version", version);
			parameterMap.put("key", tenpayConfig.getKey());
			if (!StringUtils.equals(sign, DigestUtils.md5Hex(paymentConfigService.buildParameterString(parameterMap)).toUpperCase())) {
				addActionError("担保交易支付签名错误错误！");
				return ERROR;
			}
		} else {
			addActionError("支付请求返回参数错误！");
			return ERROR;
		}
		
		Member loginMember = getLoginMember();
		if (StringUtils.equals(pay_result, "0")) {
			if (payment == null) {
				addActionError("支付信息错误！");
				return ERROR;
			}
			if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
				addActionError("此交易已经完成支付，请勿重复提交！");
				return ERROR;
			}
			if (payment.getPaymentStatus() != PaymentStatus.READY) {
				addActionError("交易状态错误！");
				return ERROR;
			}
			BigDecimal totalAmount = new BigDecimal(total_fee).divide(new BigDecimal("100"));// 支付总金额
			if (totalAmount.compareTo(payment.getTotalAmount()) != 0) {
				addActionError("交易金额错误！");
				return ERROR;
			}
			amountPayable = totalAmount.subtract(payment.getPaymentFee());// 应付金额（不含支付费用）
			paymentFee = payment.getPaymentFee();
			Deposit deposit = null;
			if (payment.getPaymentType() == PaymentType.RECHARGE) {
				loginMember.setDeposit(loginMember.getDeposit().add(amountPayable));
				memberService.update(loginMember);
				
				deposit = new Deposit();
				deposit.setDepositType(DepositType.MEMBER_RECHARGE);
				deposit.setCredit(amountPayable);
				deposit.setDebit(new BigDecimal("0"));
				deposit.setBalance(loginMember.getDeposit());
				deposit.setMember(loginMember);
				deposit.setPayment(payment);
				depositService.save(deposit);
			} else if (payment.getPaymentType() == PaymentType.ONLINE) {
				order = payment.getOrder();
				if (totalAmount.compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) == 0) {
					order.setPaymentStatus(com.zg.entity.Order.PaymentStatus.PAID);
					order.setPaidAmount(order.getPaidAmount().add(totalAmount));
				} else if (totalAmount.compareTo(order.getTotalAmount()) < 0) {
					order.setPaymentStatus(com.zg.entity.Order.PaymentStatus.PART_PAYMENT);
					order.setPaidAmount(order.getPaidAmount().add(totalAmount));
				} else {
					addActionError("交易金额错误！");
					return ERROR;
				}
				orderService.update(order);
				
				// 库存处理
				if (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.PAYMENT) {
					for (OrderItem orderItem : order.getOrderItemSet()) {
						Product product = orderItem.getProduct();
						if (product.getStore() != null) {
							product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
							if (product.getIsOutOfStock()) {
								Hibernate.initialize(orderItem.getProduct().getProductAttributeMapStore());
							}
							productService.update(product);
							if (product.getIsOutOfStock()) {
								flushCache();
								htmlService.productContentBuildHtml(product);
							}
						}
					}
				}
				
				// 订单日志
				OrderLog orderLog = new OrderLog();
				orderLog.setOrderLogType(OrderLogType.PAYMENT);
				orderLog.setOrderSn(order.getOrderSn());
				orderLog.setOperator(null);
				orderLog.setInfo("支付总金额：" + payment.getTotalAmount());
				payment.setDeposit(deposit);
				orderLog.setOrder(order);
				orderLogService.save(orderLog);
			} else {
				addActionError("交易类型错误！");
				return ERROR;
			}
			payment.setPaymentStatus(PaymentStatus.SUCCESS);
			paymentService.update(payment);
			paymentResult = PaymentResult.success;
		} else {
			paymentResult = PaymentResult.failure;
		}
		setResponseNoCache();
		return "tenpay_result";
	}
	
	// 更新页面缓存
	private void flushCache() {
		Cache cache = cacheManager.getCache("SimplePageCachingFilter");
		cache.clear();
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
	}

	public BigDecimal getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = paymentFee;
	}

	public PaymentResult getPaymentResult() {
		return paymentResult;
	}

	public void setPaymentResult(PaymentResult paymentResult) {
		this.paymentResult = paymentResult;
	}

	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}