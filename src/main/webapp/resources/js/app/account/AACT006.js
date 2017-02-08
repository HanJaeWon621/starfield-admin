
////////////////////////////////////////////////////////
// global variables
var accountData  = {
	
	adm_seq : null,
    adm_id : null,
    adm_pw : null,
    role_seq : null,
    org_role_seq : null,
    role_nm : null,
    adm_nm : null,
    adm_dept : null,
    adm_tel_num1 : null,
    adm_tel_num2 : null,
    adm_tel_num3 : null,
    adm_cel_num1 : null,
    adm_cel_num2 : null,
    adm_cel_num3 : null,
    adm_email : null,
    term_agre_yn : null,
    sts : null,
    sts_desc : null,
    status_list : [
                     {sts : 101, sts_desc : '사용중'}
                   , {sts : 102, sts_desc : '사용중지'}
                   , {sts : 103, sts_desc : '비번3회오류'}
                   , {sts : 104, sts_desc : '비번미변경'}
                   , {sts : 105, sts_desc : '등록'}
                   ],
      			
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

var rtvAccount = new Ractive({
	el : '#admin-account-modify',
	template : '#tmpl-admin-account-form',
	data : accountData
});

rtvAccount.on({
	
	'cancel-modify' : function() {
		
		var r = confirm('입력/수정 정보를 저장하지 않고 종료합니다.\n종료하시겠습니까?');
		if (r == true) {
			history.back();
		}
	},
	
	'reset-password' : function(o, adm_seq) {
		
		var params = {
				adm_seq : adm_seq,
			    bcn_cd : $('#branch-selector').val()
		};
		
		Common.REST.post('/admin/rest/account/password', params, function(data) {
			
			if(data && data.code == 1) {
				
				alert('초기화된 비밀번호는 ' + data.desc + ' 입니다.');
				location.reload(true);
				
			} else {
				alert('비밀번호를 생성하는데 실패하였습니다.\n재시도 이후에도 발생시 담당 관리자에게 문의하세요.');
			}
			
		}, function(data) {
			
			console.log('fail data is ', data);
			alert('비밀번호를 생성하는데 실패하였습니다.\n재시도 이후에도 발생시 담당 관리자에게 문의하세요.');
		});
	},
	
	'do-save' : function() {
		
		if(accountData.sts != accountData.org_sts) {
			
			switch(accountData.org_sts) {
				case 103:
					alert(accountData.sts_desc + '상태는 비밀번호 초기화를 통해 변경해주세요');
					accountData.sts = accountData.org_sts;
					rtvAccount.set(accountData);
					return false;
					break;
				case 104:
					alert(accountData.sts_desc + '상태는 비밀번호 초기화를 통해 변경해주세요');
					accountData.sts = accountData.org_sts;
					rtvAccount.set(accountData);
					return false;
					break;
				case 105:
					alert(accountData.sts_desc + '상태는 각 관리자가 개인정보 수정을 통해 사용중으로 변경 후 가능합니다.');
					accountData.sts = accountData.org_sts;
					rtvAccount.set(accountData);
					return false;
					break;
			}
			
			if(!(accountData.sts == 101 || accountData.sts == 102)) {
				alert('관리자 상태는 사용중 또는 사용중지만 선택하실 수 있습니다.');
				accountData.sts = accountData.org_sts;
				rtvAccount.set(accountData);
				return false;
			}
		}
		
		var r = confirm('입력한 내용으로 저장하시겠습니까?');
		if (r == true) {
			
			var param = { adm_seq : accountData.adm_seq,
						  sts : accountData.sts,
						  role_seq : accountData.role_seq };
			
			Common.REST.put('/admin/rest/account/admins', param, function(data) {
				
				console.log(data);
				
//				history.back();
				
				window.location.href = '/01/account/admins';
				
//				if(data instanceof Array) {
//					
//					accountData.roles = data;
//					rtvAccount.set(accountData);
//					
//				} else {
//					
//					console.log(' 관리자 역할 가져오기 오류.', data);
//					alert('정보를 가져오는 도중 문제가 발생되었습니다.\n담당 관리자에게 문의하세요');
//					history.back();
//				}
				
			}, function(data) {
				
				console.log(' 관리자 권한정보 가져오기 오류.', data);
				alert('정보를 가져오는 도중 문제가 발생되었습니다.\n담당 관리자에게 문의하세요');
				history.back();
			});
		}
		
	}
	
});

/**
 * 관리자 역할 정보 가져오기
 */
function getRoles() {
	
	Common.REST.get('/admin/rest/auth/roles', null, function(data) {
		
		if(data instanceof Array) {
			
			accountData.roles = data;
			rtvAccount.set(accountData);
			
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

// 수정 대상 관리자 정보를 가져온다.
function getAdminAccount() {
	
	var adm_seq = $('#admin-account-modify').data()['seq'];
	// var bcn_cd = $('#branch-selector').val();
	
	if(!adm_seq) {
		alert('수정 대상 관리자 정보를 가져오는 도중 문제가 발생되었습니다.\n담당 관리자에게 문의하세요');
		history.back();
		return false;
	}
	
	Common.REST.get('/admin/rest/account/01/admins/' + adm_seq, null, function(data) {
		
		console.log('success data is ', data);
		
		if(data) {
			
			accountData.adm_seq       = data.adm_seq     ;
		    accountData.adm_id        = data.adm_id      ;
		    accountData.adm_pw        = data.adm_pw      ;
		    accountData.role_seq      = data.role_seq    ;
		    accountData.role_nm       = data.role_nm     ;
		    accountData.adm_nm        = data.adm_nm      ;
		    accountData.adm_dept      = data.adm_dept    ;
		    accountData.adm_tel_num1  = data.adm_tel_num1;
		    accountData.adm_tel_num2  = data.adm_tel_num2;
		    accountData.adm_tel_num3  = data.adm_tel_num3;
		    accountData.adm_cel_num1  = data.adm_cel_num1;
		    accountData.adm_cel_num2  = data.adm_cel_num2;
		    accountData.adm_cel_num3  = data.adm_cel_num3;
		    accountData.adm_email     = data.adm_email   ;
		    accountData.term_agre_yn  = data.term_agre_yn;
		    accountData.term_agre_date = data.term_agre_date;
		    accountData.sts           = data.sts         ;
		    accountData.org_sts		  = data.sts	     ;
		    accountData.sts_desc      = data.sts_desc    ;
		    
		    rtvAccount.set(accountData);
			
		} else {
			
			console.log(' 관리자 정보 가져오기 오류.', data);
			alert('정보를 가져오는 도중 문제가 발생되었습니다.\n담당 관리자에게 문의하세요');
			history.back();
		}
		
	}, function(data) {
		
		try {
			var err = JSON.parse(data);
			if(err.code && err.code == '12007') {
				
				alert('해당 관리자 수정 권한이 없습니다.\n담당 관리자에게 문의하세요');
				history.back();
				return false;
				
			} else {
				
				console.log(' 관리자 정보 가져오기 오류.', data);
				alert('정보를 가져오는 도중 문제가 발생되었습니다.\n담당 관리자에게 문의하세요');
				history.back();
				
			}
		} catch(e) {
			
			console.log(' 관리자 정보 가져오기 오류.', data);
			alert('정보를 가져오는 도중 문제가 발생되었습니다.\n담당 관리자에게 문의하세요');
			history.back();
		}
		
	});
}


$(function() {
	
	// 관리자 역할 정보 가져오기
	getRoles();
	
	// 관리자 정보 가져오기
	getAdminAccount();

});

