<link href="${base}/template/shop/css/friend_link.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${base}/template/shop/js/friend_link.js"></script>
<div class="friendLink">
	<div class="pictureFriendLink">
		<div class="left prev"></div>
		<div class="middle scrollable">
			<ul class="items">
				<#list (pictureFriendLinkList)! as list>
					<li>
						<a href="${list.url}" target="_blank" title="${list.name}">
							<img src="${base}${list.logo}">
						</a>
					</li>
					<#if list_index + 1 == 16>
						<#break/>
					</#if>
				</#list>
			</ul>
		</div>
		<div class="right next"></div>
	</div>
	<div class="textFriendLink">
		<div class="left"></div>
		<div class="middle">
			<ul>
				<#list (textFriendLinkList)! as list>
					<li>
						<a href="${list.url}" target="_blank" title="${list.name}">${list.name}</a>
					</li>
					<#if list_index + 1 == 10>
						<#break/>
					</#if>
				</#list>
			</ul>
		</div>
		<div class="right"></div>
	</div>
</div>