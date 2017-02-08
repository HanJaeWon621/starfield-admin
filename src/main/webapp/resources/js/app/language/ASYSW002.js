var app = app || {};

app.ractive = app.ractive || {};
app.ractive.languageList = null;

var hashObject  = {
	offset : 1,
	limit : 10,
	sort_name : 'rnum',
	sort_order : 'desc',
	searchType : 1 ,
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

function LanguageGroupSection() {
	
	var $lang_mng = $('#lang-mng');
	var bcn_cd = $lang_mng.data('bcn_cd');
	
	window.onhashchange = function() {
		var hash = location.hash;	
		hash = hash.replace('#', '');
		
		var hashSplit = hash.split('&');
		
		for(var i = 0, iMax = hashSplit.length; i < iMax; i ++) {
			var objectSplit = hashSplit[i].split('=');
			hashObject[objectSplit[0]] = objectSplit[1];
		}
		getLanguageList(hashObject);
	};
	
	
	function getLanguageList(hashObject) {
		Common.REST.get('/admin/rest/' + bcn_cd + '/language', hashObject, function(result) {
			groupList(result);
		}, function(data) {
			alert('다국어 그룹 불러오기 실패');
		});
	}
	
	function groupList(result){
		
		result.paging.pages = [];
		for(var i = result.paging.page_start; i <= result.paging.page_end; ++i) {
			result.paging.pages.push(i);
        }
		
		
		if(!app.ractive.languageList) {
			app.ractive.languageList = new Ractive({
				el : '#lang-mng',
				template : '#tmpl-lang-mng',
				data : {
					list : result.list,
					paging : result.paging,
					hashObject : hashObject
				}
			});
			app.ractive.languageList.on({
				'detail' : function(e, pg_id) {
					location.href = '/' + bcn_cd + '/language/detail?pg_id=' + pg_id;
				},
				'pageMove' : function(e, page) {
					if(!$(e.node).hasClass('off')) {
						hashObject.offset = (page - 1) * hashObject.limit + 1;
						location.hash = hashConversion(hashObject);
					}
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
				},
				'search' : function(e) {
					hashObject.offset = 1;
					location.hash = hashConversion(hashObject);
				},
				'create' : function(e) {
					location.href = '/' + bcn_cd + '/language/detail?pg_id=create';
				},
				'delete' : function(e) {
					var pg_id_list = $('input:checkbox[name="groupCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(pg_id_list.length < 1) {
						return alert('삭제할 다국어 그룹을 선택해주세요.');
					}
					
					if(confirm('선택한 그룹을 삭제하시겠습니까?')) {
						Common.REST.del('/admin/rest/' + bcn_cd + '/language/delete/', pg_id_list, function(result) {
							window.onhashchange();
						}, function(data) {
							alert('그룹 삭제 실패');
						});
					}
				},
				'download' : function(e) {
					location.href = '/' + bcn_cd + '/language/download'
				}
			});
		} else {
			app.ractive.languageList.reset();
			app.ractive.languageList.set({
				list : result.list,
				paging : result.paging,
				hashObject : hashObject
			});
		}
	}
	
}

$(function() {
	new LanguageGroupSection();
	
	if(!location.hash) {
		location.hash = hashConversion(hashObject);
	}
	
	window.onhashchange();
});