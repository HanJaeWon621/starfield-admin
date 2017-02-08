var app = app || {};

app.ractive = app.ractive || {};
app.ractive.bannerGroup = null;
app.ractive.banner = null;

app.data = app.data || {};
app.data.bannerGroup = {};
app.data.tempBannerList = [];
app.data.banner = {};

var bannerList = [];

function BannerGroupSection() {
	
	
	var $main_hero_reg = $('#main-hero-reg');
	var bcn_cd = $main_hero_reg.data('bcn_cd');
	var bn_group_seq = $main_hero_reg.data('bn_group_seq');
	
	
	function getBannerList() {
		if(bn_group_seq == 'create') {
			app.data.bannerGroup = {bn_group_seq : bn_group_seq};
			app.data.bannerGroup.bannerList = [];
			bannerGroup();
		} else {
			Common.REST.get('/admin/rest/' + bcn_cd + '/bannerGroups/' + bn_group_seq, {},function(result) {
				var bn_post_strt_split = new Date(result.bn_post_strt).format('yyyy.MM.dd HH:mm').split(' ');
				var bn_post_end_split = new Date(result.bn_post_end).format('yyyy.MM.dd HH:mm').split(' ');
				
				result.bn_post_strt_date = bn_post_strt_split[0]
				result.bn_post_end_date = bn_post_end_split[0];
				result.bn_post_strt_time = bn_post_strt_split[1];
				result.bn_post_end_time = bn_post_end_split[1];
				app.data.bannerGroup = result;
				bannerGroup();
			}, function(data) {
				alert('배너 그룹 불러오기 실패');
			});
		}
		
	}
	
	function sort() {
		app.data.bannerGroup.bannerList = app.data.bannerGroup.bannerList.sort(function(a, b){
	 		return a.sort_ord - b.sort_ord;
	 	});
		
		app.ractive.bannerGroup.reset();
		app.ractive.bannerGroup.set({
			bannerGroup : app.data.bannerGroup
		});
	}
	
	function bannerGroup(){
		if(!app.ractive.bannerGroup) {
			app.ractive.bannerGroup = new Ractive({
				el : '#main-hero-reg',
				template : '#tmpl-main-hero-reg',
				data : {
					bannerGroup : app.data.bannerGroup
				}
			});
			
			app.ractive.bannerGroup.on({
				'bannerDetail' : function(e, banner) {
					if(!app.data.bannerGroup.bn_div) {
						return alert('적용 구분을 선택해주세요.');
					}
					
					BannerSection(banner);
				},
				'delete' : function(e) {
					var sort_ord_list = $('input:checkbox[name="bannerCheckBox"]:checked').map(function(){
						return $(this).val();
					}).get();
					
					if(sort_ord_list.length < 1) {
						return alert('삭제할 배너를 1개 이상 선택해주세요.');
					}
					
					if(confirm('선택한 배너를 삭제하시겠습니까?')) {
						for(var i = 0; i < app.data.bannerGroup.bannerList.length; i++) {
							for(var j = 0, jMax = sort_ord_list.length; j < jMax; j++) {
								if(app.data.bannerGroup.bannerList[i].sort_ord == sort_ord_list[j]) {
									app.data.bannerGroup.bannerList[i].sts = 9;
									if(app.data.bannerGroup.bannerList[i].bn_seq != 'create') {
										app.data.tempBannerList.push(app.data.bannerGroup.bannerList[i]);
									}
									app.data.bannerGroup.bannerList.splice(i, 1);

									i--;
									break;
								}
							}
						}
						
						for(var i = 0, iMax = app.data.bannerGroup.bannerList.length; i < iMax; i++) {
							app.data.bannerGroup.bannerList[i].sort_ord = i+1;
						}
						
						sort();
						
					}
					
					
				},
				'save' : function(e) {
					if(app.data.bannerGroup.bannerList.length == 0) {
						return alert('구성 배너는 1개 이상이어야 합니다.');
					}
					if(!app.data.bannerGroup.bn_div) {
						return alert('적용 구분을 선택해주세요.');
					}
					if(!app.data.bannerGroup.bn_group_titl) {
						return alert('그룹타이틀을 입력해주세요.');
					}
					if(!app.data.bannerGroup.bn_post_strt_date) {
						return alert('게시시작 일자를 선택해주세요.');
					}
					if(!app.data.bannerGroup.bn_post_end_date) {
						return alert('게시종료 일자를 선택해주세요.');
					}
					app.data.bannerGroup.bn_post_strt = new Date(app.data.bannerGroup.bn_post_strt_date.replace(/\./gi, '-') + ' ' + app.data.bannerGroup.bn_post_strt_time);
					
					app.data.bannerGroup.bn_post_strt = new Date(app.data.bannerGroup.bn_post_strt_date.replace(/\./gi, '-'));
					app.data.bannerGroup.bn_post_strt.setHours(app.data.bannerGroup.bn_post_strt_time.substring(0,2));
					app.data.bannerGroup.bn_post_strt.setMinutes(app.data.bannerGroup.bn_post_strt_time.substring(3,5));
					
					app.data.bannerGroup.bn_post_end = new Date(app.data.bannerGroup.bn_post_end_date.replace(/\./gi, '-'));
					app.data.bannerGroup.bn_post_end.setHours(app.data.bannerGroup.bn_post_end_time.substring(0,2));
					app.data.bannerGroup.bn_post_end.setMinutes(app.data.bannerGroup.bn_post_end_time.substring(3,5));
					
					if(app.data.bannerGroup.bn_post_strt >= app.data.bannerGroup.bn_post_end) {
						return alert('게시종료일이 게시시작일보다 빠를 수 없습니다.');
					}
					
					if(!confirm('입력한 내용으로 저장을 하시겠습니까?')) {
						return;
					}
					
					if(app.data.bannerGroup.bn_group_seq == 'create') { 
						Common.REST.post('/admin/rest/' + bcn_cd + '/bannerGroups', app.data.bannerGroup, function(result) {
							if(result.code == 0) {
								location.href = '/' + bcn_cd + '/main/bannerGroups';
							} else {
								alert('저장실패');
								getBannerList();
							}
						}, function(data) {
							getBannerList();
						});
						
					} else {
						app.data.bannerGroup.bannerList = app.data.bannerGroup.bannerList.concat(app.data.tempBannerList);
						
						Common.REST.put('/admin/rest/' + bcn_cd + '/bannerGroups/' + app.data.bannerGroup.bn_group_seq, app.data.bannerGroup, function(result) {
							if(result.code == 0) {
								location.href = '/' + bcn_cd + '/main/bannerGroups';
							} else {
								alert('저장실패');
								getBannerList();
							}
						}, function(data) {
							getBannerList();
						});
					}
				},
				'cancel' : function(e) {
					if(confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
						location.href = '/' + bcn_cd + '/main/bannerGroups';
					}
				}
			});
			$("[name='datepicker']" ).datepicker({
				dateFormat: "yy.mm.dd",
				onSelect: function() {
					app.ractive.bannerGroup.updateModel();
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
				 		app.data.bannerGroup.bannerList[list[i].sort_ord - 1].sort_ord = i + 1;
				 	}
				 	
				 	sort();
				 	
				},
				distance : 10
			});
			
		} else {
			app.ractive.bannerGroup.reset();
			app.ractive.bannerGroup.set({
				bannerGroup : app.data.bannerGroup
			});
		}
	}
	
	getBannerList();
	
}


