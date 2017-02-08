var facilityGroupData = {
	facilityGroup : null,
	paging : ""
};

var sortOrdFacilityData = {}

var rtvFacilityGroup = new Ractive({
	el : '#facility-group-mng',
	template : '#tmpl-facility-group-mng',
	data : facilityGroupData
});

var rtvSortOrdFacility = new Ractive({
	el : '#facility-group-order-popup',
	template : '#tmpl-facility-group-order-popup',
	data : sortOrdFacilityData
});

rtvFacilityGroup.on({
	'pageMove' : function(o, page) {
		if(page > 0
				&& page <= facilityGroupData.paging.total_page_cnt
				&& page != facilityGroupData.paging.cur_page) {
			
			var offset = (page - 1) * facilityGroupData.paging.list_limit + 1;
			getFacilityGroupList(offset, facilityGroupData.paging.list_limit, getFacilityGroupFilter(o));
		}
	},
	'search' : function(o) {
		getFacilityGroupList(1, facilityGroupData.paging.list_limit, getFacilityGroupFilter(o));
	},
	'syncRedis' : function(o) {
		syncRedis();
	},
	'excelDownload' : function(o) {
		getFacilityGroupList(1, -1, getFacilityGroupFilter(o),'excel');
	},
	'delete' : function() {
		var targets = new Array();
		
		var hasFacilityDetail = false;
		
		$('input:checkbox[name="checked"]:checked').each(function(){
			targets.push($(this).val());
			
			if($(this).data('conv_faci_cnt') > 0) {
				hasFacilityDetail = true;
			}
		});
		
		if(targets.length < 1) {
			return alert('삭제할 편의시설 그룹을 1개 이상 선택해주세요.');
		}
		
		if(hasFacilityDetail) {
			return alert('등록된 편의시설이 존재하는 경우 삭제를 할 수 없습니다.');
		}

		var delMsg = '선택한 편의시설을 삭제하시겠습니까?';
		
		if(confirm(delMsg)) {
			delFacilityGroupList(targets.join(','));
		}
	},
	'add' : function() {
		location.href = '/01/stores/facilities/reg'
	},
	'view' : function(o, conv_faci_seq) {
		location.href = '/01/stores/facilities/reg?conv_faci_seq='+conv_faci_seq;
	},
	'open' : function(o, sts, conv_faci_seq) {
		if(!confirm('편의시설 그룹 노출여부를 변경 하시겠습니까?')) {
			return;
		}
		openFacilityGroup(sts, conv_faci_seq);
	},
	'goFacilityDetail' : function(o, conv_faci_seq) {
		location.href = '/01/stores/facilities/detail?conv_faci_seq='+conv_faci_seq;
	},
	'viewSortPopup' : function() {
		getSortOrdFacilityData();
		$('#facility-group-order-popup').addClass('on');
	}
});

rtvSortOrdFacility.on({
	'close' : function() {
		if(!confirm('편의시설 그룹 게시 순서를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
			return;
		}
		$('#facility-group-order-popup').removeClass('on');
	},
	'save' : function() {
		if(!confirm('편의시설 그룹 게시 순서를 수정하시겠습니까?')) {
			return;
		}
		setSortOrdFacilityGroup();
	}
})

var setSortOrdFacilityGroup = function() {
	var modiData = JSON.parse(JSON.stringify(sortOrdFacilityData.facilityGroup));

	modiData.forEach(function(i){
		i.sort_ord = i.no;
		
		for(property in i) {
			if(property != 'sort_ord' && property != 'conv_faci_seq') {
				delete i[property];
			}
		}
	});

	console.log('facilityGroup is ', JSON.stringify(modiData));
	
	Common.REST.put('/admin/rest/01/facilities/', modiData, function(data) {
		alert("편의시설 그룹 게시 순서가 수정되었습니다.");
		location.href = "/01/stores/facilities";
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var delFacilityGroupList = function(targets) {
	var restUri = '/admin/rest/01/facilities';

	Common.REST.del(restUri+'/'+targets, {}, function(data) {
		$('#search-btn').trigger('click');
	}, function(data) {
		console.log('fail data is ', data);
	});
}

var openFacilityGroup = function(sts, target) {
	var restUri = '/admin/rest/01/facilities';

	Common.REST.put(restUri+'/'+target, { sts : sts }, function(data) {
		$('#search-btn').trigger('click');
	}, function(data) {
		console.log('fail data is ', data);
	});
}

function getFacilityGroupFilter(o) {
	var q = new Array();
	
	for(var i in facilityGroupData.filter) {
		q.push(i + '=' + facilityGroupData.filter[i]);
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

function syncRedis() {
	
	if(!confirm('실행동안 편의시설 정보에 오류가 발생할 수 있습니다.\n동기화 하시겠습니까?')) {
		return;
	}
	
	messageRouter.trigger('show-popup-loading');
	Common.REST.get('/admin/rest/01/facility/redis', {}, function(data) {
		alert('완료되었습니다.');
		messageRouter.trigger('hide-popup-loading');
	}, function(data) {
		alert('실패하였습니다.');
		console.log('fail data is ', data);
		messageRouter.trigger('hide-popup-loading');
	});
}

function getFacilityGroupList(offset, limit, q, type) {
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = "/admin/rest/01/facilities";
	var reqData = {'offset' : offset, 'limit' : limit, 'q' : q}
	
	if(type == 'excel') {
		restUri = '/01/stores/facilities/excel';
		reqData.limit = -1;
		
//		location.href = restUri+"?q="+q;
		location.href = restUri;

	} else {
		Common.REST.get(restUri, reqData, function(data) {
			facilityGroupData.facilityGroup = data.list;
			facilityGroupData.paging = data.paging;
			facilityGroupData.paging.pages = [];
			
			for(var i = facilityGroupData.paging.page_start; i <= facilityGroupData.paging.page_end; ++i) {
				facilityGroupData.paging.pages.push(i);
	        }
			rtvFacilityGroup.reset();
			rtvFacilityGroup.set(facilityGroupData);

		}, function(data) {
			console.log('fail data is ', data);
		});
	}
}

function getSortOrdFacilityData() {
	var restUri = "/admin/rest/01/facilities";
	var reqData = {'offset' : 0, 'limit' : -1, 'q' : 'faci_type=Y,order_key=sort_ord'}
	
	Common.REST.get(restUri, reqData, function(data) {
		sortOrdFacilityData.facilityGroup = data.list;
		rtvSortOrdFacility.set(sortOrdFacilityData);
		
		var group = $("ul.sort").sortable({
			group: 'sort',
			onDrop: function ($item, container, _super) {
				var list = group.sortable("serialize").get()[0];
			 	_super($item, container);
			 	for(var i = 0, iMax = list.length; i < iMax; i++) {
			 		sortOrdFacilityData.facilityGroup[list[i].no - 1].no = i + 1;
			 	}
			 	
			 	sortOrdFacilityData.facilityGroup = sortOrdFacilityData.facilityGroup.sort(function(a, b){
			 		return a.no - b.no;
			 	});
				
			 	rtvSortOrdFacility.reset();
				rtvSortOrdFacility.set(sortOrdFacilityData);
			},
			distance : 10
		});

	}, function(data) {
		console.log('fail data is ', data);
	});
}

$(function() {
	getFacilityGroupList();
});
