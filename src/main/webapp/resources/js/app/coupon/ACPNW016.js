
////////////////////////////////////////////////////////
// global variables
var arrtenantNum = [];
var arrDelTenantNum = [];

var couponData  = {
	tenantChkList : null,
	fileType : null,
	coupon : {
		cp_seq : null, //쿠폰순번
		img_seq : null, //쿠폰BI이미지순번
		dtl_img_seq : null,
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
		web_bg_img_seq : null,
		web_bg_clr : 1,
		app_bg_img_seq : null,
		app_bg_clr : 1,
		dft_img_yn : 'N',
		selTenantList : null,
		all_tnt : 'N',
		post_cnt : "0"
	}, 
	tenantList : null,
	categoryList : null,
	paging : {},
	filter: {
  		tnt_nm_ko : null,
 		cate_seq : null,
 		categoryList : null,
 		busi_tnt_cd:null
	}
	
};

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
	'kindCdChange' : function(o,flag) {
		kindCdChange(flag);
	},
	'tenantAll' : function(o) {
		tenantAll();
	},
	'tennantChk' : function(o,val1,val2,val3,val4,val5,val6,val7) { 
		tennantChk(val1,val2,val3,val4,val5,val6,val7);
	},
	'tennantSave' : function() { 
		tennantSave();
	},
	'tennantClose' : function() { 
		tennantClose();
	},
	'movePage' : function(o, page) {
		if(page > 0 
				&& page <= couponData.paging.total_page_cnt
				&& page != couponData.paging.cur_page) {
			
			var offset = (page - 1) * couponData.paging.list_limit + 1; 
			opentenant(offset, couponData.paging.list_limit);
		} 
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
	'deleteImg' : function(o) { 
		
		$(o.node).parent().parent('div').attr('class','img-uploader before');
		//>참조 다른 곳 보완
		var objId = $(o.node).parent().parent('div').attr('id');
		if(objId){
			couponData.coupon[objId] = '';
		}
		//<
	},
	'regCpPosting' : function(o) {
		regCpPosting();
	},
	'regCpUnPosting' : function() {
		regCpUnPosting(couponData.coupon);
	},
	'allBtTenant' : function() {
		allBtTenant();
	},
	'preview' : function() {
		preview();
	},
	'close_preview' : function() {
		$("#auto-discount-coupon-display-popup").hide();
	}
	 
});

var preview = function(){
	couponData.filter.tnt_nm_ko="";
	if(couponData.coupon.cp_kind_cd == '2'){
		$("#pv_titl").text($("#openSeltenantBtn").val());
		$("#tenant-detail-btn2").show();
		$("#tenant-detail-btn").hide();
	}else{
		$("#tenant-detail-btn2").hide();
		$("#tenant-detail-btn").show();
	} 
	  
	$("#pv_date").text($("#cp_act_strt_dt").val().replace(/-/gi, ".") +" - "+ $("#cp_act_end_dt").val().replace(/-/gi, "."));
	$("#pv_img_uri").attr('src', $("#dtl_img_seq_uri").attr('src'));
	  
	$("#auto-discount-coupon-display-popup").show();
}

var allBtTenant = function(){
	$("input[name='tenantInfo']").each(function(idx){
		var tenantInfo = $("input[name='tenantInfo']").eq(idx).val().split(",");
		if($("#allTenant").is(":checked")){
			ckAllTenant(tenantInfo[0],tenantInfo[1],tenantInfo[2],tenantInfo[3],tenantInfo[4],tenantInfo[5],tenantInfo[6]);
		}else{
			ckUnAllTenant(tenantInfo[0],tenantInfo[1],tenantInfo[2],tenantInfo[3],tenantInfo[4],tenantInfo[5],tenantInfo[6]);
		} 
	});   
	    
	if($("#allTenant").is(":checked")){
		$("input[name^='tennant_']").prop('checked',true);
	}else{
		$("input[name^='tennant_']").prop('checked',false);
	}
} 


