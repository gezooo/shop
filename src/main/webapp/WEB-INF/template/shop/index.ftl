<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${(systemConfig.shopName)!} - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<#if (systemConfig.metaKeywords)! != ""><meta name="keywords" content="${systemConfig.metaKeywords}" /></#if>
<#if (systemConfig.metaDescription)! != ""><meta name="description" content="${systemConfig.metaDescription}" /></#if>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/index.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/article.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/index.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/product.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	$(".slider .scrollable").scrollable({
		circular: true,
		speed: 500
	}).autoscroll({
		autoplay: true,
		interval: 4000
	}).navigator();
	
	$(".hotProduct .scrollable").scrollable({
		circular: true,
		speed: 500
	});
	
	$(".newProduct ul.newProductTab").tabs(".newProduct .newProductTabContent", {
		effect: "fade",// 逐渐显示动画
		fadeInSpeed: 500,// 动画显示速度
		event: "mouseover"// 触发tab切换的事件
	});

})
</script>
</head>
<body class="index">
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
														<a href="${base}/shop/product!list.action?id=${list.id}"><span class="icon">&nbsp;</span>${list.name}</a>
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
		</div>
		<div class="bodyRight">
			<div class="slider">
				<div class="scrollable">
					<div class="items">
						<div>
							<img src="${base}/upload/image/banner1.jpg" />
						</div>
						<div>
							<img src="${base}/upload/image/banner2.jpg" />
						</div>
						<div>
							<img src="${base}/upload/image/banner3.jpg" />
						</div>
					</div>
					<div class="navi"></div>
					<div class="prevNext">
						<a class="prev browse left"></a>
						<a class="next browse right"></a>
					</div>
				</div>
			</div>
			<div class="blank"></div>
			<div class="hotProduct">
				<div class="title">
					<strong>热卖商品</strong>HOT
				</div>
				<a class="prev browse"></a>
				<div class="scrollable">
					<div class="items">
						<#list hotProductList as list>
							<#if list_index + 1 == 1>
								<div>
								<ul>
							</#if>
							<li>
								<a href="${base}${list.htmlFilePath}">
									<img src="${base}${(list.productImageList[0].thumbnailProductImagePath)!systemConfig.defaultThumbnailProductImagePath}" alt="${list.name}" />
									<#if (list.name?length < 12)>
										<p title="${list.name}">${list.name}</p>
									<#else>
										<p title="${list.name}">${list.name[0..9]}...</p>
									</#if>
								</a>
							</li>
							<#if (list_index + 1) % 4 == 0 && list_has_next && list_index + 1 != 12>
								</ul>
								</div>
								<div>
								<ul>
							</#if>
							<#if ((list_index + 1) % 4 == 0 && !list_has_next) || list_index + 1 == 12>
								</ul>
								</div>
								<#break />
							</#if>
						</#list>
					</div>
				</div>
				<a class="next browse"></a>
			</div>
		</div>
		<div class="blank"></div>
		<img src="${base}/upload/image/banner4.jpg" />
		<div class="blank"></div>
		<div class="newProduct">
			<div class="left">
				<ul class="newProductTab">
					<#list rootProductCategoryList as list>
						<li>
							${list.name}
						</li>
						<#if list_index + 1 == 4>
							<#break />
						</#if>
					</#list>
				</ul>
			</div>
			<div class="right">
				<#list rootProductCategoryList as list>
					<ul class="newProductTabContent">
						<#list newProductMap[list.id] as list>
							<li>
								<a href="${base}${list.htmlFilePath}">
									<img src="${base}${(list.productImageList[0].thumbnailProductImagePath)!systemConfig.defaultThumbnailProductImagePath}" alt="${list.name}" />
									<#if (list.name?length < 12)>
										<p title="${list.name}">${list.name}</p>
									<#else>
										<p title="${list.name}">${list.name[0..9]}...</p>
									</#if>
								</a>
							</li>
							<#if list_index + 1 == 4>
								<#break>
							</#if>
						</#list>
					</ul>
					<#if list_index + 1 == 4>
						<#break />
					</#if>
				</#list>
			</div>
		</div>
		<div class="blank"></div>
		<div class="bodyLeft">
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
			<div class="hotArticle">
				<div class="top">热点文章</div>
				<div class="middle clearfix">
					<ul>
						<#list (hotArticleList)! as list>
							<li class="number${list_index + 1}">
								<#if (list.title?length < 15)>
									<span class="icon">&nbsp;</span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a>
								<#else>
									<span class="icon">&nbsp;</span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..12]}...</a>
								</#if>
							</li>
							<#if list_index + 1 == 10>
								<#break/>
							</#if>
						</#list>
					</ul>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="bodyRight">
			<div class="bestProduct">
				<div class="top">
					<strong>精品推荐</strong>BEST
				</div>
				<div class="middle">
					<ul>
						<#list (bestProductList)! as list>
							<li>
								<a href="${base}${list.htmlFilePath}">
									<img src="${base}${(list.productImageList[0].thumbnailProductImagePath)!systemConfig.defaultThumbnailProductImagePath}" alt="${list.name}" />
									<#if (list.name?length < 12)>
										<p title="${list.name}">${list.name}</p>
									<#else>
										<p title="${list.name}">${list.name[0..9]}...</p>
									</#if>
									<p class="red">${list.price?string(priceCurrencyFormat)}</p>
								</a>
							</li>
							<#if list_index + 1 == 12>
								<#break />
							</#if>
						</#list>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>