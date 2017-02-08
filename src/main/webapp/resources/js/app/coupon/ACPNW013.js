
////////////////////////////////////////////////////////
// global variables
var chTenant = 0;
var deletetenantNum = [];
var arrtenantNum = [];
var initTenant = [];
var couponData  = {
	tenantChkList : null,
	fileType : null,
	coupon : {
		cp_seq : null, //쿠폰순번
		img_seq : null, //쿠폰BI이미지순번
		dtl_img_seq : null,
		web_bg_img_seq : null,
		web_bg_clr : null,
		app_bg_img_seq : null,
		app_bg_clr : null,
		bcn_cd : null, //지점코드
		yymm : null, //년월
		mst_seq : null, //마스터순번
		cp_div_cd : 2, //쿠폰구분코드 
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
		selTenantList : null,
		oldTenantList : null,
		chkTenantList : null
	}, 
	tenantList : null,
	selTenantList2 : null,
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
		setWebBgColor(flag);
	},
	'setAppBgColor' : function(o,flag) {
		setAppBgColor(flag);
	},
	'setWebBg' : function(o,flag) {
		setWebBg(flag);
	},
	'setAppBg' : function(o,flag) {
		setAppBg(flag);
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

	Common.REST.post(window.location.origin + '/rest/bcnCd/modifyCoupon', data, function(data) {
		console.log('success data is ', data);
		//$("img[id=imgv]").attr('src','');
		alert('등록완료!!');
		
		//rtvCoupon.set(rtvCoupon);
		//resetEmpForm();
		
	}, function(data) {
		console.log('fail data is ', data);
	});
} 

//이미지 업로드
var initTitleImgUploadButton1 = function() {
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
			$("#webBiImg").attr("src",resultParam.img_uri); 
		}  
	}; 
  
	var params = {
			url : '/rest/01/file/images',
		 	beforeStart : beforStart, // called before start uploading
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	}; 
	
	$('#title-image-upload-button1').fileInputButton(params);
	
}


var initTitleImgUploadButton2 = function() {
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
			couponData.coupon.dtl_img_seq = resultParam.img_seq;
			$("#moblBiImg").attr("src",resultParam.img_uri); 
		}  
	}; 

	var params = {
			url : '/rest/01/file/images',
		 	beforeStart : beforStart, // called before start uploading
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	}; 
	
	$('#title-image-upload-button2').fileInputButton(params);
	
}


var initTitleImgUploadButton3 = function() {
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
			alert(resultParam.img_seq);
			couponData.coupon.web_bg_img_seq = resultParam.img_seq;
		}  
	}; 
	
	var params = {
			url : '/rest/01/file/images',
			beforeStart : beforStart, // called before start uploading
			done : done, // called with result json object when uploading is done 
			progress : function(){} // called with current progress (Experimental)
	}; 
	
	$('#title-image-upload-button3').fileInputButton(params);
	 
}


var initTitleImgUploadButton4 = function() {
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
			alert(resultParam.img_seq);
			couponData.coupon.app_bg_img_seq = resultParam.img_seq;
		}  
	}; 
	
	var params = {
			url : '/rest/01/file/images',
			beforeStart : beforStart, // called before start uploading
			done : done, // called with result json object when uploading is done 
			progress : function(){} // called with current progress (Experimental)
	}; 
	
	$('#title-image-upload-button4').fileInputButton(params);
	
}


var getCoupon = function(cp_seq){
	var restUri = window.location.origin + '/rest/getCoupon/' + cp_seq;
	Common.REST.get(restUri, {}, function(data) {
		couponData.coupon = data; 
		
		rtvCoupon.set(couponData); 
		rtvSelTenant.set(couponData);  
		var btnText = "";
		var idx = data.selTenantList.length - 1 ;
		if(data.selTenantList.length > 0){
			btnText = data.selTenantList[0].tnt_nm_ko;
			if(data.selTenantList.length > 1){ 
				btnText +=  " 외"+ idx +"개 테넌트 적용";
			} 
		}
		$("#openSeltenantBtn").val(btnText);
		
		
	}, function(data) {
		console.log('fail data is ', data);
	});
};

 
//////////팝업1/////////////
var rtvTenant = new Ractive({
	el : '#dialog-detail',
	template : '#tmpl-tennant-list',
	data : couponData
});


rtvTenant.on({
	'tennantChk' : function(o,tnt_seq, cate_nm_ko, tnt_nm_ko, room_num,
			busi_tnt_cd, img_main_bg_web_uri, img_main_bg_mobi_uri) { 
		tennantChk(tnt_seq, cate_nm_ko, tnt_nm_ko, room_num,
				busi_tnt_cd, img_main_bg_web_uri, img_main_bg_mobi_uri);
	},
	'tennantSave' : function() { 
		tennantSave();
	},
	'tennantClose' : function() {
		$( "#dialog-detail" ).dialog( "close" );
	},
	'movePage' : function(o, page) {
		var offset = (page - 1) * couponData.paging.list_limit + 1; 
		
		opentenant(offset, couponData.paging.list_limit);
	}
	
});


