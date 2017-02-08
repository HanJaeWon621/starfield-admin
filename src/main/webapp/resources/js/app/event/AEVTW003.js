var getHourList = function(){
	var hourList = [];
		
	for(var i = 0; i < 24; i++) {
		var hour = (i <= 9 ? "0" : "") + i + ":"+"00";
		hourList.push(hour);
	}
	
	return hourList;
}

var eventData = {
	pickDtYn : false,
	images : [],
	event : {},
	hourList : getHourList(),
	substring : function(str, i, j) {
		return str.substring(i, j);
	},
	brReplace : function(str) {
		str = str.replace(new RegExp('\n','g'), '<br>');
		str = str.replace(new RegExp('\r?\n','g'), '<br>');
		return str;
	}
};

var rtvEvent = new Ractive({
	el : '#event-reg-wrap',
	template : '#tmpl-event-reg',
	data : eventData
});

rtvEvent.on({
	'regEvent' : function() {
		if(!confirm("입력한 내용으로 저장을 하시겠습니까?")) {
			return;
		}
		
		//TODO .. enum 자동화 ㅠ
		setRelationData();
		
		if(!checkValidation()) {
			return;
		}
		
		if(evt_seq) {
			modifyEvent();
		} else {
			regEvent();
		}
	},
	'color' : function(o, color, type) {
		rtvEvent.set(type,color);
	},
	'imgDelete' : function(o, img_uri_type) {
		rtvEvent.set(img_uri_type, '');
	},
	'openTenantPopup' : function() {
		messageRouter.trigger('show-popup-tenant', null, {tnt_type : 1});
	},
	'preview' : function() {
		rtvPreview.set(eventData);
		$('#event-display-popup').show();
	},
	'cancel' : function() {
		if(!confirm("입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?")) {
			return;
		}
		
		if(eventData.event.evt_seq) {
			location.href = "/01/eventCoupon/events?evt_seq="+eventData.event.evt_seq;
		} else {
			location.href = "/01/eventCoupon/events"
		}
	}
});

rtvEvent.observe('event.web_list_open_img_uri', function(newValue, oldValue, keypath){
	console.log(newValue);
	if(newValue) {
		eventData.event.web_list_open_type = '2';
	}
});

rtvEvent.observe('event.mobi_list_open_img_uri', function(newValue, oldValue, keypath){
	console.log(newValue);
	if(newValue) {
		eventData.event.mobi_list_open_type = '2';
	}
});

rtvEvent.observe('event.web_list_open_type', function(newValue, oldValue, keypath){
	eventData.event.web_list_open_colr = eventData.event.web_list_open_colr || '1';
});

rtvEvent.observe('event.mobi_list_open_type', function(newValue, oldValue, keypath){
	eventData.event.mobi_list_open_colr = eventData.event.mobi_list_open_colr || '1';
});

rtvEvent.observe('event.evt_pick_plan_dt', function(newValue, oldValue, keypath){
	if(newValue) {
		eventData.pickDtYn = new Date(newValue.replace(/\./gi,'-')) <= new Date();
	}
});

rtvEvent.observe(
	'event.evt_dvi.codeCd', function (newValue, oldValue, keypath) {
		if(newValue == 'B') {
			$("#tnt-seq").removeClass('tr-hidden');
		} else {
			$("#tnt-seq").addClass('tr-hidden');
		}
	}
);
rtvEvent.observe(
	'event.evt_type.codeCd', function (newValue, oldValue, keypath) {
		if(newValue != '2') {
			$("#prvc-acq").addClass('tr-hidden');
			$("#opr-acq").addClass('tr-hidden');
		} else {
			$("#prvc-acq").removeClass('tr-hidden');
			$("#opr-acq").removeClass('tr-hidden');
		}
		
		if(newValue == '1' || newValue == '9') {
			$("#event-pick-plan-dt").addClass('tr-hidden');
			$("#event-link").removeClass('tr-hidden');
		} else {
			$("#event-pick-plan-dt").removeClass('tr-hidden');
			$("#event-link").addClass('tr-hidden');
		}
	}
);

rtvEvent.observe(
	'event.evt_end_dt', function (newValue, oldValue, keypath) {
		if(newValue) {
			newValue = new Date(newValue.replace(/\./gi,'-'));
			
			var end_dt = new Date(newValue);
			var now_dt = new Date();
			if(end_dt < now_dt) {
				rtvEvent.set('event.evt_stat.codeCd', '3');
			} else {
				rtvEvent.set('event.evt_stat.codeCd', '2');
			}
		}
	}
);

