
////////////////////////////////////////////////////////
// global variables

var mem_seq = null;
var locationReqViewData  = {
	locationReqView : {},
	locationUsrList : null, 
	filter :{}
};
 
 
var rtvLocation = new Ractive({
	el : '#location-mng',
	template : '#tmpl-location-mng',
	data : locationReqViewData
});
  
rtvLocation.on({
	'openRequester' : function(o) { 
		openRequester(1,10);
	},
	'locationUsrClose' : function(o) { 
		locationUsrClose();
	},
	'locationUsrSave' : function(o,data) { 
		locationUsrSave(data);
	},
	'ckUsr' : function(o,seq) { 
		ckUsr(seq);
	},
	'saveLocationReqView' : function(o) { 
		saveLocationReqView();
	},
	'closeLocationReqView' : function(o) {
		location.href='/01/location/reqViews';
	},
	'pageMove' : function(o,page) { 
		var offset = (page - 1) * locationReqViewData.paging.list_limit + 1; 
		  
		openRequester(offset, locationReqViewData.paging.list_limit);
	}
}); 

var ckUsr = function(seq){
	mem_seq = seq;
}

var locationUsrClose = function(){
	$("#coupon-reg-popup").hide();
	$("#sh_text_type").val('');
	$("#sh_text").val('');
}
 
var locationUsrSave = function(data){
	if($(':radio[name="ckUsr"]:checked').val()){
		var usrInfo = $(':radio[name="ckUsr"]:checked').val().split(',');
		locationReqViewData.locationReqView.req_adm_nm = usrInfo[0];
		locationReqViewData.locationReqView.req_adm_seq = usrInfo[4];
		locationReqViewData.locationReqView.cust_id = usrInfo[5];
		$("#req_adm_nm").val(usrInfo[0]);
		$("#req_adm_seq").val(usrInfo[4]); 
		$("#tel_num").val(usrInfo[1]+"- **** -"+usrInfo[3]);
		
		$("#coupon-reg-popup").hide(); 
	}
	$("#sh_text_type").val('');
	$("#sh_text").val('');
}

var openRequester = function(offset, limit) {
	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getRequester';
	restUri += '?'; 
	restUri += 'offset=' + offset;   
	restUri += '&limit=' + limit;
	restUri += '&sh_text_type=' + $("#sh_text_type").val();
	restUri += '&sh_text=' + $("#sh_text").val(); 
	Common.REST.get(restUri, {}, function(data) {
		locationReqViewData.locationUsrList = data.list; 
		locationReqViewData.paging = data.paging; 
 
		locationReqViewData.paging.pages = [];
		 
		for(var  i = locationReqViewData.paging.page_start; i <= locationReqViewData.paging.page_end; ++i) {
			locationReqViewData.paging.pages.push(i);
        }
		    
		 
		rtvLocation.set(locationReqViewData);
		$("#coupon-reg-popup").show(); 
	}, function(data) {
		console.log('fail data is ', data);
	}); 
	
}


var saveLocationReqView = function(){
	url = "/rest/01/regLocationReqView";
	Common.REST.post(window.location.origin + url, locationReqViewData.locationReqView, function(data) {
		alert('등록완료!!');
		
		location.href='/01/location/reqViews';
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}

$(function() {
	var today = new Date()
	var todayString = today.getFullYear() + '년 '
		+ (today.getMonth() + 1) + '월 '
		+ today.getDate() + '일 '
		+ today.getHours() + '시 '
		+ today.getMinutes() + '분';
	$('#current-time').val(todayString);
});


