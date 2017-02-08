
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



var rtvCoupon2 = new Ractive({
	el : '#style-dtl',
	template : '#tmpl-style-dtl',
	data : styleDtlData
}); 


//getFloorBlock(bcn_cd, fb_seq);
//$("#tnt-reg-popup2").show();
rtvCoupon2.on({ 
	'regSave' : function(o) {//등록/수정
		//$("#delBtn").hide();
		regStyle(styleDtlData.formdata);
	},'close' : function(o){
		self.close();
	}
});

var regStyle = function(data){
	
	if(!validation(data)){
		return false;
	}
	var bcn_cd = getUriArrVal()[1];
	var url = "/rest/01/regFloorBlock";
	//if(getU
	
	url = "/rest/modifyFloorBlock";
	

	Common.REST.post(window.location.origin + url, data, function(data) {
		
		alert('수정완료!!');  
		self.close();
		
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



var getFloorBlock = function(bcn_cd, fb_seq) {
	 console.log(bcn_cd +"," + fb_seq);
	//var bcn_cd = getUriArrVal()[1];
	var restUri = window.location.origin + '/rest/getFloorBlock';
	restUri += '?'; 
	restUri += 'sh_bcn_cd=1';
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
	getFloorBlock('1', getUriArrVal()[3]);
	//getDataList(1, 10);

	//rtvCoupon.set(styleData);
	//파일 등록 처리	
});


