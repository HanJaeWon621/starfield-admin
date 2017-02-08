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

var facilityGroupData = {
	facilityGroup : {},
	hourList : getHourList()
};

var rtvFacilityGroup = new Ractive({
	el : '#facility-group-reg-wrap',
	template : '#tmpl-facility-group-reg',
	data : facilityGroupData
});

rtvFacilityGroup.observe(
	'facilityGroup.faci_type.codeCd', function (newValue, oldValue, keypath) {
		if(newValue == 'Y') {
			$("#facility-group-reg-list tr").removeClass('tr-hidden');
		} else {
			$("#facility-group-reg-list tr").addClass('tr-hidden');
			$("#facility-group-reg-list .normal-show").removeClass('tr-hidden');
		}
	}
);

rtvFacilityGroup.on({
	'regFacilityGroup' : function() {

		if(!checkValidation()) {
			return;
		}
		
		if(!confirm('입력한 내용으로 저장을 하시겠습니까?')) {
			return;
		}
		
		if(conv_faci_seq) {
			modifyFacilityGroup();
		} else {
			regFacilityGroup();
		}
	},
	'imgDelete' : function(o, img_uri_type) {
		console.log(img_uri_type)
		rtvFacilityGroup.set(img_uri_type, '');
	},
	'cancel' : function() {
		if(!confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
			return;
		}
		location.href = "/01/stores/facilities"
	},
	'checkByte' : function(o, limit) {
		$(o.node).val(fnCut($(o.node).val(), limit));
	}
});

var regFacilityGroup = function() {
	var regData = JSON.parse(JSON.stringify(facilityGroupData.facilityGroup));
	regData.faci_type = regData.faci_type.codeCd;
	
	console.log('facilityGroup is ', JSON.stringify(regData));
	
	Common.REST.post('/admin/rest/01/facilities', regData, function(data) {
		alert("편의시설 그룹이 저장되었습니다.");
		location.href = "/01/stores/facilities"
	}, function(data) {
		console.log('fail data is ', data);
	});
};

