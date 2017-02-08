
////////////////////////////////////////////////////////
// global variables
var selTenant = new Array();
var appPushData  = {
	wcPushMsg : {
		wel_msg_push_seq : null,
		push_titl : null, 
		titl_img_seq : null, 
		exp_strt_dt : null, 
		exp_end_dt : null, 
		sys_push_msg : null, 
		dtl_msg : null, 
		dtl_msg_seq : null,
		reg_usr : null,
		reg_dttm : null,
		dtl_titl_img_seq : null,
		dtl_img_thmb_uri : null,
		cp_seq : null,
		selTenantList : null, 
	},
	allTenantList : null,
	couponList : null,
	paging : {},
};


var rtvAppPush = new Ractive({
	el : '#wcPushMsg-detail',
	template : '#tmpl-wcPushMsg-detail',
	data : appPushData
});
 

/*var rtvCoupon = new Ractive({
	el : '#dialog-detail',
	template : '#tmpl-coupon-list',
	data : appPushData
});


var rtvTenant = new Ractive({
	el : '#dialog-detail2',
	template : '#tmpl-tenant-list',
	data : appPushData
});*/

rtvAppPush.on({
	'modifyWcPushMsg' : function(o) {
		modifyWcPushMsg(appPushData.wcPushMsg);
	},
	'getCoupons' : function() { 
		getCoupons(1,3);
	},
	'getZones' : function() { 
		getZones(1,10);
	},
	'ckTenant' : function(o,zoneId,tnt_nm) {
		ckTenant(zoneId,tnt_nm);
	},
	'saveTenant' : function() {
		saveTenant();
	},
	'closeZone' : function() {
		$("#zoneP").hide();
	}
	
});

/*
rtvCoupon.on({
	'selCoupon' : function() {  
		appPushData.wcPushMsg.cp_seq = $('input:radio[name="cp_ck"]:checked').val();
		$("#acp").val($("span[id="+ appPushData.wcPushMsg.cp_seq+"]").text());
		$( "#dialog-detail" ).dialog( "close" );
	}
});

 
rtvTenant.on({
	, 

});*/


