<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
	<div id="location-mng" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-location-mng" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">• ITO 관리 > APP 로그 관리</h1>
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
				조회기준
				<div class="select-wrap">
					<select id="sh_type1"  value="{{sh_type1}}">
						<option value="0">전체</option>
						<option value="2">사용로그</option>
						<option value="1">설치로그</option>
						<option value="3">앱실행로그</option>
					</select>
				</div>
				<div class="select-wrap">
					<select id="sh_type2"  value="{{sh_type2}}">
						<option value="-1">전체</option>
						<option value="1">회원</option>
						<option value="0">비회원</option>
					</select>
				</div>
				<br/>
				검색어 입력
				<div class="select-wrap">
					<select id="sh_text_type"  value="{{sh_text_type}}">
						<option value="2">접속계정</option>
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
					<li on-click=downExcel style="cursor: pointer">엑셀 다운</li>
				</ul>
			</div>
			<table id="location-mng-list" class="basic-list">
				<colgroup>
					<col width="60">
					<col width="80">
					<col width="100">
					<col width="250">
					<col width="100">
					<col width="100">
				</colgroup>
				<thead> 
					<tr> 
						<th class="no-order">번호</th>
						<th on-click=listSort data-order-key="grp_log_type_nm">로그유형</th>
						<th on-click=listSort data-order-key="mbr_div_nm">회원구분</th>
						<th class="no-order">접속계정(달말)ID</th>
						<th on-click=listSort data-order-key="f_reg_dttm">발생일시</th>
						<th on-click=listSort data-order-key="log_type_nm">사용로그명</th>
					</tr>
				</thead>
				<tbody> 
					{{#appLogList}}
					<tr class="">  
						<td>{{tot_cnt-no+1}}</td> 
						<td>{{grp_log_type_nm}}</td>
						<td>{{mbr_div_nm}}</td>
						<td>{{uuid}}</td>
						<td>{{f_reg_dttm}}</td>
						<td>{{log_type_nm}}</td>
					</tr>
					{{/appLogList}}
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
<script src="/js/app/ITO/APLIW005.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
