(function(){
	var pathname = location.pathname;
	var change = false;
	$('#gnb .main-menu li a').map(function(){
		var $this = $(this);
		var href = $(this).attr('href');
		var match = pathname.indexOf(href);
		
		if(href && match > -1 && $this.parent().parent().hasClass('menu-3depth')) {
			$this.parents("li").addClass('selected');
			change = true;
		}
	});
	if(!change) {
		$('#gnb .main-menu li a').map(function(){
			var $this = $(this);
			var href = $(this).attr('href');
			var match = pathname.indexOf(href);

			if(href && match > -1) {
				$this.parents("li").addClass('selected');
			}
		});
	}
	
	
	var gnb = $('#gnb .main-menu > li');
	var selected_gnb = $('.main-menu > li.selected .sub-menu');
	gnb.on('mouseover', function(e) {
		var $this = $(this);
		if($this.hasClass('selected')) {
			return;
		}
		selected_gnb.css('display', 'none');
	});
	
	gnb.on('mouseout', function(e) {
		var $this = $(this);
		if($this.hasClass('selected')) {
			return;
		}
		selected_gnb.css('display', 'block');
	});
	
	
	var auth_arr = $('#gnb').data('auth');
	var gnb_list = $('#gnb .main-menu li');
	gnb_list.css('display', 'none')
	
	if(auth_arr){
		auth_arr = auth_arr.split(',');
		
		gnb_list.map(function(){
			var $this = $(this);
			var gnb_auth_arr = $this.data('auth');
			if(!gnb_auth_arr) {
				return $this.css('display', 'block');
			} else {
				gnb_auth_arr = gnb_auth_arr.split(',');
				
				for(var i = 0, iMax = gnb_auth_arr.length; i < iMax; i++) {
					for(var j = 0, jMax = auth_arr.length; j < jMax; j++) {
						if(gnb_auth_arr[i] == auth_arr[j]){
							return $this.css('display', 'block');
						}
					}
				}
			}
		});
	}
	
	
})();