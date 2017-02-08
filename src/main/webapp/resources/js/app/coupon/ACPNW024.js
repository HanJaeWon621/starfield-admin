
////////////////////////////////////////////////////////
// global variables

var couponData  = {
	coupon : {
		cp_dn_dt : null,
		cp_use_dt : null,
		cp_div_cd_nm : null,
		cp_iss_cd : null,
		cp_kind_cd_nm : null, 
		cp_iss_type_cd_nm : null,
		cp_use_sts_cd_nm : null,
		cp_use_sts_cd : null,
		cp_titl : null,
		use_tnt_nm : null,
		cp_prc_dt : null, 
		reg_usr : null,
		cp_iss_strt_dt : null,
		cp_iss_end_dt : null,
		cp_act_strt_dt : null, 
		cp_act_end_dt : null,
		cp_sale_div_cd : null,
		cp_use_cond_amt : null,
		cp_dtl_cont : null,
		cp_att_part_cont : null,
		uuid : null,
		cust_id : null, 
		mod_dttm : null, 
		cp_seq : null, 
		mod_usr : null, 
		log_seq : null 
	}, 
	filter :{}
};
 

var rtvCoupon = new Ractive({
	el : '#coupon-reg',
	template : '#tmpl-coupon-reg',
	data : couponData
});
  
rtvCoupon.on({
	
	'bntClose' : function(o) { 
		bntClose();
	},
	'modifyCpUseProcess' : function(o) { 
		modifyCpUseProcess();
	},
	'modifyCpUseProcess2' : function(o) { 
		modifyCpUseProcess2();
	}
});


var getDownCoupon = function(cp_seq, cust_id){
	var restUri ='/rest/01/getUseCoupon';
	couponData.filter.cp_seq = cp_seq;
	couponData.filter.cust_id = cust_id;
	Common.REST.post(window.location.origin + restUri, couponData.filter, function(data) {
		couponData.coupon = data; 
		if(couponData.coupon.cp_use_sts_cd_nm == '사용완료'){
			$("#btnSave2").show();
		}else{
			$("#btnSave2").hide();
		}
		
		rtvCoupon.set(couponData); 
	}, function(data) { 
		console.log('fail data is ', data);
		
	}); 
} 


var modifyCpUseProcess = function(cp_seq, cust_id){
	var restUri ='/rest/01/modifyCpUseProcess';
	Common.REST.post(window.location.origin + restUri, couponData.coupon, function(data) {

		rtvCoupon.set(couponData); 
		alert('사용처리가 변경 되었습니다.');
		bntClose();
	}, function(data) { 
		console.log('fail data is ', data);
		
	}); 
} 

 
var bntClose  = function(){
	location.href='/01/useCoupons';
}

var modifyCpUseProcess2  = function(){
	var restUri ='/rest/01/modifyCpUseProcess2';
	Common.REST.post(window.location.origin + restUri, couponData.coupon, function(data) {

		//rtvCoupon.set(couponData); 
		alert('사용처리가 변경 되었습니다.');
		bntClose();
	}, function(data) { 
		console.log('fail data is ', data);
		
	});
}

$(function() {
	if(getUriArrVal()[3] && getUriArrVal()[4]){ 
		getDownCoupon(getUriArrVal()[3], getUriArrVal()[4]);
	}
});


