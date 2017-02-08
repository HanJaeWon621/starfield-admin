var app = app || {};

app.ractive = app.ractive || {};
app.ractive.instagram = null;

app.ractive.instagramImage = null;

app.data = app.data || {};
app.data.instagram = {};

app.data.deleteInstagramList = [];

app.data.instagramImage = {
	searchKeyword : '',
	imageList : [],
	selectedCount : 0,
	viewSelected : false
};

function InstagramSection() {
	
	var $sns_reg = $('#sns-reg');
	var bcn_cd = $sns_reg.data('bcn_cd');
	var token = $sns_reg.data('token');
	var insta_tag_seq = $sns_reg.data('insta_tag_seq');
	
	
	function getInstagram() {
		
		Common.REST.get('/admin/rest/' + bcn_cd + '/starfieldStory/instagram/' + insta_tag_seq, {},function(result) {
			viewInstagram(result);
		}, function(data) {
			alert('인스타그램 불러오기 실패');
		});
	}
	
	function sort() {
		app.data.instagram.instagramImageList = app.data.instagram.instagramImageList.sort(function(a, b){
	 		return a.sort_ord - b.sort_ord;
	 	});
		
		app.ractive.instagram.reset();
		app.ractive.instagram.set({
			instagram : app.data.instagram
		});
	}
	
	function viewInstagram(result){
		app.data.instagram = result;
		
		if(!app.ractive.instagram) {
			app.ractive.instagram = new Ractive({
				el : '#sns-reg',
				template : '#tmpl-sns-reg',
				data : {
					instagram : app.data.instagram
				}
			});
			
			app.ractive.instagram.on({
				'add' : function(e) {
					InstagramImageSection();
				},
				'changeStatus' : function(e, image) {
						if(image.sts == 1) {
							image.sts = 0;
						} else {
							image.sts = 1;
						}
						app.ractive.instagram.update();
				},
				'delete' : function(e) {
					var sort_ord_list = $('input:checkbox[name="groupCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(sort_ord_list.length < 1) {
						return alert('이미지를 1개 이상 선택해주세요.');
					}
					
					if(confirm('선택한 이미지를 삭제하시겠습니까?')) {
						for(var i = 0, iMax = sort_ord_list.length; i < iMax; i++) {
							
							for(var j = 0 ;j < app.data.instagram.instagramImageList.length; j++) {
								if(app.data.instagram.instagramImageList[j].sort_ord == sort_ord_list[i]) {
									app.data.instagram.instagramImageList[j].sts = 9
									
									if(app.data.instagram.instagramImageList[j].insta_seq != 'create') {
										app.data.deleteInstagramList.push(app.data.instagram.instagramImageList[j]);
									}
									app.data.instagram.instagramImageList.splice(j, 1);
									
									j--;
								}
							}
						}
						
						for(var i = 0, iMax = app.data.instagram.instagramImageList.length; i < iMax; i++) {
							app.data.instagram.instagramImageList[i].sort_ord = i+1;
						}
						
						sort();
					}
				},
				'save' : function(e) {
					var viewCount = 0;
					
					if(app.data.instagram.instagramImageList.length < 1) {
						return alert('이미지가 1개 이상이어야 저장이 가능합니다.');
					}
					
					for(var i = 0, iMax = app.data.instagram.instagramImageList.length; i < iMax; i++) {
						if(app.data.instagram.instagramImageList[i].sts == 1){
							viewCount++;
						}
					}
					
					if(viewCount != 7 && app.data.instagram.sts == 1) {
						return alert('노출중인 인스타그램 태그는 노출중인 이미지가 7개 일때 저장이 가능합니다.');
					}
					
					if(!confirm('현재 상태를 저장하시겠습니까?')) {
						return;
					}
					
					app.data.instagram.instagramImageList = app.data.instagram.instagramImageList.concat(app.data.deleteInstagramList);
					
					Common.REST.post('/admin/rest/' + bcn_cd + '/starfieldStory/instagram/' + insta_tag_seq, app.data.instagram.instagramImageList, function(result) {
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
					location.href = '/' + bcn_cd + '/starfieldStory/instagram'
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
				 		app.data.instagram.instagramImageList[list[i].sort_ord - 1].sort_ord = i + 1;
				 	}
				 	
				 	sort();
				 	
				},
				distance : 10
			});
			
		} else {
			app.ractive.instagram.reset();
			app.ractive.instagram.set({
				instagram : app.data.instagram
			});
		}
	}
	
	getInstagram();
	
}


function InstagramImageSection() {
	
	var $sns_reg = $('#sns-reg');
	var bcn_cd = $sns_reg.data('bcn_cd');
	var token = $sns_reg.data('token');
	var insta_tag_seq = $sns_reg.data('insta_tag_seq');
	

	function ImageList(imageList){
		app.data.instagramImage.imageList = imageList;
		app.data.instagramImage.selectedCount = 0;
		if(!app.ractive.instagramImage) {
			app.ractive.instagramImage = new Ractive({
				el : '#sns-reg-popup',
				template : '#tmpl-sns-reg-popup',
				data : {
					instagram : app.data.instagramImage
				}
			});
			app.ractive.instagramImage.on({
				'search' : function(e) {
					if(!token) {
						alert('인스타그램 토큰이 만료되었습니다. 토큰을 재발행해주세요.');
					}
					
					$.ajax({  
						type: 'GET',  
						dataType: 'jsonp',  
						cache: false,  
						url: 'https://api.instagram.com/v1/tags/' + app.data.instagramImage.searchKeyword + '/media/recent/?access_token=' + token,  
						success: function(response) {  
							if(response.data && response.data.length > 0) {
								for(var i = 0, iMax = response.data.length; i < iMax; i++) {
									response.data[i].select = false;
								}
								ImageList(response.data);
							} else {
								ImageList([]);
							}
						},
						error : function() {
							alert('이미지 불러오기를 실패했습니다.');
						}
					});  
				},
				'imgSelect' : function(e, insta) {
					if(insta.select) {
						insta.select = false;
					} else {
						insta.select = true;
					}
					
					var count = 0;
					
					for(var i = 0, iMax = app.data.instagramImage.imageList.length; i < iMax; i++) {
						if(app.data.instagramImage.imageList[i].select){
							count++;
						}
					}
					
					app.data.instagramImage.selectedCount = count;
					
					app.ractive.instagramImage.update();
				},
				'viewChange' : function(e) {
					if(app.data.instagramImage.viewSelected) {
						app.data.instagramImage.viewSelected = false;
					} else {
						app.data.instagramImage.viewSelected = true;
					}
					app.ractive.instagramImage.update();
				},
				'cancel' : function(e) {
					if(!confirm('현재 정보를 저장하지 않고 취소합니다. 취소하시겠습니까?')) {
						return;
					}
					app.data.instagramImage.imageList = [];
					
					$('#sns-reg-popup').css('display', 'none');
				},
				'save' : function(e) {
					if(!confirm('이미지를 저장하시겠습니까?')) {
						return;
					}
					
					var imageList = [];
					
					for(var i = 0, iMax = app.data.instagramImage.imageList.length; i < iMax; i++) {
						if(app.data.instagramImage.imageList[i].select){
							var _image = app.data.instagramImage.imageList[i];
							imageList.push({
								cont_id : _image.id,
								created_time : _image.created_time,
								insta_seq : 'create',
								insta_tag_seq : insta_tag_seq,
								like_count : _image.likes.count,
								sort_ord : app.data.instagram.instagramImageList.length + 1 + imageList.length,
								sts : 0,
								tag : app.data.instagramImage.searchKeyword,
								url_lnk : _image.link,
								url_low : _image.images.low_resolution.url,
								url_std : _image.images.standard_resolution.url,
								url_thumb : _image.images.thumbnail.url,
								user_name : _image.user.username
							});
						}
					}
					
					app.data.instagram.instagramImageList = app.data.instagram.instagramImageList.concat(imageList);
					
					app.ractive.instagram.update();
					
					$('#sns-reg-popup').css('display', 'none');
				}
			});
			
		} else {
			app.ractive.instagramImage.reset();
			app.ractive.instagramImage.set({
				instagram : app.data.instagramImage
			});
		}
	}
	
	ImageList([]);
	
	$('#sns-reg-popup').css('display', 'block');
}

$(function() {
	new InstagramSection();
});