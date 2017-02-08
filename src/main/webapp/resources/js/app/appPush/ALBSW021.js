
////////////////////////////////////////////////////////
// global variables

var appPushData  = {
	pushMsgList : null,
	paging : {},
	pushFilter : {
		sh_strt_dt : null,
		sh_end_dt : null,
		sh_memb_type : 0,
		sh_text_type : 1,
		sh_text : null,
		sortColumn: 'no',
		sortColumnAscDesc: 'DESC'
	}
};


var rtvappPush = new Ractive({
	el : '#pushMsg-list',
	template : '#tmpl-pushMsg-list',
	data : appPushData
});
 

rtvappPush.on({
	'pageMove' : function(o, page) {
		if(page > 0 && page <= appPushData.paging.total_page_cnt && page != appPushData.paging.cur_page) {
			var offset = (page - 1) * appPushData.paging.list_limit + 1; 
			getPushMsgList(offset, appPushData.paging.list_limit);
		}
		
	},
	'shPush' : function(o, page) {
		getPushMsgList(0, 10);
	},
	'modifyPushMsg' : function(o, scn_otb_cp_push_seq,scn_otb_div_cd) {  
		location.href='/01/'+getUriArrVal()[2]+'/push/'+scn_otb_div_cd +'/'+ scn_otb_cp_push_seq ;
	}, 
	'downExcel' : function() {  
		getPushMsgListExcel();
	}, 
	'listSort' : function(o) {
		getSortFilter(o, appPushData.pushFilter,'sortColumn','sortColumnAscDesc');		
		getPushMsgList(1, 10);
	}, 
	'shPushInit' : function(o) {
		appPushData.pushFilter.sh_strt_dt = monthAgo(today());
		appPushData.pushFilter.sh_end_dt = today();
		appPushData.pushFilter.sh_memb_type = '0';
		appPushData.pushFilter.sh_text_type = '1';
		appPushData.pushFilter.sh_text = '';
		appPushData.pushFilter.sortColumn = 'no';
		appPushData.pushFilter.sortColumnAscDesc = 'DESC';
		
		rtvappPush.set(appPushData);
		getPushMsgList();
	} 
}); 
 

var getPushMsgList = function(offset, limit) {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	appPushData.pushFilter.sh_strt_dt = search_strt_dt;
	appPushData.pushFilter.sh_end_dt = search_end_dt;
	
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getScenarios/'+getUriArrVal()[2];
	restUri += '?';
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_strt_dt=' + appPushData.pushFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + appPushData.pushFilter.sh_end_dt.split('-').join(''); 
	restUri += '&sh_memb_type=' + appPushData.pushFilter.sh_memb_type;
	restUri += '&sh_text_type=' + appPushData.pushFilter.sh_text_type;
	restUri += '&sh_text=' + appPushData.pushFilter.sh_text;
	restUri += '&sortColumn=' + appPushData.pushFilter.sortColumn;
	restUri += '&sortColumnAscDesc=' + appPushData.pushFilter.sortColumnAscDesc;

	Common.REST.get(restUri, {}, function(data) {
		appPushData.pushMsgList = data.list; 
		appPushData.paging = data.paging; 
 
		appPushData.paging.pages = [];
		  
		for(var  i = appPushData.paging.page_start; i <= appPushData.paging.page_end; ++i) {
			appPushData.paging.pages.push(i);
        }
		
		rtvappPush.set(appPushData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getPushMsgListExcel = function(offset, limit) {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	appPushData.pushFilter.sh_strt_dt = search_strt_dt;
	appPushData.pushFilter.sh_end_dt = search_end_dt;
	
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/01/getScenarios/'+getUriArrVal()[2]+'/excel';
	restUri += '?';
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_strt_dt=' + appPushData.pushFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + appPushData.pushFilter.sh_end_dt.split('-').join(''); 
	restUri += '&sh_memb_type=' + appPushData.pushFilter.sh_memb_type;
	restUri += '&sh_text_type=' + appPushData.pushFilter.sh_text_type;
	restUri += '&sh_text=' + appPushData.pushFilter.sh_text;
	restUri += '&sortColumn=' + appPushData.pushFilter.sortColumn;
	restUri += '&sortColumnAscDesc=' + appPushData.pushFilter.sortColumnAscDesc;

	location.href = restUri;
};


$(function() {
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy-mm-dd"
	});
	getPushMsgList(1, 10);
});

if(getUriArrVal()[2]=='outbound'){
	$("#pg_title").html("Push 관리시스템_아웃바운드푸시");
}else{
	$("#pg_title").html("Push 관리시스템_시나리오푸시");
}

