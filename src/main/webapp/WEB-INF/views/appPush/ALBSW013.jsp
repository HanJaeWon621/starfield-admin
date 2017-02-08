<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>

<style type="text/css">
</style> 
<div id="wcPushMsg-detail" class="contents" >
<script id="tmpl-wcPushMsg-detail" type="text/ractive">
	<div id="header">
		<div class="wrap">
			<a class="img-logo" href="admin-dashboard-summary.html"></a>
			<div id="header-contents">
				<div class="select-wrap">
					<select id="branch-selector">
						<option value="하남점">하남점</option>
					</select>
				</div>
				<ul class="inline-list">
					<li>회원정보</li><li>Logout</li><li>Web</li>
				</ul>
			</div>
		</div>
	</div>
	<div id="gnb">
		<ul class="main-menu">
			<li>
				<span>메인</span>
				<ul class="sub-menu">
					<li><a href="admin-main-hero-mng.html">상단 히어로 이미지 영역 관리</a></li>
					<li><a href="admin-main-news.html">STARFIELD NOW Group 관리</a></li>
				</ul>
			</li>
			<li>
				<span>매장안내</span>
				<ul class="sub-menu">
					<li><a href="admin-operation.html">스타필드 운영정보 관리</a></li>
					<li><a href="admin-tenant-mng.html">매장 등록 관리</a></li>
					<li><a href="admin-facility-group-mng.html">편의시설 관리</a></li>
				</ul>
			</li>
			<li class="selected">
				<span>이벤트&amp;쿠폰</span>
				<ul class="sub-menu">
					<li class="selected"><a href="admin-event-mng.html">이벤트 관리</a></li>
					<li><a href="admin-coupon-mng.html">쿠폰 관리</a></li>
					<li><a href="admin-coupon-history.html">쿠폰 사용내역 조회</a></li>
				</ul>
			</li>
			<li>
				<span>스타필드 스토리</span>
				<ul class="sub-menu">
					<li><a href="admin-blog-mng.html">블로그 관리</a></li>
					<li><a href="admin-sns-mng.html">소셜미디어 관리</a></li>
					<li><a href="admin-theme-mng.html">추천테마 관리</a></li>
				</ul>
			</li>
			<li>
				<span>고객센터</span>
				<ul class="sub-menu">
					<li><a href="admin-news-mng.html">NEWS 관리</a></li>
					<li><a href="admin-faq-mng.html">FAQ 관리</a></li>
					<li><a href="admin-notice-mng.html">공지사항 관리</a></li>
					<li><a href="admin-inquiry-mng.html">1:1 이메일 문의 관리</a></li>
				</ul>
			</li>
			<li>
				<span>계정관리</span>
				<ul class="sub-menu">
					<li><a href="admin-history.html">관리자 활동이력 조회</a></li>
					<li><a href="admin-account-mng.html">관리자 계정 관리</a></li>
				</ul>
			</li>
			<li>
				<span>운영관리</span>
				<ul class="sub-menu">
					<li><a href="admin-push-mng.html">APP Push 관리</a></li>
					<li><a href="admin-holiday.html">스타필드 휴일 관리</a></li>
					<li><a href="admin-app-ver.html">APP 버전 관리</a></li>
					<li><a href="admin-mail-popup.html">메일 팝업 관리</a></li>
				</ul>
			</li>
			<li>
				<span><a href="admin-location-mng.html">개인 위치정보 관리</a></span>
			</li>
			<li>
				<span><a href="admin-cate.html">매장 카테고리 관리</a></span>
			</li>
			<li>
				<span><a href="admin-lang-mng.html">다국어 Page 관리</a></span>
			</li>
		</ul>
	</div>
	<div id="event-reg" class="contents-area">
		{{#wcPushMsg}}
		<div class="contents-wrap">
			<h1 class="menu-title">• APP Push 관리 시스템 > 푸시 메시지 관리 > 푸시메시지등록</h1>
			<table id="event-reg" class="input-list">
				<tr>
					<th class="must">타이틀</th>
					<td> 
						<div class="text-input-wrap">
							<input class="text-input" value="{{push_titl}}" type="text" placeholder="타이틀 입력">
						</div>
					</td>  
				</tr>
				<tr class="h92">
					<th>타이틀 이미지 등록</th>
					<td>
						<div class="img-uploader before" id="titl_img_seq"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete"></div>
								<img id="titl_img_seq_uri" src="">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
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
						<div class="text-input-wrap">
							<input class="text-input" value="{{sys_push_msg}}" type="text" placeholder="시스템 메시지 입력">
						</div>
					</td>  
				</tr>
				<tr>
					<th class="must" rowspan="2" >웰컴 메시지 팝업<br/>상세내용 설정</th>
					<td> 
						<label>
							<input type="radio" name="coupon-type" value="1" checked on-click="msgType:{{'2'}}">
						</label>
						<div class="date-picker">
							<input type="text" value="{{dtl_msg}}" placeholder="웰컴 메시지 팝업 상세내용 설정">
						</div>
					</td>  
				</tr> 
				<tr>
					<td>  
						<label>
							<input type="radio" name="coupon-type" value="2" on-click="msgType:{{'1'}}" >
						</label>
						<div class="img-uploader before" id="dtl_msg_seq"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="deleteImg"></div>
								<img id="dtl_msg_seq_uri" src="">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
					</td>  
				</tr>
				<tr>
					<th class="must">웰컴 존 선택</th>
					<td> 
						<div class="date-picker">
							<input type="text" id="tenantNm" placeholder="웰컴 존">
						</div>
						<div class="basic-btn gold" on-click="getZones">zone 불러오기</div>
					</td>  
				</tr>
				<tr>
					<th class="">웰컴 쿠폰 적용</th>
					<td>  
						<div class="date-picker">
							<input type="text" id="acp" placeholder="적용 쿠폰">
						</div>
						<div class="basic-btn gold" on-click="getCoupons">쿠폰 불러오기</div>
					</td>   
				</tr>
			</table>
		</div>
		<div class="btn-wrap">
			<div class="basic-btn gold">미리보기</div>
			<div class="basic-btn gold" on-click="regWcPushMsg" >저장</div>
		</div>
	{{/wcPushMsg}}
	</div>
	
	<div id="zoneP" class="popup-wrap">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="closeZone">&#215;</span></div>
				<h4 class="popup-title red">푸시 대상 목록</h4>
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
								<th>
									<input type="checkbox" >
								</th>
								<th>번호</th>
								<th>웰컴 존ID</th>
								<th>웰컴 존 타이틀</th>
							</tr>
						</thead>
						<tbody>
						{{#allTenantList:num}} 
							<tr> 
								<td><input type="checkbox" id="tenant_{{zone_id}}" on-click="ckTenant:{{zone_id}},{{tnt_nm}}"></td> 
								<td>{{no}}</td>  
								<td>{{zone_id}}</td> 
								<td>{{tnt_nm}}</td>  
							</tr>
						{{/allTenantList}}   
						</tbody>
					</table>
					{{#paging}} 
					<div class="paging-wrap">
						<ul class="paging inline-list">
							<li class="prev {{#if paging.page_start == 1}}off{{/if}}" 
								{{#if paging.page_start == 1}} on-click="zonePageMove:{{paging.page_start - 1}}" {{/if}}>PREV</li>
							{{#pages}}
								<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="zonePageMove:{{this}}">{{this}}</li>
							{{/pages}}
							<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" 
								{{#if paging.page_end == paging.total_page_cnt}} on-click="zonePageMove:{{paging.page_end + 1}}" {{/if}}>NEXT</li>
						</ul>
         		   </div> 
					{{/paging}}
					<div class="">
						<div class="basic-btn" on-click="closeZone">닫기</div>
						<div class="basic-btn" on-click="saveTenant">적용</div>
					</div>
				</div>
				   

			</div>
		</div> 
	</div>
	<div id="couponP" class="popup-wrap">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="closeCoupon">&#215;</span></div>
				<h4 class="popup-title red">쿠폰 목록</h4>
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
								<th>쿠폰명</th>
							</tr>
						</thead>
						<tbody> 
						{{#couponList:num}} 
							<tr> 
								<td><input type="radio" name="cp_ck" value="{{cp_seq}}"></td> 
								<td>{{no}}</td>   
								<td id="{{cp_seq}}">{{cp_titl}}</td> 
							</tr>
						{{/couponList}}  
						</tbody>
					</table>
					{{#paging}} 
					<div class="paging-wrap">
						<ul class="paging inline-list">
							<li class="prev {{#if paging.page_start == 1}}off{{/if}}" 
								{{#if paging.page_start == 1}} on-click="zonePageMove:{{paging.page_start - 1}}" {{/if}}>PREV</li>
							{{#pages}}
								<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="zonePageMove:{{this}}">{{this}}</li>
							{{/pages}}
							<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" 
								{{#if paging.page_end == paging.total_page_cnt}} on-click="zonePageMove:{{paging.page_end + 1}}" {{/if}}>NEXT</li>
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
 </script>
</div>
<script src="/js/app/appPush/ALBSW013.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>