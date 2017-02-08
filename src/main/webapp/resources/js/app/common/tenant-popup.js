var tenantPopupData = {
};

var rtvTenantPopup = new Ractive({
	el : '#brand-mng-popup',
	template : '#tmpl-brand-mng-popup',
	data : tenantPopupData
});

rtvTenantPopup.on({
	'pageMove' : function(o, page) {
		if(page > 0
				&& page <= tenantPopupData.paging.total_page_cnt
				&& page != tenantPopupData.paging.cur_page) {
			
			var offset = (page - 1) * tenantPopupData.paging.list_limit + 1;
			getTenantList(offset, tenantPopupData.paging.list_limit, getTenantFilter(o));
		}
	},
	'search' : function(o) {
		getTenantList(1, tenantPopupData.paging.list_limit, getTenantFilter(o));
	},
	'changeFirstCate' : function() {
		getSecondCategory(tenantPopupData.filter.mama_cate_seq);
		tenantPopupData.filter.cate_seq = "";
		$('#search-btn').trigger('click');
	},
	'changeSecondCate' : function(o, color, type) {
		$('#search-btn').trigger('click');
	},
	'selectTenant' : function(o, tenant) {
		console.log(tenant);
		messageRouter.trigger('select-popup-tenent', null, tenant);
		$('#brand-mng-popup').hide();
	},
	'closePopup' : function() {
		$('#brand-mng-popup').hide();
	}
});

function getTenantFilter(o) {
	var q = new Array();
	
	for(var i in tenantPopupData.filter) {
		q.push(i + '=' + tenantPopupData.filter[i]);
	}
	
	var order_key = o ? $(o.node).data("order-key") : "";
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

function getTenantList(offset, limit, q) {
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = "/admin/rest/01/tenants/in/category";
	var reqData = {'offset' : offset, 'limit' : limit, 'q' : q}
	
	Common.REST.get(restUri, reqData, function(data) {
		data.paging.pages = [];
		
		for(var i = data.paging.page_start; i <= data.paging.page_end; ++i) {
			data.paging.pages.push(i);
        }

		rtvTenantPopup.set('paging', data.paging);
		rtvTenantPopup.set('tenants', data.list);
		
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}

function getFirstCategory() {
	var restUri = "/admin/rest/01/categories/TENANT";
	var reqData = {};
	
	Common.REST.get(restUri, reqData, function(rootCate) {

		reqData = {offset : 0, limit : -1, q : 'sts=1'};
		Common.REST.get(restUri+"/"+rootCate.cate_seq, reqData, function(data) {
			rtvTenantPopup.set('firstCategories', data.list);
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
		rtvTenantPopup.set('secondCategories', data.list);
	}, function(data) {
		console.log('fail data is ', data);
	});
}

// q = {tnt_type : 1}
messageRouter.on('show-popup-tenant', null, function(q){
	
	if(q) {
		for(var i in q) {
			tenantPopupData.filter[i] = q[i];
		}
	}
	
	getTenantList(null, null, getTenantFilter(null));
	
	$('#brand-mng-popup').show();
});


$(function() {
	getFirstCategory();
});
