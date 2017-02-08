
////////////////////////////////////////////////////////
// global variables

var couponData  = {
	couponUseList : null,
	coupon : {
		  cp_seq : null,
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
          cp_iss_sub_seq : null, 
          cp_use_sts_cd : null,
          cp_use_dt : null
	}, 
	couponFilter : { 
		sh_strt_dt : null,
		sh_end_dt : null,
		sh_text_type : 1,
		sh_text : null,
		sortColumn: 'cp_prc_dt',
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
			getUseCoupons(offset, couponData.paging.list_limit);
		}
	},
	'getUseCoupon' : function(o,cp_seq,cust_id) {
		console.log(cp_seq);
		return;
		getUseCoupon(cp_seq,cust_id);
	},
	'downExcel' : function(){
		getUseCouponsExcel();
	}
	, 
	'reqCoupon' : function(){  
		location.href='/admin/regCoupon';
	},
	'shCoupon' : function(){
		//alert("111");
		getUseCoupons(0, 10);
	},
	'shCouponInit' : function(){
		couponData.couponFilter.sh_strt_dt = '';
		couponData.couponFilter.sh_end_dt = '';
		couponData.couponFilter.sh_text_type = '1'; 
		couponData.couponFilter.sh_text = '';
		couponData.couponFilter.sortColumn = 'cp_use_dt';
		couponData.couponFilter.sortColumnAscDesc = 'DESC';
		rtvCoupon.set(couponData);
		
		getUseCoupons(0, 10);
	},
	'listSort' : function(o){  
		//alert($(o.node).data("order-key"));
		getSortFilter(o, couponData.couponFilter,'sortColumn','sortColumnAscDesc');
		getUseCoupons(1,10);
	},
	'cnclCpUsePreAppr' : function(){  
		//alert(sortType);
		cnclCpUsePreAppr();
	},
	'selList' : function(o,log_seq){  
		//alert(log_seq);
		$("#log_seq").val(log_seq);
		//uploadExcel();
	}
});

//가승인 취소
var cnclCpUsePreAppr = function(){
	var log_seq = $("#log_seq").val();
	//alert(cp_seq)
	if(log_seq==''){
		alert("가승인 취소할 목록을 선택하세요.");
		return;
	}
	if(!confirm("가승인 취소를 하시겠습니까?")){
		alert("aaa");
		return false;
	}else{
		alert("bbb");
		var restUri = window.location.origin + '/rest/01/cnclCpUsePreAppr/'+log_seq;
		Common.REST.get(restUri, {}, function(data) {
			alert("가승인 취소가 성공했습니다.");
			//shCoupon();
			getUseCoupons(0, 10);
			//location.href='/rest/01/getCoupons';
		}, function(data) {  
			console.log('fail data is ', data);
			
		});
	}
}
var getUseCoupons = function(offset, limit) {
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	couponData.couponFilter.sh_strt_dt = search_strt_dt;
	couponData.couponFilter.sh_end_dt = search_end_dt;
	
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getUseWaitCoupons';
	restUri += '?';
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit; 
	restUri += '&sh_strt_dt=' + couponData.couponFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + couponData.couponFilter.sh_end_dt.split('-').join(''); 
	restUri += '&sh_text_type=' + couponData.couponFilter.sh_text_type;
	restUri += '&sh_text=' + couponData.couponFilter.sh_text;
	restUri += '&sortColumn=' + couponData.couponFilter.sortColumn;
	restUri += '&sortColumnAscDesc=' + couponData.couponFilter.sortColumnAscDesc;
	Common.REST.get(restUri, {}, function(data) {
		couponData.couponUseList = data.list; 
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

var getUseCouponsExcel = function(offset, limit) {
	
	var search_end_dt = $("#sh_end_dt").val() == '' ? today() : $("#sh_end_dt").val();
	var search_strt_dt = $("#sh_strt_dt").val() == '' ? monthAgo(today()) : $("#sh_strt_dt").val();
	couponData.couponFilter.sh_strt_dt = search_strt_dt;
	couponData.couponFilter.sh_end_dt = search_end_dt;
	
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/01/getUseWaitCoupons/excel';
	restUri += '?';
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_strt_dt=' + couponData.couponFilter.sh_strt_dt.split('-').join('');
	restUri += '&sh_end_dt=' + couponData.couponFilter.sh_end_dt.split('-').join(''); 
	restUri += '&sh_text_type=' + couponData.couponFilter.sh_text_type;
	restUri += '&sh_text=' + couponData.couponFilter.sh_text;
	restUri += '&sortColumn=' + couponData.couponFilter.sortColumn;
	restUri += '&sortColumnAscDesc=' + couponData.couponFilter.sortColumnAscDesc;
	location.href = restUri; 
};

var couponDownDetail = function(cp_iss_mst_seq,cp_seq,cp_iss_sub_seq,uuid){
	var restUri = window.location.origin + '/rest/bcnCd/getCouponDownDetail/'
				+ cp_iss_mst_seq +'/'+ cp_seq +'/'+ cp_iss_sub_seq +'/' + uuid;
	Common.REST.get(restUri, {}, function(data) {
		
		couponData.coupon = data; 
		 
		rtvCpDownDetail.set(couponData); 
	//	$("#dialog-detail").dialog("open");
		
	}, function(data) { 
		console.log('fail data is ', data);
		
	}); 
} 


var getUseCoupon = function(cp_seq,cust_id){
	console.log(cp_seq);
	return;
	location.href='/admin/getUseCoupon/'+cp_seq+'/'+cust_id;
	//alert(+ data.cp_iss_mst_seq +'/'+ data.cp_seq +'/'+ data.cp_iss_sub_seq +'/' + data.uuid);
	/*var restUri = window.location.origin + '/rest/bcnCd/updateCouponUseSts'
		restUri += '?';
		restUri += 'cp_iss_mst_seq=' + data.cp_iss_mst_seq; 
		restUri += '&cp_seq=' + data.cp_seq;
		restUri += '&cp_iss_sub_seq=' + data.cp_iss_sub_seq;
		restUri += '&uuid=' + data.uuid;
		restUri += '&cp_use_sts_cd=' + data.cp_use_sts_cd;
		 
	Common.REST.post(restUri, {}, function(data) { 
		alert('수정완료!!');  
		
	}, function(data) {
		console.log('fail data is ', data);
	});*/
	
}

//팝업
/*var rtvCpDownDetail = new Ractive({
	el : '#dialog-detail',
	template : '#tmpl-cpDown-detail',
	data : couponData
});


rtvCpDownDetail.on({
	'updateCouponUseSts' : function(o){
		updateCouponUseSts(couponData.coupon);   
	}
});
*/

$(function() {
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy-mm-dd"
	});
	getUseCoupons(1, 10);
});


