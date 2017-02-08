var app = app || {};

app.ractive = app.ractive || {};
app.ractive.QNAList = null;

var hashObject  = {
	offset : 1,
	limit : 10,
	sort_name : 'rnum',
	sort_order : 'desc',
	searchType : '1',
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

function QNAListSection() {
	
	
	var $inquiry_mng = $('#inquiry-mng');
	var bcn_cd = $inquiry_mng.data('bcn_cd');
	
	window.onhashchange = function() {
		var hash = location.hash;	
		hash = hash.replace('#', '');
		
		var hashSplit = hash.split('&');
		
		for(var i = 0, iMax = hashSplit.length; i < iMax; i ++) {
			var objectSplit = hashSplit[i].split('=');
			hashObject[objectSplit[0]] = objectSplit[1];
		}
		getQNAList(hashObject);
	};
	
	
	function getQNAList(hashObject) {
		Common.REST.get('/admin/rest/' + bcn_cd + '/service/qna', hashObject, function(result) {
			QNAList(result);
		}, function(data) {
			alert('QNA LIST 불러오기 실패');
		});
	}
	
	function QNAList(result){
		result.paging.pages = [];
		for(var i = result.paging.page_start; i <= result.paging.page_end; ++i) {
			result.paging.pages.push(i);
        }
		
		if(!app.ractive.QNAList) {
			app.ractive.QNAList = new Ractive({
				el : '#inquiry-mng',
				template : '#tmpl-inquiry-mng',
				data : {
					list : result.list,
					paging : result.paging,
					hashObject : hashObject
				}
			});
			app.ractive.QNAList.on({
				'detail' : function(e, qna_seq) {
					location.href = '/' + bcn_cd + '/service/qna/' + qna_seq;
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
				'delete' : function(e) {
					var qna_seq_list = $('input:checkbox[name="deleteCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(qna_seq_list.length < 1) {
						return alert('삭제할 문의를 1개 이상 선택해주세요.');
					}
					
					if(confirm('선택한 문의를 삭제하시겠습니까?')) {
						Common.REST.del('/admin/rest/' + bcn_cd + '/service/qna/' + qna_seq_list.join(','), {}, function(result) {
							window.onhashchange();
						}, function(data) {
							alert('삭제 실패');
						});
					}
				},
				'search' : function(e, searchType, searchKeyword) {
					hashObject.searchType = searchType;
					hashObject.searchKeyword = searchKeyword;
					
					hashObject.offset = 1;
					location.hash = hashConversion(hashObject);
				},
				'excelDownload' : function() {
					location.href = '/' + bcn_cd + '/service/qna/download'
				}
			});
		} else {
			app.ractive.QNAList.reset();
			app.ractive.QNAList.set({
				list : result.list,
				paging : result.paging,
				hashObject : hashObject
			});
		}
	}
	
}

$(function() {
	new QNAListSection();
	
	if(!location.hash) {
		location.hash = hashConversion(hashObject);
	}
	
	window.onhashchange();
});