var app = app || {};

app.ractive = app.ractive || {};
app.ractive.noticeList = null;

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

function NoticeListSection() {
	
	
	var $notice_mng = $('#notice-mng');
	var bcn_cd = $notice_mng.data('bcn_cd');
	
	window.onhashchange = function() {
		var hash = location.hash;	
		hash = hash.replace('#', '');
		
		var hashSplit = hash.split('&');
		
		for(var i = 0, iMax = hashSplit.length; i < iMax; i ++) {
			var objectSplit = hashSplit[i].split('=');
			hashObject[objectSplit[0]] = objectSplit[1];
		}
		getNoticeList(hashObject);
	};
	
	
	function getNoticeList(hashObject) {
		Common.REST.get('/admin/rest/' + bcn_cd + '/notices', hashObject, function(result) {
			noticeList(result);
		}, function(data) {
			alert('공지 불러오기 실패');
		});
	}
	
	function noticeList(result){
		
		result.paging.pages = [];
		for(var i = result.paging.page_start; i <= result.paging.page_end; ++i) {
			result.paging.pages.push(i);
        }
		
		
		if(!app.ractive.noticeList) {
			app.ractive.noticeList = new Ractive({
				el : '#notice-mng',
				template : '#tmpl-notice-mng',
				data : {
					list : result.list,
					paging : result.paging,
					hashObject : hashObject
				}
			});
			app.ractive.noticeList.on({
				'detail' : function(e, noti_seq) {
					location.href = '/' + bcn_cd + '/service/notice/' + noti_seq;
				},
				'pageMove' : function(e, page) {
					if(!$(e.node).hasClass('off')) {
						hashObject.offset = (page - 1) * hashObject.limit + 1;
						location.hash = hashConversion(hashObject);
					}
				},
				'search' : function(e) {
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
				},
				'create' : function(e) {
					location.href = '/' + bcn_cd + '/service/notice/create';
				},
				'delete' : function(e) {
					var noti_seq_list = $('input:checkbox[name="groupCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(noti_seq_list.length < 1) {
						return alert('공지사항을 1개 이상 선택해주세요.');
					}
					
					if(confirm('선택한 공지사항을 삭제하시겠습니까?')) {
						Common.REST.del('/admin/rest/' + bcn_cd + '/notices/' + noti_seq_list.join(','), {}, function(result) {
							window.onhashchange();
						}, function(data) {
							alert('공지사항 삭제 실패');
						});
					}
				},
				'download' : function(e) {
					location.href = '/' + bcn_cd + '/service/notice/download'
				}
			});
		} else {
			app.ractive.noticeList.reset();
			app.ractive.noticeList.set({
				list : result.list,
				paging : result.paging,
				hashObject : hashObject
			});
		}
	}
	
}

$(function() {
	new NoticeListSection();
	
	if(!location.hash) {
		location.hash = hashConversion(hashObject);
	}
	
	window.onhashchange();
});