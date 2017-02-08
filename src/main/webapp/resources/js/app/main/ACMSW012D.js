var app = app || {};

app.ractive = app.ractive || {};
app.ractive.whatsNewGroup = null;
app.ractive.whatsNew = null;
app.ractive.whatsNewPreview = null;

app.data = app.data || {};
app.data.whatsNewGroup = {};
app.data.copy_whatsNewGroup = {};
app.data.tempBannerList = [];

app.data.whatsNew = {};

var bannerList = [];

function WhatsNewGroupSection() {
	
	
	var $main_news_group_reg = $('#main-news-group-reg');
	var bcn_cd = $main_news_group_reg.data('bcn_cd');
	var what_group_seq = $main_news_group_reg.data('what_group_seq');
	
	
	function getWhatsNew() {
		if(what_group_seq == 'create') {
			app.data.whatsNewGroup = {what_group_seq : what_group_seq};
			app.data.whatsNewGroup.whatsNewList = [];
			whatsNewGroup();
		} else {
			Common.REST.get('/admin/rest/' + bcn_cd + '/whatsNewGroups/' + what_group_seq, {},function(result) {
				var what_post_strt_split = new Date(result.what_post_strt).format('yyyy.MM.dd HH:mm').split(' ');
				var what_post_end_split = new Date(result.what_post_end).format('yyyy.MM.dd HH:mm').split(' ');
				
				result.what_post_strt_date = what_post_strt_split[0]
				result.what_post_end_date = what_post_end_split[0];
				result.what_post_strt_time = what_post_strt_split[1];
				result.what_post_end_time = what_post_end_split[1];
				app.data.whatsNewGroup = result;
				whatsNewGroup();
			}, function(data) {
				alert('왓츠뉴 그룹 불러오기 실패');
			});
		}
		
	}
	
	function sort() {
		app.data.whatsNewGroup.whatsNewList = app.data.whatsNewGroup.whatsNewList.sort(function(a, b){
	 		return a.sort_ord - b.sort_ord;
	 	});
		
		app.ractive.whatsNewGroup.reset();
		app.ractive.whatsNewGroup.set({
			whatsNewGroup : app.data.whatsNewGroup
		});
	}
	
	function whatsNewGroup(){
		
		for(var i = 0, iMax = app.data.whatsNewGroup.whatsNewList.length; i < iMax; i++) {
			if(app.data.whatsNewGroup.whatsNewList[i].div != 'TT') {
				
				if(app.data.whatsNewGroup.whatsNewList[i].start_dt && app.data.whatsNewGroup.whatsNewList[i].end_dt) {
					var toDay = new Date();
					var startDate = new Date(app.data.whatsNewGroup.whatsNewList[i].start_dt.replace(/\./gi, '-'));
					var endDate = new Date(app.data.whatsNewGroup.whatsNewList[i].end_dt.replace(/\./gi, '-'));
					endDate.setDate(endDate.getDate()+1);
					
					if(startDate <= toDay && toDay <= endDate) {
						app.data.whatsNewGroup.whatsNewList[i].cont_sts = '진행';
					} else {
						app.data.whatsNewGroup.whatsNewList[i].cont_sts = '';
					}
				} else {
					app.data.whatsNewGroup.whatsNewList[i].cont_sts = '';
				}
				
			} else {
				app.data.whatsNewGroup.whatsNewList[i].cont_sts = '-';
			}
		}
		
		if(!app.ractive.whatsNewGroup) {
			app.ractive.whatsNewGroup = new Ractive({
				el : '#main-news-group-reg',
				template : '#tmpl-main-news-group-reg',
				data : {
					whatsNewGroup : app.data.whatsNewGroup
				}
			});
			
			app.ractive.whatsNewGroup.on({
				'whatsNewDetail' : function(e, whatsNew) {
					WhatsNewSection(whatsNew);
				},
				'copy' : function(e) {
					if(app.data.whatsNewGroup.whatsNewList.length != 5) {
						return alert('구성 컨텐츠가 5개 일때 그룹 복사가 가능합니다');
					}
					WhatsNewCopy(app.data.whatsNewGroup);
				},
				'preview' : function(e) {
					if(app.data.whatsNewGroup.whatsNewList.length != 5) {
						return alert('구성 컨텐츠가 5개 일때 미리보기가 가능합니다');
					}
					WhatsNewPreview(app.data.whatsNewGroup);
				},
				'delete' : function(e) {
					var sort_ord_list = $('input:checkbox[name="whatNewCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(sort_ord_list.length < 1) {
						return alert('삭제할 컨텐츠를 1개 이상 선택해주세요.');
					}
					
					
					if(confirm('선택한 컨텐츠를 삭제하시겠습니까?')) {
						for(var i = 0; i < app.data.whatsNewGroup.whatsNewList.length; i++) {
							for(var j = 0, jMax = sort_ord_list.length; j < jMax; j++) {
								if(app.data.whatsNewGroup.whatsNewList[i].sort_ord == sort_ord_list[j]) {
									app.data.whatsNewGroup.whatsNewList[i].sts = 9;
									if(app.data.whatsNewGroup.whatsNewList[i].bn_seq != 'create') {
										app.data.tempBannerList.push(app.data.whatsNewGroup.whatsNewList[i]);
									}
									app.data.whatsNewGroup.whatsNewList.splice(i, 1);

									i--;
									break;
								}
							}
						}
						
						for(var i = 0, iMax = app.data.whatsNewGroup.whatsNewList.length; i < iMax; i++) {
							app.data.whatsNewGroup.whatsNewList[i].sort_ord = i+1;
						}
						sort();	
					}
				},
				'save' : function(e) {
					if(!app.data.whatsNewGroup.what_group_titl) {
						return alert('Group Title을 입력해주세요.');
					}
					if(!app.data.whatsNewGroup.what_post_strt_date) {
						return alert('게시시작 일자를 선택해주세요.');
					}
					if(!app.data.whatsNewGroup.what_post_end_date) {
						return alert('게시종료 일자를 선택해주세요.');
					}
					
					app.data.whatsNewGroup.what_post_strt = new Date(app.data.whatsNewGroup.what_post_strt_date.replace(/\./gi, '-'));
					app.data.whatsNewGroup.what_post_strt.setHours(app.data.whatsNewGroup.what_post_strt_time.substring(0,2));
					app.data.whatsNewGroup.what_post_strt.setMinutes(app.data.whatsNewGroup.what_post_strt_time.substring(3,5));
					
					
					app.data.whatsNewGroup.what_post_end = new Date(app.data.whatsNewGroup.what_post_end_date.replace(/\./gi, '-'));
					app.data.whatsNewGroup.what_post_end.setHours(app.data.whatsNewGroup.what_post_end_time.substring(0,2));
					app.data.whatsNewGroup.what_post_end.setMinutes(app.data.whatsNewGroup.what_post_end_time.substring(3,5));
					
					if(app.data.whatsNewGroup.what_post_strt >= app.data.whatsNewGroup.what_post_end) {
						return alert('게시종료일이 게시시작일보다 빠를 수 없습니다.');
					}
					
					if(app.data.whatsNewGroup.whatsNewList.length != 5) {
						return alert('구성 컨텐츠는 5개로 제한됩니다. 컨텐츠를 추가 또는 삭제해 주세요.');
					}
					
					if(!confirm('입력한 내용으로 저장을 하시겠습니까?')) {
						return;
					}
					
					if(app.data.whatsNewGroup.what_group_seq == 'create') { 
						delete cont_sts
						Common.REST.post('/admin/rest/' + bcn_cd + '/whatsNewGroups', app.data.whatsNewGroup, function(result) {
							if(result.code == 0) {
								location.href = '/' + bcn_cd + '/main/whatsNewGroups';
							} else {
								alert('저장실패');
								getWhatsNew();
							}
						}, function(data) {
							getWhatsNew();
						});
						
					} else {
						app.data.whatsNewGroup.whatsNewList = app.data.whatsNewGroup.whatsNewList.concat(app.data.tempBannerList);
						
						Common.REST.put('/admin/rest/' + bcn_cd + '/whatsNewGroups/' + app.data.whatsNewGroup.what_group_seq, app.data.whatsNewGroup, function(result) {
							if(result.code == 0) {
								location.href = '/' + bcn_cd + '/main/whatsNewGroups';
							} else {
								alert('저장실패');
								getWhatsNew();
							}
						}, function(data) {
							getWhatsNew();
						});
					}
				},
				'cancel' : function(e) {
					if(confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
						location.href = '/' + bcn_cd + '/main/whatsNewGroups';
					}
				}
			});
			$("[name='datepicker']" ).datepicker({
				dateFormat: "yy.mm.dd",
				onSelect: function() {
					app.ractive.whatsNewGroup.updateModel();
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
				 		app.data.whatsNewGroup.whatsNewList[list[i].sort_ord - 1].sort_ord = i + 1;
				 	}
				 	sort();
				 	
				},
				distance : 10
			});
			
		} else {
			app.ractive.whatsNewGroup.reset();
			app.ractive.whatsNewGroup.set({
				whatsNewGroup : app.data.whatsNewGroup
			});
		}
	}
	
	getWhatsNew();
	
}


