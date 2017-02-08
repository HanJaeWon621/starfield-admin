
////////////////////////////////////////////////////////
// global variables
/**
 * 쿠키값 추출
 * @param cookieName 쿠키명
 */
function getCookie( cookieName )
{
 var search = cookieName + "=";
 var cookie = document.cookie;

 // 현재 쿠키가 존재할 경우
 if( cookie.length > 0 )
 {
  // 해당 쿠키명이 존재하는지 검색한 후 존재하면 위치를 리턴.
  startIndex = cookie.indexOf( cookieName );

  // 만약 존재한다면
  if( startIndex != -1 )
  {
   // 값을 얻어내기 위해 시작 인덱스 조절
   startIndex += cookieName.length;

   // 값을 얻어내기 위해 종료 인덱스 추출
   endIndex = cookie.indexOf( ";", startIndex );

   // 만약 종료 인덱스를 못찾게 되면 쿠키 전체길이로 설정
   if( endIndex == -1) endIndex = cookie.length;

   // 쿠키값을 추출하여 리턴
   return unescape( cookie.substring( startIndex + 1, endIndex ) );
  }
  else
  {
   // 쿠키 내에 해당 쿠키가 존재하지 않을 경우
   return false;
  }
 }
 else
 {
  // 쿠키 자체가 없을 경우
  return false;
 }
}



/**
 * 쿠키 설정
 * @param cookieName 쿠키명
 * @param cookieValue 쿠키값
 * @param expireDay 쿠키 유효날짜
 */
function setCookie( cookieName, cookieValue, expireDate )
{
 var today = new Date();
 today.setDate( today.getDate() + parseInt( expireDate ) );
 document.cookie = cookieName + "=" + escape( cookieValue ) + "; path=/; expires=" + today.toGMTString() + ";";
}
/**
 * 쿠키 삭제
 * @param cookieName 삭제할 쿠키명
 */
function deleteCookie( cookieName )
{
 var expireDate = new Date();
 
 //어제 날짜를 쿠키 소멸 날짜로 설정한다.
 expireDate.setDate( expireDate.getDate() - 1 );
 document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
}

var commonData  = {
		commonFilter : {
			bcnList : null,
			sh_bcn_cd : ''	
		}
		
};

var styleData  = {
	styleList : null,
	
	paging : {},
	couponFilter : {
		bcnList : null,
		sh_bcn_cd : '',
		sh_fl : ''
	},
	formdata : {
		fb_seq : null,
		style_nm : null,
		mouse_state : null,
		style_comment : null,
		bg_color : null,
		bd_size : null,
		bd_color : null,
		important_cd : null,
		ext_style : null
	}
	
};

var styleDtlData  = {
		formdata : {
			 fl_seq : null	
			,fl : null
			,fl_nm : null
			,dft_block_clr : null
			,dft_block_hgt : null
			,base_plate_yn : null
			,bs_plate_hgt : null
			,bs_plate_clr : null
			,bs_plate_oft : null
			,dft_block_pdg : null
			,dft_spray_sle : null
			,bcn_cd : null
		}
	};

var rtvCommon = new Ractive({
	el : '#common-list',
	template : '#tmpl-common-list',
	data : commonData
});
var rtvCoupon = new Ractive({
	el : '#style-list',
	template : '#tmpl-style-list',
	data : styleData
});
var rtvCoupon2 = new Ractive({
	el : '#style-dtl',
	template : '#tmpl-style-dtl',
	data : styleDtlData
});
rtvCommon.on({ 
	'chgBcn' : function(){
		shDataList();
	}
});
rtvCoupon.on({
	'pageMove' : function(o, page) {
		if(page > 0 
				&& page <= styleData.paging.total_page_cnt
				&& page != styleData.paging.cur_page) {

			var offset = (page - 1) * styleData.paging.list_limit + 1; 
			getDataList(offset, styleData.paging.list_limit);
		}
		
	}, 
	'shDataList' : function(){
		
		shDataList();
	},
	'listSort' : function(o,sortColumn){  
		//alert($(o.node).data("order-key"));
		//getSortFilter(o, styleData.couponFilter,'sortColumn','sortColumnAscDesc');
		getDataList(1,10,sortColumn);
	},
	'deleteFloor' : function(o, bcn_cd, fl_seq) {//등록/수정
		deleteFloor(bcn_cd, fl_seq);
	},
	'openPop' : function(o,mode, bcn_cd, fl_seq){

		if(mode=='Edit'){
			//alert(bcn_cd +","+ fl_seq)
			getFloor(bcn_cd, fl_seq);
			$("#formtitl").text("수정");
		}else{
			styleDtlData.formdata.fl='';
			styleDtlData.formdata.fl_nm='';
			styleDtlData.formdata.dft_block_clr='';
			styleDtlData.formdata.dft_block_hgt='';
			styleDtlData.formdata.base_plate_yn='';
			styleDtlData.formdata.bs_plate_hgt='';
			styleDtlData.formdata.bs_plate_clr='';
			styleDtlData.formdata.bs_plate_oft='';
			styleDtlData.formdata.dft_block_pdg='';
			styleDtlData.formdata.dft_spray_sle='';
			styleDtlData.formdata.bcn_cd=$("#sh_bcn_cd").val();
			rtvCoupon2.set(styleDtlData);
			$("#formtitl").text("등록");
		}
		//$("#ex1").show();
	},
	'openMap' : function(o,mode, bcn_cd){
		
	}
});

