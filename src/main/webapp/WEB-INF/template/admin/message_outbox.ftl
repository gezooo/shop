<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>发件箱 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<script type="text/javascript">
$().ready( function() {
	
	var $showMessageContentIcon = $(".showMessageContent").prev("span");
	var $showMessageContent = $(".showMessageContent");
	var $messageContentTr = $(".messageContentTr");
	var $deleteButton = $(".deleteButton");
	
	// 显示消息内容
	$showMessageContent.click( function() {
		var $this = $(this);
		var $thisShowMessageContentIcon = $this.prev("span");
		var $thisMessageContentTr = $this.parent().parent().next(".messageContentTr");
		if ($showMessageContentIcon.hasClass("downIcon")) {
			$thisMessageContentTr.show();
			$thisShowMessageContentIcon.removeClass("downIcon").addClass("upIcon");
		} else {
			$thisMessageContentTr.hide();
			$thisShowMessageContentIcon.removeClass("upIcon").addClass("downIcon");
		}
	});
	
	// 消息删除时隐藏所有消息内容
	$deleteButton.click( function() {
		$messageContentTr.hide();
		$showMessageContentIcon.removeClass("upIcon").addClass("downIcon");
	});
	
});
</script>
<style type="text/css">
<!--

.messageContentTr {
	display: none;
	background-color: #fafafa;
}

-->
</style>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>发件箱&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="message!outbox.action" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='message!send.action'" value="发送消息" />
				<label>查找:</label>
				<select name="pager.property">
					<option value="toMember.username" <#if pager.property == "toMember.username">selected="selected" </#if>>
						收件人
					</option>
					<option value="title" <#if pager.property == "title">selected="selected" </#if>>
						标题
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
						<span class="sort" name="title">标题</span>
					</th>
					<th>
						<span class="sort" name="toMember">收件人</span>
					</th>
					<th>
						<span class="sort" name="createDate">日期</span>
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${(list.id)!}" />
						</td>
						<td>
							<span class="downIcon">&nbsp;</span>
							<a class="showMessageContent {id: '${list.id}'}" href="javascript: void(0);">${list.title}</a>
						</td>
						<td>
							${(list.toMember.username)!}
						</td>
						<td>
							${list.createDate?string("yyyy-MM-dd HH:mm")}
						</td>
					</tr>
					<tr class="messageContentTr">
						<td>&nbsp;</td>
						<td colspan="3" class="messageContent">
							${(list.content)!}
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton" url="message!delete.action" value="删 除" disabled hidefocus="true" />
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