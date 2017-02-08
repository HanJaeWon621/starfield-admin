<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!-- 진행 브랜드 설정 팝업-->
	<div id="brand-mng-popup" class="popup-wrap">
	<script id="tmpl-brand-mng-popup" type="text/ractive">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="closePopup">&#215;</span></div>
				<h4 class="popup-title red">브랜드 설정</h4>
				<div class="popup-contents">
					<div class="list-top">
						<div class="select-wrap">
							<select on-change="changeFirstCate" value="{{filter.mama_cate_seq}}">
								<option value="">1차 카테고리 선택</option>
								{{#firstCategories}}
								<option value="{{cate_seq}}">{{cate_nm_ko}}</option>
								{{/firstCategories}}
							</select>
						</div>
						<div class="select-wrap">
							<select id="2nd-category-selector" on-change="changeSecondCate" value="{{filter.cate_seq}}">
								<option value="">2차 카테고리 선택</option>
								{{#secondCategories}}
								<option value="{{cate_seq}}">{{cate_nm_ko}}</option>
								{{/secondCategories}}
							</select>
						</div>
						<div class="search-input-wrap">
							<input class="search-input" type="text" placeholder="브랜드명을 입력하세요" value="{{filter.tnt_nm_ko}}">
							<div on-click="search" class="basic-btn search gold" id="search-btn">검색</div>
						</div>
					</div>
					<div id="brand-result-area">
						<h3>브랜드 목록</h3>
						<table id="brand-result" class="basic-list">
							<colgroup>
								<col width="*">
								<col width="300">
								<col width="80">
							</colgroup>
							<thead>
								<tr class="cursor-d">
									<!-- <th class="no-order"><input type="checkbox"></th> -->
									<th on-click="search" data-order-key="cate_path">카테고리</th>
									<th on-click="search" data-order-key="tnt_nm_ko">브랜드명</th>
									<th on-click="search" data-order-key="room_num">호수</th>
								</tr>
							</thead>
							<tbody>
							{{#tenants}}
								<tr class="cursor-p">
									<!-- <td class="no-order"><input type="checkbox"></td> -->
									<td class="ellipsis ta-l">{{{cate_path}}}</td>
									<td class="ellipsis cursor-p" on-click="selectTenant:{{.}}">{{tnt_nm_ko}}</td>
									<td>{{room_num}}</td>
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
				</div>
			</div>
		</div>
	</script>
	</div>
	<!--//진행 브랜드 설정 팝업-->
<script src="/js/app/common/tenant-popup.js" type="text/javascript"></script>