function WhatsNewSection(whatsNew) {
	
	var $main_news_group_reg = $('#main-news-group-reg');
	var bcn_cd = $main_news_group_reg.data('bcn_cd');
	var what_group_seq = $main_news_group_reg.data('what_group_seq');
	
	app.data.whatsNew = whatsNew && JSON.parse(JSON.stringify(whatsNew)) || {};
	app.data.whatsNew.what_new_seq = app.data.whatsNew.what_new_seq || 'create';
	if(app.data.whatsNew.what_new_seq == 'create') {
		app.data.whatsNew.bcn_cd = bcn_cd;
		app.data.whatsNew.div = 'ET';
		app.data.whatsNew.web_txt_colr = '#FFFFFF';
		app.data.whatsNew.mobi_txt_colr = '#FFFFFF';
		app.data.whatsNew.web_bg_colr = '#C31531';
		app.data.whatsNew.mobi_bg_colr = '#C31531';
		app.data.whatsNew.what_group_seq = what_group_seq;
		app.data.whatsNew.web_bg_img_type = 1;
		app.data.whatsNew.mobi_bg_img_type = 1;
		
	}
	console.log(app.data.whatsNew);
	if(!app.ractive.whatsNew) {
		app.ractive.whatsNew = new Ractive({
			el : '#main-news-cont-reg-popup',
			template : '#tmpl-main-news-cont-reg-popup',
			data : {
				whatsNew : app.data.whatsNew
			}
		});
		app.ractive.whatsNew.on({
			'web_bg_colr' : function(e, colorCode) {
				app.data.whatsNew.web_bg_colr = colorCode;
				app.ractive.whatsNew.update();
			},
			'web_txt_colr' : function(e, colorCode) {
				app.data.whatsNew.web_txt_colr = colorCode;
				app.ractive.whatsNew.update();
			},
			'mobi_bg_colr' : function(e, colorCode) {
				app.data.whatsNew.mobi_bg_colr = colorCode;
				app.ractive.whatsNew.update();
			},
			'mobi_txt_colr' : function(e, colorCode) {
				app.data.whatsNew.mobi_txt_colr = colorCode;
				app.ractive.whatsNew.update();
			},
			'changeContent' : function(e) {
				app.data.whatsNew.any_seq = null;
				app.data.whatsNew.cont_titl = null;
				app.data.whatsNew.start_dt = null;
				app.data.whatsNew.end_dt = null;
				app.data.whatsNew.etc_txt = null;
				app.data.whatsNew.title_head_text = null;
				app.ractive.whatsNew.update();
			},
			'selectPopup' : function(e, div) {
				if(div == 'TT') {
					messageRouter.trigger('show-popup-tenant', null, {tnt_type : 1, bcn_cd : bcn_cd});
				} else if(div == 'ET') {
					messageRouter.trigger('show-popup-event', null, {bcn_cd : bcn_cd});
				} else if(div == 'CP') {
					messageRouter.trigger('show-popup-coupon', null, {bcn_cd : bcn_cd});
				}
			},
			'webImageDelete' : function(){
				app.data.whatsNew.thumb_image_seq = null;
				app.data.whatsNew.thumb_image_uri = null;
				app.ractive.whatsNew.update();
			},
			'appImageDelete' : function(){
				app.data.whatsNew.mobi_thumb_image_seq = null;
				app.data.whatsNew.mobi_thumb_image_uri = null;
				app.ractive.whatsNew.update();
			},
			'save' : function(e) {
				if(!app.data.whatsNew.any_seq) {
					return alert('대상 컨텐츠를 선택해주세요.');
				}
				if(!app.data.whatsNew.title_main_text) {
					return alert('메인 타이틀1을 입력해주세요.');
				}
				if(!app.data.whatsNew.title_sub_text) {
					return alert('메인 타이틀2를 입력해주세요.');
				}
				
				if(!app.data.whatsNew.web_bg_img_type) {
					return alert('목록 노출 이미지 타입(WEB)을 선택해주세요.');
				}
				
				if(!app.data.whatsNew.mobi_bg_img_type) {
					return alert('목록 노출 이미지 타입(APP)을 선택해주세요.');
				}
				
				if(!app.data.whatsNew.web_txt_colr) {
					return alert('텍스트 색상(WEB)을 선택해주세요.');
				}
				if(!app.data.whatsNew.mobi_bg_colr) {
					return alert('텍스트 색상을(APP) 선택해주세요.');
				}
				
				if(app.data.whatsNew.web_bg_img_type == 1) {
					if(!app.data.whatsNew.web_bg_colr) {
						return alert('목록 노출 이미지(WEB) 색상을 선택해주세요.');
					}
				} else {
					if(!app.data.whatsNew.thumb_image_uri) {
						return alert('목록 노출 이미지(WEB)를 업로드 해주세요.');
					}
				}
				
				if(app.data.whatsNew.mobi_bg_img_type == 1) {
					if(!app.data.whatsNew.mobi_bg_colr) {
						return alert('목록 노출 이미지(WEB) 색상을 선택해주세요.');
					}
				} else {
					if(!app.data.whatsNew.mobi_thumb_image_uri) {
						return alert('목록 노출 이미지(APP)를 업로드 해주세요.');
					}
				}
				
				if(!confirm('입력한 내용으로 저장을 하시겠습니까?')) {
					return;
				}
				
				if(app.data.whatsNew.what_new_seq == 'create' && !app.data.whatsNew.sort_ord) {
					app.data.whatsNew.sort_ord = app.data.whatsNewGroup.whatsNewList.length + 1;
					app.data.whatsNew.rnum = app.data.whatsNewGroup.whatsNewList.length + 1;
					app.data.whatsNewGroup.whatsNewList.push(app.data.whatsNew);
				} else {
					for(var i = 0, iMax = app.data.whatsNewGroup.whatsNewList.length; i < iMax; i++) {
						if(app.data.whatsNewGroup.whatsNewList[i].sort_ord == app.data.whatsNew.sort_ord) {
							app.data.whatsNewGroup.whatsNewList[i] = app.data.whatsNew;
							break;
						}
					}
				}
				
				app.ractive.whatsNewGroup.update();
				$('#main-news-cont-reg-popup').css('display', 'none');
			},
			'close' : function(e) {
				if(!confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
					return;
				}
				
				$('#main-news-cont-reg-popup').css('display', 'none');
			}
		});
		
		messageRouter.on('select-popup-tenent', null, function(tenant){
			app.data.whatsNew.any_seq = tenant.tnt_seq;
			app.data.whatsNew.cont_titl = tenant.tnt_nm_ko;
			app.data.whatsNew.etc_txt = tenant.room_num;
			app.data.whatsNew.title_head_text = tenant.tnt_nm_ko;
			app.ractive.whatsNew.update();
		});
		messageRouter.on('select-popup-event', null, function(event){
			app.data.whatsNew.any_seq = event.evt_seq;
			app.data.whatsNew.cont_titl = event.evt_titl;
			app.data.whatsNew.start_dt = event.evt_strt_dt;
			app.data.whatsNew.end_dt = event.evt_end_dt;
			app.data.whatsNew.title_head_text = 'EVENT';
			app.ractive.whatsNew.update();
		});
		messageRouter.on('select-popup-coupon', null, function(coupon){
			app.data.whatsNew.any_seq = coupon.cp_seq;
			app.data.whatsNew.cont_titl = coupon.cp_titl;
			app.data.whatsNew.start_dt = coupon.cp_act_strt_dt;
			app.data.whatsNew.end_dt = coupon.cp_iss_end_dt;
			app.data.whatsNew.etc_txt = coupon.cp_act_strt_dt + ' - ' + coupon.cp_act_strt_dt;
			app.data.whatsNew.title_head_text = 'COUPON';
			app.ractive.whatsNew.update();
		});
		
		app.imageUpload('#web_image_upload', bcn_cd, function(err, image, el){
			if(err) {
				return alert('이미지 업로드 실패');
			}
			app.data.whatsNew.web_bg_img_type = 2;
			app.data.whatsNew.thumb_image_seq = image.img_seq;
			app.data.whatsNew.thumb_image_uri = image.img_uri;
			app.ractive.whatsNew.update();
		});
		
		app.imageUpload('#app_image_upload', bcn_cd, function(err, image, el){
			if(err) {
				return alert('이미지 업로드 실패');
			}
			app.data.whatsNew.mobi_bg_img_type = 2;
			app.data.whatsNew.mobi_thumb_image_seq = image.img_seq;
			app.data.whatsNew.mobi_thumb_image_uri = image.img_uri;
			app.ractive.whatsNew.update();
		});
	} else {
		app.ractive.whatsNew.reset();
		app.ractive.whatsNew.set({
			whatsNew : app.data.whatsNew
		});
	}
	
	$('#main-news-cont-reg-popup').css('display', 'block');
	
	
}

