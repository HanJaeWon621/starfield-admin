
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
var rtvCommon = new Ractive({
	el : '#common-list',
	template : '#tmpl-common-list',
	data : commonData
});
var styleData  = {
	styleList : null,
	paging : {},
	couponFilter : {
		bcnList : null,	
		sh_bcn_cd : '',
		sh_style_nm : '',
		sh_style_set_seq : ''
	},
	formdata : {
		style_set_seq : null,
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
			bcn_cd : '',
			style_set_seq : null,
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
		shCoupon();
	}
});
rtvCoupon.on({
	'pageMove' : function(o, page) {
		if(page > 0 
				&& page <= styleData.paging.total_page_cnt
				&& page != styleData.paging.cur_page) {

			var offset = (page - 1) * styleData.paging.list_limit + 1; 
			getCouponList(offset, styleData.paging.list_limit);
		}
		
	}, 
	'shCoupon' : function(){
		shCoupon();
	},
	'makeMapStyleFile' : function(o) {//MakeMap파일 생성
		var bcn_cd = commonData.commonFilter.sh_bcn_cd;
		makeMapStyleFile(bcn_cd);
	},
	'deleteStyle' : function(o, style_set_seq) {//삭제
		//styleDtlData.formdata.style_set_seq=style_set_seq;
		deleteStyle(style_set_seq);
	},
	'openPop' : function(o,mode, style_set_seq){
		if(mode=='Edit'){
			getStyleSetDtl(style_set_seq);
			$("#formtitl").text("스타일수정");
		}else{
			$("#formtitl").text("스타일등록");
			styleDtlData.formdata.style_set_seq='';
			styleDtlData.formdata.style_nm='';
			styleDtlData.formdata.mouse_state='';
			styleDtlData.formdata.style_comment='';
			styleDtlData.formdata.bg_color='';
			styleDtlData.formdata.bd_size='';
			styleDtlData.formdata.bd_color='';
			styleDtlData.formdata.important_cd='N';
			styleDtlData.formdata.ext_style='';
			styleDtlData.formdata.bcn_cd = commonData.commonFilter.sh_bcn_cd;
			rtvCoupon2.set(styleDtlData);
		}
		//$("#tnt-reg-popup2").show();
	}
});

rtvCoupon2.on({ 
	'regStyle' : function(o) {//등록/수정
		regStyle(styleDtlData.formdata);
	},
	'closePop' : function(o,tnt_seq){
		
	}
});
var shCoupon = function(){

	getDataList(1,10);

}

var regStyle = function(data){
	
	if(!validation(data)){
		return false;
	}
	//alert($("#bg_color").val());
	styleDtlData.formdata.bg_color=$("#bg_color").val();
	styleDtlData.formdata.bd_color=$("#bd_color").val();
	rtvCoupon.set(styleData);
	var url = "/rest/regStyleSet";
	//if(getU
	if($("#formtitl").text()=="스타일수정")
	{
		url = "/rest/modifyStyleSet";
	}else{
		url = "/rest/regStyleSet";
	}

	Common.REST.post(window.location.origin + url, data, function(data) {
		if($("#formtitl").text()=="스타일수정")
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

var deleteStyle = function(style_set_seq){
	
	var restUri = window.location.origin + "/rest/deleteStyleSet";
	restUri += '?';
	restUri += 'sh_style_set_seq=' + style_set_seq; 
	Common.REST.get(restUri, {}, function(data) {

		alert('삭제완료!!');  
		getDataList(1, 10);
		
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}

var makeMapStyleFile = function(bcn_cd){
	
	var restUri = window.location.origin + "/rest/makeMapStyleFile";
	restUri += '?';
	restUri += 'sh_bcn_cd=' + bcn_cd; 
	Common.REST.get(restUri, {}, function(data) {

		alert('스타일파일 생성완료!!');  
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}
var validation = function(data){ 
	if(data.style_nm == null || data.style_nm == '') {
		alert("스타일 이름을 입력 하세요.");
		return false;
	}
	
	if(data.style_comment == null || data.style_comment == '') {
		alert("스타일 설명을 입력 하세요.");
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
		getDataList(1, 10);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getDataList = function(offset, limit,sortColumn) {

	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/getStyleSets';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_bcn_cd=' + commonData.commonFilter.sh_bcn_cd;
	restUri += '&sh_style_nm=' + styleData.couponFilter.sh_style_nm;
	setCookie( "bcn_cd", commonData.commonFilter.sh_bcn_cd, 1 );
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

var getStyleSetDtl = function(style_set_seq) {
	console.log(style_set_seq);
	var restUri = window.location.origin + '/rest/getStyleSet';
	//alert(restUri);
	restUri += '?';
	restUri += 'sh_bcn_cd=' + commonData.commonFilter.sh_bcn_cd;
	restUri += '&sh_style_set_seq=' + style_set_seq; 
	setCookie( "bcn_cd", commonData.commonFilter.sh_bcn_cd, 1 );
	Common.REST.get(restUri, {}, function(data) {
		//alert(data.style_nm);
		//styleData.formdata = data.list;
		styleDtlData.formdata = data;
		//$("#bg_color").val(data.bg_color);
		//alert(data.empList[0].empno);
		rtvCoupon2.set(styleDtlData);
		$("#bg_color_out").html('<i style="background-color: rgb('+hexToR(data.bg_color) +','+ hexToG(data.bg_color)+','+ hexToB(data.bg_color)+');"></i>');
		$("#bd_color_out").html('<i style="background-color: rgb('+hexToR(data.bd_color) +','+ hexToG(data.bd_color)+','+ hexToB(data.bd_color)+');"></i>');
		//$('.demo2').setValue(data.bg_color);
		//$('.demo2').colorpicker();
		
		
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

function hexToR(h) {return parseInt((cutHex(h)).substring(0,2),16)}
function hexToG(h) {return parseInt((cutHex(h)).substring(2,4),16)}
function hexToB(h) {return parseInt((cutHex(h)).substring(4,6),16)}
function cutHex(h) {return (h.charAt(0)=="#") ? h.substring(1,7):h}
$(function() {
	getCommonBcnList();
	$('.demo2').colorpicker();
	$('.bd_color').colorpicker();
	
	
	//getCouponList(1, 10);
	//달력  
	
	
});


