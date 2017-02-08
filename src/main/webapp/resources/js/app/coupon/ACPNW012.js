
////////////////////////////////////////////////////////
// global variables
var arrtenantNum = [];
var couponData  = {
	tenantChkList : null,
	fileType : null,
	coupon : {
		cp_seq : null, //쿠폰순번
		img_seq : null, //쿠폰BI이미지순번
		dtl_img_seq : null,
		web_bg_img_seq : null,
		web_bg_clr : 1,
		app_bg_img_seq : null,
		app_bg_clr : 1,
		bcn_cd : null, //지점코드
		yymm : null, //년월
		mst_seq : null, //마스터순번
		cp_div_cd : 2, //쿠폰구분코드 
		cp_kind_cd : 2, //쿠폰종류코드
		cp_iss_type_cd : 1, //쿠폰발급타입코드
		cp_titl : null, //쿠폰타이틀
		cp_iss_strt_dt : null, //쿠폰발급시작일
		cp_iss_end_dt : null, //쿠폰발급종료일
		cp_act_strt_dt : null, //쿠폰유효시작일
		cp_act_end_dt : null, //쿠폰유효종료일
		cp_sale_div_cd : 2, //쿠폰소계할인구분코드
		cp_sum_sale_rt : null, //쿠폰소계할인비율
		cp_ded_amt : null, //쿠폰차감금액
		cp_iss_cnt : null, //쿠폰발급수량
		cp_use_cond_amt : null, //쿠폰사용조건금액 
		cp_dtl_cont : null, //쿠폰상세내용
		cp_att_part_cont : "• 모든 모바일 쿠폰은 푸드코트 키오스크에서 사용이 불가합니다. <br/>" +
				           "• 보유쿠폰은 유효기간 만료 90일 이후 보관함에서 자동 삭제됩니다.", //쿠폰유의사항내용
		sale_evt_cd : null, //영업행사코드
		reg_dttm : null, //등록일시
		mod_dttm : null, //수정일시
		reg_usr : null, //등록자
		cp_exp_yn : null,
		dft_img_yn : 'N',
		cp_max_sale_amt : null,
		selTenantList : null, 
	}, 
	tenantList : null,
	paging : {},
	filter: {
  		tnt_nm_ko : null,
 		cate_seq : null,
 		categoryList : null
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
	'imgBtnT' : function(o,type) { 
		couponData.fileType = type;
	},
	'opentenant' : function() {
		opentenant(0, 10);
	},
	'regCoupon' : function(o) {
		regCoupon(couponData.coupon);
	},
	'changeCpSumSaleMethDiv' : function(o,flag) {
		changeCpSumSaleMethDiv(flag);
	},
	'changeCpUsePlceSetCd' : function(o,flag) {
		changeCpUsePlceSetCd(flag);
	},
	'openSeltenant' : function(o) {
		openSeltenant();
	},
	'setWebBgColor' : function(o,flag) {
		setWebBgColor(o, flag);
	},
	'setAppBgColor' : function(o,flag) {
		setAppBgColor(o, flag);
	},
	'setWebBg' : function(o, flag) {
		setWebBg(flag); 
	},
	'setAppBg' : function(o, flag) {
		setAppBg(flag);
	},
	'tennantChk' : function(o,tnt_seq, cate_nm_ko, tnt_nm_ko, room_num,
								busi_tnt_cd, img_logo_uri, img_thmb_uri) { 
		tennantChk(tnt_seq, cate_nm_ko, tnt_nm_ko, room_num,
		busi_tnt_cd, img_logo_uri, img_thmb_uri);
	}, 
	'tennantSave' : function() { 
		tennantSave();
	},
	'deletetenant' : function(o,num) {
		deletetenant(num);
	},
	'selTennantSave' : function(o) {
		selTennantSave();
	},
	'selTennantClose' : function(o) {
		selTennantClose();
	},
	'deleteImg' : function(o) {
		$(o.node).parent().parent('div').attr('class','img-uploader before');
	},
	'tennantClose' : function() {
		$("div[name='tnt-reg-popup']").attr("class","popup-wrap");
	},
	'regCpPosting' : function() {
		regCpPosting(couponData.coupon);
	},
	'regCpUnPosting' : function() {
		regCpUnPosting(couponData.coupon);
	}
});


