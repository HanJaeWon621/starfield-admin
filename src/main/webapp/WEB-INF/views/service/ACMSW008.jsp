<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="inquiry-mng" class="contents-area" data-bcn_cd="${bcn_cd}">
		<script id="tmpl-inquiry-mng" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">고객센터 - 1:1 이메일 문의 관리 - 리스트</h1>
			<div class="search-box">
				<div class="select-wrap">
					<select id="inquiry-search-selector" value="{{hashObject.searchType}}">
						<option value="1">제목+내용</option>
						<option value="2">제목</option>
						<option value="3">내용</option>
					</select>
				</div>
				<div class="search-input-wrap">
					<input class="search-input" type="text" placeholder="검색어를 입력하세요" value="{{hashObject.searchKeyword}}">
					<div class="basic-btn search gold" on-click="search:{{hashObject.searchType}},{{hashObject.searchKeyword}}">검색</div>
				</div>
			</div>
			<div class="list-top">
				<ul class="inline-list">
					<li on-click="excelDownload">엑셀 다운</li><li on-click="delete">삭제</li>
				</ul>
			</div>
			<table id="inquiry-mng-list" class="basic-list">
				<colgroup>
					<col width="50">
					<col width="100">
					<col width="180">
					<col width="*">
					<col width="120">
					<col width="80">
					<col width="120">
					<col width="50">
				</colgroup>
				<thead>
					<tr>
						<th {{#if(hashObject.sort_name == 'rnum')}}class="selected"{{/if}} on-click="sort:rnum">NO</th>
						<th {{#if(hashObject.sort_name == 'cust_nm')}}class="selected"{{/if}} on-click="sort:cust_nm">고객 성명</th>
						<th {{#if(hashObject.sort_name == 'cust_email')}}class="selected"{{/if}} on-click="sort:cust_email">고객 이메일</th>
						<th {{#if(hashObject.sort_name == 'qutn_titl')}}class="selected"{{/if}} on-click="sort:qutn_titl">제목</th>
						<th {{#if(hashObject.sort_name == 'qutn_dttm')}}class="selected"{{/if}} on-click="sort:qutn_dttm">등록일</th>
						<th {{#if(hashObject.sort_name == 'reply_yn')}}class="selected"{{/if}} on-click="sort:reply_yn">답변</th>
						<th {{#if(hashObject.sort_name == 'reply_dttm')}}class="selected"{{/if}} on-click="sort:reply_dttm">답변 등록일</th>
						<th class="no-order">선택</th>
					</tr>
				</thead>
				<tbody>
					{{#list}}
					<tr class="cursor-p">
						<td on-click="detail:{{qna_seq}}">{{rnum}}</td>
						<td class="ellipsis" on-click="detail:{{qna_seq}}">{{cust_nm}}</td>
						<td class="ellipsis" on-click="detail:{{qna_seq}}">{{cust_email}}</td>
						<td class="ellipsis ta-l" on-click="detail:{{qna_seq}}">{{qutn_titl}}</td>
						<td on-click="detail:{{qna_seq}}">{{qutn_dttm}}</td>
						<td on-click="detail:{{qna_seq}}">{{reply_yn}}</td>
						<td on-click="detail:{{qna_seq}}">{{reply_dttm}}</td>
						<td class="cursor-d"><input name="deleteCheckBox" type="checkbox" value="{{qna_seq}}"></td>
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
<script src="/js/app/service/ACMSW008.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>