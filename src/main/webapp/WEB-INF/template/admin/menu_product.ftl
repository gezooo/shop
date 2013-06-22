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
				<span>商品分类管理</span>
			</dt>
			<dd>
				<a href="product_category!list.action" target="mainFrame">分类列表</a>
			</dd>
			<dd>
				<a href="product_category!add.action" target="mainFrame">添加分类</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>商品类型管理</span>
			</dt>
			<dd>
				<a href="product_type!list.action" target="mainFrame">类型列表</a>
			</dd>
			<dd>
				<a href="product_attribute!list.action" target="mainFrame">属性列表</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>品牌管理</span>
			</dt>
			<dd>
				<a href="brand!list.action" target="mainFrame">品牌列表</a>
			</dd>
			<dd>
				<a href="brand!add.action" target="mainFrame">添加品牌</a>
			</dd>
		</dl>
	</div>
</body>
</html>