function WhatsNewPreview(whatsNewGroup) {
	console.log(whatsNewGroup);
	if(!app.ractive.whatsNewPreview) {
		app.ractive.whatsNewPreview = new Ractive({
			el : '#main-news-display-popup',
			template : '#tmpl-main-news-display-popup',
			data : {
				whatsNewGroup : whatsNewGroup
			}
		});
		app.ractive.whatsNewPreview.on({
			'close' : function(e){
				$('#main-news-display-popup').css('display', 'none');
			}
		});
	} else {
		app.ractive.whatsNewPreview.set({
			whatsNewGroup : whatsNewGroup
		});
	}
	
	$('#main-news-display-popup').css('display', 'block');
}

function WhatsNewCopy(_whatsNewGroup) {
	app.data.copy_whatsNewGroup = JSON.parse(JSON.stringify(_whatsNewGroup));
	var $main_news_group_reg = $('#main-news-group-reg');
	var bcn_cd = $main_news_group_reg.data('bcn_cd');
	
	if(!app.ractive.whatsNewCopy) {
		app.ractive.whatsNewCopy = new Ractive({
			el : '#main-news-group-copy-popup',
			template : '#tmpl-main-news-group-copy-popup',
			data : {
				copy_whatsNewGroup : app.data.copy_whatsNewGroup
			}
		});
		app.ractive.whatsNewCopy.on({
			'close' : function(e){
				$('#main-news-group-copy-popup').css('display', 'none');
			},
			'save' : function(e) {
				if(!app.data.copy_whatsNewGroup.what_group_titl) {
					return alert('Group Title을 입력해주세요.');
				}
				if(!app.data.copy_whatsNewGroup.what_post_strt_date) {
					return alert('게시시작 일자를 선택해주세요.');
				}
				if(!app.data.copy_whatsNewGroup.what_post_end_date) {
					return alert('게시종료 일자를 선택해주세요.');
				}
				
				app.data.copy_whatsNewGroup.what_post_strt = new Date(app.data.copy_whatsNewGroup.what_post_strt_date.replace(/\./gi, '-'));
				app.data.copy_whatsNewGroup.what_post_strt.setHours(app.data.copy_whatsNewGroup.what_post_strt_time.substring(0,2));
				app.data.copy_whatsNewGroup.what_post_strt.setMinutes(app.data.copy_whatsNewGroup.what_post_strt_time.substring(3,5));
				
				
				app.data.copy_whatsNewGroup.what_post_end = new Date(app.data.copy_whatsNewGroup.what_post_end_date.replace(/\./gi, '-'));
				app.data.copy_whatsNewGroup.what_post_end.setHours(app.data.copy_whatsNewGroup.what_post_end_time.substring(0,2));
				app.data.copy_whatsNewGroup.what_post_end.setMinutes(app.data.copy_whatsNewGroup.what_post_end_time.substring(3,5));
				
				if(app.data.copy_whatsNewGroup.what_post_strt >= app.data.copy_whatsNewGroup.what_post_end) {
					return alert('게시종료일이 게시시작일보다 빠를 수 없습니다.');
				}
				
				if(app.data.copy_whatsNewGroup.whatsNewList.length != 5) {
					return alert('구성 컨텐츠는 5개로 제한됩니다. 컨텐츠를 추가 또는 삭제해 주세요.');
				}
				
				Common.REST.post('/admin/rest/' + bcn_cd + '/whatsNewGroups', app.data.copy_whatsNewGroup, function(result) {
					if(result.code == 0) {
						location.href = '/01/main/whatsNewGroups';
					} else {
						alert('저장실패');
						$('#main-news-group-copy-popup').css('display', 'block');
					}
				}, function(data) {
					$('#main-news-group-copy-popup').css('display', 'block');
				});
			}
		});
		
			$("[name='datepicker_copy']" ).datepicker({
				dateFormat: "yy.mm.dd",
				onSelect: function() {
					app.ractive.whatsNewCopy.updateModel();
				}
			});
		
	} else {
		app.ractive.whatsNewCopy.set({
			copy_whatsNewGroup : app.data.copy_whatsNewGroup
		});
	}
	
	
	
	$('#main-news-group-copy-popup').css('display', 'block');
}

$(function() {
	new WhatsNewGroupSection();
});