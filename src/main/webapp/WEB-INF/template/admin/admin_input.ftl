<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑管理员 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<#if id??>
	<#assign isEdit = true />
<#else>
	<#assign isAdd = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加管理员<#else>编辑管理员</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>admin!save.action<#else>admin!update.action</#if>" method="post">
			<#if isEdit??><input type="hidden" name="id" value="${id}" /></#if>
			<div class="blank"></div>
			<ul class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus="true" />
				</li>
				<li>
					<input type="button" value="个人资料" hidefocus="true" />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名:
					</th>
					<td>
						<#if isAdd??>
							<input type="text" name="admin.username" class="formText {required: true, username: true, remote: 'admin!checkUsername.action', minlength: 2, maxlength: 20, messages: {remote: '用户名已存在,请重新输入!'}}" title="用户名只允许包含中文、英文、数字和下划线" />
							<label class="requireField">*</label>
						<#else>
							${(admin.username)!}
							<input type="hidden" name="admin.username" value="${(admin.username)!}" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						密 码:
					</th>
					<td>
						<input type="password" name="admin.password" id="password" <#if isAdd??>class="formText {required: true, minlength: 4, maxlength: 20}"<#else>class="formText {minlength: 4, maxlength: 20}"</#if> title="密码长度只允许在4-20之间" />
						<label class="requireField">*</label>
					</td>
				</tr>
				
				<tr>
					<th>
						重复密码:
					</th>
					<td>
						<input type="password" name="rePassword" class="formText {equalTo: '#password', messages: {equalTo: '两次密码输入不一致!'}}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr>
				
				<tr>
					<th>
						E-mail:
					</th>
					<td>
						<input type="text" name="admin.email" class="formText {required: true, email: true}" value="${(admin.email)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						管理角色:
					</th>
					<td>
						<#list allRole as list>
							<label>
								<input type="checkbox" name="roleList.id" class="{required: true, messages: {required: '请至少选择一个角色!'}, messagePosition: '#roleMessagePosition'}" value="${list.id}" <#if (admin.roleSet)?? && (admin.roleSet.contains(list) == true)!> checked="checked"</#if> />
								${(list.name)!}
							</label>
						</#list>
						<span id="roleMessagePosition"></span>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						是否启用:
					</th>
					<td>
						<label><input type="radio" name="admin.isAccountEnabled" value="true"<#if (isAdd?? || ( (admin.isAccountEnabled)?? && admin.isAccountEnabled == true))> checked</#if> />是</label>
						<label><input type="radio" name="admin.isAccountEnabled" value="false"<#if ((admin.isAccountEnabled)?? && admin.isAccountEnabled == false)> checked</#if> />否</label>
					</td>
				</tr>
				<#if isEdit??>
					<tr>
						<th>&nbsp;</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>如果要修改密码,请填写密码,若留空,密码将保持不变!</span>
						</td>
					</tr>
				</#if>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						部门:
					</th>
					<td>
						<input type="text" name="admin.department" class="formText" value="${(admin.department)!}" />
					</td>
				</tr>
				<tr>
					<th>
						姓名:
					</th>
					<td>
						<input type="text" name="admin.name" class="formText" value="${(admin.name)!}" />
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