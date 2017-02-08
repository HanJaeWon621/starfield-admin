
////////////////////////////////////////////////////////
// global variables

var statsData  = {
	statsList : null,
	paging : {},
	paging2 : {},
	filter : {
		sortColumn: 'reg_dttm',
		sortColumnAscDesc: 'DESC',
		sh_dt:null
	},
	selStatsDtlList : null,
	statsDtl : null
};
 
var statsData2  = {
		//paging : {},
		paging2 : {},
		filter : {
			sortColumn: 'reg_dttm',
			sortColumnAscDesc: 'DESC',
			sh_dt:null
		},
		selStatsDtlList : null,
		statsDtl : null
	};
	 

var rtvStats = new Ractive({
	el : '#stats-list',
	template : '#tmpl-stats-list',
	data : statsData
});

var rtvStats2 = new Ractive({
	el : '#stats-dtl-list',
	template : '#tmpl-stats-dtl-list',
	data : statsData2
});
 
rtvStats.on({
	'movePage' : function(o, page) {
		if(page > 0 && page <= statsData.paging.total_page_cnt && page != statsData.paging.cur_page) {
			var offset = (page - 1) * statsData.paging.list_limit + 1; 
			installStats(offset, statsData.paging.list_limit);
		}
	},
	'search' : function(o) {
		//alert("aaa");
		//var date = new Date();
		//console.log("click"+ date);
		//return;
		installStats();
	},
	'downExcel' : function(o) {
		downExcel();
	},
	'listSort' : function(o,sortColumn){  
		//alert($(o.node).data("order-key"));
		getSortFilter(o, statsData.filter,'sortColumn','sortColumnAscDesc');
		installStats(1,10,sortColumn);
		
	},
	'openPop' : function(o,zone_id, pre_visit_tot_cnt, post_visit_tot_cnt){
		//alert("1111");
		$("#c_zone_id").val(zone_id);
		//alert(pre_visit_cnt +","+post_visit_cnt);
		
		$("#tnt_nm").text(zone_id);
		$("#pre_visit_tot_cnt").text(pre_visit_tot_cnt);
		$("#post_visit_tot_cnt").text(post_visit_tot_cnt);
		tenantAll(1,10);
		$("div[name='tnt-reg-popup2']").show();
	}
	,
	'closePop' : function(o,tnt_seq){
		//alert("1111");
		$("div[name='tnt-reg-popup2']").hide();
	}
});

rtvStats2.on({
	'movePage' : function(o, page) {
		if(page > 0 && page <= statsData.paging.total_page_cnt && page != statsData.paging.cur_page) {
			var offset = (page - 1) * statsData.paging.list_limit + 1; 
			installStats(offset, statsData.paging.list_limit);
		}
	},
	'movePage2' : function(o, page) {
		if(page > 0 && page <= statsData2.paging2.total_page_cnt && page != statsData2.paging2.cur_page) {
			var offset = (page - 1) * statsData2.paging2.list_limit + 1; 
			tenantAll(offset, statsData2.paging2.list_limit);
		}
	},
	'search' : function(o) {
		installStats();
	},
	'downExcel' : function(o) {
		downExcel();
	},
	'listSort' : function(o,sortColumn){  
		//alert($(o.node).data("order-key"));
		getSortFilter(o, statsData.filter,'sortColumn','sortColumnAscDesc');
		installStats(1,10,sortColumn);
		
	}
	/*
	,
	'openPop' : function(o,tnt_seq){
		tenantAll(1,10);
		$("div[name='tnt-reg-popup2']").show();
	}
	*/
	,
	'closePop' : function(o,tnt_seq){
		//alert("1111");
		$("div[name='tnt-reg-popup2']").hide();
	}
});
var installStats = function(offset, limit){
	var search_dt = $("#sh_dt").val() == '' ? today() : $("#sh_dt").val();
	statsData.filter.sh_dt = search_dt;
	var offset = offset || 1;  
	var limit = limit || 10;
	
	var restUri = window.location.origin + '/rest/01/getMovePathStats';
	restUri += '?';  
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_dt=' + statsData.filter.sh_dt.split('-').join('');
	restUri += '&sortColumn=' + statsData.filter.sortColumn;
	restUri += '&sortColumnAscDesc=' + statsData.filter.sortColumnAscDesc; 
	 
	Common.REST.get(restUri, {}, function(data) {
		statsData.statsList = data.list; 
		statsData.paging = data.paging; 
 
		statsData.paging.pages = [];
		 
		for(var  i = statsData.paging.page_start; i <= statsData.paging.page_end; ++i) {
			statsData.paging.pages.push(i);
        }

		rtvStats.set(statsData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
}

var tenantAll = function(offset, limit){ 
	
	var offset = offset || 1;  
	var limit = limit || 10;
	//statsData.statsDtl.selStatsDtlList = null;
	var restUri = window.location.origin + '/rest/01/getMovePathStatsDtl';
	var c_zone_id = $("#c_zone_id").val();
	
	restUri += '?';  
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_dt=' + statsData.filter.sh_dt.split('-').join('');
	restUri += '&sh_zone_id='+c_zone_id;// + statsData.filter.sh_dt_type;
	restUri += '&sh_tnt_seq=1';// + statsData.filter.sh_dt_type;
	restUri += '&sh_grade=1';// + statsData.filter.sh_dt_type;
	Common.REST.get(restUri, {}, function(data) {
		statsData2.selStatsDtlList = data.list; 
		statsData2.paging2 = data.paging; 
		
		statsData2.paging2.pages = [];
		 
		for(var  i = statsData2.paging2.page_start; i <= statsData2.paging2.page_end; ++i) {
			statsData2.paging2.pages.push(i);
        }

		rtvStats2.set(statsData2);
		//tennantSave();
		
	}, function(data) { 
		
	}); 
}
var downExcel = function(offset, limit) {
	var search_dt = $("#sh_dt").val() == '' ? monthAgo(today()) : $("#sh_dt").val();
	statsData.filter.sh_dt = search_dt;
	var offset = offset || 1;  
	var limit = limit || 10;
	
	var restUri = window.location.origin + '/01/excel/getMovePathStats';
	restUri += '?';  
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_dt=' + statsData.filter.sh_dt.split('-').join('');
	
	location.href = restUri;
};



 
$(function() {
	installStats(1, 10);
	//달력 		
		$("[name='datepicker2']" ).datepicker({
			dateFormat: "yy-mm-dd"
		}); 

	
});


