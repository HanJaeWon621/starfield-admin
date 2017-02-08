var app = app || {};

app.ractive = app.ractive || {};
app.ractive.couponPopup = null;

app.couponPopup = app.couponPopup || (function(){
	var $popup = $('#coupon-list-popup');
	var couponList = [];
	function showPopup(_couponList) {
		couponList = _couponList;
		if(!app.ractive.couponPopup) {
			app.ractive.couponPopup = new Ractive({
				el : '#coupon-list-popup',
				template : '#tmpl-coupon-list-popup',
				data : {
					couponList : couponList
				}
			});
			
			app.ractive.couponPopup.on({
				'select' : function(e, coupon) {

					messageRouter.trigger('select-popup-coupon', null, coupon);
					console.log(coupon);
					
					$popup.css('display', 'none');
				},
				'close' : function(e) {
					$popup.css('display', 'none');
				}
			});
		} else {
			app.ractive.couponPopup.reset();
			app.ractive.couponPopup.set({
				couponList : couponList
			});
		}
		$popup.css('display', 'block');
	}
	
	messageRouter.on('show-popup-coupon', null, function(param){
		if(param) {
			var bcn_cd = param.bcn_cd;
			var tnt_seq = param.tnt_seq;
		} else {
			return alert('파라미터를 입력해주세요.');
		}
		
		
		if(tnt_seq) {
			Common.REST.get('/rest/' + bcn_cd + '/coupon/tenants/' + tnt_seq, {}, function(couponList) {
				showPopup(couponList.list);
			},function(data) {
				alert('쿠폰 팝업 호출 실패');
				$popup.css('display', 'none');
			});
		} else {
			Common.REST.get('/rest/' + bcn_cd + '/coupon/nomal', {}, function(couponList) {
				showPopup(couponList.list);
			},function(data) {
				alert('쿠폰 팝업 호출 실패');
				$popup.css('display', 'none');
			});
		}
	});
	
})();