var opentenant =  function(offset, limit){
	var offset = offset || 1;
	var limit = limit || 20;
	var restUri = window.location.origin + '/rest/bcnCd/getTenants';
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
		rtvTenant.set(couponData);
		//alert(couponData.coupon.selTenantList.length);
		$("input[id^='tennant_']").prop('checked',false);
		for(var i = 0 ; i < couponData.coupon.selTenantList.length ; i ++){
			var idx = couponData.coupon.selTenantList[i].tnt_seq;
			$("input[id=tennant_"+idx+"]").prop('checked',true);  
		} 
		getCategorys(); 
		//파일
	}, function(data) {
		 
		console.log('fail data is ', data);
		
	}); 
	
	$("#dialog-detail").dialog("open");

};  


var getCategorys =  function(){
	
	var restUri = window.location.origin + '/rest/getCategorys';
	Common.REST.get(restUri, {}, function(data) {
		//couponData.tenantList = data.list;
		couponData.filter.categoryList = data.list;
		rtvTenant.set(couponData); 
	}, function(data) { 
		 
		console.log('fail data is ', data);
		
	}); 
	
}; 


var tennantChk = function(tnt_seq, cate_nm_ko, tnt_nm_ko, room_num,
		busi_tnt_cd, img_main_bg_web_uri, img_main_bg_mobi_uri){
	var arridx = "";
	var arrtennant = tnt_seq+"///"+cate_nm_ko+"///"+tnt_nm_ko+"///"+room_num
	+"///"+busi_tnt_cd+"///"+img_main_bg_web_uri+"///"+img_main_bg_mobi_uri;
	
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
		$("#webBiImg").attr('src',arrtenantNum[0].split('///')[5]);
		$("#moblBiImg").attr('src',arrtenantNum[0].split('///')[6]);
	}else{
		$("#webBiImg").attr('src','');
		$("#moblBiImg").attr('src','');
	}

	couponData.coupon.selTenantList = arr; 
	rtvSelTenant.set(couponData);
	chTenant = 1;
	 
	var idx = arrtenantNum.length-1;
	var btnText = ""; 
	if(arrtenantNum.length > 0){
		btnText = arrtenantNum[0].split('///')[2];
		if(idx > 0){ 
			btnText +=  " 외"+ idx +"개 테넌트 적용";
		} 
	}
	  	
	$("#openSeltenantBtn").val(btnText);
	$( "#dialog-detail" ).dialog( "close" );
};


var openSeltenant = function(){
	$("#dialog-detail2").dialog("open");
} 


var setWebBgColor = function(flag){
	if($(':radio[name="webBg"]:checked').val() == '1'){
		return false;
	}
	
	if(flag == '1'){
		$("#webNowColor").text('검정');
	}else if(flag == '2'){
		$("#webNowColor").text('빨강');
	}else{
		$("#webNowColor").text('노랑');
	}
	couponData.coupon.web_bg_clr = flag;
}; 


var setAppBgColor = function(flag){
	if($(':radio[name="appBg"]:checked').val() == '1'){
		return false;
	}
	
	if(flag == '1'){
		$("#appNowColor").text('검정');
	}else if(flag == '2'){
		$("#appNowColor").text('빨강');
	}else{
		$("#appNowColor").text('노랑');
	}
	couponData.coupon.app_bg_clr = flag;
}; 


var setWebBg = function(flag){
	if(flag == 'img'){
		$("#webNowColor").text("");
		$("#webBgFile").show();
		couponData.coupon.web_bg_clr = null;
	}else{ 
		
		$("#webBgFile").hide();
		couponData.coupon.web_bg_img_seq = null;
	}
}


var setAppBg = function(flag){
	if(flag == 'img'){
		$("#appNowColor").text("");
		$("#appBgFile").show();
		couponData.coupon.app_bg_clr = null;
	}else{ 
		$("#appBgFile").hide();
		couponData.coupon.app_bg_img_seq = null;
	}
}

///////////팝업2//////////////////
var rtvSelTenant = new Ractive({
	el : '#dialog-detail2',
	template : '#tmpl-seltenant-list2',
	data : couponData 
});


rtvSelTenant.on({
	'deletetenant' : function(o,num) {
		deletetenant(num);
	},
	'selTennantSave' : function(o) {
		selTennantSave();
	},
	'selTennantClose' : function(o) {
		selTennantClose();
	}
});


var deletetenant = function(num){
	//var arridx = oldtenantNum.indexOf(snum);
	//oldtenantNum.splice(num,1);
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

///////////////////////////////////////////



$(function() {
	initTitleImgUploadButton1();
	initTitleImgUploadButton2();
	initTitleImgUploadButton3();
	initTitleImgUploadButton4();
	dialog("dialog-detail");
	dialog("dialog-detail2"); 
	datepicker();
	getCoupon(getUriArrVal()[3]);
	
});
   
    

