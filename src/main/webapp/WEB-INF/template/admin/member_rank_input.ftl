<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑会员等级 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<#if !id??>
	<#assign isAdd = true />
	<#assign isEdit = false />
<#else>
	<#assign isEdit = true />
	<#assign isAdd = false />

</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd>添加会员等级<#else>编辑会员等级</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd>member_rank!save.action<#else>member_rank!update.action</#if>" method="post">
			<table class="inputTable">
				<input type="hidden" name="id" value="${id}" />
				<tr>
					<th>
						等级名称:
					</th>
					<td>
						<input type="text" name="memberRank.name" class="formText {required: true, remote: 'member_rank!checkName.action?oldValue=${(memberRank.name?url)!}', messages: {remote: '等级名称已存在!'}}" value="${(memberRank.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						优惠百分比:
					</th>
					<td>
						<input type="text" name="memberRank.preferentialScale" class="formText {required: true, min: 0}" value="${(memberRank.preferentialScale)!"100"}" title="单位: %，若输入90，表示该会员等级以商品价格的90%进行销售" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						所需积分:
					</th>
					<td>
						<input type="text" name="memberRank.point" class="formText {required : true, digits: true}" value="${(memberRank.point)!}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						是否默认:
					</th>
					<td>
						<label>
							<input type="radio" name="memberRank.isDefault" value="true"<#if (memberRank.isDefault)!false> checked</#if> />
							是
						</label>
						&nbsp;&nbsp;
						<label>
							<input type="radio" name="memberRank.isDefault" value="false"<#if (isAdd || !memberRank.isDefault)!false> checked</#if> />
							否
						</label>
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