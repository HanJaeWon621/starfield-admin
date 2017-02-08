
////////////////////////////////////////////////////////
// global variables

var couponData  = {
	coupon : {
		cp_seq : null, //쿠폰순번
		img_seq : null, //쿠폰BI이미지순번
		bcn_cd : null, //지점코드
		yymm : null, //년월
		mst_seq : null, //마스터순번
		cp_div_cd : 1, //쿠폰구분코드 
		cp_kind_cd : 1, //쿠폰종류코드
		cp_iss_type_cd : 1, //쿠폰발급타입코드
		cp_titl : null, //쿠폰타이틀
		cp_iss_strt_dt : null, //쿠폰발급시작일
		cp_iss_end_dt : null, //쿠폰발급종료일
		cp_act_strt_dt : null, //쿠폰유효시작일
		cp_act_end_dt : null, //쿠폰유효종료일
		cp_sale_div_cd : 1, //쿠폰소계할인구분코드
		cp_sum_sale_rt : null, //쿠폰소계할인비율
		cp_ded_amt : null, //쿠폰차감금액
		cp_iss_cnt : null, //쿠폰발급수량
		cp_use_cond_amt : null, //쿠폰사용조건금액
		cp_dtl_cont : null, //쿠폰상세내용
		cp_att_part_cont : null, //쿠폰유의사항내용
		sale_evt_cd : null, //영업행사코드
		reg_dttm : null, //등록일시
		mod_dttm : null, //수정일시
		reg_usr : null, //등록자
		cp_exp_yn : null,
		cp_max_sale_amt : null,
	}
};


////////////////////////////////////////////////////////
// global functions

////////////////////////////////////////////////////////
//initialize

var rtvCoupon = new Ractive({
	el : '#coupon-reg',
	template : '#tmpl-coupon-reg',
	data : couponData
});


rtvCoupon.on({
	'regCoupon' : function(o) {
		regCoupon(couponData.coupon);
	},
	'updatePosting' : function(o) {
		updatePosting(couponData.coupon);
	},
});
 

var regCoupon = function(data){
	couponData.coupon.cp_iss_strt_dt   = $("#cp_iss_strt_dt").val().split('-').join("");
	couponData.coupon.cp_iss_end_dt    = $("#cp_iss_end_dt").val().split('-').join("");
	couponData.coupon.cp_act_strt_dt   = $("#cp_act_strt_dt").val().split('-').join("");
	couponData.coupon.cp_act_end_dt    = $("#cp_act_end_dt").val().split('-').join("");
	couponData.coupon.cp_div_cd		   = "2";
	couponData.coupon.cp_kind_cd       = "3";
	couponData.coupon.cp_iss_type_cd   = "1";
	Common.REST.post(window.location.origin + '/rest/BCN001/reqCoupon', data, function(data) {
		console.log('success data is ', data);
		//$("img[id=imgv]").attr('src','');
		alert('등록완료!!');
		
	}, function(data) {
		console.log('fail data is ', data);
	});
} 


var updatePosting = function(data){
	Common.REST.post(window.location.origin + '/rest/BCN001/issuNmCoupon/'+data.cp_seq, {} , function(data) {
		console.log('success data is ', data);
		//$("img[id=imgv]").attr('src','');
		if(data.code == 0){
			alert('게시완료!!');
		}
		 
	}, function(data) {
		console.log('fail data is ', data);
	});
}


//이미지 업로드
var initTitleImgUploadButton = function() {
	var file_name = "";
	var beforStart = function(e, data, cb) {
		//var $upload = $(e.find('input')[0]);
		//alert(data);
		//alert(data.files[0].parent().attr('id')); 
	 	file_name = data.files[0].name; //실제 파일명
	    var extension = file_name.substring(file_name.lastIndexOf('.')+1).toLowerCase();
	    var clear_extensions = 'jpg/png';
	
	    if(clear_extensions.search(extension) === -1) {
	        alert('이미지는 jpg, png만 가능합니다.');
	        return;
	    }
	
	    if(typeof cb === 'function') {
	        cb();
	    }
	};
	
	var done = function(resultParam) {
		console.log("resultParam : " + resultParam.code);
		console.log("resultParam " +
				": " + resultParam.desc);
		 
		if(resultParam) {  
			couponData.coupon.img_seq = resultParam.img_seq;
			$("#imgurl").attr("src",resultParam.img_uri); 
		}  
	}; 

	var params = {
			url : '/rest/01/file/images',
		 	beforeStart : beforStart, // called before start uploading
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	}; 
	
	$('#title-image-upload-button').fileInputButton(params);
	
}


var getCoupon = function(cp_seq){
	var restUri = window.location.origin + '/rest/getCoupon/' + cp_seq;
	Common.REST.get(restUri, {}, function(data) {
		
		couponData.coupon = data; 
		rtvCoupon.set(couponData); 
		
	}, function(data) {
		console.log('fail data is ', data);
	});
};


$(function() {
	initTitleImgUploadButton();
	getCoupon(getUriArrVal()[3]);
	datepicker();
	
});
