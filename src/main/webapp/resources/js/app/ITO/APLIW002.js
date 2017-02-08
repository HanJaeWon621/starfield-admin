
////////////////////////////////////////////////////////
// global variables

var Data  = {
	list : null,
	paging : {},
	filter : {
		date_type : '1',
		stat_date : null,
		end_date : null,
		sortColumn: 'rnum',
		sortColumnAscDesc: 'DESC',
		sh_div : '-1',
		sh_text_type : '-1',
		sh_text : ''
		}
	
};


var rtvBanner= new Ractive({
	el : '#list',
	template : '#tmpl-list',
	data : Data
});
 

rtvBanner.on({
	'pageMove' : function(o, page) {
		if(page > 0 && page <= Data.paging.total_page_cnt && page != Data.paging.cur_page) {
			var offset = (page - 1) * Data.paging.list_limit + 1; 
			getList(offset, Data.paging.list_limit);
		}
	},
	'getList' : function(){  
		getList(1,10);
	},
	'getListInit' : function(){  
		getListInit(1,10);
	},
	'listSort' : function(o,sortColumn){  
		//alert($(o.node).data("order-key"));
		getSortFilter(o, Data.filter,'sortColumn','sortColumnAscDesc');
		getList(1,10);
	},
	'del' : function(o,plid_mng_seq, phone_num_nf,num){
		Data.filter.plid_mng_seq = plid_mng_seq;
		Data.filter.phone_num_nf = phone_num_nf; 
		if(confirm('개인 위치 정보를 삭제하시겠습니까?')) {
			Common.REST.post('/rest/01/ITO/deletePersonLocInfoPrc', Data.filter, function(result) {				
				if(result.code == 0) {
					$("td[id^=delete_bt"+num+"]").text('삭제처리');
					var rdata = result.extra.split('//');
					$("td[id^=delete_date"+num+"]").text(rdata[0]);
					$("td[id^=delete_usr"+num+"]").text(rdata[1]);
				} else { 
					alert('개인 위치정보 삭제 실패');
				}
			}, function(data) {
				alert('개인 위치정보 삭제 실패');
			});
			
		}
	}
});

 
 
//sort_name : 'rnum',
//sort_order : 'desc',
var getList = function(offset, limit) {
	//var search_end_dt = $("#search_end_dt").val() == '' ? today() : $("#search_end_dt").val();
	//var search_strt_dt = $("#search_strt_dt").val() == '' ? monthAgo(today()) : $("#search_strt_dt").val();
	Data.filter.end_date = today();
	Data.filter.stat_date = monthAgo(today());
	var offset = offset || 1;  
	var limit = limit || 10; 
	var restUri = window.location.origin + '/rest/01/ITO/locInfoManagePrc';
	restUri += '?offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&date_type=' + Data.filter.date_type;
	restUri += '&end_date=' +  Data.filter.end_date.split('-').join('');
	restUri += '&stat_date=' + Data.filter.stat_date.split('-').join('');
	restUri += '&sort_name=' +  Data.filter.sortColumn;
	restUri += '&sort_order=' + Data.filter.sortColumnAscDesc;
	restUri += '&sh_div=' +     Data.filter.sh_div;
	restUri += '&sh_text_type=' + Data.filter.sh_text_type;
	restUri += '&sh_text=' + Data.filter.sh_text;
	Common.REST.get(restUri, {}, function(data) {
		Data.list = data.list; 
		Data.paging = data.paging; 
  
		Data.paging.pages = [];
		  
		for(var  i = Data.paging.page_start; i <= Data.paging.page_end; ++i) {
			Data.paging.pages.push(i);
        }
		
		rtvBanner.set(Data);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getListInit = function(offset, limit) {
	//var search_end_dt = $("#search_end_dt").val() == '' ? today() : $("#search_end_dt").val();
	//var search_strt_dt = $("#search_strt_dt").val() == '' ? monthAgo(today()) : $("#search_strt_dt").val();
	
	Data.filter.date_type="1";
	Data.filter.end_date = today();
	Data.filter.stat_date = monthAgo(today());
	Data.filter.sh_div="";
	Data.filter.sh_text_type="-1";
	Data.filter.sh_text="";
	Data.filter.date_type="1";
	Data.filter.sortColumn="rnum";
	Data.filter.sortColumnAscDesc="desc";
	var offset = offset || 1;  
	var limit = limit || 10; 
	var restUri = window.location.origin + '/rest/01/ITO/locInfoManagePrc';
	restUri += '?offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&date_type=' + Data.filter.date_type;
	restUri += '&end_date=' +  Data.filter.end_date.split('-').join('');
	restUri += '&stat_date=' + Data.filter.stat_date.split('-').join('');
	restUri += '&sort_name=' +  Data.filter.sortColumn;
	restUri += '&sort_order=' + Data.filter.sortColumnAscDesc;
	restUri += '&sh_div=' +     Data.filter.sh_div;
	restUri += '&sh_text_type=' + Data.filter.sh_text_type;
	restUri += '&sh_text=' + Data.filter.sh_text;
	Common.REST.get(restUri, {}, function(data) {
		Data.list = data.list; 
		Data.paging = data.paging;  
  
		Data.paging.pages = [];
		  
		for(var  i = Data.paging.page_start; i <= Data.paging.page_end; ++i) {
			Data.paging.pages.push(i);
        }
		
		rtvBanner.set(Data);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};


$(function() {
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy-mm-dd"
	});
	getList(1, 10);
	
});