rtvCoupon2.on({ 
	'regSave' : function(o) {//등록/수정
		//$("#delBtn").hide();
		regStyle(styleDtlData.formdata);
	},
	'closePop' : function(o,tnt_seq){
		$("div[name='tnt-reg-popup2']").hide();
	}
});
var shDataList = function(){

	getDataList(1,10);

}

var regStyle = function(data){
	
	if(!validation(data)){
		return false;
	}
	var bcn_cd = getUriArrVal()[1];
	var url = "/rest/"+bcn_cd+"/regFloor";
	//if(getU
	if($("#formtitl").text()=="수정")
	{
		url = "/rest/modifyFloor";
	}else{
		url = "/rest/regFloor";
	}

	Common.REST.post(window.location.origin + url, data, function(data) {
		if($("#formtitl").text()=="수정")
		{
			alert('수정완료!!');  
		}else{
			alert('등록완료!!');  
		}

		getDataList(1, 10);

		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
} 

var deleteFloor = function(bcn_cd, fl_seq){
	var restUri = "/rest/deleteFloor";
	restUri += '?'; 
	restUri += 'bcn_cd=' + bcn_cd; 
	restUri += '&fl_seq=' + fl_seq;
	
	Common.REST.get(window.location.origin + restUri, {}, function(data) {

		alert('삭제완료!!');  
		getDataList(1, 10);
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}
var validation = function(data){
	/*
	if(data.style_nm == null || data.style_nm == '') {
		alert("스타일 이름을 입력 하세요.");
		return false;
	}
	*/
	if(data.fl == null || data.fl == '') {
		alert("층(floor)를 입력 하세요.");
		return false;
	}
	
	return true;
}



var getDataList = function(offset, limit,sortColumn) {
	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/getFloors';
	setCookie( "bcn_cd", commonData.commonFilter.sh_bcn_cd, 1 );
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_bcn_cd=' + commonData.commonFilter.sh_bcn_cd;
	
	Common.REST.get(restUri, {}, function(data) {
		styleData.styleList = data.list; 
		styleData.paging = data.paging; 
		styleData.paging.pages = [];
		 
		for(var  i = styleData.paging.page_start; i <= styleData.paging.page_end; ++i) {
			styleData.paging.pages.push(i);
        }
		rtvCoupon.set(styleData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getCommonBcnList = function() {

	var restUri = window.location.origin + '/rest/getAllBcns';
	
	Common.REST.get(restUri, {}, function(data) {
		commonData.commonFilter.bcnList = data.list;
		commonData.commonFilter.sh_bcn_cd = getCookie("bcn_cd");
		rtvCommon.set(commonData);
		//getDataList(1, 10);
		getDataList();
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};


var getFloor = function(bcn_cd, fl_seq) {
//var getFloor = function(data) {	
	//var bcn_cd = getUriArrVal()[1];
	//var restUri = window.location.origin + '/rest/getFloor/'+bcn_cd+'/'+fl_seq;
	var restUri = window.location.origin + '/rest/getFloor';
	restUri += '?'; 
	restUri += 'bcn_cd=' + bcn_cd; 
	restUri += '&fl_seq=' + fl_seq;
	Common.REST.get(restUri, {}, function(data) {
		styleDtlData.formdata = data;
		rtvCoupon2.set(styleDtlData);
	}, function(data) {
		console.log('fail data2 is ', data);
	}); 
};

$(function() {
	getCommonBcnList();
	console.log(getCookie("bcn_cd"));

});


