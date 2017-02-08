var getHourList = function(){
	var hourList = [];
		
	for(var i = 0; i < 24; i++) {
		var hourNm = (i <= 9 ? "0" : "") + i + ":"+"00";
		var hourCd = (i <= 9 ? "0" : "") + i +"00";
		var halfNm = (i <= 9 ? "0" : "") + i + ":"+"30";
		var halfCd = (i <= 9 ? "0" : "") + i + "30";
		
		hourList.push({nm : hourNm, cd : hourCd});
		hourList.push({nm : halfNm, cd : halfCd});
	}
	
	return hourList;
}

var tenantData = {
	before_opr_sts : "0",
	cate_list : [{}],
	images : [],
	tenant : {cate_list : [{}]},
	hourList : getHourList(),
	substring : function(str, i, j) {
		return str.substring(i, j);
	}
};

var rtvTenant = new Ractive({
	el : '#tenant-reg-wrap',
	template : '#tmpl-tenant-reg',
	data : tenantData
});

rtvTenant.observe('tenant.opr_sts.codeCd', function (newValue, oldValue, keypath) {
	tenantData.before_opr_sts = oldValue;
});

rtvTenant.on({
	'regTenant' : function() {

		if(!checkValidation()) {
			return;
		}
		
		if(!confirm('입력한 내용으로 저장을 하시겠습니까?')) {
			return;
		}
		
		if(tnt_seq) {
			modifyTenant();
		} else {
			regTenant();
		}
	},
	'imgDelete' : function(o, img_uri_type) {
		console.log(img_uri_type)
		rtvTenant.set(img_uri_type, '');
	},
	'changeFirstCate' : function(o, idx) {
		tenantData.tenant.modify_cate_yn = true;
		getSecondCategory(tenantData.tenant.cate_list[idx].mama_cate_seq, idx);
	},
	'changeSecondCate' : function(o, idx) {
		tenantData.tenant.modify_cate_yn = true;
	},
	'addCate' : function() {
		tenantData.tenant.modify_cate_yn = true;
		tenantData.cate_list.push({firstCategories : tenantData.cate_list[0].firstCategories});
	},
	'delCate' : function(o, idx) {
		tenantData.tenant.modify_cate_yn = true;
		rtvTenant.splice('tenant.cate_list', idx, 1);
		rtvTenant.splice('cate_list', idx, 1);
	},
	'addTag' : function(o, idx) {
		if(!$('#input-tag-box').val()) {
			alert("추가할 키워드를 입력해주세요.");
			return;
		}
		tenantData.tenant.tnt_tag_list = tenantData.tenant.tnt_tag_list || [];
		tenantData.tenant.tnt_tag_list.push($('#input-tag-box').val());
		$('#input-tag-box').val("");
		
		rtvTenant.update();
	},
	'delTag' : function(o, idx) {
		rtvTenant.splice('tenant.tnt_tag_list', idx, 1);
	},
	'cancel' : function() {
		if(!confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
			return;
		}
		location.href = "/01/stores/tenants"
	},
	'changeSts' : function() {

		var sts = tenantData.tenant.sts;
		var oprSts = tenantData.tenant.opr_sts.codeCd;
		
		if(sts == 1 && oprSts != 1) {
			alert("노출은 운영상태가 정상운영일 경우에만 가능합니다.");
			rtvTenant.set('tenant.sts', 0);
		}
	},
	'changeOprSts' : function() {
		var sts = tenantData.tenant.sts;
		var oprSts = tenantData.tenant.opr_sts.codeCd;
		
		var unfinishedData = checkTenantData();
		
		if(oprSts == 1 && unfinishedData.length > 0) {
			alert("테이터가 완결되지 않은 경우 정상운영으로 변경할 수 없습니다.\n미입력 데이터 : "+unfinishedData.join(', '));
			rtvTenant.set('tenant.opr_sts.codeCd', tenantData.before_opr_sts);
			rtvTenant.set('tenant.sts', 0);
		}
		
		if(sts == 1 && oprSts != 1) {
			alert("노출은 운영상태가 정상운영일 경우에만 가능합니다.");
			rtvTenant.set('tenant.sts', 0);
		}
	},
	'checkByte' : function(o, limit) {
		$(o.node).val(fnCut($(o.node).val(), limit));
	}
});