var ckAllTenant = function(val1,val2,val3,val4,val5,val6,val7){
	var arridx = "";
	var arrtennant = val1+"///"+val2+"///"+val3+"///"+val4+"///"+val5+"///"+val6+"///"+val7;

	if(arrDelTenantNum.indexOf(arrtennant) != -1){
		arridx = arrDelTenantNum.indexOf(arrtennant);
		arrDelTenantNum.splice(arridx,1);
	}else{
		arrDelTenantNum.push(arrtennant);
	} 

	if(arrtenantNum.indexOf(arrtennant) == -1){
		arrtenantNum.push(arrtennant);
	}
} 

var ckUnAllTenant = function(val1,val2,val3,val4,val5,val6,val7){
	var arridx = "";
	var arrtennant = val1+"///"+val2+"///"+val3+"///"+val4+"///"+val5+"///"+val6+"///"+val7;
	
	if(arrDelTenantNum.indexOf(arrtennant) != -1){
		arridx = arrDelTenantNum.indexOf(arrtennant);
		arrDelTenantNum.splice(arridx,1);
	}else{
		arrDelTenantNum.push(arrtennant);
	}
	
	if(arrtenantNum.indexOf(arrtennant) != -1){
		arridx = arrtenantNum.indexOf(arrtennant);
		arrtenantNum.splice(arridx,1);
	}
} 


var tennantClose = function(){
	for(var i = 0 ; i < arrDelTenantNum.length ; i ++){
		if(couponData.coupon.selTenantList == null){ 
			arrtenantNum = [];
			break; 
		}   
		var tnt = arrDelTenantNum[i];
		arridx = arrtenantNum.indexOf(tnt);
		if(arridx > 0){ 
			arrtenantNum.splice(arridx,1);
		}else{ 
			if(couponData.coupon.cp_kind_cd == '1'){ 
				arrtenantNum.push(arrDelTenantNum[i]);
			} 
		} 
	} 
	arrDelTenantNum = [];
	couponData.filter.tnt_nm_ko="";
	couponData.filter.busi_tnt_cd="";
	$("div[name='tnt-reg-popup']").hide();
}
 
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
	couponData.coupon.cp_iss_strt_dt  =	$("#cp_iss_strt_dt").val().split('-').join("");
	couponData.coupon.cp_iss_end_dt   =	$("#cp_iss_end_dt").val().split('-').join("");
	couponData.coupon.cp_act_strt_dt  =	$("#cp_act_strt_dt").val().split('-').join("");
	couponData.coupon.cp_act_end_dt   =	$("#cp_act_end_dt").val().split('-').join("");
	if($('#dft_img_yn:checked').val()){
		couponData.coupon.dft_img_yn = "Y"; 	 
	}else{
		couponData.coupon.dft_img_yn = "N";
	} 
	if($('#all_tnt:checked').val()){
		couponData.coupon.all_tnt = "Y"; 	 
	}else{ 
		couponData.coupon.all_tnt = "N";
	} 
	
	if(!validation(couponData.coupon)){
		return false;
	}
	 
	
	var url = "/rest/01/reqCoupon";
	if(getUriArrVal()[4]){ 
		url = "/rest/01/modifyCoupon";
	}
	Common.REST.post(window.location.origin + url, data, function(data) {
		alert('등록완료!!');
		
		location.href='/01/modifyCoupon/mb/' + data.extra;
		
	}, function(data) {
		console.log('fail data is ', data);
	});
} 


