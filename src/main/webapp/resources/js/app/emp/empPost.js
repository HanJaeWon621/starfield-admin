
////////////////////////////////////////////////////////
// global variables

var empData  = {
	deptList : null,
	emp : {
		ename : null,
		job : null,
		sal : null,
		deptno : null
	}
};


////////////////////////////////////////////////////////
// global functions

////////////////////////////////////////////////////////
//initialize

var rtvEmp = new Ractive({
	el : '#emp-reg',
	template : '#tmpl-emp-reg',
	data : empData
});

rtvEmp.on({
	'regEmp' : function() {
		console.log('emp is ', empData.emp);
		regEmp(empData.emp);
	},
	
	'resetEmp' : function() {
		resetEmpForm();
	},
	
	'empList' : function() {
		console.log('목록이동');
		location.href='/emps';
	}
});


var getDeptList = function() {
	//Common.REST.get('http://localhost:7777/rest/depts', {}, function(data) {
	Common.REST.get(window.location.origin + '/rest/depts', {}, function(data) {
		console.log('success data is ', data);
		
		empData.deptList = data;
		rtvEmp.set(empData);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
};

var regEmp = function(emp) {
	//Common.REST.post('http://localhost:7777/rest/emps', emp, function(data) {
	Common.REST.get(window.location.origin + '/rest/emps', emp, function(data) {
		console.log('success data is ', data);
		alert('등록완료!!');
		
		rtvEmp.set(empData);
		resetEmpForm();
		
		
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var resetEmpForm = function() {
	var emp = {
		ename : null,
		job : null,
		sal : null,
		deptno : empData.deptList[0].deptno
	}
	
	empData.emp = emp;
	rtvEmp.set(empData);
};

$(function() {
	
	getDeptList();
	
});






