var PUSH_WEB_URI = '/01/operation/pushes';
var PUSH_REST_URI = '/admin/rest/01/pushes';

var getHourList = function(){
	var hourList = [];
		
	for(var i = 0; i < 24; i++) {
		var hour = (i <= 9 ? "0" : "") + i;
		hourList.push(hour);
	}
	
	return hourList;
}

var getHourList = function(){
	var hourList = [];
		
	for(var i = 0; i < 24; i++) {
		var hourNm = (i <= 9 ? "0" : "") + i + '시';
		var hourCd = (i <= 9 ? "0" : "") + i;
		
		hourList.push({nm : hourNm, cd : hourCd});
	}
	
	return hourList;
}

var getMinuteList = function(){
	var minuteList = [];
		
	for(var i = 0; i < 60; i++) {
		var minuteNm = (i <= 9 ? "0" : "") + i + '분';
		var minuteCd = (i <= 9 ? "0" : "") + i;
		minuteList.push({nm : minuteNm, cd : minuteCd});
	}
	
	return minuteList;
}

var pushData = {
	push : {},
	hourList : getHourList(),
	minuteList : getMinuteList()
};

var rtvPush = new Ractive({
	el : '#push-reg',
	template : '#tmpl-push-reg',
	data : pushData
});

rtvPush.on({
	'regPush' : function() {
		setRelationData();
		
		if(!checkValidation()) {
			return;
		}

		var msg = (pushData.push.send_type.codeCd == '1' ? "즉시발송을 선택하신 경우 1분 이내에 발송됩니다.\n" : "") + "입력한 내용으로 발송 하시겠습니까?";
		if(!confirm(msg)) {
			return;
		}
		
		if(push_mng_seq) {
			modifyPush();
		} else {
			regPush();
		}
	},
	'cancel' : function() {
		if(!confirm("입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?")) {
			return;
		}

		location.href = PUSH_WEB_URI;

	},
	'confirm' : function() {
		location.href = PUSH_WEB_URI;
	}
});

rtvPush.observe(
	'push.send_type.codeCd', function (newValue, oldValue, keypath) {
		if(newValue == '2') {
			$('.reserve-only').show()
		} else {
			$('.reserve-only').hide()
		}
	}
);

var regPush = function() {
	var regData = JSON.parse(JSON.stringify(pushData.push));
	regData.send_type = pushData.push.send_type.codeCd;
	regData.send_yn = 'N';
	
	Common.REST.post(PUSH_REST_URI, regData, function(data) {
		location.href = PUSH_WEB_URI;
	}, function(data) {
		alert(JSON.parse(data).errMsg);
	});
	
};

var modifyPush = function() {
	var modiData = JSON.parse(JSON.stringify(pushData.push));
	modiData.send_type = pushData.push.send_type.codeCd;
	modiData.send_yn = null;
	
	console.log(modiData);
	
	Common.REST.put(PUSH_REST_URI + '/' + modiData.push_mng_seq, modiData, function(data) {
		location.href = PUSH_WEB_URI;
	}, function(data) {
		alert(JSON.parse(data).errMsg);
	});
	
};

var getPush = function(push_msg_seq) {
	if(!push_msg_seq) {
		return;
	}
	
	Common.REST.get(PUSH_REST_URI + '/' + push_msg_seq, {}, function(data) {
		rtvPush.set('push', data);
		
		if(data.send_dttm) {
			$('.before-send-only input').addClass('no-border');
			$('.before-send-only input, .before-send-only select').attr('disabled', true);
		}
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var setRelationData = function() {

	var pushObj = pushData.push;
	
	if(pushObj.send_type.codeCd != '2') {
		pushObj.reserve_dt = '';
		pushObj.reserve_hour = '00';
		pushObj.reserve_minute = '00';
	}
	
}

var checkValidation = function() {
	
	var pushObj = pushData.push;
	
	if(pushObj.send_type.codeCd == '2' && (!pushObj.reserve_dt || !pushObj.reserve_hour || !pushObj.reserve_minute )) {
		alert("예약 일시를 입력해주세요.");
		return false;
	}

	var reserve_dttm = new Date(pushObj.reserve_dt.replace(/\./gi,'-'))
	reserve_dttm.setHours(pushObj.reserve_hour);
	reserve_dttm.setMinutes(pushObj.reserve_minute);
	
	if(pushObj.send_type.codeCd == '2' && reserve_dttm < new Date()) {
		alert("예약 일시가 현재 일시 보다 빠릅니다.");
		return false;
	}
	
	if(!pushObj.push_msg) {
		alert("푸시 메시지를 입력해주세요.");
		return false;
	}
	
	return true;
}

$(function() {
	getPush(push_mng_seq);
	
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy.mm.dd",
		onSelect: function() {
			rtvPush.updateModel();
		}
	});
});
