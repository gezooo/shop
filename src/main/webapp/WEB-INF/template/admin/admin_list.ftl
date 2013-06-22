<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理员列表 - Powered By ${systemConfig.systemName}</title>
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
			<h1><span class="icon">&nbsp;</span>管理员列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount} (共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="admin!list.action" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='admin!add.action'" value="添加管理员" />
				<label>查找:</label>
				<select name="pager.property">
					<option value="username" <#if pager.property??><#if pager.property == "username">selected="selected" </#if></#if> >
						用户名
					</option>
					<option value="name" <#if pager.property??><#if pager.property == "name">selected="selected" </#if></#if> >
						姓名
					</option> 
				</select>
				<label class="searchText"><input type="text" name="pager.keywords" value="${pager.keywords!}" /></label><input type="button" id="searchButton" class="searchButton" value="" />
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
						<span class="sort" name="username">用户名</span>
					</th>
					<th>
						<span class="sort" name="email">E-mail</span>
					</th>
					<th>
						<span class="sort" name="name">姓名</span>
					</th>
					<th>
						<span class="sort" name="department">所属部门</span>
					</th>
					<th>
						<span class="sort" name="loginDate">最后登录时间</span>
					</th>
					<th>
						<span class="sort" name="loginIp">最后登录IP</span>
					</th>
					<th>
						状态
					</th>
					<th>
						<span class="sort" name="createDate">创建日期</span>
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
							${list.username}
						</td>
						<td>
							${list.email}
						</td>
						<td>
							${(list.name)!"&nbsp;"}
						</td>
						<td>
							${(list.department)!"&nbsp;"}
						</td>
						<td>
							<#if (list.loginDate?? && list.loginDate?string.full != "")>
								<span title="${list.loginDate?string("yyyy-MM-dd HH:mm:ss")}">${list.loginDate}</span>
							<#else>
								&nbsp;
							</#if>
						</td>
						<td>
							${(list.loginIp)!"&nbsp;"}
						</td>
						<td>
							<#if list.isAccountEnabled == true && list.isAccountLocked == false && list.isAccountExpired == false && list.isCredentialsExpired == false>
								<span class="green">正常</span>
							<#elseif list.isAccountEnabled == false>
								<span class="red"> 未启用 </span>
							<#elseif list.isAccountLocked == true>
								<span class="red"> 已锁定 </span>
							<#elseif list.isAccountExpired == true>
								<span class="red"> 已过期 </span>
							<#elseif list.isCredentialsExpired == true>
								<span class="red"> 凭证过期 </span>
							</#if>
						</td>
						<td>
							<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
						</td>
						<td>
							<a href="admin!edit.action?id=${list.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton" url="admin!delete.action" value="删 除" disabled hidefocus="true" />
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
