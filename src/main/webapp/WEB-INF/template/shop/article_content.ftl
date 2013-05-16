<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${article.title} - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<#if (article.metaKeywords)! != ""><meta name="keywords" content="${article.metaKeywords}" /></#if>
<#if (article.metaDescription)! != ""><meta name="description" content="${article.metaDescription}" /></#if>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/article.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/article_content.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/article.js"></script>
<script type="text/javascript">
$().ready( function() {

	$hits = $("#hits");

	// 统计文章点击数
	$.ajax({
		url: "${base}/shop/article!ajaxCounter.action?id=${article.id}",
		dataType: "json",
		async: false,
		success: function(data) {
			if (data.status == "success") {
				$hits.text(data.hits);
			}
		}
	});

});
</script>
</head>
<body class="articleContent">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="bodyLeft">
			<div class="recommendArticle">
				<div class="top">推荐文章</div>
				<div class="middle clearfix">
					<ul>
						<#list (recommendArticleList)! as list>
							<li>
								<#if (list.title?length < 15)>
									<span class="icon">&nbsp;</span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a>
								<#else>
									<span class="icon">&nbsp;</span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..12]}...</a>
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
								<#break />
							</#if>
						</#list>
					</ul>
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
							<a href="${base}/shop/article!list.action?id=${list.id}">${list.name}</a>>
						</#list>
					</div>
					<div class="articleSearch">
						<form id="articleSearchForm" action="${base}/shop/article!search.action" method="get">
							<input type="text" name="pager.keyword" id="articleSearchKeyword" class="keyword" value="请输入关键词..." />
							<input type="submit" class="searchButton" value="" />
						</form>
					</div>
				</div>
				<div class="right"></div>
			</div>
			<div class="blank"></div>
			<div class="articleContentDetail">
				<div class="articleContentTop"></div>
				<div class="articleContentMiddle">
					<div class="title">${article.title}<#if (pageCount > 1 && pageNumber > 1)>(${pageNumber})</#if></div>
                    <div class="blank"></div>
                    <div class="info">
                    	<span class="createDate">日期：${article.createDate?datetime?string.short}</span>
                    	<#if article.author==""><span class="author">作者: ${article.author}</span></#if>
                    	点击：<span id="hits"></span> 次
                    	<span class="fontSize">【<a id="changeBigFontSize" href="javascript:void(0);">大</a> <a id="changeNormalFontSize" href="javascript:void(0);">中</a> <a id="changeSmallFontSize" href="javascript:void(0);">小</a>】</span>
                    </div>
					<div id="articleContent" class="content">
             			${content}
             			<div class="blank"></div>
             			<link href="${base}/template/shop/css/pager.css" rel="stylesheet" type="text/css" />
             			<#import "/WEB-INF/template/shop/pager.ftl" as p>
             			<@p.articleContentPager article = article pageNumber = pageNumber />
                    </div>
				</div>
				<div class="articleContentBottom"></div>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>
