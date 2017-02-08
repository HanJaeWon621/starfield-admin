
////////////////////////////////////////////////////////
// global variables
var newAccountData  = {
		
    adm_id : null,
    adm_pw : null,
    role_seq : null,
    check_overlap : false,
    roles : [],
    			
	getYYYYMMDD : function(timestamp, _type) {
		
		try {
			
			if(!timestamp) {
				return null;
			}
			
			var date = new Date(timestamp);
		    var type = _type || '.';
		
		    var arr = [];
		    arr.push(date.getFullYear());
		    arr.push(( (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1) ));
		    arr.push(( date.getDate() < 10 ? '0' + date.getDate() : date.getDate()));
		
		    return arr.join(type);
			
		} catch(e) {
			return null;
		}
	}
		
};


////////////////////////////////////////////////////////
// global functions

////////////////////////////////////////////////////////
//initialize

var rtvAccountReg = new Ractive({
	el : '#account-reg',
	template : '#tmpl-account-reg',
	data : newAccountData
});

rtvAccountReg.on({
	
	'check-overlap-id' : function() {
		_checkOverlapAdminId()
	},
	
	'on-keydown-check-overlap' : function(evt) {
		
		if(evt.original.keyCode == 13) {
			_checkOverlapAdminId();
		}
	},
	
	'do-cancel' : function() {
		
		var r = confirm('계정 생성을 하지 않고 이전으로 돌아갑니다.\n취소하시겠습니까?');
		if (r == true) {
			history.back();
		}
	},
	
	'make-temp-password' : function() {
		
		Common.REST.post('/admin/rest/account/password/temp', null, function(data) {
			
			if(data && data.code == 1) {
				
				newAccountData.adm_pw = data.desc;
				rtvAccountReg.set(newAccountData);
				
			} else {
				alert('임시비밀번호를 생성하는데 실패하였습니다.\n재시도 이후에도 발생시 담당 관리자에게 문의하세요.');
			}
			
		}, function(data) {
			
			console.log('fail data is ', data);
			alert('임시비밀번호를 생성하는데 실패하였습니다.\n재시도 이후에도 발생시 담당 관리자에게 문의하세요.');
		});
		
	},
	
	'reg-account' : function() {
		
		// 관리자 생성
		if(!newAccountData.adm_id) {
			
			alert('아이디를 입력하세요.');
			$('input[type=text]:eq(0)').focus();
			return false;
			
		} else if(newAccountData.adm_id.length < 4 || newAccountData.adm_id.length > 15) { 
			
			alert('아이디는 4자이상 15자 이하로 입력하세요.');
			$('input[type=text]:eq(0)').focus();
			return false;
			
		} else if(!newAccountData.adm_pw) {
			
			alert('임시 비밀번호를 생성하세요.');
			$('.text-input:eq(1)').focus();
			return false;
			
		} else if(!newAccountData.check_overlap) {
			
			alert('아이디 중복 검사를 하세요.');
			$('.text-input:eq(1)').focus();
			return false;
		}
		
		var r = confirm('입력한 내용으로 계정생성을 하시겠습니까?');
		if (r == false) {
			return false;
		}
		
		var params = {};
		params.adm_id = newAccountData.adm_id;
		params.adm_pw = newAccountData.adm_pw;
		params.role_seq = newAccountData.role_seq;
		
		Common.REST.post('/admin/rest/account/admins', params, function(data) {
			
			console.log('success!! data is ', data);
			
			if(data && data.code == 1) {
				
				window.location.href = '/01/account/admins';
				
			} else {
				alert('관리자 계정을 생성하는데 실패하였습니다.\n재시도 이후에도 발생시 담당 관리자에게 문의하세요.');
			}
			
		}, function(data) {
			
			console.log('fail data is ', data);
			alert('관리자 계정을 생성하는데 실패하였습니다.\n재시도 이후에도 발생시 담당 관리자에게 문의하세요.');
		});
	}

});


rtvAccountReg.observe({
	
	// 관리자 아이디 변경시 중복 체크 여부를 false로 바꾼다.
	'adm_id' : function(newValue, oldValue, keypath) {
		
		if(newAccountData.check_overlap && newValue != oldValue) {
			newAccountData.check_overlap = false;
			rtvAccountReg.set(newAccountData);
		}
	}

});




// 관리자 아이디 중복 체크
function _checkOverlapAdminId() {
	
	if(!newAccountData.adm_id) {
		alert('아이디를 입력하세요.');
		$('input[type=text]:eq(0)').focus();
		return false;
	}
	
	Common.REST.get('/admin/rest/account/has/admin?adm_id=' + newAccountData.adm_id, null, function(data) {
		
		if(data && data.code) {
			
			switch(data.code) {
				case 1:
					alert('사용 가능한 ID입니다.');
					newAccountData.check_overlap = true;
					break;
				case 2:
					alert('이미 사용하고 있는 ID입니다.');
					newAccountData.check_overlap = false;
					break;
			}
		}
		
	}, function(data) {
		
		console.log('fail data is ', data);
		alert('아이디 중복 체크 오류\n담당 관리자에게 문의하세요.');
	});
}

/**
 * 관리자 역할 정보 가져오기
 */
function getRoles() {
	
	Common.REST.get('/admin/rest/auth/roles', null, function(data) {
		
		console.log('success data is ', data);
		
		if(data instanceof Array) {
			
			newAccountData.roles = data;
			rtvAccountReg.set(newAccountData);
			
		} else {
			
			console.log(' 관리자 역할 가져오기 오류.', data);
			alert('정보를 가져오는 도중 문제가 발생되었습니다.\n담당 관리자에게 문의하세요');
			history.back();
		}
		
	
	}, function(data) {
		console.log(' 관리자 권한정보 가져오기 오류.', data);
		alert('정보를 가져오는 도중 문제가 발생되었습니다.\n담당 관리자에게 문의하세요');
		history.back();
	});
	
}

$(function() {
	
	// 관리자 역할 정보 가져오기
	getRoles();
});

