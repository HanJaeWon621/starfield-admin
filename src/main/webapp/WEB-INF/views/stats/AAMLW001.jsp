<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="all-stats-list" class="contents-area">
		<script id="tmpl-all-stats-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">앱사용 통계 > 방문자 통계 > 전체 방문자 통계</h1>
			{{#statsFilter}}	
			<div class="search-box" data="{{sortColumnAscDesc}}">
				<div class="select-wrap">
					<select id="sh_dt_type"  value="{{sh_dt_type}}" on-change="search">
						<option value="day">일단위</option>
						<option value="week">주단위</option>
						<option value="month">월단위</option>
						<option value="year">년단위</option>
					</select>
				</div>
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_strt_dt" value="{{sh_strt_dt}}" >
				</div>
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_end_dt" value="{{sh_end_dt}}">
					<div class="basic-btn search gold" on-click="search" id="search-btn">검색</div>
				</div> 
			</div>
			{{/statsFilter}}   
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=downExcel>엑셀다운</li>
				</ul>
			</div>
			<table  class="basic-list">
				<colgroup>
					<col width="200">
					<col width="*">
					<col width="*">
					<col width="*"> 
					<col width="*">  
				</colgroup> 
				<thead> 
					<tr> 
						<th rowspan="2" on-click=listSort data-order-key="visit_dt">기간</th>
						<th colspan="2" on-click=listSort data-order-key="all_visit_cnt">전체 방문자 수</th>
						<th colspan="2" on-click=listSort data-order-key="all_visit_mbr_cnt">전체 방문자 대비<br/>회원 방문자 수</th>
						<th colspan="2" on-click=listSort data-order-key="new_visit_cnt">신규 방문자 수</th> 
						<th colspan="2" on-click=listSort data-order-key="re_visit_cnt">재방문자 수</th>
					</tr>
					<tr id="trSpan"> 
						<th class="no-order">전년도</th>
						<th class="no-order">현재년도</th>
						<th class="no-order">전년도</th>
						<th class="no-order">현재년도</th>
						<th class="no-order">전년도</th>
						<th class="no-order">현재년도</th>
						<th class="no-order">전년도</th>
						<th class="no-order">현재년도</th>
					</tr>
				</thead>
				<tbody>
					{{#allStatsList}}
					<tr>
						<td>{{visit_dt}}</td>              
						<td>{{pre_all_visit_cnt}}</td>              
						<td>{{all_visit_cnt}}</td>              
						<td>{{pre_all_visit_mbr_cnt}}</td>              
						<td>{{all_visit_mbr_cnt}}</td>              
						<td>{{pre_new_visit_cnt}}</td>              
						<td>{{new_visit_cnt}}</td>              
						<td>{{pre_re_visit_cnt}}</td>              
						<td>{{re_visit_cnt}}</td>              
					</tr> 
					{{/allStatsList}}
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
	<script src="/js/app/stats/AAMLW001.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
