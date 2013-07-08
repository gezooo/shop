<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑底部信息 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--

.input .contentArea {
	clear: both;
	padding: 5px;
}

.input .buttonArea {
	clear: both;
	padding: 5px 0px;
	text-align: center;
}

-->
</style>
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
			<h1><span class="icon">&nbsp;</span>编辑底部信息</h1>
		</div>
		<form id="inputForm" class="validate" action="footer!update.action" method="post">
			<div class="contentArea">
				<textarea name="footer.content" class="wysiwyg {required: true}" rows="30" cols="100">${(footer.content)!}</textarea>
			</div>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>