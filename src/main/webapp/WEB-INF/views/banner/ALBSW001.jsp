<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="banner-list" class="contents">
		<script id="tmpl-banner-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">모바일마케팅시스템> 배너관리</h1>
			
			<div class="search-box">
				{{#filter}}
				<span class="select-title">조회 기간</span>
				<div class="select-wrap">
					<select id="search_dt_type"  value="{{search_dt_type}}">
						<option value="1">등록일시</option>
						<option value="2">노출기간</option>
					</select>
				</div>
				<div class="date-picker">
					<input type="text" name="datepicker" id="search_strt_dt" value="{{search_strt_dt}}" >
				</div>
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" id="search_end_dt" value="{{search_end_dt}}">
				</div>
				<br>
				<span class="select-title">조회 기준 </span>
				<div class="select-wrap">
					<select id="sh_post_type"  value="{{sh_post_type}}">
						<option value="0">전체</option>
						<option value="2">쿠폰</option>
						<option value="1">이벤트</option>
					</select>
				</div>
				<div class="select-wrap">
					<select id="sh_text_type"  value="{{sh_text_type}}">
						<option value="1">타이틀</option>
						<option value="2">내용</option>
					</select>
				</div>
				<div class="date-picker">
					<input type="text" name="sh_text" id="sh_text" value="{{sh_text}}" placeholder="타이틀/내용 입력">
				</div> 
				<span on-click="getBannerList"  style="cursor: pointer">검색</span>
				<span on-click="getBannerListInit"  style="cursor: pointer">초기화</span>
				{{/filter}}
			</div>
			   
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=downExcel style="cursor: pointer">엑셀다운</li>
				</ul>
			</div>
			<table id="" class="basic-list">
				<colgroup>
					<col width="50">
					<col width="150">
					<col width="60">
					<col width="120">
					<col width="60">
					<col width="60">
					<col width="60">
					<col width="60">
					<col width="60">
					<col width="60">
				</colgroup>
				<thead>
					<tr>
						<th on-click=listSort data-order-key="no">NO</th>
						<th on-click=listSort data-order-key="bn_titl">배너 타이틀</th>
						<th on-click=listSort data-order-key="bn_post_type">노출물 유형</th>
						<th on-click=listSort data-order-key="link_pg_titl">연결 페이지 타이틀</th>
						<th on-click=listSort data-order-key="reg_dttm">등록일시</th>
						<th on-click=listSort data-order-key="bn_post_end_dt" colspan="2">노출 기간</th>
						<th on-click=listSort data-order-key="ord_seq">정렬 순서</th>
						<th on-click=listSort data-order-key="bn_exp_yn">노출 현황</th>
						<th on-click=listSort data-order-key="click_cnt">클릭 카운팅</th>
					</tr>
				</thead>
				<tbody>
					{{#bannerList}}
					<tr on-click=modifyBanner:{{bn_seq}} style="cursor: pointer">
						<td>{{no}}</td> 
						<td class="ellipsis ta-l">{{bn_titl}}</td>
						<td class="ellipsis">{{bn_post_type}}</td>
						<td class="ellipsis ta-l">{{link_pg_titl}}</td>
						<td>{{f_reg_dttm}}</td>
						<td>{{bn_post_strt_dt}}</td>
						<td>{{bn_post_end_dt}}</td>
						<td>{{ord_seq}}</td> 
						<td>{{bn_exp_yn}}</td>
						<td>{{click_cnt}}</td>
					</tr>
					{{/bannerList}}
					
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
	<script src="/js/app/banner/ALBSW001.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
