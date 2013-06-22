<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/menu.css" rel="stylesheet" type="text/css" />
</head>
<body class="menu">
	<div class="menuContent">
		<dl>
			<dt>
				<span>商品管理</span>
			</dt>
			<dd>
				<a href="product!list.action" target="mainFrame">商品列表</a>
			</dd>
			<dd>
				<a href="product!add.action" target="mainFrame">添加商品</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>文章管理</span>
			</dt>
			<dd>
				<a href="article!list.action" target="mainFrame">文章列表</a>
			</dd>
			<dd>
				<a href="article!add.action" target="mainFrame">添加文章</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>订单管理</span>
			</dt>
			<dd>
				<a href="order!list.action" target="mainFrame">订单列表</a>
			</dd>
		</dl>
	</div>
</body>
</html>