
////////////////////////////////////////////////////////
// global variables
var ckeckTenant = new Array();
var selectTenant = new Array();
var arrDelTenantNum = new Array();
var appPushData  = {
	wcPushMsg : {
		push_titl : null, 
		titl_img_seq : null, 
		exp_strt_dt : null, 
		exp_end_dt : null, 
		sys_push_msg : null, 
		dtl_msg : null, 
		dtl_msg_seq : null,
		cp_seq : null,
		cmp_id : null,
		selTenantList : null,
		exp_yn : null
	},
	tenantList : null,
	couponList : null,
	paging : {},
	filter :{}
};


var rtvAppPush = new Ractive({
	el : '#wcPushMsg-detail',
	template : '#tmpl-wcPushMsg-detail',
	data : appPushData
});


rtvAppPush.on({
	'msgType' : function(o,type) { 
		msgType(type);
	},
	'reqWcPushMsg' : function() { 
		reqWcPushMsg(appPushData.wcPushMsg);
	},
	'getCoupons' : function() { 
		getCoupons(1,10);
	},
	'openZonePop' : function() {
		appPushData.filter.tnt_nm_ko="";
		$("#zoneP").show();
		getZones(1,10);
	},
	'getZones' : function() {
		getZones(1,10);
	},
	'zonePageMove' : function(o, page) {
		if(page > 0 && page <= appPushData.paging.total_page_cnt && page != appPushData.paging.cur_page) {
			var offset = (page - 1) * appPushData.paging.list_limit + 1; 
		
			getZones(offset, appPushData.paging.list_limit);
		}
	},
	'cpPageMove' : function(o, page) {
		if(page > 0 && page <= appPushData.paging.total_page_cnt && page != appPushData.paging.cur_page) {
			var offset = (page - 1) * appPushData.paging.list_limit + 1; 
		 
			getCoupons(offset, appPushData.paging.list_limit);
		}
	},
	'closeTenant' : function() {
		for(var i = 0 ; i < arrDelTenantNum.length ; i ++){
			if(appPushData.wcPushMsg.selTenantList == null){ 
				ckeckTenant = [];
				break; 
			}    
			var tnt = arrDelTenantNum[i];
			arridx = ckeckTenant.indexOf(tnt);
			if(arridx > 0){ 
				ckeckTenant.splice(arridx,1);
			}else{ 
				ckeckTenant.push(arrDelTenantNum[i]);
			} 
		} 
		arrDelTenantNum = [];
		
		$("#zoneP").hide();
	},
	'saveTenant' : function() {
		saveTenant();
	},
	'reqWcPosting' : function() {
		reqWcPosting(appPushData.wcPushMsg);
	},
	'reqWcUnPosting' : function() {
		reqWcUnPosting(appPushData.wcPushMsg);
	},
	'ckTenant' : function(o,zoneId,tnt_nm) {
		ckTenant(zoneId,tnt_nm);
	},
	'selCoupon' : function() {  
		appPushData.wcPushMsg.cp_seq = $('input:radio[name="cp_ck"]:checked').val();
		$("#acp").val($("td[id="+ appPushData.wcPushMsg.cp_seq+"]").text()); 
		$("#couponP").hide();
	},
	'delCoupon' : function() {//적용 쿠폰 삭제  
		if(appPushData.wcPushMsg.exp_yn == 'Y'){
			return false;
		}
		appPushData.wcPushMsg.cp_seq = '';
		$("#acp").val(''); 
	},  
	'closeCoupon' : function() {
		$("#couponP").hide();
	}, 
	'deleteImg' : function(o) {
		$(o.node).parent().parent('div').attr('class','img-uploader before');
		var objId = $(o.node).parent().parent('div').attr('id');
		if(objId){ 
			appPushData.wcPushMsg[objId] = '';
		}
	},
	'allBtTenant' : function(o) {
		allBtTenant();
	},
	'getSelZones' : function(o) {
		//alert("aa");
		$("#selZoneP").show();  
	},
	'selZoneClose' : function(o) {
		$("#selZoneP").hide();  
	},
	'selDeleteZone' : function(o, zone_id) {
		selDeleteZone(zone_id);  
	},
	'selZoneSave' : function(o) {
		selZoneSave();  
	},
	'preview' : function() {
		preview();
	},
	'close_preview' : function() {
		$("#welcome-display-popup").hide();
	}
});

