<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="news-mng" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-news-mng" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">스타필드 스토리 관리 - NEWS 관리 - NEWS 목록</h1>
		<div class="search-box">
			<div class="select-wrap">
				<select id="lang-mng-selector" value="{{hashObject.searchType}}">
					<option value="1">제목+내용</option>
					<option value="2">제목</option>
					<option value="3">내용</option>
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
		<table id="news-mng-list" class="basic-list">
			<colgroup>
				<col width="60">
				<col width="120">
				<col width="*">
				<col width="120">
				<col width="60">
			</colgroup>
			<thead>
				<tr>
					<th {{#if(hashObject.sort_name == 'rnum')}}class="selected"{{/if}} on-click="sort:rnum">NO</th>
					<th {{#if(hashObject.sort_name == 'cate_nm_ko')}}class="selected"{{/if}} on-click="sort:cate_nm_ko">구분</th>
					<th {{#if(hashObject.sort_name == 'news_titl')}}class="selected"{{/if}} on-click="sort:news_titl">제목</th>
					<th {{#if(hashObject.sort_name == 'reg_dttm')}}class="selected"{{/if}} on-click="sort:reg_dttm">등록일</th>
					<th class="no-order">선택</th>
				</tr>
			</thead>
			<tbody>
				{{#list}}
				<tr>
					<td class="cursor-p" on-click="detail:{{news_seq}}">{{rnum}}</td>
					<td class="cursor-p" on-click="detail:{{news_seq}}">{{cate_nm_ko}}</td>
					<td class="ellipsis ta-l cursor-p" on-click="detail:{{news_seq}}">{{news_titl}}</td>
					<td class="cursor-p" on-click="detail:{{news_seq}}">{{reg_dttm}}</td>
					<td><input name="groupCheckBox" type="checkbox" value="{{news_seq}}"></td>
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
<script src="/js/app/starfieldStory/ACMSW004.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>