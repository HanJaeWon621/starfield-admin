<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="notice-mng" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-notice-mng" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">고객센터 - 공지사항 관리 - 리스트</h1>
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
				  <li on-click="download">엑셀 다운</li><li on-click="create">추가</li><li on-click="delete">삭제</li>
			</ul>
		</div>
		<table id="notice-mng-list" class="basic-list">
			<colgroup>
				<col width="100">
				<col width="*">
				<col width="150">
				<col width="80">
			</colgroup>
			<thead>
				<tr>
					<th {{#if(hashObject.sort_name == 'rnum')}}class="selected"{{/if}} on-click="sort:rnum">NO</th>
					<th {{#if(hashObject.sort_name == 'noti_titl')}}class="selected"{{/if}} on-click="sort:noti_titl">제목</th>
					<th {{#if(hashObject.sort_name == 'reg_dttm')}}class="selected"{{/if}} on-click="sort:reg_dttm">등록일</th>
					<th class="no-order">선택</th>
				</tr>
			</thead>
			<tbody>
				{{#list}}
				<tr class="cursor-p">
					<td on-click="detail:{{noti_seq}}">{{rnum}}</td>
					<td class="ellipsis ta-l" on-click="detail:{{noti_seq}}">{{noti_titl}}</td>
					<td on-click="detail:{{noti_seq}}">{{reg_dttm}}</td>
					<td class="cursor-d"><input name="groupCheckBox" type="checkbox" value="{{noti_seq}}"></td>
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
<script src="/js/app/service/ACMSW005.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>