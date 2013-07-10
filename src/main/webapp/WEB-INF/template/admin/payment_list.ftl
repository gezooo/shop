<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>收款单列表 - Powered By ${systemConfig.systemName}</title>
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
			<h1><span class="icon">&nbsp;</span>收款单列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="payment!list.action" method="post">
			<div class="operateBar">
				<label>查找:</label>
				<select name="pager.property">
					<option value="paymentSn" <#if pager.property == "paymentSn">selected="selected" </#if>>
						支付编号
					</option>
					<option value="bankName" <#if pager.property == "bankName">selected="selected" </#if>>
						收款银行名称
					</option>
					<option value="bankAccount" <#if pager.property == "bankAccount">selected="selected" </#if>>
						收款银行账号
					</option>
					<option value="payer" <#if pager.property == "payer">selected="selected" </#if>>
						付款人
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
						<span class="sort" name="paymentSn">支付编号</span>
					</th>
					<th>
						<span class="sort" name="paymentType">支付类型</span>
					</th>
					<th>
						<span class="sort" name="paymentConfigName">支付方式</span>
					</th>
					<th>
						<span class="sort" name="totalAmount">支付金额</span>
					</th>
					<th>
						<span class="sort" name="paymentFee">支付手续费</span>
					</th>
					<th>
						<span class="sort" name="payer">付款人</span>
					</th>
					<th>
						<span class="sort" name="paymentStatus">支付状态</span>
					</th>
					<th>
						<span class="sort" name="createDate">支付时间</span>
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
							${list.paymentSn}
						</td>
						<td>
							${action.getText("PaymentType." + list.paymentType)}
						</td>
						<td>
							${list.paymentConfigName}
						</td>
						<td>
							${list.totalAmount?string(orderUnitCurrencyFormat)}
						</td>
						<td>
							${list.paymentFee?string(orderUnitCurrencyFormat)}
						</td>
						<td>
							${list.payer}
						</td>
						<td>
							${action.getText("PaymentStatus." + list.paymentStatus)}
						</td>
						<td>
							<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
						</td>
						<td>
							<a href="payment!view.action?id=${list.id}" title="查看">[查看]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton" url="payment!delete.action" value="删 除" disabled hidefocus="true" />
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