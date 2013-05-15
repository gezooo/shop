/***
 *	SHOP++ Install JavaScript
 *
 *	http://www.shopxx.net
 *
 *	Copyright (c) 2008 SHOP++. All rights reserved. 
 **/
 
if(!window.XMLHttpRequest) {

	// 解决IE6透明PNG图片BUG
	DD_belatedPNG.fix(".png");
	
	// 解决IE6不缓存背景图片问题
	document.execCommand("BackgroundImageCache", false, true);

}

$().ready( function() {

	// 消息提示窗口
	$("body").prepend('<div id="messageWindow" class="messageWindow"><div class="windowTop"><div class="windowTitle">提示信息&nbsp;</div><a class="messageClose windowClose" href="#" hidefocus="true"></a></div><div class="windowMiddle"><div class="messageContent"><span class="icon">&nbsp;</span><span class="messageText"></span></div><input type="button" class="formButton messageButton windowClose" value="确  定" hidefocus="true"/></div><div class="windowBottom"></div></div>');
	
	// 消息提示窗口
	$("#messageWindow").jqm({
		overlay: 60,
		closeClass: "windowClose",
		modal: true,
		trigger: false,
		onHide: function(object) {
			object.o.remove();
			object.w.fadeOut();
		}
	}).jqDrag(".windowTop");
	
	// 警告信息
	$.message = function () {
		var $messageWindow = $("#messageWindow");
		var $icon = $("#messageWindow .icon");
		var $messageText = $("#messageWindow .messageText");
		var $messageButton = $("#messageWindow .messageButton");
		var messageType;
		var messageText;
		if (arguments.length == 1) {
			messageType = "warn";
			messageText = arguments[0];
		} else {
			messageType = arguments[0];
			messageText = arguments[1];
		}
		if (messageType == "success") {
			$icon.removeClass("warn").removeClass("error").addClass("success");
		} else if (messageType == "error") {
			$icon.removeClass("warn").removeClass("success").addClass("error");
		} else {
			$icon.removeClass("success").removeClass("error").addClass("warn");
		}
		$messageText.html(messageText);
		$messageWindow.jqmShow();
		$messageButton.focus();
	}

});