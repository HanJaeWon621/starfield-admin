
////////////////////////////////////////////////////////
// global variables

var allStatsData  = {
	allStatsList : null,
	paging : {},
	statsFilter : {
		sh_dt_type : 'day',
		sh_strt_dt : null,
		sh_end_dt : null,
		sortColumn: 'VISIT_DT',
		sortColumnAscDesc: 'DESC'	
	}
};


var rtvAllStats = new Ractive({
	el : '#all-stats-list',
	template : '#tmpl-all-stats-list',
	data : allStatsData
});
 
rtvAllStats.on({
	'movePage' : function(o, page) {
		if(page > 0 && page <= allStatsData.paging.total_page_cnt && page != allStatsData.paging.cur_page) {
			var offset = (page - 1) * allStatsData.paging.list_limit + 1; 
			getAllVisitorStatsList(offset, allStatsData.paging.list_limit);
		}
	},
	'search' : function(){  
		getAllVisitorStatsList(1,10);
	},
	'listSort' : function(o,sortColumn){  
		//alert(sortType);
		
		getSortFilter(o, allStatsData.statsFilter,'sortColumn','sortColumnAscDesc');
		getAllVisitorStatsList(1,10,'');
	}
	,
	'downExcel' : function(){  
		//alert(sortType);
		getAllVisitorStatsListExcel();
	}
});

var getAllVisitorStatsList = function(offset, limit,sortColumn) {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	allStatsData.statsFilter.sh_strt_dt = search_strt_dt;
	allStatsData.statsFilter.sh_end_dt = search_end_dt;
	 
	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getAllVisitorStats';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	//var allStatsData = allStatsData.statsFilter.sh_dt_type;
	//alert("1");
	//v_sortColumn = allStatsData.statsFilter.sortColumn;
	
	//var v_sortColumn =  sortColumn == undefined ? 'VISIT_DT' : sortColumn;
	//alert(v_sortColumn);
	restUri += '&sh_dt_type=' + allStatsData.statsFilter.sh_dt_type;
	restUri += '&sh_strt_dt=' + allStatsData.statsFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + allStatsData.statsFilter.sh_end_dt.split('-').join('');
	//restUri += '&sortColumn=' + v_sortColumn;
	//restUri += '&sortColumnAscDesc=' + allStatsData.statsFilter.sortColumnAscDesc;
	 
	restUri += '&sortColumn=' + allStatsData.statsFilter.sortColumn;
	restUri += '&sortColumnAscDesc=' + allStatsData.statsFilter.sortColumnAscDesc;
	

	
	Common.REST.get(restUri, {}, function(data) {
		allStatsData.allStatsList = data.list; 
		allStatsData.paging = data.paging; 
 
		allStatsData.paging.pages = [];
		 
		for(var  i = allStatsData.paging.page_start; i <= allStatsData.paging.page_end; ++i) {
			allStatsData.paging.pages.push(i);
        }
		//alert(data.empList[0].empno);
		rtvAllStats.set(allStatsData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getAllVisitorStatsListExcel = function(sortColumn) {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	allStatsData.statsFilter.sh_strt_dt = search_strt_dt;
	allStatsData.statsFilter.sh_end_dt = search_end_dt;
	 
	var restUri = window.location.origin + '/01/getAllVisitorStats/excel';
	restUri += '?'; 
	//var allStatsData = allStatsData.statsFilter.sh_dt_type;
	//alert("1");
	//v_sortColumn = allStatsData.statsFilter.sortColumn;
	
	var v_sortColumn =  sortColumn == undefined ? 'VISIT_DT' : sortColumn;
	//alert(v_sortColumn);
	restUri += '&sh_dt_type=' + allStatsData.statsFilter.sh_dt_type;
	restUri += '&sh_strt_dt=' + allStatsData.statsFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + allStatsData.statsFilter.sh_end_dt.split('-').join('');
	restUri += '&sortColumn=' + v_sortColumn;
	restUri += '&sortColumnAscDesc=' + allStatsData.statsFilter.sortColumnAscDesc;
	
	location.href = restUri;

	return;
	Common.REST.get(restUri, {}, function(data) {
		allStatsData.allStatsList = data.list; 
		allStatsData.paging = data.paging; 
 
		allStatsData.paging.pages = [];
		 
		for(var  i = allStatsData.paging.page_start; i <= allStatsData.paging.page_end; ++i) {
			allStatsData.paging.pages.push(i);
        }
		//alert(data.empList[0].empno);
		rtvAllStats.set(allStatsData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};
var settingDate = function(){
	var toDate = new Date();
	var year = toDate.getFullYear();
	var month = toDate.getMonth()+1;
	var date = toDate.getDate();
	allStatsData.statsFilter.sh_strt_dt = year+'-'+leadingZeros(month,2)+'-'+leadingZeros(date,2);
	
	var settingDate = new Date();
	settingDate.setMonth(settingDate.getMonth()-1); //한달 전
	year = settingDate.getFullYear();
	month = settingDate.getMonth()+1;
	date = settingDate.getDate(); 
	allStatsData.statsFilter.sh_end_dt = year+'-'+leadingZeros(month,2)+'-'+leadingZeros(date,2);
}
$(function() {
	settingDate();
	getAllVisitorStatsList(1, 10);
	//달력 
	datepicker();
});




