<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="lang-mng" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-lang-mng" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">다국어 PAGE 관리 - PAGE 목록</h1>
		<div class="search-box">
			<div class="select-wrap">
				<select id="lang-mng-selector" value="{{hashObject.searchType}}">
					<option value="1">Page ID</option>
					<option value="2">항목명</option>
				</select>
			</div>
			<div class="search-input-wrap">
				<input class="search-input" type="text" placeholder="검색할 내용을 입력하세요" value="{{hashObject.searchKeyword}}">
				<div class="basic-btn search gold" on-click="search">검색</div>
			</div>
		</div>
		<div class="list-top">
			<ul class="inline-list">
				<li on-click="download">엑셀다운</li><li on-click="create">추가</li><li on-click="delete">삭제</li>
			</ul>
		</div>
		<table id="lang-mng-list" class="basic-list">
			<colgroup>
				<col width="50">
				<col width="200">
				<col width="80">
				<col width="*">
				<col width="150">
				<col width="50">
			</colgroup>
			<thead>
				<tr>
					<th {{#if(hashObject.sort_name == 'rnum')}}class="selected"{{/if}} on-click="sort:rnum">No.</th>
					<th {{#if(hashObject.sort_name == 'pg_id')}}class="selected"{{/if}} on-click="sort:pg_id">Page ID</th>
					<th {{#if(hashObject.sort_name == 'count')}}class="selected"{{/if}} on-click="sort:count">항목수</th>
					<th {{#if(hashObject.sort_name == 'txt_symb')}}class="selected"{{/if}} on-click="sort:txt_symb">항목명</th>
					<th {{#if(hashObject.sort_name == 'reg_dttm')}}class="selected"{{/if}} on-click="sort:reg_dttm">등록일</th>
					<th class="no-order">선택</th>
				</tr>
			</thead>
			<tbody>
				{{#list}}
				<tr class="cursor-p">
					<td on-click="detail:{{pg_id}}">{{rnum}}</td>
					<td on-click="detail:{{pg_id}}">{{pg_id}}</td>
					<td on-click="detail:{{pg_id}}">{{count}}</td>
					<td class="ellipsis ta-l" on-click="detail:{{pg_id}}">{{txt_symb}}</td>
					<td on-click="detail:{{pg_id}}">{{reg_dttm}}</td>
					<td class="cursor-d"><input name="groupCheckBox" type="checkbox" value="{{pg_id}}"></td>
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
<script src="/js/app/language/ASYSW002.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>