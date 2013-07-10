<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑支付方式 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready( function() {

	var $paymentConfigTypeSelect = $("select[name='paymentConfig.paymentConfigType']");
	var $tenpayConfigTr = $(".tenpayConfigTr");
	var $tenpayInput = $(".tenpayConfigTr :input");
	
	// 根据支付类型显示/隐藏输入项
	$paymentConfigTypeSelect.change( function() {
		var $this = $(this);
		if ($this.val() == "tenpay") {
			$tenpayInput.removeClass("ignoreValidate");
			$tenpayConfigTr.show();
		} else {
			$tenpayInput.addClass("ignoreValidate");
			$tenpayConfigTr.hide();
		}
	});

});
</script>
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
			<h1><span class="icon">&nbsp;</span><#if isAdd>添加支付方式<#else>编辑支付方式</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd>payment_config!save.action<#else>payment_config!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						支付类型:
					</th>
					<td>
						<select name="paymentConfig.paymentConfigType" class="{required: true}">
							<option value="">请选择...</option>
							<#list allPaymentConfigType as list>
								<option value="${list}"<#if (list == paymentConfig.paymentConfigType)!> selected</#if>>
									${action.getText("PaymentConfigType." + list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						支付方式名称:
					</th>
					<td>
						<input type="text" name="paymentConfig.name" class="formText {required: true, remote: 'payment_config!checkName.action?oldValue=${(paymentConfig.name?url)!}', messages: {remote: '支付方式名称已存在!'}}" value="${(paymentConfig.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						支付手续费设置:
					</th>
					<td>
						<#list allPaymentFeeType as list>
							<label class="requireField">
								<input type="radio" name="paymentConfig.paymentFeeType" value="${list}"<#if ((isAdd && list == "scale") || list == paymentConfig.paymentFeeType)!> checked </#if>>
								${action.getText("PaymentFeeType." + list)}
							</label>
						</#list>
						<label class="requireField">*</label>
					</td>
				</tr>
					<th>
						支付费率/固定费用:
					</th>
					<td>
						<input type="text" name="paymentConfig.paymentFee" class="formText {required: true, min: 0}'" value="${(paymentConfig.paymentFee)!"0"}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr class="tenpayConfigTr<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!> hidden</#if>">
					<th>
						财付通交易类型:
					</th>
					<td>
						<select name="tenpayConfig.tenpayType" class="<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!>ignoreValidate </#if>{required: true}">
							<option value="">请选择...</option>
							<#list allTenpayType as list>
								<option value="${list}"<#if (list == tenpayConfig.tenpayType)!> selected</#if>>
									${action.getText("TenpayType." + list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr class="tenpayConfigTr<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!> hidden</#if>">
					<th>
						财付通商户号:
					</th>
					<td>
						<input type="text" name="tenpayConfig.bargainorId" class="formText<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!> ignoreValidate</#if> {required: true}'" value="${(tenpayConfig.bargainorId)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr class="tenpayConfigTr<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!> hidden</#if>">
					<th>
						财付通密钥:
					</th>
					<td>
						<input type="text" name="tenpayConfig.key" class="formText<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!> ignoreValidate</#if> {required: true}'" value="${(tenpayConfig.key)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						排序:
					</th>
					<td>
						<input type="text" name="paymentConfig.orderList" class="formText {required: true, digits: true}" value="${(paymentConfig.orderList)!50}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						介绍:
					</th>
					<td>
						<textarea name="paymentConfig.description" class="wysiwyg" rows="20" cols="100">${(paymentConfig.description)!}</textarea>
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