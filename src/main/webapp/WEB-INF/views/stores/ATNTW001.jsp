<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="tenant-mng" class="contents-area">
	<script id="tmpl-tenant-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">점포 관리 - 매장 관리 - 매장 목록</h1>
			<div class="search-box">
				<span class="select-title">1차 카테고리</span>
				<div class="select-wrap cate-select-wrap">
					<select on-change="changeFirstCate" value="{{filter.mama_cate_seq}}">
						<option value="">전체</option>
						{{#firstCategories}}
						<option value="{{cate_seq}}">{{cate_nm_ko}}</option>
						{{/firstCategories}}
					</select>
				</div>
				<span class="select-title">2차 카테고리</span>
				<div class="select-wrap cate-select-wrap">
					<select on-change="changeSecondCate" value="{{filter.cate_seq}}">
						<option value="">전체</option>
						{{#secondCategories}}
						<option value="{{cate_seq}}">{{cate_nm_ko}}</option>
						{{/secondCategories}}
					</select>
				</div>
				<span class="select-title">매장구분</span>
				<div class="select-wrap">
				<select id="tnt-type" on-change="search" value="{{filter.tnt_type}}">
					<option value="" selected="selected">전체</option>
					<c:forEach var="option" items="${tenantType}">				
						<option value="${option.codeCd}">${option.codeNm}</option>
					</c:forEach>
				</div>
				<div class="search-input-wrap">
					<input class="search-input" type="text" placeholder="매장명을 입력해주세요" value="{{filter.tnt_nm_ko}}">
					<div on-click="search" id="search-btn" class="basic-btn search gold">검색</div>
				</div>
			</div>
			<div class="list-top">
				<ul class="inline-list">
					<li on-click="syncRedis">운영데이터 동기화</li><li on-click="excelDownload">엑셀다운</li><li on-click="add">추가</li><li on-click="delete">선택 삭제</li>
				</ul>
			</div>
			<table id="tenant-mng-list" class="basic-list">
				<colgroup>
					<col width="60">
					<col width="*">
					<col width="120">
					<col width="120">
					<col width="80">
					<col width="80">
					<col width="70">					
					<col width="70">
					<col width="90">
					<col width="90">
					<col width="90">
					<col width="60">
				</colgroup>
				<thead>
					<tr>
						<th class="no-order">NO</th>
						<th on-click="search" data-order-key="cate_path">카테고리</th>
						<th class="no-order">BI이미지</th>
						<th on-click="search" data-order-key="tnt_nm_ko">매장명</th>
						<th on-click="search" data-order-key="tnt_type">매장구분</th>
						<th on-click="search" data-order-key="room_num">호수</th>
						<th on-click="search" data-order-key="evt_cnt">이벤트</th>
						<th on-click="search" data-order-key="cp_cnt">쿠폰</th>
						<th on-click="search" data-order-key="opr_sts">운영상태</th>
						<th on-click="search" data-order-key="opr_sts">정보완결성</th>
						<th on-click="search" data-order-key="open_yn">노출여부</th>
						<th class="no-order">선택</th>
					</tr>
				</thead>
				<tbody>
					{{#tenants}}
					<tr>
						<td>{{no}}</td>
						<td class="ta-l">{{{cate_path}}}</td>
						<td class="logo-cell {{#if !img_logo_uri}} no-logo {{/if}}"><!--logo 없을 경우 no-logo class 추가-->
							<span class="img-wrap"><img src="{{img_logo_uri}}"></span>
							<span class="no-logo-icon"><img src="/images/icon_preview.png"></span>
						</td>
						<td class="ta-l cursor-p" on-click="view:{{tnt_seq}}">{{tnt_nm_ko}}</td>
						<td>{{tnt_type.codeNm}}</td>
						<td>{{room_num}}</td>
						<td class="cursor-p" on-click="openEventPopup:{{evt_cnt}},{{tnt_seq}}">{{evt_cnt}}</td>
						<td class="cursor-p" on-click="openCouponPopup:{{cp_cnt}},{{tnt_seq}}">{{cp_cnt}}</td>
						<td>{{opr_sts.codeNm}}</td>
						<td class="{{#if opr_sts.codeCd != '0'}} green {{else}} red {{/if}}">{{#if opr_sts.codeCd != '0'}} 완결 {{else}} 미완결 {{/if}}</td><!-- .green : 완결 / .red: 미완 -->
						<td class="{{#if open_yn == 'Y'}} blue {{else}} red {{/if}}">
							<div class="basic-btn free-width" on-click="open:{{opr_sts.codeCd}},{{#if open_yn == 'Y'}}'0'{{else}}'1'{{/if}},{{tnt_seq}}">{{#if open_yn == 'Y'}} 노출 {{else}} 미노출 {{/if}}</div>
						</td>
						<td><input type="checkbox" name="checked" value="{{.tnt_seq}}" data-evt_cnt="{{evt_cnt}}" data-cp_cnt="{{cp_cnt}}"></td>
					</tr>
					{{/tenants}}
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
	<!--//진행중인 이벤트/쿠폰 이벤트 팝업-->
	<%@ include file="../common/event-popup.jsp" %>
	<%@ include file="../common/coupon-popup.jsp" %>
	<%@ include file="../common/loading-popup.jsp" %>
	<script src="/js/app/stores/ATNTW001.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>