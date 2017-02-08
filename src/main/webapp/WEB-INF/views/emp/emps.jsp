<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>사원 목록</title>
	<script src="/js/lib/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <script src="/js/lib/common.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/common.css">
</head>
<body>

<h3>사원 목록 리스트</h3>
<div id="emp-list" class="contents">
<script id="tmpl-emp-list" type="text/ractive">

	
	<input type="button" value="로그아웃" on-click="do-logout" />
	<input type="button" value="인터셉터 테스트(쿠폰)" on-click="couponEmpPost" />
	<div id="fb" class="fb-share-button" data-href="" data-layout="icon" data-mobile-iframe="false"></div>
	<div id="fb-root"></div>
	<div id="status"></div>

	<div id="emp" class="emp-list-wrap">
		<ul class="emp-list">
			<li class="list-header">
				<span class="list-empno">사번</span>
				<span class="list-ename">사원이름</span>
				<span class="list-job">직급</span>
				<span class="list-sal">연봉</span>
				<span class="list-hiredate">고용일</span>
				<span class="list-button"></span>
          	</li>

		{{#empList:num}}
			<li class="list-body {{#if (num % 2 != 1) }}odd{{/if}}">
				<span class="list-empno" on-click=empPost:{{empno}}>{{empno}}</span>
				<span class="list-ename">{{ename}}</span>
				<span class="list-job">{{#if job == 'MANAGER'}}관리자{{else}}일반{{/if}}</span>
				<span class="list-sal">{{sal}}</span>
				<span class="list-hiredate">{{getYYYYMMDD(hiredate)}}</span>
				<span class="list-button"><input type="button" value="삭제" on-click="deleteEmp:{{empno}}" /></span>
			</li>
		{{/empList}}
          
		</ul>

		{{#paging}}
			<div class="paging-wrap">
				{{#if page_start > 1}}
					<img class="btn-prev" src="/images/btn-prev.png" on-click="movePage:{{paging.page_start - 1}}">
				{{/if}}
                <ul class="paging">
					{{#pages}}
                    <li {{#if paging.cur_page === this}} class="cur-page" {{/if}} on-click="movePage:{{this}}">{{this}}</li>
					{{/pages}}
                </ul>
				{{#if paging.page_end < paging.total_page_cnt}}
                	<img class="btn-next" src="/images/btn-next.png" on-click="movePage:{{paging.page_end + 1}}">
				{{/if}}
            </div>
		{{/paging}}

        </div>

</script>
</div>
	
	<script src="/js/app/emp/emps.js" type="text/javascript"></script>
	<script src="/js/lib/facebook.js" type="text/javascript"></script>
</body>
</html>