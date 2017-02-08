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
 
<h3>위치 정보 로그 리스트</h3>
<div id="locationLog-list"  class="contents">
<script id="tmpl-locationLog-list" type="text/ractive">
<br/>검색어cust_id  <input type="text" value="C0019">
<br/>
조회기간 <input type="text">~<input type="text">
<br/><input type="button" value="검색">
	<div id="emp" class="emp-list-wrap">
		<ul class="emp-list"> 
			<li class="list-header">
				<span class="list-empno">번호</span>
				<span style="width: 290px;">디바이스ID</span>
				<span class="list-empno">고객ID</span>
				<span class="list-empno">법인코드</span>
				<span class="list-empno">브랜드코드</span>
				<span class="list-empno">지점코드</span>
				<span class="list-empno">층</span>
				<span class="list-empno">존ID</span>
				<span class="list-empno">X좌표</span>
				<span class="list-empno">Y좌표</span>
				<span class="list-empno">등록일시</span>
          	</li>

		{{#locationLogList:num}}
			<li class="list-body {{#if (num % 2 != 1) }}odd{{/if}}">
				<span class="list-empno">{{num}}</span>
				<span class="list-jobe">{{uuid}}</span>
				<span class="list-ename">{{cust_id}}</span>
				<span class="list-ename">{{corp_cd}}</span>
				<span class="list-ename">{{brnd_cd}}</span>
				<span class="list-ename">{{bcn_cd}}</span>
				<span class="list-ename">{{fl}}</span>
				<span class="list-ename">{{zone_id}}</span> 
				<span class="list-ename">{{x_ctn_cord}}</span>
				<span class="list-ename">{{y_ctn_cord}}</span>
				<span class="list-ename">{{reg_dttm}}</span>
			</li>
		{{/locationLogList}}
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
	
	<script src="/js/app/log/SAMLW002.js" type="text/javascript"></script>
</body>
</html>