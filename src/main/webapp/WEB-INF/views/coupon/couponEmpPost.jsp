<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>사원 등록</title>

	<script src="/js/lib/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <script src="/js/lib/common.js" type="text/javascript"></script>
    
    <link rel="stylesheet" type="text/css" href="/css/common.css">
</head>
<body>
	<div id="emp-reg" class="contents">
		<script id="tmpl-emp-reg" type="text/ractive">
		<h3>사원 등록 페이</h3>
		{{#emp}}
			<p>사원이름 : <input id="ename" type="text" value="{{ename}}"></p>
			<p>직   종 : <input id="job" type="text" value="{{job}}"></p>
			<p>연   봉 : <input id="sal" type="text" value="{{sal}}"></p>
			<p>
				부   서 : <select id="dept" value="{{deptno}}">
							{{#deptList}}<option value="{{deptno}}">{{dname}}</option>{{/deptList}}
						 </select>
			</p>
			<p>
				<input type="button" value="등록" on-click="regEmp" />
				<input type="button" value="취소" on-click="resetEmp"/>
				<input type="button" value="목록" on-click="empList"/>
			</p>
		{{/emp}}
		</script>
	</div> 

	<script src="/js/app/coupon/couponEmpPost.js" type="text/javascript"></script>
</body>
</html>