var rtvPreview = new Ractive({
	el : '#event-display-popup',
	template : '#tmpl-event-display-popup',
	data : eventData
});

rtvPreview.on({
	'close' : function() {
		$('.popup-wrap').hide();
	}
});

var regEvent = function() {
	var regData = JSON.parse(JSON.stringify(eventData.event));
	regData.evt_type = eventData.event.evt_type.codeCd;
	regData.evt_dvi = eventData.event.evt_dvi.codeCd;
	regData.evt_stat = eventData.event.evt_stat.codeCd;
	
	console.log('evnet is ', JSON.stringify(regData));
	
	Common.REST.post('/admin/rest/01/events', regData, function(data) {
		alert("이벤트가 저장되었습니다.");
		location.href = '/01/eventCoupon/events?evt_seq='+data.extra;
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var modifyEvent = function() {
	var modiData = JSON.parse(JSON.stringify(eventData.event));
	modiData.evt_type = eventData.event.evt_type.codeCd;
	modiData.evt_dvi = eventData.event.evt_dvi.codeCd;
	modiData.evt_stat = eventData.event.evt_stat.codeCd;
	
	console.log('evnet is ', JSON.stringify(modiData));
	
	Common.REST.put('/admin/rest/01/events/'+modiData.evt_seq, modiData, function(data) {
		alert("이벤트가 수정되었습니다.");
		location.href = '/01/eventCoupon/events?evt_seq='+modiData.evt_seq;
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

//var confirmEvent = function() {
//
//	Common.REST.put('/admin/rest/01/events/'+eventData.event.evt_seq, {sts : 1}, function(data) {
//		alert("승인되었습니다.");
//		Common.REST.get('/admin/rest/01/events/'+eventData.event.evt_seq, {}, function(data) {
//			rtvEvent.set('event.sts', data.sts);
//			rtvEvent.set('event.evt_app_dt', data.evt_app_dt);
//			rtvEvent.set('event.evt_app_id', data.evt_app_id);
//		}, function(data) {
//			console.log('fail data is ', data);
//		});
//	}, function(data) {
//		console.log('fail data is ', data);
//	});
//	
//};

var getEvent = function(evt_seq) {
	if(!evt_seq) {
		return;
	}
	
	Common.REST.get('/admin/rest/01/events/'+evt_seq, {}, function(data) {
		rtvEvent.set('event', data);
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var setRelationData = function() {

	var eventObj = eventData.event;
	
	// !경품(개인정보) (hide 개인정보활용동의, 운영방침동의)
	if(eventObj.evt_type.codeCd != '2') {
		eventObj.prvc_agre_term = '';
		eventObj.opr_info_agre_term = '';
	}
	
	// 공지 (hide 당첨자 발표 예정일)
	// !공지 (hide 링크 URL, 링크 버튼명)
	if(eventObj.evt_type.codeCd == '1' || eventObj.evt_type.codeCd == '9') {
		eventObj.evt_pick_plan_dt = '';
	} else {
		eventObj.evt_lnk_url = '';
		eventObj.evt_lnk_btn = '';
	}
	
	// !브랜드 (hide 브랜드)
	if(eventObj.evt_dvi.codeCd != 'B') {
		eventObj.tnt_seq = '';
	}
	
	// 목록 노출 이미지
	if(eventObj.web_list_open_type == '1') {
		eventObj.web_list_open_img = '';
		
	} else {
		eventObj.web_list_open_colr = '';
	}
	
	// 목록 노출 이미지
	if(eventObj.mobi_list_open_type == '1') {
		eventObj.mobi_list_open_img = '';
	} else {
		eventObj.mobi_list_open_colr = '';
	}

}

var checkValidation = function() {
	
	var eventObj = eventData.event;
	
	if(eventObj.evt_dvi.codeCd == 'B' && !eventObj.tnt_seq) {
		alert("진행 브랜드 명을 설정해주세요.");
		return false;
	}
	
	if(!eventObj.evt_titl) {
		alert("국문 타이틀을 입력해주세요.");
		return false;
	}
	
	if(!eventObj.evt_strt_dt || !eventObj.evt_end_dt) {
		alert("이벤트 기간을 설정해주세요.");
		return false;
	}

	if(new Date(eventObj.evt_strt_dt.replace(/\./gi,'-')) > new Date(eventObj.evt_end_dt.replace(/\./gi,'-')) ) {
		alert("이벤트 종료 일자가 시작 일자보다 빠릅니다.");
		return false;
	}
	
	if(eventObj.evt_type.codeCd != '1' && eventObj.evt_type.codeCd != '9' && !eventObj.evt_pick_plan_dt) {
		alert("당첨자 발표일을 설정해주세요.");
		return false;
	}
	
	if(!eventObj.evt_post_strt_dt || !eventObj.evt_post_end_dt) {
		alert("게시 일정을 설정해주세요.");
		return false;
	}

	var evt_post_strt_dt = new Date(eventObj.evt_post_strt_dt.replace(/\./gi,'-'));
	evt_post_strt_dt.setHours(eventObj.evt_post_strt_hour.substring(0,2));
	evt_post_strt_dt.setMinutes(eventObj.evt_post_strt_hour.substring(3,5));
	
	var evt_post_end_dt = new Date(eventObj.evt_post_end_dt.replace(/\./gi,'-'))
	evt_post_end_dt.setHours(eventObj.evt_post_end_hour.substring(0,2));
	evt_post_end_dt.setMinutes(eventObj.evt_post_end_hour.substring(3,5));
	
	if(evt_post_strt_dt > evt_post_end_dt) {
		alert("게시 종료 일자가 시작 일자보다 빠릅니다.");
		return false;
	}
	
	if(!eventObj.evt_img) {
		alert("이미지를 등록해주세요.");
		return false;
	}
	
	var event_lnk_yn = $("input[type=radio][name=event-link]:checked").val();
	if( (eventObj.evt_type.codeCd == '1' || eventObj.evt_type.codeCd == '9') && event_lnk_yn == 'Y' ) {
	
		if(!eventObj.evt_lnk_url || !eventObj.evt_lnk_btn) {
			alert("링크 URL 정보를 입력해주세요.");
			return false;
		}
		
		if(eventObj.evt_type.codeCd != '9'){
			if(eventObj.evt_lnk_url.indexOf('http://') != 0 && eventObj.evt_lnk_url.indexOf('https://') != 0) {
				alert("링크 URL 형식이 잘못되었습니다.\nhttp:// 또는 https:// 로 시작되어야합니다.");
				return false;
			}	
		}
		
	}
	
	if(eventObj.evt_type.codeCd == '2') {
		if(!eventObj.prvc_agre_term) {
			alert("개인정보 활용 동의를 입력해주세요.");
			return false;
		}
		
		if(!eventObj.opr_info_agre_term) {
			alert("운영방침 동의를 입력해주세요.");
			return false;
		}
	}
	
	if(eventObj.web_list_open_type == '2' && !eventObj.web_list_open_img) {
		alert("목록 노출 이미지(WEB)를 선택해주세요.");
		return false;
	}
	
	if(eventObj.mobi_list_open_type == '2' && !eventObj.mobi_list_open_img) {
		alert("목록 노출 이미지(MOBILE)를 선택해주세요.");
		return false;
	}
	
	return true;
}

messageRouter.on('select-popup-tenent', null, function(tenant){
	rtvEvent.set('event.tnt_seq', tenant.tnt_seq);
	rtvEvent.set('event.tnt_nm_ko', tenant.tnt_nm_ko);
});

$(function() {
	getEvent(evt_seq);
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy.mm.dd",
		onSelect: function() {
			rtvEvent.updateModel();
		}
	});
	
	$('.file-uploader-wrap > .basic-btn').each(function(){
		var img_el_selector = "#" + $(this).parents(".img-uploader").attr('id') + " > .file-uploader-wrap > .basic-btn";
		app.imageUpload(img_el_selector, '01', function(err, image, img_el_selector){
			if(err) {
				return alert('이미지 업로드 실패');
			}
			
			var $targetParent = $(img_el_selector).parents(".img-uploader");

			eventData.event[$targetParent.attr("id")] = image.img_seq;
			eventData.event[$targetParent.attr("id")+"_uri"] = image.img_uri;
			
			rtvEvent.set(eventData);
		});
	});
	
	$("input[type=radio][name=event-link]").change(function(){
		if($(this).val() == 'Y') {
			$('#event-link input[type=text]').attr('disabled', false);
		} else {
			$('#event-link input[type=text]').val('');
			$('#event-link input[type=text]').attr('disabled', true);
		}
	});
});
