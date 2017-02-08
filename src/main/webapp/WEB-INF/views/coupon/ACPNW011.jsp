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
		<div id="coupon-list" class="contents">
		<script id="tmpl-coupon-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">쿠폰관리 > 쿠폰목록관리</h1>
			<div class="search-box">
			<input id="copy_cp_seq" type="hidden">
			{{#couponFilter}}
				조회 기간 
				<div class="select-wrap"> 
					<select id="sh_dt_type"  value="{{sh_dt_type}}">
						<option value="0">등록일시</option>
						<option value="1">발급일시작일</option>
						<option value="2">유효기간</option>
					</select> 
				</div> 
				<div class="date-picker2">
					<input type="text" name="datepicker2" id="sh_strt_dt" value="{{sh_strt_dt}}" >
				</div>
				<span>~</span>
				<div class="date-picker2">
					<input type="text" name="datepicker2" id="sh_end_dt" value="{{sh_end_dt}}">
				</div>

				<br/> 
				쿠폰 종류 
				<div class="select-wrap">
					<select id="sh_kind_type"  value="{{sh_kind_type}}">
						<option value="0">전체</option>
						<option value="1">에누리</option>
						<option value="2">할인쿠폰</option>
						<option value="3">교환권</option>
					</select>
				</div>
				<div class="select-wrap">
					<select id="sh_div_type"  value="{{sh_div_type}}">
						<option value="0">전체</option>
						<option value="1">일반</option>
						<option value="2">웰컴용</option>
						<option value="3">시나리오푸시용</option>
					</select>
				</div>
				<br/>
				조회 기준
				<div class="select-wrap">
					<select id="sh_text_type"  value="{{sh_text_type}}">
						<option value="1">타이틀</option>
						<option value="2">사용처</option>
					</select>
				</div>
				<div class="date-picker">
					<input type="text" name="sh_text" id="sh_text" value="{{sh_text}}">
				</div>
				<span on-click="shCoupon" style="cursor: pointer">검색</span>
				<span on-click="shCouponInit" style="cursor: pointer">초기화</span>
			{{/couponFilter}} 
			</div> 
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=downExcel style="cursor: pointer">엑셀다운</li>
					<li on-click=copyCoupon id="copyCpBtn" style="cursor: pointer">쿠폰복사</li>
				</ul>
			</div>
			<table id="event-mng-list" class="basic-list">
				<colgroup>
					<col width="40">
					<col width="50">
					<col width="82">
					<col width="65">
					<col width="65">
					<col width="80">
					<col width="60">
					<col width="90">
					<col width="90">
					<col width="60">
					<col width="60">   
					<col width="60">
					<col width="60">
					<col width="60">
					<col width="60">
					<col width="70">
					<col width="70"> 
					<col width="70">
					<col width="60"> 
					<col width="70"> 
				</colgroup>
				<thead>
					<tr>
						<th class="no-order">선택<br>복사</th>
						<th class="no-order">NO</th>
						<th class="no-order">쿠폰발급 코드</th>
						<th style="cursor: pointer" on-click=listSort data-order-key="cp_div_cd_nm">쿠폰 구분</th>
						<th class=""  on-click=listSort data-order-key="cp_sale_div_cd_nm">할인방식</th> 
						<th class="no-order">사용처</th>  
						<th on-click=listSort data-order-key="cp_kind_cd_nm">쿠폰종류</th> 
						<th on-click=listSort data-order-key="cp_iss_type_cd_nm">발급타입</th>
						<th class="no-order">쿠폰 타이틀</th>
						<th on-click=listSort data-order-key="cp_iss_cnt">전체발행쿠폰수</th>
						<th on-click=listSort data-order-key="push_cnt">푸시 카운팅</th>
						<th on-click=listSort data-order-key="dn_cnt">다운로드 쿠폰수</th>
						<th on-click=listSort data-order-key="use_cnt">회수처리 쿠폰수</th>
						<th on-click=listSort data-order-key="use_rt">쿠폰 회수율</th>
						<th on-click=listSort data-order-key="remain_cnt">쿠폰 잔여수량</th>
						<th on-click=listSort data-order-key="cp_iss_strt_dt">발급일시작일</th>
						<th colspan="2" on-click=listSort data-order-key="cp_act_end_dt">쿠폰 유효기간</th>
						<th on-click=listSort data-order-key="cp_exp_yn">노출<br/> 현황</th>
						<th on-click=listSort data-order-key="reg_dttm">등록일</th>
					</tr>
				</thead>
				<tbody>
					{{#couponList:num}}
					<tr >
						<td><input name='1' type="radio" on-click=selCoupon:{{cp_seq}}></td> 
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{tot_cnt+1-no}}</td>                     
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{cp_iss_cd}}</td>              
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{cp_div_cd_nm}}</td>       
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{cp_sale_div_cd_nm}}</td>     
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{tnt_nm_ko}}</td>              
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{cp_kind_cd_nm}}</td>          
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{cp_iss_type_cd_nm}}</td>                
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{cp_titl}}</td>                
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{cp_iss_cnt}}</td>             
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{push_cnt}}</td>                           
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{dn_cnt}}</td>                 
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{use_cnt}}</td>                
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{use_rt}}</td>                  
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{remain_cnt}}</td>                           
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{cp_iss_strt_dt}}</td>         
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{cp_act_strt_dt}}</td>         
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{cp_act_end_dt}}</td>          
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{cp_exp_yn}}</td>
						<td on-click=modifyCoupon:{{cp_seq}},{{cp_kind_cd}},{{cp_div_cd}} style="cursor: pointer">{{reg_dttm}}</td>
					</tr> 
					{{/couponList}}
					
				</tbody>
			</table>
			{{#paging}}
			<div class="paging-wrap">
				<ul class="paging inline-list">
				<li class="prev {{#if paging.page_start == 1}}off{{/if}}" on-click="pageMove:{{paging.page_start - 1}}">PREV</li>
				{{#pages}}
				<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="pageMove:{{this}}">{{this}}</li>
				{{/pages}}
				<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" on-click="pageMove:{{paging.page_end + 1}}">NEXT</li>
				</ul>
            </div>
			{{/paging}}
		</div>
		</script>
	</div>
	<script src="/js/app/coupon/ACPNW011.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
