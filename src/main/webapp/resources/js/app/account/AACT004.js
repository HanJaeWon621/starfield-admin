
////////////////////////////////////////////////////////
// global variables

var adminListData  = {

	admins : [],
	paging : null,
	sort_target : 'rnum',
	sort_way : 'asc',
	search_text : null,
	
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

// 리스트 상태 저장을 위한 해시 오브젝트
var hashObject  = {
	page : 1,
	sort_target : 'rnum',
	sort_way : 'asc',
	search_text : ''
};

////////////////////////////////////////////////////////
// global functions

function hashConversion(hashObject){
	var hashArr = [];
	for (var k in hashObject){
	    if (hashObject.hasOwnProperty(k)) {
	    	hashArr.push(k + '=' +  hashObject[k])
	    }
	}
	return '#' + hashArr.join('&');
}

function hashObjConversion(cb) {
	
	var hash = location.hash;	
	hash = hash.replace('#', '');
	
	var hashSplit = hash.split('&');
	
	for(var i = 0, iMax = hashSplit.length; i < iMax; i ++) {
		var objectSplit = hashSplit[i].split('=');
		hashObject[objectSplit[0]] = objectSplit[1];
	}
	
	if(typeof cb == 'function') {
		cb();
	}
}


////////////////////////////////////////////////////////
//initialize

var rtvAdmins = new Ractive({
	el : '#account-mng',
	template : '#tmpl-account-mng-list',
	data : adminListData
});

rtvAdmins.on({
	
	
	'page-move' : function(o, page) {
		
		movePage(page, function(err) {});
	},
	
	'reg-admin' : function() {
		
		window.location.href = '/01/account/admin/reg';
	},
	
	'move-detail' : function(e, adm_seq) {
		
		window.location.href = '/01/account/admins/' + adm_seq;
	},
	
	'cancel-change' : function() {
		
		var r = confirm('입력/수정 정보를 저장하지 않고 종료합니다.\n종료하시겠습니까?');
		if (r == true) {
			window.location.href = '/';
		}
	},
	
	'on-search' : function() {
		
		if(!adminListData.search_text) {
			alert('검색어를 입력하세요.');
			$('input[type=text].search-input').focus();
			return false;
		}
		
		adminListData.sort_target = 'rnum';
		adminListData.sort_way = 'asc';
		
		searchAdminId(adminListData.search_text, function(err) {
			movePage(1, function(err) {});
		});
	},
	
	'on-search-keydown' : function(evt) {
		
		if(evt.original.keyCode == 13) {
			
			if(!adminListData.search_text) {
				alert('검색어를 입력하세요.');
				$('input[type=text].search-input').focus();
				return false;
			}
			
			searchAdminId(adminListData.search_text, function(err) {
				movePage(1, function(err) {});
			});
		}
	}, 
	
	'down-list' : function() {
		
		window.location.href = '/01/account/admins/excel';
	},
	
	'sort-list' : function(evt, sort_target) {
		
		var sort_way = 'desc';
		
		if(adminListData.sort_target == sort_target) {
			var sort_way = adminListData.sort_way == 'asc' ? 'desc' : 'asc';
		}
		
		sortList(sort_target, sort_way, function() {
			
			adminListData.sort_target = sort_target;
			adminListData.sort_way = sort_way;
			
			movePage(1, function(err) {});
		});
	}
});


/**
 * 최초 관리자 목록 정보 얻어 오기
 */
function getAdmins(cb) {
	
	var bcn_cd = $('#branch-selector').val();
	
	Common.REST.get('/admin/rest/account/' + bcn_cd + '/admins', null, function(data) {

		if(data && data instanceof Array) {
			
			 if(typeof cb == 'function') { cb(null, data); }
			
		} else {
			
			console.log(' 관리자 계정 가져오기 에러 발생!! ', data);
			alert('정보를 가져오는 도중 문제가 발생되었습니다.\n담당 관리자에게 문의하세요');
		}
		
	}, function(data) {
		console.log(' 관리자 계정 가져오기 에러 발생!! ', data);
		alert('정보를 가져오는 도중 문제가 발생되었습니다.\n담당 관리자에게 문의하세요');
	});
}


/**
 * search admin ID
 * @param _search_text
 * @param cb
 */
function searchAdminId(search_text, cb) {
	
	if(search_text) {
	
		adminListData.targets = adminListData.admins.filter(function(admin) {
			
			if(admin.adm_id.indexOf(search_text) != -1) {
				return true;
			} else {
				return false;
			}
		});
		
	} else {
		adminListData.targets = adminListData.admins;
	}
	
	adminListData.targets = adminListData.targets.map(function(admin, i) {
		admin.rnum = i + 1;
		return admin;
	});
	
	sortList(adminListData.sort_target, adminListData.sort_way, function() {
		
		if(typeof cb == 'function') {
			cb(null);
		}		
	});
}

/**
 * 리스트 정렬
 */
function sortList(sort_target, sort_way, cb) {
	
	adminListData.targets.sort(function(a, b) {
		
		var tempA = a;
		var tempB = b;
		
		if(sort_way == 'asc') {
			tempA = b;
			tempB = a;
		}
		
		if(tempA[sort_target] > tempB[sort_target]) {
			return -1;
		} else if(tempA[sort_target] < tempB[sort_target]) {
			return 1;
		} else {
			return 0;
		}
	});
	
	if(typeof cb == 'function') {
		cb(null);
	}
}


/**
 * page move
 * @param cb
 */
function movePage(_page, cb) {
	
	var LIST_LIMIT = 10;
	var PAGE_LIMIT = 10;
	
	var totCnt = adminListData.targets.length;
	
	var page = _page || 1;
	page = page <= 1 ? 1 : page;
	var offset = ((page - 1) * LIST_LIMIT);
	var limit = (offset - 1) + LIST_LIMIT + 1;
	limit = limit >= totCnt ? totCnt : limit;
	
	Common.Global.initPaging(offset, LIST_LIMIT, PAGE_LIMIT, totCnt, function(paging) {

		adminListData.paging = paging;
		adminListData.showList = [];
		
		for(var i = offset; i < limit; i++) {
			adminListData.showList.push(adminListData.targets[i]);
		}
		
		rtvAdmins.set(adminListData);
		
		// hash 정보를 갱신한다.
		hashObject.page = _page;
		hashObject.search_text = adminListData.search_text;
		hashObject.sort_target = adminListData.sort_target;
		hashObject.sort_way = adminListData.sort_way;
		location.hash = hashConversion(hashObject);
		
		if(typeof cb == 'function') {
			cb(null);
		}
	});
}


/**
 * 최초 페이지 설정
 * 저장되어 있는 hash 정보를 이용하여 
 */
function initPage() {
	
	var hash = location.hash;
	hash = hash.replace('#', '');
	
	var hashSplit = hash.split('&');
	
	for(var i = 0, iMax = hashSplit.length; i < iMax; i ++) {
		var objectSplit = hashSplit[i].split('=');
		hashObject[objectSplit[0]] = objectSplit[1];
	}
	
	// ractive object에 정보를 저장한다.
	adminListData.search_text = hashObject.search_text;
	adminListData.sort_target = hashObject.sort_target;
	adminListData.sort_way = hashObject.sort_way;

	searchAdminId(adminListData.search_text, function(err) {
		
		movePage(hashObject.page, function(err) {
			
//			console.log('냥냥');
		});
		
	});
}



$(function() {
	
	getAdmins(function(err, admins) {
		
		if(!location.hash) {
			location.hash = hashConversion(hashObject);
		}
		
		adminListData.admins = admins;
		
		initPage();
	});
	
});

