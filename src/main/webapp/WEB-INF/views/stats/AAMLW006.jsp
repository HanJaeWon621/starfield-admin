<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="stats-list" class="contents">
		<script id="tmpl-stats-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">앱사용 통계 > APP 설치 통계 > 전체 누적 설치 통계</h1>
			<div class="search-box">
				조회기간 
				<div class="select-wrap">
					<select id="sh_dt_type"  value="{{filter.sh_dt_type}}" >
						<option value="day">일단위</option>
						<option value="week">주단위</option>
						<option value="month">월단위</option>
						<option value="year">연단위</option>
					</select>
				</div>
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_strt_dt" value="{{filter.sh_strt_dt}}" >
				</div>
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_end_dt" value="{{filter.sh_end_dt}}">
					<div class="basic-btn search gold" on-click="search" id="search-btn">검색</div>
				</div> 
			</div>   
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=downExcel>엑셀다운</li>
				</ul>
			</div>
			<table id="" class="basic-list">
				<colgroup>
					<col width="300">
					<col width="*">
					<col width="*">
					<col width="*"> 
				</colgroup>   
				<thead>     
					<tr>  
						<th rowspan="2" on-click=listSort data-order-key="reg_dttm">기간</th>
						<th class="no-order" colspan="3">기간별 설치 수</th>
					</tr>    
					<tr> 
						<th class="no-order">IOS</th>
						<th class="no-order">Android</th> 
						<th class="no-order">전체</th> 
					</tr>   
				</thead>
				<tbody>  
					{{#statsList}}
					<tr>
						<td>{{reg_dttm}}</td>              
						<td>{{i_cnt}}</td>              
						<td>{{a_cnt}}</td>              
						<td>{{sum_cnt}}</td>              
					</tr> 
					{{/statsList}}
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
	<script src="/js/app/stats/AAMLW006.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
