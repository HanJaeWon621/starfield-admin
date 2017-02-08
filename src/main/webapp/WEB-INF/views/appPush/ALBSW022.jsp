<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>

	<div id="scenarioPush-detail" class="contents" >
	<script id="tmpl-scenarioPush-detail" type="text/ractive">
	<div id="event-reg" class="contents-area">
		<div class="contents-wrap">
			<c:choose>
    			<c:when test="${type eq '1'}">
       				<h1 class="menu-title">시나리오 기반 쿠폰 푸시 등록 시스템</h1>
   				</c:when>
			    <c:otherwise>
       				<h1 class="menu-title">아웃바운드 기반 쿠폰 푸시 등록 시스템</h1>
    			 </c:otherwise>
			</c:choose>
			
			<table id="event-reg" class="input-list">
				<tr>
					<th class="must">타이틀</th>
					<td colspan="6">
						<div class="text-input-wrap ex-mid-width ex-input-btm-margin">
							<input class="text-input"  maxlength="22" id="scn_cp_push_titl" value="{{scenario.scn_cp_push_titl}}" type="text" placeholder="메시지 입력 / 22자(44byte) 입력제한">
						</div>
						<div>
						타이틀은 팝업 화면의 상단에 표시됩니다.
						</div> 
					</td>  
				</tr>
				<tr>
					<th class="must" rowspan="4" >푸시대상</th>
					<td>회원구분</td> 
					<td colspan="5">
						<div class="select-wrap">
							<select value="{{scenario.app_tgt_mbr_div_cd}}" on-change="changeMbr">
								<c:forEach var="option" items="${tgtMbr}">				
									<option value="${option.codeCd}">${option.codeNm}</option>
								</c:forEach> 
							</select>
						</div>
					</td>
				</tr>
				<tr> 
					<td>성별</td> 
					<td colspan="5">
						<div class="select-wrap">
							<select id="app_tgt_sex" value="{{scenario.app_tgt_sex}}" on-change="resetDelMemSeq">
								<c:forEach var="option" items="${tgtSex}" varStatus="status">				
									<option value="${option.codeCd}">${option.codeNm}</option>
								</c:forEach> 
							</select>
						</div>
					</td>
				</tr>
				<tr>   
					<td>연령</td>   
					<td colspan="5"> 
						<label><input type="checkbox" id="tgtAge0" name="app_tgt_age_all" value="all" on-click="ckTgtAge:0"{{#if scenario.app_tgt_age_all == 'Y'}}checked{{/if}} >전체</label> 
						<label><input type="checkbox" id="tgtAge1" name="app_tgt_age_20" value="20" on-click="ckTgtAge:1" {{#if scenario.app_tgt_age_20 == 'Y'}}checked{{/if}}>20</label>
						<label><input type="checkbox" id="tgtAge2" name="app_tgt_age_30" value="30" on-click="ckTgtAge:2" {{#if scenario.app_tgt_age_30 == 'Y'}}checked{{/if}}>30</label>
						<label><input type="checkbox" id="tgtAge3" name="app_tgt_age_40" value="40" on-click="ckTgtAge:3" {{#if scenario.app_tgt_age_40 == 'Y'}}checked{{/if}}>40</label>
						<label><input type="checkbox" id="tgtAge4" name="app_tgt_age_50" value="50" on-click="ckTgtAge:4" {{#if scenario.app_tgt_age_50 == 'Y'}}checked{{/if}}>50</label>
					</td>  
				</tr>
				<tr>  
					<td>push 적용 대상</td>  
					<td colspan="5">
						<label>  
							<span class="label-text" id="push_memb_cnt"></span>
							<div class="basic-btn gold" on-click="getPushMembs">푸시대상 목록보기</div>
						</label>
					</td>
				</tr>
				<tr>
					<th class="must">푸시 기간 설정<br/>(언제)</th>
					<td colspan="6"> 
						<div class="date-picker"> 
							<input type="text" name="datepicker" placeholder="시작일" id="push_strt_dt" value="{{scenario.push_strt_dt}}">
						</div>
						<span>~</span> 
						<div class="date-picker">  
							<input type="text" name="datepicker" placeholder="종료일" id="push_end_dt" value="{{scenario.push_end_dt}}">
						</div> 
					</td>  
				</tr>
				<tr>
					<th>푸시 쿠폰 설정</th>
					<td colspan="6">
						<div class="text-input-wrap">
							<input class="text-input" id="acp" type="text" placeholder="푸시 쿠폰 설정">
							
						</div>
						<span on-click="delAppCoupon"> X </span>  
						<div class="basic-btn gold" on-click="getCoupons">적용 쿠폰</div>
						<input class="text-input" id="cp_act_strt_dt" type="hidden" placeholder="푸시 쿠폰 설정" value="{{scenario.cp_act_strt_dt}}">
						<input class="text-input" id="cp_act_end_dt" type="hidden" placeholder="푸시 쿠폰 설정" value="{{scenario.cp_act_end_dt}}"> 
					</td>   
				</tr>

				<tr> 
					<th class="must">이미지등록</th> 
						<td class="coupon-img-reg" colspan="6">
						<span class="comment front gray">BI 이미지</span> 
						<div class="img-uploader {{#if scenario.bi_img_seq_uri != null}}after{{else}}before{{/if}}" id="bi_img_seq">
							<div class="img-preview">
								<div class="icon-delete" on-click="deleteImg"></div>
								<img id="bi_img_seq_uri" src="{{scenario.bi_img_seq_uri}}"> 
							</div> 
							<div class="file-uploader-wrap"> 
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div> 
						</div> 
					</td>  
					
				</tr>
				<c:if test="${type eq '1'}">
				<tr>
					<th class="must" rowspan="3" >푸시 상황 설정<br/>(어떤상황에)</th>
					<td colspan="6">
						<label>
							<input type="radio" name="coupon-type" name="{{scenario.push_time_div_cd}}" value="1" on-change="chPushTimeDiv:1">
							앱 실행 시점(앱이 실행되는 시점에 노출되는 푸시)
						</label>
					</td>
				</tr>
				<tr> 
					<td colspan="6">
						<br>
						<div style="width:300px;height:100px;float:left;position:relative;">
						<label>
							<input type="radio" name="coupon-type" name="{{scenario.push_time_div_cd}}" value="2" on-change="chPushTimeDiv:2">
							특정 존(장소) 방문 및 체류 시간
						</label>
						</div>
						<div style="float:left;pposition:relative;height:100px;margin-bottom:15px">  
						<span>① 존 선택</span>
						<div class="date-picker" on-click="openSeltenant" style="margin-left:50px;">
							<input type="text" id="openSeltenantBtn" placeholder="테넌트"   >
						</div>  
						<div class="basic-btn gold" id="opentenantBtn" on-click="opentenant">zone 불러오기</div>
						<br/><br/>
						<span>② 체류시간 설정&nbsp;&nbsp;</span>
						<div class="select-wrap" style="margin-left:5px;margin-right:0px;"> 
							<select id="operating-status-selector" on-change="selChange:'stay_time'" id="stay_time_cd" value="{{scenario.stay_time_cd}}">
								<optgroup label="체류시간 설정">
									<c:forEach var="option" items="${visitTime}">				
										<option value="${option.codeCd}">${option.codeNm}</option>
									</c:forEach>  
								</optgroup>
							</select> 
						</div>
						<div class="date-picker"> 
							<input type="text" name="" placeholder="숫자만 입력" numberOnly id="stay_time" value="{{scenario.stay_time}}">
						</div>
						</div>
						<div style="width:100%;height:50px;float:left;position:relative;padding-top:10px;margin-bottom:15px;border-top:1px solid #eee">
						<label>
							<input type="radio" name="coupon-type" name="{{scenario.push_time_div_cd}}" value="6" on-change="chPushTimeDiv:6">
							임시 관심매장 설정을 위한 체류시간 설정
							<br/>
							<div style="color:red;padding-left:5px;">임시관심매장 설정을 위한 체류시간 설정시에는 전체 테넌트가 자동선택됩니다.<br/>해당 설정에는 쿠폰 설정이 불가합니다.</div>
						</label>
						</div>
					</td> 
				</tr>
				<tr>
					<td colspan="6">
						<br/>
						<label style="margin-left:0px;">
							<input type="radio" name="coupon-type" name="{{scenario.push_time_div_cd}}" value="3" on-change="chPushTimeDiv:3">
							고객 동선별 푸시
						</label> 
						<br/>   
						<br/> 
						① 존 선택
						<div class="basic-btn gold" id="movingTnt1" on-click="openTenant2:1">선택없음</div>
							<input type="hidden" name="movingTntZoneId" id="movingZoneId1" value="">
							<input type="hidden" name="movingTntBusiCd" id="movingBusiCd1" value="">
						<span on-click="delTenant:1"> X </span> 
						<div class="basic-btn gold" id="movingTnt2" on-click="openTenant2:2">선택없음</div>
							<input type="hidden" name="movingTntZoneId" id="movingZoneId2" value="">
							<input type="hidden" name="movingTntBusiCd" id="movingBusiCd2" value="">
						<span on-click="delTenant:2"> X </span>
						<div class="basic-btn gold" id="movingTnt3" on-click="openTenant2:3">선택없음</div>
							<input type="hidden" name="movingTntZoneId" id="movingZoneId3" value="">
							<input type="hidden" name="movingTntBusiCd" id="movingBusiCd3" value="">
						<span on-click="delTenant:3"> X </span>
						<div class="basic-btn gold" id="movingTnt4" on-click="openTenant2:4">선택없음</div>
							<input type="hidden" name="movingTntZoneId" id="movingZoneId4" value="">
							<input type="hidden" name="movingTntBusiCd" id="movingBusiCd4" value="">
						<span on-click="delTenant:4"> X </span> 
						<div class="basic-btn gold" id="movingTnt5" on-click="openTenant2:5">선택없음</div>
							<input type="hidden" name="movingTntZoneId" id="movingZoneId5" value="">
							<input type="hidden" name="movingTntBusiCd" id="movingBusiCd5" value="">
						<span on-click="delTenant:5"> X </span>  
						<br/>
						<br/> 
						② 각 테넌트별 체류시간 설정
						<div class="select-wrap">
							<select id="operating-status-selector" on-change="selChange:'stay_time2'" id="stay_time_cd2" value="{{scenario.stay_time_cd2}}">
								<optgroup label="각테넌트별체류시간설정">
									<c:forEach var="option" items="${visitTime}">				
										<option value="${option.codeCd}">${option.codeNm}</option>
									</c:forEach> 
								</optgroup>
							<select id="operating-status-selector" id="stay_time_cd2" value="{{scenario.stay_time_cd2}}">
								<c:forEach var="option" items="${visitTime}">				
									<option value="${option.codeCd}">${option.codeNm}</option>
								</c:forEach> 
							</select> 
						</div>
						<div class="date-picker">  
							<input type="text" placeholder="숫자만 입력" numberOnly id="stay_time2" value="{{scenario.stay_time2}}">
						</div>
						<div><br></div>
					</td>
				</tr>
				</c:if>
				<c:if test="${type eq '2'}">
				<tr>
					<th class="must" rowspan="3" >푸시 상황 설정<br/>(어떤상황에)</th>
					<td colspan="6">
						<label>
							<input type="radio" name="coupon-type" name="{{scenario.push_time_div_cd}}" value="4" on-change="chPushTimeDiv:4">
							특정기간 스타필드 미방문 고객
						</label>
						<div class="date-picker"> 
							<input type="text" name="" placeholder="숫자만 입력" id="day_cnt1" value="{{scenario.day_cnt}}">
						</div> 일 경과
					</td> 
				</tr>
				<tr>  
					<td colspan="6">
						<label>
							<input type="radio" name="coupon-type" name="{{scenario.push_time_div_cd}}" value="5" on-change="chPushTimeDiv:5">
							특정기간 앱 미사용 고객
						</label>
						<div class="date-picker"> 
							<input type="text" name="" placeholder="숫자만 입력" id="day_cnt2" value="{{scenario.day_cnt2}}" disabled>
						</div> 일 경과
					</td>
				</tr>
				<tr>  
					<td colspan="6">
						<label>
							<input type="radio" name="coupon-type" name="{{scenario.push_time_div_cd}}" value="7" on-change="chPushTimeDiv:7">
							웰컴 메시지 수신 대상 고객 (감사메세지)
							<br/> 
							<span class="red">* 해당 푸시는 익일 오전 11시에 일괄 발송됩니다.</span>
						</label>    
						<div class="date-picker"> 
							<input type="text" name="" placeholder="팝업(상세) 페이지 URL 입력" id="link_url" value="{{scenario.link_url}}" disabled>
						</div> 
					</td>
				</tr>
				
				</c:if>
 

				<tr>
					<th class="must">푸시메세지설정</th>
					<td colspan="6">
						<div class="text-input-wrap ex-max-width">
							<input class="text-input" type="text" id="scn_push_msg" maxlength="40" placeholder="메시지 입력 / 40자(74byte) 입력제한, 줄넘김 처리시 '\n' 입력 " value="{{scenario.scn_push_msg}}">
						</div>
					</td>  
				</tr>
			</table>
		</div>
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="preview">미리보기</div>
			<div class="basic-btn gold" id="btnSave" on-click="saveScenario">저장</div>
			<div class="basic-btn gold" id="btnPostion" on-click="regScenarioPosting" style="display: none;">게시</div>
			<div class="basic-btn gold" id="btnUnPostion" on-click="regScenarioUnPosting" style="display: none;">미게시</div>
		</div>
	</div>

	<div id="entry-list" name="mem-reg-popup" class="popup-wrap">
		<div class="popup-container">
			<div class="popup"> 
				<div class="popup-header"><span class="btn-close" on-click="closeMem">&#215;</span></div>
				<h4 class="popup-title red">푸시 대상 목록</h4>
				<div class="popup-table-top">
				<div class="select-wrap" id="selVip" style="display: none;">
					<select id="abst_no" on-change="getPushMembs">
						{{#abstMstList}}
						<option value="{{abst_no}}">{{abst_titl}}</option>
						{{/abstMstList}} 
					</select> 
				</div>
					<div class="search-input-wrap">
						<input class="search-input" value="{{filter.memb_nm_ko}}" id="memb_nm_ko" type="text" placeholder="회원명 검색">
						<div class="basic-btn search" on-click="shMemb">검색</div>
					</div>
				</div>
				<h4 class="popup-title sub" id="push_tgt_cnt">푸시 대상 건수</h4> 
				<div class="popup-contents"> 
					<table class="basic-list">
						<colgroup>
							<col width="10">  
							<col width="10"> 
							<col width="10"> 
							<col width="30">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order">
									<input type="checkbox" name="all_ck" id="all_ck" on-click="ckAllMemb">									
									선택
								</th> 
								<th class="no-order">번호</th>
								<th class="no-order">성별</th>
								<th class="no-order">회원명</th>
							</tr>
						</thead> 
						<tbody> 
						{{#pushMembList:num}} 
							<tr> 
								<td class="ellipsis"><input type="checkbox" name="ck_mem" id="{{cust_id}}" on-click="ckMemb:{{cust_id}}"></td> 
								<td class="ellipsis">{{no}}</td>  
								<td class="ellipsis">{{mbr_sex}}</td> 
								<td class="ellipsis">{{mbr_nm}}</td>   
							</tr>
						{{/pushMembList}}   
						</tbody>
					</table>
					{{#paging}}
					<div class="paging-wrap">
						<ul class="paging inline-list">
						<li class="prev {{#if paging.page_start == 1}}off{{/if}}" on-click="membPageMove:{{paging.page_start - 1}}">PREV</li>
						{{#pages}}
						<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="membPageMove:{{this}}">{{this}}</li>
						{{/pages}}
						<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" on-click="membPageMove:{{paging.page_end + 1}}">NEXT</li>
						</ul> 
         		   </div>  
					{{/paging}}  
				</div>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="closeMem">닫기</div>
					<div class="basic-btn gold" on-click="saveMem">적용</div>
				</div>
			</div> 
		</div>
	</div>
	
	<div id="couponP" class="popup-wrap">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="closeCoupon">&#215;</span></div>
				<h4 class="popup-title red">적용 쿠폰 설정</h4>
				<div class="popup-table-top">
					<div class="search-input-wrap"> 
						<input class="search-input" value="{{filter.cp_titl}}" type="text" placeholder="쿠폰명 검색">
						<div class="basic-btn search" on-click="shCp">검색</div>
					</div>  
					
				</div>
				
				<h4 class="popup-title sub" id="cp_cnt">${count}</h4>
				<div class="popup-contents">
					
					<table class="basic-list">
						<colgroup> 
							<col width="5%">
							<col width="15%">
							<col width="*">
							<col width="15%">
							<col width="15%">
							<col width="15%">
							<col width="15%">
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
								<td  class="ellipsis ta-l" id="{{cp_seq}}">{{cp_titl}}</td> 
								<td  class="ellipsis ta-l">{{tnt_nm}}</td>
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
						<div class="basic-btn" on-click="selCoupon" >적용</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!--신규 쿠폰등록-->
	<div id="coupon-reg-popup" name="tnt-reg-popup" class="popup-wrap"><!--place-on 추가시 테넌트 불러오기 팝업 열림-->
		<div class="popup-container">
			<!--테넌트 선택 팝업-->
			<div id="coupon-place-setting" class="popup"> 
				<div class="popup-header"><span class="btn-close" on-click="closeTenant">&#215;</span></div>
				<h4 class="popup-title red">zone 목록</h4>
				<div class="popup-contents">
					<div class="popup-table-top">
						<div class="select-wrap">
							<select id="search-cate-selector" id="cate_seq"  value="{{filter.cate_seq}}">
								<option value="">전체</option>
								{{#categoryList}} 
									<option value="{{cate_seq}}">{{cate_nm_ko}}</option>
								{{/categoryList}}
							</select> 
						</div>
						<div class="search-input-wrap">
							<input class="search-input" value="{{filter.tnt_nm_ko}}" id="tnt_nm_ko" type="text" placeholder="사용처 검색">
							<div class="basic-btn search" on-click="opentenant">검색</div>
						</div>
					</div>
					<table class="basic-list">
						<colgroup>
							<col width="38">
							<col width="*">
							<col width="*">
							<col width="*">
							<col width="*">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order"><input name="all_tenant_seq" id="all_tenant_seq" type="checkbox" on-click="allTenant" >선택</th>
								<th class="no-order">카테고리</th>
								<th class="no-order">존ID</th>
								<th class="no-order">존타이틀</th>
								<th class="no-order">호수</th>
							</tr>
						</thead>
						<tbody>
							{{#tenantList:num}} 
							<tr>
								<td><input id="tenant_{{tnt_seq}}" name="tenant_seq" type="checkbox"   
										on-click="tenantChk:{{tnt_seq}},{{tnt_nm_ko}},{{busi_tnt_cd}},{{zone_id}},{{cate_nm_ko}},{{room_num}}">
									<input type="hidden" name="alltnt" value="{{tnt_seq}},{{tnt_nm_ko}},{{busi_tnt_cd}},{{zone_id}},{{cate_nm_ko}},{{room_num}}">
								</td>
								<td  class="ellipsis ta-l">{{cate_nm_ko}}</td>
								<td class="ellipsis">{{zone_id}}</td>
								<td  class="ellipsis ta-l">{{tnt_nm_ko}}</td>
								<td class="ellipsis">{{room_num}}</td>
							</tr>
							{{/tenantList}}
						</tbody>
					</table>
					{{#paging}} 
					<div class="paging-wrap">
						<ul class="paging inline-list">
							<li class="prev {{#if paging.page_start == 1}}off{{/if}}" 
								on-click="moveTntPage:{{paging.page_start - 1}}">PREV</li>
							{{#pages}}
								<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="moveTntPage:{{this}}">{{this}}</li>
							{{/pages}}
							<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" 
								on-click="moveTntPage:{{paging.page_end + 1}}">NEXT</li>
						</ul>
         		   </div> 
					{{/paging}} 
					<div class="btn-wrap">
						<div class="basic-btn" on-click="closeTenant">닫기</div>
						<div class="basic-btn" on-click="saveTenant">적용</div>
					</div>
				</div>
			</div>
			<!--//테넌트 선택 팝업-->
		</div>
	</div>
	<!--//신규 쿠폰등록-->

	<!--신규 쿠폰등록2-->
	<div id="coupon-reg-popup" name="tnt-reg-popup2" class="popup-wrap"><!--place-on 추가시 테넌트 불러오기 팝업 열림-->
		<div class="popup-container">
			<!--테넌트 선택 팝업-->  
			<div id="coupon-place-setting" class="popup" > 
				<div class="popup-header"><span class="btn-close" on-click="selTenantClose">&#215;</span></div>
				<h4 class="popup-title red">쿠폰 사용처 설정</h4>
				<div class="popup-contents">  
					<table class="basic-list">
						<colgroup>
							<col width="100"> 
							<col width="40">
							<col width="20"> 
						</colgroup> 
						<thead>
							<tr>  
								<th class="no-order">카테고리</th>
								<th class="no-order">테넌트명</th>
								<th class="no-order">삭제</th>
							</tr> 
						</thead> 
						<tbody>  
						{{#scenario}}
							{{#selTenantList:num}} 
							<tr id="selTenant_{{tnt_seq}}">
								<td class="ellipsis">{{cate_nm_ko}}</td>
								<td class="ellipsis">{{tnt_nm_ko}}</td>
								<td class="ellipsis" on-click="deletetenant:{{tnt_seq}}">삭제</td>
							</tr>
							{{/selTenantList}}
						{{/scenario}}
						</tbody>
					</table>
					<br/>
					<br/>
					<div class="btn-wrap">
						<div class="basic-btn" on-click="selTenantClose">닫기</div>
						<div class="basic-btn" on-click="selTenantSave">적용</div>
					</div>
				</div>
			</div>
			<!--//테넌트 선택 팝업-->
		</div>
	</div>
	<!--//신규 쿠폰등록2-->
 

	<!--신규 쿠폰등록-->
	<div id="coupon-reg-popup" name="tnt-reg-popup3" class="popup-wrap"><!--place-on 추가시 테넌트 불러오기 팝업 열림-->
		<div class="popup-container">
			<!--테넌트 선택 팝업-->
			<div id="coupon-place-setting" class="popup"> 
				<div class="popup-header"><span class="btn-close" on-click="movingTntClose">&#215;</span></div>
				<h4 class="popup-title red">ZONE 목록</h4>
				<div class="popup-contents">
					<div class="popup-table-top">
						<div class="select-wrap">
							<select id="search-cate-selector"  id="cate_seq2" value="{{filter.cate_seq}}">
								<option value="">전체</option>
								{{#categoryList}} 
									<option value="{{cate_seq}}">{{cate_nm_ko}}</option>
								{{/categoryList}}
							</select>   
						</div>
						<div class="search-input-wrap">
							<input class="search-input" value="{{filter.tnt_nm_ko}}" id="tnt_nm_ko2" type="text" placeholder="테넌트 검색">
							<div class="basic-btn search" on-click="openTenant2">검색</div>
						</div>
					</div>
					<table class="basic-list"> 
						<colgroup>
							<col width="38">
							<col width="*">
							<col width="*">
							<col width="*">
							<col width="*">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order">선택</th>
								<th class="no-order">카테고리</th>
								<th class="no-order">존ID</th>
								<th class="no-order">존타이틀</th>
								<th class="no-order">호수</th>
							</tr>
						</thead>
						<tbody>
							{{#tenantList2:num}} 
							<tr>
								<td><input id="tenant2_{{zone_id}}" name="tenant_seq" type="radio"   
										on-click="movingTntChk:{{tnt_seq}},{{tnt_nm_ko}},{{busi_tnt_cd}},{{zone_id}}"></td>
								<td class="ellipsis  ta-l">{{cate_nm_ko}}</td>
								<td class="ellipsis">{{zone_id}}</td>
								<td class="ellipsis ta-l">{{tnt_nm_ko}}</td>
								<td class="ellipsis">{{room_num}}</td>
							</tr>
							{{/tenantList2}}
						</tbody> 
					</table>
					{{#paging}}  
					<div class="paging-wrap">
						<ul class="paging inline-list">
							<li class="prev {{#if paging.page_start == 1}}off{{/if}}" 
								on-click="movetnt2Page:{{paging.page_start - 1}}">PREV</li>
							{{#pages}}
								<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="movetnt2Page:{{this}}">{{this}}</li>
							{{/pages}}
							<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" 
								on-click="movetnt2Page:{{paging.page_end + 1}}">NEXT</li>
						</ul>
         		   </div>  
					{{/paging}} 
					<div class="btn-wrap">
						<div class="basic-btn" on-click="movingTntClose">닫기</div>
						<div class="basic-btn" on-click="movingTntSave">적용</div>
					</div>
				</div>
			</div>
			<!--//테넌트 선택 팝업-->
		</div>
	</div>
	<!--//신규 쿠폰등록-->
<!--쿠폰 푸시 미리보기 팝업-->
	<div id="coupon-push-display-popup" class="popup-wrap"><!--on class 추가-->
		<div class="popup-container">
			<div class="popup push-size">
				<div class="popup-header"><span class="btn-close" on-click="btn-close">&#215;</span></div>
				<h4 class="popup-title red">푸시 미리보기</h4>
				<div class="popup-contents">
					<div class="coupon-push-bi-img-wrap">
						<img src="" alt="푸시이미지" id="pv_img">
					</div>
					<div class="coupon-push-text-wrap">
						<div class="coupon-push-title" id="pv_titl_text">스타벅스</div>
						<div class="coupon-push-detail" id="pv_titl_detail">아이스 아메리카노 무료 쿠폰</div>
						<div class="coupon-push-period" id="pv_period">2016.5.28 - 2016.7.20</div>
					</div>
					<div class="popup-btn-wrap more">
						<div class="popup-btn popup-btn-close" on-click="btn-close">닫기</div><div class="popup-btn popup-btn-more" id="digo">바로가기</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--//쿠폰 푸시 미리보기 팝업-->
	</script>
	</div styl>
	<script src="/js/app/appPush/ALBSW022.js" type="text/javascript" ></script>
<%@ include file="../common/footer.jsp" %>