var getCoupon = function(cp_seq){
	var restUri = window.location.origin + '/rest/01/getCoupon/' + cp_seq;
	Common.REST.get(restUri, {}, function(data) {
		couponData.coupon = data; 
		rtvCoupon.set(couponData);  
		
		var btnText = "";
		var idx = data.selTenantList.length;
		if(data.selTenantList.length > 0){
			btnText = data.selTenantList[0].tnt_nm_ko;
			if(data.selTenantList.length > 1){ 
				btnText +=  " 외"+ (idx-1) +"개 테넌트 적용";
			} 
		}
		$("#openSeltenantBtn").val(btnText);
		 
		var arridx = ""; 
		for(var i = 0 ; i < data.selTenantList.length ; i ++){
			var tnt = data.selTenantList[i];
			var arrtennant = tnt.tnt_seq+"///"+tnt.cate_nm_ko+"///"+tnt.tnt_nm_ko+"///"
							+tnt.room_num+"///"+tnt.busi_tnt_cd+"///"+tnt.img_logo_uri+"///"+tnt.img_thmb_uri;
			arrtenantNum.push(arrtennant); 
		}
		var pos_cnt=$("#post_cnt").val();
		//pos_cnt=0;
		if(data.cp_exp_yn  == 'N' ){//미게시
			$("#btnPostion").show(); 
			$("#btnSave").show();
			if(pos_cnt==0){
				$('input').attr("disabled",false);
				$('textarea').attr("disabled",false);
				$('#opentenantBtn').attr("disabled",false);
				$('#img_del1').show();
				$('#img_del2').show();
				
			}else{
				$('input').attr("disabled",true);
				$('textarea').attr("disabled",true);
				$('#appBg2').attr("disabled",false);
				$('#webBg2').attr("disabled",false);
				$('#appBg1').attr("disabled",false);
				$('#webBg1').attr("disabled",false);
				$('#opentenantBtn').attr("disabled",true);
				$('#img_del1').hide();
				$('#img_del2').hide();
			}
		}else{//게시
			$("#btnSave").hide();
			$("#btnPostion").hide();
			$("#btnUnPostion").show();  
			$('input').attr("disabled",true);
			$('textarea').attr("disabled",true);
			$('#opentenantBtn').attr("disabled",false);
			$('#img_del1').hide();
			$('#img_del2').hide();
		} 
		//$('#img_del1').hide();
		//$('#img_del2').hide();
	}, function(data) {
		console.log('fail data is ', data);
	});
};
 
var opentenant =  function(offset, limit){
	/*
	if($("#post_cnt").val() == '1'){
		return false;
	}
	*/
	//alert("aaa");
	//couponData.filter.tnt_nm_ko=""; 
	$("#all_tnt").prop("checked",false);
	$("#allTenant").prop("checked",false);
	couponData.coupon.all_tnt = 'N';
	var offset = offset || 1;
	var limit = limit || 20;
	var restUri = window.location.origin + '/rest/01/getTenants';
	restUri += '?'; 
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit;
	restUri += '&cate_seq=' + couponData.filter.cate_seq;
	//couponData.filter.tnt_nm_ko="";
	//alert(busi_tnt_cd);
	restUri += '&busi_tnt_cd=' + couponData.filter.busi_tnt_cd;
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
		  
		if(arrtenantNum != null){   
			for(var i = 0 ; i < arrtenantNum.length ; i ++){
				var idx = arrtenantNum[i].split('///')[0]; 
				$("input[id=tennant_"+idx+"]").prop('checked',true);  
			}   
		} 
		
		getCategorys();
		$("div[name='tnt-reg-popup']").show();
		
	}, function(data) {
		 
		console.log('fail data is ', data);
		
	}); 
	

};  
 
var getCategorys =  function(){
	
	var restUri = window.location.origin + '/rest/01/getCategorys';
	Common.REST.get(restUri, {}, function(data) {
		couponData.filter.categoryList = data.list;
		rtvCoupon.set(couponData); 
	}, function(data) { 
		  
		console.log('fail data is ', data);
		
	}); 
	
}; 
 
