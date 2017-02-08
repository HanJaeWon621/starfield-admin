
////////////////////////////////////////////////////////
// global variables

var couponData  = {
	couponDownList : null,
	coupon : {
		  cust_id : null,
	      uuid : null,
	      cp_dn_dt : null,
	      cp_div_cd_nm : null,
	      cp_iss_cd : null,
	      cp_kind_cd_nm : null,
	      cp_iss_type_cd_nm : null,
       	  cp_titl : null,
          cp_iss_strt_dt : null,
          cp_iss_end_dt : null,
          cp_act_strt_dt : null,
          cp_act_end_dt : null,  
          cp_sale_div_nm : null, 
          cp_sale_div_cd : null,
          cp_sum_sale_rt : null,
          cp_max_sale_amt : null,
          cp_ded_amt : null,
          cp_use_cond_amt : null, 
          cp_dtl_cont : null,
          cp_att_part_cont : null,
          cp_iss_mst_seq : null,
          cp_iss_sub_seq : null
	}, 
	couponFilter : {
		sh_strt_dt : null,
		sh_end_dt : null,
		sh_div_type : 0,
		sh_text_type : 1,
		sh_text : null,
		sortColumn: 'CP_DN_DT',
		sortColumnAscDesc: 'DESC'
	},
	paging : {},
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
	'movePage' : function(o, page) {
		if(page > 0 && page <= couponData.paging.total_page_cnt && page != couponData.paging.cur_page) {
			var offset = (page - 1) * couponData.paging.list_limit + 1; 
		
			getCouponDownList(offset, couponData.paging.list_limit);
		}
	},
	
	'couponDownDetail' : function(o,cp_iss_mst_seq,cp_seq,cp_iss_sub_seq,uuid) {  
		couponDownDetail(cp_iss_mst_seq,cp_seq,cp_iss_sub_seq,uuid);
	}, 
	'reqCoupon' : function(){  
		location.href='/admin/regCoupon';
	},
	'downExcel' : function(){
		getCouponDownListExcel();
	},
	'shCoupon' : function(){
		getCouponDownList(0, 10);
	},
	'getDownCoupon' : function(o, cp_seq, cust_id){
		getDownCoupon(cp_seq, cust_id);
	},
	'shCouponInit' : function(o){
		couponData.couponFilter.sh_strt_dt = '';
		couponData.couponFilter.sh_end_dt = '';
		couponData.couponFilter.sh_div_type = '0';
		couponData.couponFilter.sh_text_type = '1'; 
		couponData.couponFilter.sh_text = '';
		couponData.couponFilter.sortColumn = 'cp_use_dt';
		couponData.couponFilter.sortColumnAscDesc = 'DESC';
		rtvCoupon.set(couponData);
		
		getCouponDownList(0, 10);
	},
	'listSort' : function(o){  
		//alert($(o.node).data("order-key"));
		getSortFilter(o, couponData.couponFilter,'sortColumn','sortColumnAscDesc');
		getCouponDownList(1,10);
	}
	 
});


var getCouponDownList = function(offset, limit) {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	couponData.couponFilter.sh_strt_dt = search_strt_dt;
	couponData.couponFilter.sh_end_dt = search_end_dt;
	
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getDownCoupons';
	restUri += '?';
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_strt_dt=' + couponData.couponFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + couponData.couponFilter.sh_end_dt.split('-').join(''); 
	restUri += '&sh_div_type=' + couponData.couponFilter.sh_div_type;
	restUri += '&sh_text_type=' + couponData.couponFilter.sh_text_type;
	restUri += '&sh_text=' + couponData.couponFilter.sh_text;
	restUri += '&sortColumn=' + couponData.couponFilter.sortColumn;
	restUri += '&sortColumnAscDesc=' + couponData.couponFilter.sortColumnAscDesc;
	Common.REST.get(restUri, {}, function(data) {
		couponData.couponDownList = data.list; 
		couponData.paging = data.paging; 
  
		couponData.paging.pages = [];
		 
		for(var  i = couponData.paging.page_start; i <= couponData.paging.page_end; ++i) {
			couponData.paging.pages.push(i);
        }
		
		rtvCoupon.set(couponData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

 
var getCouponDownListExcel = function(offset, limit) {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	couponData.couponFilter.sh_strt_dt = search_strt_dt;
	couponData.couponFilter.sh_end_dt = search_end_dt;
	
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/01/getDownCoupons/excel';
	restUri += '?';
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_strt_dt=' + couponData.couponFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + couponData.couponFilter.sh_end_dt.split('-').join(''); 
	restUri += '&sh_div_type=' + couponData.couponFilter.sh_div_type;
	restUri += '&sh_text_type=' + couponData.couponFilter.sh_text_type;
	restUri += '&sh_text=' + couponData.couponFilter.sh_text;
	restUri += '&sortColumn=' + couponData.couponFilter.sortColumn;
	restUri += '&sortColumnAscDesc=' + couponData.couponFilter.sortColumnAscDesc;
	location.href = restUri;
}; 

var getDownCoupon = function(cp_seq, cust_id){
	location.href='/01/getDownCoupon/' + cp_seq + '/' + cust_id;
	
	
	/*var restUri = window.location.origin + '/rest/01/getCouponDownDetail';
	couponData.filter.cp_seq = cp_seq;
	couponData.filter.cust_id = cust_id;
		
	Common.REST.post(restUri, couponData.filter, function(data) {
		
		couponData.coupon = data; 
		 
		rtvCpDownDetail.set(couponData); 
		
	}, function(data) { 
		console.log('fail data is ', data);
		
	}); */
} 



$(function() {
	
	getCouponDownList(1,10);
	//달력   
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy-mm-dd"
	});
});