var preview = function(){
	
	if($("#titl_img_seq_uri").attr('src')){
		$("#pv_titl_img").show();
		$("#pv_titl_img_uri").attr('src',$("#titl_img_seq_uri").attr('src'));
		
	}else{ 
		$("#pv_titl_img").hide();
		$("#pv_titl_text").text($("#push_titl").val());
	}
	
	if($("#dtl_msg_seq_uri").attr('src')){
		$("#pv_detail_img").show();
		$("#pv_detail_img_uri").attr('src',$("#dtl_msg_seq_uri").attr('src'));
		
	}else{
		$("#pv_detail_img").hide();
		$("#pv_detail_text").text($("#dtl_msg").val());
	}
	
	$("#welcome-display-popup").show();
} 

var selDeleteZone = function(zone_id){
	$("tr[id=selZone_"+zone_id+"]").hide();     
} 
  
var selZoneSave = function(){
	$("#selZoneP").hide(); 
	for(var i = $("tr[id^='selZone']").length ; i >= 0 ; --i){
		if($("tr[id^='selZone']").eq(i).css("display") == "none"){
			ckeckTenant.splice(i,1);
		}
	}  
	
	$("tr[id^='selZone']").show();
	saveTenant();
	
}

var allBtTenant = function(){   
	$("input[name='tenantInfo']").each(function(idx){
		var tenantInfo = $("input[name='tenantInfo']").eq(idx).val().split(",");
		if($("#allTenant").is(":checked")){
			ckAllTenant(tenantInfo[0],tenantInfo[1]);
		}else{
			ckUnAllTenant(tenantInfo[0],tenantInfo[1]);
		} 
	});   
	 
	if($("#allTenant").is(":checked")){
		$("input[name^='tenant_']").prop('checked',true);
	}else{
		$("input[name^='tenant_']").prop('checked',false);
	}
	
	var tot_cnt = 0;
	var push_cnt = 0;
	//if(appPushData.pushMembList.length > 0){
	tot_cnt = $("#all_zone_cnt").val();
		//push_cnt = 0;
		//if(appPushData.scenario.delMemSeq != null){
	push_cnt = ckeckTenant.length;
		//}
	//}
	//alert(push_cnt+","+tot_cnt);
	$("#zone_cnt").text("선택 "+push_cnt+"개  / 전체웰컴존 "+tot_cnt+"개");
}
//전체선택
var ckAllTenant = function(zoneId,tnt_nm){
	if(arrDelTenantNum.indexOf(zoneId+"///"+tnt_nm) != -1){
		var arridx = arrDelTenantNum.indexOf(zoneId+"///"+tnt_nm);
		arrDelTenantNum.splice(arridx,1);
	}else{
		arrDelTenantNum.push(zoneId+"///"+tnt_nm);
	} 
	
	if(ckeckTenant.indexOf(zoneId+"///"+tnt_nm) == -1){
		ckeckTenant.push(zoneId+"///"+tnt_nm);
	}
	 
	
} 
//전체해제시
var ckUnAllTenant = function(zoneId,tnt_nm){
	var arridx= '';
	if(arrDelTenantNum.indexOf(zoneId+"///"+tnt_nm) != -1){
		arridx = arrDelTenantNum.indexOf(zoneId+"///"+tnt_nm);
		arrDelTenantNum.splice(arridx,1);
	}else{
		arrDelTenantNum.push(zoneId+"///"+tnt_nm);
	} 

	if(ckeckTenant.indexOf(zoneId+"///"+tnt_nm) != -1){
		arridx = ckeckTenant.indexOf(zoneId+"///"+tnt_nm);
		ckeckTenant.splice(arridx,1);
	}
	
	
} 

