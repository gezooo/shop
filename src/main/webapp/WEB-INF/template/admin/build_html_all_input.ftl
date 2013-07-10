<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>一键网站更新 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready( function() {

	// 根据更新选项显示/隐藏开始日期和结束日期
	$(".buildTypeInput").click( function() {
		if ($(this).val() == "date") {
			$(".dateTr").show();
		} else {
			$(".dateTr").hide();
		}
	})
	
	$("#inputForm").submit(function() {
		$("#buildType").val($(".buildTypeInput:checked").val());
		$("#maxResults").val($("#maxResultsInput").val());
		$("#beginDate").val($("#beginDateInput").val());
		$("#endDate").val($("#endDateInput").val());
	});

	var isInitialize = false;
	var buildTotal = 0;
	$("#inputForm").ajaxForm({
		dataType: "json",
		beforeSubmit: function(data) {
			if (!isInitialize) {
				isInitialize = true;
				$(".buildTypeInput").attr("disabled", true);
				$("#maxResultsInput").attr("disabled", true);
				$("#beginDateInput").attr("disabled", true);
				$("#endDateInput").attr("disabled", true);
				$(":submit").attr("disabled", true);
				$("#statusTr").show();
				$("#status").text("正在更新BASE_JAVASCRIPT，请稍后...");
			}
		},
		success: function(data) {
			if (data.buildTotal) {
				buildTotal += Number(data.buildTotal);
			}
			if (data.status == "baseJavascriptFinish") {
				$("#status").text("正在更新自定义错误页，请稍后...");
				$("#buildContent").val("errorPage");
				$("#inputForm").submit();
			} else if (data.status == "errorPageFinish") {
				$("#status").text("正在更新首页，请稍后...");
				$("#buildContent").val("index");
				$("#inputForm").submit();
			} else if (data.status == "indexFinish") {
				$("#status").text("正在更新登录页，请稍后...");
				$("#buildContent").val("login");
				$("#inputForm").submit();
			} else if (data.status == "loginFinish") {
				$("#status").text("正在更新文章，请稍后...");
				$("#buildContent").val("article");
				$("#inputForm").submit();
			} else if (data.status == "articleBuilding") {
				var maxResults = Number($("#maxResults").val());
				var firstResult = Number(data.firstResult);
				$("#status").text("正在更新文章[" + (firstResult + 1) + " - " + (firstResult + maxResults) + "]，请稍后...");
				$("#buildContent").val("article");
				$("#firstResult").val(firstResult);
				$("#inputForm").submit();
			} else if (data.status == "articleFinish") {
				$("#status").text("正在更新商品，请稍后...");
				$("#buildContent").val("product");
				$("#firstResult").val("0");
				$("#inputForm").submit();
			} else if (data.status == "productBuilding") {
				var maxResults = Number($("#maxResults").val());
				var firstResult = Number(data.firstResult);
				$("#status").text("正在更新商品[" + (firstResult + 1) + " - " + (firstResult + maxResults) + "]，请稍后...");
				$("#buildContent").val("product");
				$("#firstResult").val(firstResult);
				$("#inputForm").submit();
			} else if (data.status == "productFinish") {
				$("#buildContent").val("");
				$("#firstResult").val("0");
				$("#statusTr").hide();
				$(".buildTypeInput").attr("disabled", false);
				$("#maxResultsInput").attr("disabled", false);
				$("#beginDateInput").attr("disabled", false);
				$("#endDateInput").attr("disabled", false);
				$(":submit").attr("disabled", false);
				$.message("success", "网站更新成功！[更新总数: " + buildTotal + "]");
				isInitialize = false;
				buildTotal = 0;
			}
		}
	});

});
</script>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>一键网站更新</h1>
		</div>
		<form id="inputForm" action="build_html!all.action" method="post">
			<input type="hidden" id="buildType" name="buildType" value="" />
			<input type="hidden" id="maxResults" name="maxResults" value="" />
			<input type="hidden" id="firstResult" name="firstResult" value="0" />
			<input type="hidden" id="buildContent" name="buildContent" value="" />
			<input type="hidden" id="beginDate" name="beginDate" value="" />
			<input type="hidden" id="endDate" name="endDate" value="" />
			<table class="inputTable">
				<tr>
					<th>
						更新选项:
					</th>
					<td>
						<label><input type="radio" name="buildTypeInput" class="buildTypeInput" value="date" checked />指定日期</label>&nbsp;&nbsp;
						<label><input type="radio" name="buildTypeInput" class="buildTypeInput" value="all" />更新所有</label>
					</td>
				</tr>
				<tr class="dateTr">
					<th>
						起始日期:
					</th>
					<td>
						<input type="text" id="beginDateInput" name="" class="formText datePicker" value="${(defaultBeginDate?string("yyyy-MM-dd"))!}" title="留空则从最早的内容开始更新" />
					</td>
				</tr>
				<tr class="dateTr">
					<th>
						结束日期:
					</th>
					<td>
						<input type="text" id="endDateInput" name="" class="formText datePicker" value="${(defaultEndDate?string("yyyy-MM-dd"))!}" title="留空则更新至最后的内容" />
					</td>
				</tr>
				<tr>
					<th>
						每次更新数
					</th>
					<td>
						<input type="text" id="maxResultsInput" name="" class="formText" value="50" />
					</td>
				</tr>
				<tr id="statusTr" class="hidden">
					<th>
						&nbsp;
					</th>
					<td>
						<span class="loadingBar">&nbsp;</span>
						<p id="status"></p>
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