var changeCpSumSaleMethDiv = function(flag){
	if(flag){
		$("#cp_sum_sale_rt").attr("disabled",false);
		$("#cp_max_sale_amt").attr("disabled",false);
		$("#cp_ded_amt").attr("disabled",true);
		couponData.coupon.cp_ded_amt = '';
	}else{ 
		$("#cp_sum_sale_rt").attr("disabled",true);
		$("#cp_max_sale_amt").attr("disabled",true);
		$("#cp_ded_amt").attr("disabled",false);
		couponData.coupon.cp_sum_sale_rt = '';
		couponData.coupon.cp_max_sale_amt = '';
	} 
	rtvCoupon.set(couponData);
}


var changeCpUsePlceSetCd = function(flag){
	if(flag){
		$("#opentenantBtn").attr("disabled",true);
	}else{
		$("#opentenantBtn").attr("disabled",false);
	}
}


var regCoupon = function(data){
	couponData.coupon.cp_iss_strt_dt           = 	$("#cp_iss_strt_dt").val().split('-').join("");
	couponData.coupon.cp_iss_end_dt            = 	$("#cp_iss_end_dt").val().split('-').join("");
	couponData.coupon.cp_act_strt_dt           = 	$("#cp_act_strt_dt").val().split('-').join("");
	couponData.coupon.cp_act_end_dt            = 	$("#cp_act_end_dt").val().split('-').join("");
	
	if(!validation(couponData.coupon)){
		return false;
	}
	
	
	var url = "/rest/01/reqCoupon";
	if(getUriArrVal()[4]){ 
		url = "/rest/01/modifyCoupon";
	}
	 
	
	Common.REST.post(window.location.origin + url, data, function(data) {
		alert('등록완료!!');  
		location.href='/admin/modifyCoupon/nm/' + data.extra;
		
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

var opentenant =  function(offset, limit){
	if(getUriArrVal()[4]){ 
		if(couponData.coupon.cp_exp_yn  == 'Y') {
			return false;
		} 
	}
	
	var offset = offset || 1;
	var limit = limit || 20;
	var restUri = window.location.origin + '/rest/01/getTenants';
	restUri += '?'; 
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit;
	restUri += '&cate_seq=' + couponData.filter.cate_seq;
	restUri += '&tnt_nm_ko=' + couponData.filter.tnt_nm_ko;
	Common.REST.get(restUri, {}, function(data) {
		couponData.tenantList = data.list;
		couponData.paging = data.paging;
				
		couponData.paging.pages = [];
		
		for(var i = couponData.paging.page_start; i <= couponData.paging.page_end; ++i) {
			couponData.paging.pages.push(i);
        } 
		rtvCoupon.set(couponData);
		
		$("input[id^='tennant_']").prop('checked',false);

		if(couponData.coupon.selTenantList != null){  
			for(var i = 0 ; i < couponData.coupon.selTenantList.length ; i ++){
				var idx = couponData.coupon.selTenantList[i].tnt_seq;
				$("input[id=tennant_"+idx+"]").prop('checked',true);  
			}  
		}
		  
		getCategorys(); 
		$("div[name='tnt-reg-popup']").attr("class","place-on"); 
	}, function(data) {
		 
		console.log('fail data is ', data);
		
	}); 

};  
 

var getCategorys =  function(){
	
	var restUri = window.location.origin + '/rest/getCategorys';
	Common.REST.get(restUri, {}, function(data) {
		//couponData.tenantList = data.list;
		couponData.filter.categoryList = data.list;
		rtvCoupon.set(couponData); 
	}, function(data) {
		
		console.log('fail data is ', data);
	}); 
	
}; 


var tennantChk = function(tnt_seq, cate_nm_ko, tnt_nm_ko, room_num,
								busi_tnt_cd, img_logo_uri, img_thmb_uri){
	var arridx = "";
	var arrtennant = tnt_seq+"///"+cate_nm_ko+"///"+tnt_nm_ko+"///"+room_num
				   +"///"+busi_tnt_cd+"///"+img_logo_uri+"///"+img_thmb_uri;

	arrtenantNum = [];
		
	if(arrtenantNum.indexOf(arrtennant) != -1){
		arridx = arrtenantNum.indexOf(arrtennant);
		arrtenantNum.splice(arridx,1);
	}else{
		arrtenantNum.push(arrtennant);
	}
	
};
 
var tennantSave = function(){
	var arr = new Array();
	var idx = arrtenantNum.length-1;
	for(var i = 0 ; i < arrtenantNum.length ; i ++){
		arr.push({
		   'tnt_seq' : arrtenantNum[i].split('///')[0] ,
		   'cate_nm_ko' : arrtenantNum[i].split('///')[1],
		   'tnt_nm_ko' : arrtenantNum[i].split('///')[2],
		   'room_num' : arrtenantNum[i].split('///')[3],
		   'busi_tnt_cd' : arrtenantNum[i].split('///')[4],
		});
	}  
	
	if(arrtenantNum.length > 0){
		$("#img_seq_uri").attr('src',arrtenantNum[0].split('///')[5]);
		$("#dtl_img_seq_uri").attr('src',arrtenantNum[0].split('///')[6]);
		 
		$("#img_seq").attr("class","img-uploader after");
		$("#dtl_img_seq").attr("class","img-uploader after");
	}else{
		$("#img_seq_uri").attr('src','');
		$("#dtl_img_seq_uri").attr('src','');

		$("#img_seq").attr("class","img-uploader before");
		$("#dtl_img_seq").attr("class","img-uploader before");
	}
	 
	couponData.coupon.selTenantList = arr; 
	rtvCoupon.set(couponData);
	
	var btnText = ""; 
	if(arrtenantNum.length > 0){
		btnText = arrtenantNum[0].split('///')[2];
		if(idx > 0){ 
			btnText +=  " 외"+ idx +"개 테넌트 적용";
		} 
	}
	
	$("#openSeltenantBtn").val(btnText);	
	$("div[name='tnt-reg-popup']").attr("class","popup-wrap");
};

 
var openSeltenant = function(){
	$("div[name='salTnt-reg-popup']").attr("class","place-on");
};   


var setWebBgColor = function(o, flag){
	if($(':radio[name="webBg"]:checked').val() == '1' || couponData.coupon.cp_exp_yn  == 'Y' ){
		return false;
	} 
	 
	$(o.node).siblings().removeClass("selected");
	$(o.node).addClass("selected"); 
	 
	couponData.coupon.web_bg_clr = flag;
}; 

 
var setAppBgColor = function(o, flag){
	if($(':radio[name="appBg"]:checked').val() == '1' || couponData.coupon.cp_exp_yn  == 'Y' ){
		return false;
	}
	
	$(o.node).siblings().removeClass("selected");
	$(o.node).addClass("selected"); 
	
	couponData.coupon.app_bg_clr = flag;
}; 


var setWebBg = function(flag){
	if(flag == 'img'){
		$("span[id^='bgColor']").removeClass("selected"); 
		$("input[name='file']").eq(2).attr("disabled",false);
		couponData.coupon.web_bg_clr = null; 
	}else{ 
		$("#web_bg_img_seq").attr("class","img-uploader before");
		$("input[name='file']").eq(2).attr("disabled",true);
		couponData.coupon.web_bg_img_seq = null; 
	}
}


var setAppBg = function(flag){
	if(flag == 'img'){ 
		$("span[id^='bgAppColor']").removeClass("selected");
		$("input[name='file']").eq(3).attr("disabled",false);
		couponData.coupon.app_bg_clr = null;
	}else{ 
		$("input[name='file']").eq(3).attr("disabled",true);
		couponData.coupon.app_bg_img_seq = null;
	}
}


var deletetenant = function(num){
	$("li[id=selTenant_"+num+"]").hide();     
} 


var selTennantSave = function(){
	$("#dialog-detail2").dialog("close");
	
	$( "li[id^='selTenant_']" ).each(function( index ) {
		if($(this).css("display") == "none"){
			arrtenantNum.splice(index,1);
		}
	});
	
	$("li[id^='selTenant_']").show();
	tennantSave();
	
}


var selTennantClose = function(){
	$("#dialog-detail2").dialog("close"); 
	$("li[id^='selTenant_']").show();
} 


var getCoupon = function(cp_seq){
	var restUri = window.location.origin + '/rest/getCoupon/' + cp_seq;
	Common.REST.get(restUri, {}, function(data) {
		couponData.coupon = data; 
		
		rtvCoupon.set(couponData); 
		var btnText = ""; 
		var idx = data.selTenantList.length - 1 ;
		if(data.selTenantList.length > 0){
			btnText = data.selTenantList[0].tnt_nm_ko;
			if(data.selTenantList.length > 1){ 
				btnText +=  " 외"+ idx +"개 테넌트 적용";
			} 
		}
		$("#openSeltenantBtn").val(btnText);
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


var validation = function(data){ 
	if(data.cp_titl == null || data.cp_titl == '') {
		alert("타이틀을 입력 하세요.");
		return false;
	}else if(data.selTenantList == null) {
		alert("사용자 설정을 입력 하세요."); 
		return false; 
	}else if(data.cp_iss_strt_dt == null || data.cp_iss_strt_dt == '') {
		alert("발급 시작일을 입력 하세요.");
		return false;
	}else if(data.cp_iss_end_dt == null || data.cp_iss_end_dt == '') {
		alert("발급 종료일을 입력 하세요.");
		return false;
	}else if(data.cp_iss_strt_dt > data.cp_iss_end_dt) {
		alert("발급일을 확인하세요");
		return false;
	}else if(data.cp_act_strt_dt == null || data.cp_act_strt_dt == '') {
		alert("유효기간 시작일을 입력 하세요.");
		return false;
	}else if(data.cp_act_end_dt == null || data.cp_act_end_dt == '') {
		alert("유효기간 종료일을 입력 하세요.");
		return false;
	}else if(data.cp_act_strt_dt > data.cp_act_end_dt) {
		alert("유효기간일을 확인하세요");
		return false;
	}else if(data.cp_sale_div_cd == null || data.cp_sale_div_cd == '') {
		return false;
	}
	
	if(data.cp_sale_div_cd == '2'){
		if((data.cp_sum_sale_rt == null || data.cp_sum_sale_rt == '') 
				|| (data.cp_max_sale_amt == null || data.cp_max_sale_amt == '')) {
			alert("할일방식 설정을 입력하세요");
			return false;
			 
		}
	}
	 
	if(data.cp_sale_div_cd == '1'){
		if(data.cp_ded_amt == null || data.cp_ded_amt == '') {
			alert("할일방식 설정을 입력하세요");
			return false; 
		}
	}
	
	if(data.cp_iss_cnt == null || data.cp_iss_cnt == '') {
		alert("발급수량 설정을 입력하세요."); 
		return false;
	}
	
	return true;
}


var regCpPosting = function(data) { 
	var restUri = window.location.origin + '/rest/01/nmCoupon/posting/' + data.cp_seq;
	Common.REST.get(restUri, {}, function(param) {
		location.href='/admin/modifyCoupon/nm/' + data.cp_seq;
	}, function(data) { 
		console.log('fail data is ', data);
	});
	
}


var regCpUnPosting = function(data){
	var restUri = window.location.origin + '/rest/01/nmCoupon/unposted/' + data.cp_seq;
	Common.REST.get(restUri, {}, function(param) {
		location.href='/admin/modifyCoupon/nm/' + data.cp_seq;
		  
	}, function(data) {
		console.log('fail data is ', data);
		
	});
}

$(function() {
	initTitleImgUploadButton();
	$("input[name='file']").eq(2).attr("disabled",true);
	$("input[name='file']").eq(3).attr("disabled",true);
	 
	if(getUriArrVal()[4]){ 
		getCoupon(getUriArrVal()[4]);
	} 
	datepicker();
});
   