var modifyWcPushMsg = function(data){
	data.exp_strt_dt    = 	$("#exp_strt_dt").val().split('-').join("");
	data.exp_end_dt     = 	$("#exp_end_dt").val().split('-').join("");
	
	Common.REST.post(window.location.origin + '/rest/bcnCd/modifyWcPushMsg', data, function(data) {
		console.log('success data is ', data);
		//$("img[id=imgv]").attr('src','');
		alert('등록완료!!');
		
		//rtvCoupon.set(rtvCoupon);
		//resetEmpForm();
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}


var msgType = function(type){
	if(type == 1){
		$("input[name='file']").eq(1).attr("disabled",false);
		$("#dtl_msg").attr("disabled",true);
	}else{
		$("input[name='file']").eq(1).attr("disabled",true);
		$("#dtl_msg").attr("disabled",false);
		
		appPushData.wcPushMsg.dtl_msg_seq = null;
		$("#imgurl2").attr("src",""); 
	} 
}


var getCoupons =  function(offset, limit){
	var offset = offset || 1;
	var limit = limit || 20;
	var restUri = window.location.origin + '/rest/bcnCd/welcome/coupons';
	restUri += '?'; 
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit;
	Common.REST.get(restUri, {}, function(data) {
		appPushData.couponList = data.list;
		appPushData.paging = data.paging;
				
		appPushData.paging.pages = [];
		
		for(var i = appPushData.paging.page_start; i <= appPushData.paging.page_end; ++i) {
			appPushData.paging.pages.push(i);
        }
		rtvAppPush.set(appPushData);
	
		//파일
	}, function(data) {
		 
		console.log('fail data is ', data);
		
	}); 
	
	$("#dialog-detail").dialog("open");

};



var getZones =  function(offset, limit){
	var offset = offset || 1;
	var limit = limit || 20;
	var restUri = window.location.origin + '/rest/01/welcome/tenants';
	restUri += '?'; 
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit;
	Common.REST.get(restUri, {}, function(data) {
		appPushData.allTenantList = data.list;
		appPushData.paging = data.paging;
				
		appPushData.paging.pages = [];
		 
		for(var i = appPushData.paging.page_start; i <= appPushData.paging.page_end; ++i) {
			appPushData.paging.pages.push(i); 
        }
		rtvAppPush.set(appPushData);  
		if(appPushData.wcPushMsg.selTenantList != null){ 
			for(var i = 0 ; i < appPushData.wcPushMsg.selTenantList.length ; i++) {
				var zone_id = appPushData.wcPushMsg.selTenantList[i].zone_id;
				var tnt_nm = appPushData.wcPushMsg.selTenantList[i].tnt_nm_ko;
				selTenant.push(zone_id+"///"+tnt_nm); 
				$("input[id=tenant_"+zone_id+"]").prop('checked',true);  
			}     
		}else{
			$("input[id^='tenant_']").prop('checked',false);
		} 
		 
		$("#zoneP").show();
		//파일
	}, function(data) {
		 
		console.log('fail data is ', data);
		
	}); 
	 

};


var ckTenant = function(zoneId,tnt_nm){
	if(selTenant.indexOf(zoneId+"///"+tnt_nm) != -1){
		var	arridx = selTenant.indexOf(zoneId+"///"+tnt_nm);
		selTenant.splice(arridx,1);
		
	}else{
		selTenant.push(zoneId+"///"+tnt_nm);
	} 
} 

 
var saveTenant = function(){
	alert(selTenant.length); 
	var arr = new Array();  
	var idx = selTenant.length;
	for(var i = 0 ; i < idx ; i ++){
		arr.push({ 
		   'zone_id' : selTenant[i].split('///')[0],
		   'tnt_nm_ko' : selTenant[i].split('///')[1]
		});
	}  
 	     
	appPushData.wcPushMsg.selTenantList = arr;
	 
	if(appPushData.wcPushMsg.selTenantList.length > 0 && appPushData.wcPushMsg.selTenantList.length == 1){
		$("#tenantNm").val(appPushData.wcPushMsg.selTenantList[0].tnt_nm_ko);
	}else if(appPushData.wcPushMsg.selTenantList.length > 0){   
		$("#tenantNm").val(appPushData.wcPushMsg.selTenantList[0].tnt_nm_ko+"외 "+(appPushData.wcPushMsg.selTenantList.length-1)+"개");
	} 
	
	$("#zoneP").hide(); 
	 
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
			appPushData.wcPushMsg.titl_img_seq = resultParam.img_seq;
			$("#imgurl1").attr("src",resultParam.img_uri); 
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



//이미지 업로드
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
			appPushData.wcPushMsg.dtl_msg_seq = resultParam.img_seq;
			$("#imgurl2").attr("src",resultParam.img_uri); 
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


var getWcPushMsg = function(wel_msg_push_seq){
	var restUri = window.location.origin + '/rest/01/appPush/welcome/' + wel_msg_push_seq;
	Common.REST.get(restUri, {}, function(data) {
		appPushData.wcPushMsg = data; 
		rtvAppPush.set(appPushData);  
		 
		$("#imgurl1").attr("src",data.dtl_titl_img_seq);
		$("#imgurl2").attr("src",data.dtl_img_thmb_uri);
		
		if(data.dtl_msg == null){
			$("#msgType1").prop("checked",true);
			msgType(1);
		}else{ 
			$("#msgType2").prop("checked",true);
			msgType(2);
		}
		 
	}, function(data) {
		console.log('fail data is ', data);
	});
}



$(function() { 
	initTitleImgUploadButton1();
	initTitleImgUploadButton2();
	alert(getUriArrVal()[3]);
	getWcPushMsg(getUriArrVal()[3]);
});


