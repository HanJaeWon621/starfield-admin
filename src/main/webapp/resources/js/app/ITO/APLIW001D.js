var app = app || {};

app.ractive = app.ractive || {};
app.ractive.locationInfo = null;

app.data = app.data || {};
app.data.locationInfo = {};


function LocationInfoSection() {
	var $location_reg = $('#location-reg');
	var bcn_cd = $location_reg.data('bcn_cd');
	var plid_mng_seq = $location_reg.data('plid_mng_seq');
	
	
	function getLocationInfoDetail(plid_mng_seq) {
		if(plid_mng_seq == 'create') {
			locationInfoDetail({
				plid_mng_seq : plid_mng_seq,
				name : '',
				phone_num : '',
				bcn_cd : "01",
				req_del_dttm : '',
				act_dttm :'',
				act_yn : 'N',
				sts : 0
			});
		} else {
			Common.REST.get('/admin/rest/' + bcn_cd + '/ITO/locInfoManage/' + plid_mng_seq, {}, function(result) {				
				locationInfoDetail(result);
			}, function(data) {
				alert('개인 위치정보 관리 불러오기 실패');
			});
		}
	}
	
	function locationInfoDetail(result){
		app.data.locationInfo = result;
		
		if(!app.ractive.locationInfo) {
			app.ractive.locationInfo = new Ractive({
				el : '#location-reg',
				template : '#tmpl-location-reg',
				data : {
					locationInfo : app.data.locationInfo
				}
			});
			app.ractive.locationInfo.on({
				'cancel' : function() {
					if(!confirm('입력/수정정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
						return;
					}
					location.href = '/' + bcn_cd + '/ITO/locInfoManage';
				},
				'save' : function() {
					if(confirm('입력한 내용으로 저장을 하시겠습니까?')) {
						
						if(plid_mng_seq == 'create') {
							Common.REST.post('/admin/rest/' + bcn_cd + '/ITO/locInfoManage', app.data.locationInfo, function(result) {				
								if(result.code == 0) {
									location.href = '/' + bcn_cd + '/ITO/locInfoManage';
								} else {
									alert('개인 위치정보 관리 저장 실패');
								}
							}, function(data) {
								alert('개인 위치정보 관리 저장 실패');
							});
						} else {
							Common.REST.put('/admin/rest/' + bcn_cd + '/ITO/locInfoManage/' + plid_mng_seq, app.data.locationInfo, function(result) {				
								if(result.code == 0) {
									location.href = '/' + bcn_cd + '/ITO/locInfoManage';
								} else {
									alert('개인 위치정보 관리 저장 실패');
								}
							}, function(data) {
								alert('개인 위치정보 관리 저장 실패');
							});
						}
					}
				}
			});
		} else {
			app.ractive.locationInfo.reset();
			app.ractive.locationInfo.set({
				locationInfo : app.data.locationInfo
			});
		}
	}
	
	getLocationInfoDetail(plid_mng_seq);
}

$(function() {
	new LocationInfoSection();

});