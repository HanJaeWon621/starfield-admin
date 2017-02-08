<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="history" class="contents-area">
	<script id="tmpl-history" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">관리자 활동이력 조회</h1>
		<div class="search-box">
			<div class="date-picker">
				<input type="text" name="datepicker" value="{{hashObject.start_date}}">
			</div>
			<span>~</span>
			<div class="date-picker">
				<input type="text" name="datepicker" value="{{hashObject.end_date}}">
			</div>
			<div class="search-input-wrap">
				<input class="search-input" type="text" placeholder="아이디를 입력하세요" value="{{hashObject.searchKeyword}}">
				<div class="basic-btn search gold" on-click="search">조회</div>
			</div>
			<span class="comment">* 최대 6개월까지 조회 가능합니다.</span>
		</div>
		<table id="history" class="basic-list">
			<colgroup>
				<col width="50">
				<col width="150">
				<col width="150">
				<col width="150">
				<col width="150">
				<col width="*">
				<col width="180">
			</colgroup>
			<thead>
				<tr>
					<th {{#if(hashObject.sort_name == 'rnum')}}class="selected"{{/if}} on-click="sort:rnum">NO</th>
					<th {{#if(hashObject.sort_name == 'cd_nm')}}class="selected"{{/if}} on-click="sort:cd_nm">지점</th>
					<th {{#if(hashObject.sort_name == 'adm_id')}}class="selected"{{/if}} on-click="sort:adm_id">아이디</th>
					<th {{#if(hashObject.sort_name == 'adm_nm')}}class="selected"{{/if}} on-click="sort:adm_nm">이름</th>
					<th {{#if(hashObject.sort_name == 'role_nm')}}class="selected"{{/if}} on-click="sort:role_nm">권한</th>
					<th {{#if(hashObject.sort_name == 'acc_pefm_dtl')}}class="selected"{{/if}} on-click="sort:acc_pefm_dtl">활동이력</th>
					<th {{#if(hashObject.sort_name == 'acc_dttm')}}class="selected"{{/if}} on-click="sort:acc_dttm">날짜</th>
				</tr>
			</thead>
			<tbody>
				{{#list}}
				<tr>
					<td>{{rnum}}</td>
					<td>{{cd_nm}}</td>
					<td>{{adm_id}}</td>
					<td>{{adm_nm}}</td>
					<td>{{role_nm}}</td>
					<td class="ellipsis">{{acc_pefm_dtl}}</td>
					<td>{{acc_dttm}}</td>
				</tr>
				{{/list}}
			</tbody>
		</table>
		{{#paging}}
		<div class="paging-wrap">
			<ul class="paging inline-list">
			<li class="prev {{#if paging.page_start == 1}}off{{/if}}" on-click="pageMove:{{paging.page_start - 1}}">PREV</li>
			{{#pages}}
			<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="pageMove:{{this}}">{{this}}</li>
			{{/pages}}
			<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" on-click="pageMove:{{paging.page_end + 1}}">NEXT</li>
			</ul>
		</div>
		{{/paging}}
	</div>
	</script>
</div>
<script src="/js/app/account/AADMW004.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
