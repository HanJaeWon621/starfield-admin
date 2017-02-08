
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
		sh_bcn_nm : ''
	},
	formdata : {
		bcn_seq : null,
		bcn_cd : null,
		bcn_nm : null,
		bcn_addr : null,
		bcn_tel : null
	}
};

var styleDtlData  = {
		formdata : {
			bcn_seq : null,
			bcn_cd : null,
			bcn_nm : null,
			bcn_addr : null,
			bcn_tel : null
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
		setCookie( "bcn_cd", commonData.commonFilter.sh_bcn_cd, 1 );
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
	
	'saveMapFile' : function(o,bcn_cd) {//데이터생성
		//alert(bcn_cd);
		//return;
		saveMapFile(bcn_cd);
	},
	'makeMapFile' : function(o,bcn_cd) {//데이터배포
		//alert(bcn_cd);
		//return;
		makeMapFile(bcn_cd);
	}
});

rtvCoupon2.on({ 
	'regSave' : function(o) {//등록/수정
		regSave(styleDtlData.formdata);
	},
	'closePop' : function(o,tnt_seq){
		
	}
});
var shCoupon = function(){

	getCouponList(1,10);

}

var regSave = function(data){
	
	if(!validation(data)){
		return false;
	}
	var bcn_cd = getUriArrVal()[1];
	var url = "";
	//if(getU
	if($("#formtitl").text()=="수정")
	{
		url = "/rest/modifyBcn";
	}else{
		url = "/rest/regBcn";
	}

	Common.REST.post(window.location.origin + url, data, function(data) {
		if($("#formtitl").text()=="수정")
		{
			alert('수정완료!!');  
		}else{
			if(data.code=-1){
				alert('이미 등록된 지점코드입니다.');
				return;
			}else{
				alert('등록완료!!');	
			}
			  
		}

		getCouponList(1, 10);
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
} 
//데이터 생성
var saveMapFile = function(bcn_cd){
	console.log("bcn_cd>>"+bcn_cd);
	var url = "/rest/saveMapFile";
	url += '?';
	url += 'sh_bcn_cd=' + bcn_cd;
	Common.REST.get(window.location.origin + url, {}, function(data) {
		alert('맵메타데이터 저장 완료!!');  
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}
//데이터배포
var makeMapFile = function(bcn_cd){
	console.log("bcn_cd>>"+bcn_cd);
	var url = "/rest/makeMapFile";
	url += '?';
	url += 'sh_bcn_cd=' + bcn_cd;
	Common.REST.get(window.location.origin + url, {}, function(data) {
		alert('맵파일 생성완료!!');  
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}
var getCommonBcnList = function() {

	var restUri = window.location.origin + '/rest/getAllBcns';
	
	Common.REST.get(restUri, {}, function(data) {
		commonData.commonFilter.bcnList = data.list;
		commonData.commonFilter.sh_bcn_cd = getCookie("bcn_cd");
		rtvCommon.set(commonData);
		//getDataList(1, 10);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};
var getCouponList = function(offset, limit,sortColumn) {
	var offset = offset || 1;  
	var limit = limit || 10;
	//var bcn_cd = getUriArrVal()[1];
	var restUri = window.location.origin + '/rest/getBcns';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	
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

var getBcn = function(bcn_seq) {
	//alert(bcn_seq);
	//var bcn_cd = getUriArrVal()[1];
	var restUri = window.location.origin + '/rest/getBcn';
	//alert(restUri);
	restUri += '?';
	restUri += 'bcn_seq=' + bcn_seq; 
	
	Common.REST.get(restUri, {}, function(data) {
		styleDtlData.formdata = data;
		rtvCoupon2.set(styleDtlData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};
function hello(bcn_cd){
	alert(bcn_cd);
}
 
$(function() {
	getCommonBcnList();
	getCouponList(1, 10);
	//달력   
	rtvCoupon.set(styleData);
});


