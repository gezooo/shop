<link href="${base}/template/shop/css/footer.css" type="text/css" rel="stylesheet" />
<div class="footer">
	<div class="bottomNavigation">
		<#list bottomNavigationList as list>
			<#if (list_index + 1) == 1>
				<dl>
			</#if>
				<dd>
					<a href="<@list.url?interpret />"<#if list.isBlankTarget == true> target="_blank"</#if>>${list.name}</a>
				</dd>
			<#if (list_index + 1) % 3 == 0 && list_has_next>
				</dl>
				<dl>
			</#if>
			<#if !list_has_next>
				</dl>
			</#if>
		</#list>
	</div>
	<div class="footerInfo">
		<@footer.content?interpret />
	</div>
</div>