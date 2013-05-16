<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${product.name} - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<#if (product.metaKeywords)! != ""><meta name="keywords" content="${product.metaKeywords}" /></#if>
<#if (product.metaDescription)! != ""><meta name="description" content="${product.metaDescription}" /></#if>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/product_content.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/product.js"></script>
<script type="text/javascript">
$().ready( function() {
	
	// 添加商品浏览记录
	$.addProductHistory("<#if (product.name?length <= 15)>${product.name}<#else>${product.name[0..12]}...</#if>", "${base}${product.htmlFilePath}");

})
</script>
</head>
<body class="productContent">
	<div id="addCartItemTip" class="addCartItemTip">
		<div class="top">
			<div class="tipClose addCartItemTipClose"></div>
		</div>
		<div class="middle">
			<p>
				<span id="addCartItemTipMessageIcon">&nbsp;</span>
				<span id="addCartItemTipMessage"></span>
			</p>
			<p id="addCartItemTipInfo" class="red"></p>
			<input type="button" class="formButton tipClose" value="继续购物" hidefocus="true" />
			<input type="button" class="formButton" onclick="location.href='${base}/shop/cart_item!list.action'" value="进入购物车" hidefocus="true" />
		</div>
		<div class="bottom"></div>
	</div>
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="bodyLeft">
			<div class="productCategory">
            	<div class="top">商品分类</div>
            	<div class="middle clearfix">
            		<ul class="menu">
            			<#list rootProductCategoryList as list>
            				<li class="mainCategory">
								<a href="${base}/shop/product!list.action?id=${list.id}">${list.name}</a>
							</li>
							<#if (list.children != null && list.children?size > 0)>
								<#list list.children as list>
									<li>
										<a href="${base}/shop/product!list.action?id=${list.id}"><span class="icon">&nbsp;</span>${list.name}</a>
										<#if (list.children != null && list.children?size > 0)>
											<ul>
												<#list list.children as list>
													<li>
														<#if (list.name?length < 15)>
															<a href="${base}/shop/product!list.action?id=${list.id}"><span class="icon">&nbsp;</span>${list.name}</a>
														<#else>
															<a href="${base}/shop/product!list.action?id=${list.id}"><span class="icon">&nbsp;</span>${list.name[0..12]}...</a>
														</#if>
													</li>
												</#list>
											</ul>
										</#if>
									</li>
									<#if list_index + 1 == 5>
										<#break />
									</#if>
								</#list>
							</#if>
							<#if list_index + 1 == 3>
								<#break />
							</#if>
            			</#list>
					</ul>
            	</div>
                <div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div class="hotProduct">
				<div class="top">热销排行</div>
				<div class="middle clearfix">
					<ul>
						<#list (hotProductList)! as list>
							<li class="number${list_index + 1}">
								<#if (list.name?length < 15)>
									<span class="icon">&nbsp;</span><a href="${base}${list.htmlFilePath}" title="${list.name}">${list.name}</a>
								<#else>
									<span class="icon">&nbsp;</span><a href="${base}${list.htmlFilePath}" title="${list.name}">${list.name[0..12]}...</a>
								</#if>
							</li>
							<#if list_index + 1 == 10>
								<#break />
							</#if>
						</#list>
					</ul>
				</div>
				<div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div class="productHistory">
				<div class="top">浏览记录</div>
				<div class="middle clearfix">
					<ul id="productHistoryListDetail"></ul>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="bodyRight">
			<div class="listBar">
				<div class="left"></div>
				<div class="middle">
					<div class="path">
						<a href="${base}/" class="home"><span class="icon">&nbsp;</span>首页</a>>
						<#list pathList as list>
							<a href="${base}/shop/product!list.action?id=${list.id}">${list.name}</a>>
						</#list>
					</div>
				</div>
				<div class="right"></div>
			</div>
			<div class="blank"></div>
			<div class="productTop">
				<div class="productTopLeft">
					<div class="productImage">
	                	<#list product.productImageList as list>
	                		<a href="${base}${list.bigProductImagePath}" class="tabContent zoom<#if (list_index > 0)> nonFirst</#if>">
								<img src="${base}${list.smallProductImagePath}" />
							</a>
						</#list>
						<#if product.productImageList == null>
	            			<a href="${base}${systemConfig.defaultBigProductImagePath}" class="zoom">
								<img src="${base}${systemConfig.defaultSmallProductImagePath}" />
							</a>
	                	</#if>
                	</div>
					<div class="thumbnailProductImage">
						<a class="prev browse" href="javascript:void(0);" hidefocus="true"></a>
						<div class="scrollable">
							<ul class="items productImageTab">
								<#if (product.productImageList == null)!>
									<li>
										<img src="${base}${systemConfig.defaultThumbnailProductImagePath}" />
									</li>
	                        	</#if>
	                        	<#list (product.productImageList)! as list>
									<li>
										<img src="${base}${list.thumbnailProductImagePath}" />
									</li>
								</#list>
							</ul>
						</div>
						<a class="next browse" href="javascript:void(0);" hidefocus="true"></a>
					</div>
				</div>
				<div class="productTopRight">
					<h1>
						<#if (product.name?length > 50)>${product.name[0..46]}...<#else>${product.name}</#if>
					</h1>
					<ul class="productAttribute">
						<#assign index = 1 />
						<#list (product.productType.enabledProductAttributeList)! as list>
							<#if (product.productAttributeMap.get(list) != null)!>
	                    		<li>
	                    			<strong>${list.name}:</strong>
	                				<#list (product.productAttributeMap.get(list))! as attributeOptionList>
	                            		${attributeOptionList}&nbsp;
	                            		<#if (attributeOptionList_index == 3) >
											<#break>
										</#if>
	                            	</#list>
	                            </li>
	                            <#if index == 5 >
									<#break>
								</#if>
								<#assign index = index + 1 />
							</#if>
						</#list>
                        <#if (product.productType.productAttributeList != null && product.productType.productAttributeList?size > 0)!>
                        	<li><a href="#productAttribute" id="moreProductAttribute">更多参数>></a></li>
                        </#if>
					</ul>
					<div class="blank"></div>
					<div class="productInfo">
						<div class="left"></div>
						<div class="right">
							<div class="top">
								销 售 价：<span class="price">${product.price?string(priceCurrencyFormat)}</span>
								市 场 价：<span class="marketPrice">${product.marketPrice?string(priceCurrencyFormat)}</span>
							</div>
							<div class="bottom">
								<#if (product.weight > 0)>
									重 量：<span class="weight">${product.weight} ${bundle("WeightUnit." + product.weightUnit)}</span>
								</#if>
								<#if (product.point != 0)>
									积 分：<span>${product.point}</span>
								</#if>
							</div>
						</div>
					</div>
					<div class="blank"></div>
					<div class="productNumber">
						货号: <span>${product.productSn}</span>
                        ${("品牌: <span>" + product.brand.name + "</span>")!}
					</div>
					<div class="blank"></div>
					<div class="productBuy">
						<div class="buyCount">
							我要购买: <input type="text" id="quantity" value="1" /> 件
						</div>
						<#if product.isOutOfStock>
							<input type="button" class="outOfStockButton" value="" hidefocus="true" />
						<#else>
							<input type="button" class="addCartItemButton addCartItem {id: '${product.id}'}" value="" hidefocus="true" />
						</#if>
                        <input type="button" class="addFavoriteButton addFavorite {id: '${product.id}'}" value="" hidefocus="true" />
					</div>
				</div>
			</div>
			<div class="blank"></div>
			<div class="productBottom">
				<ul class="productAttributeTab">
					<li>
						<a href="javascript:void(0);" class="current" hidefocus="true">商品介绍</a>
					</li>
					<li>
						<a href="javascript:void(0);" name="productAttribute" hidefocus="true">规格参数</a>
					</li>
				</ul>
				<div class="tabContent productDescription">
					${product.description}
				</div>
				<div class="tabContent productAttribute">
					<table class="productAttributeTable">
						<#list (product.productType.enabledProductAttributeList)! as list>
							<#if (product.productAttributeMap.get(list) != null)!>
								<tr>
									<th>${list.name}</th>
									<td>
										<#list (product.productAttributeMap.get(list))! as attributeOptionList>
											${attributeOptionList}&nbsp;
										</#list>
									</td>
								</tr>
							</#if>
						</#list>
					</table>
				</div>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>