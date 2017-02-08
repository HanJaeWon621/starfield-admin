
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
		floorList : null,
		sh_bcn_cd : '',	
		sh_fl : '',
		sh_room_num : null
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
			fb_seq : null,
			spray : null,
			data : null,
			height : null,
			spray_oft_x : null,
			spray_oft_y : null,
			spray_sle : null,
			point_cord : null,
			fl : null,
			fl_seq : null,
			room_num : null,
			etc : null,
			bcn_cd : null
		}
	};




//public String fl_seq;
//public String fl;

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
		getBcnFloorList();
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
		shCoupon();
	}, 
	'chgBcn' : function(){
		getBcnFloorList();
	},
	'deleteFloorBlock' : function(o, bcn_cd, fb_seq) {//등록/수정
		//$("#delBtn").show();
		//alert(fb_seq);
		
		deleteFloorBlock(bcn_cd, fb_seq);
	},
	'deleteAllFloorBlock' : function(o) {//등록/수정
		//$("#delBtn").show();
		//alert(fb_seq);
		var bcn_cd = commonData.commonFilter.sh_bcn_cd;
		var fl = styleData.couponFilter.sh_fl;
		//alert(bcn_cd +""+ fl);
		if(bcn_cd==''){
			alert("지점을 선택하세요.");
			return;
		}
		if(fl==''){
			alert("층을 선택하세요.");
			return;
		}
		deleteAllFloorBlock(bcn_cd, fl);
	},
	
	'openPop' : function(o,mode, bcn_cd, fb_seq){
		if(mode=='Edit'){
			getFloorBlock(bcn_cd, fb_seq);
			$("#formtitl").text("수정");
		}else{
			styleDtlData.formdata.fb_seq='';
			styleDtlData.formdata.spray='';
			styleDtlData.formdata.data='';
			styleDtlData.formdata.height='';
			styleDtlData.formdata.spray_oft_x='';
			styleDtlData.formdata.spray_oft_y='';
			styleDtlData.formdata.spray_sle='';
			styleDtlData.formdata.point_cord='';
			styleDtlData.formdata.bcn_cd=commonData.commonFilter.sh_bcn_cd;
			styleDtlData.formdata.room_num='';
			styleDtlData.formdata.fl=$("#sh_fl").val();
			rtvCoupon2.set(styleDtlData);
			$("#formtitl").text("등록");
		}
		$("#tnt-reg-popup2").show();
	},
	'moveFile' : function(o){
		
		var bcn_cd = commonData.commonFilter.sh_bcn_cd;
		var fl=$("#sh_fl").val();
		if(fl==''){
			alert("층을 선택하세요.");
			return;
		}
		location.href=window.location.origin + "/"+bcn_cd+"/map/floorblocksup/"+fl;
	}
});

rtvCoupon2.on({ 
	'regSave' : function(o) {//등록/수정
		//$("#delBtn").hide();
		regStyle(styleDtlData.formdata);
	},
	'closePop' : function(o,tnt_seq){
		//$("div[name='tnt-reg-popup2']").hide();
	}
});
var shCoupon = function(){

	getDataList(1,10);

}

