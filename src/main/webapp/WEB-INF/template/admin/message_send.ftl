<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>发送消息 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>发送消息</h1>
		</div>
		<form id="inputForm" class="validate" action="message!save.action" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						收件人:
					</th>
					<td>
						<input type="text" name="toMemberUsername" class="formText {required: true, remote: 'message!checkUsername.action', messages: {required: '请填写用户名!', remote: '此会员不存在!'}}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						标题:
					</th>
					<td>
						<input type="text" name="message.title" class="formText {required: true, messages: {required: '请填写标题!'}}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						内容:
					</th>
					<td>
						<textarea name="message.content" class="formTextarea {required: true, messages: {required: '请填写消息内容!'}}" rows="5" cols="50"></textarea>
						<label class="requireField">*</label>
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