<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
<style type="text/css">
	.date-picker2 {
	display: inline-block;
	width: 80px;
	height: 30px;
	border: 1px solid #7b6d65;
	background-color: #ffffff;
	position: relative;
	vertical-align: middle; 
}
.date-picker2 input {
	position: absolute;
	top: 0;
	left: 0;
	border: none;
	outline: none;
	height: 100%;
	width: 100%;
	padding-left: 5px;
	z-index: 4;
}
</style>
		<div id="stats-list" class="contents">
		<script id="tmpl-stats-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">앱사용 통계 > Heat Map > 이동 경로 분석</h1>
			<input type="hidden" id="c_zone_id">
			<div class="search-box">
				기준일자 
				<div class="date-picker2">
					<input type="text" name="datepicker2" id="sh_dt" value="{{filter.sh_dt}}">
				</div>
				<span on-click="search" style="cursor: pointer">검색</span>
				
			</div>   
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=downExcel>엑셀다운</li>
				</ul>
			</div>
			<table id="" class="basic-list">
				<colgroup>
					<col width="50">
					<col width="200">
					<col width="50">
					<col width="100">
					<col width="100">
					<col width="100">
					<col width="180">
					<col width="180">
					<col width="70">
				</colgroup>   
				<thead>         
					<tr>
						<th class="no-order">순위</th>
						<th class="no-order">테넌트명</th>
						<th class="no-order">층정보</th> 
						<th class="no-order">총방문자수</th>
						<th class="no-order">UV<br>(Unique Visitor)</th> 
						<th class="no-order">전체방문자 중 <br>매장 방문자비율</th>
						<th class="no-order">이전방문<br>테넌트</th>
						<th class="no-order">이후방문<br>테넌트</th>
						<th class="no-order">이동경로<br>상세보기</th>
					</tr>   
				</thead>
				<tbody>  
					{{#statsList}}
					<tr>
						<td>{{rnk}}</td>
						<td class="ellipsis ta-l">{{tnt_nm}}</td>              
						<td>{{fl_nm}}</td>              
						<td>{{all_visit_cnt}}</td>
						<td>{{all_unk_visit_cnt}}</td>
						<td>{{all_tnt_rt}}</td>
						<td class="ellipsis ta-l">{{pre_top_tnt_nm}}</td>	              
						<td class="ellipsis ta-l">{{post_top_tnt_nm}}</td>
						<td on-click=openPop:{{tnt_nm}},{{pre_visit_tot_cnt}},{{post_visit_tot_cnt}} style="cursor: pointer">상세보기</td>      
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
		<!--신규 쿠폰등록2-->
	<div id="stats-dtl-list" class="contents">
		<script id="tmpl-stats-dtl-list" type="text/ractive">
	<div id="coupon-reg-popup" name="tnt-reg-popup2" class="popup-wrap"><!--place-on 추가시 테넌트 불러오기 팝업 열림-->
		<div class="popup-container">
			<!--테넌트 선택 팝업-->
			<div id="coupon-place-setting" class="popup"> 
				<div class="popup-header"><span class="btn-close" on-click="closePop" style="cursor: pointer">&#215;</span></div>
				<h4 class="popup-title red">이동경로 분석 요약</h4>
				<table>
					<tr>
						<td style="background-color:#edebea">테넌트명</td>
						<td colspan=3 id="tnt_nm">테넌트명</td>
					</tr>
					<tr>
						<td style="background-color:#edebea" >이전방문테넌트총방문</td><td id="pre_visit_tot_cnt"></td>
						<td style="background-color:#edebea" >이후방문테넌트총방문</td><td id="post_visit_tot_cnt"></td>
					</tr>
				</table>
				<h4 class="popup-title red">이동경로 분석 상세</h4>
				<div class="popup-contents" id="sc2">
						 
					<table class="basic-list">
						<colgroup>
							<col width="50">
							<col width="120">
							<col width="50">
							<col width="40">
							<col width="120">
							<col width="50">
							<col width="40"> 
						</colgroup>
						<thead>
							<tr>
								<tr>   
									<th rowspan="2" on-click=listSort data-order-key="reg_dttm">순위</th>
									<th class="no-order" colspan="3">이전 방문 테넌트</th>
									<th class="no-order" colspan="3">이후 방문 테넌트</th>
								</tr>     
							<tr> 
							<th class="no-order">테넌트명</th>
							<th class="no-order">유입횟수</th> 
							<th class="no-order">비율(%)</th>
							<th class="no-order">테넌트명</th>
							<th class="no-order">이탈 횟수</th> 
							<th class="no-order">비율</th> 
							</tr>  
							 
						</thead>

						<tbody>
							{{#selStatsDtlList:num}} 
								<tr>
								<td class="ellipsis">{{rnk}}</td>
								<td  class="ellipsis ta-l">{{pre_tnt_nm}}</td>
								<td class="ellipsis">{{pre_visit_cnt}}</td>
								<td class="ellipsis">{{pre_visit_rt}}</td>
								<td  class="ellipsis ta-l">{{post_tnt_nm}}</td>
								<td class="ellipsis">{{post_visit_cnt}}</td>
								<td class="ellipsis">{{post_visit_rt}}</td>
							</tr>
							{{/selStatsDtlList}}

						</tbody>
					</table>
					{{#paging2}} 
					<div class="paging-wrap"> 
						<ul class="paging inline-list">
						<li class="prev {{#if paging.page_start == 1}}off{{/if}}" on-click="movePage2:{{paging.page_start - 1}}">PREV</li>
						{{#pages}}
						<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="movePage2:{{this}}">{{this}}</li>
						{{/pages}}
						<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" on-click="movePage2:{{paging.page_end + 1}}">NEXT</li>
						</ul>
            		</div>
					{{/paging2}} 	
				</div>
				<br/>
				<div class="btn-wrap">
					<div class="basic-btn" on-click="closePop">닫기</div> 
				</div> 
			</div>
			<!--//테넌트 선택 팝업--> 
		</div>
	</div>
		</script>
	</div>
	<script src="/js/app/stats/AAMLW008.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
