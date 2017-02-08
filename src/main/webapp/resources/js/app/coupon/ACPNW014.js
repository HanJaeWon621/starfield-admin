
////////////////////////////////////////////////////////
// global variables
var couponData  = {
	coupon : {
		cp_seq : null, //쿠폰순번
		img_seq : null, //쿠폰BI이미지순번
		bcn_cd : null, //지점코드
		yymm : null, //년월
		mst_seq : null, //마스터순번
		cp_div_cd : 2, //쿠폰구분코드 
		cp_kind_cd : 3, //쿠폰종류코드
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
	'deleteImg' : function(o) {
		$(o.node).parent().parent('div').attr('class','img-uploader before');
	},
	'regCpPosting' : function() {
		regCpPosting(couponData.coupon);
	},
	'regCpUnPosting' : function() {
		regCpUnPosting(couponData.coupon);
	}
});
 
var regCoupon = function(data){
	couponData.coupon.cp_iss_strt_dt   = $("#cp_iss_strt_dt").val().split('-').join("");
	couponData.coupon.cp_iss_end_dt    = $("#cp_iss_end_dt").val().split('-').join("");
	couponData.coupon.cp_act_strt_dt   = $("#cp_act_strt_dt").val().split('-').join("");
	couponData.coupon.cp_act_end_dt    = $("#cp_act_end_dt").val().split('-').join("");

	if(!validation(couponData.coupon)){
		return false;
	}
	
	Common.REST.post(window.location.origin + '/rest/BCN001/reqCoupon', data, function(data) {
		console.log('success data is ', data);
		alert('등록완료!!'); 
		location.href='/admin/modifyCoupon/ch/' + data.extra;
	}, function(data) {
		console.log('fail data is ', data);
	});
} 

//이미지 업로드 버튼 설정 
var initTitleImgUploadButton = function() {
	var $target = null;
	
	var beforStart = function(e, data, cb) {
		$target = $(e.target);
 		
   	var file_name = data.files[0].name;
      var extension = file_name.substring(file_name.lastIndexOf('.')+1).toLowerCase();

      var clear_extensions = 'jpeg/jpg/png';

      if(clear_extensions.search(extension) === -1) {
          alert('이미지는 jpg, png만 가능합니다.');
          return;
      }

      if(typeof cb === 'function') {
          cb();
      }
	};
	
	var done = function(r) {
		var $targetParent = $target.parents(".img-uploader");
		$targetParent.removeClass("before").addClass("after");
		couponData.coupon[$targetParent.attr("id")] = r.img_seq;
		$("img[id="+ $targetParent.attr("id")+"_uri" +"]").attr('src',r.img_uri);
	}; 
	 
	var params = {
			url : '/rest/01/file/images', //upload url
		 	beforeStart : beforStart, // called before start uploading
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	};
	
	$('.file-uploader-wrap > .basic-btn').each(function(){
		$(this).fileInputButton(params);
	});
}

var getCoupon = function(cp_seq){
	var restUri = window.location.origin + '/rest/getCoupon/' + cp_seq;
	Common.REST.get(restUri, {}, function(data) {
		
		couponData.coupon = data; 
		rtvCoupon.set(couponData); 
		
		if(data.cp_exp_yn  == 'N' ){
			$("#btnPostion").show(); 
			$("#btnSave").show(); 
		}else{
			$("#btnSave").hide();
			$("#btnPostion").hide();
			$("#btnUnPostion").show();  
			$('input').attr("disabled",true); 
		}
		
	}, function(data) {
		console.log('fail data is ', data);
	});
};

var regCpPosting = function(data) { 
	var restUri = window.location.origin + '/rest/01/nmCoupon/posting/' + data.cp_seq;
	Common.REST.get(restUri, {}, function(param) {
		location.href='/admin/modifyCoupon/ch/' + data.cp_seq;
	}, function(data) { 
		console.log('fail data is ', data);
	});
	
}

var regCpUnPosting = function(data){
	var restUri = window.location.origin + '/rest/01/nmCoupon/unposted/' + data.cp_seq;
	Common.REST.get(restUri, {}, function(param) {
		location.href='/admin/modifyCoupon/ch/' + data.cp_seq;
		  
	}, function(data) {
		console.log('fail data is ', data);
		
	});
}

var validation = function(data){
	if(data.cp_titl == null || data.cp_titl == '') {
		alert("타이틀을 입력 하세요.");
		//$("#cp_titl").focus();
		return false;
	}else if(data.cp_iss_strt_dt == null || data.cp_iss_strt_dt == '') {
		alert("노출 시작일을 입력 하세요.");
		//$("#cp_iss_strt_dt").focus();
		return false;
	}else if(data.cp_iss_end_dt == null || data.cp_iss_end_dt == '') {
		alert("노출 종료일을 입력 하세요.");
		//$("#cp_iss_end_dt").focus();
		return false;
	}else if(data.cp_iss_strt_dt > data.cp_iss_end_dt) {
		alert("노출일을 확인하세요");
		//$("#cp_iss_strt_dt").focus();
		return false;
	}else if(data.cp_act_strt_dt == null || data.cp_act_strt_dt == '') {
		alert("사용기간 시작일을 입력 하세요.");
		//$("#cp_act_strt_dt").focus();
		return false;
	}else if(data.cp_act_end_dt == null || data.cp_act_end_dt == '') {
		alert("사용기간 종료일을 입력 하세요.");
		//$("#cp_act_end_dt").focus();
		return false;
	}else if(data.cp_act_strt_dt > data.cp_act_end_dt) {
		alert("사용기간일을 확인하세요");
		//$("#cp_act_strt_dt").focus();
		return false;
	}else if(data.cp_iss_cnt == null || data.cp_iss_cnt == '') {
		alert("발급수량을 입력 하세요.");
		//$("#cp_titl").focus();
		return false;
	}
	
	return true;
}

$(function() {
	initTitleImgUploadButton();
	if(getUriArrVal()[4]){ 
		getCoupon(getUriArrVal()[4]);
	}
});
