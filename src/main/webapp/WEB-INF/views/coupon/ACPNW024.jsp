<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>

<style type="text/css">
</style> 
	<div id="coupon-reg" class="contents" >
	<script id="tmpl-coupon-reg" type="text/ractive">
	
	{{#coupon}}
	<div id="coupon-reg" class="contents-area">
		<div class="contents-wrap">
			<h1 class="menu-title">쿠폰 사용 상세</h1>
			
			<table id="coupon-reg-info" class="input-list">
				<colgroup>
					<col width="">
					<col width="*">
					<col width="">
					<col width="*">
				</colgroup>
				<tr>
					<th class="no-order">사용자 ID</th>
					<td class="no-order">
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text" value="{{cust_id}}" disabled>
						</div>
					</td>
					<th class="no-order" class="no-order">단말ID</th>
					<td class="no-order">
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text" value="{{uuid}}" disabled>
						</div>
					</td>
				</tr>
				<tr>
					<th class="no-order">다운로드 일시</th>
					<td class="no-order">
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text" value="{{cp_dn_dt}}" disabled>
						</div>
					</td>
					<th class="no-order" class="no-order">사용 처리 일시</th>
					<td class="no-order">
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"   value="{{cp_prc_dt}}" disabled>
						</div>
					</td>
				</tr>
				<tr>
					<th class="no-order">사용처리결과</th>
					<td class="no-order">
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"   value="{{cp_use_sts_cd_nm}}" disabled>
						</div>
					</td>
					<th class="no-order" class="no-order">처리변경 작업자</th>
					<td class="no-order">
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text" value="{{reg_usr}}" disabled>
						</div> 
					</td>
				</tr>
			</table>
			<table id="coupon-reg-input" class="input-list">
				<tr>
					<th>쿠폰ID<br>(쿠폰발급코드)</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{cp_iss_cd}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th>종류</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{cp_kind_cd_nm}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th>발급 타입</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{cp_iss_type_cd_nm}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th>타이틀</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{cp_titl}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th>사용처</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{use_tnt_nm}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th>발급일<br>(쿠폰 노출일)</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{cp_iss_strt_dt}} ~ {{cp_iss_end_dt}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th>유효기간</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{cp_act_strt_dt}} ~ {{cp_act_end_dt}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th>할인방식</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{cp_sale_div_cd}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th>사용조건설정</th> 
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{cp_use_cond_amt}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th>쿠폰상세</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{cp_dtl_cont}}" disabled>
						</div>  
					</td> 
				</tr>
				<tr>
					<th>유의사항</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text"  value="{{cp_att_part_cont}}" disabled>
						</div>  
					</td> 
				</tr>
			</table>
			
			<div class="btn-wrap">
				<div class="basic-btn" id="btnSave2" on-click="modifyCpUseProcess2" style="display: none;" >사용취소적용</div> 
				<div class="basic-btn" id="btnClose" on-click="bntClose" >목록</div>
			</div> 
		</div>
	</div> 
	{{/coupon}}
	</script> 
	</div > 
	<script src="/js/app/coupon/ACPNW024.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>