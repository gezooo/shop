<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>收货地址 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/member_center.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $areaSelect = $(".areaSelect");

	// 地区选择菜单
	$areaSelect.lSelect({
		url: "area!ajaxChildrenArea.action"// AJAX数据获取url
	});

});
</script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="memberCenter">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body receiverInput">
		<div class="bodyLeft">
			<div class="memberInfo">
				<div class="top"></div>
				<div class="middle">
					<p>欢迎您！<span class="username">${loginMember.username}</span> [<a class="userLogout" href="member!logout.action"">退出</a>]</p>
					<p>会员等级:<span class="red"> ${loginMember.memberRank.name}</span></p>
				</div>
				<div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div class="memberMenu">
				<div class="top">
					<a href="member_center!index.action">会员中心首页</a>
				</div>
				<div class="middle">
					<ul>
	                	<li class="order">
	                    	<ul>
	                        	<li><a href="order!list.action">我的订单</a></li>
	                        </ul>
	                    </li>
	                    <li class="category favorite">
	                    	<ul>
	                        	<li><a href="favorite!list.action">商品收藏</a></li>
	                        </ul>
	                    </li>
	                  	<li class="message">
	                    	<ul>
	                        	<li><a href="message!send.action">发送消息</a></li>
	                            <li><a href="message!inbox.action">收件箱</a></li>
	                            <li><a href="message!draftbox.action">草稿箱</a></li>
	                            <li><a href="message!outbox.action">发件箱</a></li>
	                        </ul>
	                    </li>
	                    <li class="profile">
	                    	<ul>
	                        	<li><a href="profile!edit.action">个人信息</a></li>
	                            <li><a href="password!edit.action">修改密码</a></li>
	                            <li class="current"><a href="receiver!list.action">收货地址</a></li>
	                        </ul>
	                    </li>
	                    <li class="deposit">
	                    	<ul>
	                    		<li><a href="deposit!list.action">我的预存款</a></li>
	                        	<li><a href="deposit!recharge.action">预存款充值</a></li>
	                        </ul>
	                    </li>
	                </ul>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="bodyRight">
			<div class="memberCenterDetail">
				<div class="top">
					收货地址
				</div>
				<div class="middle">
					<div class="blank"></div>
					<form id="inputForm" class="validate" action="<#if isAdd??>receiver!save.action<#else>receiver!update.action</#if>" method="post">
						<input type="hidden" name="id" value="${id}" />
						<table class="inputTable">
							<tr>
								<th>
									收货人姓名:
								</th>
								<td>
									<input type="text" name="receiver.name" class="formText {required: true, messages: {required: '请输入收货人姓名！'}}" value="${(receiver.name)!}" />
									<label class="requireField">*</label>
								</td>
							</tr>
							<tr>
								<th>
									地区:
								</th>
								<td>
									<input type="text" name="receiver.areaPath" class="areaSelect hidden {required: true, messages: {required: '请选择地区！'}, messagePosition: '#addressMessagePosition'}" value="${(receiver.areaPath)!}" />
									<span id="addressMessagePosition"></span>
									<label class="requireField">*</label>
								</td>
							</tr>
							<tr>
								<th>
									地址:
								</th>
								<td>
									<input type="text" name="receiver.address" class="formText {required: true, messages: {required: '请输入地址！'}}" value="${(receiver.address)!}" />
									<label class="requireField">*</label>
								</td>
							</tr>
							<tr>
								<th>
									电话:
								</th>
								<td>
									<input type="text" name="receiver.phone" class="formText {requiredOne: '#mobile', phone: true, messages: {requiredOne: '电话、手机必须填写其中一项!'}}" value="${(receiver.phone)!}" />
									<label class="requireField">*</label>
								</td>
							</tr>
							<tr>
								<th>
									手机:
								</th>
								<td>
									<input type="text" id="mobile" name="receiver.mobile" class="formText {mobile: true}" value="${(receiver.mobile)!}" />
								</td>
							</tr>
							<tr>
								<th>
									邮编:
								</th>
								<td>
									<input type="text" name="receiver.zipCode" class="formText {required: true, zipCode: true, messages: {required: '请输入邮编！'}}" value="${(receiver.zipCode)!}" />
									<label class="requireField">*</label>
								</td>
							</tr>
							<tr>
								<th>
									是否默认:
								</th>
								<td>
									<label><input type="radio" name="receiver.isDefault" value="true"<#if (receiver.isDefault)!> checked</#if> />是</label>
									<label><input type="radio" name="receiver.isDefault" value="false"<#if (isAdd || !receiver.isDefault)!> checked</#if> />否</label>
								</td>
							</tr>
							<tr>
								<th>
									&nbsp;
								</th>
								<td>
									<input type="submit" class="submitButton" value="提 交" hidefocus="true" />
									<input type="button" class="backButton" onclick="window.history.back(); return false;" value="返 回" hidefocus="true" />
								</td>
							</tr>
						</table>
					</form>
					<div class="blank"></div>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>