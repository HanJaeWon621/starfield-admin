var app = app || {};

app.ractive = app.ractive || {};
app.ractive.instagramTagList = null;

app.data = app.data || {};
app.data.instagramTagList = [];
app.data.deleteInstagramTagList = [];
app.data.newInstagramTag = {};

function InstagramListSection() {
	
	
	var $sns_mng = $('#sns-mng');
	var bcn_cd = $sns_mng.data('bcn_cd');
	var token = $sns_mng.data('token');
	var client_id = $sns_mng.data('client_id');
	
	if(!token) {
		alert('인스타그램 토큰이 만료되었습니다. 토큰을 재발급하세요.');
	}
	
	
	function getInstagramTagList() {
		Common.REST.get('/admin/rest/' + bcn_cd + '/starfieldStory/instagram', {},function(result) {
			app.data.instagramTagList = result;
			instagramTagList();
		}, function(data) {
			alert('인스타그램 태그 불러오기 실패');
		});
	}
	
	function sort() {
		
		app.data.instagramTagList = app.data.instagramTagList.sort(function(a, b){
	 		return a.sort_ord - b.sort_ord;
	 	});
		
		app.ractive.instagramTagList.reset();
		app.ractive.instagramTagList.set({
			instagramTagList : app.data.instagramTagList
		});
		
	}
	
	
	function instagramTagList(){
		if(!app.ractive.instagramTagList) {
			app.ractive.instagramTagList = new Ractive({
				el : '#sns-mng',
				template : '#tmpl-sns-mng',
				data : {
					instagramTagList : app.data.instagramTagList
				}
			});
			
			app.ractive.instagramTagList.on({
				'addTagPopup' : function(e) {
					$('#sns-tag-reg-popup').css('display','block');
				},
				'closePopup' : function(e, category) {
					if(!confirm('입력/수정 정보를 추가하지 않고 종료합니다. 종료하시겠습니까?')) {
						return;
					}
					
					$('#addTag').val('');
					$('#sns-tag-reg-popup').css('display','none');
				},
				'addTag' : function(e) {
					var tag = $('#addTag').val();
					
					if(!tag) return alert('태그명을 입력해주세요.');
					
					if(!confirm('입력한 내용으로 저장을 하시겠습니까?')) {
						return;
					}
					
					app.data.instagramTagList.push({
						bcn_cd : bcn_cd,
						img_count : 0,
						insta_tag_nm : tag,
						insta_tag_seq : 'create',
						reg_dttm :'',
						sort_ord : app.data.instagramTagList.length + 1,
						sts : 0
					});
					
					$('#addTag').val('');
					
					$('#sns-tag-reg-popup').css('display','none');
				},
				'delete' : function(e) {
					var sort_ord_list = $('input:checkbox[name="groupCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(sort_ord_list.length < 1) {
						return alert('태그를 1개 이상 선택해주세요.');
					}
					
					if(confirm('선택한 태그를 삭제하시겠습니까?')) {
						for(var i = 0, iMax = sort_ord_list.length; i < iMax; i++) {
							
							for(var j = 0 ;j < app.data.instagramTagList.length; j++) {
								if(app.data.instagramTagList[j].sort_ord == sort_ord_list[i]) {
									app.data.instagramTagList[j].sts = 9
									
									if(app.data.instagramTagList[j].insta_tag_seq != 'create') {
										app.data.deleteInstagramTagList.push(app.data.instagramTagList[j]);
									}
									app.data.instagramTagList.splice(j, 1);
									
									j--;
								}
							}
						}
						
						for(var i = 0, iMax = app.data.instagramTagList.length; i < iMax; i++) {
							app.data.instagramTagList[i].sort_ord = i+1;
						}
						
						sort();
					}
				},
				'detail' : function(e, insta) {
					if(insta.insta_tag_seq == 'create') {
						return alert('저장하지 않은 태그는 이미지 목록으로 이동할 수 없습니다.');
					}
					location.href = '/' + bcn_cd + '/starfieldStory/instagram/' + insta.insta_tag_seq;
					
				},
				'changeStatus' : function(e, tag) {
					
					if(tag.img_count == 7) {
						if(tag.sts == 1) {
							tag.sts = 0;
						} else {
							tag.sts = 1;
						}
						app.ractive.instagramTagList.update();
					} else if(tag.sts == 1){
						tag.sts = 0;
						app.ractive.instagramTagList.update();
					} else {
						alert('이미지가 7개 일때만 노출로 변경 가능합니다.');
					}
				},
				'save' : function(e) {
					if(!confirm('현재 상태를 저장하시겠습니까?')) {
						return;
					}
					app.data.instagramTagList = app.data.instagramTagList.concat(app.data.deleteInstagramTagList);
					
					Common.REST.post('/admin/rest/' + bcn_cd + '/starfieldStory/instagram/', app.data.instagramTagList, function(result) {
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
					location.href = '/' + bcn_cd + '/starfieldStory/instagram';
				},
				'download' : function(e) {
					location.href = '/' + bcn_cd + '/starfieldStory/instagram/download';
				},
				'token' : function(e) {
					window.open('https://www.instagram.com/oauth/authorize/?client_id=' + client_id + '&redirect_uri=http://' + location.host + '/' + bcn_cd + '/starfieldStory/instagram/createToken&response_type=token&scope=public_content', 'token', 'width=300, height=400, scrollbars= 0, toolbar=0, menubar=no');
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
				 		app.data.instagramTagList[list[i].sort_ord - 1].sort_ord = i + 1;
				 	}
				 	
				 	sort();
				 	
				},
				distance : 10
			});
		} else {
			app.ractive.instagramTagList.reset();
			app.ractive.instagramTagList.set({
				instagramTagList : app.data.instagramTagList
			});
		}
	}
	
	getInstagramTagList();
	
}

function addFaqSection() {
	
}


$(function() {
	new InstagramListSection();
});