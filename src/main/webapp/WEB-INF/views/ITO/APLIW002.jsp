<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
		<div id="list" class="contents">
		<script id="tmpl-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">ITO관리 > 사용자위치정보 삭제관리</h1>
			
			<div class="search-box">
				{{#filter}}
				<span class="select-title">조회일 기준 구분</span>
				<div class="select-wrap">
					<select id="date_type"  value="{{date_type}}">
						<option value="1">삭제요청일</option>
						<option value="2">삭제완료일</option>
					</select>
				</div>
				<div class="date-picker">
					<input type="text" name="datepicker" id="stat_date" value="{{stat_date}}" >
				</div>
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" id="search_end_dt" value="{{end_date}}">
				</div>
				<br/>
				<span class="select-title">조회구분</span>
				<div class="select-wrap w80">
					<select id="location-result-selector" value="{{sh_div}}">
						<option value="-1">전체</option>
						<option value="1">고객요청</option>
						<option value="2">보유기간만료</option>
					</select>
				</div>
				<br/>
				<span class="select-title">조회 기준</span>
				<div class="select-wrap">
					<select id="sh_text_type"  value="{{sh_text_type}}">
						<option value="-1">전체</option>
						<option value="1">이름</option>
						<option value="2">신세계포인트번호</option>
						<option value="3">전화번호</option>
					</select>
				</div> 
				<div class="date-picker">
					<input type="text" name="sh_text" id="sh_text" value="{{sh_text}}">
				</div> 
				<span on-click="getList"  style="cursor: pointer">검색</span>
				<span on-click="getListInit"  style="cursor: pointer">초기화</span>
				{{/filter}}
			</div>
			   
			<div class="list-top">
			</div>
			<table id="" class="basic-list">
				<colgroup>
					<col width="50">
					<col width="60">
					<col width="120">
					<col width="60">
					<col width="60">
					<col width="60">
					<col width="100">
					<col width="100">
					<col width="60">
				</colgroup>
				<thead>
					<tr>
						<th on-click=listSort data-order-key="rnum">NO</th>
						<th on-click=listSort data-order-key="name">회원명</th>
						<th on-click=listSort data-order-key="card_no">신세계포인트번호</th>
						<th on-click=listSort data-order-key="phone_num">전화번호</th>
						<th on-click=listSort data-order-key="del_div">삭제사유</th>
						<th on-click=listSort data-order-key="act_yn">위치정보<br>삭제처리</th>
						<th on-click=listSort data-order-key="req_del_dttm">삭제요청일</th>
						<th on-click=listSort data-order-key="act_dttm">삭제처리<br>완료일시</th>
						<th on-click=listSort data-order-key="act_usr">조치자ID</th>
					</tr>
				</thead>
				<tbody>
					{{#list:num}}
					<tr style="cursor: pointer">
						<td>{{rnum}}</td> 
						<td class="ellipsis ta-l">{{name}}</td>
						<td class="ellipsis">{{card_no}}</td>
						<td>{{phone_num}}</td>
						<td>{{#if del_div == 0}}고객요청{{elseif del_div == 1}}보유기간만료{{/if}}</td>
						<td id="delete_bt{{rnum}}">{{#if act_yn == 'N'}}<input type="button"  value="삭제요청" on-click="del:{{plid_mng_seq}},{{phone_num_nf}},{{rnum}}">{{elseif act_yn == 'Y'}}삭제처리{{/if}}</td>
						<td>{{req_del_dttm}}</td>
						<td id="delete_date{{rnum}}">{{act_dttm}}</td>  
						<td id="delete_usr{{rnum}}">{{act_usr}}</td>
					</tr>
					{{/list}}
					
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
	<script src="/js/app/ITO/APLIW002.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
