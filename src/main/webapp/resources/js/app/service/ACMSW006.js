var app = app || {};

app.ractive = app.ractive || {};
app.ractive.faqCate = null;

app.data = app.data || {};
app.data.faqCateList = [];
app.data.deleteFaqCateList = [];
app.data.newFaqCate = {};
app.data.category = {};
function FAQCategorySection() {
	
	
	var $faq_cate_mng = $('#faq-cate-mng');
	var bcn_cd = $faq_cate_mng.data('bcn_cd');
	
	
	function getFAQCateList() {
		
		Common.REST.get('/admin/rest/' + bcn_cd + '/faqs/cate/', {},function(result) {
			app.data.faqCateList = result;
			faqCateList();
		}, function(data) {
			alert('faq 카테고리 불러오기 실패');
		});
	}
	
	function sort() {
		app.data.faqCateList = app.data.faqCateList.sort(function(a, b){
	 		return a.sort_ord - b.sort_ord;
	 	});
		
		app.ractive.faqCate.reset();
		app.ractive.faqCate.set({
			faqCateList : app.data.faqCateList
		});
		
	}
	
	
	function faqCateList(){
		if(!app.ractive.faqCate) {
			app.ractive.faqCate = new Ractive({
				el : '#faq-cate-mng',
				template : '#tmpl-faq-cate-mng',
				data : {
					faqCateList : app.data.faqCateList
				}
			});
			
			app.ractive.faqCate.on({
				'showPopup' : function(e) {
					app.data.category = {
						cate_nm_ko : '',
						cate_nm_en : '',
						cate_nm_cn : '',
						cate_nm_jp :'',
						sts : 0,
						bcn_cd : bcn_cd,
						cate_seq : 'create',
						sort_ord : app.data.faqCateList.length + 1,
						count : 0
					}
					$('#faq-cate-reg-popup').css('display','block');
				},
				'modifyPopup' : function(e, category) {
					app.data.category = category && JSON.parse(JSON.stringify(category)) || {};
					$('#cate_nm_ko').val(app.data.category.cate_nm_ko);
					$('#cate_nm_en').val(app.data.category.cate_nm_en);
					$('#cate_nm_cn').val(app.data.category.cate_nm_cn);
					$('#cate_nm_jp').val(app.data.category.cate_nm_jp);
					
					$('#faq-cate-reg-popup').css('display','block');
				},
				'hidePopup' : function(e) {
					if(!confirm('입력/수정 정보를 추가하지 않고 종료합니다. 종료하시겠습니까?')) {
						return;
					}
					$('#cate_nm_ko').val('');
					$('#cate_nm_en').val('');
					$('#cate_nm_cn').val('');
					$('#cate_nm_jp').val('');
					
					$('#faq-cate-reg-popup').css('display','none');
				},
				'add' : function(e) {
					
					app.data.category.cate_nm_ko = $('#cate_nm_ko').val();
					app.data.category.cate_nm_en = $('#cate_nm_en').val();
					app.data.category.cate_nm_cn = $('#cate_nm_cn').val();
					app.data.category.cate_nm_jp = $('#cate_nm_jp').val();
					
				
					if(!app.data.category.cate_nm_ko) return alert('국문 카테고리명을 입력해주세요.');
					if(!app.data.category.cate_nm_en) return alert('영문 카테고리명을 입력해주세요.');
					if(!app.data.category.cate_nm_cn) return alert('중문 카테고리명을 입력해주세요.');
					if(!app.data.category.cate_nm_jp) return alert('일문 카테고리명을 입력해주세요.');
					
					if(!confirm('입력한 내용으로 카테고리를 추가 하시겠습니까?')) {
						return;
					}
					
					app.data.faqCateList[app.data.category.sort_ord-1] = app.data.category;
					app.ractive.faqCate.update();
					
					$('#cate_nm_ko').val('');
					$('#cate_nm_en').val('');
					$('#cate_nm_cn').val('');
					$('#cate_nm_jp').val('');
					
					$('#faq-cate-reg-popup').css('display','none');
				},
				'delete' : function(e) {
					var sort_ord_list = $('input:checkbox[name="groupCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(sort_ord_list.length < 1) {
						return alert('카테고리를 1개 이상 선택해주세요.');
					}
					
					if(confirm('선택한 카테고리를 삭제하시겠습니까?')) {
						for(var i = 0, iMax = sort_ord_list.length; i < iMax; i++) {
							
							for(var j = 0 ;j < app.data.faqCateList.length; j++) {
								if(app.data.faqCateList[j].sort_ord == sort_ord_list[i]) {
									app.data.faqCateList[j].sts = 9
									
									if(app.data.faqCateList[j].cate_seq != 'create') {
										app.data.deleteFaqCateList.push(app.data.faqCateList[j]);
									}
									app.data.faqCateList.splice(j, 1);
									
									j--;
								}
							}
						}
						
						for(var i = 0, iMax = app.data.faqCateList.length; i < iMax; i++) {
							app.data.faqCateList[i].sort_ord = i+1;
						}
						
						sort();
					}
				},
				'faqList' : function(e, faqCate) {
					if(faqCate.cate_seq == 'create') {
						return alert('저장하지 않은 카테고리는 게시글 목록으로 이동할 수 없습니다.');
					}
					location.href = '/' + bcn_cd + '/service/faq/' + faqCate.cate_seq;
					
				},
				'changeStatus' : function(e, faqCate) {
					
					if(faqCate.count > 0) {
						if(faqCate.sts == 1) {
							faqCate.sts = 0;
						} else {
							faqCate.sts = 1;
						}
						app.ractive.faqCate.update();
					} else {
						alert('게시글이 없는 경우에는 노출 상태로 변경 할 수 없습니다.')
					}
				},
				'save' : function(e) {
					if(!confirm('현재 상태를 저장하시겠습니까?')) {
						return;
					}
					app.data.faqCateList = app.data.faqCateList.concat(app.data.deleteFaqCateList);
					
					Common.REST.post('/admin/rest/' + bcn_cd + '/faqs/cate/', app.data.faqCateList, function(result) {
						if(result.code == 0) {
							location.reload();
						} else {
							alert('저장실패');
						}
					}, function(data) {
						alert('저장실패');
					});
				},
				'cancel' : function(e) {
					if(!confirm('현재 정보를 저장하지 않고 취소합니다. 취소하시겠습니까?')) {
						return;
					}
					location.href = '/' + bcn_cd + '/service/faq';
				},
				'download' : function(e) {
					location.href = '/' + bcn_cd + '/service/faq/download';
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
				 		app.data.faqCateList[list[i].sort_ord - 1].sort_ord = i + 1;
				 	}
				 	
				 	sort();
				 	
				},
				distance : 10
			});
		} else {
			app.ractive.faqCate.reset();
			app.ractive.faqCate.set({
				faqCateList : app.data.faqCateList
			});
		}
	}
	
	getFAQCateList();
	
}

function addFaqSection() {
	
}


$(function() {
	new FAQCategorySection();
});