var app = app || {};

app.ractive = app.ractive || {};
app.ractive.actionLogList = null;

var beforeDate = new Date();
beforeDate.setDate(beforeDate.getDate() - 180);

var hashObject  = {
	offset : 1,
	limit : 10,
	sort_name : 'rnum',
	sort_order : 'desc',
	start_date : beforeDate.format('YYYY.MM.DD'),
	end_date : new Date().format('YYYY.MM.DD'),
	searchKeyword : ''
};

function hashConversion(hashObject){
	var hashArr = [];
	for (var k in hashObject){
	    if (hashObject.hasOwnProperty(k)) {
	    	hashArr.push(k + '=' +  hashObject[k])
	    }
	}
	return '#' + hashArr.join('&');
}

function ActionLogListSection() {
	
	window.onhashchange = function() {
		var hash = location.hash;	
		hash = hash.replace('#', '');
		
		var hashSplit = hash.split('&');
		
		for(var i = 0, iMax = hashSplit.length; i < iMax; i ++) {
			var objectSplit = hashSplit[i].split('=');
			hashObject[objectSplit[0]] = objectSplit[1];
		}
		getActionLogList(hashObject);
	};
	
	
	function getActionLogList(hashObject) {
		Common.REST.get('/admin/rest/account/actionLog', hashObject, function(result) {
			actionLogList(result);
		}, function(data) {
			alert('관리자 액션 로그 불러오기 실패');
		});
	}
	
	function actionLogList(result){
		
		result.paging.pages = [];
		for(var i = result.paging.page_start; i <= result.paging.page_end; ++i) {
			result.paging.pages.push(i);
        }
		
		
		if(!app.ractive.actionLogList) {
			app.ractive.actionLogList = new Ractive({
				el : '#history',
				template : '#tmpl-history',
				data : {
					list : result.list,
					paging : result.paging,
					hashObject : hashObject
				}
			});
			app.ractive.actionLogList.on({
				'pageMove' : function(e, page) {
					if(!$(e.node).hasClass('off')) {
						hashObject.offset = (page - 1) * hashObject.limit + 1;
						location.hash = hashConversion(hashObject);
					}
				},
				'search' : function(e) {
					
					var s_date = new Date(hashObject.start_date.replace(/\./gi, '-'));
					var e_date = new Date(hashObject.end_date.replace(/\./gi, '-'));
					
					if(e_date < s_date) {
						return alert('종료일이 시작일보다 클 수 없습니다.');
					}
					
					var btMs = e_date.getTime() - s_date.getTime();
					var btDay = btMs / (1000*60*60*24);
					
					if(btDay > 180) {
						return alert('최대 6개월까지 조회 가능합니다.');
					}
					
					hashObject.offset = 1;
					
					location.hash = hashConversion(hashObject);
				},
				'sort' : function(e, sort_name) {
					$(e.node).siblings().removeClass('selected');
					$(e.node).addClass('selected');
					if(hashObject.sort_name == sort_name) {
						if(hashObject.sort_order == 'desc') {
							hashObject.sort_order = 'asc';
						} else {
							hashObject.sort_order = 'desc';
						}
						
					} else {
						hashObject.sort_name = sort_name;
						hashObject.sort_order = 'desc';
					}
					location.hash = hashConversion(hashObject);
				}
			});
			
			$("[name='datepicker']" ).datepicker({
				dateFormat: "yy.mm.dd",
				onSelect: function() {
					app.ractive.actionLogList.updateModel();
				}
			});
		} else {
			app.ractive.actionLogList.reset();
			app.ractive.actionLogList.set({
				list : result.list,
				paging : result.paging,
				hashObject : hashObject
			});
		}
	}
	
}

$(function() {
	new ActionLogListSection();
	
	if(!location.hash) {
		location.hash = hashConversion(hashObject);
	}
	
	window.onhashchange();
});