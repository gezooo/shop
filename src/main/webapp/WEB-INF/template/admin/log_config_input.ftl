<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑日志监控设置 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready( function() {

	// 查询Action类对应的方法名称
	$("#actionClassName").change( function() {
		var actionClassName = $("#actionClassName");
		var actionMethodName = $("#actionMethodName");
		$.post("log_config!getAllActionMethod.action", {
			"logConfig.actionClassName" :actionClassName.val()
		}, function(data, textStatus) {
			if (data != "") {
				actionMethodName.html(data);
			} else {
				actionMethodName.html("");
			}
		});
	});

})
</script>
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
			<h1><span class="icon">&nbsp;</span><#if isAdd>添加需进行日志监控的方法<#else>编辑需进行日志监控的方法</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd>log_config!save.action<#else>log_config!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						操作名称:
					</th>
					<td>
						<input type="text" name="logConfig.operationName" class="formText {required: true, remote: 'log_config!checkOperationName.action?oldValue=${(logConfig.operationName)!}', messages: {remote: '此操作名称已存在!'}}" value="${(logConfig.operationName)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						Action类:
					</th>
					<td>
						<select id="actionClassName" name="logConfig.actionClassName" class="{required: true, messages: {required: '此内容为必选项,请选择!'}}">
							<option value="">请选择...</option>
							<#list allActionClassName as list>
								<option value="${list}" <#if (list == logConfig.actionClassName)!>selected="selected"</#if>>
									${list}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						Action方法:
					</th>
					<td>
						<select id="actionMethodName" name="logConfig.actionMethodName" class="{required: true}">
							<option value="">请选择...</option>
							<option value="${(logConfig.actionMethodName)!}" selected="selected">
								${(logConfig.actionMethodName)!}
							</option>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						描述:
					</th>
					<td>
						<input type="text" name="logConfig.description" class="formText" value="${(logConfig.description)!}" />
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