var regTenant = function() {
	var regData = JSON.parse(JSON.stringify(tenantData.tenant));
	regData.tnt_type = 1;
	regData.opr_sts = regData.opr_sts.codeCd;
	regData.tnt_tag = regData.tnt_tag_list ? regData.tnt_tag_list.join(";") : "";
	
	console.log('tenant is ', JSON.stringify(regData));
	
	Common.REST.post('/admin/rest/01/tenants', regData, function(data) {
		alert("매장이 저장되었습니다.");
		location.href = '/01/stores/tenants/reg?tnt_seq='+data.extra;
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var modifyTenant = function() {
	var modiData = JSON.parse(JSON.stringify(tenantData.tenant));
	modiData.opr_sts = modiData.opr_sts.codeCd;
	modiData.tnt_type = modiData.tnt_type.codeCd;
	modiData.tnt_tag = modiData.tnt_tag_list.join(";");
	
	console.log('tenant is ', JSON.stringify(modiData));
	
	Common.REST.put('/admin/rest/01/tenants/'+modiData.tnt_seq, modiData, function(data) {
		alert("매장이 수정되었습니다.");
		location.href = '/01/stores/tenants/reg?tnt_seq='+modiData.tnt_seq;
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var getTenant = function(evt_seq) {
	if(!tnt_seq) {
		return;
	}
	
	Common.REST.get('/admin/rest/01/tenants/'+tnt_seq, {}, function(data) {
		rtvTenant.set('tenant', data);
		
		if(data.tnt_type.codeCd == 2) {
			$('.dept-read-only').addClass('no-border');
			$('.dept-read-only input, .dept-read-only select').attr('disabled', true);
		}
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var checkValidation = function() {
	var tenantObj = tenantData.tenant;
	
	if(!tenantObj.tnt_nm_ko) {
		alert("국문 매장명을 입력해주세요.");
		return false;
	}
	
	var cateArr = new Array();
	
	for(var i = 0, iMax = tenantObj.cate_list.length; i < iMax; i++) {
		var cate = tenantObj.cate_list[i];
		
		if(cateArr.indexOf(cate.cate_seq) > -1) {
			alert("중복된 카테고리가 있습니다.");
			return false;
		}

		if(!cate.cate_seq) {
			alert("선택되지 않은 카테고리가 있습니다.\n카테고리 삭제 또는 선택해주세요.");
			return false;
		}
		
		cateArr.push(cate.cate_seq);
	}

	return true;
}

var checkTenantData = function() {
	var tenantObj = tenantData.tenant;
	
	var unfinishedData = [];
	
	if(!tenantObj.tnt_nm_ko) { unfinishedData.push('국문 매장명'); }
	if(!tenantObj.tnt_nm_en) { unfinishedData.push('영문 매장명'); }
	if(!tenantObj.tnt_desc_ko && tenantObj.tnt_type.codeCd == 1) { unfinishedData.push('국문 서브 타이틀'); }
	if(!tenantObj.tnt_desc_en && tenantObj.tnt_type.codeCd == 1) { unfinishedData.push('영문 서브 타이틀'); }
	if(!tenantObj.fl) { unfinishedData.push('층'); }
	if(!tenantObj.room_num) { unfinishedData.push('호수'); }
	if(!tenantObj.busi_tnt_cd && tenantObj.tnt_type.codeCd == 1) { unfinishedData.push('테넌트 코드'); }
	if((!tenantObj.coct_strt_prid || !tenantObj.coct_end_prid) && tenantObj.tnt_type.codeCd == 1) { unfinishedData.push('계약기간'); }
	if(!tenantObj.map_id) { unfinishedData.push('MAP ID'); }
	if(!tenantObj.zone_id) { unfinishedData.push('ZONE ID'); }
	if(!tenantObj.x_ctn_cord || !tenantObj.y_ctn_cord) { unfinishedData.push('X/Y 좌표'); }
	if(!tenantObj.poi_lev && tenantObj.tnt_type.codeCd == 1) { unfinishedData.push('POI 레벨'); }
	
	//전화번호
	var tel_size = 0;
	if(tenantObj.tnt_tel_num1) { tel_size++; }
	if(tenantObj.tnt_tel_num2) { tel_size++; }
	if(tenantObj.tnt_tel_num3) { tel_size++; }
	
	if(tel_size < 2) {
		unfinishedData.push('전화번호');
	}
	
	if(!tenantObj.open_hr_min || !tenantObj.end_hr_min) { unfinishedData.push('운영시간(평일)'); }
	if(!tenantObj.irgu_open_hr_min || !tenantObj.irgu_end_hr_min) { unfinishedData.push('운영시간(휴일)'); }
	
	if(tenantObj.cate_list.length < 1) {
		unfinishedData.push('카테고리');
	}
	
	if(!tenantObj.img_main_bg_web_uri) { unfinishedData.push('웹 상단 이미지'); }
	if(!tenantObj.img_main_bg_mobi_uri) { unfinishedData.push('모바일 상단 이미지'); }
	if(!tenantObj.img_main_bg_logo_uri) { unfinishedData.push('투명 BI'); }
	if(!tenantObj.img_logo_uri) { unfinishedData.push('기준 BI 1'); }
	if(!tenantObj.img_thmb_uri) { unfinishedData.push('기준 BI 2'); }
	if(!tenantObj.img_mobi_logo_uri) { unfinishedData.push('APP 저용량 BI'); }

	return unfinishedData;
}

function getFirstCategory() {
	var restUri = "/admin/rest/01/categories/TENANT";
	var reqData = {};
	
	Common.REST.get(restUri, reqData, function(rootCate) {

		reqData = {offset : 0, limit : -1};
		Common.REST.get(restUri+"/"+rootCate.cate_seq, reqData, function(data) {
			var iMax = tenantData.tenant.cate_list && tenantData.tenant.cate_list.length > 0 ? tenantData.tenant.cate_list.length : tenantData.cate_list.length;
			for(var i = 0; i < iMax; i++) {
				tenantData.cate_list[i] = tenantData.cate_list[i] || {};
				tenantData.cate_list[i].firstCategories = data.list;
				tenantData.tenant.cate_list[i] = tenantData.tenant.cate_list[i] || {};
				tenantData.tenant.cate_list[i].mama_cate_seq = tenantData.tenant.cate_list[i].mama_cate_seq || "";
				tenantData.tenant.cate_list[i].cate_seq = tenantData.tenant.cate_list[i].cate_seq || "";
				
				if(tenantData.tenant.cate_list[i].mama_cate_seq) {
					getSecondCategory(tenantData.tenant.cate_list[i].mama_cate_seq, i);
				}
			}
			
			rtvTenant.update();
		}, function(data) {
			console.log('fail data is ', data);
		});
	}, function(data) {
		console.log('fail data is ', data);
	});
}

function getSecondCategory(cateSeq, idx) {
	var restUri = "/admin/rest/01/categories/TENANT";
	var reqData = {offset : 0, limit : -1};
	
	Common.REST.get(restUri+"/"+cateSeq, reqData, function(data) {
		rtvTenant.set('cate_list['+idx+'].secondCategories', data.list);
	}, function(data) {
		console.log('fail data is ', data);
	});
}

$(function() {
	
	getTenant(tnt_seq);
	
	if(!tenantData.tenant.tnt_type) {
		tenantData.tenant.tnt_type = {};
		tenantData.tenant.tnt_type.codeCd = 1;
	}
	
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy.mm.dd",
		onSelect: function() {
			rtvTenant.updateModel();
		}
	});
	
	$('.file-uploader-wrap > .basic-btn').each(function(){
		var img_el_selector = "#" + $(this).parents(".img-uploader").attr('id') + " > .file-uploader-wrap > .basic-btn";
		app.imageUpload(img_el_selector, '01', function(err, image, img_el_selector){
			if(err) {
				return alert('이미지 업로드 실패');
			}
			
			var $targetParent = $(img_el_selector).parents(".img-uploader");

//			tenantData.tenant[$targetParent.attr("id")] = image.img_seq;
			tenantData.tenant[$targetParent.attr("id")+"_uri"] = image.img_uri;
			
			rtvTenant.set(tenantData);
		});
	});
	
	getFirstCategory();
});
