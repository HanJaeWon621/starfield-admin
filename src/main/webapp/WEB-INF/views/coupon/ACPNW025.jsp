<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="coupon-list" class="contents">
		<script id="tmpl-coupon-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">쿠폰 관리 > 쿠폰 사용내역조회(가승인)</h1>
			<input id="log_seq" type="hidden">
			<div class="search-box">
				{{#couponFilter}}
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
					<select id="sh_text_type"  value="{{sh_text_type}}">
						<option value="1">쿠폰번호</option>
						<option value="2">사용자 ID</option>
						<option value="3">사용처</option>
						<option value="4">사용자</option>
						<option value="5">전화번호</option>
						<option value="6">쿠폰타이틀</option>
					</select>
				</div>
				<div class="date-picker">
					<input type="text" name="sh_text" id="sh_text" value="{{sh_text}}" placeholder="쿠폰번호/사용자ID/사용처/사용자/전화번호/쿠폰타이틀 검색">
				</div>
				<span on-click="shCoupon">검색</span>
				<span on-click="shCouponInit">초기화</span>
				{{/couponFilter}}
			</div>  
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=cnclCpUsePreAppr id="copyCpBtn" style="cursor: pointer">가승인취소</li>
				</ul>
			</div>
			<table id="" class="basic-list">
				<colgroup>
					<col width="30">
					<col width="50">
					<col width="60">
					<col width="90">
					<col width="90">
					<col width="70">
					<col width="70">
					<col width="150">  
					<col width="90">
					<col width="*">
					<col width="90"> 
					<col width="90"> 
					<col width="70">  
					<col width="70">  
				</colgroup> 
				<thead>
					<tr>
						<th class="no-order">선택</th> 
						<th on-click=listSort data-order-key="no">NO</th>
						<th class="no-order">사용자</th>
						<th class="no-order">전화번호</th>
						<th on-click=listSort data-order-key="cp_iss_cd">쿠폰발급 코드</th>
						<th on-click=listSort data-order-key="cp_div_cd_nm">쿠폰 구분</th>
						<th on-click=listSort data-order-key="cp_kind_cd_nm">쿠폰 종류</th>
						<th on-click=listSort data-order-key="cp_iss_bcd_id">쿠폰 번호</th>
						<th on-click=listSort data-order-key="cust_id">사용자 ID</th>
						<th on-click=listSort data-order-key="cp_titl">쿠폰 타이틀</th>
						<th on-click=listSort data-order-key="cp_prc_dt">처리 일시</th>
						<th on-click=listSort data-order-key="cp_use_sts_cd_nm">사용처리결과</th>
						<th on-click=listSort data-order-key="reg_usr">처리자</th>
						<th on-click=listSort data-order-key="tnt_nm_ko">사용처</th>
					</tr>
				</thead>
				<tbody>
					{{#couponUseList}}
					<tr>
						<td><input name='1' type="radio" on-click=selList:{{log_seq}}></td>
						<td>{{tot_cnt+1-no}}</td> 
						<td>{{user_nm_f}}</td>
						<td>{{phone_num_f}}</td>
						<td>{{cp_iss_cd}}</td>              
						<td>{{cp_div_cd_nm}}</td>              
						<td>{{cp_kind_cd_nm}}</td>              
						<td>{{cp_iss_bcd_id}}</td>              
						<td>{{cust_id}}</td>               
						<td class="ellipsis ta-l">{{cp_titl}}</td>              
						<td>{{cp_prc_dt}}</td>              
						<td>{{cp_use_sts_cd_nm}}</td>              
						<td>{{reg_usr}}</td>              
						<td>{{use_tnt_nm}}</td>              
					</tr> 
					{{/couponUseList}}
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
	<script src="/js/app/coupon/ACPNW025.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
