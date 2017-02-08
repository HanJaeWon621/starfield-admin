
////////////////////////////////////////////////////////
// global variables

var bannerData  = {
	bannerList : null,
	paging : {},
	filter : {
		search_dt_type : '1',
		search_strt_dt : null,
		search_end_dt : null,
		sh_post_type : '0',
		sh_text_type : '1',
		sh_text : '',
		sortColumn: 'no',
		sortColumnAscDesc: 'DESC'
		}
	
};


var rtvBanner= new Ractive({
	el : '#banner-list',
	template : '#tmpl-banner-list',
	data : bannerData
});
 

rtvBanner.on({
	'modifyBanner' : function(o,bn_seq) {  
		modifyBanner(bn_seq);
	},
	'pageMove' : function(o, page) {
		if(page > 0 && page <= bannerData.paging.total_page_cnt && page != bannerData.paging.cur_page) {
			var offset = (page - 1) * bannerData.paging.list_limit + 1; 
			getBanners(offset, bannerData.paging.list_limit);
		}
	},
	'getBannerList' : function(){  
		getBanners(1,10);
	},
	'getBannerListInit' : function(){  
		getBannersInit(1,10);
	},
	'listSort' : function(o,sortColumn){  
		//alert($(o.node).data("order-key"));
		getSortFilter(o, bannerData.filter,'sortColumn','sortColumnAscDesc');
		getBanners(1,10);
	},
	'downExcel' : function(){  
		//alert(sortType);
		getBannersExcel();
	}
});

 
var modifyBanner = function(bn_seq){
	location.href='/01/modifyBanner/'+ bn_seq;
}
 

var getBanners = function(offset, limit) {
	//var search_end_dt = $("#search_end_dt").val() == '' ? today() : $("#search_end_dt").val();
	//var search_strt_dt = $("#search_strt_dt").val() == '' ? monthAgo(today()) : $("#search_strt_dt").val();
	bannerData.filter.search_end_dt = today();
	bannerData.filter.search_strt_dt = monthAgo(today());
	var offset = offset || 1;  
	var limit = limit || 10; 
	var restUri = window.location.origin + '/rest/01/getBanners';
	restUri += '?offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&search_dt_type=' + bannerData.filter.search_dt_type;
	restUri += '&search_end_dt=' + bannerData.filter.search_end_dt.split('-').join('');
	restUri += '&search_strt_dt=' + bannerData.filter.search_strt_dt.split('-').join('');
	restUri += '&sh_post_type=' + bannerData.filter.sh_post_type;
	restUri += '&sh_text_type=' + bannerData.filter.sh_text_type;
	restUri += '&sh_text=' + 	  bannerData.filter.sh_text;
	restUri += '&sortColumn=' + bannerData.filter.sortColumn;
	restUri += '&sortColumnAscDesc=' + bannerData.filter.sortColumnAscDesc;
	Common.REST.get(restUri, {}, function(data) {
		bannerData.bannerList = data.list; 
		bannerData.paging = data.paging; 
  
		bannerData.paging.pages = [];
		  
		for(var  i = bannerData.paging.page_start; i <= bannerData.paging.page_end; ++i) {
			bannerData.paging.pages.push(i);
        }
		
		rtvBanner.set(bannerData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getBannersInit = function(offset, limit) {
	//var search_end_dt = $("#search_end_dt").val() == '' ? today() : $("#search_end_dt").val();
	//var search_strt_dt = $("#search_strt_dt").val() == '' ? monthAgo(today()) : $("#search_strt_dt").val();
	
	bannerData.filter.search_dt_type=1;
	bannerData.filter.search_end_dt = today();
	bannerData.filter.search_strt_dt = monthAgo(today());
	
	bannerData.filter.sh_post_type = "0"
	bannerData.filter.sh_text_type ="1";
	bannerData.filter.sh_text ="";
	
	bannerData.filter.sortColumn = "no"
	bannerData.filter.sortColumnAscDesc ="DESC";
	
	
		
	var offset = offset || 1;  
	var limit = limit || 10; 
	var restUri = window.location.origin + '/rest/01/getBanners';
	restUri += '?offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&search_dt_type=' + bannerData.filter.search_dt_type;
	restUri += '&search_end_dt=' + bannerData.filter.search_end_dt.split('-').join('');
	restUri += '&search_strt_dt=' + bannerData.filter.search_strt_dt.split('-').join('');
	restUri += '&sh_post_type=' + bannerData.filter.sh_post_type;
	restUri += '&sh_text_type=' + bannerData.filter.sh_text_type;
	restUri += '&sh_text=' + 	  bannerData.filter.sh_text;
	restUri += '&sortColumn=' + bannerData.filter.sortColumn;
	restUri += '&sortColumnAscDesc=' + bannerData.filter.sortColumnAscDesc;
	rtvBanner.set(bannerData);
	Common.REST.get(restUri, {}, function(data) {
		bannerData.bannerList = data.list; 
		bannerData.paging = data.paging; 
  
		bannerData.paging.pages = [];
		  
		for(var  i = bannerData.paging.page_start; i <= bannerData.paging.page_end; ++i) {
			bannerData.paging.pages.push(i);
        }
		
		rtvBanner.set(bannerData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getBannersExcel = function(offset, limit) {
	var search_end_dt = $("#search_end_dt").val() == '' ? today() : $("#search_end_dt").val();
	var search_strt_dt = $("#search_strt_dt").val() == '' ? monthAgo(today()) : $("#search_strt_dt").val();
	bannerData.filter.search_end_dt = today();
	bannerData.filter.search_strt_dt = monthAgo(today());
	var offset = offset || 1;  
	var limit = limit || 10; 
	var restUri = window.location.origin + '/01/getBanners/excel';
	restUri += '?offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&search_dt_type=' + bannerData.filter.search_dt_type;
	restUri += '&search_end_dt=' + bannerData.filter.search_end_dt.split('-').join('');
	restUri += '&search_strt_dt=' + bannerData.filter.search_strt_dt.split('-').join('');
	restUri += '&sh_post_type=' + bannerData.filter.sh_post_type;
	restUri += '&sh_text_type=' + bannerData.filter.sh_text_type;
	restUri += '&sh_text=' + 	  bannerData.filter.sh_text;
	restUri += '&sortColumn=' + bannerData.filter.sortColumn;
	restUri += '&sortColumnAscDesc=' + bannerData.filter.sortColumnAscDesc;
	location.href = restUri;
};
$(function() {
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy-mm-dd"
	});
	getBanners(1, 10);
	
});


