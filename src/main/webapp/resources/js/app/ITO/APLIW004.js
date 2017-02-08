
////////////////////////////////////////////////////////
// global variables

var locationReqViewData  = {
	locationReqView : {},
	locationReqViewList : null, 
	filter :{
		sh_end_dt : '',
		sh_strt_dt : '',
		sh_text_type : '1',
		sh_text : '',
		sortColumn: 'use_dttm',
		sortColumnAscDesc: 'DESC'
	}
};
 
 
var rtvLocation = new Ractive({
	el : '#location-mng',
	template : '#tmpl-location-mng',
	data : locationReqViewData
});
  
rtvLocation.on({
	'pageMove' : function(o, page) {
		if(page > 0 && page <= locationReqViewData.paging.total_page_cnt && page != locationReqViewData.paging.cur_page) {
			var offset = (page - 1) * locationReqViewData.paging.list_limit + 1; 
		
			getAppLogList(offset, locationReqViewData.paging.list_limit);
		}
	},
	'reqLocationView' : function(o) { 
		reqLocationView();
	},
	'excelDn' : function(o,seq, read_req_seq) { 
		excelDn(seq, read_req_seq);
	},
	'search' : function(o,seq, read_req_seq) { 
		getLocationReqViewList(1,10);
	},
	'search_init' : function(o,seq, read_req_seq) { 
		getLocationReqViewList(1,10,'init');
	},
	'locationReqViewListExcel' : function() { 
		locationReqViewListExcel();
	},
	'listSort' : function(o,sortColumn){  
		//alert($(o.node).data("order-key"));
		getSortFilter(o, locationReqViewData.filter,'sortColumn','sortColumnAscDesc');
		getLocationReqViewList(1,10);
		
	}
}); 



var getLocationReqViewList = function(offset, limit,div_cd) {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	locationReqViewData.filter.sh_end_dt = search_end_dt;
	locationReqViewData.filter.sh_strt_dt = search_strt_dt;
	if(div_cd=='init'){
		locationReqViewData.filter.sh_end_dt = today();
		locationReqViewData.filter.sh_strt_dt = monthAgo(today());
		locationReqViewData.filter.sh_text_type="1";
		locationReqViewData.filter.sh_text="";
		locationReqViewData.filter.sortColumn="use_dttm";
		locationReqViewData.filter.sortColumnAscDesc="desc";
	}
	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getLocationReqViewList';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit; 
	restUri += '&sh_strt_dt=' + locationReqViewData.filter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' +  locationReqViewData.filter.sh_end_dt.split('-').join(''); 
	restUri += '&sh_text_type=' + locationReqViewData.filter.sh_text_type; 
	restUri += '&sh_text=' + locationReqViewData.filter.sh_text;
	restUri += '&sortColumn=' + locationReqViewData.filter.sortColumn;
	restUri += '&sortColumnAscDesc=' + locationReqViewData.filter.sortColumnAscDesc;
	Common.REST.get(restUri, {}, function(data) { 
		locationReqViewData.locationReqViewList = data.list; 
		locationReqViewData.paging = data.paging; 
 
		locationReqViewData.paging.pages = [];
		 
		for(var  i = locationReqViewData.paging.page_start; i <= locationReqViewData.paging.page_end; ++i) {
			locationReqViewData.paging.pages.push(i);
        }
 
		rtvLocation.set(locationReqViewData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
}; 

var locationReqViewListExcel = function() {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	locationReqViewData.filter.sh_end_dt = search_end_dt;
	locationReqViewData.filter.sh_strt_dt = search_strt_dt;
	
	var restUri = window.location.origin + '/01/getLocationReqViewList/excel';
	restUri += '?';  
	restUri += 'sh_strt_dt=' + locationReqViewData.filter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + locationReqViewData.filter.sh_end_dt.split('-').join(''); 
	restUri += '&sh_text_type=' + locationReqViewData.filter.sh_text_type; 
	restUri += '&sh_text=' + locationReqViewData.filter.sh_text;
	restUri += '&sortColumn=' + locationReqViewData.filter.sortColumn;
	restUri += '&sortColumnAscDesc=' + locationReqViewData.filter.sortColumnAscDesc;
	location.href = restUri;
	
};


var excelDn = function(mem_seq,read_req_seq) {
	var restUri = window.location.origin + '/01/locationReqViewExcel/excel';
	restUri += '?';  
	restUri += '&mem_seq=' + mem_seq;
	location.href = restUri;
	//return;
	
	url = "/rest/01/regLocationDnYn/"+read_req_seq;
	Common.REST.post(window.location.origin + url, '', function(data) {
		
		$("td[id^=dn_yn"+read_req_seq+"]").text('다운완료'); 
		$("td[id^=use_dttm"+read_req_seq+"]").text(data.extra); 
		 
	}, function(data) {
		console.log('fail data is ', data);
	});
};

var reqLocationView = function(offset, limit) {
	location.href='/01/location/reqView';

}

$(function() {
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy-mm-dd"
	});
	getLocationReqViewList(1,10);
});


