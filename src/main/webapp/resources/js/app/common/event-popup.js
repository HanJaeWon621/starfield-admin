var app = app || {};

app.ractive = app.ractive || {};
app.ractive.eventPopup = null;

app.eventPopup = app.eventPopup || (function(){
	var $popup = $('#event-list-popup');
	var eventList = [];
	function showPopup(_eventList) {
		eventList = _eventList;
		if(!app.ractive.eventPopup) {
			app.ractive.eventPopup = new Ractive({
				el : '#event-list-popup',
				template : '#tmpl-event-list-popup',
				data : {
					eventList : eventList
				}
			});
			
			app.ractive.eventPopup.on({
				'select' : function(e, event) {
					messageRouter.trigger('select-popup-event', null, event);
					console.log(event);
					$popup.css('display', 'none');
				},
				'close' : function(e) {
					$popup.css('display', 'none');
				}
			});
		} else {
			app.ractive.eventPopup.reset();
			app.ractive.eventPopup.set({
				eventList : eventList
			});
		}
		$popup.css('display', 'block');
	}
	
	messageRouter.on('show-popup-event', null, function(param){
		if(param) {
			var bcn_cd = param.bcn_cd;
			var tnt_seq = param.tnt_seq;
		} else {
			return alert('파라미터를 입력해주세요.');
		}
		
		Common.REST.get('/admin/rest/' + bcn_cd + '/events/select', {tnt_seq : tnt_seq}, function(eventList) {
			showPopup(eventList);
		},function(data) {
			alert('이벤트 팝업 호출 실패');
			$popup.css('display', 'none');
		});
	});
	
})();