var ckTenant = function(zoneId,tnt_nm){
	
	if(arrDelTenantNum.indexOf(zoneId+"///"+tnt_nm) != -1){
		var arridx = arrDelTenantNum.indexOf(zoneId+"///"+tnt_nm);
		arrDelTenantNum.splice(arridx,1);
	}else{
		arrDelTenantNum.push(zoneId+"///"+tnt_nm);
	} 
	
	if(ckeckTenant.indexOf(zoneId+"///"+tnt_nm) != -1){
		var	arridx = ckeckTenant.indexOf(zoneId+"///"+tnt_nm);
		ckeckTenant.splice(arridx,1);
		
	}else{
		ckeckTenant.push(zoneId+"///"+tnt_nm);
	}
	var tot_cnt = 0;
	var push_cnt = 0;
	//if(appPushData.pushMembList.length > 0){
	tot_cnt = $("#all_zone_cnt").val();
		//push_cnt = 0;
		//if(appPushData.scenario.delMemSeq != null){
	push_cnt = ckeckTenant.length;
		//}
	//}
	//alert(push_cnt+","+tot_cnt);
	$("#zone_cnt").text("선택 "+push_cnt+"개  / 전체웰컴존 "+tot_cnt+"개");
	//alert("bb");
	
} 

var msgType = function(type){
	if(type == 1){
		$("input[name='file']").eq(1).attr("disabled",false);
		$("#dtl_msg").attr("disabled",true);
 
		$("#dtl_msg").val('');
		appPushData.wcPushMsg.dtl_msg = null;
		 
	}else{
		$("input[name='file']").eq(1).attr("disabled",true);
		$("#dtl_msg").attr("disabled",false);
		 
		
		$("#dtl_msg_seq").attr("class","img-uploader before"); 
		$("#dtl_msg_seq_uri").attr("src",""); 
		
	} 
} 


var reqWcPushMsg = function(data){
	data.exp_strt_dt    = 	$("#exp_strt_dt").val().split('-').join("");
	data.exp_end_dt     = 	$("#exp_end_dt").val().split('-').join("");
	var url = "/rest/01/reqWcPushMsg";
	if(getUriArrVal()[4]){ 
		url = "/rest/01/modifyWcPushMsg";
	}
	Common.REST.post(window.location.origin + url, data, function(data) {
		console.log('success data is ', data);
		location.href='/01/welcome/push/' + data.extra;
		alert('등록완료!!');
		 
	}, function(data) {
		console.log('fail data is ', data);
	});
}


var getCoupons =  function(offset, limit){
	if(appPushData.wcPushMsg.exp_yn  == 'Y') {
		return false;
	} 
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/2/coupons';
	restUri += '?'; 
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit; 
	restUri += '&cp_titl=' + appPushData.filter.cp_titl;
	Common.REST.get(restUri, {}, function(data) {
		$("#cp_cnt").text(data.srch_cnt);
		appPushData.couponList = data.list;
		appPushData.paging = data.paging;
				
		appPushData.paging.pages = []; 
		
		for(var i = appPushData.paging.page_start; i <= appPushData.paging.page_end; ++i) {
			appPushData.paging.pages.push(i);
        }
		rtvAppPush.set(appPushData);
		
		if($("#acp").val() == ''){
			$("input[name='cp_ck']").prop('checked',false); 
		}
		//파일
	}, function(data) {
		 
		console.log('fail data is ', data);
		
	}); 
	 
	$("#couponP").show();

};  


