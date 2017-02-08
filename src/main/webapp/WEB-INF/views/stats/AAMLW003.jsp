<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="stats-list" class="contents-area">
		<script id="tmpl-stats-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">앱사용 통계 > 방문자 통계 > 테넌트별 방문자 통계 > 회원기준 기준통계</h1>
			{{#statsFilter}}
			<div class="search-box">
				조회기간 
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_strt_dt" value="{{sh_strt_dt}}">
				</div>                                       
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_end_dt" value="{{sh_end_dt}}">
				</div> 
				<br/>
				검색어 검색
				<div class="date-picker"> 
					<input type="text" name="" id="sh_tnt_nm" value="{{sh_tnt_nm}}" placeholder="테넌트 명 검색">
					<div class="basic-btn search gold" on-click="search" id="search-btn">검색</div>
				</div> 
			</div>
			{{/statsFilter}}   
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=downExcel>엑셀다운</li>
				</ul>
			</div>
			<table class="basic-list">
				<colgroup>
					<col width="50">
					<col width="110">
					<col width="110">
					<col width="110">  
					<col width="110">  
					<col width="*">   
				</colgroup>   
				<thead>   
					<tr> 
						<th class="no-order" rowspan="3">순위</th>
						<th rowspan="3" on-click=listSort data-order-key="tnt_nm">테넌트 명</th>
						<th rowspan="3" on-click=listSort data-order-key="all_visit_mbr_cnt">회원 방문자 수</th>
						<th rowspan="3" on-click=listSort data-order-key="today_visit_mbr_cnt">금일 반문회원 수</th> 
						<th rowspan="3" on-click=listSort data-order-key="re_visit_mbr_cnt">재방문 회원 수</th>
						<th colspan="2" >방문회원 성비율(%)</th> 
						<th colspan="2" colspan="12">방문회원 연령대별 방문 비율(%)</th>
					</tr>              
					<tr>               
						<th class="no-order" rowspan="2">여</th>
						<th class="no-order" rowspan="2">남</th>
						<th class="no-order" colspan="6">여(%)</th>
						<th class="no-order" colspan="6">남(%)</th>
					</tr>   
					<tr>
						<th class="no-order">10</th>     
						<th class="no-order">20</th>
						<th class="no-order">30</th>
						<th class="no-order">40</th>
						<th class="no-order">50</th>
						<th class="no-order">60~</th>
						<th class="no-order">10</th>
						<th class="no-order">20</th>
						<th class="no-order">30</th>
						<th class="no-order">40</th>
						<th class="no-order">50</th>
						<th class="no-order">60~</th>
					</tr> 
				</thead>
				<tbody> 
					{{#statsList}}
					<tr>
						<td>{{no}}</td>              
						<td>{{tnt_nm}}</td>              
						<td>{{all_visit_mbr_cnt}}</td>              
						<td>{{today_visit_mbr_cnt}}</td>              
						<td>{{re_visit_mbr_cnt}}</td>              
						<td>{{sex_rt_f}}</td>              
						<td>{{sex_rt_m}}</td>
						<td>{{sex_rt_10_f}}</td>
						<td>{{sex_rt_20_f}}</td>              
						<td>{{sex_rt_30_f}}</td>
						<td>{{sex_rt_40_f}}</td>
						<td>{{sex_rt_50_f}}</td>
						<td>{{sex_rt_60_f}}</td>
						<td>{{sex_rt_10_m}}</td>	              
						<td>{{sex_rt_20_m}}</td>              
						<td>{{sex_rt_30_m}}</td>
						<td>{{sex_rt_40_m}}</td>
						<td>{{sex_rt_50_m}}</td>
						<td>{{sex_rt_60_m}}</td>
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
	<script src="/js/app/stats/AAMLW003.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