var regStyle = function(data){
	
	if(!validation(data)){
		return false;
	}
	var bcn_cd = getUriArrVal()[1];
	var url = "/rest/01/regFloorBlock";
	//if(getU
	if($("#formtitl").text()=="수정")
	{
		url = "/rest/modifyFloorBlock";
	}else{
		url = "/rest/regFloorBlock";
	}

	Common.REST.post(window.location.origin + url, data, function(data) {
		if($("#formtitl").text()=="수정")
		{
			alert('수정완료!!');  
		}else{
			alert('등록완료!!');  
		}

		getDataList(1, 10);
		//$("div[name='tnt-reg-popup2']").hide();
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
} 

var deleteFloorBlock = function(bcn_cd, fb_seq){
	//var bcn_cd = getUriArrVal()[1];
	//alert(fb_seq);
	var url = "/rest/deleteFloorBlock";
	url += '?';
	url += 'sh_bcn_cd=' + bcn_cd;
	url += '&sh_fb_seq=' + fb_seq;
	Common.REST.get(window.location.origin + url, {}, function(data) {

		alert('삭제완료!!');  
		getDataList(1, 10);
		//$("div[name='tnt-reg-popup2']").hide();
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}
var deleteAllFloorBlock = function(bcn_cd, fl){
	var url = "/rest/deleteAllFloorBlock";
	url += '?';
	url += 'sh_bcn_cd=' + bcn_cd;
	url += '&sh_fl=' + fl;
	Common.REST.get(window.location.origin + url, {}, function(data) {

		alert('삭제완료!!');  
		getDataList(1, 10);
		//$("div[name='tnt-reg-popup2']").hide();
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
	if(data.point_cord == null || data.point_cord == '') {
		alert("좌표(PointCord)를 입력 하세요.");
		return false;
	}
	
	return true;
}

var getCommonBcnList = function() {

	var restUri = window.location.origin + '/rest/getAllBcns';
	
	Common.REST.get(restUri, {}, function(data) {
		commonData.commonFilter.bcnList = data.list;
		commonData.commonFilter.sh_bcn_cd = getCookie("bcn_cd");
		rtvCommon.set(commonData);
		//getDataList(1, 10);
		getBcnFloorList();
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};


var getBcnFloorList = function() {
	var bcn_cd = $("#sh_bcn_cd").val();
	console.log("bcn_cd>>"+bcn_cd);
	var restUri = window.location.origin + '/rest/getBcnFloors';
	restUri += '?';
	restUri += 'sh_bcn_cd=' + bcn_cd; 
	Common.REST.get(restUri, {}, function(data) {
		styleData.couponFilter.floorList = data.list;
		//styleData.couponFilter.sh_fl="0";
		rtvCoupon.set(styleData);
		//$('#sh_fl option[value=0]').attr('selected', 'selected');
		getDataList(1, 10);
		//$("#sh_fl").val("0").prop("selected", true);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};
var getDataList = function(offset, limit,sortColumn) {

	var offset = offset || 1;  
	var limit = limit || 10;
	bcn_cd = $("#sh_bcn_cd").val();
	
	setCookie( "bcn_cd", commonData.commonFilter.sh_bcn_cd, 1 );
	var restUri = window.location.origin + '/rest/getFloorBlocks';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_bcn_cd=' + commonData.commonFilter.sh_bcn_cd;
	restUri += '&sh_fl=' + styleData.couponFilter.sh_fl;
	restUri += '&sh_room_num=' + styleData.couponFilter.sh_room_num;
	
	Common.REST.get(restUri, {}, function(data) {
		styleData.styleList = data.list; 
		styleData.paging = data.paging; 
 
		styleData.paging.pages = [];
		 
		for(var  i = styleData.paging.page_start; i <= styleData.paging.page_end; ++i) {
			styleData.paging.pages.push(i);
        }
		//alert(data.empList[0].empno);
		rtvCoupon.set(styleData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getFloorBlock = function(bcn_cd, fb_seq) {
	 console.log(bcn_cd +"," + fb_seq);
	//var bcn_cd = getUriArrVal()[1];
	var restUri = window.location.origin + '/rest/getFloorBlock';
	restUri += '?'; 
	restUri += 'sh_bcn_cd=' + bcn_cd;
	restUri += '&sh_fb_seq=' + fb_seq;
	
	Common.REST.get(restUri, {}, function(data) {
		//alert(data.style_nm);
		//styleData.formdata = data.list;
		styleDtlData.formdata = data;

		//alert(data.empList[0].empno);
		rtvCoupon2.set(styleDtlData);
		/*
		$("#fb_seq").val(data.fb_seq);
		*/
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};
 
$(function() {
	getCommonBcnList();

	//getDataList(1, 10);

	//rtvCoupon.set(styleData);
	//파일 등록 처리	
});


