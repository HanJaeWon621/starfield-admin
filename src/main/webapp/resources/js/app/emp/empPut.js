
////////////////////////////////////////////////////////
// global variables

var empData  = {
	deptList : null,
	emp : {
		empno : null,
		ename : null,
		job : null,
		sal : null,
		deptno : null,
		dname : null
	}
};


////////////////////////////////////////////////////////
// global functions

////////////////////////////////////////////////////////
//initialize

var rtvEmp = new Ractive({
	el : '#emp-mod',
	template : '#tmpl-emp-mod',
	data : empData
});

rtvEmp.on({
	'modifyEmp' : function() {
		console.log('emp is ', empData.emp);
		modifyEmp(empData.emp);
	},
	
	'resetEmp' : function() {
		console.log('입력초기화');
		resetEmpForm();
	}, 

	'empList' : function() {
		console.log('목록이동');
		location.href='/emps';
	}
	
});

//부서목록 조회
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

//입력 초기화
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

//사원정보 조회
var getEmp = function(empno) {
	//var restUri = 'http://localhost:7777/rest/emps/' + empno;
	var restUri = window.location.origin + '/rest/emps/' + empno;
	
	Common.REST.get(restUri, { id: "empno", value: empno}, function(data) {
		console.log('success data is ', data);
		
		empData.emp = data;
		rtvEmp.set(empData);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
};

//사원정보 수정
var modifyEmp = function(emp) {
	//Common.REST.put('http://localhost:7777/rest/emps', emp, function(data) {
	Common.REST.get(window.location.origin + '/rest/emps', emp, function(data) {
		console.log('success data is ', data);
		alert('수정완료!!');
		
	}, function(data) {
		console.log('fail data is ', data);
		
	});
	
};

$(function() {
	
	getDeptList();
	getEmp(getUriArrVal()[2]);
	
});






