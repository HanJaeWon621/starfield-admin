<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>

<style type="text/css">
.date-picker2 {
	display: inline-block;
	width: 80px;
	height: 30px;
	border: 1px solid #7b6d65;
	background-color: #ffffff;
	position: relative;
	vertical-align: middle; 
}
.date-picker2 input {
	position: absolute;
	top: 0;
	left: 0;
	border: none;
	outline: none;
	height: 100%;
	width: 100%;
	padding-left: 5px;
	z-index: 4;
}
.input-list .text-input-wrap-title.long{
	height: 40px;
	width: 200px;
}
.popup2 .popup-contents {
	height: 669px;
	width: 400px;
	background-color: #ededed;
	position: relative;
	overflow-y: scroll;
}

#cp_att_part_cont  {
	width: 20; 
    height: 90; 
    min-height: 60px;
    margin: 0px 0;
    padding: 10px;
    border: 1px solid;
    font-size: inherit;
}
 
#cp_dtl_cont {
	width: 20; 
    height: 90; 
    min-height: 60px;
    margin: 0px 0;
    padding: 10px;
    border: 1px solid;
    font-size: inherit;
}
</style> 
	<div id="coupon-reg" class="contents" >
	<script id="tmpl-coupon-reg" type="text/ractive">
	<div id="coupon-reg" class="contents-area">
		<div class="contents-wrap">
			<h1 class="menu-title">자동회수형 쿠폰 신규 등록</h1>
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
						<input type="hidden" id="post_cnt"  value="{{post_cnt}}" disabled>	
							<input class="text-input" type="text" value="{{reg_usr}}" disabled>
						</div>
					</td>
					<th class="no-order" class="no-order">등록일</th>
					<td class="no-order">
						<div class="text-input-wrap">
							<input class="text-input" type="text"  value="{{reg_dttm}}" disabled>
						</div>
					</td>
				</tr>
			</table>
			<table id="coupon-reg-input" class="input-list">
				<tr>
					<th>쿠폰ID<br>(쿠폰발급코드)</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text"  placeholder="테넌트" value="{{#if bcn_cd+yymm+mst_seq != 0}} {{bcn_cd+yymm+mst_seq}} {{/if}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th class="must">종류</th>
					<td>
						<label><input type="radio" id="cp_kind_cd1" name="{{cp_kind_cd}}" value="1" on-change="kindCdChange:{{'1'}}"{{#if cp_seq != null}}disabled{{/if}}>에누리쿠폰</label>
						<label><input type="radio" id="cp_kind_cd2" name="{{cp_kind_cd}}" value="2" on-change="kindCdChange:{{'2'}}"{{#if cp_seq != null}}disabled{{/if}}>할인쿠폰</label>
					</td>
				</tr> 
				<tr>
					<th class="must">발급 타입 선택</th>
					<td>
						<label><input type="radio" id="cp_iss_type_cd1" name="{{cp_iss_type_cd}}" value="1" {{#if cp_seq != null}}disabled{{/if}}>일반쿠폰</label>
						<label><input type="radio" id="cp_iss_type_cd2" name="{{cp_iss_type_cd}}" value="2" {{#if cp_seq != null}}disabled{{/if}}>웰컴 메시지용</label>
						<label><input type="radio" id="cp_iss_type_cd3" name="{{cp_iss_type_cd}}" value="3" {{#if cp_seq != null}}disabled{{/if}}>시나리오 푸시용</label>
					</td>
				</tr>
				<tr>
					<th class="must">타이틀</th> 
					<td>
						<div class="text-input-wrap ex-max-width">
							<input class="text-input" type="text" value="{{cp_titl}}" id="cp_titl"  placeholder="타이틀 입력" maxlength=50>
						</div>
					</td>
				</tr>
				<tr>
					<th class="must">사용처 설정</th>
					<td> 
						<div class="date-picker" on-click="openSeltenant">
							<input type="text" id="openSeltenantBtn" placeholder="테넌트" disabled >
						</div>
						<div class="basic-btn gold" id="opentenantBtn" on-click="opentenant">테넌트 불러오기</div>
						{{#if cp_kind_cd != '2'}}						
							<label id="all_tnt_ck"><input class="" type="checkbox" name="{{all_tnt}}" id="all_tnt" value="Y" on-click="tenantAll">테넌트 전체 선택</label>
						{{/if}}	  
					</td>  
				</tr>
				<tr>
					<th class="must">발급일<br/>(쿠폰 노출일)</th>
					<td>
						<span class="comment front gray">시작일</span>
						<div class="date-picker2">
						<input id="cp_iss_strt_dt" name="datepicker" type="text" value="{{cp_iss_strt_dt}}" >
						</div>
						<span class="dash">~</span>
						<span class="comment front gray">종료일</span>
						<div class="date-picker2">
							<input id="cp_iss_end_dt" name="datepicker" type="text" value="{{cp_iss_end_dt}}" >
						</div>
					</td>
				</tr>
				<tr class="must"> 
					<th class="must">유효기간 설정</th>
					<td>
						<span class="comment front gray">시작일</span>
						<div class="date-picker2">
						<input id="cp_act_strt_dt" name="datepicker" type="text" value="{{cp_act_strt_dt}}">
						</div>
						<span class="dash">~</span>
						<span class="comment front gray">종료일</span>
						<div class="date-picker2">
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
								<input class="text-input" id="cp_sum_sale_rt" type="text" value="{{cp_sum_sale_rt}}" numberOnly="true" placeholder="할인율 입력(숫자만 입력)">
							</div>
							<span class="comment">%</span> 
							<div class="text-input-wrap">
								<input class="text-input" id="cp_max_sale_amt" type="text" value="{{cp_max_sale_amt}}" numberOnly="true" placeholder="최대 할인제한금액 입력(숫자만 입력)">
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
								<input class="text-input" id="cp_ded_amt" type="text" value="{{cp_ded_amt}}" numberOnly="true" disabled placeholder="최대 할인제한금액 입력(숫자만 입력)">
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
						<span class="comment front gray">BI 이미지<br/></span> 
						<div class="img-uploader {{#if img_uri != null}}after{{else}}before{{/if}}" id="img_seq">
							<div class="img-preview">  
								<div class="icon-delete" on-click="deleteImg" id="img_del1"></div>
								<img id="img_seq_uri" src="{{img_uri}}"> 
							</div> 
							<div class="file-uploader-wrap"> 
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>등록 이미지 권장 사이즈 : 130 * 130
						</div>
					</td> 
				</tr> 
				<tr>
					<td class="coupon-img-reg">
						<span class="comment front gray">쿠폰 상세 이미지</span>
						<div class="img-uploader {{#if dtl_img_uri != null}}after{{else}}before{{/if}}" id="dtl_img_seq">
							<div class="img-preview"> 
								<div class="icon-delete" on-click="deleteImg"  id="img_del2"></div>
								<img id="dtl_img_seq_uri" src="{{dtl_img_uri}}"> 
							</div> 
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div> 
								<span class="comment">선택된 파일 없음</span>
							</div>등록 이미지 권장 사이즈 : 334 * 175
						</div>
					</td>
				</tr>
				<tr>
					<th>기본이미지 변경하기</th>
					<td>  
						<label><input class="" type="checkbox" name="{{dft_img_yn}}" id="dft_img_yn" value="Y">현재 등록된 이미지를 기본 이미지로 변경됩니다.</label>
					</td>
				</tr>
				<tr>
					<th>쿠폰 상세(내용)</th>
					<td>
						<textarea rows="5" cols="2" placeholder="쿠폰 상세내용 직접입력" id="cp_dtl_cont" value="{{cp_dtl_cont}}"></textarea>
					</td>
				</tr>
				<tr>
					<th>유의사항</th>
					<td> 
						<br/>모든 모바일 쿠폰은 푸드코트 키오스크 에서 사용이 불가 합니다. 보유쿠폰은 유효기간 만료 후 90일 이후 보관함에서 자동 삭제 됩니다.
						<textarea rows="1" cols="2" placeholder="유의사항 직접입력" id="cp_att_part_cont" value="{{cp_att_part_cont}}" ></textarea>
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
				<div class="basic-btn" on-click="preview">미리보기</div>
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
				<h4 class="popup-title red">쿠폰 사용처</h4>
				<div class="popup-contents">
					{{#filter}} 						
					<div class="popup-table-top">
						<div class="select-wrap">
							<select id="search-cate-selector" value="{{cate_seq}}">
								<option value="">전체</option>	
								{{#filter.categoryList:num}} 								
									<option value="{{cate_seq}}">{{cate_nm_ko}}</option>
								{{/filter.categoryList}} 
							</select>
						</div>
						<div class="search-input-wrap">
							<input class="search-input" type="text" placeholder="테넌트 검색" value="{{tnt_nm_ko}}">
							
						</div>
						<br>
						<div class="search-input-wrap ex-mid-width">
							<input class="search-input" type="text" placeholder="영업테넌트코드멀치검색(0287,0171)" value="{{busi_tnt_cd}}">
							<div class="basic-btn search" on-click="opentenant">검색</div>
						</div>
					</div>   
					{{/filter}} 
					<table class="basic-list">
						<colgroup>
							<col width="38">
							<col width="200">
							<col width="200">
							<col width="100">
							<col width="72">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order"><input id="allTenant" type="checkbox" on-click="allBtTenant">선택</th>
								<th class="no-order">카테고리</th>
								<th class="no-order ellipsis ta-l">테넌트명</th>
								<th class="no-order" >영업테넌트코드</th>
								<th class="no-order">호수</th>
							</tr>
						</thead> 
						<tbody>
							{{#tenantList:num}} 
							<tr>
								<td><input id="tennant_{{tnt_seq}}" name="tennant_seq" type="checkbox"   
										on-click="tennantChk:{{tnt_seq}},{{cate_nm_ko}},{{tnt_nm_ko}},{{room_num}},{{busi_tnt_cd}},{{img_logo_uri}},{{img_thmb_uri}}">
									<input type="hidden" name="tenantInfo" value="{{tnt_seq}},{{cate_nm_ko}},{{tnt_nm_ko}},{{room_num}},{{busi_tnt_cd}},{{img_logo_uri}},{{img_thmb_uri}}">
								</td> 
								<td class="ellipsis ta-l">{{cate_nm_ko}}</td>
								<td class="ellipsis ta-l">{{tnt_nm_ko}}</td>
								<td class="ellipsis">{{busi_tnt_cd}}</td>
								<td class="ellipsis">{{room_num}}</td>
							</tr>
							{{/tenantList}}
						</tbody>
					</table>
					{{#paging}} 
					<div class="paging-wrap">
						<ul class="paging inline-list">
							<li class="prev {{#if paging.page_start == 1}}off{{/if}}" 
								on-click="movePage:{{paging.page_start - 1}}">PREV</li>
							{{#pages}}
								<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="movePage:{{this}}">{{this}}</li>
							{{/pages}}
							<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" 
								on-click="movePage:{{paging.page_end + 1}}">NEXT</li>
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

	<!--신규 쿠폰등록2-->
	<div id="coupon-reg-popup" name="tnt-reg-popup2" class="popup-wrap"><!--place-on 추가시 테넌트 불러오기 팝업 열림-->
		<div class="popup-container">
			<!--테넌트 선택 팝업-->
			<div id="coupon-place-setting" class="popup"> 
				<div class="popup-header"><span class="btn-close" on-click="selTennantClose" style="cursor: pointer">&#215;</span></div>
				<h4 class="popup-title red">쿠폰 사용처</h4>
				<div class="popup-contents" id="sc">
						 
					<table class="basic-list">
						<colgroup>
							<col width="*">
							<col width="120">
							<col width="60">
							<col width="50"> 
						</colgroup>
						<thead>
							<tr>
								<th class="no-order">카테고리</th>
								<th class="no-order">테넌트명</th>
								<th class="no-order">호수</th>
								<th class="no-order">삭제</th>
							</tr> 
						</thead>
						<tbody>
						{{#coupon}}
							{{#selTenantList:num}} 
							<tr id="selTenant_{{tnt_seq}}">
								<td class="ellipsis">{{cate_nm_ko}}</td>
								<td class="ellipsis">{{tnt_nm_ko}}</td>
								<td class="ellipsis">{{room_num}}</td>
								<td class="ellipsis" on-click="deletetenant:{{tnt_seq}}">삭제</td>
							</tr>
							{{/selTenantList}}
						{{/coupon}}
						</tbody>
					</table>
					
				</div>
				<br/> 
				<br/>
				<br/>
				<div class="btn-wrap">
					<div class="basic-btn" on-click="selTennantClose">닫기</div>
				{{#if coupon.post_cnt != '1'}}    
					<div class="basic-btn" on-click="selTennantSave">적용</div>
 				{{/if}}  
				</div> 
			</div>
			<!--//테넌트 선택 팝업--> 
		</div>
	</div>
	<!--//신규 쿠폰등록2-->
	
	<!--자동회수형 쿠폰_할인 쿠폰 미리보기 팝업-->
	<div id="auto-discount-coupon-display-popup" class="popup-wrap"><!--on class 추가-->
		<div class="popup-container">
			<div class="popup default-size">
				<div class="popup-header"><span class="btn-close" on-click="close_preview">&#215;</span></div>
				<h4 class="popup-title red">자동회수형 쿠폰 - 쿠폰 미리보기</h4>
				<div class="popup-contents">
					<section id="" class="contents auto"><!-- direct / auto -->
						<div class="discount"><!--kinds : overcharge / discount / gift-->
							<div id="coupon-area">
								<div class="logo-wrap">
									<img id="pv_img_uri" src="">
									<h3 id="pv_titl"></h3>
								</div>
								<div class="coupon-desc">{{coupon.cp_titl}}</div>
								<div class="period" id="pv_date"></div>
								<div class="barcode-wrap cpn-limit-ok cpn-period-in cpn-can-be-downloaded">
									<img class="confirm-coupon" src="https://www.starfield.co.kr/images/webview/btn_coupon_use.png" alt="쿠폰 사용하기" data-down_yn="N">
									<img class="confirm-gift" src="https://www.starfield.co.kr/images/webview/btn_giftcard.png" alt="상품권 교환하기">
									<img class="down-coupon" src="https://www.starfield.co.kr/images/webview/btn_coupon_down.png" alt="쿠폰 담기">
									<img class="coupon-barcode" src="https://www.starfield.co.kr/images/barcode.png" alt="바코드">
									<img class="used-coupon" src="https://www.starfield.co.kr/images/webview/btn_coupon_done.png" alt="쿠폰 사용완료">
									<img class="used-gift" src="https://www.starfield.co.kr/images/webview/btn_giftcard_done.png" alt="상품권 발급 완료">
									<img class="expired-coupon" src="https://www.starfield.co.kr/images/webview/btn_coupon_expired.png" alt="쿠폰 기간 만료">
									<img class="issuing-exit" src="https://www.starfield.co.kr/images/webview/btn_coupon_exit.png" alt="쿠폰 발급 종료">
								</div>
							</div>
							<div id="coupon-info-area">
								<div class="detail-contents">
									<div class="btn-area">
										<div class="btn-wrap">
											<span class="btn location"action-gguk="gguk"><span class="thumb"></span>위치</span><span class="btn call" action-gguk="gguk"><span class="thumb"></span>전화</span><span class="btn btn-wide location"  action-gguk="gguk"><span class="thumb"></span>위치</span><span class="btn btn-wide call"  action-gguk="gguk"><span class="thumb"></span>전화</span>
										</div>
									</div>
									<div class="tenant-list">
										<h3>쿠폰 사용가능 매장</h3>
									</div>
									<div class="coupon-detail">
										<h3>쿠폰상세</h3>
										<p id="tenant-detail-btn" class="red" style="display: none;">+ 상세보기</p>
										<p id="tenant-detail-btn2" style="display: none;">...</p>
									</div>
									<div class="coupon-notice">  
										<h3>유의사항</h3>
										<p>• 모든 모바일 쿠폰은 푸드코트 키오스크에서 사용이 불가합니다. <br/> 보유쿠폰은 유효기간 만료 90일 이후 보관함에서 자동 삭제됩니다.<br/>
											{{coupon.cp_att_part_cont}}
										</p> 
									</div>
									<div class="giftcard">
										<h3>상세내용</h3>
										<p>{{coupon.cp_dtl_cont}}</p>
									</div>
								</div>
							</div>
						</div>
					</section>
				</div>
			</div>
		</div>
	</div>
	<!--//자동회수형 쿠폰_할인 쿠폰 미리보기 팝업-->
	</script> 
	</div> 
	<script src="/js/app/coupon/ACPNW016.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>