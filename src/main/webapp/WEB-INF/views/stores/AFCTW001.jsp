<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="facility-group-mng" class="contents-area">
	<script id="tmpl-facility-group-mng" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">매장 관리 - 편의시설 관리 - 그룹 목록</h1>
			<div class="search-box">
				<div class="search-input-wrap">
					<input class="search-input" type="text" placeholder="편의시설 그룹명을 입력해주세요" value="{{filter.conv_faci_nm_ko}}">
					<div on-click="search" id="search-btn"  class="basic-btn search gold">검색</div>
				</div>
			</div>
			<div class="list-top">
				<ul class="inline-list">
					<li on-click="syncRedis">운영데이터 동기화</li><li on-click="excelDownload">엑셀다운</li><li on-click="viewSortPopup">게시순서수정</li><li on-click="add">그룹추가</li><li on-click="delete">선택 삭제</li>
				</ul>
			</div>
			<table id="facility-group-mng-list" class="basic-list">
				<colgroup>
					<col width="80">
					<col width="160">
					<col width="*">
					<col width="100">
					<col width="160">
					<col width="100">
					<col width="150">
					<col width="80">
				</colgroup>
				<thead>
					<tr>
						<th class="no-order">NO</th>
						<th on-click="search" data-order-key="faci_type">시설구분</th>
						<th on-click="search" data-order-key="conv_faci_nm_ko">그룹명</th>
						<th class="no-order">기본 아이콘</th>
						<th on-click="search" data-order-key="conv_faci_cnt">등록 시설 수</th>
						<th on-click="search" data-order-key="sort_ord">노출순서</th>
						<th on-click="search" data-order-key="sts">노출여부</th>
						<th class="no-order">선택</th>
					</tr>
				</thead>
				<tbody>
					{{#facilityGroup}}
					<tr>
						<td>{{no}}</td>
						<td>{{faci_type.codeNm}}</td>
						<td class="cursor-p ellipsis" on-click="view:{{conv_faci_seq}}">{{conv_faci_nm_ko}}</td>
						<td class="facility-icon-cell">
							{{#if img_seq_icon_uri}}
							<div class="facility-icon-wrap">
								<img src="{{img_seq_icon_uri}}">
							</div>
							{{else}}
							-
							{{/if}}
						</td>
						<td>
							<div class="basic-btn free-width" on-click="goFacilityDetail:{{conv_faci_seq}}">{{conv_faci_cnt}}개소</div>
						</td>
						<td>{{#if faci_type.codeCd == 'Y'}}{{sort_ord}}{{else}}-{{/if}}</td>
						<td class="{{#if sts == '1'}} blue {{else}} red {{/if}}">
							<div class="basic-btn free-width" on-click="open:{{#if sts == '1'}}'0'{{else}}'1'{{/if}},{{conv_faci_seq}}">{{#if sts == '1'}} 노출 {{else}} 미노출 {{/if}}</div>
						</td>
						<td><input type="checkbox" name="checked" data-conv_faci_cnt="{{conv_faci_cnt}}" value="{{.conv_faci_seq}}"></td>
					</tr>
					{{/facilityGroup}}
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
		
	<!--편의시설 그룹 정렬 팝업-->
	<div id="facility-group-order-popup" class="popup-wrap">
		<script id="tmpl-facility-group-order-popup" type="text/ractive">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
				<h4 class="popup-title red">편의시설 관리 - 그룹 정렬</h4>
				<div class="popup-contents">
					<div id="facility-group-order-list" class="list-table">
						<div class="t-row list-head">
							<div class="t-cell">NO</div>
							<div class="t-cell">편의시설 그룹</div>
							<div class="t-cell">아이콘</div>
						</div>
						<ul class="list-body sort">
						{{#facilityGroup}}
							<li class="t-row cursor-m t-row-h42" data-no={{no}}>
								<div class="t-cell">{{no}}</div>
								<div class="t-cell">{{conv_faci_nm_ko}}</div>
								<div class="t-cell facility-icon-cell">
									<div class="facility-icon-wrap cursor-d">
										<img src="{{img_seq_icon_uri}}">
									</div>
								</div>
							</li>
						{{/facilityGroup}}
						</ul>
					</div>
				</div>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="close">취소</div>
					<div class="basic-btn gold" on-click="save">저장</div>
				</div>
			</div>
		</div>
		</script>
	</div>
	<!--//편의시설 그룹 등록 팝업-->
		
	<script src="/js/app/stores/AFCTW001.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-sortable.js" type="text/javascript"></script>
	<%@ include file="../common/loading-popup.jsp" %>
<%@ include file="../common/footer.jsp" %>