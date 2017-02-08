var app = app || {};

app.ractive = app.ractive || {};
app.ractive.appVerList = null;
app.ractive.appVerDetail = null;

app.data = app.data || {};
app.data.Android_ver = {};
app.data.iOS_ver = {};

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

function AppVerSection() {
	
	
	var $admin_app_ver = $('#admin-app-ver');
	var bcn_cd = $admin_app_ver.data('bcn_cd');
	
	window.onhashchange = function() {
		var hash = location.hash;	
		hash = hash.replace('#', '');
		
		var hashSplit = hash.split('&');
		
		for(var i = 0, iMax = hashSplit.length; i < iMax; i ++) {
			var objectSplit = hashSplit[i].split('=');
			hashObject[objectSplit[0]] = objectSplit[1];
		}
		getAppVerList(hashObject);
	};
	
	
	function getAppVerList(hashObject) {	
		Common.REST.get('/admin/rest/' + bcn_cd + '/operation/appVer', hashObject, function(result) {
			appVerList(result);
		}, function(data) {
			alert('앱 버전 불러오기 실패');
		});
	}
	
	function appVerList(result){
		
		app.data.Android_ver = result.Android_ver;
		app.data.iOS_ver = result.iOS_ver;
		
		result.paging.pages = [];
		for(var i = result.paging.page_start; i <= result.paging.page_end; ++i) {
			result.paging.pages.push(i);
        }
		
		
		if(!app.ractive.appVerList) {
			app.ractive.appVerList = new Ractive({
				el : '#admin-app-ver',
				template : '#tmpl-admin-app-ver',
				data : {
					list : result.list,
					paging : result.paging,
					hashObject : hashObject
				}
			});
			app.ractive.appVerList.on({
				'create' : function(e) {
					appVerDetail({
						amav_seq : 'create',
						bcn_cd : bcn_cd,
						pltf_type : 2,
						version : '',
						sts : 0
					});
				},
				'detail' : function(e, appVer) {
					appVerDetail(appVer);
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
				'applyVersion' : function(e, appVer) {
					if(!confirm('해당 버전을 적용 하시겠습니까?')) {
						return;
					}
					appVer.sts = 1;
					Common.REST.put('/admin/rest/' + bcn_cd + '/operation/appVer/' + appVer.amav_seq, appVer, function(result) {
						window.onhashchange();
					}, function(data) {
						alert('적용 실패');
						window.onhashchange();
					});
				},
				'delete' : function(e) {
					var amav_seq_list = $('input:checkbox[name="groupCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(amav_seq_list.length < 1) {
						return alert('삭제할 버전을 1개 이상 선택해주세요.');
					}
					
					if(confirm('선택한 그룹을 삭제하시겠습니까?')) {
						Common.REST.del('/admin/rest/' + bcn_cd + '/operation/appVer/' + amav_seq_list.join(','), {}, function(result) {
							window.onhashchange();
						}, function(data) {
							alert('버전 삭제 실패');
						});
					}
				},
				'download' : function(e) {
					location.href = '/' + bcn_cd + '/operation/appVer/download';
				}
			});
		} else {
			app.ractive.appVerList.reset();
			app.ractive.appVerList.set({
				list : result.list,
				paging : result.paging,
				hashObject : hashObject
			});
		}
	}
}

function appVerDetail(param) {
	var appVer = param && JSON.parse(JSON.stringify(param)) || {};
	var bcn_cd = appVer.bcn_cd;
	
	if(!app.ractive.appVerDetail) {
		app.ractive.appVerDetail = new Ractive({
			el : '#app-ver-reg-popup',
			template : '#tmpl-app-ver-reg-popup',
			data : {
				appVer : appVer,
				Android_ver : app.data.Android_ver,
				iOS_ver : app.data.iOS_ver
			}
		});
		app.ractive.appVerDetail.on({
			'close' : function(e, sts) {
				if(sts == 0 && !confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')){
					return;
				}
				$('#app-ver-reg-popup').css('display', 'none');
			},
			'save' : function(e) {
				var save_appVer = app.ractive.appVerDetail.get('appVer');
				
				if(!save_appVer.version) {
					return alert('신규 버전을 입력해 주세요.');
				}
				
				if(!confirm('입력한 내용으로 저장을 하시겠습니까?')) {
					return;
				}
				
				if(save_appVer.amav_seq == 'create') {
					Common.REST.post('/admin/rest/' + bcn_cd + '/operation/appVer', save_appVer, function(result) {
						location.reload();
					}, function(data) {
						alert('저장 실패');
						location.reload();
					});
				}else {
					Common.REST.put('/admin/rest/' + bcn_cd + '/operation/appVer/' + save_appVer.amav_seq, save_appVer, function(result) {
						location.reload();
					}, function(data) {
						alert('수정 실패');
						location.reload();
					});
				}
			}
		});
	} else {
		app.ractive.appVerDetail.reset();
		app.ractive.appVerDetail.set({
			appVer : appVer,
			Android_ver : app.data.Android_ver,
			iOS_ver : app.data.iOS_ver
		});
	}
	
	$('#app-ver-reg-popup').css('display', 'block');
}

$(function() {
	new AppVerSection();
	
	if(!location.hash) {
		location.hash = hashConversion(hashObject);
	}
	
	window.onhashchange();
});