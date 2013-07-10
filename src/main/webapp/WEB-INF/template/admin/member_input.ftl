<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑会员 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready( function() {

	// 地区选择菜单
	$(".areaSelect").lSelect({
		url: "${base}/admin/area!ajaxChildrenArea.action"// Json数据获取url
	});

});
</script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加会员<#else>编辑会员</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>member!save.action<#else>member!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						用户名:
					</th>
					<td>
						<#if isAdd??>
							<input type="text" name="member.username" class="formText {required: true, minlength: 2, maxlength: 20, username: true, remote: 'member!checkUsername.action', messages: {remote: '用户名已存在!'}}" />
							<label class="requireField">*</label>
						<#else>
							${member.username}
							<input type="hidden" name="member.username" value="${(member.username)!}" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						密 码:
					</th>
					<td>
						<input type="password" id="password" name="member.password" class="formText<#if isAdd??> {required: true, minlength: 4, maxlength: 20}<#else> {minlength: 4, maxlength: 20}</#if>" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						确认密码:
					</th>
					<td>
						<input type="password" name="rePassword" class="formText {equalTo: '#password', messages: {equalTo: '两次密码输入不一致!'}}" />
					</td>
				</tr>
				<tr>
					<th>
						E-mail:
					</th>
					<td>
						<input type="text" name="member.email" class="formText {required: true, email: true}" value="${(member.email)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						积分:
					</th>
					<td>
						<input type="text" name="member.point" class="formText {required: true, digits: true}" value="${(member.point)!"0"}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						预存款:
					</th>
					<td>
						<input type="text" name="member.deposit" class="formText {required: true, min: 0}" value="${(member.deposit)!"0"}" title="只允许输入大于或等于零的数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						会员等级
					</th>
					<td>
						<select name="member.memberRank.id">
							<#list allMemberRank as list>
								<option value="${list.id}"<#if ((isAdd && list.isDefault) || (isEdit && member.memberRank.id == list.id))!> selected</#if>>${list.name}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						是否启用:
					</th>
					<td>
						<label><input type="radio" name="member.isAccountEnabled" value="true"<#if (isAdd || member.isAccountEnabled == true)!> checked</#if> />是</label>
						<label><input type="radio" name="member.isAccountEnabled" value="false"<#if (member.isAccountEnabled == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						&nbsp;
					</td>
				</tr>
				<#if isEdit>
					<tr>
						<th>
							注册日间
						</th>
						<td>
							${(member.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
					</tr>
					<tr>
						<th>
							注册IP
						</th>
						<td>
							${(member.registerIp)!}
						</td>
					</tr>
				</#if>
				<#list enabledMemberAttributeList as list>
					<tr>
						<th>
							${list.name}:
						</th>
						<td>
							<#if list.attributeType == "TEXT">
								<input type="text" name="${list.id}" class="formText<#if list.isRequired> {required: true}</#if>" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "NUMBER">
								<input type="text" name="${list.id}" class="formText {<#if list.isRequired>required: true, </#if>number: true}" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "ALPHAINT">
								<input type="text" name="${list.id}" class="formText {<#if list.isRequired>required: true, </#if>lettersonly: true}" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "EMAIL">
								<input type="text" name="${list.id}" class="formText {<#if list.isRequired>required: true, </#if>email: true}" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "SELECT">
								<select name="${list.id}"<#if list.isRequired> class="{required: true}"</#if>>
									<option value="">请选择...</option>
									<#list list.attributeOptionList as attributeOptionList>
										<option value="${attributeOptionList}"<#if (member.memberAttributeMap.get(list)[0] == attributeOptionList)!> selected</#if>>${attributeOptionList}</option>
									</#list>
								</select>
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "CHECKBOX">
								<#list list.attributeOptionList as attributeOptionList>
									<label><input type="checkbox" name="${list.id}"<#if list.isRequired> class="{required: true, messagePosition: '#${list.id}MessagePosition'}"</#if> value="${attributeOptionList}"<#if (member.memberAttributeMap.get(list).contains(attributeOptionList))!> checked</#if>  />${attributeOptionList}</label>
								</#list>
								<span id="${list.id}MessagePosition"></span>
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "NAME">
								<input type="text" name="${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "GENDER">
								<label><input type="radio" name="${list.id}"<#if list.isRequired> class="{required: true, messagePosition: '#${list.id}MessagePosition'}"</#if> value="male" <#if (member.memberAttributeMap.get(list)[0] == "male")!> checked</#if> />${action.getText("Gender.male")}</label>
								<label><input type="radio" name="${list.id}"<#if list.isRequired> class="{required: true, messagePosition: '#${list.id}MessagePosition'}"</#if> value="female" <#if (member.memberAttributeMap.get(list)[0] == "female")!> checked</#if> />${action.getText("Gender.female")}</label>
								<span id="${list.id}MessagePosition"></span>
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "DATE">
								<input type="text" name="${list.id}" class="formText datePicker {<#if list.isRequired>required: true, </#if>dateISO: true}" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "AREA">
								<input type="text" name="${list.id}" class="formText areaSelect<#if list.isRequired> {required: true}</#if>" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "ADDRESS">
								<input type="text" name="${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "ZIPCODE">
								<input type="text" name="${list.id}" class="formText {<#if list.isRequired>required: true, </#if>zipCode: true}" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "MOBILE">
								<input type="text" name="${list.id}" class="formText {<#if list.isRequired>required: true, </#if>mobile: true}" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "PHONE">
								<input type="text" name="${list.id}" class="formText {<#if list.isRequired>required: true, </#if>phone: true}" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "QQ">
								<input type="text" name="${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "MSN">
								<input type="text" name="${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "WANGWANG">
								<input type="text" name="${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "SKYPE">
								<input type="text" name="${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(member.memberAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							</#if>
						</td>
					</tr>
				</#list>
				<#if member != null>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>如果要修改密码，请填写密码，若留空，密码将保持不变</span>
						</td>
					</tr>
				</#if>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>