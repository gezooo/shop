<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>导航列表 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>导航列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="navigation!list.action" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='navigation!add.action'" value="添加导航" />
				<label>查找:</label>
				<select name="pager.property">
					<option value="name" <#if (pager.property)! == "name">selected="selected" </#if>>
						名称
					</option>
				</select>
				<label class="searchText"><input type="text" name="pager.keyword" value="${pager.keyword!}" /></label><input type="button" id="searchButton" class="searchButton" value="" />
				<label>每页显示:</label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10" <#if ((pager.pageSize)!10) == 10>selected="selected" </#if>>
						10
					</option>
					<option value="20" <#if ((pager.pageSize)!10) == 20>selected="selected" </#if>>
						20
					</option>
					<option value="50" <#if ((pager.pageSize)!10) == 50>selected="selected" </#if>>
						50
					</option>
					<option value="100" <#if ((pager.pageSize)!10) == 100>selected="selected" </#if>>
						100
					</option>
				</select>
			</div>
			<table class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						名称&nbsp;
					</th>
					<th>
						位置&nbsp;
					</th>
					<th>
						是否显示&nbsp;
					</th>
					<th>
						是否新窗口&nbsp;
					</th>
					<th>
						排序&nbsp;
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${list.id}" />
						</td>
						<td>
							${list.name}
						</td>
						<td>
							${action.getText("Position." + list.position)}
						</td>
						<td>
							<#if list.isVisible == true>
								<img src="${base}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${base}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td>
						<td>
							<#if list.isBlankTarget == true>
								<img src="${base}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${base}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td>
						<td>
							${list.orderList}
						</td>
						<td>
							<a href="navigation!edit.action?id=${list.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
					<#if list_has_next>
						<#if pager.list[list_index + 1].position != list.position>
							<tr>
								<td colspan="7">&nbsp;</td>
							</tr>
						</#if>
					</#if>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton" url="navigation!delete.action" value="删 除" disabled hidefocus="true" />
					<#include "/WEB-INF/template/admin/pager.ftl" />
				</div>
			<#else>
				<div class="noRecord">
					没有找到任何记录!
				</div>
			</#if>
		</form>
	</div>
</body>
</html>