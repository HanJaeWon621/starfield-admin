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

<h3>위치정보 로그 열람 리스트</h3>
<div id="locationLog-list" class="contents">
<script id="tmpl-locationLog-list" type="text/ractive">
<br/>검색어 열람자<input type="text" value="이길동">
<br/>
조회기간 <input type="text">~<input type="text">
<br/><input type="button" value="검색">
	<div id="emp" class="">
		<ul class=""> 
			<li class="list-header"> 
				<span class="list-empno">번호</span>
				<span class="list-empno">열람자</span>
				<span class="list-empno">사유</span>
				<span style="width: 390px;">디바이스ID</span>
				<span class="list-empno">고객ID</span>
				<span style="width: 390px;">처리정보</span>
				<span class="list-empno">등록일자</span>
				<span class="list-empno">요청자</span>
          	</li>
 
		{{#locationLogList:num}}
			<li class="list-body {{#if (num % 2 != 1) }}odd{{/if}}">
				<span class="list-empno">{{num+1}}</span>
				<span class="list-empno">이길동</span> 
				<span class="list-empno">삭제요청</span>
				<span style="width: 390px;">{{uuid}}</span>
				<span class="list-empno">{{cust_id}}</span>
				<span style="width: 415px;">{{prc_info}}...</span> 
				<span class="list-ename">{{reg_dttm}}</span>
				<span class="list-ename">홍길동</span>
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
	
	<script src="/js/app/log/SAMLW004.js" type="text/javascript"></script>
</body>
</html>