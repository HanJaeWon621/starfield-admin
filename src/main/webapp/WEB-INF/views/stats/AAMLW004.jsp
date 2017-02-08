<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="stats-list" class="contents">
		<script id="tmpl-stats-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">앱사용 통계 > 앱캠페인 효과 분석 통계 > 전체 데이터</h1>
			<div class="search-box">
				{{#filter}}
				조회기간 
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_strt_dt" value="{{filter.sh_strt_dt}}" >
				</div>
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_end_dt" value="{{filter.sh_end_dt}}">
				</div> 
				<br/>
				검색어 검색
				<div class="date-picker"> 
					<input type="text" name="" id="" value="{{filter.sh_titl}}" placeholder="타이틀 명 검색">
					<div class="basic-btn search gold" on-click="search" id="search-btn">검색</div>
				</div>
				{{/filter}} 
			</div>   
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=downExcel>리스트다운</li>
				</ul>
			</div>
			<table id="event-mng-list" class="basic-list">
				<colgroup>
					<col width="50">
					<col width="80">
					<col width="60">
					<col width="60">  
					<col width="120">  
					<col width="*">  
					<col width="*">  
					<col width="*">  
					<col width="*">  
				</colgroup>   
				<thead>   
					<tr> 
						<th class="no-order" rowspan="2">번호</th>
						<th rowspan="2">캠페인<br/>종류구분</th>
						<th rowspan="2">노출물<br/>유형</th>
						<th rowspan="2">노출<br/>형태</th> 
						<th rowspan="2">쿠폰/이벤트 타이틀</th>
						<th colspan="3">노출(푸시)<br/>카운팅</th> 
						<th colspan="3">클릭<br/>카운팅</th>
						<th colspan="3">도달률<br/>(다운로드)</th>
						<th colspan="3">회수율</th>
					</tr>   
					<tr> 
						<th class="no-order">전체<br/>누적</th>
						<th class="no-order">금일</th>
						<th class="no-order">전일</th>
						<th class="no-order">전체<br/>누적</th>
						<th class="no-order">금일</th>
						<th class="no-order">전일</th>
						<th class="no-order">전체<br/>누적</th>
						<th class="no-order">금일</th>
						<th class="no-order">전일</th>
						<th class="no-order">전체<br/>누적</th>
						<th class="no-order">금일</th>
						<th class="no-order">전일</th>
					</tr>           
				</thead>
				<tbody>   
					{{#statsList}}
					<tr> 
						<td>{{tot_cnt-no+1}}</td>              
						<td>{{cmp_type_nm}}</td>              
						<td>{{exp_type}}</td>              
						<td>{{exp_div}}</td>              
						<td>{{cp_titl}}</td>                      
						<td>{{push_cnt}}</td>              
						<td>{{push_today_cnt}}</td>              
						<td>{{push_pre_cnt}}</td>  
						<td>{{cl_cnt}}</td>              
						<td>{{cl_today_cnt}}</td>              
						<td>{{cl_pre_cnt}}</td>              
						<td>{{dn_cnt}}</td>              
						<td>{{dn_today_cnt}}</td>              
						<td>{{dn_pre_cnt}}</td>              
						<td>{{us_cnt}}</td>
						<td>{{us_today_cnt}}</td>              
						<td>{{us_pre_cnt}}</td>              
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
	<script src="/js/app/stats/AAMLW004.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
