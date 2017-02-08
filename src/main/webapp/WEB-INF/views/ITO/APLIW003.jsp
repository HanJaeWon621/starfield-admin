<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
	<div id="location-mng" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-location-mng" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">ITO 관리 > 위치정보이용.제공내역</h1>
			<div class="search-box">
			{{#filter}}
				조회 기간 
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_strt_dt" value="{{sh_strt_dt}}" >
				</div>
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" id="sh_end_dt" value="{{sh_end_dt}}">
				</div> 
				<br/>
				검색어 입력
				<div class="select-wrap">
					<select id="sh_text_type"  value="{{sh_text_type}}">
						<option value="1">대상</option>
						<option value="2">제공받는자</option>
					</select>
				</div>
				<div class="date-picker">
					<input type="text" name="sh_text" id="sh_text" value="{{sh_text}}">
				</div>
				<span on-click="search" style="cursor: pointer">검색</span>
				<span on-click="search_init" style="cursor: pointer">초기화</span>
			{{/filter}}    
			</div>
			<div class="list-top">
				<ul class="inline-list">
				</ul>
			</div>
			<table id="location-mng-list" class="basic-list">
				<colgroup>
					<col width="80">
					<col width="300">
					<col width="100">
					<col width="300">
					<col width="100">
					<col width="200">
				</colgroup>
				<thead> 
					<tr> 
						<th class="no-order">NO</th>
						<th class="no-order">대상</th>
						<th on-click=listSort data-order-key="act_path">취득경로</th>
						<th class="no-order">제공서비스</th>
						<th class="no-order">제공받는자</th>
						<th on-click=listSort data-order-key="f_reg_dttm">이용일시</th>
					</tr>
				</thead>
				<tbody> 
					{{#locationUseList}}
					<tr class="">  
						<td>{{tot_cnt-no+1}}</td> 
						<td>{{uuid}}</td>
						<td>{{act_path}}</td>
						<td>{{svc}}</td>
						<td>{{rcv_nm}}</td>
						<td>{{f_reg_dttm}}</td>
					</tr>
					{{/locationUseList}}
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
<script src="/js/app/ITO/APLIW003.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
