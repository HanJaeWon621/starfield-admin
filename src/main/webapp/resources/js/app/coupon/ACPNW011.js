
////////////////////////////////////////////////////////
// global variables

var couponData  = {
	couponList : null,
	paging : {},
	couponFilter : {
		sh_dt_type : 0,
		sh_strt_dt : null,
		sh_end_dt : null,
		sh_div_type : '0',
		sh_kind_type : '0',
		sh_text_type : '1',
		sh_text : "",
		sortColumn: 'reg_dttm',
		sortColumnAscDesc: 'DESC'
	},
	getYYYYMMDD : function(timestamp, _type) {
		var date = new Date(timestamp);
	    var type = _type || ' ';

	    var arr = [];
	    arr.push(date.getFullYear() + '년');
	    arr.push(( (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1) ) + '월');
	    arr.push(( date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + '일');

	    return arr.join(type);
	}
	
};


var rtvCoupon = new Ractive({
	el : '#coupon-list',
	template : '#tmpl-coupon-list',
	data : couponData
});
 
rtvCoupon.on({
	'pageMove' : function(o, page) {
		if(page > 0 
				&& page <= couponData.paging.total_page_cnt
				&& page != couponData.paging.cur_page) {

			var offset = (page - 1) * couponData.paging.list_limit + 1; 
			getCouponList(offset, couponData.paging.list_limit);
		}
		
	}, 
	
	'modifyCoupon' : function(o, cp_seq, cp_kind_cd, cp_div_cd) {
		var view = '';
		if(cp_div_cd == 2){
			if(cp_kind_cd == 3){
				view = 'ec'
			}else{
				view = 'nm'
			}
		}else{
			view = 'mb'
		}
		location.href='/admin/modifyCoupon/' + view + '/' + cp_seq;
	}, 
	'reqCoupon' : function(){  
		location.href='/admin/regCoupon';
	},
	'shCoupon' : function(){
		shCoupon();
	},
	'shCouponInit' : function(){  
		getCouponListInit(1,10);
		$("#copy_cp_seq").val("");
	},
	'listSort' : function(o,sortColumn){  
		//alert($(o.node).data("order-key"));
		getSortFilter(o, couponData.couponFilter,'sortColumn','sortColumnAscDesc');
		getCouponList(1,10,sortColumn);
		$("#copy_cp_seq").val("");
		
	},
	'downExcel' : function(){  
		//alert(sortType);
		getCouponListExcel();
	},
	'uploadExcel' : function(){  
		//alert(sortType);
		uploadExcel();
	},
	'copyCoupon' : function(){  
		//alert(sortType);
		copyCoupon();
	},
	'selCoupon' : function(o,cp_seq){  
		//alert(cp_seq);
		$("#copy_cp_seq").val(cp_seq);
		//uploadExcel();
	}
});

var shCoupon = function(){
	var strt_dt = $("#sh_strt_dt").val().split('-').join('');
	var end_dt = $("#sh_end_dt").val().split('-').join('');
	if(strt_dt>end_dt){
		alert('조회 기간을 확인 하세요.');
		return false; 
	}

	getCouponList(1,10);
	$("#copy_cp_seq").val("");

}

//쿠폰 복사
var copyCoupon = function(){
	var cp_seq = $("#copy_cp_seq").val();
	//alert(cp_seq)
	if(cp_seq==''){
		alert("복사할 쿠폰을 선택하세요.");
		return;
	}
	if(!confirm("쿠폰 복사를 하시겠습니까?")){
		return false;
	}else{
		
		var restUri = window.location.origin + '/rest/01/copyCoupon/'+cp_seq;
		Common.REST.get(restUri, {}, function(data) {
			alert("쿠폭복사가 성공했습니다.")
			shCoupon();
			//location.href='/rest/01/getCoupons';
		}, function(data) {  
			console.log('fail data is ', data);
			
		});
	}
}

var uploadExcel = function(){
	
}

//초기화
var getCouponListInit = function(offset, limit,sortColumn) {
	//var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	//var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	/*
	sh_dt_type : 0,
	sh_strt_dt : null,
	sh_end_dt : null,
	sh_div_type : 0,
	sh_kind_type : 0,
	sh_text_type : 1,
	sh_text : "",
	sortColumn: 'reg_dttm',
	sortColumnAscDesc: 'DESC'
	*/

	couponData.couponFilter.sh_dt_type =0;
	couponData.couponFilter.sh_end_dt = today();
	couponData.couponFilter.sh_strt_dt = monthAgo(today());
	couponData.couponFilter.sh_div_type ='0';
	couponData.couponFilter.sh_kind_type ='0';
	couponData.couponFilter.sh_text ='';
	couponData.couponFilter.sh_text_type =1;
	couponData.couponFilter.sortColumn ='reg_dttm';
	couponData.couponFilter.sortColumnAscDesc ="DESC";
	//rtvCoupon.set(couponData); //제거 필요
	
	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getCoupons';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	
	restUri += '&sh_dt_type=' + couponData.couponFilter.sh_dt_type;
	restUri += '&sh_strt_dt=' + couponData.couponFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + couponData.couponFilter.sh_end_dt.split('-').join('');
	restUri += '&sh_div_type=' + couponData.couponFilter.sh_div_type;
	restUri += '&sh_kind_type=' + couponData.couponFilter.sh_kind_type;
	restUri += '&sh_text=' + couponData.couponFilter.sh_text;
	restUri += '&sh_text_type=' + couponData.couponFilter.sh_text_type;
	restUri += '&sortColumn=' + couponData.couponFilter.sortColumn;
	restUri += '&sortColumnAscDesc=' + couponData.couponFilter.sortColumnAscDesc;
	
	Common.REST.get(restUri, {}, function(data) {
		couponData.couponList = data.list; 
		couponData.paging = data.paging; 
 
		couponData.paging.pages = [];
		 
		for(var  i = couponData.paging.page_start; i <= couponData.paging.page_end; ++i) {
			couponData.paging.pages.push(i);
        }
		//alert(data.empList[0].empno);
		rtvCoupon.set(couponData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getCouponList = function(offset, limit,sortColumn) {


	//var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	//var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	var search_end_dt ="";
	var search_strt_dt ="";
	couponData.couponFilter.sh_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	couponData.couponFilter.sh_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();

	 
	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getCoupons';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	
	restUri += '&sh_dt_type=' + couponData.couponFilter.sh_dt_type;
	restUri += '&sh_strt_dt=' + couponData.couponFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + couponData.couponFilter.sh_end_dt.split('-').join(''); 
	restUri += '&sh_div_type=' + couponData.couponFilter.sh_div_type;
	restUri += '&sh_kind_type=' + couponData.couponFilter.sh_kind_type;
	restUri += '&sh_text=' + couponData.couponFilter.sh_text;
	restUri += '&sh_text_type=' + couponData.couponFilter.sh_text_type;
	restUri += '&sortColumn=' + couponData.couponFilter.sortColumn;
	restUri += '&sortColumnAscDesc=' + couponData.couponFilter.sortColumnAscDesc;
	
	Common.REST.get(restUri, {}, function(data) {
		couponData.couponList = data.list; 
		couponData.paging = data.paging; 
 
		couponData.paging.pages = [];
		 
		for(var  i = couponData.paging.page_start; i <= couponData.paging.page_end; ++i) {
			couponData.paging.pages.push(i);
        }
		//alert(data.empList[0].empno);
		rtvCoupon.set(couponData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getCouponListExcel = function(offset, limit,sortColumn) {

	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();

	couponData.couponFilter.sh_strt_dt = search_strt_dt;
	couponData.couponFilter.sh_end_dt = search_end_dt;

	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/01/getCoupons/excel';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	
	restUri += '&sh_dt_type=' + couponData.couponFilter.sh_dt_type;
	restUri += '&sh_strt_dt=' + couponData.couponFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + couponData.couponFilter.sh_end_dt.split('-').join('');

	restUri += '&sh_div_type=' + couponData.couponFilter.sh_div_type;
	restUri += '&sh_kind_type=' + couponData.couponFilter.sh_kind_type;
	restUri += '&sh_text=' + couponData.couponFilter.sh_text;
	restUri += '&sh_text_type=' + couponData.couponFilter.sh_text_type;
	restUri += '&sortColumn=' + couponData.couponFilter.sortColumn;
	restUri += '&sortColumnAscDesc=' + couponData.couponFilter.sortColumnAscDesc;
	
	location.href = restUri;
	
	
};

var settingDate = function(){
	var toDate = new Date();
	var year = toDate.getFullYear();
	var month = toDate.getMonth()+1;
	var date = toDate.getDate();
	var settingDate = new Date();
	monthPre = settingDate.getMonth();
	//alert(monthPre);
	if(monthPre==0){
		//alert(leadingZeros(month,2));
		month =12;
		//alert(leadingZeros(month,2));
		year = year -1;
	}else{
		//settingDate.setMonth(settingDate.getMonth()-1); //한달 전
	}
	//alert(year+'-'+leadingZeros(month,2)+'-'+leadingZeros(date,2));
	//couponData.couponFilter.strt_dt = year+'-'+month+'-'+leadingZeros(date,2);
	couponData.couponFilter.sh_strt_dt = year+'-'+leadingZeros(month,2)+'-'+leadingZeros(date,2);
	
	
	//alert(settingDate.getMonth());
	
	year = settingDate.getFullYear();
	
	
	
	month = settingDate.getMonth()+1;
	date = settingDate.getDate(); 
	couponData.couponFilter.sh_end_dt = year+'-'+leadingZeros(month,2)+'-'+leadingZeros(date,2);
}
 

var initExcelButton = function() {
	
	var beforStart = function(e, data, cb) {
   		
     	var file_name = data.files[0].name;
        var extension = file_name.substring(file_name.lastIndexOf('.')+1).toLowerCase();

        var clear_extensions = 'xls/xlsx';

        if(clear_extensions.search(extension) === -1) {
            alert('엑셀은 xls, xlsx만 가능합니다.');
            return;
        }

        if(typeof cb === 'function') {
            cb();
        }
	};
	
	var done = function(r) {
		
		console.log(r);
		
		if(r && r.code == 0) {
			alert('엑셀 업로드 완료');
		} else {
			alert('엑셀 업로드 실패');
		}
	};
	
	var params = { 
			url : '/rest/01/excel/coupon', //upload url
		 	beforeStart : beforStart, // called before start uploading
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	}; 
	
	$('#excelBtn').fileInputButton(params);
	
}

$(function() {
	initExcelButton();
	//settingDate();
	//return;
	getCouponList(1, 10);
	//달력   
	$("[name='datepicker2']" ).datepicker({
		dateFormat: "yy-mm-dd",
		onSelect: function() {
			rtvCoupon.set('filter.search_option_yn', true);
			rtvCoupon.updateModel();
		}
	});
});


