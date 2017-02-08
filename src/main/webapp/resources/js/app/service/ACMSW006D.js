var app = app || {};

app.ractive = app.ractive || {};
app.ractive.FAQList = null;
app.ractive.FAQ = null;

app.data = app.data || {};
app.data.FAQList = [];
app.data.category = [];
app.data.deleteFAQList = [];
app.data.FAQ = {};

var faqList = [];

function FAQGroupSection() {
	
	
	var $faq_mng = $('#faq-mng');
	var bcn_cd = $faq_mng.data('bcn_cd');
	var cate_seq = $faq_mng.data('cate_seq');
	
	
	function getFAQList() {
		async.parallel([
			function(asyncCallback) {
				Common.REST.get('/admin/rest/' + bcn_cd + '/faqs/cate/' + cate_seq, {},function(result) {
					asyncCallback(null, result);
				}, function(data) {
					asyncCallback(data);
				});
			},
			function(asyncCallback) {
				Common.REST.get('/admin/rest/' + bcn_cd + '/faqs/' + cate_seq, {},function(result) {
					asyncCallback(null, result);
				}, function(data) {
					asyncCallback(data);
				});
			}
		], function(err, result) {
			if(err) {
				return alert('FAQ 불러오기 실패');
			}
			
			var category = result[0];
			var FAQList = result[1];
			
			FAQGroup(category, FAQList);
		});
		
	}
	
	function sort() {
		app.data.FAQList = app.data.FAQList.sort(function(a, b){
	 		return a.sort_ord - b.sort_ord;
	 	});
		
		app.ractive.FAQList.reset();
		app.ractive.FAQList.set({
			category : app.data.category,
			FAQList : app.data.FAQList
		});
	}
	
	function FAQGroup(category, FAQList){
		app.data.category = category;
		app.data.FAQList = FAQList;
		if(!app.ractive.FAQList) {
			app.ractive.FAQList = new Ractive({
				el : '#faq-mng',
				template : '#tmpl-faq-mng',
				data : {
					category : app.data.category,
					FAQList : app.data.FAQList
				}
			});
			
			app.ractive.FAQList.on({
				'changeStatus' : function(e, faq) {
						if(faq.sts == 1) {
							faq.sts = 0;
						} else {
							faq.sts = 1;
						}
						app.ractive.FAQList.update();
				},
				'delete' : function(e) {
					var sort_ord_list = $('input:checkbox[name="groupCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(sort_ord_list.length < 1) {
						return alert('FAQ를 1개 이상 선택해주세요.');
					}
					
					if(confirm('선택한 FAQ를 삭제하시겠습니까?')) {
						for(var i = 0, iMax = sort_ord_list.length; i < iMax; i++) {
							
							for(var j = 0 ;j < app.data.FAQList.length; j++) {
								if(app.data.FAQList[j].sort_ord == sort_ord_list[i]) {
									app.data.FAQList[j].sts = 9
									
									if(app.data.FAQList[j].faq_seq != 'create') {
										app.data.deleteFAQList.push(app.data.FAQList[j]);
									}
									app.data.FAQList.splice(j, 1);
									
									j--;
								}
							}
						}
						
						for(var i = 0, iMax = app.data.FAQList.length; i < iMax; i++) {
							app.data.FAQList[i].sort_ord = i+1;
						}
						
						sort();
					}
				},
				'save' : function(e) {
					var view = false;
					
					if(app.data.FAQList.length < 1) {
						return alert('질문글이 1개 이상이어야 저장이 가능합니다.');
					}
					
					for(var i = 0, iMax = app.data.FAQList.length; i < iMax; i++) {
						if(app.data.FAQList[i].sts == 1){
							view = true;
							break;
						}
					}
					
					if(!view && app.data.category.sts == 1) {
						return alert('노출중인 카테고리는 노출중인 질문글이 1개 이상이여야 저장이 가능합니다.');
					}
					
					if(!confirm('현재 상태를 저장하시겠습니까?')) {
						return;
					}
					
					app.data.FAQList = app.data.FAQList.concat(app.data.deleteFAQList);
					
					
					Common.REST.post('/admin/rest/' + bcn_cd + '/faqs/' + cate_seq, app.data.FAQList, function(result) {
						if(result.code == 0) {
							location.reload();
						} else {
							alert('저장실패');
							location.reload();
						}
					}, function(data) {
						alert('저장실패');
						location.reload();
					});
				},
				'cancel' : function(e) {
					if(!confirm('현재 정보를 저장하지 않고 취소합니다. 취소하시겠습니까?')) {
						return;
					}
					location.reload()
				},
				'back' : function(e) {
					location.href = '/' + bcn_cd + '/service/faq'
				},
				'detail' : function(e, faq) {
					FAQSection(faq);
				}
			});
			
			//정렬
			var group = $("ul.sort").sortable({
				group: 'sort',
				onDrop: function ($item, container, _super) {
					var list = group.sortable("serialize").get()[0];
					// $('#serialize_output2').text(jsonString);
				 	_super($item, container);
				 	
				 	for(var i = 0, iMax = list.length; i < iMax; i++) {
				 		app.data.FAQList[list[i].sort_ord - 1].sort_ord = i + 1;
				 	}
				 	
				 	sort();
				 	
				},
				distance : 10
			});
			
		} else {
			app.ractive.FAQList.reset();
			app.ractive.FAQList.set({
				category : app.data.category,
				FAQList : app.data.FAQList
			});
		}
	}
	
	getFAQList();
	
}


