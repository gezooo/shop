<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>配送方式列表 - Powered By ${systemConfig.systemName}</title>
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
			<h1><span class="icon">&nbsp;</span>配送方式管理&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="delivery_type!list.action" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='delivery_type!add.action'" value="添加方式" />
			</div>
			<table class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<span class="sort" name="name">配送方式名称</span>
					</th>
					<th>
						<span class="sort" name="deliveryMethod">配送类型</span>
					</th>
					<th>
						<span class="sort" name="firstWeight">首重量</span>
					</th>
					<th>
						<span class="sort" name="continueWeight">续重量</span>
					</th>
					<th>
						<span class="sort" name="orderList">排序</span>
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${(list.id)!}" />
						</td>
						<td>
							${list.name}
						</td>
						<td>
							${action.getText("DeliveryMethod." + list.deliveryMethod)}
						</td>
						<td>
							${list.firstWeight}${action.getText("WeightUnit." + list.firstWeightUnit)}
						</td>
						<td>
							${list.continueWeight}${action.getText("WeightUnit." + list.continueWeightUnit)}
						</td>
						<td>
							${list.orderList}
						</td>
						<td>
							<a href="delivery_type!edit.action?id=${list.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton" url="delivery_type!delete.action" value="删 除" disabled hidefocus="true" />
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