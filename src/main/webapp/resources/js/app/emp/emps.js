

////////////////////////////////////////////////////////
// global variables

var empData  = {
	empList : null,
	paging : {},
	getYYYYMMDD : function(timestamp, _type) {

		var date = new Date(timestamp);
		
	    var type = _type || ' ';

	    var arr = [];
	    arr.push(date.getFullYear() + '년');
	    arr.push(( (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1) ) + '월');
	    arr.push(( date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + '일');

	    return arr.join(type);
	}
};


////////////////////////////////////////////////////////
// global functions

////////////////////////////////////////////////////////
//initialize

var rtvEmp = new Ractive({
	el : '#emp-list',
	template : '#tmpl-emp-list',
	data : empData
});

rtvEmp.on({
	'movePage' : function(o, page) {
		
		var offset = (page - 1) * empData.paging.list_limit + 1; 
		
		getEmpList(offset, empData.paging.list_limit, null);
	},
	
	'deleteEmp' : function(o, empno) {
		console.log('delete target empno is ', empno);
		deleteEmp(empno);
	},
	
	'empPost' : function(o, empno) {
		console.log('empno is ', empno);
		location.href='/emps/'+empno;
	},

	'do-logout' : function() {
		console.log('logout');
		location.href='/logout';
	},
	
	'couponEmpPost' : function() {
		location.href='/coupon/emps/post';
	}
	
});


var getEmpList = function(offset, limit, q) {
	
	var offset = offset || 1;
	var limit = limit || 10;
	
	//var restUri = 'http://10.162.246.97:7777/rest/emps';
	var restUri = window.location.origin + '/ADM/rest/emps';
	restUri += '?';
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit;
	
	Common.REST.get(restUri, {}, function(data) {
		
		empData.empList = data.empList;
		empData.paging = data.paging;
				
		empData.paging.pages = [];
		
		for(var i = empData.paging.page_start; i <= empData.paging.page_end; ++i) {
        	empData.paging.pages.push(i);
        }
		
		rtvEmp.set(empData);
		
	}, function(data) {
		
		console.log('fail data is ', data);
		
	});
	
};


var deleteEmp = function(empno) {
	
	//var restUri = 'http://10.162.246.97:7777/rest/emps/' + empno;
	var restUri = window.location.origin + '/rest/emps/' + empno;
	
	Common.REST.del(restUri, {}, function(data) {
		
		console.log('success data is ', data);
		
		getEmpList();
		
	}, function(data) {
		
		console.log('fail data is ', data);
		
	});	
};


$(function() {

	getEmpList(1, 3);
});