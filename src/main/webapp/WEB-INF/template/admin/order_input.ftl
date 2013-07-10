<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑订单 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready( function() {
	
	var productTotalPrice = parseFloat("${order.productTotalPrice}");// 商品总价格
	var deliveryFee = parseFloat("${order.deliveryFee}");// 配送费用
	var paymentFee = parseFloat("${order.paymentFee}");// 支付费用
	
	var $areaSelect = $(".areaSelect");
	var $productTotalPrice = $("#productTotalPrice");
	var $totalAmount = $("#totalAmount");
	var $deliveryFeeInput = $("input[name='order.deliveryFee']");
	var $paymentFeeInput = $("input[name='order.paymentFee']");
	var $productPriceInput = $("input.productPrice");
	var $productQuantityInput = $("input.productQuantity");
	var $deleteOrderItem = $(".deleteOrderItem");
	
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "area!ajaxChildrenArea.action"// Json数据获取url
	});
	
	// 修改商品总价格
	function modifyProductTotalPrice() {
		productTotalPrice = 0.0;
		$productPriceInput.each(function(){
			var productPrice = $(this).val();
			var productQuantity = $(this).parent().parent().find("input.productQuantity").val();
			productTotalPrice = floatAdd(productTotalPrice, floatMul(productPrice, productQuantity));
		});
		$productTotalPrice.text(priceUnitCurrencyFormat(productTotalPrice));
	}
	
	// 修改订单总金额
	function modifyTotalAmount() {
		var totalAmount = floatAdd(floatAdd(productTotalPrice, deliveryFee), paymentFee);
		$totalAmount.text(priceUnitCurrencyFormat(totalAmount));
	}
	
	// 根据配送费用修改订单总金额
	$deliveryFeeInput.change( function() {
		$this = $(this);
		var deliveryFeeValue = $this.val();
		var reg = /^(([0-9]+\.?[0-9]+)|[0-9])$/;
		if (!reg.test(deliveryFeeValue)) {
			$this.val(deliveryFee);
			$.tip("配送费用输入有误！");
		} else {
			deliveryFee = deliveryFeeValue;
			modifyTotalAmount();
		}
	});
	
	// 根据支付费用修改订单总金额
	$paymentFeeInput.change( function() {
		$this = $(this);
		var paymentFeeValue = $this.val();
		var reg = /^(([0-9]+\.?[0-9]+)|[0-9])$/;
		if (!reg.test(paymentFeeValue)) {
			$this.val(paymentFee);
			$.tip("支付手续费输入有误！");
		} else {
			paymentFee = paymentFeeValue;
			modifyTotalAmount();
		}
	});
	
	// 记录初始商品价格
	$productPriceInput.each(function(){
		$this = $(this);
		$this.data("previousProductPrice", $this.val());
	});
	
	// 记录初始商品购买数
	$productQuantityInput.each(function(){
		$this = $(this);
		$this.data("previousProductQuantity", $this.val());
	});
	
	// 根据商品价格修改商品总价格、订单总金额
	$productPriceInput.change( function() {
		$this = $(this);
		var productPriceValue = $this.val();
		var reg = /^(([0-9]+\.?[0-9]+)|[0-9])$/;
		if (!reg.test(productPriceValue)) {
			var previousProductPrice = $this.data("previousProductPrice");
			$this.val(previousProductPrice);
			$.tip("商品价格输入有误！");
		} else {
			$this.data("previousProductPrice", productPriceValue);
			modifyProductTotalPrice();
			modifyTotalAmount();
		}
	});
	
	// 根据商品数量修改商品总价格、订单总金额
	$productQuantityInput.change( function() {
		$this = $(this);
		var productQuantityValue = $this.val();
		var availableStore = $this.metadata().availableStore;
		var reg = /^[0-9]*[1-9][0-9]*$/;
		if (!reg.test(productQuantityValue)) {
			var previousProductQuantity = $this.data("previousProductQuantity");
			$this.val(previousProductQuantity);
			$.tip("商品数量输入有误！");
		} else {
			if (availableStore != null && parseInt(productQuantityValue) > parseInt(availableStore)) {
				var previousProductQuantity = $this.data("previousProductQuantity");
				$this.val(previousProductQuantity);
				$.tip("商品数量超出可用库存数！");
				return false;
			}
			$this.data("previousProductQuantity", productQuantityValue);
			modifyProductTotalPrice();
			modifyTotalAmount();
		}
	});
	
	// 删除订单项
	$deleteOrderItem.click( function() {
		$this = $(this);
		if ($productPriceInput.length == 1) {
			$.tip("请保留至少一个商品！");
			return false;
		}
		if (confirm("您确定要移除此商品吗？") == true) {
			$this.parent().parent().remove();
			$productPriceInput = $("input.productPrice");
			$productQuantityInput = $("input.productQuantity");
			modifyProductTotalPrice();
			modifyTotalAmount();
		}
		return false;
	});

});
</script>
<#if !id??>
	<#assign isAdd = true />
	<#assign isEdit = false />

