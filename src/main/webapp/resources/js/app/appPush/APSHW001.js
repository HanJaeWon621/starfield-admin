var PUSH_WEB_URI = '/01/operation/pushes';
var PUSH_REST_URI = '/admin/rest/01/pushes';

var pushData = {
	pushList : null,
	paging : ""
};

var rtvPush = new Ractive({
	el : '#push-mng',
	template : '#tmpl-push-mng',
	data : pushData
});

rtvPush.on({
	'pageMove' : function(o, page) {
		if(page > 0
				&& page <= pushData.paging.total_page_cnt
				&& page != pushData.paging.cur_page) {

			var offset = (page - 1) * pushData.paging.list_limit + 1;
			getPushList(offset, pushData.paging.list_limit, getPushFilter(o));
		}
	},
	'search' : function(o) {
		
		if(pushData.filter.search_option_yn && (!pushData.filter.push_strt_dt || !pushData.filter.push_end_dt)) {
			alert('검색할 시작일자 또는 종료일자를 입력해주세요.');
			return;
		}
		
		getPushList(1, pushData.paging.list_limit, getPushFilter(o));
	}
	,
	'delete' : function() {
		// TODO : 삭제는 발송상태 전일때만 !! 실시간 체크 필요.
		var targets = new Array();
		var alreadySend = false;
		
		$('input:checkbox[name="checked"]:checked').each(function(){
			targets.push($(this).val());
			
			if($(this).data('send_dttm')) {
				alreadySend = true;
			}
		});
		
		if(targets.length < 1) {
			return alert('삭제할 PUSH를 1개 이상 선택해주세요.');
		}
		
		if(alreadySend) {
			return alert('이미 발송된 푸시는 삭제가 불가합니다.');
		}
		
		if(confirm('선택한 PUSH를 삭제하시겠습니까?')) {
			delPushList(targets.join(','));
		}
	},
	'add' : function() {
		location.href = PUSH_WEB_URI + '/reg'
	},
	'modify' : function(o, push_mng_seq) {
		location.href = PUSH_WEB_URI + '/reg?push_mng_seq='+push_mng_seq;
	},
	'excelDownload' : function(o) {
		getPushList(1, -1, getPushFilter(o),'excel');
	}
});

function getPushFilter(o) {
	var q = new Array();
	
	for(var i in pushData.filter) {
		q.push(i + '=' + pushData.filter[i]);
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

var delPushList = function(targets) {
	var restUri = PUSH_REST_URI;

	Common.REST.del(restUri+'/'+targets, {}, function(data) {
		$('#search-btn').trigger('click');
	}, function(data) {
		console.log('fail data is ', data);
	});
}

var getPushList = function(offset, limit, q, type) {
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = PUSH_REST_URI;
	var reqData = {'offset' : offset, 'limit' : limit, 'q' : q}
	
	if(type == 'excel') {
		restUri = PUSH_WEB_URI+'/excel';
		reqData.limit = -1;
		
//		location.href = restUri+"?q="+q;
		location.href = restUri;

	} else {
		Common.REST.get(restUri, reqData, function(data) {
			pushData.pushList = data.list;
			pushData.paging = data.paging;
			pushData.paging.pages = [];
			
			for(var i = pushData.paging.page_start; i <= pushData.paging.page_end; ++i) {
				pushData.paging.pages.push(i);
	        }
			rtvPush.reset();
			rtvPush.set(pushData);
		}, function(data) {
			console.log('fail data is ', data);
		});
	}
};

$(function() {
	getPushList();
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy.mm.dd",
		onSelect: function() {
			rtvPush.set('filter.search_option_yn', true);
			rtvPush.updateModel();
		}
	});
});


