<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="stats-list" class="contents">
		<script id="tmpl-stats-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">앱사용 통계 > 앱캠페인 효과 분석 통계 > 회원기준 통계 데이터</h1>
			<div class="search-box">
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
					<input type="text" name="" id="" value="{{filter.sh_titl}}" placeholder="타이틀 검색">
					<div class="basic-btn search gold" on-click="search" id="search-btn">검색</div>
				</div> 
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
					<col width="50">
					<col width="50">  
					<col width="130">  
					<col width="50">  
					<col width="50">  
					<col width="50">  
					<col width="50">  
					<col width="50">  
					<col width="50">  
					<col width="*">   
					<col width="*">   
					<col width="*">   
					<col width="*">   
				</colgroup>   
				<thead>     
					<tr> 
						<th class="no-order" rowspan="3">번호</th>
						<th rowspan="3">캠페인<br/>종류구분</th>
						<th rowspan="3">노출물<br/>유형</th>
						<th rowspan="3">노출<br/>형태</th> 
						<th rowspan="3">쿠폰/이벤트 타이틀</th>
						<th colspan="3">전체 대비 회원<br/>노출(푸시)비율(%)</th> 
						<th colspan="3">전체 클릭 카운팅 대비<br/>회원 클릭 비율(%)</th>
						<th colspan="2" colspan="12">다운로드<br/>비율(%)</th>
						<th colspan="2" colspan="12">회수율(%)</th>
					</tr>   
					<tr> 
						<th class="no-order" rowspan="2">전체<br/>누적</th>
						<th class="no-order" rowspan="2">금일</th>
						<th class="no-order" rowspan="2">전일</th>
						<th class="no-order" rowspan="2">전체<br/>누적</th>
						<th class="no-order" rowspan="2">금일</th>
						<th class="no-order" rowspan="2">전일</th>
						<th class="no-order" colspan="6">여(%)</th>
						<th class="no-order" colspan="6">남(%)</th>
						<th class="no-order" colspan="6">여(%)</th>
						<th class="no-order" colspan="6">남(%)</th>
					</tr>  
					<tr>   
						<th class="no-order">10</th>
						<th class="no-order">20</th>
						<th class="no-order">30</th>
						<th class="no-order">40</th>
						<th class="no-order">50</th>
						<th class="no-order">60</th>
						<th class="no-order">10</th>
						<th class="no-order">20</th>
						<th class="no-order">30</th>
						<th class="no-order">40</th>
						<th class="no-order">50</th>
						<th class="no-order">60</th>
						<th class="no-order">10</th>
						<th class="no-order">20</th>
						<th class="no-order">30</th>
						<th class="no-order">40</th>
						<th class="no-order">50</th>
						<th class="no-order">60</th>
						<th class="no-order">10</th>
						<th class="no-order">20</th>
						<th class="no-order">30</th>
						<th class="no-order">40</th>
						<th class="no-order">50</th>
						<th class="no-order">60</th>
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
						<td>{{push_all_rt}}</td>               
						<td>{{push_cust_today_rt}}</td>              
						<td>{{push_all_pre_rt}}</td>              
						<td>{{cl_all_rt}}</td>              
						<td>{{cl_all_today_rt}}</td>              
						<td>{{cl_all_pre_rt}}</td>              
						<td>{{m_dn_10_cnt}}</td>              
						<td>{{m_dn_20_cnt}}</td>              
						<td>{{m_dn_30_cnt}}</td>              
						<td>{{m_dn_40_cnt}}</td>              
						<td>{{m_dn_50_cnt}}</td>              
						<td>{{m_dn_60_cnt}}</td>              
						<td>{{f_dn_10_cnt}}</td>              
						<td>{{f_dn_20_cnt}}</td>              
						<td>{{f_dn_30_cnt}}</td>              
						<td>{{f_dn_40_cnt}}</td>              
						<td>{{f_dn_50_cnt}}</td>              
						<td>{{f_dn_60_cnt}}</td>              
						<td>{{m_us_10_cnt}}</td>              
						<td>{{m_us_20_cnt}}</td>              
						<td>{{m_us_30_cnt}}</td>              
						<td>{{m_us_40_cnt}}</td>              
						<td>{{m_us_50_cnt}}</td>              
						<td>{{m_us_60_cnt}}</td>              
						<td>{{f_us_10_cnt}}</td>              
						<td>{{f_us_20_cnt}}</td>              
						<td>{{f_us_30_cnt}}</td>              
						<td>{{f_us_40_cnt}}</td>              
						<td>{{f_us_50_cnt}}</td>              
						<td>{{f_us_60_cnt}}</td>              
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
	<script src="/js/app/stats/AAMLW005.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
