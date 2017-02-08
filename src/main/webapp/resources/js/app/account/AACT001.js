
////////////////////////////////////////////////////////
// global variables
/*var returnURI = aaa();*/
var loginData  = {
		  adm_id : null
		, adm_pw : null
};


////////////////////////////////////////////////////////
// global functions
/*var aaa = function() {
	var returnValue = "";
	 
	if(returnURI.attributes[3].nodeValue != null || returnURI.attributes[3].nodeValue === "") {
		returnValue = returnURI.attributes[3].nodeValue;
	}
	
	return returnValue;
} 
*/

////////////////////////////////////////////////////////
//initialize

var rtvLogin = new Ractive({
	el : '#login',
	template : '#tmpl-login-form',
	data : loginData
});


rtvLogin.on({
	
	'do-login' : function() {
		_doLogin();
	},
	
	'on-keydown' : function(evt) {
		
		if(evt.original.keyCode == 13) {
			_doLogin();
		}
	}
});


function _doLogin() {
	
	if(!loginData.adm_id) {
		
		alert("아이디를 입력해주세요.");
		$('.adm_id').focus();
		return false;
		
	} else if(!loginData.adm_pw) {
		
		alert("패스워드를 입력해주세요.");
		$('.adm_pw').focus();
		return false;
	}
	
    var params = {
	    adm_id : loginData.adm_id,
	    adm_pw : loginData.adm_pw
    };
    
    
    messageRouter.trigger('show-popup-loading');
    Common.REST.post('/admin/rest/auth/login', params, function(data) {
		
    	if(data && data.code) {
    		
    		var bcn_cd = '01';
    		
    		switch(data.code) {
    			case 101:
    				window.location.href = '/';
    				break;
	    		case 102:
	    			alert('계정이 유효하지 않습니다.\n아이디 또는 비밀번호를 확인하세요.');
	    			break;
	    		case 103:
	    			alert('사용중지된 계정입니다.\n관리자에게 담당 문의하세요.');
	    			break;
	    		case 104:
	    			alert('비밀번호 3회 입력오류로 계정이 정지되었습니다.\n담당 관리자에게 비밀번호 초기화를 요청하여\n새로운 비밀번호를 발급 받으시기 바랍니다.');
	    			break;
	    		case 105:
	    			alert('계정이 유효하지 않습니다.\n아이디 또는 비밀번호를 확인하세요.');
	    			break;
	    		case 106:
	    			alert('비밀번호 미변경 3개월 이상 계정입니다.');
	    			window.location.href = '/' + bcn_cd + '/account/password/self';
	    			break;
	    		case 107:
	    			alert('회원정보 미등록 상태입니다.\n회원정보를 완료해주세요.');
	    			window.location.href = '/' + bcn_cd + '/account/self';
	    			break;
	    		default:
	    			alert('계정이 유효하지 않습니다.\n관리자에게 담당 문의하세요.');
    		}
    	} else {
    		alert('계정이 유효하지 않습니다.\n관리자에게 담당 문의하세요.');
    	}
    	
    	messageRouter.trigger('hide-popup-loading');
				
	}, function(data) {
		console.log(' 로그인 자체가 실패 data is ', data);
		alert('계정이 유효하지 않습니다.\n관리자에게 담당 문의하세요.');
		messageRouter.trigger('hide-popup-loading');
	});
}