var getZones = function(offset, limit) {
	//$("#tenantNm").val("");
	//appPushData.filter.tnt_nm_ko="";
	arrDelTenantNum = [];
	if(appPushData.wcPushMsg.exp_yn  == 'Y') {
		return false;
	} 
	
	var offset = offset || 1;
	var limit = limit || 20;
	var restUri = window.location.origin + '/rest/01/welcome/tenants';
	restUri += '?'; 
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit;
	restUri += '&tnt_nm=' + appPushData.filter.tnt_nm_ko;
	Common.REST.get(restUri, {}, function(data) {
		$("#all_zone_cnt").val(data.srch_cnt);
		$("#zone_cnt").text("선택 0개/전체웰컴존 "+data.srch_cnt +"개");
		appPushData.tenantList = data.list;
		appPushData.paging = data.paging;
				
		appPushData.paging.pages = [];
		 
		for(var i = appPushData.paging.page_start; i <= appPushData.paging.page_end; ++i) {
			appPushData.paging.pages.push(i);
        }
		
		rtvAppPush.set(appPushData);
		
		if(appPushData.wcPushMsg.selTenantList != null){
			$("input[id^='tenant_']").prop('checked',false);
			for(var i = 0 ; i < appPushData.wcPushMsg.selTenantList.length ; i ++){
				var idx = appPushData.wcPushMsg.selTenantList[i].zone_id;
				$("input[id=tenant_"+idx+"]").prop('checked',true);  
			}   
		}else{
			$("input[id^='tenant_']").prop('checked',false);
		}  
		
		if(ckeckTenant != null){  
			for(var i = 0 ; i < ckeckTenant.length ; i ++){
				var idx = ckeckTenant[i].split('///')[0]; 
				$("input[id=tenant_"+idx+"]").prop('checked',true);  
			}  
		}
		 
		$("#zoneP").show();
		//파일
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};


var saveTenant = function(){
	arrDelTenantNum = [];
	var arr = new Array();  
	var idx = ckeckTenant.length;
	for(var i = 0 ; i < idx ; i ++){
		arr.push({ 
		   'zone_id' : ckeckTenant[i].split('///')[0],
		   'tnt_nm_ko' : ckeckTenant[i].split('///')[1]
		});
	}  
 	    
	selectTenant = arr; 
	appPushData.wcPushMsg.selTenantList = arr;
	if(selectTenant.length == 1){
		$("#tenantNm").val(selectTenant[0].tnt_nm_ko);
	}else if(selectTenant.length > 1){   
		$("#tenantNm").val(selectTenant[0].tnt_nm_ko+"외 "+(selectTenant.length-1)+"개");
	}else{
		$("#tenantNm").val(''); 
	}
	 
	rtvAppPush.set(appPushData);
	$("#zoneP").hide();
	 
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
		appPushData.wcPushMsg[$targetParent.attr("id")] = r.img_seq;
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



var getWcPushMsg = function(wel_msg_push_seq){
	var restUri = window.location.origin + '/rest/01/appPush/welcome/' + wel_msg_push_seq;
	Common.REST.get(restUri, {}, function(data) {
		appPushData.wcPushMsg = data; 
		rtvAppPush.set(appPushData);  
		$("#acp").val(appPushData.wcPushMsg.cp_titl);
		$("#imgurl1").attr("src",data.dtl_titl_img_seq);
		$("#imgurl2").attr("src",data.dtl_img_thmb_uri);
 
		if(data.dtl_msg == null){
			$("#msgType1").prop("checked",true);
			msgType(1);  
		}else{ 
			$("#msgType2").prop("checked",true);
			msgType(2); 
		} 
 
		if(data.selTenantList != null) {
			for(var i = 0 ; i < data.selTenantList.length ; i ++) {
				var zone_id = data.selTenantList[i].zone_id;
				var tnt_nm_ko = data.selTenantList[i].tnt_nm_ko;
				ckTenant(zone_id,tnt_nm_ko);  
			}  
		}
		
		if(data.selTenantList.length == 1){
			$("#tenantNm").val(data.selTenantList[0].tnt_nm_ko);
		}else if(data.selTenantList.length > 1){   
			$("#tenantNm").val(data.selTenantList[0].tnt_nm_ko+"외 "+(data.selTenantList.length-1)+"개");
		}  

		if(data.exp_yn  == 'N' ){
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
}

var reqWcPosting = function(data) { 
	var restUri = window.location.origin + '/rest/01/welcome/posting/' + getUriArrVal()[4];
	Common.REST.post(restUri, data, function(data) {
		location.href='/01/welcome/push/' + getUriArrVal()[4];
	}, function(data) { 
		console.log('fail data is ', data);
	});
}

var reqWcUnPosting = function(data) { 
	var restUri = window.location.origin + '/rest/01/welcome/unPosting/' + getUriArrVal()[4];
	Common.REST.post(restUri, data, function(data) {
		location.href='/01/welcome/push/' + getUriArrVal()[4];
	}, function(data) { 
		console.log('fail data is ', data);
	});
}

$(function() {  
	initTitleImgUploadButton();
	datepicker();
	if(getUriArrVal()[4]){ 
		getWcPushMsg(getUriArrVal()[4]);
	}else{  
		$("input[name='file']").eq(1).attr("disabled",true);
	}
});