function BannerSection(banner) {
	var $main_hero_reg = $('#main-hero-reg');
	var bcn_cd = $main_hero_reg.data('bcn_cd');
	
	app.data.banner = banner && JSON.parse(JSON.stringify(banner)) || {};
	app.data.banner.bn_seq = app.data.banner.bn_seq || 'create';
	if(app.data.banner.bn_seq == 'create') {
		app.data.banner.div = 'INTR';
		app.data.banner.txt_colr_cd = '#FFFFFF';
		app.data.banner.bg_colr_cd = '#C31531';
		app.data.banner.bg_img_type = 1;
		app.data.banner.bn_group_seq = app.data.bannerGroup.bn_group_seq;
		
	}
	
	if(app.data.bannerGroup.bn_div == 2) {
		app.data.banner.bg_img_type = 2;
	}
	
	if(!app.ractive.banner) {
		app.ractive.banner = new Ractive({
			el : '#main-hero-reg-popup',
			template : '#tmpl-main-hero-reg-popup',
			data : {
				banner : app.data.banner,
				bn_div : app.data.bannerGroup.bn_div
			}
		});
		app.ractive.banner.on({
			'txt_colr_cd' : function(e, colorCode) {
				app.data.banner.txt_colr_cd = colorCode;
				app.ractive.banner.update();
			},
			'bg_colr_cd' : function(e, colorCode) {
				app.data.banner.bg_colr_cd = colorCode;
				app.ractive.banner.update();
			},
			'changeContent' : function(e) {
				app.data.banner.any_seq = null;
				app.data.banner.cont_titl = null;
				app.ractive.banner.update();
			},
			'selectPopup' : function(e, div) {
				if(div == 'TT') {
					messageRouter.trigger('show-popup-tenant', null, {tnt_type : 1, bcn_cd : bcn_cd});
				} else if(div == 'ET') {
					messageRouter.trigger('show-popup-event', null, {bcn_cd : bcn_cd});
				}
			},
			'close' : function(e) {
				if(!confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
					return;
				}
				
				$('#main-hero-reg-popup').css('display', 'none');
			},
			'save' : function(e) {
				
				if(app.data.banner.div != 'INTR' && !app.data.banner.any_seq) {
					return alert('컨텐츠를 선택해주세요.');
				}
				
				if(app.data.bannerGroup.bn_div == 1) {
					if(!app.data.banner.bn_titl) {
						return alert('브랜드명/매장명을 입력해주세요.');
					}
					if(!app.data.banner.great_titl1) {
						return alert('대 타이틀1을 입력해주세요.');
					}
					if(!app.data.banner.great_titl2) {
						return alert('대 타이틀2을 입력해주세요.');
					}
				}
				
				
				if(!app.data.banner.txt_colr_cd) {
					return alert('텍스트 색상을 선택해주세요.');
				}
				
				if(!app.data.banner.bg_img_type) {
					return alert('배경 이미지 타입을 선택해주세요.');
				}
				if(app.data.banner.bg_img_type == 1) {
					if(!app.data.banner.bg_colr_cd) {
						return alert('배경 이미지 색상을 선택해주세요.');
					}
				} else {
					if(!app.data.banner.bn_img_uri) {
						return alert('배경 이미지를 업로드 해주세요.');
					}
				}
				
				if(!confirm('입력한 내용으로 저장을 하시겠습니까?')) {
					return;
				}
				
				if(app.data.banner.bn_seq == 'create' && !app.data.banner.sort_ord) {
					app.data.banner.sort_ord = app.data.bannerGroup.bannerList.length + 1;
					app.data.banner.rnum = app.data.bannerGroup.bannerList.length + 1;
					app.data.bannerGroup.bannerList.push(app.data.banner);
				} else {
					for(var i = 0, iMax = app.data.bannerGroup.bannerList.length; i < iMax; i++) {
						if(app.data.bannerGroup.bannerList[i].sort_ord == app.data.banner.sort_ord) {
							app.data.bannerGroup.bannerList[i] = app.data.banner;
							break;
						}
					}
				}
				
				app.ractive.bannerGroup.update();
				
				$('#main-hero-reg-popup').css('display', 'none');
				
			},
			'image-delete' : function(e) {
				app.data.banner.bn_img_seq = null;
				app.data.banner.bn_img_uri = null;
				app.ractive.banner.update();
			}
		});
		
		app.imageUpload('#bg_image_upload', bcn_cd, function(err, image, el){
			if(err) {
				return alert('이미지 업로드 실패');
			}
			app.data.banner.bg_img_type = 2;
			app.data.banner.bn_img_seq = image.img_seq;
			app.data.banner.bn_img_uri = image.img_uri;
			app.ractive.banner.update();
		});
		
		messageRouter.on('select-popup-tenent', null, function(tenant){
			app.data.banner.any_seq = tenant.tnt_seq;
			app.data.banner.cont_titl = tenant.tnt_nm_ko;
			app.ractive.banner.update();
		});
		messageRouter.on('select-popup-event', null, function(event){
			app.data.banner.any_seq = event.evt_seq;
			app.data.banner.cont_titl = event.evt_titl;
			app.data.banner.small_titl = event.evt_strt_dt + ' - ' + event.evt_end_dt;
			app.ractive.banner.update();
		});
	} else {
		app.ractive.banner.reset();
		app.ractive.banner.set({
			banner : app.data.banner,
			bn_div : app.data.bannerGroup.bn_div
		});
	}
	
	
	$('#main-hero-reg-popup').css('display', 'block');
	
	
}

$(function() {
	new BannerGroupSection();
});