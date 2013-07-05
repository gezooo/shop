<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑品牌 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
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
			<h1><span class="icon">&nbsp;</span><#if isAdd >添加品牌<#else>编辑品牌</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd >brand!save.action<#else>brand!update.action</#if>" enctype="multipart/form-data" method="post">
			<#if isEdit ><input type="hidden" name="id" value="${id}" /></#if>
			<table class="inputTable">
				<tr>
					<th>
						品牌名称:
					</th>
					<td>
						<input type="text" name="brand.name" class="formText {required: true}" value="${(brand.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						品牌LOGO:
					</th>
					<td>
						<input type="file" name="logo" /><#if (brand.logo)??>&nbsp;&nbsp;&nbsp;<a href="${base}${brand.logo}" class="imagePreview" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						网址:
					</th>
					<td>
						<input type="text" name="brand.url" class="formText {url: true}" value="${(brand.url)!}" title="必须以http://开头" />
					</td>
				</tr>
				<tr>
					<th>
						排序:
					</th>
					<td>
						<input type="text" name="brand.orderList" class="formText {required: true, digits: true}" value="${(brand.orderList)!50}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						介绍:
					</th>
					<td>
						<textarea name="brand.introduction" class="wysiwyg" rows="20">${(brand.introduction)!}</textarea>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>