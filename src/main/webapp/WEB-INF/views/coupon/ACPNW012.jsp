<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>

<style type="text/css">
</style> 
	<div id="coupon-reg" class="contents" >
	<script id="tmpl-coupon-reg" type="text/ractive">
	<div id="coupon-reg" class="contents-area">
		<div class="contents-wrap">
			<h1 class="menu-title">이벤트&amp;쿠폰 - 쿠폰 등록/수정</h1>
			{{#coupon}}
			<table id="coupon-reg-info" class="input-list">
				<colgroup>
					<col width="">
					<col width="*">
					<col width="">
					<col width="*">
				</colgroup>
				<tr>
					<th class="no-order">등록자</th>
					<td class="no-order">
						<div class="text-input-wrap">
							<input class="text-input" type="text" value="등록자" value="{{reg_usr}}" disabled>
						</div>
					</td>
					<th class="no-order" class="no-order">등록일</th>
					<td class="no-order">
						<div class="text-input-wrap">
							<input class="text-input" type="text"  value="작업시간" value="{{reg_dttm}}" disabled>
						</div>
					</td>
				</tr>
			</table>
			<table id="coupon-reg-input" class="input-list">
				<tr>
					<th>쿠폰ID<br>(쿠폰발급코드)</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{#if bcn_cd+yymm+mst_seq != 0}} {{bcn_cd+yymm+mst_seq}} {{/if}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th class="must">종류</th>
					<td>
						<label><input type="radio" id="cp_kind_cd2" name="{{cp_kind_cd}}" value="2">할인쿠폰</label>
					</td>
				</tr>
				<tr>
					<th class="must">발급 타입 선택</th>
					<td>
						<label><input type="radio" id="cp_iss_type_cd1" name="{{cp_iss_type_cd}}" value="1">일반쿠폰</label>
						<label><input type="radio" id="cp_iss_type_cd2" name="{{cp_iss_type_cd}}" value="2">웰컴 메시지용</label>
						<label><input type="radio" id="cp_iss_type_cd3" name="{{cp_iss_type_cd}}" value="3">시나리오 푸시용</label>
					</td>
				</tr>
				<tr>
					<th class="must">타이틀</th>
					<td>
						<div class="text-input-wrap long">
							<input class="text-input" type="text" value="{{cp_titl}}" id="cp_titl"  placeholder="쿠폰타이틀 직접입력">
						</div>
					</td>
				</tr>
				<tr>
					<th class="must">사용처 설정</th>
					<td> 
						<div class="date-picker">
							<input type="text" id="openSeltenantBtn" placeholder="테넌트" disabled>
						</div>
						<div class="basic-btn gold" id="opentenantBtn" on-click="opentenant">테넌트 불러오기</div>
					</td>  
				</tr>
				<tr>
					<th class="must">발급일<br/>(쿠폰 노출일)</th>
					<td>
						<span class="comment front gray">시작일</span>
						<div class="date-picker">
						<input id="cp_iss_strt_dt" name="datepicker" type="text" value="{{cp_iss_strt_dt}}" >
						</div>
						<span class="dash">~</span>
						<span class="comment front gray">종료일</span>
						<div class="date-picker">
							<input id="cp_iss_end_dt" name="datepicker" type="text" value="{{cp_iss_end_dt}}" >
						</div>
					</td>
				</tr>
				<tr class="must"> 
					<th class="must">유효기간 설정</th>
					<td>
						<span class="comment front gray">시작일</span>
						<div class="date-picker">
						<input id="cp_act_strt_dt" name="datepicker" type="text" value="{{cp_act_strt_dt}}">
						</div>
						<span class="dash">~</span>
						<span class="comment front gray">종료일</span>
						<div class="date-picker">
							<input id="cp_act_end_dt" name="datepicker" type="text" value="{{cp_act_end_dt}}">
						</div>
					</td>
				</tr>
				<tr>
					<th class="must" rowspan="2">할인방식 설정</th>
					<td class="coupon-setting">
						<label>
							<input type="radio" id="cp_sale_div_cd1" name="{{cp_sale_div_cd}}" value="2" on-click="changeCpSumSaleMethDiv:true">
							<span class="label-text">소계 할인형</span>
							<div class="text-input-wrap">
								<input class="text-input" id="cp_sum_sale_rt" type="text" value="{{cp_sum_sale_rt}}" numberOnly="true" placeholder="숫자만 입력">
							</div>
							<span class="comment">%</span> 
							<div class="text-input-wrap">
								<input class="text-input" id="cp_max_sale_amt" type="text" value="{{cp_max_sale_amt}}" numberOnly="true" placeholder="숫자만 입력">
							</div>
							<span class="comment">₩</span>
						</label>
					</td>
				</tr>
				<tr>
					<td class="coupon-setting">
						<label>
							<input type="radio" id="cp_sale_div_cd2" name="{{cp_sale_div_cd}}" value="1" on-click="changeCpSumSaleMethDiv:false">
							<span class="label-text">금액 차감형</span>
							<div class="text-input-wrap">
								<input class="text-input" id="cp_ded_amt" type="text" value="{{cp_ded_amt}}" numberOnly="true" disabled placeholder="숫자만 입력">
							</div> 
							<span class="comment">₩</span>
						</label>
					</td>
				</tr>
				<tr>
					<th class="must" >발급수량 설정</th>
					<td class="coupon-setting">
						<label>
							<span class="label-text">일반노출<br/>(쿠폰목록)</span>
							<div class="text-input-wrap">
								<input class="text-input" id="cp_iss_cnt" type="text" value="{{cp_iss_cnt}}" numberOnly="true" placeholder="숫자만 입력">
							</div>
							<span class="comment">장</span>
						</label>
					</td>
				</tr>
				<tr>
					<th>사용조건 설정</th>
					<td class="coupon-setting">
						<label> 
							<span class="label-text">특정금액이상사용</span>
							<div class="text-input-wrap long">
								<input class="text-input" id="cp_use_cond_amt" type="text" value="{{cp_use_cond_amt}}" numberOnly="true" placeholder="숫자만 입력">
							</div>
							<span class="comment">₩</span>
						</label>
					</td>
				</tr>
				<tr>
					<th rowspan="2">이미지등록</th> 
					<td class="coupon-img-reg">
						<span class="comment front gray">BI 이미지</span> 
						<div class="img-uploader {{#if img_uri != null}}after{{else}}before{{/if}}" id="img_seq">
							<div class="img-preview">  
								<div class="icon-delete" on-click="deleteImg"></div>
								<img id="img_seq_uri" src="{{img_uri}}"> 
							</div> 
							<div class="file-uploader-wrap"> 
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div> 
						</div>
					</td> 
				</tr>
				<tr>
					<td class="coupon-img-reg">
						<span class="comment front gray">쿠폰 상세 이미지</span>
						<div class="img-uploader {{#if dtl_img_uri != null}}after{{else}}before{{/if}}" id="dtl_img_seq">
							<div class="img-preview"> 
								<div class="icon-delete" on-click="deleteImg"></div>
								<img id="dtl_img_seq_uri" src="{{dtl_img_uri}}"> 
							</div> 
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div> 
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
					</td>
				</tr>
<!--
				<tr>
					<th>기본이미지 변경하기</th>
					<td>  
						<label><input class="" type="checkbox" name="{{dft_img_yn}}" value="Y">현재 등록된 이미지를 기본 이미지로 변경됩니다.</label>
					</td>
				</tr>
-->
				<tr>
					<th>쿠폰 상세(내용)</th>
					<td>
						<div class="text-input-wrap long">
							<input class="text-input" type="text" value="{{cp_dtl_cont}}" placeholder="쿠폰 상세내용 직접입력">
						</div>
					</td>
				</tr>
				<tr>
					<th>유의사항</th>
					<td>
						<div class="text-input-wrap long">
							<input class="text-input" type="text" value="{{cp_att_part_cont}}" placeholder="유의사항 직접입력">
						</div>
					</td>
				</tr>
				<tr class="h92">
					<th>목록 노출 이미지(WEB)</th>
					<td>
						<label><input type="radio" id="webBg2" name="webBg" value="2" on-click="setWebBg:{{'color'}}" 
								{{#if web_bg_clr != null}}checked{{/if}}>색상</label>
						<div class="color-picker"> 
							<span id="bgColor1" on-click="setWebBgColor:{{'1'}}"  class="bg-red {{#if web_bg_clr == '1'}}selected{{/if}}"></span>
							<span id="bgColor2" on-click="setWebBgColor:{{'2'}}"  class="bg-gold {{#if web_bg_clr == '2'}}selected{{/if}}"></span>
							<span id="bgColor3" on-click="setWebBgColor:{{'3'}}"  class="bg-darkgray {{#if web_bg_clr == '3'}}selected{{/if}}"></span>
							<span id="bgColor4" on-click="setWebBgColor:{{'4'}}"  class="bg-lightgray {{#if web_bg_clr == '4'}}selected{{/if}}"></span>
						</div>  
						<label><input type="radio" id="webBg1" name="webBg" value="1" on-click="setWebBg:{{'img'}}"
									 {{#if web_bg_img_seq != null}}checked{{/if}}>이미지</label>
						<div class="img-uploader {{#if web_bg_img_uri != null}}after{{else}}before{{/if}}" id="web_bg_img_seq">
							<div class="img-preview">
								<div class="icon-delete" on-click="deleteImg"></div> 
								<img id="web_bg_img_seq_uri" src="{{web_bg_img_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
					</td>
				</tr>
				<tr class="h92">
					<th>목록 노출 이미지(MOBILE)</th>
					<td> 
						<label><input type="radio" id="appBg2" name="appBg" value="2" on-click="setAppBg:{{'color'}}" 
								{{#if app_bg_clr != null}}checked{{/if}}>색상</label>
						<div class="color-picker">
							<span id="bgAppColor1" on-click="setAppBgColor:{{'1'}}" class="bg-red {{#if app_bg_clr == '1'}}selected{{/if}}"></span>
							<span id="bgAppColor2" on-click="setAppBgColor:{{'2'}}" class="bg-gold {{#if app_bg_clr == '2'}}selected{{/if}}"></span>
							<span id="bgAppColor3" on-click="setAppBgColor:{{'3'}}" class="bg-darkgray {{#if app_bg_clr == '3'}}selected{{/if}}"></span>
							<span id="bgAppColor4" on-click="setAppBgColor:{{'4'}}" class="bg-lightgray {{#if app_bg_clr == '4'}}selected{{/if}}"></span>
						</div>
						  
						<label><input type="radio"id="appBg1" name="appBg" value="1" on-click="setAppBg:{{'img'}}" 
								{{#if app_bg_img_seq != null}}checked{{/if}}>이미지</label>
						<div class="img-uploader {{#if app_bg_img_uri != null}}after{{else}}before{{/if}}" id="app_bg_img_seq"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="deleteImg"></div> 
								<img id="app_bg_img_seq_uri" src="{{app_bg_img_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
						
					</td>
				</tr>
			</table>
			{{/coupon}}
			<div class="btn-wrap"> 
				<div class="basic-btn">미리보기</div>
				<div class="basic-btn" id="btnSave" on-click="regCoupon" >저장</div>
				<div class="basic-btn" id="btnPostion" on-click="regCpPosting" style="display: none;">게시</div>
				<div class="basic-btn" id="btnUnPostion" on-click="regCpUnPosting" style="display: none;">미게시</div>
			</div>
		</div>
	</div>
	<!--신규 쿠폰등록-->
	<div id="coupon-reg-popup" name="tnt-reg-popup" class="popup-wrap"><!--place-on 추가시 테넌트 불러오기 팝업 열림-->
		<div class="popup-container">
			<!--테넌트 선택 팝업-->
			<div id="coupon-place-setting" class="popup"> 
				<div class="popup-header"><span class="btn-close" on-click="tennantClose">&#215;</span></div>
				<h4 class="popup-title red">쿠폰 사용처 설정</h4>
				<div class="popup-contents">
					<div class="popup-table-top">
						<div class="select-wrap">
							<select id="search-cate-selector">
								<option>카테고리</option>
							</select>
						</div>
						<div class="search-input-wrap">
							<input class="search-input" type="text" placeholder="테넌트 검색">
						</div>
					</div>
					<table class="basic-list">
						<colgroup>
							<col width="38">
							<col width="*">
							<col width="*">
							<col width="72">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order">선택</th>
								<th class="no-order">카테고리</th>
								<th class="no-order">테넌트명</th>
								<th class="no-order">호수</th>
							</tr>
						</thead>
						<tbody>
							{{#tenantList:num}} 
							<tr>
								<td><input id="tennant_{{tnt_seq}}" name="tennant_seq" type="radio"   
										on-click="tennantChk:{{tnt_seq}},{{cate_nm_ko}},{{tnt_nm_ko}},{{room_num}}
										,{{busi_tnt_cd}},{{img_logo_uri}},{{img_thmb_uri}}"></td>
								<td class="ellipsis">{{cate_nm_ko}}</td>
								<td class="ellipsis">{{tnt_nm_ko}}</td>
								<td class="ellipsis">{{room_num}}</td>
							</tr>
							{{/tenantList}}
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
					<div class="btn-wrap">
						<div class="basic-btn" on-click="tennantClose">닫기</div>
						<div class="basic-btn" on-click="tennantSave">적용</div>
					</div>
				</div>
			</div>
			<!--//테넌트 선택 팝업-->
		</div>
	</div>
	<!--//신규 쿠폰등록-->

	</script> 
	</div > 
	<script src="/js/app/coupon/ACPNW012.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>