<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>사원 목록</title>
	<script src="/js/lib/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <script src="/js/lib/common.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="/css/common.css">
</head>
<body>

<h3>위치 정보 로그 열람 리스트</h3>
<div id="actionLog-list" class="contents">
<script id="tmpl-actionLog-list" type="text/ractive">
<br/>cust_id  <input type="text">
<br/>
조회기간 <input type="text">~<input type="text">
<br/><input type="button" value="검색">
	<div id="emp" class="emp-list-wrap">
		<ul class="emp-list"> 
			<li class="list-header">
				<span class="list-empno">번호</span>
				<span class="list-empno">다바아스id</span>
				<span class="list-empno">고객id</span>
				<span class="list-empno">회원구분코드</span>
				<span class="list-empno">로그유형</span>
				<span class="list-empno">이벤트대분류코드</span>
				<span class="list-empno">이벤트중분류코드</span>
				<span class="list-empno">이벤트발생값</span>
				<span class="list-empno">등록일시</span>
          	</li>

		{{#actionLogList:num}}
			<li class="list-body {{#if (num % 2 != 1) }}odd{{/if}}">
				<span class="list-empno">{{num}}</span>
				<span class="list-ename">{{uuid}}</span>
				<span class="list-ename">{{cust_id}}</span>
				<span class="list-ename">{{mbr_div_cd}}</span>
				<span class="list-ename">{{log_type}}</span>
				<span class="list-ename">{{evt_div_cd1}}</span>
				<span class="list-ename">{{evt_div_cd2}}</span>
				<span class="list-ename">{{evt_val}}</span>
				<span class="list-ename">{{reg_dttm}}</span>
			</li>
		{{/actionLogList}}
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
	
	<script src="/js/app/log/SAMLW003.js" type="text/javascript"></script>
</body>
</html>