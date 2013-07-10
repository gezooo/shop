<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>发货单列表 - Powered By ${systemConfig.systemName}</title>
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
			<h1><span class="icon">&nbsp;</span>发货单列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="shipping!list.action" method="post">
			<div class="operateBar">
				<label>查找:</label>
				<select name="pager.property">
					<option value="shippingSn" <#if pager.property == "shippingSn">selected="selected" </#if>>
						发货编号
					</option>
					<option value="deliverySn" <#if pager.property == "deliverySn">selected="selected" </#if>>
						物流编号
					</option>
					<option value="shipName" <#if pager.property == "shipName">selected="selected" </#if>>
						收货人姓名
					</option>
					<option value="shipArea" <#if pager.property == "shipArea">selected="selected" </#if>>
						收货地区
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
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<span class="sort" name="shippingSn">发货编号</span>
					</th>
					<th>
						<span class="sort" name="deliveryTypeName">配送方式名称</span>
					</th>
					<th>
						<span class="sort" name="deliveryCorpName">物流公司名称</span>
					</th>
					<th>
						<span class="sort" name="deliverySn">物流编号</span>
					</th>
					<th>
						<span class="sort" name="deliveryFee">物流费用</span>
					</th>
					<th>
						<span class="sort" name="shipName">收货人姓名</span>
					</th>
					<th>
						<span class="sort" name="shipArea">收货地区</span>
					</th>
					<th>
						<span class="sort" name="createDate">发货时间</span>
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
							${list.shippingSn}
						</td>
						<td>
							${list.deliveryTypeName}
						</td>
						<td>
							${list.deliveryCorpName}
						</td>
						<td>
							${list.deliverySn}
						</td>
						<td>
							${list.deliveryFee?string(orderUnitCurrencyFormat)}
						</td>
						<td>
							${list.shipName}
						</td>
						<td>
							${list.shipArea}
						</td>
						<td>
							<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
						</td>
						<td>
							<a href="shipping!view.action?id=${list.id}" title="查看">[查看]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton" url="shipping!delete.action" value="删 除" disabled hidefocus="true" />
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