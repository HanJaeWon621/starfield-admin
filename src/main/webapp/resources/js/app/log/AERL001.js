var errorData = {
	errorList : null,
	paging : "",
};

var ractive = new Ractive({
	el : '#errorLog',
	template : '#errorLogTemplate',
	data : errorData
});

var getListE5S;

ractive.on({
	'pageMove' : function(o, page) {
		getPages(o, page);
	},
	'sortColumn' : function(o, order_key) {
		var order_type = "desc";
		if($(o.node).attr("order-type") == "desc") {
			order_type = "asc";
			$(o.node).attr("order-type", "asc");
		} else {
			order_type = "asc";
			$(o.node).attr("order-type", "desc");
		}
		var q = "order_key=" + order_key + "," + "order_type=" + order_type;
		getErrorList(null, null, q);
	},
	'autoRefresh' : function(o) {
		var status = "off";
		if($(o.node).attr("status") == "off"){
			var autoOn = $('#auto').addClass('auto-refresh');
			$('#status-message').text('5초 간격으로 최신데이터로 갱신합니다.' + "\n" + currentTime());
			status = "on";
			$(o.node).attr("status", "on");
			getListE5S = setInterval(function() {
				getErrorList()}, 5000);
		} else {
			status = "off";
			$('#status-message').text('');
			$(o.node).attr("status", "off");
			var autoOff = $('#auto').removeClass('auto-refresh');
			var pauseGetList = clearTimeout(getListE5S);
		}
	},
	'goToFind' : function() {
		var errorTypeArr = [];
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();
		var severity = $('#severity-type').val();
		var serverName = $('#search-server-name').val().trim();
		var moduleName = $('#search-module-name').val().trim();
		var methodName = $('#search-method-name').val().trim();
		
		var q = 'sDate='+ sDate + ',' + 'eDate=' + eDate + ',' + 'SEVERITY=' + severity + ',' + 'SERVER_NM=' + serverName + ',' + 'MODULE_NM=' + moduleName + ',' + 'METHOD_NM=' + methodName;
		getErrorList(null, null, q);
	}	
});

//전체 error 리스트 가져오기 
var getErrorList = function(offset, limit, q) {
	var offset = offset || 1;
	var limit = limit || 100;
	var q = q;
	var restUri = '/admin/rest/error';
	var reqData = {'offset' : offset, 'limit' : limit, 'q' : q};
	Common.REST.get(restUri, reqData, function(data) {
		errorData.errorList = data.list;
		errorData.paging = data.paging;
		errorData.paging.pages = [];
		
		for(var i = errorData.paging.page_start; i <= errorData.paging.page_end; ++i) {
			errorData.paging.pages.push(i);
        }
		ractive.set(errorData);
	}, function(data) {
		console.log('fail data is ', data);
	});
}

//페이지 정보 가져오기
var getPages = function(o, page) {
	if(page > 0
			&& page <= errorData.paging.total_page_cnt
			&& page != errorData.paging.cur_page) {
		
		var offset = (page - 1) * errorData.paging.list_limit + 1;
		getErrorList(offset, errorData.paging.list_limit);
	}
}


//데이터 자동 갱신 버튼 클릭시 시간 출력
function currentTime(){
	var date = new Date();
	var dateArr = [];
	dateArr.push((date.getFullYear()+"-"));
	dateArr.push((date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1 + "-") : date.getMonth() + 1 + "-");
	dateArr.push((date.getDate() < 10 ? '0' + date.getDate() : date.getDate() + "\n"));
	dateArr.push((date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ":");
	dateArr.push((date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ":");
	dateArr.push((date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()));
	return dateArr.join('');
	
}

// input datepicker 현재 날짜 디폴트 설정 
function configDate() {
	 var date = new Date();

	 var year = date.getFullYear();
	 var month = date.getMonth() + 1;
	 var day = date.getDate();

	 if (month < 10) month = "0" + month;
	 if (day < 10) day = "0" + day;

	 var today = year + "-" + month + "-" + day;       
	 $("#sDate").attr("value", today);
	 $("#eDate").attr("value", today);
}

//검색어 입력 후 마우스 포커스 시 검색어 초기화
function initSearch(){
	var searchServerName = $('#search-server-name');
	var searchModuleName = $('#search-module-name');
	var searchMethodName = $('#search-method-name');
	
	searchServerName.focus(function() {
		searchServerName.val('');
	});
	searchModuleName.focus(function() {
		searchModuleName.val('');
	});
	searchMethodName.focus(function() {
		searchMethodName.val('');
	});
};

//실행시 실행될 메서드
$(function() {
	initSearch();
	configDate();
	getErrorList();
});

