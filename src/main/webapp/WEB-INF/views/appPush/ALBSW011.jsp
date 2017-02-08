<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="wcPushMsg-list" class="contents">
		<script id="tmpl-wcPushMsg-list" type="text/ractive"> 
		<div class="contents-wrap">
			<h1 class="menu-title">APP Push 시스템> 푸시메시지관리> 웰컴메시지목록</h1>
			<div class="search-box">
				<div class="select-wrap">
					<select id="search_dt_type"  value="{{search_dt_type}}">
						<option value="1">등록일시</option>
						<option value="2">노출기간</option>
					</select>
				</div>
				<div class="date-picker">
					<input type="text" name="datepicker" id="search_strt_dt" values="{{search_strt_dt}}" >
				</div>
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" id="search_end_dt" values="{{search_end_dt}}">
				</div>
				푸시일자
				<br/>
				<div class="date-picker">
					<input type="text" name="datepicker" id="push_search_strt_dt" values="{{push_search_strt_dt}}" >
				</div>
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" id="push_search_end_dt" values="{{push_search_end_dt}}">
				</div>  
				<span on-click="getWcPushMsgList" style="cursor: pointer">검색</span>
				<span on-click="getWcPushMsgListInit" style="cursor: pointer">초기화</span>
			</div>  
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=downExcel style="cursor: pointer">엑셀다운</li>
				</ul>
			</div>
			<table class="basic-list">
				<colgroup>
					<col width="50">
					<col width="50">
					<col width="200">
					<col width="60">
					<col width="50">
					<col width="50">
					<col width="60">
					<col width="60">
					<col width="60">
				</colgroup>
				<thead>
					<tr>
						<th on-click=listSort data-order-key="no">NO</th>
						<th on-click=listSort data-order-key="reg_dttm">등록일시</th>
						<th on-click=listSort data-order-key="push_titl">메시지 타이틀</th>
						<th on-click=listSort data-order-key="push_cnt">푸시 카운팅</th>
						<th on-click=listSort data-order-key="tot_push_cnt">푸시 카운팅(누적)</th>
						<th on-click=listSort data-order-key="exp_end_dt"colspan="2">메시지 노출 기간</th>
						<th on-click=listSort data-order-key="apply_yn">쿠폰 적용 여부</th>
						<th on-click=listSort data-order-key="exp_yn">노출 현황</th>
					</tr>
				</thead>
				<tbody>
					{{#wcPushMsgList}}
					<tr on-click=modifyWcPushMsg:{{wel_msg_push_seq}} style="cursor: pointer">
						<td>{{no}}</td> 
						<td class="ellipsis">{{reg_dttm}}</td>
						<td class="ellipsis ta-l">{{push_titl}}</td>
						<td>{{push_cnt}}</td>
						<td>{{tot_push_cnt}}</td>
						<td><span class="start-date">{{exp_strt_dt}}</td>
						<td><span class="start-date">{{exp_end_dt}}</span></td>
						<td>{{apply_yn}}</td>
						<td>{{exp_yn}}</td>
					</tr>
					{{/wcPushMsgList}}
					
				</tbody>
			</table>
			{{#paging}}
			<div class="paging-wrap">
				<ul class="paging inline-list">
				<li class="prev {{#if paging.page_start == 1}}off{{/if}}" on-click="movePage:{{paging.page_start - 1}}">PREV</li>
				{{#pages}}
				<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="movePage:{{this}}">{{this}}</li>
				{{/pages}}
				<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" on-click="movePage:{{paging.page_end + 1}}">NEXT</li>
				</ul>
            </div>
			{{/paging}} 
		</div>
		</script>
	</div>
	<script src="/js/app/appPush/ALBSW011.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
