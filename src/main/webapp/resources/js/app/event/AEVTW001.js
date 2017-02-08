var eventData = {
	eventList : null,
	paging : "",
	getYYMMDD : function(timestamp, _type) {

		var date = new Date(timestamp);
		
	    var type = _type || ' ';
	
	    var arr = [];
	    arr.push((date.getFullYear()+"").substring(2, 4));
	    arr.push(( (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1) ));
	    arr.push(( date.getDate() < 10 ? '0' + date.getDate() : date.getDate()));
	    return arr.join(type);
	}
};

var rtvEvent = new Ractive({
	el : '#event-mng',
	template : '#tmpl-event-list',
	data : eventData
});

rtvEvent.on({
	'pageMove' : function(o, page) {
		if(page > 0
				&& page <= eventData.paging.total_page_cnt
				&& page != eventData.paging.cur_page) {

			var offset = (page - 1) * eventData.paging.list_limit + 1;
			getEventList(offset, eventData.paging.list_limit, getEventFilter(o));
		}
	},
	'search' : function(o) {
		getEventList(1, eventData.paging.list_limit, getEventFilter(o));
	}
	,
	'excelDownload' : function(o) {
		getEventList(1, -1, getEventFilter(o),'excel');
	} 
	,
	'changeSearchOption' : function(o) {
		if($(o.node).val() == 3) {
			var index = $("#evt-end-dt").index();
			$("#evt-end-dt").siblings().eq(index-1).hide();
			$("#evt-end-dt").hide();
			$("#evt-strt-dt > input").attr('placeholder','발표일자');
		} else {
			$("#evt-end-dt").siblings().eq(index-1).show();
			$("#evt-end-dt").show();
			$("#evt-strt-dt > input").attr('placeholder','시작일자');
		}
	},
	'delete' : function() {
		var targets = new Array();
		
		$('input:checkbox[name="checked"]:checked').each(function(){
			targets.push($(this).val());
		});
		
		if(targets.length < 1) {
			return alert('삭제할 이벤트를 1개 이상 선택해주세요.');
		}
		
		if(confirm('선택한 이벤트를 삭제하시겠습니까?')) {
			delEventList(targets.join(','));
		}
	},
	'close' : function() {
		if(!confirm('이벤트를 조기종료하시겠습니까?')) {
			return;
		}
		var targets = new Array();

		$('input:checkbox[name="checked"]:checked').each(function(){
			if($(this).data('stat') == '진행중') {
				targets.push($(this).val());
			}
		});
		
		if(targets.length < 1) {
			return alert('조기 종료할 이벤트를 1개 이상 선택해주세요.\n진행 중 이벤트만 조기 종료가 가능합니다.');
		}
		
		if(confirm('선택한 이벤트를 조기종료 하시겠습니까?')) {
			closeEventList(targets.join(','));
		}
	},
	'add' : function() {
		location.href = '/01/eventCoupon/events/reg'
	},
	'modify' : function(o, evt_seq) {
		location.href = '/01/eventCoupon/events/reg?evt_seq='+evt_seq;
	},
	'open' : function(o, open_yn, evt_seq) {
		if(!confirm('이벤트를 노출여부를 변경 하시겠습니까?')) {
			return;
		}
		openEvent(open_yn, evt_seq);
	},
	'view' : function(o, evt_seq) {
		location.href = '/01/eventCoupon/events?evt_seq='+evt_seq;
	}
});

function getEventFilter(o) {
	var q = new Array();
	
	for(var i in eventData.filter) {
		q.push(i + '=' + eventData.filter[i]);
	}
	
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
	
	return q.join(',');
}

var openEvent = function(yn, target) {
	var restUri = '/admin/rest/01/events';

	Common.REST.put(restUri+'/'+target, { evt_open_yn : yn }, function(data) {
		$('#search-btn').trigger('click');
	}, function(data) {
		console.log('fail data is ', data);
	});
}

var closeEventList = function(targets) {
	var restUri = '/admin/rest/01/events';

	var date = new Date()
	var temp = date.getDate();
	date.setDate(temp - 1);

    var year= date.getFullYear();
    var mon = (date.getMonth()+1)>9 ? ''+(date.getMonth()+1) : '0'+(date.getMonth()+1);
    var day = date.getDate()>9 ? ''+date.getDate() : '0'+date.getDate();
            
    var resultDate = year + '.' + mon + '.' + day;
	    
	Common.REST.put(restUri+'/'+targets, {evt_end_dt : resultDate}, function(data) {
		alert('종료되었습니다.');
		$('#search-btn').trigger('click');
	}, function(data) {
		console.log('fail data is ', data);
	});
}

var delEventList = function(targets) {
	var restUri = '/admin/rest/01/events';

	Common.REST.del(restUri+'/'+targets, {}, function(data) {
		$('#search-btn').trigger('click');
	}, function(data) {
		console.log('fail data is ', data);
	});
}

var getEventList = function(offset, limit, q, type) {
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = '/admin/rest/01/events';
	var reqData = {'offset' : offset, 'limit' : limit, 'q' : q}
	
	if(type == 'excel') {
		restUri = '/01/eventCoupon/events/excel';
		reqData.limit = -1;
		
//		location.href = restUri+"?q="+q;
		location.href = restUri;

	} else {
		Common.REST.get(restUri, reqData, function(data) {
			eventData.eventList = data.list;
			eventData.paging = data.paging;
			eventData.paging.pages = [];
			
			for(var i = eventData.paging.page_start; i <= eventData.paging.page_end; ++i) {
				eventData.paging.pages.push(i);
	        }
			rtvEvent.reset();
			rtvEvent.set(eventData);
		}, function(data) {
			console.log('fail data is ', data);
		});
	}
};

$(function() {
	getEventList();
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy.mm.dd",
		onSelect: function() {
			rtvEvent.set('filter.search_option_yn', true);
			rtvEvent.updateModel();
		}
	});
});


