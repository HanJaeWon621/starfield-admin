<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="push-mng" class="contents-area">
		<script id="tmpl-push-mng" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">APP Push - APP Push 관리 - 리스트</h1>
			<div class="search-box">
				<div class="select-wrap">
					<select id="search-option-selector" class="with-checkbox" value="{{filter.search_option_selector}}" on-change="changeSearchOption">
					<c:forEach var="option" items="${searchOption}">
						<option value="${option.codeCd}">${option.codeNm}</option>
					</c:forEach>
					</select>
					<input class="" type="checkbox" id="search-option-yn" checked="{{filter.search_option_yn}}">
				</div>
				<div class="date-picker" id ="push-strt-dt">
					<input type="text" name="datepicker" placeholder="시작일자" value="{{filter.push_strt_dt}}">
				</div>
				<span class="dash">~</span>
				<div class="date-picker" id ="push-end-dt">
					<input type="text" name="datepicker" placeholder="종료일자" value="{{filter.push_end_dt}}">
				</div>
				<span class="select-title">발송구분</span>
				<div class="select-wrap w80">
					<select id="push-type-selector" value="{{filter.send_type}}">
						<option value="" selected="selected">전체</option>
						<c:forEach var="option" items="${sendType}">				
							<option value="${option.codeCd}">${option.codeNm}</option>
						</c:forEach>
					</select>
				</div>
				<span class="select-title">발송상태</span>
				<div class="select-wrap w80">
					<select id="push-status-selector" value="{{filter.send_yn}}">
						<option value="">전체</option>
						<c:forEach var="option" items="${sendYn}">				
							<option value="${option.codeCd}">${option.codeNm}</option>
						</c:forEach>
					</select>
				</div>
				<div class="search-input-wrap">
					<input class="search-input" type="text" placeholder="Push 메시지를 입력하세요." value="{{filter.push_msg}}">
					<div class="basic-btn search gold" on-click="search" id="search-btn">검색</div>
				</div>
			</div>
			<div class="list-top">
				<ul class="inline-list">
					<li on-click="excelDownload">엑셀다운</li><li on-click="add">추가</li><li on-click="delete">선택 삭제</li>
				</ul>
			</div>
			<table id="push-mng-list" class="basic-list">
				<colgroup>
					<col width="70">
					<col width="120">
					<col width="160">
					<col width="120">
					<col width="*">
					<col width="120">
					<col width="70">
				</colgroup>
				<thead>
					<tr>
						<th class="no-order">NO</th>
						<th on-click="search" data-order-key="send_type">발송구분</th>
						<th on-click="search" data-order-key="send_dttm">발송일시</th>
						<th on-click="search" data-order-key="send_yn">발송상태</th>
						<th on-click="search" data-order-key="push_msg">Push 메시지</th>
						<th on-click="search" data-order-key="a.reg_dttm" order-type="desc" class="selected">등록일</th>
						<th class="no-order">선택</th>
					</tr>
				</thead>
				<tbody>
					{{#pushList}}
					<tr>
						<td>{{no}}</td>
						<td>{{send_type.codeNm}}</td>
						<td>{{#if send_dttm}} {{send_dttm}} {{else}}-{{/if}}</td>
						<td>{{send_yn.codeNm}}</td>
						<td class="cursor-p ta-l ellipsis" on-click="modify:{{push_mng_seq}}">{{push_msg}}</td>
						<td>{{reg_dttm}}</td>
						<td><input type="checkbox" name="checked" value="{{.push_mng_seq}}" data-send_dttm="{{send_dttm}}"></td>
					</tr>
					{{/pushList}}
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
	<script src="/js/app/appPush/APSHW001.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>