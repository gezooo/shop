<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑配送方式 - Powered By ${systemConfig.systemName}</title>
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
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加配送方式<#else>编辑配送方式</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>delivery_type!save.action<#else>delivery_type!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						配送方式名称:
					</th>
					<td>
						<input type="text" name="deliveryType.name" class="formText {required: true, remote: 'delivery_type!checkName.action?oldValue=${(deliveryType.name?url)!}', messages: {remote: '配送方式名称已存在!'}}" value="${(deliveryType.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						配送类型:
					</th>
					<td>
						<select name="deliveryType.deliveryMethod">
							<#list allDeliveryMethod as list>
								<option value="${list}"<#if (list == deliveryType.deliveryMethod)!> selected </#if>>
									${action.getText("DeliveryMethod." + list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						默认物流公司:
					</th>
					<td>
						<select name="deliveryType.defaultDeliveryCorp.id">
							<option value="">请选择...</option>
							<#list allDeliveryCorp as list>
								<option value="${list.id}" <#if (list == deliveryType.defaultDeliveryCorp)!>selected </#if>>
									${list.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						首重量:
					</th>
					<td>
						<input type="text" name="deliveryType.firstWeight" class="formText {required: true, min: 0, messagePosition: '#firstWeightMessagePosition'}" value="${(deliveryType.firstWeight)!}" />
						<select name="deliveryType.firstWeightUnit">
							<#list allWeightUnit as list>
								<option value="${list}"<#if (list == deliveryType.firstWeightUnit)!> selected </#if>>
									${action.getText("WeightUnit." + list)}
								</option>
							</#list>
						</select>
						<span id="firstWeightMessagePosition"></span>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						续重量:
					</th>
					<td>
						<input type="text" name="deliveryType.continueWeight" class="formText {required: true, min: 0, messagePosition: '#continueWeightMessagePosition'}" value="${(deliveryType.continueWeight)!}" />
						<select name="deliveryType.continueWeightUnit">
							<#list allWeightUnit as list>
								<option value="${list}"<#if (list == deliveryType.continueWeightUnit)!> selected </#if>>
									${action.getText("WeightUnit." + list)}
								</option>
							</#list>
						</select>
						<span id="continueWeightMessagePosition"></span>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						首重价格:
					</th>
					<td>
						<input type="text" name="deliveryType.firstWeightPrice" class="formText {required: true, min: 0}" value="${(deliveryType.firstWeightPrice)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						续重价格:
					</th>
					<td>
						<input type="text" name="deliveryType.continueWeightPrice" class="formText {required: true, min: 0}" value="${(deliveryType.continueWeightPrice)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						排序:
					</th>
					<td>
						<input type="text" name="deliveryType.orderList" class="formText {digits: true, required: true}" value="${(deliveryType.orderList)!50}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						介绍:
					</th>
					<td>
						<textarea name="deliveryType.description" class="wysiwyg" rows="20" cols="100">${(deliveryType.description)!}</textarea>
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