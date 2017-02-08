
////////////////////////////////////////////////////////
// global variables

var allStatsData  = {
	allStatsList : null,
	paging : {},
	statsFilter : {
		sh_strt_dt : '',
		sh_end_dt : '',
		sh_tnt_nm : '',
		sortColumn: 'TNT_NM',
		sortColumnAscDesc: 'DESC'	
	}
};


var rtvAllStats = new Ractive({
	el : '#stats-list',
	template : '#tmpl-stats-list',
	data : allStatsData
});
 
rtvAllStats.on({
	'movePage' : function(o, page) {
		if(page > 0 && page <= allStatsData.paging.total_page_cnt && page != allStatsData.paging.cur_page) {
			var offset = (page - 1) * allStatsData.paging.list_limit + 1; 
			getTntMbrVisitorStats(offset, allStatsData.paging.list_limit);
		}
	},
	'search' : function(){  
		getTntMbrVisitorStats(1,10);
	},
	'listSort' : function(o,sortColumn){  
		//alert(sortType);
		
		getSortFilter(o, allStatsData.statsFilter,'sortColumn','sortColumnAscDesc');
		getTntMbrVisitorStats(1,10,'');
	}
	,
	'downExcel' : function(){  
		//alert(sortType);
		getTntMbrVisitorStatsExcel();
	}
});

var getTntMbrVisitorStats = function(offset, limit,sortColumn) {
	//return;
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	
	allStatsData.statsFilter.sh_strt_dt = search_strt_dt;
	allStatsData.statsFilter.sh_end_dt = search_end_dt;
	//alert(allStatsData.statsFilter.sh_strt_dt);
	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getTntMbrVisitorStats';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	//alert("aaa>>"+search_strt_dt);
	//return;
	restUri += '&sh_strt_dt=' + allStatsData.statsFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + allStatsData.statsFilter.sh_end_dt.split('-').join('');
	restUri += '&sh_tnt_nm=' + allStatsData.statsFilter.sh_tnt_nm;
	//restUri += '&sortColumn=' + v_sortColumn;
	//restUri += '&sortColumnAscDesc=' + allStatsData.statsFilter.sortColumnAscDesc;
	 
	restUri += '&sortColumn=' + allStatsData.statsFilter.sortColumn;
	restUri += '&sortColumnAscDesc=' + allStatsData.statsFilter.sortColumnAscDesc;
	

	
	Common.REST.get(restUri, {}, function(data) {
		allStatsData.statsList = data.list; 
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

var getTntMbrVisitorStatsExcel = function(sortColumn) {
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	
	allStatsData.statsFilter.sh_strt_dt = search_strt_dt;
	allStatsData.statsFilter.sh_end_dt = search_end_dt;
	 
	var restUri = window.location.origin + '/01/getTntMbrVisitorStats/excel';
	restUri += '?'; 
	//var allStatsData = allStatsData.statsFilter.sh_dt_type;
	//alert("1");
	//v_sortColumn = allStatsData.statsFilter.sortColumn;
	
	var v_sortColumn =  sortColumn == undefined ? 'TNT_NM' : sortColumn;
	//alert(v_sortColumn);
	
	restUri += '&sh_strt_dt=' + allStatsData.statsFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + allStatsData.statsFilter.sh_end_dt.split('-').join('');
	restUri += '&sh_tnt_nm=' + allStatsData.statsFilter.sh_tnt_nm;
	restUri += '&sortColumn=' + v_sortColumn;
	restUri += '&sortColumnAscDesc=' + allStatsData.statsFilter.sortColumnAscDesc;
	
	location.href = restUri;

};
var settingDate = function(){
	var toDate = new Date();
	var year = toDate.getFullYear();
	var month = toDate.getMonth()+1;
	var date = toDate.getDate();
	
	var strt_dt = year+'-'+leadingZeros(month,2)+'-'+leadingZeros(date,2);
	
	
	allStatsData.statsFilter.sh_strt_dt = year+'-'+leadingZeros(month,2)+'-'+leadingZeros(date,2);
	
	var settingDate = new Date();
	settingDate.setMonth(settingDate.getMonth()-1); //한달 전
	year = settingDate.getFullYear();
	month = settingDate.getMonth()+1;
	date = settingDate.getDate(); 
	allStatsData.statsFilter.sh_end_dt = year+'-'+leadingZeros(month,2)+'-'+leadingZeros(date,2);
}
$(function() {
	
	//settingDate();
	//달력 
	datepicker();
	
	getTntMbrVisitorStats(1, 10);
	
});




