<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看购物车 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/cart_item.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $listTable = $(".listTable");
	var $quantity = $("input.quantity");
	var $changeQuantityTip = $("#changeQuantityTip");
	var $changeQuantityTipClose = $("#changeQuantityTipClose");
	var $changeQuantityTipTopMessage = $("#changeQuantityTipTopMessage");
	var $changeQuantityTipBottomMessage = $("#changeQuantityTipBottomMessage");
	var $deleteCartItem = $(".deleteCartItem");
	var $clearCartItem = $("#clearCartItem");
	var $totalQuantity = $("#totalQuantity");
	var $totalPoint = $("#totalPoint");
	var $totalPrice = $("#totalPrice");
	var $orderInfoButton = $("#orderInfoButton");
	
	
	// 记录初始商品购买数
	$quantity.each(function(){
		$this = $(this);
		$this.data("previousQuantity", $this.val());
	});
	
	// 修改商品数量
	$quantity.change( function() {
		$this = $(this);
		var id = $this.metadata().id;
		var quantity = $this.val();
		
		var x = $this.offset().left - 63;
		var y = $this.offset().top + 25;
		$changeQuantityTip.css({"left" :x, "top" :y});
		
		var reg = /^[0-9]*[1-9][0-9]*$/;
		if (!reg.test(quantity)) {
			var previousQuantity = $this.data("previousQuantity");
			$this.val(previousQuantity);
			$changeQuantityTipTopMessage.text("商品数量修改失败！");
			$changeQuantityTipBottomMessage.text("商品数量必须为正整数！");
			$changeQuantityTip.fadeIn();
			setTimeout(function() {$changeQuantityTip.fadeOut()}, 3000);
			return false;
		}
		$.ajax({
			url: "cart_item!ajaxEdit.action",
			data: {"id": id, "quantity": quantity},
			dataType: "json",
			async: false,
			beforeSend: function() {
				$quantity.attr("disabled", true);
			},
			success: function(data) {
				if (data.status == "success") {
					$this.data("previousQuantity", quantity);
					$this.parent().parent().find(".subtotalPrice").text(data.subtotalPrice);
					$this.next(".storeInfo").empty();
					$.flushCartItemList();
					$totalQuantity.text(data.totalQuantity);
					$totalPoint.text(data.totalPoint);
					$totalPrice.text(data.totalPrice);
					$changeQuantityTipTopMessage.text("商品数量修改成功！");
					$changeQuantityTipBottomMessage.text("商品总金额：" + data.totalPrice);
					$changeQuantityTip.fadeIn();
					setTimeout(function() {$changeQuantityTip.fadeOut()}, 3000);
				} else {
					var previousQuantity = $this.data("previousQuantity");
					$this.val(previousQuantity);
					$changeQuantityTipTopMessage.text("商品数量修改失败！");
					$changeQuantityTipBottomMessage.text(data.message);
					$changeQuantityTip.fadeIn();
					setTimeout(function() {$changeQuantityTip.fadeOut()}, 3000);
				}
				$quantity.attr("disabled", false);
			}
		});
	});
	
	// 修改商品数量提示框隐藏
	$changeQuantityTipClose.click( function() {
		$changeQuantityTip.fadeOut();
		return false;
	});
	
	// 删除购物车项
	$deleteCartItem.click( function() {
		$this = $(this);
		var id = $this.metadata().id;
		if (confirm("您确定要移除此商品吗？") == true) {
			$.ajax({
				url: "cart_item!ajaxDelete.action",
				data: {"id": id},
				dataType: "json",
				async: false,
				success: function(data) {
					if (data.status == "success") {
						$.flushCartItemList();
						$this.parent().parent().remove();
						$totalQuantity.text(data.totalQuantity);
						$totalPoint.text(data.totalPoint);
						$totalPrice.text(data.totalPrice);
					}
					$.tip(data.status, data.message);
				}
			});
		}
	});
	
	// 清空购物车项
	$clearCartItem.click( function() {
		if (confirm("您确定要清空购物车吗？") == true) {
			$.ajax({
				url: "cart_item!ajaxClear.action",
				dataType: "json",
				async: false,
				success: function(data) {
					if (data.status == "success") {
						$.flushCartItemList();
						$(".listTable tr:gt(0)").remove();
						$listTable.append('<tr><td class="noRecord" colspan="5">购物车目前没有加入任何商品！</td></tr>');
						$orderInfoButton.remove();
						$clearCartItem.remove();
					}
					$.tip(data.status, data.message);
				}
			});
		}
	});
	
	// 结算前检测购物车状态
	$orderInfoButton.click( function() {
		var $this = $(this);
		if (parseInt($totalQuantity.text()) < 1) {
			$.message("error", "购物车目前没有加入任何商品！");
			return false;
		}
		if ($.cookie("loginMemberUsername") == null) {
			$.cookie("redirectionUrl", $(this).attr("href"), {path: "/"});
			$("#loginWindow").jqmShow();
			return false; 
		}
	});

});
</script>
</head>
<body class="cartItemList">
	<div id="changeQuantityTip" class="changeQuantityTip">
		<div class="tipArrow"></div>
		<div class="tipDetail">
			<div id="changeQuantityTipClose" class="tipClose"></div>
			<p id="changeQuantityTipTopMessage"></p>
			<p id="changeQuantityTipBottomMessage" class="red"></p>
		</div>
	</div>
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="cartItemListBar"></div>
		<div class="blank"></div>
		<div class="cartItemListDetail">
			<div class="top">
				<div class="topLeft"></div>
				<div class="topMiddle">已放入购物车的商品:</div>
				<div class="topRight"></div>
			</div>
			<div class="middle">
				<table class="listTable">
					<tr>
						<th>商品</th>
						<th>销售价格</th>
						<#if (loginMember.memberRank.preferentialScale != 100)!>
							<th>优惠价格</th>
						</#if>
						<th>数量</th>
						<th>小计</th>
						<th>删除</th>
					</tr>
					<#list cartItemList as list>
						<tr>
							<td class="productName">
								<a href="${base}${list.product.htmlFilePath}" target="_blank">
									<img src="${base}${(list.product.productImageList[0].thumbnailProductImagePath)!systemConfig.defaultThumbnailProductImagePath}" />
									${list.name}
								</a>
							</td>
							<#if (loginMember.memberRank.preferentialScale != 100)!>
								<td class="priceTd">
									<span class="lineThrough">${list.product.price?string(priceCurrencyFormat)}</span>
								</td>
								<td class="priceTd">
									${list.preferentialPrice?string(priceCurrencyFormat)}
								</td>
							<#else>
								<td class="priceTd">
									${list.product.price?string(priceCurrencyFormat)}
								</td>
							</#if>
							<td>
								<input type="text" name="quantity" class="formText quantity {id: '${list.product.id}'}" value="${list.quantity}">
								<#if (list.product.store != null && list.product.freezeStore + list.quantity > list.product.store)>
									<strong class="storeInfo red">[库存不足]</strong>
								</#if>
							</td>
							<td>
								<span class="subtotalPrice">${list.subtotalPrice?string(orderCurrencyFormat)}</span>
							</td>
							<td>
								<a class="deleteCartItem {id: '${list.product.id}'}" href="javascript: void(0);">删除</a>
							</td>
						</tr>
					</#list>
					<#if (cartItemList == null || cartItemList?size == 0)!>
						<tr>
							<td class="noRecord" colspan="<#if (loginMember.memberRank.preferentialScale != 100)!>6<#else>5</#if>">购物车目前没有加入任何商品!</td>
						</tr>
					<#else>
						<tr>
							<td class="info" colspan="<#if (loginMember.memberRank.preferentialScale != 100)!>6<#else>5</#if>">
								商品共计：<span id="totalQuantity" class="red">${totalQuantity}</span> 件&nbsp;&nbsp;&nbsp;&nbsp;
								<#if systemConfig.pointType != "disable">
									积分：<span id="totalPoint" class="red">${totalPoint}</span>&nbsp;&nbsp;&nbsp;&nbsp;
								</#if>
								商品总金额(不含运费)：<span id="totalPrice" class="red">${totalPrice?string(orderUnitCurrencyFormat)}</span>
							</td>
						</tr>
					</#if>
				</table>
				<div class="blank"></div>
				<a class="continueShopping" href="${base}/"><span class="icon">&nbsp;</span>继续购物</a>
				<#if (cartItemList != null && cartItemList?size > 0)!>
					<a id="clearCartItem" class="clearCartItem" href="javascript: void(0);"><span class="icon">&nbsp;</span>清空购物车</a>
					<a id="orderInfoButton" class="formButton" href="order!info.action">去结算</a>
				</#if>
				<div class="clearfix"></div>
			</div>
			<div class="bottom">
				<div class="bottomLeft"></div>
				<div class="bottomMiddle"></div>
				<div class="bottomRight"></div>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>