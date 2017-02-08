<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="coupon-list" class="contents">
		<script id="tmpl-coupon-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">쿠폰 관리 > 쿠폰 다운로드 내역 조회</h1>
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
					<select id="sh_div_type"  value="{{sh_div_type}}">
						<option value="0">전체</option>
						<option value="2">직접회수형</option>
						<option value="1">자동회수형</option>
						<option value="3">교환권</option>
					</select>
				</div>
				<div class="select-wrap">
					<select id="sh_text_type"  value="{{sh_text_type}}">
						<option value="1">쿠폰번호</option>
						<option value="2">사용자 ID</option>
						<option value="3">사용처</option>
						<option value="4">쿠폰타이틀</option>
					</select>
				</div>
				<div class="date-picker">
					<input type="text" name="sh_text" id="sh_text" value="{{sh_text}}" placeholder="쿠폰번호/사용자ID/사용처 /쿠폰타이틀 검색">
				</div>
				<span on-click="shCoupon" style="cursor: pointer">검색</span>
				<span on-click="shCouponInit">초기화</span>
				{{/couponFilter}}
			</div>  
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=downExcel>엑셀다운</li>
				</ul>
			</div>
			<table id="" class="basic-list">
				<colgroup> 
					<col width="50">
					<col width="100">
					<col width="70">
					<col width="70">
					<col width="150">
					<col width="80"> 
					<col width="*">
					<col width="150">
					<col width="*"> 
				</colgroup> 
				<thead>
					<tr> 
						<th on-click=listSort data-order-key="no">NO</th>
						<th on-click=listSort data-order-key="cp_iss_cd">쿠폰발급 코드</th>
						<th on-click=listSort data-order-key="cp_div_cd_nm">쿠폰 구분</th>
						<th on-click=listSort data-order-key="cp_kind_cd_nm">쿠폰 종류</th>
						<th on-click=listSort data-order-key="cp_iss_bcd_id">쿠폰 번호</th>
						<th on-click=listSort data-order-key="cust_id">사용자 ID</th>
						<th on-click=listSort data-order-key="cp_titl">쿠폰 타이틀</th>
						<th on-click=listSort data-order-key="cp_dn_dt">다운로드일</th>
						<th on-click=listSort data-order-key="use_tnt_nm">사용처</th>
					</tr>
				</thead>
				<tbody>
					{{#couponDownList}}
					<tr on-click=getDownCoupon:{{cp_seq}},{{cust_id}} style="cursor: pointer">
						<td>{{no}}</td>                      
						<td>{{cp_iss_cd}}</td>              
						<td style="cursor: pointer" >{{cp_div_cd_nm}}</td>              
						<td>{{cp_kind_cd_nm}}</td>              
						<td>{{cp_iss_bcd_id}}</td>              
						<td>{{cust_id}}</td>              
						<td class="ellipsis ta-l">{{cp_titl}}</td>              
						<td>{{cp_dn_dt}}</td>              
						<td class="ellipsis ta-l">{{use_tnt_nm}}</td>              
					</tr> 
					{{/couponDownList}}
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
	<script src="/js/app/coupon/ACPNW021.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
