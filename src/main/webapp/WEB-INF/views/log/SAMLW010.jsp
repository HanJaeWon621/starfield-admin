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

<h3>• ITO 관리 > 위치정보이용 . 제공 사실 확인자료</h3>
<div id="locationLog-list" class="contents">
<script id="tmpl-locationLog-list" type="text/ractive">
<br/>조회기간<input type="text" value="">~<input type="text" value="">
<br/>검색어검색<select><option>취급자</option></select><input type="text" value="">
<input type="button" value="검색"><input type="button" value="검색초기화">
	<div id="emp" class="">
		<ul class="">  
			<li class="list-header"> 
				<span class="list-empno">번호</span>
				<span class="list-empno">사용자</span>
				<span class="list-empno">취득경로</span>
				<span style="width: 390px;">제공서비스</span>
				<span class="list-empno">이용일시</span>
          	</li>
 
			<li class="list-body ">
				<span class="list-empno">4</span>
				<span class="list-empno">정주현</span> 
				<span class="list-empno">IOS</span>
				<span style="width: 390px;">스타필드 하남 지도 네비게이션/모바일 폰 서비스</span>
				<span class="list-empno">2016.07.18 13:15:12</span> 
			</li>
			<li class="list-body ">
				<span class="list-empno">3</span>
				<span class="list-empno">홍길동</span> 
				<span class="list-empno">Andorid</span>
				<span style="width: 390px;">스타필드 하남 지도 네비게이션/모바일 폰 서비스</span>
				<span class="list-empno">2016.07.18 13:15:12</span> 
			</li>
			<li class="list-body ">
				<span class="list-empno">2</span>
				<span class="list-empno">이길동</span> 
				<span class="list-empno">Andorid</span>
				<span style="width: 390px;">스타필드 하남 지도 네비게이션/모바일 폰 서비스</span>
				<span class="list-empno">2016.07.18 13:15:12</span> 
			</li>
			<li class="list-body ">
				<span class="list-empno">1</span>
				<span class="list-empno">김길동</span> 
				<span class="list-empno">IOS</span>
				<span style="width: 390px;">스타필드 하남 지도 네비게이션/모바일 폰 서비스</span>
				<span class="list-empno">2016.07.18 13:15:12</span> 
			</li>
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