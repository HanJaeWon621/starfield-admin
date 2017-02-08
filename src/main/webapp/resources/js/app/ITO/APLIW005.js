
////////////////////////////////////////////////////////
// global variables

var appLogListData  = {
	appLogList : null, 
	filter :{
		sh_end_dt : '',
		sh_strt_dt : '',
		sh_text_type : '2',
		sh_type1 : '0',
		sh_type2 : '-1',
		sh_text : '', 
		sortColumn: 'f_reg_dttm',
		sortColumnAscDesc: 'DESC'
	}
};
 
 
var rtvLocation = new Ractive({
	el : '#location-mng',
	template : '#tmpl-location-mng',
	data : appLogListData
});
  
rtvLocation.on({
	'pageMove' : function(o, page) {
		if(page > 0 && page <= appLogListData.paging.total_page_cnt && page != appLogListData.paging.cur_page) {
			var offset = (page - 1) * appLogListData.paging.list_limit + 1; 
		
			getAppLogList(offset, appLogListData.paging.list_limit);
		}
	},
	'search' : function(o) { 
		getAppLogList(1,10);
	},
	'search_init' : function(o) { 
		getAppLogList(1,10,"init");
	},
	'listSort' : function(o,sortColumn){  
		//alert($(o.node).data("order-key"));
		getSortFilter(o, appLogListData.filter,'sortColumn','sortColumnAscDesc');
		getAppLogList(1,10);
		
	},
	'downExcel' : function(){  
		//alert(sortType);
		getAppLogListExcel();
	}
}); 



var getAppLogList = function(offset, limit,div_cd) {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	appLogListData.filter.sh_end_dt = search_end_dt;
	appLogListData.filter.sh_strt_dt = search_strt_dt;
	if(div_cd=='init'){
		appLogListData.filter.sh_end_dt=today();
		appLogListData.filter.sh_strt_dt=monthAgo(today());
		appLogListData.filter.sh_type1="0";
		appLogListData.filter.sh_type2="-1";
		appLogListData.filter.sh_text_type="2";
		appLogListData.filter.sh_text="";
		appLogListData.filter.sortColumn="f_reg_dttm";
		appLogListData.filter.sortColumnAscDesc="desc";
	}
	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getAppLogList';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit; 
	restUri += '&sh_strt_dt=' + appLogListData.filter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + appLogListData.filter.sh_end_dt.split('-').join(''); 
	restUri += '&sh_type1=' + appLogListData.filter.sh_type1; 
	restUri += '&sh_type2=' + appLogListData.filter.sh_type2; 
	restUri += '&sh_text_type=' + appLogListData.filter.sh_text_type; 
	restUri += '&sh_text=' + appLogListData.filter.sh_text;
	restUri += '&sortColumn=' + appLogListData.filter.sortColumn;
	restUri += '&sortColumnAscDesc=' + appLogListData.filter.sortColumnAscDesc;
	Common.REST.get(restUri, {}, function(data) { 
		appLogListData.appLogList = data.list; 
		appLogListData.paging = data.paging; 
 
		appLogListData.paging.pages = [];
		 
		for(var  i = appLogListData.paging.page_start; i <= appLogListData.paging.page_end; ++i) {
			appLogListData.paging.pages.push(i);
        }
 
		rtvLocation.set(appLogListData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
}; 
var getAppLogListExcel = function(offset, limit) {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	appLogListData.filter.sh_end_dt = search_end_dt;
	appLogListData.filter.sh_strt_dt = search_strt_dt;
	 
	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/01/getAppLogList/excel';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit; 
	restUri += '&sh_strt_dt=' + search_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + search_end_dt.split('-').join(''); 
	restUri += '&sh_type1=' + appLogListData.filter.sh_type1; 
	restUri += '&sh_type2=' + appLogListData.filter.sh_type2; 
	restUri += '&sh_text_type=' + appLogListData.filter.sh_text_type; 
	restUri += '&sh_text=' + appLogListData.filter.sh_text;
	restUri += '&sortColumn=' + appLogListData.filter.sortColumn;
	restUri += '&sortColumnAscDesc=' + appLogListData.filter.sortColumnAscDesc;
	location.href = restUri;
}; 
$(function() {
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy-mm-dd"
	});
	getAppLogList();
});


