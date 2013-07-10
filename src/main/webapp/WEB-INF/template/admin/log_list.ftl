<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>日志列表 - Powered By ${systemConfig.systemName}</title>
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
			<h1><span class="icon">&nbsp;</span>日志列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="log!list.action" method="post">
			<div class="operateBar">
				<label>查找:</label>
				<select name="pager.property">
					<option value="operationName" <#if pager.property == "operationName">selected="selected" </#if>>
						操作名称
					</option>
					<option value="operator" <#if pager.property == "operator">selected="selected" </#if>>
						操作员用戶名
					</option>
				</select>
				<label class="searchText"><input type="text" name="pager.keyword" value="${pager.keyword!}" /></label><input type="button" id="searchButton" class="searchButton" value="" />
				<label>每页显示:</label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10" <#if pager.pageSize == 10>selected="selected" </#if>>
						10
					</option>
					<option value="20" <#if pager.pageSize == 20>selected="selected" </#if>>
						20
					</option>
					<option value="50" <#if pager.pageSize == 50>selected="selected" </#if>>
						50
					</option>
					<option value="100" <#if pager.pageSize == 100>selected="selected" </#if>>
						100
					</option>
				</select>
			</div>
			<table class="listTable">
				<tr>
					<th class="check">
						&nbsp;
					</th>
					<th>
						<span class="sort" name="operationName">操作名称</span>
					</th>
					<th>
						<span class="sort" name="operator">操作员;</span>
					</th>
					<th>
						<span class="sort" name="ip">操作IP</span>
					</th>
					<th>
						<span class="sort" name="info">日志信息</span>
					</th>
					<th>
						<span class="sort" name="createDate">记录时间</span>
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
						<td>
							&nbsp;
						</td>
						<td>
							${list.operationName}
						</td>
						<td>
							${list.operator}
						</td>
						<td>
							${list.ip}
						</td>
						<td>
							${list.info}&nbsp;
						</td>
						<td>
							<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size == 0)>
				<div class="noRecord">
					没有找到任何记录!
				</div>
			</#if>
		</form>
	</div>
</body>
</html>