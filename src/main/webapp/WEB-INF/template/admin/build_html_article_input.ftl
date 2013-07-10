<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>文章更新 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready(function() {

	$("#inputForm").submit(function() {
		$("#id").val($("#idSelect").val());
		$("#maxResults").val($("#maxResultsInput").val());
	});

	var isInitialize = false;
	$("#inputForm").ajaxForm({
		dataType: "json",
		beforeSubmit: function(data) {
			if (!isInitialize) {
				isInitialize = true;
				$("#idSelect").attr("disabled", true);
				$("#maxResultsInput").attr("disabled", true);
				$(".submitButton").attr("disabled", true);
				$("#statusTr").show();
				$("#status").text("正在进行更新操作，请稍后...");
			}
		},
		success: function(data) {
			if (data.status == "ARTICLE_BUILDING") {
				var maxResults = Number($("#maxResults").val());
				var firstResult = Number(data.firstResult);
				$("#status").text("正在更新文章[" + (firstResult + 1) + " - " + (firstResult + maxResults) + "]，请稍后...");
				$("#firstResult").val(firstResult);
				$("#inputForm").submit();
			} else if (data.status == "ARTICLE_FINISH") {
				isInitialize = false;
				$("#firstResult").val("0");
				$("#statusTr").hide();
				$("#idSelect").attr("disabled", false);
				$("#maxResultsInput").attr("disabled", false);
				$(".submitButton").attr("disabled", false);
				$.message("success", "文章更新成功！[更新总数: " + data.buildTotal + "]");
			}
		}
	});

})
</script>
<style type="text/css">
<!--

#statusTr {
	display: none;
}

-->
</style>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>文章更新</h1>
		</div>
		<form id="inputForm" action="build_html!article.action" method="post">
			<input type="hidden" id="id" name="id" value="" />
			<input type="hidden" id="maxResults" name="maxResults" value="" />
			<input type="hidden" id="firstResult" name="firstResult" value="0" />
			<table class="inputTable">
				<tr>
					<th>
						文章分类:
					</th>
					<td>
						<select id="idSelect" name="">
							<option value="">更新所有分类</option>
							<#list articleCategoryTreeList as list>
								<option value="${list.id}">
									<#if list.level != 0>
										<#list 1..list.level as i>
											------
										</#list>
									</#if>
								${list.name}
								</option>
							</#list>
						</select>
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
				<tr id="statusTr">
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