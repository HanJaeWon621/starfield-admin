<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="pushMsg-list" class="contents">
		<script id="tmpl-pushMsg-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title" id="pg_title">Push 관리시스템_아웃바운드푸시</h1>
			<div class="search-box">
			{{#pushFilter}}
				조회 기간 
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_strt_dt" value="{{sh_strt_dt}}" >
				</div>
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_end_dt" value="{{sh_end_dt}}">
				</div> 
				<br/> 
				조회 기준 
				<div class="select-wrap">
					<select id="sh_memb_type"  value="{{sh_memb_type}}">
						<option value="0">전체</option>
						<option value="1">회원</option>
						<option value="2">비회원</option>
						<option value="3">관심고객회원</option>
						<option value="4">vip회원</option>
					</select>
				</div>
				<div class="select-wrap">
					<select id="sh_text_type"  value="{{sh_text_type}}">
						<option value="1">타이틀</option>
						<option value="2">쿠폰명</option>
					</select>
				</div>
				<div class="date-picker">
					<input type="text" name="sh_text" id="sh_text" value="{{sh_text}}" placeholder="타이틀/쿠폰명 입력">
				</div>
				<span on-click="shPush" style="cursor: pointer">검색</span>
				<span on-click="shPushInit" style="cursor: pointer">초기화</span>
			{{/couponFilter}} 
			</div> 
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=downExcel style="cursor: pointer">엑셀다운</li>
				</ul>
			</div> 
			<table class="basic-list">
				<colgroup>
					<col width="50">
					<col width="150">
					<col width="50">
					<col width="100"> 
					<col width="150">
					<col width="45">
					<col width="45">
					<col width="45">
					<col width="45">  
					<col width="55"> 
				</colgroup>
				<thead>
					<tr>
						<th on-click=listSort data-order-key="no">NO</th>
						<th on-click=listSort data-order-key="scn_cp_push_titl">시나리오 타이틀</th>
						<th on-click=listSort data-order-key="app_tgt_mbr_div_nm">푸시 대상</th>
						<th class="no-order">푸시 쿠폰</th>
						<th on-click=listSort data-order-key="push_time_div_nm">푸시 상황</th>
						<th on-click=listSort data-order-key="exp_yn">노출 현황</th>
						<th on-click=listSort data-order-key="push_cnt">푸시 카운팅</th>
						<th on-click=listSort data-order-key="push_cnt">푸시 카운팅(누적)</th>
						<th on-click=listSort data-order-key="dn_cnt">푸시쿠폰<br/>다운로드</th>
						<th on-click=listSort data-order-key="reg_dttm">등록일</th>
					</tr>
				</thead>
				<tbody>
					{{#pushMsgList}}
					<tr on-click=modifyPushMsg:{{scn_otb_cp_push_seq}},{{scn_otb_div_cd}} style="cursor: pointer"> 
						<td>{{no}}</td> 
						<td class="ellipsis ta-l">{{scn_cp_push_titl}}</td> 
						<td>{{app_tgt_mbr_div_nm}}</td> 
						<td class="ellipsis ta-l">{{cp_titl}}</td> 
						<td class="ellipsis ta-l">{{push_time_div_nm}}</td>  
						<td>{{exp_yn}}</td> 
						<td>{{push_cnt}}</td>
						<td>{{tot_push_cnt}}</td> 
						<td>{{dn_cnt}}</td> 
						<td>{{reg_dttm}}</td> 
					</tr>
					{{/pushMsgList}}
				</tbody>
			</table>
			{{#paging}}
			<div class="paging-wrap">
				<ul class="paging inline-list">
				<li class="prev {{#if paging.page_start == 1}}off{{/if}}" on-click="pageMove:{{paging.page_start}}">PREV</li>
				{{#pages}}
				<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="pageMove:{{this}}">{{this}}</li>
				{{/pages}} 
				<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" on-click="pageMove:{{paging.page_end}}">NEXT</li>
				</ul> 
            </div>
			{{/paging}} 
		</div>
		</script>
	</div>
	<script src="/js/app/appPush/ALBSW021.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
