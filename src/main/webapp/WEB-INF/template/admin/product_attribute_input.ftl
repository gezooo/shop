<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑商品属性 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready(function() {

	var $productAttributeType = $("#productAttributeType");
	var $productAttributeTypeTr = $("#productAttributeTypeTr");
	var $addAndRemoveTr = $("#addAndRemoveTr");

	// 显示选项内容
	$productAttributeType.change(function() {
		productAttributeChange();
	})
	
	// 增加选项内容输入框
	$("#addImage").click( function() {
		addAttributeOptionTr();
	})
	
	// 减少选项内容输入框
	$("#removeImage").click( function() {
		removeAttributeOptionTr();
	})
	
	// 删除选项内容输入框
	$(".deleteImage").livequery("click", function() {
		if($(".attributeOptionTr").length > 1) {
			$(this).parent().parent().remove();
		} else {
			alert("请至少保留一个选项!");
		}
	});

	function productAttributeChange() {
		$addAndRemoveTr.hide();
		$(".attributeOptionTr").remove();
		if($productAttributeType.val() == "select" || $productAttributeType.val() == "checkbox") {
			addAttributeOptionTr();
			$addAndRemoveTr.show();
		}
	}
	
	function addAttributeOptionTr() {
		var attributeOptionTrHtml = '<tr class="attributeOptionTr"><th>选项内容:</th><td><input type="text" name="attributeOptionList" class="formText attributeOption {required: true}" />&nbsp;<img src="${base}/template/admin/images/input_delete_icon.gif" class="deleteImage" alt="删除" /></td></tr>';
		if($(".attributeOptionTr").length > 0) {
			$(".attributeOptionTr:last").after(attributeOptionTrHtml);
		} else {
			$productAttributeTypeTr.after(attributeOptionTrHtml);
		}
	}

	function removeAttributeOptionTr() {
		if($(".attributeOptionTr").length > 1) {
			$(".attributeOptionTr:last").remove();
		} else {
			alert("请至少保留一个选项!");
		}
	}

})
</script>
<style type="text/css">
<!--

.deleteImage, #addImage, #removeImage {
	cursor: pointer;
}

-->
</style>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加商品属性<#else>编辑商品属性</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>product_attribute!save.action<#else>product_attribute!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="productTypeId" value="${productTypeId}" />
			<table class="inputTable">
				<tr>
					<th>
						所属商品类型:
					</th>
					<td>
						<select name="productAttribute.productType.id" id="productTypeId" class="{required: true}">
							<option value="">请选择...</option>
							<#list allProductType as list>
								<option value="${list.id}"<#if (list.id == productTypeId || list.id == productAttribute.productType.id)!> selected </#if>>${list.name}</option>
							</#list>
						</select>
						<lable class="requireField">*</lable>
					</td>
				</tr>
				<tr>
					<th>
						商品属性名称:
					</th>
					<td>
						<input type="text" name="productAttribute.name" id="productAttributeName" class="formText {required: true}" value="${(productAttribute.name)!}" />
						<lable class="requireField">*</lable>
					</td>
				</tr>
				<tr id="productAttributeTypeTr">
					<th>
						商品属性类型:
					</th>
					<td>
						<select id="productAttributeType" name="productAttribute.attributeType" class="{required: true}">
							<option value="">请选择...</option>
							<#list allAttributeType as list>
								<option value="${list}"<#if (list == productAttribute.attributeType)!> selected </#if>>
								${action.getText("AttributeType." + list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<#if (productAttribute.attributeType == "select" || productAttribute.attributeType == "checkbox")!>
					<#list productAttribute.attributeOptionList as list>
						<tr class="attributeOptionTr">
							<th>选项内容:</th>
							<td>
								<input type="text" name="attributeOptionList" class="formText attributeOption {required: true}" value="${list}" />
								&nbsp;<img src="${base}/template/admin/images/input_delete_icon.gif" class="deleteImage" alt="删除" />
							</td>
						</tr>
					</#list>
					<tr id="addAndRemoveTr">
				<#else>
					<tr id="addAndRemoveTr" style="display: none;">
				</#if>
					<td class="label">
						&nbsp;
					</td>
					<td>
						<img src="${base}/template/admin/images/input_add_icon.gif" id="addImage" alt="增加选项" />&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="${base}/template/admin/images/input_remove_icon.gif" id="removeImage" alt="减少选项" />
					</td>
				</tr>
				<tr>
					<th>
						排序:
					</th>
					<td>
						<input type="text" name="productAttribute.orderList" class="formText {digits: true, required:true}" value="${(productAttribute.orderList)!50}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						是否必填:
					</th>
					<td>
						<label><input type="radio" name="productAttribute.isRequired" value="true"<#if (productAttribute.isRequired == true)!> checked</#if> />是</label>
						<label><input type="radio" name="productAttribute.isRequired" value="false"<#if (isAdd || productAttribute.isRequired == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						是否启用:
					</th>
					<td>
						<label><input type="radio" name="productAttribute.isEnabled" value="true"<#if (isAdd || productAttribute.isEnabled == true)!> checked</#if> />是</label>
						<label><input type="radio" name="productAttribute.isEnabled" value="false"<#if (productAttribute.isEnabled == false)!> checked</#if> />否</label>
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