var tennantChk = function(val1,val2,val3,val4,val5,val6,val7){
	var arridx = "";
	var arrtennant = val1+"///"+val2+"///"+val3+"///"+val4+"///"+val5+"///"+val6+"///"+val7;

	if(couponData.coupon.cp_kind_cd == '2'){
		arrtenantNum = [];
		$("input[id^='tennant_']").prop('checked',false);
		$("input[id=tennant_"+val1+"]").prop('checked',true);
	}
	
	if(arrDelTenantNum.indexOf(arrtennant) != -1){
		arridx = arrDelTenantNum.indexOf(arrtennant);
		arrDelTenantNum.splice(arridx,1);
	}else{
		arrDelTenantNum.push(arrtennant);
	}
	 
	
	if(arrtenantNum.indexOf(arrtennant) != -1){
		arridx = arrtenantNum.indexOf(arrtennant);
		arrtenantNum.splice(arridx,1);
	}else{
		arrtenantNum.push(arrtennant);
	}
	
};
 
var tennantSave = function(){
	couponData.filter.tnt_nm_ko="";
	arrDelTenantNum = []; 
	var arr = new Array();
	var idx = arrtenantNum.length; 
	for(var i = 0 ; i < arrtenantNum.length ; i ++){
		arr.push({
		   'tnt_seq' : arrtenantNum[i].split('///')[0] ,
		   'cate_nm_ko' : arrtenantNum[i].split('///')[1],
		   'tnt_nm_ko' : arrtenantNum[i].split('///')[2],
		   'room_num' : arrtenantNum[i].split('///')[3],
		   'busi_tnt_cd' : arrtenantNum[i].split('///')[4],
		   'img_logo_uri' : arrtenantNum[i].split('///')[5],
		   'img_thmb_uri' : arrtenantNum[i].split('///')[6],
		});
	} 

	if(couponData.coupon.cp_kind_cd == '2'){
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
	}
	 
	couponData.coupon.selTenantList = arr; 
	rtvCoupon.set(couponData);
	
	var btnText = ""; 
	if(arrtenantNum.length > 0){
		btnText = arrtenantNum[0].split('///')[2];
		if(idx > 1){ 
			btnText +=  " 외"+ (idx-1) +"개 테넌트 적용";
		} 
	}
	
	$("#openSeltenantBtn").val(btnText);	
	$("div[name='tnt-reg-popup']").hide();
	
	couponData.filter.tnt_nm_ko="";
	couponData.filter.busi_tnt_cd="";
};

var openSeltenant = function(){
	$("div[name='tnt-reg-popup2']").show();
} 

var kindCdChange = function(flag){
	if(flag == '1'){
		$("#tenantAll").show();
		$("#dft_img_yn").attr("disabled",false);
		$("#cp_sale_div_cd1").attr("disabled",true);
		$("#cp_sale_div_cd2").prop('checked',true); 
		changeCpSumSaleMethDiv(false); 
		$("#all_tnt_ck").show();
		getCouponImg();
		
	}else if(flag == '2'){
		$("#all_tnt_ck").hide(); 
		$("#tenantAll").hide(); 
		$("#webBiImg").attr('src','');	  
		$("#moblBiImg").attr('src','');
		$("#dft_img_yn").prop("checked",false); 
		$("#dft_img_yn").attr("disabled",true);
		$("#cp_sale_div_cd1").attr("disabled",false);
		$("#img_seq").attr("class","img-uploader before");
		$("#dtl_img_seq").attr("class","img-uploader before");
		
		couponData.coupon.dft_img_yn = "N";
		couponData.coupon.img_seq = null;
		couponData.coupon.dtl_img_seq = null;
	} 
	
	arrtenantNum = [];
	couponData.coupon.selTenantList = null;
	couponData.coupon.all_tnt = "N";
	rtvCoupon.set(couponData); 
	$("#openSeltenantBtn").val('');
} 

