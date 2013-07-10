<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看退货单 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>查看退货单</h1>
		</div>
		<table class="inputTable">
			<tr>
				<th>
					退货编号:
				</th>
				<td>
					${reship.reshipSn}
				</td>
				<th>
					订单编号:
				</th>
				<td>
					${(reship.order.orderSn)!}
				</td>
			</tr>
			<tr>
				<th>
					退货日期:
				</th>
				<td>
					${reship.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
				<th>
					配送方式名称:
				</th>
				<td>
					${reship.deliveryTypeName}
				</td>
			</tr>
			<tr>
				<th>
					物流公司名称:
				</th>
				<td>
					${reship.deliveryCorpName}
				</td>
				<th>
					物流编号:
				</th>
				<td>
					${reship.deliverySn}
				</td>
			</tr>
			<tr>
				<th>
					物流费用:
				</th>
				<td>
					${reship.deliveryFee?string(orderUnitCurrencyFormat)}
				</td>
				<th>
					退货人姓名:
				</th>
				<td>
					${reship.shipName}
				</td>
			</tr>
			<tr>
				<th>
					退货地区:
				</th>
				<td>
					${reship.shipArea}
				</td>
				<th>
					退货地址:
				</th>
				<td>
					${reship.shipAddress}
				</td>
			</tr>
			<tr>
				<th>
					邮编:
				</th>
				<td>
					${reship.shipZipCode}
				</td>
				<th>
					电话:
				</th>
				<td>
					${reship.shipPhone}
				</td>
			</tr>
			<tr>
				<th>
					手机:
				</th>
				<td>
					${reship.shipMobile}
				</td>
				<th>
					备注:
				</th>
				<td>
					${reship.memo}
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
				<th>退货数</th>
			</tr>
			<#list reship.deliveryItemSet as list>
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
						${list.deliveryQuantity}
					</td>
				</tr>
			</#list>
		</table>
		<div class="buttonArea">
			<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
		</div>
	</div>
</body>
</html>