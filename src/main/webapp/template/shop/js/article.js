/***
 *	SHOP++ Article JavaScript
 *
 *	http://www.shopxx.net
 *
 *	Copyright (c) 2008 SHOP++. All rights reserved.
 **/

$().ready( function() {

	// 改变文章内容字体大小
	$articleContent = $("#articleContent");
	
	$("#changeBigFontSize").click(function () {
		$articleContent.css({"font-size": "16px"});
	});
	
	$("#changeNormalFontSize").click(function () {
		$articleContent.css({"font-size": "14px"});
	});
	
	$("#changeSmallFontSize").click(function () {
		$articleContent.css({"font-size": "12px"});
	});

	// 文章搜索
	$articleSearchKeyword = $("#articleSearchKeyword");
	articleSearchKeywordDefaultValue = $("#articleSearchKeyword").val();
	$articleSearchKeyword.focus( function() {
		if ($articleSearchKeyword.val() == articleSearchKeywordDefaultValue) {
			$articleSearchKeyword.val("");
		}
	});
	
	$articleSearchKeyword.blur( function() {
		if ($articleSearchKeyword.val() == "") {
			$articleSearchKeyword.val(articleSearchKeywordDefaultValue);
		}
	});
	
	$("#articleSearchForm").submit( function() {
		if ($.trim($articleSearchKeyword.val()) == "" || $.trim($articleSearchKeyword.val()) == articleSearchKeywordDefaultValue) {
			return false;
		}
	});

});