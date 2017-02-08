var app = app || {};

app.ractive = app.ractive || {};
app.ractive.blogList = null;

var hashObject  = {
	offset : 1,
	limit : 10,
	sort_name : 'rnum',
	sort_order : 'desc'
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

function BlogListSection() {
	
	
	var $blog_mng = $('#blog-mng');
	var bcn_cd = $blog_mng.data('bcn_cd');
	
	window.onhashchange = function() {
		var hash = location.hash;	
		hash = hash.replace('#', '');
		
		var hashSplit = hash.split('&');
		
		for(var i = 0, iMax = hashSplit.length; i < iMax; i ++) {
			var objectSplit = hashSplit[i].split('=');
			hashObject[objectSplit[0]] = objectSplit[1];
		}
		getBlogList(hashObject);
	};
	
	
	function getBlogList(hashObject) {
		Common.REST.get('/admin/rest/' + bcn_cd + '/theme', hashObject, function(result) {
			blogList(result);
		}, function(data) {
			alert('리스트 불러오기 실패');
		});
	}
	
	function blogList(result){
		
		result.paging.pages = [];
		for(var i = result.paging.page_start; i <= result.paging.page_end; ++i) {
			result.paging.pages.push(i);
        }
		
		
		if(!app.ractive.blogList) {
			app.ractive.blogList = new Ractive({
				el : '#blog-mng',
				template : '#tmpl-blog-mng',
				data : {
					list : result.list,
					paging : result.paging,
					hashObject : hashObject
				}
			});
			app.ractive.blogList.on({
				'detail' : function(e, thme_seq) {
					location.href = '/' + bcn_cd + '/starfieldStory/theme/' + thme_seq;
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
				'create' : function(e) {
					location.href = '/' + bcn_cd + '/starfieldStory/theme/create';
				},
				'delete' : function(e) {
					var thme_seq_list = $('input:checkbox[name="groupCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(thme_seq_list.length < 1) {
						return alert('추천테마를 1개 이상 선택해주세요.');
					}
					
					if(confirm('선택한 추천테마를 삭제하시겠습니까?')) {
						Common.REST.del('/admin/rest/' + bcn_cd + '/theme/' + thme_seq_list.join(','), {}, function(result) {
							window.onhashchange();
						}, function(data) {
							alert('삭제 실패');
						});
					}
				},
				'link' : function(e, blog_url) {
					window.open(blog_url);
				},
				'download' : function(e) {
					location.href = '/' + bcn_cd + '/starfieldStory/theme/download'
				},
				'change_sts' : function(e, theme) {
					theme.sts = theme.sts == 1 ? 0 : 1;
					
					Common.REST.put('/admin/rest/' + bcn_cd + '/theme/' + theme.thme_seq, theme, function(result) {
						window.onhashchange();
					}, function(data) {
						alert('수정 실패');
					});
				}
			});
		} else {
			app.ractive.blogList.reset();
			app.ractive.blogList.set({
				list : result.list,
				paging : result.paging,
				hashObject : hashObject
			});
		}
	}
	
}

$(function() {
	new BlogListSection();
	
	if(!location.hash) {
		location.hash = hashConversion(hashObject);
	}
	
	window.onhashchange();
});