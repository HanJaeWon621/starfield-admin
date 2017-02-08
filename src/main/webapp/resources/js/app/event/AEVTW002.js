var getHourList = function(){
	var hourList = [];
		
	for(var i = 0; i < 24; i++) {
		var hour = (i <= 9 ? "0" : "") + i + ":"+"00";
		hourList.push(hour);
	}
	
	return hourList;
}

var eventData = {
	pickDtYn : false,
	images : [],
	event : {},
	hourList : getHourList(),
	substring : function(str, i, j) {
		return str.substring(i, j);
	},
	brReplace : function(str) {
		str = str.replace(new RegExp('\n','g'), '<br>');
		str = str.replace(new RegExp('\r?\n','g'), '<br>');
		return str;
	}
};

var wonData = {
	getTel : function(tel1, tel2, tel3) {
		var array = [];
		if(tel1 != null) { array.push(tel1); }
		if(tel2 != null) { array.push(tel2); }
		if(tel3 != null) { array.push(tel3); }
		return array.join('-');
	}
}

var aplyData = {
	getTel : function(tel1, tel2, tel3) {
		var array = [];
		if(tel1 != null) { array.push(tel1); }
		if(tel2 != null) { array.push(tel2); }
		if(tel3 != null) { array.push(tel3); }
		return array.join('-');
	}
}

var getMultCntList = function(){
	var multCntList = [];
		
	for(var i = 2; i <= 15; i++) {
		multCntList.push(i);
	}
		
	return multCntList;
}

var pickData = {
	multCntList : getMultCntList(),
//	pick_info : {mult_pick_info : [{win_item : "", pick_count : 0},{win_item : "", pick_count : 0}]}
};

var rtvEvent = new Ractive({
	el : '#event-reg-wrap',
	template : '#tmpl-event-reg',
	data : eventData
});

rtvEvent.on({
	'preview' : function() {
		rtvPreview.set(eventData);
		$('#event-display-popup').show();
	},
	'confirm' : function() {
		if(!confirm("승인하면 이벤트 수정이 불가합니다.\n승인하시겠습니까?")) {
			return;
		}
		confirmEvent();
	},
	'viewAplyInfo' : function() {
		getAplyList();
		$('#entry-list').show();
	},
	'viewWonInfo' : function() {
		getWonList();
		$('#result-popup').show();
	},
	'goPick' : function() {
		$('#draw-popup').show();
	},
	'goList' : function() {
		location.href = "/01/eventCoupon/events"
	},
	'goModify' : function() {
		location.href = "/01/eventCoupon/events/reg?evt_seq="+eventData.event.evt_seq;
	}
});

rtvEvent.observe('event.evt_pick_plan_dt', function(newValue, oldValue, keypath){
	if(newValue) {
		eventData.pickDtYn = new Date(newValue.replace(/\./gi,'-')) <= new Date();
	}
});

var rtvWonInfo = new Ractive({
	el : '#result-popup',
	template : '#tmpl-result-popup',
	data : wonData
});

rtvWonInfo.on({
	'pageMove' : function(o, page) {
		if(page > 0
				&& page <= wonData.paging.total_page_cnt
				&& page != wonData.paging.cur_page) {
			
			var offset = (page - 1) * wonData.paging.list_limit + 1;
			getWonList(offset, wonData.paging.list_limit);
		}
	},
	'search' : function(o) {
		var q = new Array();
		
		var order_key = $(o.node).data("order-key");
		var order_type = "desc";
		
		if(order_key) {	
			$(o.node).siblings().removeClass("selected");
			$(o.node).addClass("selected");
			
			if($(o.node).attr("order-type") == "desc") {
				order_type = "asc";
				$(o.node).attr("order-type", "asc");
			} else {
				order_type = "desc";
				$(o.node).attr("order-type", "desc");
			}
		}
		
		var order_key = $("[data-order-key].selected").data("order-key");
		var order_type = $("[data-order-key].selected").attr("order-type");
		
		if(order_key) {
			q.push("order_key=" + order_key);
			q.push("order_type=" + order_type);
		}
		var offset = (wonData.paging.cur_page - 1) * wonData.paging.list_limit + 1;
		getWonList(offset, wonData.paging.list_limit, q.join(','));
	},
	'close' : function() {
		$('.popup-wrap').hide();
	}
});

var rtvAplyInfo = new Ractive({
	el : '#entry-list',
	template : '#tmpl-entry-list',
	data : aplyData
});

rtvAplyInfo.on({
	'pageMove' : function(o, page) {
		if(page > 0
				&& page <= aplyData.paging.total_page_cnt
				&& page != aplyData.paging.cur_page) {
			
			var offset = (page - 1) * aplyData.paging.list_limit + 1;
			getAplyList(offset, aplyData.paging.list_limit);
		}
	},
	'search' : function(o) {
		var q = new Array();
		
		var order_key = $(o.node).data("order-key");
		var order_type = "desc";
		
		if(order_key) {	
			$(o.node).siblings().removeClass("selected");
			$(o.node).addClass("selected");
			
			if($(o.node).attr("order-type") == "desc") {
				order_type = "asc";
				$(o.node).attr("order-type", "asc");
			} else {
				order_type = "desc";
				$(o.node).attr("order-type", "desc");
			}
		}
		
		var order_key = $("[data-order-key].selected").data("order-key");
		var order_type = $("[data-order-key].selected").attr("order-type");
		
		if(order_key) {
			q.push("order_key=" + order_key);
			q.push("order_type=" + order_type);
		}
		var offset = (aplyData.paging.cur_page - 1) * aplyData.paging.list_limit + 1;
		getAplyList(offset, aplyData.paging.list_limit, q.join(','));
	},
	'close' : function() {
		$('.popup-wrap').hide();
	}
});