var tenantAll = function(){ 
	if(!$("input[id='all_tnt']").is(":checked")){
		kindCdChange();
		return false;
	}
	
	
	arrtenantNum = [];
	couponData.coupon.selTenantList = null;
	var restUri = window.location.origin + '/rest/01/getTenants';
	Common.REST.get(restUri, {}, function(data) {
		couponData.coupon.selTenantList = data.list; 
		rtvCoupon.set(couponData);
		  
		$("#openSeltenantBtn").val(data.list[0].cate_nm_ko+" 외"+ data.list.length +"개 테넌트 적용");
		
		for(var i = 0 ; i < data.list.length ; i ++){
			var arrtennant = data.list[i].tnt_seq+"///"
							+ data.list[i].cate_nm_ko+"///"
							+ data.list[i].tnt_nm_ko+"///"
							+ data.list[i].room_num+"///"
							+ data.list[i].busi_tnt_cd+"///"
							+ data.list[i].img_logo_uri+"///"
							+ data.list[i].img_thmb_uri;
  
			arrtenantNum.push(arrtennant);
		}
		
		tennantSave();
		
	}, function(data) { 
		
	}); 
}

var deletetenant = function(num){
	if($("#post_cnt").val() == '1'){
		return false;
	}	
 
	$("tr[id=selTenant_"+num+"]").hide();     
	  
} 

var selTennantSave = function(){
	$("div[name='tnt-reg-popup2']").hide();
	for(var i = $("tr[id^='selTenant_']").length ; i >= 0 ; --i){
		if($("tr[id^='selTenant_']").eq(i).css("display") == "none"){
			arrtenantNum.splice(i,1);
		}
	}  
	
	$("tr[id^='selTenant_']").show();
	tennantSave();
	
}

var selTennantClose = function(){
	$("tr[id^='selTenant_']").show();  
	$("div[name='tnt-reg-popup2']").hide();
} 

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

var getCouponImg =  function(){
	var restUri = window.location.origin + '/rest/01/getCouponImg';
	Common.REST.get(restUri, {}, function(data) {

		if(data != null){
			if(data.img_uri != null){
				$("#img_seq").attr("class","img-uploader after");
				$("#img_seq_uri").attr('src',data.img_uri);
				couponData.coupon.img_seq = data.img_seq;
			}
			if(data.dtl_img_uri != null){
				$("#dtl_img_seq").attr("class","img-uploader after");
				$("#dtl_img_seq_uri").attr('src',data.dtl_img_uri);
				couponData.coupon.dtl_img_seq = data.dtl_img_seq;
			}
		}
		
	}, function(data) { 
		console.log('fail data is ', data);
		
	}); 
	
	//$("#dialog-detail").dialog("open");

}; 

var regCpPosting = function(){
	if(!confirm("게시처리 된 쿠폰은 수정이 불가합니다. \n게시전 입력내용을 다시 확번 확인해주세요.")){
		return false;
	}else{
		var restUri = window.location.origin + '/rest/01/mbCoupon/posting/'+couponData.coupon.cp_seq;
		Common.REST.get(restUri, {}, function(data) {
			location.href='/01/modifyCoupon/mb/' + couponData.coupon.cp_seq;
		}, function(data) {  
			console.log('fail data is ', data);
			
		});
	}
}

var regCpUnPosting = function(data){
	var restUri = window.location.origin + '/rest/01/nmCoupon/unposted/' + data.cp_seq;
	Common.REST.get(restUri, {}, function(param) {
		location.href='/01/modifyCoupon/mb/' + data.cp_seq;
		  
	}, function(data) {
		console.log('fail data is ', data);
		 
	});
}


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
 
$(function() { 
	initTitleImgUploadButton();
	$("#cp_sale_div_cd1").attr("disabled",true); 
	changeCpSumSaleMethDiv(false);  
	getCouponImg();
	$("input[name='file']").eq(2).attr("disabled",true);
	$("input[name='file']").eq(3).attr("disabled",true);
	
	if(getUriArrVal()[4]){ 
		getCoupon(getUriArrVal()[4]);
	}
	datepicker();
});
   


