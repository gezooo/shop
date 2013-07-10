<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>处理订单 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready( function() {

	var $paymentTabButton = $("#paymentTabButton");
	var $shippingTabButton = $("#shippingTabButton");
	var $refundTabButton = $("#refundTabButton");
	var $reshipTabButton = $("#reshipTabButton");

	var $areaSelect = $(".areaSelect");
	var $paymentProcessButton = $("input[name='paymentProcess']");
	var $shippingProcessButton = $("input[name='shippingProcess']");
	var $completedProcessButton = $("input[name='completedProcess']");
	var $refundProcessButton = $("input[name='refundProcess']");
	var $reshipProcessButton = $("input[name='reshipProcess']");
	var $invalidProcessButton = $("input[name='invalidProcess']");
	
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "area!ajaxChildrenArea.action"// Json数据获取url
	});

	var tabs = $("ul.tab").tabs();
	
	// 订单支付
	$paymentProcessButton.click( function() {
		tabs.click(2);
	});
	
	// 订单发货
	$shippingProcessButton.click( function() {
		tabs.click(3);
	});
	
	// 订单完成
	$completedProcessButton.click( function() {
		var $this = $(this);
		if (confirm("订单完成后将不允许对此订单进行任何操作，确认执行？") == true) {
			$.ajax({
				url: "order!completed.action",
				data: {"order.id": "${order.id}"},
				dataType: "json",
				async: false,
				beforeSend: function() {
					$this.attr("disabled", true);
				},
				success: function(data) {
					$.message(data.status, data.message);
					if (data.status == "success") {
						$paymentTabButton.attr("disabled", true);
						$shippingTabButton.attr("disabled", true);
						$refundTabButton.attr("disabled", true);
						$reshipTabButton.attr("disabled", true);
						
						$paymentProcessButton.attr("disabled", true);
						$shippingProcessButton.attr("disabled", true);
						$completedProcessButton.attr("disabled", true);
						$refundProcessButton.attr("disabled", true);
						$reshipProcessButton.attr("disabled", true);
						$invalidProcessButton.attr("disabled", true);
					} else {
						$this.attr("disabled", true);
					}
				}
			});
		}
	});
	
	// 退款
	$refundProcessButton.click( function() {
		tabs.click(4);
	});
	
	// 退货
	$reshipProcessButton.click( function() {
		tabs.click(5);
	});
	
	// 作废
	$invalidProcessButton.click( function() {
		var $this = $(this);
		if (confirm("订单作废后将不允许对此订单进行任何操作，确认执行？") == true) {
			$.ajax({
				url: "order!invalid.action",
				data: {"order.id": "${order.id}"},
				dataType: "json",
				async: false,
				beforeSend: function() {
					$this.attr("disabled", true);
				},
				success: function(data) {
					$.message(data.status, data.message);
					if (data.status == "success") {
						$paymentTabButton.attr("disabled", true);
						$shippingTabButton.attr("disabled", true);
						$refundTabButton.attr("disabled", true);
						$reshipTabButton.attr("disabled", true);
						
						$paymentProcessButton.attr("disabled", true);
						$shippingProcessButton.attr("disabled", true);
						$completedProcessButton.attr("disabled", true);
						$refundProcessButton.attr("disabled", true);
						$reshipProcessButton.attr("disabled", true);
						$invalidProcessButton.attr("disabled", true);
					} else {
						$this.attr("disabled", false);
					}
				}
			});
		}
	});
	
	// 订单支付表单验证
	$("#paymentForm").validate({
		errorClass: "validateError",
		ignore: ".ignoreValidate",
		errorPlacement: function(error, element) {
			var messagePosition = element.metadata().messagePosition;
			if("undefined" != typeof messagePosition && messagePosition != "") {
				var $messagePosition = $(messagePosition);
				if ($messagePosition.size() > 0) {
					error.insertAfter($messagePosition).fadeOut(300).fadeIn(300);
				} else {
					error.insertAfter(element).fadeOut(300).fadeIn(300);
				}
			} else {
				error.insertAfter(element).fadeOut(300).fadeIn(300);
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	// 订单发货表单验证
	$("#shippingForm").validate({
		errorClass: "validateError",
		ignore: ".ignoreValidate",
		errorPlacement: function(error, element) {
			var messagePosition = element.metadata().messagePosition;
			if("undefined" != typeof messagePosition && messagePosition != "") {
				var $messagePosition = $(messagePosition);
				if ($messagePosition.size() > 0) {
					error.insertAfter($messagePosition).fadeOut(300).fadeIn(300);
				} else {
					error.insertAfter(element).fadeOut(300).fadeIn(300);
				}
			} else {
				error.insertAfter(element).fadeOut(300).fadeIn(300);
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	// 订单退款表单验证
	$("#refundForm").validate({
		errorClass: "validateError",
		ignore: ".ignoreValidate",
		errorPlacement: function(error, element) {
			var messagePosition = element.metadata().messagePosition;
			if("undefined" != typeof messagePosition && messagePosition != "") {
				var $messagePosition = $(messagePosition);
				if ($messagePosition.size() > 0) {
					error.insertAfter($messagePosition).fadeOut(300).fadeIn(300);
				} else {
					error.insertAfter(element).fadeOut(300).fadeIn(300);
				}
			} else {
				error.insertAfter(element).fadeOut(300).fadeIn(300);
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	// 订单退货表单验证
	$("#reshipForm").validate({
		errorClass: "validateError",
		ignore: ".ignoreValidate",
		errorPlacement: function(error, element) {
			var messagePosition = element.metadata().messagePosition;
			if("undefined" != typeof messagePosition && messagePosition != "") {
				var $messagePosition = $(messagePosition);
				if ($messagePosition.size() > 0) {
					error.insertAfter($messagePosition).fadeOut(300).fadeIn(300);
				} else {
					error.insertAfter(element).fadeOut(300).fadeIn(300);
				}
			} else {
				error.insertAfter(element).fadeOut(300).fadeIn(300);
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});

});
</script>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>处理订单</h1>
		</div>
		<div class="blank"></div>
		<ul class="tab">
			<li>
				<input type="button" value="基本信息" hidefocus="true" />
			</li>
			<li>
				<input type="button" value="商品信息" hidefocus="true" />
			</li>
			<li>
				<input type="button" id="paymentTabButton" value="订单支付"<#if order.orderStatus == "COMPLETED" || order.orderStatus == "INVALID" || order.paymentStatus == "paid" || order.paymentStatus == "partRefund" || order.paymentStatus == "refunded"> disabled</#if> hidefocus="true" />
			</li>
			<li>
				<input type="button" id="shippingTabButton" value="订单发货"<#if order.orderStatus == "COMPLETED" || order.orderStatus == "INVALID" || order.shippingStatus == "shipped"> disabled</#if> hidefocus="true" />
			</li>
			<li>
				<input type="button" id="refundTabButton" value="退款"<#if order.orderStatus == "COMPLETED" || order.orderStatus == "INVALID" || order.paymentStatus == "unpaid" || order.paymentStatus == "refunded"> disabled</#if> hidefocus="true" />
			</li>
			<li>
				<input type="button" id="reshipTabButton" value="退货"<#if order.orderStatus == "COMPLETED" || order.orderStatus == "INVALID" || order.shippingStatus == "unshipped" || order.shippingStatus == "reshiped"> disabled</#if> hidefocus="true" />
			</li>
		</ul>
		<div class="tabContent">
			<table class="inputTable">
				<tr>
					<th>
						订单状态操作:
					</th>
					<td>
						<input type="button" name="paymentProcess" class="formButton" value="订单支付"<#if order.orderStatus == "COMPLETED" || order.orderStatus == "INVALID" || order.paymentStatus == "paid" || order.paymentStatus == "partRefund" || order.paymentStatus == "refunded"> disabled</#if> hidefocus="true" />
						<input type="button" name="shippingProcess" class="formButton" value="订单发货"<#if order.orderStatus == "COMPLETED" || order.orderStatus == "INVALID" || order.shippingStatus == "shipped"> disabled</#if> hidefocus="true" />
						<input type="button" name="completedProcess" class="formButton" value="订单完成"<#if order.orderStatus == "COMPLETED" || order.orderStatus == "INVALID"> disabled</#if> hidefocus="true" />
					</td>
					<td colspan="2">
						<input type="button" name="refundProcess" class="formButton" value="退款"<#if order.orderStatus == "COMPLETED" || order.orderStatus == "INVALID" || order.paymentStatus == "unpaid" || order.paymentStatus == "refunded"> disabled</#if> hidefocus="true" />
						<input type="button" name="reshipProcess" class="formButton" value="退货"<#if order.orderStatus == "COMPLETED" || order.orderStatus == "INVALID" || order.shippingStatus == "unshipped" || order.shippingStatus == "reshiped"> disabled</#if> hidefocus="true" />
						<input type="button" name="invalidProcess" class="formButton" value="作废"<#if order.orderStatus == "COMPLETED" || order.orderStatus == "INVALID" || order.paymentStatus != "unpaid" || order.shippingStatus != "unshipped"> disabled</#if> hidefocus="true" />
					</td>
				</tr>
				<tr>
					<th>
						订单状态:
					</th>
					<td colspan="3">
						[${action.getText("OrderStatus." + order.orderStatus)}]
						[${action.getText("PaymentStatus." + order.paymentStatus)}]
						[${action.getText("ShippingStatus." + order.shippingStatus)}]
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<th>
						订单编号:
					</th>
					<td>
						${order.orderSn}
					</td>
					<th>
						下单时间:
					</th>
					<td>
						${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
					</td>
				</tr>
				<tr>
					<th>
						商品总金额:
					</th>
					<td>
						<span id="productTotalPrice" class="red">${order.productTotalPrice?string(orderUnitCurrencyFormat)}</span>
					</td>
					<th>
						订单总金额:
					</th>
					<td>
						<span id="totalAmount" class="red">${order.totalAmount?string(orderUnitCurrencyFormat)}</span>&nbsp;&nbsp;
						<strong class="red">[已付金额：${order.paidAmount?string(orderUnitCurrencyFormat)}]</strong>
					</td>
				</tr>
				<tr>
					<th>
						配送方式:
					</th>
					<td>
						${order.deliveryTypeName}
					</td>
					<th>
						支付方式:
					</th>
					<td>
						${order.paymentConfigName}
					</td>
				</tr>
				<tr>
					<th>
						配送费用:
					</th>
					<td>
						${order.deliveryFee?string(orderUnitCurrencyFormat)}
					</td>
					<th>
						支付手续费:
					</th>
					<td>
						${order.paymentFee?string(orderUnitCurrencyFormat)}
					</td>
				</tr>
				<tr>
					<th>
						商品重量:
					</th>
					<td>
						${order.productWeight}${action.getText("WeightUnit." + order.productWeightUnit)}
					</td>
					<th>
						附言:
					</th>
					<td>
						${(order.memo)!"&nbsp;"}
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<th>
						收货人姓名:
					</th>
					<td>
						${order.shipName}
					</td>
					<th>
						收货地区:
					</th>
					<td>
						${order.shipArea}
					</td>
				</tr>
				<tr>
					<th>
						收货地址:
					</th>
					<td>
						${order.shipAddress}
					</td>
					<th>
						邮编:
					</th>
					<td>
						${order.shipZipCode}
					</td>
				</tr>
				<tr>
					<th>
						电话:
					</th>
					<td>
						${order.shipPhone}
					</td>
					<th>
						手机:
					</th>
					<td>
						${order.shipMobile}
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<#if order.member??>
					<tr>
						<th>
							用户名:
						</th>
						<td>
							${order.member.username}
						</td>
						<th>
							会员等级:
						</th>
						<td>
							${order.member.memberRank.name}
							<#if order.member.memberRank.preferentialScale != 100>
								<span class="red">[优惠百分比：${order.member.memberRank.preferentialScale}%]</span>
							</#if>
						</td>
					</tr>
					<tr>
						<th>
							E-mail:
						</th>
						<td>
							${order.member.email}
						</td>
						<th>
							最后登录IP:
						</th>
						<td>
							${order.member.loginIp}
						</td>
					</tr>
					<tr>
						<th>
							预存款余额:
						</th>
						<td>
							${order.member.deposit?string(orderUnitCurrencyFormat)}
						</td>
						<th>
							积分:
						</th>
						<td>
							${order.member.point}
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<div class="buttonArea">
								<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
							</div>
						</td>
					</tr>
				<#else>
					<tr>
						<th>
							会员状态:
						</th>
						<td colspan="3">
							<span class="red">会员不存在</span>
						</td>
					</tr>
				</#if>
			</table>
		</div>
		<div class="tabContent">
			<table class="inputTable">
				<tr class="title">
					<th>货号</th>
					<th>商品名称</th>
					<th>价格</th>
					<th>购买数量</th>
				</tr>
				<#list order.orderItemSet as list>
					<tr>
						<td>
							<a href="${base}${list.productHtmlFilePath}" target="_blank">
								${list.productSn}
							</a>
						</td>
						<td>
							<a href="${base}${list.productHtmlFilePath}" target="_blank">
								${list.productName}
							</a>
						</td>
						<td>
							${list.productPrice?string(priceUnitCurrencyFormat)}
						</td>
						<td>
							${list.productQuantity}
						</td>
					</tr>
				</#list>
				<tr>
					<td colspan="4">
						<div class="buttonArea">
							<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="tabContent">
			<#if order.orderStatus != "COMPLETED" && order.orderStatus != "INVALID" && order.paymentStatus != "paid" && order.paymentStatus != "partRefund" && order.paymentStatus != "refunded">
				<form id="paymentForm" action="order!payment.action" method="post">
					<input type="hidden" name="order.id" value="${order.id}" />
					<table class="inputTable">
						<tr>
							<th>
								订单编号:
							</th>
							<td>
								${order.orderSn}
							</td>
							<th>
								下单时间:
							</th>
							<td>
								${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
							</td>
						</tr>
						<tr>
							<th>
								订单总金额:
							</th>
							<td>
								<span class="red">${order.totalAmount?string(orderUnitCurrencyFormat)}</span>
							</td>
							<th>
								已付金额:
							</th>
							<td>
								<span class="red">${order.paidAmount?string(orderUnitCurrencyFormat)}</span>
							</td>
						</tr>
						<tr>
							<th>
								收款银行:
							</th>
							<td>
								<input type="text" name="payment.bankName" class="formText" />
							</td>
							<th>
								收款账号:
							</th>
							<td>
								<input type="text" name="payment.bankAccount" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								支付类型:
							</th>
							<td>
								<select name="payment.paymentType">
									<#list nonRechargePaymentTypeList as list>
										<option value="${list}">
											${action.getText("PaymentType." + list)}
										</option>
									</#list>
								</select>
							</td>
							<th>
								支付方式:
							</th>
							<td>
								<select name="payment.paymentConfig.id">
									<#list allPaymentConfig as list>
										<option value="${list.id}"<#if (list == order.paymentConfig)!> selected</#if>>
											${list.name}
										</option>
									</#list>
								</select>
							</td>
						</tr>
						<tr>
							<th>
								付款金额:
							</th>
							<td>
								<input type="text" name="payment.totalAmount" class="formText {required: true, positive: true, max: ${order.totalAmount - order.paidAmount}}" value="${order.totalAmount - order.paidAmount}" />
							</td>
							<th>
								付款人:
							</th>
							<td>
								<input type="text" name="payment.payer" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								收款单备注:
							</th>
							<td colspan="3">
								<input type="text" name="payment.memo" class="formText" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="buttonArea">
									<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</#if>
		</div>
		<div class="tabContent">
			<#if order.orderStatus != "COMPLETED" && order.orderStatus != "INVALID" && order.shippingStatus != "shipped">
				<form id="shippingForm" action="order!shipping.action" method="post">
					<input type="hidden" name="order.id" value="${order.id}" />
					<table class="inputTable">
						<tr>
							<th>
								订单编号:
							</th>
							<td>
								${order.orderSn}
							</td>
							<th>
								下单时间:
							</th>
							<td>
								${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
							</td>
						</tr>
						<tr>
							<th>
								配送方式:
							</th>
							<td>
								<select name="shipping.deliveryType.id">
									<#list allDeliveryType as list>
										<option value="${list.id}"<#if (list == order.deliveryType)!> selected</#if>>
											${list.name}
										</option>
									</#list>
								</select>
							</td>
							<th>
								配送费用:
							</th>
							<td>
								<span class="red">${order.deliveryFee?string(orderUnitCurrencyFormat)}</span>
							</td>
						</tr>
						<tr>
							<th>
								物流公司:
							</th>
							<td>
								<select name="shipping.deliveryCorpName">
									<#list allDeliveryCorp as list>
										<option value="${list.name}"<#if (list == order.deliveryType.defaultDeliveryCorp)!> selected</#if>>
											${list.name}
										</option>
									</#list>
								</select>
							</td>
							<th>
								物流编号:
							</th>
							<td>
								<input type="text" name="shipping.deliverySn" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								物流费用:
							</th>
							<td colspan="3">
								<input type="text" name="shipping.deliveryFee" class="formText {min: 0}" value="${order.deliveryFee}" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								&nbsp;
							</td>
						</tr>
						<tr>
							<th>
								收货人姓名:
							</th>
							<td>
								<input type="text" name="shipping.shipName" class="formText {required: true}" value="${order.shipName}" />
							</td>
							<th>
								收货地区:
							</th>
							<td>
								<input type="text" name="shipping.shipAreaPath" class="areaSelect {required: true, messagePosition: '#shipAreaPathMessagePosition'}" value="${order.shipAreaPath}" />
								<span id="shipAreaPathMessagePosition"></span>
								<label class="requireField">*</label>
							</td>
						</tr>
						<tr>
							<th>
								收货地址:
							</th>
							<td>
								<input type="text" name="shipping.shipAddress" class="formText {required: true}" value="${order.shipAddress}" />
							</td>
							<th>
								邮编:
							</th>
							<td>
								<input type="text" name="shipping.shipZipCode" class="formText {required: true, zipCode: true}" value="${order.shipZipCode}" />
							</td>
						</tr>
						<tr>
							<th>
								电话:
							</th>
							<td>
								<input type="text" name="shipping.shipPhone" class="formText {requiredOne: '#shipMobile', phone: true}" value="${order.shipPhone}" />
							</td>
							<th>
								手机:
							</th>
							<td>
								<input type="text" name="shipping.shipMobile" id="shipMobile" class="formText {mobile: true}" value="${order.shipMobile}" />
							</td>
						</tr>
						<tr>
							<th>
								发货备注:
							</th>
							<td colspan="3">
								<input type="text" name="shipping.memo" class="formText" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								&nbsp;
							</td>
						</tr>
					</table>
					<table class="inputTable">
						<tr class="title">
							<th>货号</th>
							<th>商品名称</th>
							<th>购买数量</th>
							<th>当前库存</th>
							<th>已发货数</th>
							<th>本次发货数</th>
						</tr>
						<#list order.orderItemSet as list>
							<tr>
								<td>
									<input type="hidden" name="deliveryItemList[${list_index}].product.id" value="${list.product.id}" />
									<a href="${base}${list.productHtmlFilePath}" target="_blank">
										${list.productSn}
									</a>
								</td>
								<td>
									<a href="${base}${list.productHtmlFilePath}" target="_blank">
										${list.productName}
									</a>
								</td>
								<td>
									${list.productQuantity}
								</td>
								<td>
									<span title="被占用数: ${(list.product.freezeStore)}">${(list.product.store)!"-"}</span>
								</td>
								<td>
									${list.deliveryQuantity}
								</td>
								<td>
									<input type="text" name="deliveryItemList[${list_index}].deliveryQuantity" class="formText {required: true, min: 0, max: ${list.productQuantity - list.deliveryQuantity}}" value="${list.productQuantity - list.deliveryQuantity}" style="width: 50px;" />
								</td>
							</tr>
						</#list>
						<tr>
							<td colspan="6">
								<div class="buttonArea">
									<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</#if>
		</div>
		<div class="tabContent">
			<#if order.orderStatus != "COMPLETED" && order.orderStatus != "INVALID" && order.paymentStatus != "unpaid" && order.paymentStatus != "refunded">
				<form id="refundForm" action="order!refund.action" method="post">
					<input type="hidden" name="order.id" value="${order.id}" />
					<table class="inputTable">
						<tr>
							<th>
								订单编号:
							</th>
							<td>
								${order.orderSn}
							</td>
							<th>
								下单时间:
							</th>
							<td>
								${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
							</td>
						</tr>
						<tr>
							<th>
								订单总金额:
							</th>
							<td>
								<span class="red">${order.totalAmount?string(orderUnitCurrencyFormat)}</span>
							</td>
							<th>
								已付金额:
							</th>
							<td>
								<span class="red">${order.paidAmount?string(orderUnitCurrencyFormat)}</span>
							</td>
						</tr>
						<tr>
							<th>
								退款银行:
							</th>
							<td>
								<input type="text" name="refund.bankName" class="formText" />
							</td>
							<th>
								退款账号:
							</th>
							<td>
								<input type="text" name="refund.bankAccount" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								退款类型:
							</th>
							<td>
								<select name="refund.refundType">
									<#list refundTypeList as list>
										<option value="${list}">
											${action.getText("RefundType." + list)}
										</option>
									</#list>
								</select>
							</td>
							<th>
								退款方式:
							</th>
							<td>
								<select name="refund.paymentConfig.id">
									<#list allPaymentConfig as list>
										<option value="${list.id}"<#if (list == order.paymentConfig)!> selected</#if>>
											${list.name}
										</option>
									</#list>
								</select>
							</td>
						</tr>
						<tr>
							<th>
								退款金额:
							</th>
							<td>
								<input type="text" name="refund.totalAmount" class="formText {required: true, positive: true, max: ${order.paidAmount}}" value="${order.paidAmount}" />
							</td>
							<th>
								收款人:
							</th>
							<td>
								<input type="text" name="refund.payee" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								退款备注:
							</th>
							<td colspan="3">
								<input type="text" name="refund.memo" class="formText" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="buttonArea">
									<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</#if>
		</div>
		<div class="tabContent">
			<#if order.orderStatus != "COMPLETED" && order.orderStatus != "INVALID" && order.shippingStatus != "unshipped" && order.shippingStatus != "reshiped">
				<form id="reshipForm" action="order!reship.action" method="post">
					<input type="hidden" name="order.id" value="${order.id}" />
					<table class="inputTable">
						<tr>
							<th>
								订单编号:
							</th>
							<td>
								${order.orderSn}
							</td>
							<th>
								下单时间:
							</th>
							<td>
								${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
							</td>
						</tr>
						<tr>
							<th>
								配送方式:
							</th>
							<td>
								<select name="reship.deliveryType.id">
									<#list allDeliveryType as list>
										<option value="${list.id}"<#if (list == order.deliveryType)!> selected</#if>>
											${list.name}
										</option>
									</#list>
								</select>
							</td>
							<th>
								物流公司:
							</th>
							<td>
								<select name="reship.deliveryCorpName">
									<#list allDeliveryCorp as list>
										<option value="${list.name}">
											${list.name}
										</option>
									</#list>
								</select>
							</td>
						</tr>
						<tr>
							<th>
								物流费用:
							</th>
							<td>
								<input type="text" name="reship.deliveryFee" class="formText {min: 0}" value="${order.deliveryFee}" />
							</td>
							<th>
								物流编号:
							</th>
							<td>
								<input type="text" name="reship.deliverySn" class="formText" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								&nbsp;
							</td>
						</tr>
						<tr>
							<th>
								退货人姓名:
							</th>
							<td>
								<input type="text" name="reship.shipName" class="formText {required: true}" value="${order.shipName}" />
							</td>
							<th>
								退货地区:
							</th>
							<td>
								<input type="text" name="reship.shipAreaPath" class="areaSelect {required: true, messagePosition: '#shipAreaPathMessagePosition'}" value="${order.shipAreaPath}" />
								<span id="shipAreaPathMessagePosition"></span>
								<label class="requireField">*</label>
							</td>
						</tr>
						<tr>
							<th>
								退货地址:
							</th>
							<td>
								<input type="text" name="reship.shipAddress" class="formText {required: true}" value="${order.shipAddress}" />
							</td>
							<th>
								邮编:
							</th>
							<td>
								<input type="text" name="reship.shipZipCode" class="formText {required: true, zipCode: true}" value="${order.shipZipCode}" />
							</td>
						</tr>
						<tr>
							<th>
								电话:
							</th>
							<td>
								<input type="text" name="reship.shipPhone" class="formText {requiredOne: '#shipMobile', phone: true}" value="${order.shipPhone}" />
							</td>
							<th>
								手机:
							</th>
							<td>
								<input type="text" name="reship.shipMobile" id="shipMobile" class="formText {mobile: true}" value="${order.shipMobile}" />
							</td>
						</tr>
						<tr>
							<th>
								退货备注:
							</th>
							<td colspan="3">
								<input type="text" name="reship.memo" class="formText" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								&nbsp;
							</td>
						</tr>
					</table>
					<table class="inputTable">
						<tr class="title">
							<th>货号</th>
							<th>商品名称</th>
							<th>购买数量</th>
							<th>已发货数</th>
							<th>本次退货数</th>
						</tr>
						<#list order.orderItemSet as list>
							<tr>
								<td>
									<input type="hidden" name="deliveryItemList[${list_index}].product.id" value="${list.product.id}" />
									<a href="${base}${list.productHtmlFilePath}" target="_blank">
										${list.productSn}
									</a>
								</td>
								<td>
									<a href="${base}${list.productHtmlFilePath}" target="_blank">
										${list.productName}
									</a>
								</td>
								<td>
									${list.productQuantity}
								</td>
								<td>
									${list.deliveryQuantity}
								</td>
								<td>
									<input type="text" name="deliveryItemList[${list_index}].deliveryQuantity" class="formText {required: true, min: 0, max: ${list.deliveryQuantity}}" value="${list.deliveryQuantity}" style="width: 50px;" />
								</td>
							</tr>
						</#list>
						<tr>
							<td colspan="6">
								<div class="buttonArea">
									<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</#if>
		</div>
	</div>
</body>
</html>