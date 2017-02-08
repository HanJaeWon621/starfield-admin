var facilityData = {
	facility : null,
	paging : ""
};

var rtvFacility = new Ractive({
	el : '#facility-mng',
	template : '#tmpl-facility-mng',
	data : facilityData
});

rtvFacility.on({
	'pageMove' : function(o, page) {
		if(page > 0
				&& page <= facilityData.paging.total_page_cnt
				&& page != facilityData.paging.cur_page) {
			
			var offset = (page - 1) * facilityData.paging.list_limit + 1;
			getFacilityList(offset, facilityData.paging.list_limit, getFacilityFilter(o));
		}
	},
	'search' : function(o) {
		getFacilityList(1, facilityData.paging.list_limit, getFacilityFilter(o));
	},
	'delete' : function() {
		var targets = new Array();

		$('input:checkbox[name="checked"]:checked').each(function(){
			targets.push($(this).val());
		});
		
		if(targets.length < 1) {
			return alert('삭제할 편의시설을 1개 이상 선택해주세요.');
		}

		var delMsg = '선택한 편의시설을 삭제하시겠습니까?';
		
		if(confirm(delMsg)) {
			delFacilityList(targets.join(','));
		}
	},
	'add' : function() {
		location.href = '/01/stores/facilities/detail/reg?conv_faci_seq='+conv_faci_seq;
	},
	'view' : function(o, conv_faci_dtl_seq) {
		location.href = '/01/stores/facilities/detail/reg?conv_faci_seq='+conv_faci_seq+'&conv_faci_dtl_seq='+conv_faci_dtl_seq;
	},
	'open' : function(o, sts, data_sts, conv_faci_dtl_seq) {
		if(data_sts != 1 && sts == 1) {
			alert('노출은 데이터가 완결일 경우에만 가능합니다.');
			return;
		}
		
		if(!confirm('편의시설 노출여부를 변경 하시겠습니까?')) {
			return;
		}
		openFacility(sts, conv_faci_dtl_seq);
	},
	'goFacilityGroup' : function() {
		location.href = '/01/stores/facilities';
	}
});

var delFacilityList = function(targets) {
	var restUri = '/admin/rest/01/facilities/'+conv_faci_seq;

	Common.REST.del(restUri+'/'+targets, {}, function(data) {
		$('#search-btn').trigger('click');
	}, function(data) {
		console.log('fail data is ', data);
	});
}

var openFacility = function(sts, target) {
	var restUri = '/admin/rest/01/facilities/'+conv_faci_seq;

	Common.REST.put(restUri+'/'+target, { sts : sts }, function(data) {
		$('#search-btn').trigger('click');
	}, function(data) {
		console.log('fail data is ', data);
	});
}

function getFacilityFilter(o) {
	var q = new Array();
	
	for(var i in facilityData.filter) {
		q.push(i + '=' + facilityData.filter[i]);
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

function getFacilityList(offset, limit, q, type) {
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = '/admin/rest/01/facilities/' + conv_faci_seq + '/all';
	var reqData = {'offset' : offset, 'limit' : limit, 'q' : q}
	
	if(type == 'excel') {
		restUri = '/01/stores/facilities/excel';
		reqData.limit = -1;
		
		location.href = restUri+"?q="+q;

	} else {
		Common.REST.get(restUri, reqData, function(data) {
			facilityData.facility = data.list;
			facilityData.paging = data.paging;
			facilityData.paging.pages = [];
			
			for(var i = facilityData.paging.page_start; i <= facilityData.paging.page_end; ++i) {
				facilityData.paging.pages.push(i);
	        }
			rtvFacility.reset();
			rtvFacility.set(facilityData);

		}, function(data) {
			console.log('fail data is ', data);
		});
	}
}

$(function() {
	getFacilityList();
});
