<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="facility-mng" class="contents-area">
		<script id="tmpl-facility-mng" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">매장 관리 - 편의시설 관리 - 편의시설 목록</h1>
			<div class="search-box">
				<div class="search-input-wrap">
					<input class="search-input" type="text" placeholder="편의시설명을 입력해주세요" value="{{filter.conv_faci_nm_ko}}">
					<div on-click="search" id="search-btn" class="basic-btn search gold">검색</div>
				</div>
			</div>
			<div class="list-top">
				<div class="basic-btn w155" on-click="goFacilityGroup">편의시설 그룹 목록</div>
				<ul class="inline-list">
					<li on-click="add">편의시설 추가</li><li on-click="delete">선택 삭제</li>
				</ul>
			</div>
			<table id="facility-mng-list" class="basic-list">
				<colgroup>
					<col width="80">
					<col width="*">
					<col width="*">
					<col width="100">
					<col width="150">
					<col width="150">
					<col width="150">
					<col width="80">
				</colgroup>
				<thead>
					<tr>
						<th class="no-order">NO</th>
						<th on-click="search" data-order-key="conv_faci_group_nm_ko">편의시설 그룹</th>
						<th on-click="search" data-order-key="conv_faci_nm_ko">편의시설명</th>
						<th on-click="search" data-order-key="fl">층수</th>
						<th on-click="search" data-order-key="room_num">호수</th>
						<th on-click="search" data-order-key="zone_id">ZONE ID</th>
						<th on-click="search" data-order-key="sts">노출여부</th>
						<th class="no-order">선택</th>
					</tr>
				</thead>
				<tbody>
					{{#facility}}
					<tr>
						<td>{{no}}</td>
						<td class="ellipsis">{{conv_faci_group_nm_ko}}</td>
						<td class="cursor-p ellipsis" on-click="view:{{conv_faci_dtl_seq}}">{{conv_faci_nm_ko}}</td>
						<td>{{fl}}</td>
						<td>{{room_num}}</td>
						<td>{{zone_id}}</td>
						<td class="cursor-d {{#if sts == '1'}} blue {{else}} red {{/if}}">
							<div class="basic-btn free-width" on-click="open:{{#if sts == '1'}}'0'{{else}}'1'{{/if}},{{data_sts}},{{conv_faci_dtl_seq}}">{{#if sts == '1'}} 노출 {{else}} 미노출 {{/if}}</div>
						</td>
						<td class="cursor-d"><input type="checkbox" name="checked" value="{{.conv_faci_dtl_seq}}"></td>
					</tr>
					{{/facility}}
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
<script type="text/javascript">
var conv_faci_seq = '${conv_faci_seq}' || '';
</script>
<script src="/js/app/stores/AFCTW003.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>