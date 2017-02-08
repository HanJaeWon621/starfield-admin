var cate = {};

cate.modifyYn = {firstCategories : false, secondCategories : false}
cate.selected = {rootCate : "", firstCate : "", popup : ""}
cate.popupType = 'view';
cate.data = cate.data || {};
cate.data.categories = {};
cate.data.selected = {};
cate.del = [];

var rtvCategory = new Ractive({
	el : '#cate-wrap',
	template : '#tmpl-cate',
	data : cate.data.categories
});

rtvCategory.on({
	'syncRedis' : function(o) {
		syncRedis();
	},
	'changeFirstCate' : function(o, cate_seq) {
		if(!cate_seq) {
			alert('신규 카테고리는 저장 후 2차 카테고리 등록/수정이 가능합니다.');
			return;
		}
		if(cate.modifyYn.secondCategories && !confirm('2차 카테고리 등록/수정 정보가 취소 됩니다. 진행하시겠습니까?')) {
			return;
		}
		cate.selected.firstCate = cate_seq;
		
		cate.modifyYn.secondCategories = false;
		$('#cate-first-wrap li').removeClass('selected');
		$('#cate-first-wrap li[data-cate_seq='+cate_seq+']').addClass('selected');
		getSecondCategory(cate_seq);
	},
	'del' : function(o, type, sort_id, idx, cnt) {

		if(cnt > 0) {
			var target = type == 'firstCategories' ? '자식 카테고리' : '카테고리 매핑 테넌트';
			alert(target + '이 1개 이상 존재하므로 삭제가 불가합니다.\n' + target + ' 개수 : ' + cnt);
			return;
		}
		
		cate.modifyYn[type] = true;
		
		if(cate.data.categories[type][idx].modify_sts != 1) {
			cate.del.push(cate.data.categories[type][idx].cate_seq);
		}
		
		console.log(cate.data.categories[type][idx].cate_seq, cate.selected.firstCate);
		
		if(cate.data.categories[type][idx].cate_seq == cate.selected.firstCate) {
			cate.data.categories.secondCategories = null;
			cate.selected.firstCate = "";
			rtvCategory.set('secondCategories', cate.data.categories.secondCategories);
		}
		
		cate.data.categories[type].splice(idx,1);
		
		for(var i = 0, iMax = cate.data.categories[type].length; i < iMax; i++) {
			cate.data.categories[type][i].no = i+1;
		}
		
		cate.data.categories.firstCategories = cate.data.categories.firstCategories.sort(function(a, b){
	 		return a.no - b.no;
	 	});
		
	 	rtvCategory.reset();
	 	rtvCategory.set(cate.data.categories);
	},
	'add' : function(o, type) {
		if(type == 'secondCategories' && !cate.selected.firstCate) {
			alert('등록할 1차 카테고리를 선택해주세요.');
			return;
		}
		
		cate.popupType = 'reg';
		cate.data.selected = {};
		cate.selected.popup = type;
		
		rtvRegCategory.set('popupType', cate.popupType);
		rtvRegCategory.set('category', cate.data.selected);
		
		$('#cate-name-reg-popup .text-input-wrap').removeClass('no-border');
		$('#cate-name-reg-popup input[type=text]').attr('disabled', false);
		$('#cate-name-reg-popup').show();
	},
	'view' : function(o, type, idx) {
		cate.popupType = 'view';
		cate.data.selected = cate.data.categories[type][idx];
		cate.selected.popup = type;
		
		rtvRegCategory.set('type',type);
		rtvRegCategory.set('popupType', cate.popupType);
		rtvRegCategory.set('category', cate.data.selected);
		
		$('#cate-name-reg-popup').show();
		$('#cate-name-reg-popup .text-input-wrap').addClass('no-border');
		$('#cate-name-reg-popup input[type=text]').attr('disabled', true);
	},
	'open' : function(o, type, idx, sts) {
		cate.data.categories[type][idx].sts = sts;
		rtvCategory.set(cate.data.categories);
		cate.modifyYn[type] = true;
	},
	'cancel' : function() {
		if(!confirm('입력/수정 정보를 저장하지 않고 초기화합니다. 취소하시겠습니까?')) {
			return;
		}
		location.href = '/01/category'
	},
	'save' : function() {
		if(!confirm('입력한 내용으로 저장을 하시겠습니까?')) {
			return;
		}
		saveCategory();
	}
});

function saveCategory() {
	var restUri = "/admin/rest/01/categories/TENANT";
	var reqData = {first_categories : cate.data.categories.firstCategories
		, second_categories : cate.data.categories.secondCategories
		, del_seq_arr : cate.del};
	
	if(reqData.first_categories) {
		reqData.first_categories.forEach(function(i){
			i.sort_ord = i.no;
		});
	}
	
	if(reqData.second_categories) {
		reqData.second_categories.forEach(function(i){
			i.sort_ord = i.no;
		});
	}
	messageRouter.trigger('show-popup-loading');
	Common.REST.put(restUri, reqData, function(data) {
		alert('저장되었습니다.');
		location.href = '/01/category'
	}, function(data) {
		console.log('fail data is ', data);
		messageRouter.trigger('hide-popup-loading');
	});
}

