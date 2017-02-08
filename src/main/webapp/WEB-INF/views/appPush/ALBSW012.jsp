<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>

<style type="text/css">
</style> 
<div id="wcPushMsg-detail" class="contents" >
<script id="tmpl-wcPushMsg-detail" type="text/ractive">
	<div id="event-reg" class="contents-area">
		{{#wcPushMsg}}
		<div class="contents-wrap">
			<h1 class="menu-title">• APP Push 관리 시스템 > 푸시 메시지 관리 > 푸시메시지등록</h1>
			<table id="event-reg" class="input-list">
				<tr> 
					<th class="must">타이틀</th>
					<td> 
						<div class="text-input-wrap ex-mid-width ex-input-btm-margin">
							<input class="text-input" value="{{push_titl}}" id="push_titl" maxlength="22" type="text" placeholder="메시지 입력/22자(44byte)입력제한">
						</div>
						<div>
						타이틀 이미지가 등록 될 경우 해당 타이틀은 팝업 화면에 표기되지 않으며 목록에만 노출됩니다.
						</div> 
					</td>  
				</tr> 
				<tr class="h92">
					<th>타이틀 이미지 등록</th>
					<td>
						<div class="img-uploader {{#if dtl_titl_img_seq != null}}after{{else}}before{{/if}}" id="titl_img_seq">
							<div class="img-preview"> 
								<div class="icon-delete" on-click="deleteImg"></div>
								<img id="titl_img_seq_uri" src="{{dtl_titl_img_seq}}"> 
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>  이미지 파일 업로드(600 * 180)
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th class="must">노출 기간 설정</th>
					<td >
						<div class="date-picker"> 
							<input type="text" name="datepicker" id="exp_strt_dt" value="{{exp_strt_dt}}" placeholder="시작일">
						</div>
						<span> ~ </span> 
						<div class="date-picker">
							<input type="text" name="datepicker" id="exp_end_dt" value="{{exp_end_dt}}" placeholder="종료일">
						</div>
					</td>  
				</tr>
				<tr>
					<th class="must">시스템 메시지 입력<br/>(시스템푸시 텍스트 입력)</th>
					<td> 
						<div class="text-input-wrap ex-max-width">
							<input class="text-input" value="{{sys_push_msg}}" type="text" placeholder="메시지 입력 /22자(44Byte) 입력제한">
						</div>
					</td>  
				</tr>
				<tr>
					<th class="must" rowspan="2" >웰컴 메시지 팝업<br/>상세내용 설정</th>
					<td> 
						<label>
							<input type="radio" name="coupon-type" id="msgType2" checked on-click="msgType:{{'2'}}">
						</label>
						<div class="date-picker ex-max-width"> 
							<input type="text" id="dtl_msg" value="{{dtl_msg}}" maxlength="150" placeholder="푸시메세지 입력 / 글자 제한수 : 150자 , 262byte 입력 제한, 줄넘김 처리시 '\n'입력">
						</div>
					</td>  
				</tr>  
				<tr>
					<td>  
						<label>
							<input type="radio" name="coupon-type" id="msgType1" on-click="msgType:{{'1'}}" >
						</label>

						<div class="img-uploader {{#if dtl_img_thmb_uri != null}}after{{else}}before{{/if}}" id="dtl_msg_seq">
							<div class="img-preview"> 
								<div class="icon-delete" on-click="deleteImg"></div>
								<img id="dtl_msg_seq_uri" src="{{dtl_img_thmb_uri}}"> 
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>이미지 파일 업로드 (600*500)
							</div>
						</div>
					</td>  
				</tr>
				<tr>
					<th class="must">웰컴 존 선택</th>
					<td> 
						<div class="date-picker">
							<input type="text" on-click="getSelZones" id="tenantNm" placeholder="웰컴 존">
						</div>
						<div class="basic-btn gold" on-click="openZonePop">zone 불러오기</div>
					</td>  
				</tr>
				<tr>
					<th class="">웰컴 쿠폰 적용</th>
					<td>  
						<div class="date-picker">
							<input type="text" id="acp" placeholder="적용 쿠폰">
						</div>
						<span on-click="delCoupon"> X </span> 
						<div class="basic-btn gold" on-click="getCoupons">쿠폰 불러오기</div>
					</td>   
				</tr>
			</table>
		</div>
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="preview">미리보기</div>
			<div class="basic-btn gold" id="btnSave" on-click="reqWcPushMsg" >저장</div>
			<div class="basic-btn gold" id="btnPostion" on-click="reqWcPosting" style="display: none;">게시</div>
			<div class="basic-btn gold" id="btnUnPostion" on-click="reqWcUnPosting" style="display: none;">미게시</div>
		</div>
	{{/wcPushMsg}}
	</div> 
	
	<div id="zoneP" class="popup-wrap">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="closeTenant">&#215;</span></div>
				<h4 class="popup-title red">웰컴 존 목록</h4>
				<div class="popup-table-top">
					<div class="search-input-wrap">
						<input class="search-input" value="{{filter.tnt_nm_ko}}" id="memb_nm_ko" type="text" placeholder="웰컴 존 검색">
						<div class="basic-btn search" on-click="getZones">검색</div>
					</div>  
				</div>
				<input type="hidden" id="all_zone_cnt">
 				<h4 class="popup-title sub" id="zone_cnt"></h4> 
				<div class="popup-contents">
					<table class="basic-list">
						<colgroup>
							<col width="10%">
							<col width="*">
							<col width="*">
							<col width="*">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order">
									<input type="checkbox" on-click="allBtTenant" id="allTenant">
								</th>
								<th class="no-order">번호</th>
								<th class="no-order">웰컴 존ID</th>
								<th class="no-order">웰컴 존 타이틀</th>
							</tr>
						</thead>
						<tbody>
						{{#tenantList:num}} 
							<tr> 
								<td><input type="checkbox" id="tenant_{{zone_id}}" name="tenant_{{zone_id}}" on-click="ckTenant:{{zone_id}},{{tnt_nm}}">
									<input type="hidden" name="tenantInfo" value="{{zone_id}},{{tnt_nm}}">
								</td> 
								<td>{{no}}</td>  
								<td>{{zone_id}}</td> 
								<td>{{tnt_nm}}</td>  
							</tr>
						{{/tenantList}}  
						</tbody>
					</table>
					{{#paging}} 
					<div class="paging-wrap">
						<ul class="paging inline-list">
							<li class="prev {{#if paging.page_start == 1}}off{{/if}}"  
								on-click="zonePageMove:{{paging.page_start - 1}}" >PREV</li>
							{{#pages}}
								<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="zonePageMove:{{this}}">{{this}}</li>
							{{/pages}}
							<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" 
								on-click="zonePageMove:{{paging.page_end + 1}}" >NEXT</li>
						</ul>
         		   </div> 
					{{/paging}}
					<div class="">
						<div class="basic-btn" on-click="closeTenant">닫기</div>
						<div class="basic-btn" on-click="saveTenant">적용</div>
					</div>
				</div>
			</div>
		</div> 
	</div>


	<div id="selZoneP" class="popup-wrap">
		<div class="popup-container">
			<!--테넌트 선택 팝업-->  
			<div id="coupon-place-setting" class="popup" > 
				<div class="popup-header"><span class="btn-close" on-click="selZoneClose">&#215;</span></div>
				<h4 class="popup-title red">웰컴 존 목록</h4>
				<div class="popup-contents">  
					<table class="basic-list">
						<colgroup>
							<col width="80">
							<col width="*"> 
							<col width="80"> 
						</colgroup>  
						<thead>
							<tr>   
								<th class="no-order">웰컴 존ID</th>
								<th class="no-order">웰컴 존 타이틀</th>
								<th class="no-order">삭제</th>
							</tr> 
						</thead> 
						<tbody>    
						{{#wcPushMsg}}
							{{#selTenantList:num}} 
							<tr id="selZone_{{zone_id}}">
								<td class="ellipsis">{{zone_id}}</td>
								<td class="ellipsis ta-l">{{tnt_nm_ko}}</td>
								<td class="ellipsis" on-click="selDeleteZone:{{zone_id}}">삭제</td>
							</tr> 
							{{/selTenantList}}
						{{/wcPushMsg}}
						</tbody>
					</table>
					<br/>
					<br/>
					<div class="btn-wrap">
						<div class="basic-btn" on-click="selZoneClose">닫기</div>
						<div class="basic-btn" on-click="selZoneSave">적용</div>
					</div>
				</div>
			</div>
			<!--//테넌트 선택 팝업-->
		</div>
	</div>


	<div id="couponP" class="popup-wrap">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="closeCoupon">&#215;</span></div>
				<h4 class="popup-title red">쿠폰 목록</h4>
				<div class="popup-table-top">
					<div class="search-input-wrap">
						<input class="search-input" value="{{filter.cp_titl}}" id="cp_titl" type="text" placeholder="웰컴 쿠폰명 검색">
						<div class="basic-btn search" on-click="getCoupons">검색</div>
					</div>  
				</div> 
				<h4 class="popup-title sub" id="cp_cnt">${count}</h4>
				<div class="popup-contents">
					<table class="basic-list">
						<colgroup>
							<col width="10%">
							<col width="15%">
							<col width="*">
							<col width="15%">
							<col width="15%">
							<col width="15%">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order">선택</th> 
								<th class="no-order">번호</th>
								<th  class="no-order">쿠폰명</th>
								<th class="no-order">발행<br/>쿠폰수</th>
								<th class="no-order">발급완료<br/>쿠폰수</th>
								<th class="no-order">잔여<br/>쿠폰수</th>
							</tr>
						</thead>
						<tbody> 
						{{#couponList:num}} 
							<tr> 
								<td><input type="radio" name="cp_ck" value="{{cp_seq}}" on-click="ckTenant:{{zone_id}},{{tnt_nm}}"></td> 
								<td>{{no}}</td>   
								<td class="ellipsis ta-l" id="{{cp_seq}}">{{cp_titl}}</td> 
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
								on-click="cpPageMove:{{paging.page_start - 1}}" >PREV</li>
							{{#pages}} 
								<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="cpPageMove:{{this}}">{{this}}</li>
							{{/pages}}
							<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" 
								on-click="cpPageMove:{{paging.page_end + 1}}" >NEXT</li>
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
	<!--웰컴 메시지 미리보기 팝업-->
	<div id="welcome-display-popup" class="popup-wrap"><!--on class 추가-->
		<div class="popup-container">
			<div class="popup push-size">
				<div class="popup-header"><span class="btn-close" on-click="close_preview">&#215;</span></div>
				<h4 class="popup-title red">웰컴 메시지 미리보기</h4>
				<div class="popup-contents">
					<div class="welcome-title-wrap"><!--이미지 등록시 img class 추가-->
						<div class="welcome-title-text"id="pv_titl_text"></div>
						<div class="welcome-title-img" id="pv_titl_img">
							<img src="" alt="웰컴타이틀이미지" id="pv_titl_img_uri">
						</div>
					</div>
					<div class="welcome-detail-wrap"><!--이미지 등록시 img class 추가-->
						<div class="welcome-detail-text" id="pv_detail_text">스타필드 하남 앱 웰컴 푸시 쿠폰 스타필드 하남 앱 웰컴 푸시 쿠폰 스타필드 하남 앱 웰컴 푸시 쿠폰 스타필드 하남 앱 웰컴 푸시 쿠폰 스타필드 하남 앱 웰컴 푸시 쿠폰 스타필드 하남 앱 웰컴 푸시 쿠폰 </div>
						<div class="welcome-detail-img" id="pv_detail_img">
							<img src="" alt="웰컴상세이미지" id="pv_detail_img_uri">
						</div>
					</div>
					<div class="popup-btn-wrap"><!--바로가기 버튼 추가시 more 클래스 추가-->
						<div class="popup-btn popup-btn-close" on-click="close_preview">닫기</div><div class="popup-btn popup-btn-more">바로가기</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--//웰컴 메시지 미리보기 팝업-->
 </script>
</div>
<script src="/js/app/appPush/ALBSW012.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>