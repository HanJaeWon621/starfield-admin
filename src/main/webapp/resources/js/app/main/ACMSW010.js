var app = app || {};

app.ractive = app.ractive || {};
app.ractive.bannerGroupList = null;

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

function BannerGroupSection() {
	
	
	var $admin_main_hero = $('#admin-main-hero');
	var bcn_cd = $admin_main_hero.data('bcn_cd');
	
	window.onhashchange = function() {
		var hash = location.hash;	
		hash = hash.replace('#', '');
		
		var hashSplit = hash.split('&');
		
		for(var i = 0, iMax = hashSplit.length; i < iMax; i ++) {
			var objectSplit = hashSplit[i].split('=');
			hashObject[objectSplit[0]] = objectSplit[1];
		}
		getGroupList(hashObject);
	};
	
	
	function getGroupList(hashObject) {
		Common.REST.get('/admin/rest/' + bcn_cd + '/bannerGroups', hashObject, function(result) {
			groupList(result);
		}, function(data) {
			alert('배너 그룹 불러오기 실패');
		});
	}
	
	function groupList(result){
		
		result.paging.pages = [];
		for(var i = result.paging.page_start; i <= result.paging.page_end; ++i) {
			result.paging.pages.push(i);
        }
		
		for(var i = 0, iMax = result.list.length; i < iMax; i++) {
			result.list[i].bn_post_strt = new Date(result.list[i].bn_post_strt).format('yyyy.MM.dd HH:mm');
			result.list[i].bn_post_end = new Date(result.list[i].bn_post_end).format('yyyy.MM.dd HH:mm');
		}
		
		if(!app.ractive.bannerGroupList) {
			app.ractive.bannerGroupList = new Ractive({
				el : '#admin-main-hero',
				template : '#tmpl-admin-main-hero',
				data : {
					list : result.list,
					paging : result.paging,
					hashObject : hashObject
				}
			});
			app.ractive.bannerGroupList.on({
				'detail' : function(e, bn_group_seq) {
					location.href = '/' + bcn_cd + '/main/bannerGroups/' + bn_group_seq;
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
					location.href = "/01/main/bannerGroups/create";
				},
				'delete' : function(e) {
					var bn_group_seq_list = $('input:checkbox[name="groupCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(bn_group_seq_list.length < 1) {
						return alert('삭제할 그룹을 1개 이상 선택해주세요.');
					}
					
					if(confirm('선택한 그룹을 삭제하시겠습니까?')) {
						Common.REST.del('/admin/rest/' + bcn_cd + '/bannerGroups/' + bn_group_seq_list.join(','), {}, function(result) {
							window.onhashchange();
						}, function(data) {
							alert('배너 그룹 삭제 실패');
						});
					}
				},
				'redis' : function(e) {
					Common.REST.put('/admin/rest/' + bcn_cd + '/bannerGroups/redis', {}, function(result) {
						alert('redis 반영 완료');
					}, function(data) {
						alert('redis 반영 실패');
					});
				}
			});
		} else {
			app.ractive.bannerGroupList.reset();
			app.ractive.bannerGroupList.set({
				list : result.list,
				paging : result.paging,
				hashObject : hashObject
			});
		}
	}
	
}

$(function() {
	new BannerGroupSection();
	
	if(!location.hash) {
		location.hash = hashConversion(hashObject);
	}
	
	window.onhashchange();
});