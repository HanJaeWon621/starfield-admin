<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
<div id="account-mng" class="contents-area">
	<script id="tmpl-account-mng-list" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">계정 관리 - 관리자 계정 목록</h1>
		<div class="search-box">
			<div class="search-input-wrap">
				<input class="search-input" type="text" value="{{search_text}}" placeholder="아이디를 입력하세요" on-keydown="on-search-keydown">
				<div class="basic-btn search gold" on-click="on-search">검색</div>
			</div>
		</div>
		<div class="list-top">
			<ul class="inline-list">
				<li on-click="down-list">리스트다운</li><li on-click="reg-admin">추가</li>
			</ul>
		</div>
		<table id="account-mng-list" class="basic-list">
			<colgroup>
				<col width="100">
				<col width="*">
				<col width="*">
				<col width="*">
				<col width="160">
				<col width="140">
			</colgroup>
			<thead>
				<tr>
					<th {{#if sort_target == 'rnum' && sort_way == 'desc'}}class="selected"{{/if}} on-click="sort-list:'rnum'">NO</th>
					<th {{#if sort_target == 'adm_id' && sort_way == 'desc'}}class="selected"{{/if}} on-click="sort-list:'adm_id'">아이디</th>
					<th {{#if sort_target == 'role_nm' && sort_way == 'desc'}}class="selected"{{/if}} on-click="sort-list:'role_nm'">권한</th>
					<th {{#if sort_target == 'adm_dept' && sort_way == 'desc'}}class="selected"{{/if}} on-click="sort-list:'adm_dept'">소속</th>
					<th {{#if sort_target == 'reg_dttm' && sort_way == 'desc'}}class="selected"{{/if}} on-click="sort-list:'reg_dttm'">등록일</th>
					<th {{#if sort_target == 'sts' && sort_way == 'desc'}}class="selected"{{/if}} on-click="sort-list:'sts'">상태</th>
				</tr>
			</thead>
			<tbody>
				{{#showList}}
				<tr>
					<td>{{rnum}}</td>
					<td on-click="move-detail:{{adm_seq}}" class="cursor-p ellipsis">{{adm_id}}</td>
					<td>{{role_nm}}</td>
					<td>{{adm_dept}}</td>
					<td>{{getYYYYMMDD(reg_dttm)}}</td>
					<td class="{{#if sts == 101}}green{{elseif sts == 105}}blue{{else}}red{{/if}}">{{sts_desc}}</td><!-- .blue: 등록 .green : 사용중 / .red: 미사용 -->
				</tr>
				{{/}}
			</tbody>
		</table>
		{{#paging}}
		<div class="paging-wrap">
			<ul class="paging inline-list">
			<li class="prev {{#if paging.page_start == 1}}off{{/if}}" on-click="page-move:{{paging.page_start - 1}}">PREV</li>
			{{#pages}}
			<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="page-move:{{this}}">{{this}}</li>
			{{/pages}}
			<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" on-click="page-move:{{paging.page_end + 1}}">NEXT</li>
			</ul>
           </div>
		{{/paging}}
	</div>
	</script>
</div>
<script src="/js/app/account/AACT004.js" type="text/javascript"></script>
</body>
</html>