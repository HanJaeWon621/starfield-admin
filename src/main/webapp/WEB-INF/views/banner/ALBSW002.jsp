<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>

<style type="text/css" >
</style> 
	<div id="banner-detail" class="contents" >
	<script id="tmpl-banner-detail" type="text/ractive">
	<div id="event-reg" class="contents-area">
		<div class="contents-wrap">
       		<h1 class="menu-title">신규 배너 등록</h1>
			<table id="event-reg" class="input-list">
				<tr>
					<th class="must">노출유형</th>
					<td >
						<div class="select-wrap">
							<select value="{{banner.bn_post_type}}" on-change="chType">
								<option value="1">이벤트</option>
								<option value="2">쿠폰</option>
							</select>
						</div>
					</td> 
				</tr> 
				<tr>
					<th class="must">타이틀</th>
					<td >
						<div class="text-input-wrap">
							<input class="text-input" maxlength="23" value="{{banner.bn_titl}}" type="text" placeholder="공백포함 23자 입력">
						</div>
					</td>  
				</tr>
				<tr>
					<th class="must">정렬순서</th>
					<td >
						<div class="select-wrap">
							<select value="{{banner.ord_seq}}">
								<option value="">선택</option> 
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
							</select>
						</div>
					</td> 
				</tr>
				<tr>
					<th class="must">노출기간</th>
					<td >  
						<div class="date-picker"> 
							<input type="text" name="datepicker" placeholder="시작일" id="bn_post_strt_dt" value="{{banner.bn_post_strt_dt}}">
						</div>
						<span>~</span> 
						<div class="date-picker">  
							<input type="text" name="datepicker" placeholder="종료일" id="bn_post_end_dt" value="{{banner.bn_post_end_dt}}">
						</div> 
					</td>  
				</tr>
				<tr >
					<th class="must" rowspan="2">이미지 등록</th>
					<td>  
  						<div class="img-uploader {{#if banner.bn_bi_img_seq != null}}after{{else}}before{{/if}}" id="bn_bi_img_seq">
							<div class="img-preview"> 
								<div class="icon-delete" on-click="deleteImg"></div>
								<img id="bn_bi_img_seq_uri" src="{{banner.bn_bi_img}}"> 
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">BI이미지</div>
								<span class="comment">선택된 파일 없음</span>
							</div> 등록 이미지 권장 사이즈 : 90 * 90
						</div>
					</td>   
				</tr> 
				<tr>
					<td> 
  						<div class="img-uploader {{#if banner.bn_all_img_seq != null}}after{{else}}before{{/if}}" id="bn_all_img_seq">
							<div class="img-preview"> 
								<div class="icon-delete" on-click="deleteImg"></div>
								<img id="bn_all_img_seq_uri" src="{{banner.bn_all_img}}"> 
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">배너 전체 이미지</div>
								<span class="comment">선택된 파일 없음</span>
							</div> 등록 이미지 권장 사이즈 : 750 * 132
						</div>
					</td> 
				</tr>
				<tr>
					<th class="must">연결 페이지</th>
					<td> 
						<div class="date-picker">
							<input type="text" id="linkPg" placeholder="이벤트/쿠폰 자동 입력">
						</div>  
						<div class="basic-btn gold" on-click="getLinkPg">불러오기</div>
					</td>  
				</tr> 
 
				<tr>
					<th class="must">상세 내용 입력</th>
					<td >
						<div class="text-input-wrap">
							<input class="text-input" value="{{banner.bn_dtl_cts}}" type="text" placeholder="상세 내용 입력">
						</div>
					</td>  
				</tr>  
			</table>

		</div>
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="preview">미리보기</div>
			<div class="basic-btn gold" id="btnSave" on-click="saveBanner">저장</div>
			<div class="basic-btn gold" id="btnPostion" on-click="regBannerPosting" style="display: none;">게시</div>
			<div class="basic-btn gold" id="btnUnPostion" on-click="regBannerUnPosting" style="display: none;">미게시</div>
		</div>
	</div>
	<div id="couponP" class="popup-wrap">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="closeCoupon">&#215;</span></div>
				<h4 class="popup-title red">쿠폰 목록</h4>
				<div class="popup-table-top">
					<div class="search-input-wrap">
						<input class="search-input" value="{{filter.cp_titl}}" id="cp_titl" type="text" placeholder="쿠폰 명">
						<div class="basic-btn search" on-click="getLinkPg">검색</div>
					</div> 
				</div>
				<div class="popup-contents">
					<table class="basic-list">
						<colgroup>
							<col width="5%">
							<col width="5%">
							<col width="25%">
							<col width="25%">
							<col width="10%">
							<col width="10%">
							<col width="10%">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order">선택</th>
								<th class="no-order">번호</th> 
								<th class="no-order">쿠폰명</th>
								<th class="no-order">테넌트</th>
								<th class="no-order">발행<br/>쿠폰수</th>
								<th class="no-order">발급완료<br/>쿠폰수</th>
								<th class="no-order">잔여<br/>쿠폰수</th>
							</tr>
						</thead>
						<tbody> 
						{{#couponList:num}} 
							<tr> 
								<td><input type="radio" name="cp_ck" value="{{cp_seq}}"></td>
								<td>{{no}}</td> 
								<td class="ellipsis ta-l" id="{{cp_seq}}">{{cp_titl}}</td> 
								<td class="ellipsis ta-l">{{tnt_nm}}</td>
								<td>{{cp_iss_cnt}}</td>   
								<td>{{dn_cnt}}</td>
								<td>{{remain_cnt}}</td>   
							</tr>
						{{/couponList}}  
						</tbody>
					</table>
					{{#paging}} 
					<div class="paging-wrap">
						<ul class="paging inline-list">
							<li class="prev {{#if paging.page_start == 1}}off{{/if}}" 
								{{#if paging.page_start == 1}} on-click="cpPageMove:{{paging.page_start - 1}}" {{/if}}>PREV</li>
							{{#pages}}
								<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="cpPageMove:{{this}}">{{this}}</li>
							{{/pages}}
							<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" 
								{{#if paging.page_end == paging.total_page_cnt}} on-click="cpPageMove:{{paging.page_end + 1}}" {{/if}}>NEXT</li>
						</ul>
         		   </div> 
					{{/paging}}
					<div class="">
						<div class="basic-btn" on-click="closeCoupon">닫기</div>
						<div class="basic-btn" on-click="selCoupon">적용</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="eventP" class="popup-wrap">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="closeEvent">&#215;</span></div>
				<h4 class="popup-title red">이벤트 목록</h4>
				<div class="popup-table-top">
					<div class="search-input-wrap">
						<input class="search-input" value="{{filter.evt_titl}}" id="cp_titl" type="text" placeholder="이벤트 명">
						<div class="basic-btn search" on-click="getLinkPg">검색</div>
					</div> 
				</div>
				<div class="popup-contents">
					<table class="basic-list">
						<colgroup>
							<col width="15%">
							<col width="15%">
							<col width="*">
						</colgroup>
						<thead>
							<tr>
								<th>선택</th> 
								<th>번호</th>
								<th>이벤트명</th>
							</tr>
						</thead>
						<tbody> 
						{{#eventList:num}} 
							<tr> 
								<td><input type="radio" name="evt_ck" value="{{evt_seq}}"></td> 
								<td>{{no}}</td>   
								<td  class="ellipsis ta-l" id="{{evt_seq}}">{{evt_titl}}</td> 
							</tr>
						{{/eventList}}  
						</tbody>
					</table>
					{{#paging}} 
					<div class="paging-wrap">
						<ul class="paging inline-list">
							<li class="prev {{#if paging.page_start == 1}}off{{/if}}" 
								{{#if paging.page_start == 1}} on-click="evtPageMove:{{paging.page_start - 1}}" {{/if}}>PREV</li>
							{{#pages}}
								<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="evtPageMove:{{this}}">{{this}}</li>
							{{/pages}}
							<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" 
								{{#if paging.page_end == paging.total_page_cnt}} on-click="evtPageMove:{{paging.page_end + 1}}" {{/if}}>NEXT</li>
						</ul>
         		   </div> 
					{{/paging}}
					<div class="">
						<div class="basic-btn" on-click="closeEvent">닫기</div>
						<div class="basic-btn" on-click="selEvent">적용</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--배너 미리보기 팝업-->
	<div id="banner-display-popup" class="popup-wrap"><!--on class 추가-->
		<div class="popup-container"> 
			<div class="popup default-size">
				<div class="popup-header"><span class="btn-close" on-click="pv_close">&#215;</span></div>
				<h4 class="popup-title red">배너 미리보기</h4>
				<div class="popup-contents"> 
					<div class="map-area">
						<img src="" alt="지도배경이미지">
					</div>
					<div class="banner-area">
						<div class="banner-img-wrap" id="pv_imgf1" style="display: none;">
							<img src=""  id="pv_img1" alt="배너이미지">
						</div>
						<div class="banner-text-wrap"id="pv_textf" style="display: none;">
							<div class="banner-title" id="pv_title">스타필드 하남 오픈 기념 할인 쿠폰</div>
							<div class="banner-sub-title" id="pv_date">기간 2016.5.28 - 2016.6.28</div>
						</div>
						<div class="banner-icon" id="pv_imgf2" style="display: none;">
							<img src="" id="pv_img2" alt="배너아이콘">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--//배너 미리보기 팝업-->
	</script> 
	</div> 
	<script src="/js/app/banner/ALBSW002.js" type="text/javascript" ></script>
<%@ include file="../common/footer.jsp" %>