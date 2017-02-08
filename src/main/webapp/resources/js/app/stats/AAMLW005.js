
////////////////////////////////////////////////////////
// global variables

var statsData  = {
	statsList : null,
	paging : {},
	filter : {
		sh_titl : ''
	}
};


var rtvStats = new Ractive({
	el : '#stats-list',
	template : '#tmpl-stats-list',
	data : statsData
});
 
rtvStats.on({
	'movePage' : function(o, page) {
		if(page > 0 && page <= statsData.paging.total_page_cnt && page != statsData.paging.cur_page) {
		var offset = (page - 1) * statsData.paging.list_limit + 1; 
			campaingStatsList(offset, statsData.paging.list_limit);
		}
	},
	'search' : function(o) {
		campaingStatsList();
	}
});

var campaingStatsList = function(offset, limit){
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	statsData.filter.sh_strt_dt = search_strt_dt;
	statsData.filter.sh_end_dt = search_end_dt;
	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getCampaignMembStatsList';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_strt_dt=' + statsData.filter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + statsData.filter.sh_end_dt.split('-').join('');
	restUri += '&sh_titl=' + statsData.filter.sh_titl;
	 

	
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

$(function() {
	campaingStatsList(1, 10);
	//달력 
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy-mm-dd"
	});
});


