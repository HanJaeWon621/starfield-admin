var app = app || {};

app.ractive = app.ractive || {};
app.ractive.locationInfoList = null;

var hashObject  = {
	offset : 1,
	limit : 10,
	sort_name : 'rnum',
	sort_order : 'desc',
	sts : -1,
	act_yn : '',
	date_search : false,
	date_type : 'REQ_DEL_DTTM', //REQ_DEL_DTTM - 삭제 요청일, ACT_DTTM - 조치일
	stat_date : '',
	end_date : ''
};


function hashConversion(hashObject){
	var hashArr = [];
	for (var k in hashObject){
	    if (hashObject.hasOwnProperty(k)) {
	    	hashArr.push(k + '=' +  hashObject[k])
	    }
	}
	return '#' + hashArr.join('&');
}

function LocationInfoSection() {
	
	var $location_mng = $('#location-mng');
	var bcn_cd = $location_mng.data('bcn_cd');
	
	window.onhashchange = function() {
		var hash = location.hash;	
		hash = hash.replace('#', '');
		
		var hashSplit = hash.split('&');
		
		for(var i = 0, iMax = hashSplit.length; i < iMax; i ++) {
			var objectSplit = hashSplit[i].split('=');
			if(objectSplit[0] == 'date_search') {
				if(objectSplit[1] == 'true') {
					hashObject[objectSplit[0]] = true;
				} else {
					hashObject[objectSplit[0]] = false;
				}
			} else {
				hashObject[objectSplit[0]] = objectSplit[1];
			}
			
		}
		getLocationInfoList(hashObject);
	};
	
	
	function getLocationInfoList(hashObject) {
		Common.REST.get('/admin/rest/' + bcn_cd + '/ITO/locInfoManage', hashObject, function(result) {
			locationInfoList(result);
		}, function(data) {
			alert('개인 위치정보 관리 정보 불러오기 실패');
		});
	}
	
	function locationInfoList(result){
		
		result.paging.pages = [];
		for(var i = result.paging.page_start; i <= result.paging.page_end; ++i) {
			result.paging.pages.push(i);
        }
		
		
		if(!app.ractive.locationInfoList) {
			app.ractive.locationInfoList = new Ractive({
				el : '#location-mng',
				template : '#tmpl-location-mng',
				data : {
					list : result.list,
					paging : result.paging,
					hashObject : hashObject
				}
			});
			app.ractive.locationInfoList.on({
				'detail' : function(e, plid_mng_seq) {
					location.href = '/' + bcn_cd + '/ITO/locInfoManage/' + plid_mng_seq;
				},
				'pageMove' : function(e, page) {
					if(!$(e.node).hasClass('off')) {
						hashObject.offset = (page - 1) * hashObject.limit + 1;
						location.hash = hashConversion(hashObject);
					}
				},
				'sort' : function(e, sort_name) {
					$(e.node).siblings().removeClass('selected');
					$(e.node).addClass('selected');
					if(hashObject.sort_name == sort_name) {
						if(hashObject.sort_order == 'desc') {
							hashObject.sort_order = 'asc';
						} else {
							hashObject.sort_order = 'desc';
						}
						
					} else {
						hashObject.sort_name = sort_name;
						hashObject.sort_order = 'desc';
					}
					location.hash = hashConversion(hashObject);
				},
				'search' : function(e) {
					hashObject.offset = 1;
					location.hash = hashConversion(hashObject);
				},
				'create' : function(e) {
					location.href = '/' + bcn_cd + '/ITO/locInfoManage/create';
				},
				'download' : function(e) {
					location.href = '/' + bcn_cd + '/ITO/locInfoManage/download'
				}
			});
			
			$("[name='datepicker']" ).datepicker({
				dateFormat: "yy.mm.dd",
				onSelect: function() {
					app.ractive.locationInfoList.updateModel();
				}
			});
		} else {
			app.ractive.locationInfoList.reset();
			app.ractive.locationInfoList.set({
				list : result.list,
				paging : result.paging,
				hashObject : hashObject
			});
		}
	}
	
}

$(function() {
	new LocationInfoSection();
	
	if(!location.hash) {
		location.hash = hashConversion(hashObject);
	}
	
	window.onhashchange();
});