var modifyFacilityGroup = function() {
	var modiData = JSON.parse(JSON.stringify(facilityGroupData.facilityGroup));
	modiData.faci_type = modiData.faci_type.codeCd;
	
	console.log('facilityGroup is ', JSON.stringify(modiData));
	
	Common.REST.put('/admin/rest/01/facilities/'+modiData.conv_faci_seq, modiData, function(data) {
		alert("편의시설 그룹이 수정되었습니다.");
		location.href = "/01/stores/facilities"
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var getFacilityGroup = function() {
	if(!conv_faci_seq) {
		return;
	}
	
	Common.REST.get('/admin/rest/01/facilities/'+conv_faci_seq, {}, function(data) {
		rtvFacilityGroup.set('facilityGroup', data);
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var checkValidation = function() {
	var fgObj = facilityGroupData.facilityGroup;
	
	if(!fgObj.conv_faci_nm_ko) {
		alert("편의시설 그룹명(국문)을 입력해주세요.");
		return false;
	}
	
	if(!fgObj.img_seq_icon) {
		alert("기본 아이콘을 선택해주세요.");
		return false;
	}
	
	if(fgObj.faci_type.codeCd == 'Y') {
		if(!fgObj.conv_faci_nm_en) {
			alert("편의시설 그룹명(영문)을 입력해주세요.");
			return false;
		}
		
		if(!fgObj.conv_faci_nm_cn) {
			alert("편의시설 그룹명(중문)을 입력해주세요.");
			return false;
		}
		
		if(!fgObj.conv_faci_nm_jp) {
			alert("편의시설 그룹명(일문)을 입력해주세요.");
			return false;
		}
		
		if(!fgObj.conv_faci_desc_ko) {
			alert("편의시설 이용안내 대표(국문)을 입력해주세요.");
			return false;
		}
		
		if(!fgObj.conv_faci_desc_en) {
			alert("편의시설 이용안내 대표(영문)을 입력해주세요.");
			return false;
		}
		
		if(!fgObj.conv_faci_desc_cn) {
			alert("편의시설 이용안내 대표(중문)을 입력해주세요.");
			return false;
		}
		
		if(!fgObj.conv_faci_desc_jp) {
			alert("편의시설 이용안내 대표(일문)을 입력해주세요.");
			return false;
		}
		
		if(!fgObj.img_seq_facl_image) {
			alert("APP용 아이콘을 선택해주세요.");
			return false;
		}
	}
	

	
	
	return true;
}

//var checkTenantData = function() {
//	var tenantObj = tenantData.tenant;
//	
//	var unfinishedData = [];
//	
//	if(!tenantObj.tnt_nm_ko) { unfinishedData.push('국문 매장명'); }
//	if(!tenantObj.tnt_nm_en) { unfinishedData.push('영문 매장명'); }
//	if(!tenantObj.tnt_desc_ko && tenantObj.tnt_type.codeCd == 1) { unfinishedData.push('국문 서브 타이틀'); }
//	if(!tenantObj.tnt_desc_en && tenantObj.tnt_type.codeCd == 1) { unfinishedData.push('영문 서브 타이틀'); }
//	if(!tenantObj.fl) { unfinishedData.push('층'); }
//	if(!tenantObj.room_num) { unfinishedData.push('호수'); }
//	if(!tenantObj.busi_tnt_cd && tenantObj.tnt_type.codeCd == 1) { unfinishedData.push('테넌트 코드'); }
//	if((!tenantObj.coct_strt_prid || !tenantObj.coct_end_prid) && tenantObj.tnt_type.codeCd == 1) { unfinishedData.push('계약기간'); }
//	if(!tenantObj.map_id) { unfinishedData.push('MAP ID'); }
//	if(!tenantObj.zone_id) { unfinishedData.push('ZONE ID'); }
//	if(!tenantObj.x_ctn_cord || !tenantObj.y_ctn_cord) { unfinishedData.push('X/Y 좌표'); }
//	if(!tenantObj.poi_lev && tenantObj.tnt_type.codeCd == 1) { unfinishedData.push('POI 레벨'); }
//	
//	//전화번호
//	var tel_size = 0;
//	if(tenantObj.tnt_tel_num1) { tel_size++; }
//	if(tenantObj.tnt_tel_num2) { tel_size++; }
//	if(tenantObj.tnt_tel_num3) { tel_size++; }
//	
//	if(tel_size < 2) {
//		unfinishedData.push('전화번호');
//	}
//	
//	if(!tenantObj.open_hr_min || !tenantObj.end_hr_min) { unfinishedData.push('운영시간(평일)'); }
//	if(!tenantObj.irgu_open_hr_min || !tenantObj.irgu_end_hr_min) { unfinishedData.push('운영시간(휴일)'); }
//	
//	if(tenantObj.cate_list.length < 1) {
//		unfinishedData.push('카테고리');
//	}
//	
//	if(!tenantObj.img_main_bg_web_uri) { unfinishedData.push('웹 상단 이미지'); }
//	if(!tenantObj.img_main_bg_mobi_uri) { unfinishedData.push('모바일 상단 이미지'); }
//	if(!tenantObj.img_main_bg_logo_uri) { unfinishedData.push('투명 BI'); }
//	if(!tenantObj.img_logo_uri) { unfinishedData.push('기준 BI 1'); }
//	if(!tenantObj.img_thmb_uri) { unfinishedData.push('기준 BI 2'); }
//	if(!tenantObj.img_mobi_logo_uri) { unfinishedData.push('APP 저용량 BI'); }
//
//	return unfinishedData;
//}

$(function() {
	
	getFacilityGroup(conv_faci_seq);
	
	$('.file-uploader-wrap > .basic-btn').each(function(){
		var img_el_selector = "#" + $(this).parents(".img-uploader").attr('id') + " > .file-uploader-wrap > .basic-btn";
		app.imageUpload(img_el_selector, '01', function(err, image, img_el_selector){
			if(err) {
				return alert('이미지 업로드 실패');
			}
			
			var $targetParent = $(img_el_selector).parents(".img-uploader");

			facilityGroupData.facilityGroup[$targetParent.attr("id")] = image.img_seq;
			facilityGroupData.facilityGroup[$targetParent.attr("id")+"_uri"] = image.img_uri;
			
			rtvFacilityGroup.set(facilityGroupData);
		});
	});
});
