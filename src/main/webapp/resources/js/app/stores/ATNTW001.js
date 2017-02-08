var tenantData = {
	tenants : null,
	paging : ""
};

var rtvTenant = new Ractive({
	el : '#tenant-mng',
	template : '#tmpl-tenant-list',
	data : tenantData
});

rtvTenant.on({
	'pageMove' : function(o, page) {
		if(page > 0
				&& page <= tenantData.paging.total_page_cnt
				&& page != tenantData.paging.cur_page) {
			
			var offset = (page - 1) * tenantData.paging.list_limit + 1;
			getTenantList(offset, tenantData.paging.list_limit, getTenantFilter(o));
		}
	},
	'search' : function(o) {
		getTenantList(1, tenantData.paging.list_limit, getTenantFilter(o));
	},
	'syncRedis' : function(o) {
		syncRedis();
	},
	'excelDownload' : function(o) {
		getTenantList(1, -1, getTenantFilter(o),'excel');
	},
	'changeFirstCate' : function() {
		getSecondCategory(tenantData.filter.mama_cate_seq);
		tenantData.filter.cate_seq = "";
		$('#search-btn').trigger('click');
	},
	'changeSecondCate' : function(o, color, type) {
		$('#search-btn').trigger('click');
	},
	'closePopup' : function() {
		$('#brand-mng-popup').hide();
	},
	'delete' : function() {
		var targets = new Array();
		
		var hasEventCoupon = false;
		
		$('input:checkbox[name="checked"]:checked').each(function(){
			targets.push($(this).val());
			
			if($(this).data('evt_cnt') > 0 || $(this).data('cp_cnt') > 0) {
				hasEventCoupon = true;
			}
		});
		
		if(targets.length < 1) {
			return alert('삭제할 매장을 1개 이상 선택해주세요.');
		}
		
		var warnMsg = '쿠폰/이벤트가 1개 이상인 매장이 있습니다.\n';
		var delMsg = '선택한 매장을 삭제하시겠습니까?'; hasEventCoupon ? warnMsg + delMsg : delMsg
		
		if(confirm(hasEventCoupon ? warnMsg + delMsg : delMsg)) {
			delTenantList(targets.join(','));
		}
	},
	'add' : function() {
		location.href = '/01/stores/tenants/reg'
	},
	'view' : function(o, tnt_seq) {
		location.href = '/01/stores/tenants/reg?tnt_seq='+tnt_seq;
	},
	'open' : function(o, opr_sts, sts, tnt_seq) {
		if(opr_sts != 1 && sts == 1) {
			alert("노출은 운영상태가 정상운영일 경우에만 가능합니다.");
			return;
		}
		
		if(!confirm('매장 노출여부를 변경 하시겠습니까?')) {
			return;
		}
		openTenant(sts, tnt_seq);
	},
	'openEventPopup' : function(o, cnt, tnt_seq) {
		if(cnt) {
			messageRouter.trigger('show-popup-event', null, {bcn_cd : '01', tnt_seq : tnt_seq});
		}
	},
	'openCouponPopup' : function(o, cnt, tnt_seq) {
		if(cnt) {
			messageRouter.trigger('show-popup-coupon', null, {bcn_cd : '01', tnt_seq : tnt_seq});
		}
	}
});

var delTenantList = function(targets) {
	var restUri = '/admin/rest/01/tenants';

	Common.REST.del(restUri+'/'+targets, {}, function(data) {
		$('#search-btn').trigger('click');
	}, function(data) {
		console.log('fail data is ', data);
	});
}

var openTenant = function(sts, target) {
	var restUri = '/admin/rest/01/tenants';

	Common.REST.put(restUri+'/'+target, { sts : sts }, function(data) {
		$('#search-btn').trigger('click');
	}, function(data) {
		console.log('fail data is ', data);
	});
}

function getTenantFilter(o) {
	var q = new Array();
	
	for(var i in tenantData.filter) {
		q.push(i + '=' + tenantData.filter[i]);
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
	
	if(!confirm('동기화시 20초 이상 시간이 소요되며\n실행동안 매장 정보에 오류가 발생할 수 있습니다.\n동기화 하시겠습니까?')) {
		return;
	}
	
	messageRouter.trigger('show-popup-loading');
	Common.REST.get('/admin/rest/01/tenants/redis', {}, function(data) {
		alert('완료되었습니다.');
		messageRouter.trigger('hide-popup-loading');
	}, function(data) {
		alert('실패하였습니다.');
		console.log('fail data is ', data);
		messageRouter.trigger('hide-popup-loading');
	});
}

function getTenantList(offset, limit, q, type) {
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = "/admin/rest/01/tenants";
	var reqData = {'offset' : offset, 'limit' : limit, 'q' : q}
	
	if(type == 'excel') {
		restUri = '/01/stores/teannts/excel';
		reqData.limit = -1;
		
		location.href = restUri+"?q="+q;

	} else {
		Common.REST.get(restUri, reqData, function(data) {
			tenantData.tenants = data.list;
			tenantData.paging = data.paging;
			tenantData.paging.pages = [];
			
			for(var i = tenantData.paging.page_start; i <= tenantData.paging.page_end; ++i) {
				tenantData.paging.pages.push(i);
	        }
			rtvTenant.reset();
			rtvTenant.set(tenantData);
			
		}, function(data) {
			console.log('fail data is ', data);
		});
	}
}

function getFirstCategory() {
	var restUri = "/admin/rest/01/categories/TENANT";
	var reqData = {};
	
	Common.REST.get(restUri, reqData, function(rootCate) {

		reqData = {offset : 0, limit : -1, q : 'sts=1'};
		Common.REST.get(restUri+"/"+rootCate.cate_seq, reqData, function(data) {
			tenantData.firstCategories = data.list;
			rtvTenant.set('firstCategories', tenantData.firstCategories);
		}, function(data) {
			console.log('fail data is ', data);
		});
	}, function(data) {
		console.log('fail data is ', data);
	});
}

function getSecondCategory(cateSeq) {
	var restUri = "/admin/rest/01/categories/TENANT";
	var reqData = {offset : 0, limit : -1, q : 'sts=1'};
	
	Common.REST.get(restUri+"/"+cateSeq, reqData, function(data) {
		tenantData.secondCategories = data.list;
		rtvTenant.set('secondCategories', tenantData.secondCategories);
	}, function(data) {
		console.log('fail data is ', data);
	});
}

$(function() {
	getFirstCategory();
	getTenantList();
});
