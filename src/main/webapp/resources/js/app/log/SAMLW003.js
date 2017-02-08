
////////////////////////////////////////////////////////
// global variables

var actionLogData  = {
	actionLogList : null,
	paging : {}
	
};


var rtvlocationLog = new Ractive({
	el : '#actionLog-list',
	template : '#tmpl-actionLog-list',
	data : actionLogData
});
 

rtvActionLog.on({
	'movePage' : function(o, page) {
		var offset = (page - 1) * actionLogData.paging.list_limit + 1; 
		
		getActionLogList(offset, actionLogData.actionLogList.list_limit);
	}
});


var getActionLogList = function(offset, limit) {
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/bcnCd/getActionLogs';
	restUri += '?';
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	Common.REST.get(restUri, {}, function(data) {
		actionLogData.actionLogList = data.list; 
		actionLogData.paging = data.paging; 
 
		actionLogData.paging.pages = [];
		  
		for(var  i = actionLogData.paging.page_start; i <= actionLogData.paging.page_end; ++i) {
			actionLogData.paging.pages.push(i);
        }
		//alert(data.empList[0].empno);
		rtvActionLog.set(actionLogData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};



$(function() {
	getActionLogList(1, 10);
});


