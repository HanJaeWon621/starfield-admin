
////////////////////////////////////////////////////////
// global variables
var selfData  = {
		
    adm_id : null, 
    adm_dept : null, 
    adm_rank : null, 
    adm_nm : null, 
    role_desc : null,
    adm_tel_num1 : null, 
    adm_tel_num2 : null, 
    adm_tel_num3 : null, 
    adm_cel_num1 : null, 
    adm_cel_num2 : null, 
    adm_cel_num3 : null,
    adm_email : null,
    term_agre_yn : null, 
    adm_acut_act_yn : null, 
    term_agre_date : null,
				
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

var rtvSelf = new Ractive({
	el : '#admin-info',
	template : '#tmpl-admin-info-form',
	data : selfData
});

rtvSelf.on({
	
	'cancel-change' : function() {
		
		var r = confirm('입력/수정 정보를 저장하지 않고 종료합니다.\n종료하시겠습니까?');
		if (r == true) {
			window.location.href = '/';
		}
	},
	
	'move-change-password' : function() {
	
		var bcn_cd = $('#branch-selector').val();
		
		window.location.href = '/' + bcn_cd + '/account/password/self';
	},
	
	'on-keydown' : function(evt) {
		
		if(evt.original.keyCode == 13) {
			
			var r = confirm('입력한 내용으로 저장을 하시겠습니까?');
			if (r == true) {
				_doSave();
			}
		}
	},
	
	'do-save' : function() {
		var r = confirm('입력한 내용으로 저장을 하시겠습니까?');
		if (r == true) {
			_doSave();
		}
	}
});


/**
 * 관리자 정보 얻어 오기
 */
function getAdminInfo() {
	
	Common.REST.get('/admin/rest/account/self', null, function(data) {
		
		selfData.adm_id = data.adm_id;
		selfData.adm_dept = data.adm_dept;
		selfData.adm_rank = data.adm_rank;
		selfData.adm_nm = data.adm_nm;
		selfData.role_desc = data.role_desc;
		selfData.adm_tel_num1 = data.adm_tel_num1;
		selfData.adm_tel_num2 = data.adm_tel_num2;
		selfData.adm_tel_num3 = data.adm_tel_num3;
		selfData.adm_cel_num1 = data.adm_cel_num1;
		selfData.adm_cel_num2 = data.adm_cel_num2;
		selfData.adm_cel_num3 = data.adm_cel_num3;
		selfData.adm_email = data.adm_email;
		selfData.term_agre_yn = data.term_agre_yn;
		selfData.adm_acut_act_yn = data.adm_acut_act_yn;
		selfData.term_agre_date= data.term_agre_date;
		
		rtvSelf.set(selfData);
		
	}, function(data) {
		console.log(' 관리자 계정 가져오기 에러 발생!! ', data);
		alert('정보를 가져오는 도중 문제가 발생되었습니다.\n담당 관리자에게 문의하세요');
//		window.location.href = '/login';
	});
	
}


function _doSave() {
	
	var params = {};
	
	params.adm_nm = selfData.adm_nm;
	params.adm_dept = selfData.adm_dept;
	params.adm_tel_num1 = selfData.adm_tel_num1;
	params.adm_tel_num2 = selfData.adm_tel_num2;
	params.adm_tel_num3 = selfData.adm_tel_num3;
	params.adm_cel_num1 = selfData.adm_cel_num1;
	params.adm_cel_num2 = selfData.adm_cel_num2;
	params.adm_cel_num3 = selfData.adm_cel_num3;
	params.adm_email = selfData.adm_email;
	params.term_agre_yn = selfData.term_agre_yn;
	
	var isParamEmpty = false;
	$('input[type=text]:gt(1)').each(function(i, el) {
		
		var $self = $(this);
		
		if(!$self.val()) {
			$self.focus();
			console.log(i, el);
			isParamEmpty = true;
			return false;
		}
	});
	
	if(isParamEmpty) {
		alert('필수 정보를 입력하지 않으셨습니다.');
		return false;
	}
	
	// email validation check
	var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
	if(!params.adm_email.match(regExp)) {
		alert('이메일 형식에 어긋납니다.');
		$('#txt-email-address').focus();
		return false;
	}
	
	// 개인정보 활용동의 체크상태 확인
	if(selfData.term_agre_yn == 'N' && $('input[type=checkbox]').is(':checked')) {
		
		params.term_agre_yn = selfData.term_agre_yn = 'Y'
		
	} else if(selfData.term_agre_yn != 'Y' && !$('input[type=checkbox]').is(':checked')) {
		
		alert('개인정보 활용동의는 필수 입니다.');
		$('input[type=checkbox]').focus();
		return false;
	}
	
	Common.REST.put('/admin/rest/account/self', params, function(data) {
		
		window.location.href = '/';
		
	}, function(data) {
		
		console.log(' 회원정보 변경 자체가 실패 data is ', data);
		alert('회원정보 변경 오류.\n담당 관리자에게 문의하세요');
//		window.location.href = '/login';
	});
}

$(function() {
	getAdminInfo();
});

