var app = app || {};

app.ractive = app.ractive || {};
app.ractive.languageDetail = null;

app.data = app.data || {};
app.data.languageList = [];
function LanguageSection() {
	var $lang_reg = $('#lang-reg');
	var bcn_cd = $lang_reg.data('bcn_cd');
	var pg_id = $lang_reg.data('pg_id');
	
	
	function getLanguageDetail(pg_id) {
		if(pg_id == 'create') {
			LanguageDetail([]);
		} else {
			Common.REST.get('/admin/rest/' + bcn_cd + '/language/detail?pg_id=' + pg_id, {}, function(result) {				
				LanguageDetail(result.list);
			}, function(data) {
				alert('다국어 불러오기 실패');
			});
		}
	}
	
	function addLanguage() {
		return {
			pg_id : 'create',
			bcn_cd : bcn_cd,
			txt_symb : '',
			txt_ko : '',
			txt_en : '',
			txt_jp : '',
			txt_cn : '',
			txt_desc : '',
			sts : 1,
		};
	}
	
	function LanguageDetail(result){
		app.data.languageList = result
		
		if(app.data.languageList.length < 1) {
			app.data.languageList.unshift(addLanguage());
		}
		if(!app.ractive.languageDetail) {
			app.ractive.languageDetail = new Ractive({
				el : '#lang-reg',
				template : '#tmpl-lang-reg',
				data : {
					pg_id : pg_id == 'create' ? '' : pg_id,
					languageList : app.data.languageList
				}
			});
			app.ractive.languageDetail.on({
				'add' : function() {
					app.data.languageList.unshift(addLanguage());
				},
				'delete' : function(e, i) {
					if(confirm('해당 항목을 삭제하시겠습니까?')) {
						if(app.data.languageList[i].pg_id == 'create') {
							app.data.languageList.splice(i, 1);
						} else {
							app.data.languageList[i].sts = 9;
						}
						app.ractive.languageDetail.update();
					}
				},
				'cancel' : function() {
					location.href = '/' + bcn_cd + '/language';
				},
				'save' : function() {
					
					var fail = false;
					$(".text-input").each(function () {
						if(fail) {return fail;}
						if(!$(this).val()) {fail = true;}
					});
					
					if(fail) {
						return alert('내용을 전부 입력해주세요.');
					}
					
					
					var _pg_id = app.ractive.languageDetail.get('pg_id')
					
					for(var i = 0, iMax = app.data.languageList.length; i < iMax; i++) {
						app.data.languageList[i].pg_id = _pg_id;
					}
					
					if(confirm('입력한 내용으로 저장을 하시겠습니까?')) {
						console.log(app.data.languageList);
						
						Common.REST.post('/admin/rest/' + bcn_cd + '/language', app.data.languageList, function(result) {				
							if(result.code == 0) {
								location.href = '/' + bcn_cd + '/language';
							} else {
								alert('다국어 저장 실패');
							}
						}, function(data) {
							alert('다국어 저장 실패');
						});
					}
				}
			});
		} else {
			app.ractive.languageDetail.reset();
			app.ractive.languageDetail.set({
				pg_id : pg_id == 'create' ? '' : pg_id,
				languageList : app.data.languageList
			});
		}
	}
	
	getLanguageDetail(pg_id);
}

$(function() {
	new LanguageSection();

});