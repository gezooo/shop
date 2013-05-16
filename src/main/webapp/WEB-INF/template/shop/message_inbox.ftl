<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>收件箱 - Powered By ${systemConfig.systemName}</title>
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
	
	// 删除
	$(".deleteMessage").click( function() {
		if (confirm("您确定要删除吗？") == false) {
			return false;
		}
	});
	
	// 显示消息内容
	$(".showMessageContent").click( function() {
		var $this = $(this);
		var $messageContentTr = $this.parent().parent().next(".messageContentTr");
		if ($.trim($messageContentTr.find("td").text()) == "") {
			var id = $this.metadata().id;
			$.ajax({
				url: "message!ajaxMessageContent.action",
				data: {"id": id},
				async: false,
				dataType: "json",
				beforeSend: function(data) {
					$messageContentTr.find("td").html('<span class="loadingIcon">&nbsp;</span> 加载中...');
				},
				success: function(data) {
					if (data.status == "success") {
						$messageContentTr.find("td").html(data.content);
					} else {
						$.message(data.status, data.message);
					}
				}
			});
		}
		var $showMessageContentIcon = $this.prev("span");
		if ($showMessageContentIcon.hasClass("downIcon")) {
			$messageContentTr.show();
			$showMessageContentIcon.removeClass("downIcon").addClass("upIcon");
		} else {
			$messageContentTr.hide();
			$showMessageContentIcon.removeClass("upIcon").addClass("downIcon");
		}
	});
	
})
</script>
<style type="text/css">
<!--

.messageInbox .messageContentTr {
	display: none;
	background-color: #fafafa;
}

.messageInbox .messageContentTr td {
	padding-left: 30px;
}


-->
</style>
</head>
<body class="memberCenter">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body messageInbox">
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
	                            <li class="current"><a href="message!inbox.action">收件箱</a></li>
	                            <li><a href="message!draftbox.action">草稿箱</a></li>
	                            <li><a href="message!outbox.action">发件箱</a></li>
	                        </ul>
	                    </li>
	                    <li class="profile">
	                    	<ul>
	                        	<li><a href="profile!edit.action">个人信息</a></li>
	                            <li><a href="password!edit.action">修改密码</a></li>
	                            <li><a href="receiver!list.action">收货地址</a></li>
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
					收件箱
				</div>
				<div class="middle">
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th>标题</th>
							<th>发件人</th>
							<th>已读</th>
							<th>时间</th>
							<th>操作</th>
						</tr>
						<#list pager.list as list>
							<tr>
								<td>
									<span class="downIcon">&nbsp;</span><a class="showMessageContent {id: '${list.id}'}" href="javascript: void(0);">${list.title}</a>
								</td>
								<td>
									${(list.fromMember.username)!"<span class=\"green\">管理员</span>"}
								</td>
								<td>
									<#if list.isRead>
										是
									<#else>
										<span class="red">否</span>
									</#if>
								</td>
								<td>
									${list.createDate?string("yyyy-MM-dd HH:mm")}
								</td>
								<td>
									<a href="${base}/shop/message!reply.action?id=${list.id}">回复</a>
									<a href="${base}/shop/message!delete.action?id=${list.id}" class="deleteMessage">删除</a>
								</td>
							</tr>
							<tr class="messageContentTr">
								<td colspan="5"></td>
							</tr>
						</#list>
					</table>
					<div class="blank"></div>
         			<link href="${base}/template/shop/css/pager.css" rel="stylesheet" type="text/css" />
         			<#import "/WEB-INF/template/shop/pager.ftl" as p>
         			<@p.pager pager = pager baseUrl = "/shop/message!inbox.action" />
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