<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑资源 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加资源<#else>编辑资源</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>resource!save.action<#else>resource!update.action</#if>" method="post">
			<#if isEdit??><input type="hidden" name="id" value="${id}" /></#if>
			<table class="inputTable">
				<tr>
					<th>
						资源名称:
					</th>
					<td>
						<#setting url_escaping_charset='utf-8'>
						<input type="text" name="resource.name" class="formText {required: true, remote: 'resource!checkName.action?oldValue=${(resource.name?url)!}', messages: {remote: '资源名称已存在!'}}" value="${(resource.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						资源值:
					</th>
					<td>
						<input type="text" name="resource.value" class="formText {required: true, remote: 'resource!checkValue.action?oldValue=${(resource.value?url)!}', messages: {remote: '此资源值已存在!'}}" value="${(resource.value)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						描述:
					</th>
					<td>
						<input type="text" name="resource.description" class="formText" value="${(resource.description)!}" />
					</td>
				</tr>
				<tr>
					<th>
						排序:
					</th>
					<td>
						<input type="text" name="resource.orderList" class="formText {required: true, digits: true}" value="${(resource.orderList)!50}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<#if (resource.isSystem)!false>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>系统内置资源不允许修改!</span>
						</td>
					</tr>
				</#if>
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<input type="submit" class="formButton" value="确  定"<#if (resource.isSystem)!false> disabled</#if> hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>