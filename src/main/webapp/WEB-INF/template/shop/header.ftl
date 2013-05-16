<script type="text/javascript" src="${base}/template/shop/js/header.js"></script>
<link href="${base}/template/shop/css/header.css" rel="stylesheet" type="text/css" />
<div class="header png">
	<div class="headerTop png">
		<div class="headerTopContent">
			<div class="headerLoginInfo">
				您好<span id="headerLoginMemberUsername"></span>，欢迎来到${(systemConfig.shopName)!}！
				<a href="#" id="headerShowLoginWindow" class="showLoginWindow">登录</a>
				<a href="${base}/shop/member_center!index.action" id="headerMemberCenter">会员中心</a>
				<a href="#" id="headerShowRegisterWindow" class="showRegisterWindow">注册</a>
				<a href="${base}/shop/member!logout.action" id="headerLogout">[退出]</a>
			</div>
			<div class="headerTopNav">
				<#list topNavigationList as list>
					<a href="<@list.url?interpret />"<#if list.isBlankTarget == true> target="_blank"</#if>>${list.name}</a>
					<#if list_has_next>|</#if>
				</#list>
			</div>
		</div>
	</div>
	<div class="headerMiddle">
		<div class="headerInfo">
			7×24小时服务热线：<strong>${(systemConfig.phone)!}</strong>
		</div>
		<div class="headerLogo">
			<a href="${base}/"><img class="png" src="${base}${(systemConfig.shopLogo)!}" title="${(systemConfig.shopName)!}" /></a>
		</div>
		<div class="headerSearch png">
			<form id="productSearchForm" action="${base}/shop/product!search.action" method="get">
				<div class="headerSearchText">
					<input type="text" id="productSearchKeyword" name="pager.keyword" value="<#if (pager.keyword != null && rootProductCategoryList != null)!>${pager.keyword}<#else>请输入关键词...</#if>"}" />
				</div>
				<input type="submit" class="headerSearchButton" value="" />
				<div class="hotKeyword">
					热门关键词: 
					<#list systemConfig.hotSearchList as list>
						<a href="${base}/shop/product!search.action?pager.keyword=${list?url}">${list}</a>
					</#list>
				</div>
			</form>
		</div>
	</div>
	<div class="headerBottom">
		<input type="button" class="cartItemListButton showCartItemList" value="" onclick="window.open('${base}/shop/cart_item!list.action')" />
		<ul id="cartItemListDetail"></ul>
		<input type="button" class="orderButton" value="" onclick="window.open('${base}/shop/cart_item!list.action')" />
		<div class="headerMiddleNav">
			<div class="headerMiddleNavLeft png"></div>
			<ul class="headerMiddleNavContent png">
				<#list middleNavigationList as list>
					<li>
						<a href="<@list.url?interpret />"<#if list.isBlankTarget == true> target="_blank"</#if>>${list.name}</a>
					</li>
				</#list>
			</ul>
			<div class="headerMiddleNavRight png"></div>
		</div>
	</div>
</div>