<#else>
	<#assign isAdd = false />
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>编辑订单</h1>
		</div>
		<form id="inputForm" class="validate" action="order!update.action" method="post">
			<input type="hidden" name="order.id" value="${order.id}" />
			<div class="blank"></div>
			<ul class="tab">
				<li>
					<input type="button" value="订单信息" hidefocus="true" />
				</li>
				<li>
					<input type="button" value="商品信息" hidefocus="true" />
				</li>
			</ul>
			<table class="inputTable tabContent">
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
						<span id="totalAmount" class="red">${order.totalAmount?string(orderUnitCurrencyFormat)}</span>
					</td>
				</tr>
				<tr>
					<th>
						配送方式:
					</th>
					<td>
						<select name="order.deliveryType.id">
							<#list allDeliveryType as list>
								<option value="${list.id}"<#if (list == order.deliveryType)!> selected</#if>>
									${list.name}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
					<th>
						支付方式:
					</th>
					<td>
						<select name="order.paymentConfig.id">
							<option value="">
								货到付款
							</option>
							<#list allPaymentConfig as list>
								<option value="${list.id}"<#if (list == order.paymentConfig)!> selected</#if>>
									${list.name}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						配送费用:
					</th>
					<td>
						<input type="text" name="order.deliveryFee" class="formText {required: true, min: 0}" value="${order.deliveryFee}" />
						<label class="requireField">*</label>
					</td>
					<th>
						支付手续费:
					</th>
					<td>
						<input type="text" name="order.paymentFee" class="formText {required: true, min: 0}" value="${order.paymentFee}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						商品重量:
					</th>
					<td>
						<input type="text" name="order.productWeight" class="formText {required: true, min: 0, messagePosition: '#productWeightMessagePosition'}" value="${order.productWeight}" />
						<select name="order.productWeightUnit">
							<#list allWeightUnit as list>
								<option value="${list}"<#if list == order.productWeightUnit> selected </#if>>
									${action.getText("WeightUnit." + list)}
								</option>
							</#list>
						</select>
						<span id="productWeightMessagePosition"></span>
						<label class="requireField">*</label>
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
						<input type="text" name="order.shipName" class="formText {required: true}" value="${order.shipName}" />
					</td>
					<th>
						收货地区:
					</th>
					<td>
						<input type="text" name="order.shipAreaPath" class="areaSelect hidden {required: true, messagePosition: '#areaMessagePosition'}" value="${(order.shipAreaPath)!}" />
						<span id="areaMessagePosition"></span>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						收货地址:
					</th>
					<td>
						<input type="text" name="order.shipAddress" class="formText {required: true}" value="${order.shipAddress}" />
					</td>
					<th>
						邮编:
					</th>
					<td>
						<input type="text" name="order.shipZipCode" class="formText {required: true, zipCode: true}" value="${order.shipZipCode}"  />
					</td>
				</tr>
				<tr>
					<th>
						电话:
					</th>
					<td>
						<input type="text" name="order.shipPhone" class="formText {requiredOne: '#shipMobile', phone: true, messages: {requiredOne: '电话、手机必须填写其中一项！'}}" value="${order.shipPhone}" />
					</td>
					<th>
						手机:
					</th>
					<td>
						<input type="text" id="shipMobile" name="order.shipMobile" class="formText {mobile: true, messages: {mobile: '手机格式错误！'}}" value="${order.shipMobile}"  />
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
			<table class="inputTable tabContent">
				<tr class="title">
					<th>货号</th>
					<th>商品名称</th>
					<th>价格</th>
					<th>购买数量</th>
					<th>删除</th>
				</tr>
				<#list order.orderItemSet as list>
					<tr>
						<td>
							<input type="hidden" name="orderItemList[${list_index}].id" value="${list.id}" />
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
							<input type="text" name="orderItemList[${list_index}].productPrice" class="formText productPrice" value="${list.productPrice}" style="width: 50px;" />
						</td>
						<td>
							<#if list.product.store != null>
								<#if (systemConfig.storeFreezeTime == "payment" && order.paymentStatus == "unpaid") || (systemConfig.storeFreezeTime == "ship" && order.shippingStatus == "unshipped")>
									<#assign availableStore = list.product.store - list.product.freezeStore />
								<#else>
									<#assign availableStore = list.product.store - list.product.freezeStore + list.productQuantity />
								</#if>
							</#if>
							<input type="text" name="orderItemList[${list_index}].productQuantity" class="formText productQuantity <#if list.product.store != null>{availableStore: ${availableStore}}</#if>" value="${list.productQuantity}" style="width: 50px;" />
						</td>
						<td>
							<a href="javascript: void(0);" class="deleteOrderItem">删除</a>
						</td>
					</tr>
				</#list>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>