
////////////////////////////////////////////////////////
// global variables

var couponData  = {
	coupon : {
		cp_dn_dt : null,
		cp_div_cd_nm : null,
		cp_iss_cd : null,
		cp_iss_bcd_id : null,
		cp_kind_cd_nm : null,
		cp_iss_type_cd_nm : null,
		cp_titl : null,
		use_tnt_nm : null,
		cp_iss_strt_dt : null,
		cp_iss_end_dt : null,
		cp_act_strt_dt : null,
		cp_act_end_dt : null,
		cp_sale_div_cd : null,
		cp_use_cond_amt : null,
		cp_dtl_cont : null,
		cp_att_part_cont : null,
		uuid : null,
		cust_id : null 
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
	}
});


var getDownCoupon = function(cp_seq, cust_id){
	var restUri ='/rest/01/getDownCoupon';
	couponData.filter.cp_seq = cp_seq;
	couponData.filter.cust_id = cust_id; 
	Common.REST.post(window.location.origin + restUri, couponData.filter, function(data) {

		couponData.coupon = data;  
		rtvCoupon.set(couponData); 
		 
	}, function(data) { 
		console.log('fail data is ', data);
		
	}); 
	
} 

 
var bntClose  = function(){
	location.href='/01/downCoupons';
}



$(function() {
	if(getUriArrVal()[3] && getUriArrVal()[4]){ 
		getDownCoupon(getUriArrVal()[3], getUriArrVal()[4]);
	}
});


