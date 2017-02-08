messageRouter.on('show-popup-loading', null, function(){
	$('#loading-popup').show();
});

messageRouter.on('hide-popup-loading', null, function(){
	$('#loading-popup').hide();
});
