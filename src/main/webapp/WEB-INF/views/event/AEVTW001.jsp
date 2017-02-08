<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="event-mng" class="contents-area">
		<script id="tmpl-event-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">이벤트 관리 - 이벤트 목록</h1>
			<div class="search-box">
				<div class="select-wrap">
					<select id="search-option-selector" class="with-checkbox" value="{{filter.search_option_selector}}" on-change="changeSearchOption">
					<c:forEach var="option" items="${eventSearchOption}">
						<option value="${option.codeCd}">${option.codeNm}</option>
					</c:forEach>
					</select>
					<input class="" type="checkbox" id="search-option-yn" checked="{{filter.search_option_yn}}">
				</div>
				<div class="date-picker" id ="evt-strt-dt">
					<input type="text" name="datepicker" placeholder="시작일자" value="{{filter.evt_strt_dt}}">
				</div>
				<span class="dash">~</span>
				<div class="date-picker" id ="evt-end-dt">
					<input type="text" name="datepicker" placeholder="종료일자" value="{{filter.evt_end_dt}}">
				</div>
				<div class="search-input-wrap">
					<input class="search-input" type="text" placeholder="이벤트명" id="evt-titl" value="{{filter.evt_titl}}">
					<div class="basic-btn search gold" on-click="search" id="search-btn">검색</div>
				</div>
			</div>
			<div class="list-top">
				<span class="select-title">이벤트 유형</span>
				<div class="select-wrap">
					<select id="evt-type" on-change="search" value="{{filter.evt_type}}">
						<option value="" selected="selected">전체</option>
						<c:forEach var="option" items="${eventType}">
							<option value="${option.codeCd}">${option.codeNm}</option>
						</c:forEach>
					</select>
				</div>
				<span class="select-title">이벤트 주체</span>
				<div class="select-wrap">
					<select id="evt-dvi" on-change="search" value="{{filter.evt_dvi}}">
						<option value="" selected="selected">전체</option>
						<c:forEach var="option" items="${eventDvi}">				
						<option value="${option.codeCd}">${option.codeNm}</option>
						</c:forEach>
					</select>
				</div>
				<ul class="inline-list">
					<li on-click="excelDownload">엑셀다운</li><li on-click="close">조기종료</li><li on-click="add">추가</li><li on-click="delete">선택 삭제</li>
				</ul>
			</div>
			<table id="event-mng-list" class="basic-list">
				<colgroup>
					<col width="40">
					<col width="80">
					<col width="*">
					<col width="110">
					<col width="90">
					<col width="90">
					<col width="80">
					<col width="80">
					<col width="70">
					<col width="80">
					<col width="80">
					<col width="70">
					<col width="70">
					<col width="40">
				</colgroup>
				<thead>
					<tr>
						<th order-type="desc" class="no-order">NO</th>
						<th on-click="search" data-order-key="evt_dvi">이벤트 주체</th>
						<th on-click="search" data-order-key="evt_titl">타이틀</th>
						<th class="no-order">대표이미지</th>
						<th on-click="search" data-order-key="evt_post_strt_dt">게시기간</th>
						<th on-click="search" data-order-key="evt_strt_dt">이벤트기간</th>
						<th on-click="search" data-order-key="evt_type">이벤트유형</th>
						<th on-click="search" data-order-key="evt_open_yn">노출상태</th>
						<th on-click="search" data-order-key="evt_stat">진행상태</th>
						<th on-click="search" data-order-key="evt_pick_plan_dt">당첨자발표</th>
						<th on-click="search" data-order-key="pick_cnt">당첨자추첨</th>
						<th on-click="search" data-order-key="reg_dttm" class="selected" order-type="desc">등록일</th>
						<th on-click="search" data-order-key="evt_app_dt">승인일</th>
						<th class="no-order">선택</th>
					</tr>
				</thead>
				<tbody>
					{{#eventList}}
					<tr>
						<td>{{no}}</td>
						<td class="ellipsis">{{evt_dvi.codeNm}}</td>
						<td class="ellipsis ta-l cursor-p" on-click="view:{{evt_seq}}">{{evt_titl}}</td>
						<td><span class="img-wrap"><img src="${pageContext.request.contextPath}{{evt_img_uri}}"></span></td>
						<td><span class="start-date">{{evt_post_strt_dt}}</span><br>~<span class="end-date">{{evt_post_end_dt}}</span></td>
						<td><span class="start-date">{{evt_strt_dt}}</span><br>~<span class="end-date">{{evt_end_dt}}</span></td>
						<td>{{evt_type.codeNm}}</td>
						{{#if evt_open_yn == 'Y'}}
						<td class="red"><div class="basic-btn free-width" on-click="open:'N',{{evt_seq}}">노출</div></td><!-- .red : 활성 / .gray: 비활성 -->
						{{else}}
						<td class="gray"><div class="basic-btn free-width" on-click="open:'Y',{{evt_seq}}">미노출</div></td><!-- .red : 활성 / .gray: 비활성 -->
						{{/if}}
						<td class="{{evt_stat.cssClass}}">{{evt_stat.codeNm}}</td><!-- .red : 진행중 / .green : 종료 / .gray: 진행전 -->
						<td>{{evt_pick_plan_dt}}</td>						
						<td>{{#if evt_pick_dt }} Y {{else}} N {{/if}}</td>
						<td>{{reg_dttm}}</td>
						<td>{{evt_app_dt}}</td>
						<td><input type="checkbox" name="checked" value="{{.evt_seq}}" data-stat="{{evt_stat.codeNm}}"></td>
					</tr>
					{{/eventList}}
					
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
	<script src="/js/app/event/AEVTW001.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