var rtvPickView = new Ractive({
	el : '#draw-popup',
	template : '#tmpl-draw-popup',
	data : pickData
});

rtvPickView.on({
	'pick' : function() {
		pick();
	},
	'close' : function() {
		$('.popup-wrap').hide();
	}
});

rtvPickView.observe('pick_info.pick_div_cnt', function(newValue, oldValue, keypath){
	pickData.pick_info.mult_pick_info = [];
	
	for(var i = 0; i < newValue; i++) {
		pickData.pick_info.mult_pick_info.push({win_item : "", pick_count : 0});
	}
	
	rtvPickView.update();
});

rtvPickView.observe('pick_info.pick_div', function(newValue, oldValue, keypath){
	if(newValue == '2') {
		$('#draw-popup').removeClass('type1');
		$('#draw-popup').addClass('type2');
	} else {
		$('#draw-popup').removeClass('type2');
		$('#draw-popup').addClass('type1');
	}
});

var rtvPreview = new Ractive({
	el : '#event-display-popup',
	template : '#tmpl-event-display-popup',
	data : eventData
});

rtvPreview.on({
	'close' : function() {
		$('.popup-wrap').hide();
	}
});

var confirmEvent = function() {

	Common.REST.put('/admin/rest/01/events/confirm/'+eventData.event.evt_seq, {sts : 1}, function(data) {
		alert("승인되었습니다.");
		Common.REST.get('/admin/rest/01/events/'+evt_seq, {}, function(data) {
			rtvEvent.set('event.sts', data.sts);
			rtvEvent.set('event.evt_app_dt', data.evt_app_dt);
			rtvEvent.set('event.evt_app_id', data.evt_app_id);
		}, function(data) {
			console.log('fail data is ', data);
		});
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var getEvent = function(evt_seq) {
	if(!evt_seq) {
		return;
	}
	
	Common.REST.get('/admin/rest/01/events/'+evt_seq, {}, function(data) {
		rtvEvent.set('event', data);
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var getWonList = function(offset, limit, q){
	var offset = offset || 1;
	var limit = limit || 10;
	var reqData = {'offset' : offset, 'limit' : limit, 'q' : q}
	Common.REST.get('/admin/rest/01/events/'+eventData.event.evt_seq+'/winner/members', reqData, function(data) {
		wonData.members = data.list;
		wonData.pick_div = data.list.length > 0 ? data.list[0].pick_div : 1;
		wonData.paging = data.paging;
		wonData.paging.pages = [];
		for(var i = wonData.paging.page_start; i <= wonData.paging.page_end; ++i) {
			wonData.paging.pages.push(i);
        }
		rtvWonInfo.set(wonData);
	}, function(data) {
		console.log('fail data is ', data);
	});
}

var getAplyList = function(offset, limit, q){
	var offset = offset || 1;
	var limit = limit || 10;
	var codeCd = eventData.event.evt_type.codeCd;
	var reqData = {'offset' : offset, 'limit' : limit, 'q' : q, 'codeCd' : codeCd}
	
	Common.REST.get('/admin/rest/01/events/'+eventData.event.evt_seq+'/apply/members', reqData, function(data) {
		aplyData.members = data.list;
		aplyData.paging = data.paging;
		aplyData.paging.pages = [];
		aplyData.codeCd = codeCd;
		for(var i = aplyData.paging.page_start; i <= aplyData.paging.page_end; ++i) {
			aplyData.paging.pages.push(i);
        }
		rtvAplyInfo.set(aplyData);
	}, function(data) {
		console.log('fail data is ', data);
	});
}

var pick = function() {
	var pickInfoData = JSON.parse(JSON.stringify(pickData.pick_info));
	
	if(pickInfoData.pick_div == '1') {
		pickInfoData.pick_div_cnt = 1;
		pickInfoData.mult_pick_info = [{pick_count:pickInfoData.pick_count, win_item : ""}];
	} else {
		pickInfoData.pick_count = pickInfoData.mult_pick_info.reduce(function(prev, curr){ 
			return prev + Number(curr.pick_count);
		}, 0);
		
		for(var i=0; i < pickInfoData.mult_pick_info.length; i++) {
			if(!pickInfoData.mult_pick_info[i].win_item) {
				alert("추첨 구분명을 입력해주세요.");
				return;
			}
			
			var pick_count = Number(pickInfoData.mult_pick_info[i].pick_count);
			if(!pick_count || pick_count < 1) {
				alert("추첨자 수를 입력해주세요.");
				return;
			}
		}
	}

	if(!pickInfoData.pick_count || pickInfoData.pick_count == 0) {
		alert("추첨자 수를 입력해주세요.");
		return;
	}
	
	if(pickInfoData.pick_count > eventData.event.aply_cnt) {
		alert("응모자 수 보다 추첨 수가 더 큽니다.\n추첨자 수를 재 설정해주세요.");
		return;
	}
	
	
	console.log(pickInfoData);
	
	Common.REST.post('/admin/rest/01/events/'+eventData.event.evt_seq+'/pick', pickInfoData, function(data) {
		location.href = "/01/eventCoupon/events?evt_seq="+eventData.event.evt_seq;
	}, function(data) {
		alert(JSON.parse(data).errMsg);
	});
}

$(function() {
	getEvent(evt_seq);
	
	/* 추첨 관련 */
	$('#draw-popup .btn-close').on('click', function() {
		$('.popup-wrap').hide();
	});
});
