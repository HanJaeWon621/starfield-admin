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

<h3>쿠폰 목록 리스트</h3>
<div id="coupon-list" class="contents">
<script id="tmpl-coupon-list" type="text/ractive">
	
	<input type="button" value="쿠폰 등록" on-click="reqCoupon" />
	<input type="button" value="엘셀 다운" on-click="reqCoupon" />
	<div id="emp" class="emp-list-wrap">
		<ul class="emp-list"> 
			<li class="list-header">
				<span class="list-empno">번호</span>
				<span class="list-empno">시나리오 타이틀</span>
				<span class="list-empno">푸시 대상</span>
				<span class="list-empno">푸시 쿠폰</span>
				<span class="list-empno">푸시 상황</span>
				<span class="list-empno">노출 현황</span>
				<span class="list-empno">푸시 카운팅</span>
				<span class="list-empno">푸시 쿠폰<br/>다운로드</span>
          	</li>

		{{#couponList:num}}
			<li class="list-body {{#if (num % 2 != 1) }}odd{{/if}}">
				<span class="list-empno" on-click=modifyCoupon:{{cp_seq}}>{{cp_seq}}</span>
				<span class="list-ename"></span>
				<span class="list-empno">{{#if cp_sum_sale_div_cd == 'MANAGER'}}관리자{{else}}일반{{/if}}</span>
				<span class="list-empno">{{cp_use_plce_div_cd}}</span>
				<span class="list-empno">{{cp_kind_cd}}</span>
				<span class="list-empno">{{cp_div_cd}}</span>
				<span class="list-empno">{{cp_titl}}</span>
				<span class="list-empno">{{all_iss_cp_cnt}}</span>
			</li>
		{{/couponList}}
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
	
	<script src="/js/app/coupon/ACPNW011.js" type="text/javascript"></script>
</body>
</html>