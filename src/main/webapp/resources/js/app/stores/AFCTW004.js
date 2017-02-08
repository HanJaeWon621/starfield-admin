var facilityData = {
	facilityGroup : {},
	facility : {}
};

var rtvFacility = new Ractive({
	el : '#facility-reg-wrap',
	template : '#tmpl-facility-reg-wrap',
	data : facilityData
});

rtvFacility.on({
	'regFacility' : function() {

		if(!checkValidation()) {
			return;
		}
		
		if(!confirm('입력한 내용으로 저장을 하시겠습니까?')) {
			return;
		}
		
		if(conv_faci_dtl_seq) {
			modifyFacility();
		} else {
			regFacility();
		}
	},
	'imgDelete' : function(o, img_uri_type) {
		rtvFacility.set(img_uri_type, '');
	},
	'cancel' : function() {
		if(!confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
			return;
		}
		location.href = '/01/stores/facilities/detail?conv_faci_seq='+conv_faci_seq;
	},
	'changeSts' : function() {
		var sts = facilityData.facility.sts;
		
		var unfinishedData = checkFacilityData();
		
		if(sts == 1 && unfinishedData.length > 0) {
			alert("테이터가 완결되지 않은 경우 노출로 변경할 수 없습니다.\n미입력 데이터 : "+unfinishedData.join(', '));
			rtvFacility.set('facility.sts', 0);
		}
	},
	'checkByte' : function(o, limit) {
		$(o.node).val(fnCut($(o.node).val(), limit));
	}
});

var regFacility = function() {
	var regData = JSON.parse(JSON.stringify(facilityData.facility));
	regData.poi_lev = regData.poi_lev || 1;
	
	console.log('facility is ', JSON.stringify(regData));
	
	Common.REST.post('/admin/rest/01/facilities/'+conv_faci_seq, regData, function(data) {
		alert("매장이 저장되었습니다.");
		location.href = '/01/stores/facilities/detail?conv_faci_seq='+conv_faci_seq;
	}, function(data) {
		console.log('fail data is ', data);
	});
};

var modifyFacility = function() {
	var modiData = JSON.parse(JSON.stringify(facilityData.facility));
	
	console.log('facility is ', JSON.stringify(modiData));
	
	Common.REST.put('/admin/rest/01/facilities/'+conv_faci_seq+'/'+modiData.conv_faci_dtl_seq, modiData, function(data) {
		alert("매장이 수정되었습니다.");
		location.href = '/01/stores/facilities/detail?conv_faci_seq='+conv_faci_seq;
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var getFacility = function() {
	if(!conv_faci_dtl_seq) {
		return;
	}
	
	Common.REST.get('/admin/rest/01/facilities/'+conv_faci_seq+'/'+conv_faci_dtl_seq, {}, function(data) {
		rtvFacility.set('facility', data);
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var getFacilityGroup = function() {
	if(!conv_faci_seq) {
		return;
	}
	
	Common.REST.get('/admin/rest/01/facilities/'+conv_faci_seq, {}, function(data) {
		rtvFacility.set('facilityGroup', data);
	}, function(data) {
		console.log('fail data is ', data);
	});
};

var checkValidation = function() {
	var faciObj = facilityData.facility;
	
	var unfinishedData = checkFacilityData();
	
	if(faciObj.sts == 1 && unfinishedData.length > 0) {
		alert("테이터가 완결되지 않은 경우 노출로 변경할 수 없습니다.\n미입력 데이터 : "+unfinishedData.join(', '));
		rtvFacility.set('facility.sts', 0);
		return false;
	}
	
	if(!faciObj.conv_faci_nm_ko) {
		alert("편의시설명을 입력해주세요.");
		return false;
	}
	
	if(!faciObj.room_num) {
		alert("호수를 입력해주세요.");
		return false;
	}

	return true;
}

var checkFacilityData = function() {
	var faciObj = facilityData.facility;
	
	var unfinishedData = [];
	
	if(!faciObj.map_id) { unfinishedData.push('MAP ID'); }
	if(!faciObj.zone_id) { unfinishedData.push('ZONE ID'); }
	if(!faciObj.x_ctn_cord || !faciObj.y_ctn_cord) { unfinishedData.push('X/Y 좌표'); }

	return unfinishedData;
}

$(function() {	
	getFacility();
	getFacilityGroup();
});
