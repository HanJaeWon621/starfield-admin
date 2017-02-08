
////////////////////////////////////////////////////////
// global variables

var locationUseListData  = {
	locationUseList : null, 
	filter :{
		sh_end_dt : '',
		sh_strt_dt : '',
		sh_text_type : '1',
		sh_text : '',
		sortColumn: 'f_reg_dttm',
		sortColumnAscDesc: 'DESC'
	}
};
 
 
var rtvLocation = new Ractive({
	el : '#location-mng',
	template : '#tmpl-location-mng',
	data : locationUseListData
});
  
rtvLocation.on({
	'pageMove' : function(o, page) {
		if(page > 0 && page <= locationUseListData.paging.total_page_cnt && page != locationUseListData.paging.cur_page) {
			var offset = (page - 1) * locationUseListData.paging.list_limit + 1; 
			locationUseList(offset, locationUseListData.paging.list_limit);
		}
	},
	'search' : function() { 
		locationUseList(1,10);
	},
	'search_init' : function() { 
		locationUseList(1,10,'init');
	},
	'listSort' : function(o,sortColumn){  
		//alert($(o.node).data("order-key"));
		getSortFilter(o, locationUseListData.filter,'sortColumn','sortColumnAscDesc');
		locationUseList(1,10);
		
	}
});  
 


var locationUseList = function(offset, limit, div_cd) {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	locationUseListData.filter.sh_end_dt = search_end_dt;
	locationUseListData.filter.sh_strt_dt = search_strt_dt;
	
	if(div_cd=='init'){
		locationUseListData.filter.sh_end_dt=today();
		locationUseListData.filter.sh_strt_dt=monthAgo(today());
		locationUseListData.filter.sh_text_type="1";
		locationUseListData.filter.sh_text="";
		locationUseListData.filter.sortColumn="f_reg_dttm";
		locationUseListData.filter.sortColumnAscDesc="desc";
	}
	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getLocationUseList';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit; 
	restUri += '&sh_strt_dt=' + locationUseListData.filter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + locationUseListData.filter.sh_end_dt.split('-').join(''); 
	restUri += '&sh_text_type=' + locationUseListData.filter.sh_text_type; 
	restUri += '&sh_text=' + locationUseListData.filter.sh_text; 
	restUri += '&sortColumn=' +  locationUseListData.filter.sortColumn;
	restUri += '&sortColumnAscDesc=' + locationUseListData.filter.sortColumnAscDesc;
	Common.REST.get(restUri, {}, function(data) { 
		locationUseListData.locationUseList = data.list; 
		locationUseListData.paging = data.paging; 
 
		locationUseListData.paging.pages = [];
		 
		for(var  i = locationUseListData.paging.page_start; i <= locationUseListData.paging.page_end; ++i) {
			locationUseListData.paging.pages.push(i);
        }
 
		rtvLocation.set(locationUseListData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
}; 

$(function() {
	locationUseList();
	
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy-mm-dd"
	});
});


