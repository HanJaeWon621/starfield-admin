
////////////////////////////////////////////////////////
// global variables

var locationLogData  = {
	locationLogList : null,
	paging : {}
	
};


var rtvlocationLog = new Ractive({
	el : '#locationLog-list',
	template : '#tmpl-locationLog-list',
	data : locationLogData
});
 

rtvlocationLog.on({
	'movePage' : function(o, page) {
		var offset = (page - 1) * locationLogData.paging.list_limit + 1; 
		
		getlocationLogList(offset, locationLogData.locationLogList.list_limit);
	}
});


var getLocationLogList = function(offset, limit) {
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/bcnCd/getLocationReadingLogs';
	restUri += '?';
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	Common.REST.get(restUri, {}, function(data) {
		locationLogData.locationLogList = data.list; 
		locationLogData.paging = data.paging; 
 
		locationLogData.paging.pages = [];
		  
		for(var  i = locationLogData.paging.page_start; i <= locationLogData.paging.page_end; ++i) {
			locationLogData.paging.pages.push(i);
        }
		//alert(data.empList[0].empno);
		rtvlocationLog.set(locationLogData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};



$(function() {
	getLocationLogList(1, 10);
});


