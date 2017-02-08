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
			<h1 class="menu-title">교환권등록</h1>
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
					<th class="must">타이틀</th> 
					<td>
						<div class="text-input-wrap long">
							<input class="text-input" type="text" value="{{cp_titl}}" id="cp_titl"  placeholder="쿠폰타이틀 직접입력">
						</div>
					</td>
				</tr>
				<tr class="must"> 
					<th class="must">노출기간 설정</th>
					<td>
						<span class="comment front gray">시작일</span>
						<div class="date-picker">
						<input id="cp_iss_strt_dt" name="datepicker" type="text" value="{{cp_iss_strt_dt}}">
						</div>
						<span class="dash">~</span>
						<span class="comment front gray">종료일</span>
						<div class="date-picker">
							<input id="cp_iss_end_dt" name="datepicker" type="text" value="{{cp_iss_end_dt}}">
						</div>
					</td>
				</tr>
				<tr class="must"> 
					<th class="must">사용기간 설정</th>
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
					<th >이미지등록</th> 
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
					<th>쿠폰 상세(내용)</th>
					<td>
						<div class="text-input-wrap long">
							<input class="text-input" type="text" value="{{cp_dtl_cont}}" placeholder="쿠폰 상세내용 직접입력">
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
	</script> 
	</div > 
	<script src="/js/app/coupon/ACPNW014.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>