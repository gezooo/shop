<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看收款单 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>查看收款单</h1>
		</div>
		<table class="inputTable">
			<tr>
				<th>
					支付编号:
				</th>
				<td>
					${payment.paymentSn}
				</td>
				<th>
					订单编号:
				</th>
				<td>
					${(payment.order.orderSn)!}
				</td>
			</tr>
			<tr>
				<th>
					支付类型:
				</th>
				<td>
					${action.getText("PaymentType." + payment.paymentType)}
				</td>
				<th>
					支付方式:
				</th>
				<td>
					${payment.paymentConfigName}
				</td>
			</tr>
			<tr>
				<th>
					收款银行名称:
				</th>
				<td>
					${payment.bankName}
				</td>
				<th>
					收款银行账号:
				</th>
				<td>
					${payment.bankAccount}
				</td>
			</tr>
			<tr>
				<th>
					支付金额:
				</th>
				<td>
					${payment.totalAmount?string(orderUnitCurrencyFormat)}
				</td>
				<th>
					支付手续费:
				</th>
				<td>
					${payment.paymentFee?string(orderUnitCurrencyFormat)}
				</td>
			</tr>
			<tr>
				<th>
					付款人:
				</th>
				<td>
					${payment.payer}
				</td>
				<th>
					操作员:
				</th>
				<td>
					${payment.operator}
				</td>
			</tr>
			<tr>
				<th>
					支付状态:
				</th>
				<td>
					${action.getText("PaymentStatus." + payment.paymentStatus)}
				</td>
				<th>
					备注:
				</th>
				<td>
					${payment.memo}
				</td>
			</tr>
		</table>
		<div class="buttonArea">
			<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
		</div>
	</div>
</body>
</html>