function getFirstCategory() {
	var restUri = "/admin/rest/01/categories/TENANT";
	var reqData = {};
	
	Common.REST.get(restUri, reqData, function(rootCate) {
		cate.selected.rootCate = rootCate.cate_seq;
		reqData = {offset : 0, limit : -1};
		Common.REST.get(restUri+"/"+rootCate.cate_seq, reqData, function(data) {
			cate.data.categories.firstCategories = data.list;
			rtvCategory.set('firstCategories', data.list);
			
			var first_group = $("#cate-first-wrap ul.sort").sortable({
				group: 'frist-sort',
				onDrop: function ($item, container, _super) {
					cate.modifyYn.firstCategories = true;
					var list = first_group.sortable("serialize").get()[0];
				 	_super($item, container);
				 	for(var i = 0, iMax = list.length; i < iMax; i++) {
				 		cate.data.categories.firstCategories[list[i].no - 1].no = i + 1;
				 	}
				 	
				 	cate.data.categories.firstCategories = cate.data.categories.firstCategories.sort(function(a, b){
				 		return a.no - b.no;
				 	});
					
				 	rtvCategory.reset();
				 	rtvCategory.set(cate.data.categories);
				 	if(cate.selected.firstCate) {
				 		$('#cate-first-wrap li[data-cate_seq='+cate.selected.firstCate+']').addClass('selected');
				 	}
				},
				distance : 10
			});
		}, function(data) {
			console.log('fail data is ', data);
		});
	}, function(data) {
		console.log('fail data is ', data);
	});
}

function getSecondCategory(cateSeq) {
	var restUri = "/admin/rest/01/categories/TENANT";
	var reqData = {offset : 0, limit : -1};
	
	Common.REST.get(restUri+"/"+cateSeq, reqData, function(data) {
		
		cate.data.categories.secondCategories = data.list;
		rtvCategory.set('secondCategories', data.list);
		
		var seconde_group = $("#cate-second-wrap ul.sort").sortable({
			group: 'second-sort',
			onDrop: function ($item, container, _super) {
				cate.modifyYn.secondCategories = true;
				var list = seconde_group.sortable("serialize").get()[0];
			 	_super($item, container);
			 	for(var i = 0, iMax = list.length; i < iMax; i++) {
			 		cate.data.categories.secondCategories[list[i].no - 1].no = i + 1;
			 	}
			 	
			 	cate.data.categories.secondCategories = cate.data.categories.secondCategories.sort(function(a, b){
			 		return a.no - b.no;
			 	});
				
			 	rtvCategory.reset();
			 	rtvCategory.set(cate.data.categories);
			 	$('#cate-first-wrap li[data-cate_seq='+cate.selected.firstCate+']').addClass('selected');
			},
			distance : 10
		});
	}, function(data) {
		console.log('fail data is ', data);
	});
}


var rtvRegCategory = new Ractive({
	el : '#cate-name-reg-popup',
	template : '#tmpl-cate-name-reg-popup',
	data : {category : cate.data.selected, popupType : cate.popupType}
});

rtvRegCategory.on({
	'save' : function(o) {
		
		if(!cate.data.selected.cate_nm_ko) { alert('국문 카테고리명을 입력해주세요.'); return; }
		if(!cate.data.selected.cate_nm_en) { alert('영문 카테고리명을 입력해주세요.'); return; }
		if(!cate.data.selected.cate_nm_cn) { alert('중문 카테고리명을 입력해주세요.'); return; }
		if(!cate.data.selected.cate_nm_jp) { alert('일문 카테고리명을 입력해주세요.'); return; }
		
		
		if(cate.popupType == 'modify') {
			console.log(cate.data.selected);
			cate.data.categories[cate.selected.popup][cate.data.selected.no - 1] = cate.data.selected;
			rtvCategory.set(cate.data.categories);
		} else {
			
			if(cate.selected.popup == 'firstCategories') {
				cate.data.selected.mama_cate_seq = cate.selected.rootCate;
				cate.data.selected.cate_cd = 'TNT_CATE_BIG';
				cate.data.selected.sts = 0;
			} else {
				cate.data.selected.mama_cate_seq = cate.selected.firstCate;
				cate.data.selected.sts = 0;
			}
			
			cate.data.selected.modify_sts = 1;
			cate.data.selected.no = cate.data.categories[cate.selected.popup].length + 1;
			
			console.log(cate.data.selected);
			
			cate.data.categories[cate.selected.popup][cate.data.selected.no - 1] = cate.data.selected;
			rtvCategory.set(cate.data.categories);
		}
		cate.modifyYn[cate.selected.popup] = true;
		$('#cate-name-reg-popup').hide();
	},
	'goModify' : function(o) {
		cate.popupType = 'modify';
		rtvRegCategory.set('popupType', cate.popupType);
		$('#cate-name-reg-popup .text-input-wrap').removeClass('no-border');
		$('#cate-name-reg-popup input[type=text]').attr('disabled', false);
	},
	'close' : function(o) {
		$('#cate-name-reg-popup').hide();
	},
	'cancel' : function(o) {
		if(!confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
			return;
		}
		$('#cate-name-reg-popup').hide();
	}
});

$(function() {
	getFirstCategory();
});
