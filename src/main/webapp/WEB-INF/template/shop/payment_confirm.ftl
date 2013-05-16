<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付确认 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/payment.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
</head>
<body class="paymentGateway">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="blank"></div>
		<div class="paymentGatewayDetail">
			<form action="${base}/shop/payment!gateway.action" method="post">
				<@s.token />
				<input type="hidden" name="paymentType" value="${paymentType}" />
				<input type="hidden" name="amountPayable" value="${amountPayable}" />
				<input type="hidden" name="paymentConfig.id" value="${(paymentConfig.id)!}" />
				<input type="hidden" name="order.id" value="${(order.id)!}" />
				<p>
					尊敬的<strong class="green">${loginMember.username}</strong>，
					您选择的支付方式为：<strong>${paymentConfig.name}</strong>
					<#if order != null><a href="${base}/shop/order!view.action?id=${order.id}">[查看订单详情]</a></#if>
				</p>
				<p>
					支付总金额：<strong class="red">${(amountPayable + paymentFee)?string(orderUnitCurrencyFormat)}</strong>
					<#if paymentConfig.paymentFeeType == "scale" && paymentConfig.paymentFee != 0>
						[含支付手续费费率：${paymentConfig.paymentFee}%]
					<#elseif paymentConfig.paymentFeeType == "fixed" && paymentConfig.paymentFee != 0>
						[含支付手续费：${paymentConfig.paymentFee?string(orderUnitCurrencyFormat)}]
					</#if>
				</p>
				<div class="buttonArea">
					<input type="submit" class="formButton" value="确认支付" />
				</div>
			</form>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>