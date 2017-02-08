
////////////////////////////////////////////////////////
// global variables
	
var appPushData  = {
	wcPushMsgList : null,
	paging : {},
	filter : {
		search_dt_type : 0,
		search_strt_dt : null,
		search_end_dt : null,
		push_search_strt_dt : null,
		push_search_end_dt : null,
		sortColumn: 'no',
		sortColumnAscDesc: 'DESC'
	}
	
};


var rtvappPush = new Ractive({
	el : '#wcPushMsg-list',
	template : '#tmpl-wcPushMsg-list',
	data : appPushData
});
 

rtvappPush.on({
	'movePage' : function(o, page) {
		if(page > 0 && page <= appPushData.paging.total_page_cnt && page != appPushData.paging.cur_page) {
			var offset = (page - 1) * appPushData.paging.list_limit + 1;
			getWcPushMsgList(offset, appPushData.paging.list_limit);
		}
	},
	'modifyWcPushMsg' : function(o, wel_msg_push_seq) {  
		location.href='/01/welcome/push/' + wel_msg_push_seq ;
	}, 
	'reqWcPushMsg' : function(o, wel_msg_push_seq) {  
		location.href='/reqWelcomePush';
	}, 
	'getWcPushMsgList' : function() {  
		getWcPushMsgList(1, 10);
	}, 
	'downExcel' : function() {  
		getWcPushMsgListExcel();
	}, 
	'getWcPushMsgListInit' : function() {  
		$("#search_dt_type").val('1');    
		$("#search_end_dt").val(''); 
		$("#search_strt_dt").val('');
		$("#push_search_end_dt").val(''); 
		$("#push_search_strt_dt").val('');
		appPushData.filter.sortColumn = 'no';
		appPushData.filter.sortColumnAscDesc = 'DESC';
		
		rtvappPush.set(appPushData);
		getWcPushMsgList();
	}, 
	'listSort' : function(o) {
		getSortFilter(o, appPushData.filter,'sortColumn','sortColumnAscDesc');		
		getWcPushMsgList(1, 10);
	}
});

 
var getWcPushMsgList = function(offset, limit) {
	var search_end_dt = $("#search_end_dt").val() == '' ? today() : $("#search_end_dt").val();
	var search_strt_dt = $("#search_strt_dt").val() == '' ? monthAgo(today()) : $("#search_strt_dt").val();
	var push_search_end_dt = $("#push_search_end_dt").val() == '' ? today() : $("#push_search_end_dt").val();
	var push_search_strt_dt = $("#push_search_strt_dt").val() == '' ? monthAgo(today()) : $("#push_search_strt_dt").val();
	var offset = offset || 1;  
	var limit = limit || 10; 
	var restUri = window.location.origin + '/rest/01/getWcPushMsgs';
	restUri += '?offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&search_dt_type=' + $("#search_dt_type").val();
	restUri += '&search_strt_dt=' + search_strt_dt.split('-').join("");
	restUri += '&search_end_dt=' + search_end_dt.split('-').join("");
	restUri += '&push_search_strt_dt=' + push_search_strt_dt.split('-').join("");
	restUri += '&push_search_end_dt=' + push_search_end_dt.split('-').join("");
	restUri += '&sortColumn=' + appPushData.filter.sortColumn;
	restUri += '&sortColumnAscDesc=' + appPushData.filter.sortColumnAscDesc;
	Common.REST.get(restUri, {}, function(data) {
		appPushData.wcPushMsgList = data.list; 
		appPushData.paging = data.paging; 
  
		appPushData.paging.pages = [];
		  
		for(var  i = appPushData.paging.page_start; i <= appPushData.paging.page_end; ++i) {
			appPushData.paging.pages.push(i);
        }
		//alert(data.empList[0].empno);
		$("#search_strt_dt").val(search_strt_dt);
		$("#search_end_dt").val(search_end_dt); 
		$("#push_search_strt_dt").val(push_search_strt_dt);
		$("#push_search_end_dt").val(push_search_end_dt); 
		
		rtvappPush.set(appPushData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getWcPushMsgListExcel = function(offset, limit) {
	var search_end_dt = $("#search_end_dt").val() == '' ? today() : $("#search_end_dt").val();
	var search_strt_dt = $("#search_strt_dt").val() == '' ? monthAgo(today()) : $("#search_strt_dt").val();
	var push_search_end_dt = $("#push_search_end_dt").val() == '' ? today() : $("#push_search_end_dt").val();
	var push_search_strt_dt = $("#push_search_strt_dt").val() == '' ? monthAgo(today()) : $("#push_search_strt_dt").val();
	var offset = offset || 1;  
	var limit = limit || 10; 
	var restUri = window.location.origin + '/01/getWcPushMsgs/excel';
	restUri += '?offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&search_dt_type=' + $("#search_dt_type").val();
	restUri += '&search_strt_dt=' + search_strt_dt.split('-').join("");
	restUri += '&search_end_dt=' + search_end_dt.split('-').join("");
	restUri += '&push_search_strt_dt=' + push_search_strt_dt.split('-').join("");
	restUri += '&push_search_end_dt=' + push_search_end_dt.split('-').join("");
	restUri += '&sortColumn=' + appPushData.filter.sortColumn;
	restUri += '&sortColumnAscDesc=' + appPushData.filter.sortColumnAscDesc;
	location.href = restUri; 
};


$(function() {   
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy-mm-dd"
	});
	getWcPushMsgList(1, 10);
});


