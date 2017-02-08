
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
		sh_div_cd:''	
	},
	formdata : {
		tenant_poi_seq : null,
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
			tenant_poi_seq : null,
			tenant_poi_nm : null,
			icon : null,
			room_num : null,
			x_cord : null,
			y_cord : null,
			div_cd : null,
			poi_type : null,
			fl : null,
			fl_seq : null,
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
			getCouponList(offset, styleData.paging.list_limit);
		}
		
	}, 
	'shCoupon' : function(){
		shCoupon();
	},
	'makeMapFile' : function(o) {//등록/수정
		//$("#delBtn").show();
		//alert(tenant_poi_seq);
		//styleDtlData.formdata.tenant_poi_seq=tenant_poi_seq;
		makeMapFile(styleDtlData.formdata);
	}
	,
	'deleteTenantPoi' : function(o, bcn_cd, tenant_poi_seq) {//등록/수정
		//$("#delBtn").show();
		//alert(tenant_poi_seq);
		//styleDtlData.formdata.tenant_poi_seq=tenant_poi_seq;
		deleteTenantPoi(bcn_cd, tenant_poi_seq);
	},
	'openPop' : function(o,mode, bcn_cd, tenant_poi_seq){
		//alert(tenant_poi_seq);
		console.log("aa"+tenant_poi_seq+"bb");
		if(mode=='Edit'){
			getTenantPoi(bcn_cd, tenant_poi_seq);
			$("#formtitl").text("수정");
		}else{			
			styleDtlData.formdata.tenant_poi_seq='';
			styleDtlData.formdata.tenant_poi_nm='';
			styleDtlData.formdata.icon='';
			styleDtlData.formdata.x_cord='';
			styleDtlData.formdata.y_cord='';
			styleDtlData.formdata.div_cd='T';
			styleDtlData.formdata.poi_type='';
			styleDtlData.formdata.bcn_cd=$("#sh_bcn_cd").val();
			styleDtlData.formdata.fl=$("#sh_fl").val();
			rtvCoupon2.set(styleDtlData);
			$("#formtitl").text("등록");
		}
		//$("#tnt-reg-popup2").show();
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
var shCoupon = function(){

	getCouponList(1,10);

}

var regStyle = function(data){
	
	if(!validation(data)){
		return false;
	}
	
	var url = "/rest/01/regTenantPoi";
	//if(getU
	var bcn_cd = getUriArrVal()[1];
	if($("#formtitl").text()=="수정")
	{
		url = "/rest/modifyTenantPoi";
	}else{
		url = "/rest/regTenantPoi";
	}

	Common.REST.post(window.location.origin + url, data, function(data) {
		if($("#formtitl").text()=="수정")
		{
			alert('수정완료!!');  
		}else{
			alert('등록완료!!');  
		}

		getCouponList(1, 10);
		$("div[name='tnt-reg-popup2']").hide();
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
} 

var deleteTenantPoi = function(bcn_cd, tenant_poi_seq){
	//var bcn_cd = getUriArrVal()[1];
	var url = "/rest/deleteTenantPoi";
	url += '?';
	url += 'sh_bcn_cd=' + bcn_cd;
	url += '&sh_tenant_poi_seq=' + tenant_poi_seq; 
	Common.REST.post(window.location.origin + url, data, function(data) {

		alert('삭제완료!!');  
		getCouponList(1, 10);
		$("div[name='tnt-reg-popup2']").hide();
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}
var makeMapFile = function(data){
	var bcn_cd = getUriArrVal()[1];
	var url = "/rest/"+bcn_cd +"/makeMapFile";

	Common.REST.post(window.location.origin + url, data, function(data) {
		alert('맵파일 생성완료!!');  
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
	
	if(data.point_cord == null || data.point_cord == '') {
		alert("좌표(PointCord)를 입력 하세요.");
		return false;
	}
	*/
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
	
	setCookie( "bcn_cd", commonData.commonFilter.sh_bcn_cd, 1 );
	Common.REST.get(restUri, {}, function(data) {
		styleData.couponFilter.floorList = data.list;
		//styleData.couponFilter.sh_fl="0";
		rtvCoupon.set(styleData);
		//$('#sh_fl option[value=0]').attr('selected', 'selected');
		getCouponList(1, 10);
		//$("#sh_fl").val("0").prop("selected", true);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};
var getCouponList = function(offset, limit,sortColumn) {

	var offset = offset || 1;  
	var limit = limit || 10;
	var bcn_cd = getUriArrVal()[1];
	var restUri = window.location.origin + '/rest/getTenantPois';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_fl=' + styleData.couponFilter.sh_fl;
	restUri += '&sh_bcn_cd=' + commonData.commonFilter.sh_bcn_cd;
	restUri += '&sh_div_cd=' + styleData.couponFilter.sh_div_cd;
	
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

var getTenantPoi = function(bcn_cd, tenant_poi_seq) {
	console.log(tenant_poi_seq);
	//var bcn_cd = styleData.couponFilter.sh_bcn_cd;
	setCookie( "bcn_cd", commonData.commonFilter.sh_bcn_cd, 1 );
	var restUri = window.location.origin + '/rest/getTenantPoi';
	//alert(restUri);
	restUri += '?';
	restUri += 'sh_bcn_cd=' + bcn_cd;
	restUri += '&sh_tenant_poi_seq=' + tenant_poi_seq; 
	
	Common.REST.get(restUri, {}, function(data) {
		//alert(data.style_nm);
		//styleData.formdata = data.list;
		styleDtlData.formdata = data;

		//alert(data.empList[0].empno);
		rtvCoupon2.set(styleDtlData);
		/*
		$("#tenant_poi_seq").val(data.tenant_poi_seq);
		*/
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};


 
$(function() {
	getCommonBcnList();
	//getCouponList(1, 10);
	
});


