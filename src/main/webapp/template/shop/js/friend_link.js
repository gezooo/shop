/***
 *	SHOP++ Friend Link JavaScript
 *
 *	http://www.shopxx.net
 *
 *	Copyright (c) 2008 SHOP++. All rights reserved.
 **/

$().ready( function() {

	$(".pictureFriendLink .scrollable").scrollable({
		loop: true,
		speed: 1000
	});
	
	$(".pictureFriendLink .scrollable a").hover(
		function() {
			$(this).stop().animate({"opacity": 1});
		}, function() {
			$(this).stop().animate({"opacity": 0.5});
		}
	).animate({"opacity": 0.5 });

});