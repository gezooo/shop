<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>文章分类列表 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<script type="text/javascript">
$(document).ready(
function() {
	// 删除确认对话框
	$(".deleteAction").click( function() {
		if (confirm("您确定要删除此文章分类吗？") == false) {
			return false;
		}
	});

	// 树折叠
	$(".categoryName").click( function() {
		var level = $(this).parent().attr("level");
		var isHide;
		$(this).parent().nextAll("tr").each(function(){
			var thisLevel = $(this).attr("level");
			if(thisLevel <=  level) {
				return false;
			}
			if(isHide == null) {
				if($(this).is(":hidden")){
					isHide = true;
				} else {
					isHide = false;
				}
			}
			if( isHide) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
	});	
})
</script>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>文章分类列表&nbsp;<span class="pageInfo">总记录数: ${articleCategoryTreeList?size}</span></h1>
		</div>
		<form id="listForm" action="article_category!list.action" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='article_category!add.action'" value="添加分类" />
			</div>
			<table class="listTable">
				<tr>
					<th>
						分类名称&nbsp;
					</th>
					<th>
						排序&nbsp;
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list articleCategoryTreeList as list>
					<tr level="${(list.level)!}">
						<td class="categoryName">
							<span style="margin-left: ${list.level * 20}px;">
								<#if list.level == 0>
									<img src="${base}/template/admin/images/list_category_first_icon.gif" />
								<#else>
									<img src="${base}/template/admin/images/list_category_icon.gif" />
								</#if>
								${list.name}
							</span>
						</td>
						<td>
							${list.orderList}
						</td>
						<td>
							<a href="${base}/shop/article!list.action?id=${list.id}" target="_blank" title="浏览">[浏览]</a>
							<#if list.children?size gt 0>
								<span title="无法删除">[删除]</span>
							<#else>
								<a href="article_category!delete.action?id=${list.id}" class="deleteAction" title="删除">[删除]</a>
							</#if>
							<a href="article_category!edit.action?id=${list.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if articleCategoryTreeList?size == 0>
				<div class="noRecord">
					没有找到任何记录!
				</div>
			</#if>
		</form>
	</div>
</body>
</html>