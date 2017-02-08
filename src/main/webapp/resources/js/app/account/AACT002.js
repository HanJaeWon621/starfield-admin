
////////////////////////////////////////////////////////
// global variables
var passData  = {
		  org_pw : null
		, new_pw : null
		, re_new_pw : null
};


////////////////////////////////////////////////////////
// global functions

////////////////////////////////////////////////////////
//initialize

var rtvPass = new Ractive({
	el : '#admin-pw',
	template : '#tmpl-change-pass-form',
	data : passData
});

rtvPass.on({
	
	'do-cancel' : function() {
		
		var r = confirm('입력/수정 정보를 저장하지 않고 종료합니다.\n종료하시겠습니까?');
		if (r == true) {
			window.history.back();
		}
	},
	
	'on-keydown' : function(evt) {
		
		if(evt.original.keyCode == 13) {
			_doSave();
		}
//		evt.original.preventDefault();
	},
	
	'do-save' : function() {
		_doSave();
	}
});


function _doSave() {
	
	if(!passData.org_pw) {
		alert("기존 비밀번호를 입력해주세요.");
		$('.text-input-wrap input:eq(0)').focus();
		return false;
	} else if(!passData.new_pw) {
		alert("새로운 비밀번호를 입력해주세요.");
		$('.text-input-wrap input:eq(1)').focus();
		return false;
	} else if(!passData.re_new_pw) {
		alert("비밀번호 재확인을 입력해주세요.");
		$('.text-input-wrap input:eq(2)').focus();
		return false;
	} else if(passData.new_pw != passData.re_new_pw) {
		alert("새로운 비밀번호와 재확인 비밀번호가 동일하지 않습니다.");
		$('.text-input-wrap input:eq(2)').focus();
		return false;
	}
	
	// 특수 패스워드 유효성 검사
	valiPass(passData.new_pw, function(err) {
		
		if(err) {
			switch(err.errCd) {
				case 'VER01':
					alert("6자리 ~ 12자리 이내로 입력해주세요.");
					return false;
					break;
				case 'VER02':
					alert("비밀번호는 공백없이 입력해주세요.");
					return false;
					break;
				case 'VER03':
					alert("영문,숫자, 특수문자를 혼합하여 입력해주세요.");
					return false;
					break;
				case 'VER04':
					alert("비밀번호에 3자 이상의 연속 또는 반복 문자 및 숫자를 사용하실 수 없습니다.");
					return false;
					break;
				default:
					alert("비밀번호는 6자리 ~ 12자리 이내, 공백없이 영문,숫자, 특수문자를 혼합하여 입력해주세요.");
					return false;
			}
		}
		
		var params = {
		    adm_pw : passData.org_pw,
		    adm_new_pw : passData.new_pw,
		    bcn_cd : $('#branch-selector').val()
		};
		
		messageRouter.trigger('show-popup-loading');
		
		Common.REST.put('/admin/rest/account/password/self', params, function(data) {
			
	    	if(data && data.code) {
	    		
	    		switch(data.code) {
	    			case 101:
	    				alert('비밀번호 변경에 성공하였습니다.\n재로그인해주세요.');
	    				window.location.href = '/logout';
	    				break;
		    		case 102:
		    			alert('로그인 계정이 유효하지 않습니다.\n담당 관리자에게 문의하세요');
		    			window.location.href = '/logout';
		    			break;
		    		case 103:
		    			alert('기존 비밀번호 입력오류\n비밀번호를 확인하세요.');
		    			break;
		    		case 104:
		    			alert('기존 비밀번호화 신규비밀번호가 동일합니다.\n신규 비밀번호를 다시 작성해주세요.');
		    			break;
		    		case 105:
		    			alert('신규비밀번호가 3개월내 패스워드와 동일합니다.\n신규 비밀번호를 다시 작성해주세요.');
		    			break;
		    		default:
		    			alert('로그인 계정이 유효하지 않습니다.\n담당 관리자에게 문의하세요');
		    			window.location.href = '/logout';
	    		}
	    	} else {
	    		alert('로그인 계정이 유효하지 않습니다.\n담당 관리자에게 문의하세요');
	    		window.location.href = '/logout';
	    	}
	    	messageRouter.trigger('hide-popup-loading');
			
    	}, function(data) {
    		console.log(' 비밀번호 변경 자체가 실패 data is ', data);
    		alert('로그인 계정이 유효하지 않습니다.\n담당 관리자에게 문의하세요');
    		window.location.href = '/logout';
    	});
		
	});
	
}


function valiPass(pass, cb) {
	
	var isNumExist = pass.search(/[0-9]/g);
	var isNngExist = pass.search(/[a-z]/ig);
	var isSpeExist = pass.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
	
	 // 자릿수 체크 6~12자리
	 if(pass.length < 6 || pass.length > 12) { 
		 cb({errCd : 'VER01'}, null);
		 return false;
	 }
	 
	 // 공백여부 확인
	 if(pass.search(/₩s/) != -1) {
		 cb({errCd : 'VER02'}, null);
		 return false;
	 }
	 
	 // 숫자, 영문, 특수문자 존재 여부
	 if(isNumExist < 0 || isNngExist < 0 || isSpeExist < 0 ) {
		 cb({errCd : 'VER03'}, null);
		 return false; 
	 }
	 
	// 동일한 문자/숫자 3이상
	if( /(\w)\1\1/.test(pass) == true || /([0-9])\1\1/.test(pass) == true ){
	
		cb({errCd : 'VER04'}, null);
		return false;
		
	}
		
	// 연속된 숫자
	for(i=0; i<pass.length; i++){
		var count = 3;
		var splitText = pass.substr(i,count);
		
		if( splitText.length > count-1 ){
			
			var tempText = splitText.charCodeAt(0);
			var incCharText = String.fromCharCode(tempText, tempText+1, tempText+2);
			var decCharText = String.fromCharCode(tempText, tempText-1, tempText-2);
			
			if( splitText == incCharText || splitText == decCharText ){
				
				cb({errCd : 'VER04'}, null);
				return false;
			}
		}
	}
		
	 cb(null);
}