function FAQSection(faq) {
	var $faq_mng = $('#faq-mng');
	var bcn_cd = $faq_mng.data('bcn_cd');
	var cate_seq = $faq_mng.data('cate_seq');
	var category = app.ractive.FAQList.get('category');
	
	app.data.FAQ = faq && JSON.parse(JSON.stringify(faq)) || {};
	app.data.FAQ.faq_seq = app.data.FAQ.faq_seq || 'new';
	if(app.data.FAQ.faq_seq == 'new') {
		app.data.FAQ.cate_seq = cate_seq;
		app.data.FAQ.bcn_cd = bcn_cd;
		app.data.FAQ.faq_seq = 'create';
		app.data.FAQ.faq_titl_ko = '';
		app.data.FAQ.faq_titl_en = '';
		app.data.FAQ.faq_titl_cn = '';
		app.data.FAQ.faq_titl_jp = '';
		
		app.data.FAQ.faq_cont_ko = '';
		app.data.FAQ.faq_cont_en = '';
		app.data.FAQ.faq_cont_cn = '';
		app.data.FAQ.faq_cont_jp = '';
		
		app.data.FAQ.sort_ord = app.data.FAQList.length + 1;
		app.data.FAQ.sts = 0;
	}
	
	if(!app.ractive.FAQ) {
		app.ractive.FAQ = new Ractive({
			el : '#faq-reg-popup',
			template : '#tmpl-faq-reg-popup',
			data : {
				category : category,
				faq : app.data.FAQ
			}
		});
		app.ractive.FAQ.on({
			'close' : function(e) {
				if(!confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
					return;
				}
				
				$('#faq-reg-popup').css('display', 'none');
			},
			'save' : function(e) {
				
				if(!app.data.FAQ.faq_titl_ko) return alert('FAQ 국문 제목을 입력해 주세요.');
				if(!app.data.FAQ.faq_titl_en) return alert('FAQ 영문 제목을 입력해 주세요.');
				if(!app.data.FAQ.faq_titl_cn) return alert('FAQ 중문 제목을 입력해 주세요.');
				if(!app.data.FAQ.faq_titl_jp) return alert('FAQ 일문 제목을 입력해 주세요.');
				
				if(!app.data.FAQ.faq_cont_ko) return alert('FAQ 국문 답변을 입력해 주세요.');
				if(!app.data.FAQ.faq_cont_en) return alert('FAQ 영문 답변을 입력해 주세요.');
				if(!app.data.FAQ.faq_cont_cn) return alert('FAQ 중문 답변을 입력해 주세요.');
				if(!app.data.FAQ.faq_cont_jp) return alert('FAQ 일문 답변을 입력해 주세요.');
				
				if(!confirm('입력한 내용으로 저장을 하시겠습니까?')) {
					return;
				}
				
				
				app.data.FAQList[app.data.FAQ.sort_ord-1] = app.data.FAQ;
				
				app.ractive.FAQList.update();
				
				$('#faq-reg-popup').css('display', 'none');
				
			}
		});
	} else {
		app.ractive.FAQ.reset();
		app.ractive.FAQ.set({
			category : category,
			faq : app.data.FAQ
		});
	}
	
	
	$('#faq-reg-popup').css('display', 'block');
	
	
}

$(function() {
	new FAQGroupSection();
});