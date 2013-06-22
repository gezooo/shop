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
				<span>网站设置</span>
			</dt>
			<dd>
				<a href="system_config!edit.action" target="mainFrame">系统设置</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>支付管理</span>
			</dt>
			<dd>
				<a href="payment_config!list.action" target="mainFrame">支付方式</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>配送管理</span>
			</dt>
			<dd>
				<a href="delivery_type!list.action" target="mainFrame">配送方式</a>
			</dd>
			<dd>
				<a href="area!list.action" target="mainFrame">地区管理</a>
			</dd>
			<dd>
				<a href="delivery_corp!list.action" target="mainFrame">物流公司</a>
			</dd>
		</dl>
	</div>
</body>
</html>