<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>

<style type="text/css">
</style> 
	<div id="location-mng" class="contents" >
	<script id="tmpl-location-mng" type="text/ractive">
	<div id="coupon-reg" class="contents-area">
		<div class="contents-wrap">
			<h1 class="menu-title">위치정보 열람요청</h1>
			{{#locationReqView}}
			<table id="coupon-reg-info" class="input-list">
				<colgroup>
					<col width="">
					<col width="*">
					<col width=""> 
					<col width="*">
				</colgroup>
				<tr>
					<th class="no-order">취급자</th>
					<td class="no-order">
						<div class="text-input-wrap">
							<input class="text-input" type="text" value="등록자 명 (접속자 자동 입력)" value="${adm_id}" disabled>
						</div>
					</td>
					<th class="no-order" class="no-order">등록일</th>
					<td class="no-order">
						<div class="text-input-wrap">
							<input class="text-input" type="text"  id="current-time" value="작업시간 자동 입력" value="" disabled>
						</div>
					</td>
				</tr>
			</table>
			<table id="coupon-reg-input" class="input-list">
				<tr> 
					<th class="must" >요청자</th>
					<td> 
						<br/> 
						<div class="basic-btn gold" id="" on-click="openRequester">요청자 검색</div>
						<br/> 
						<br/> 
						<div class="text-input-wrap long">
							<input class="text-input" type="text" value="{{locationReqView.req_adm_nm}}" id="req_adm_nm" placeholder="요청자 이름 자동 입력" disabled>
							<input class="text-input" type="hidden" value="{{locationReqView.req_adm_seq}}" id="req_adm_seq">
						</div>  
						<br/> 
						<br/> 
						<div class="text-input-wrap long">
							<input class="text-input" type="text" value="" id="tel_num" placeholder="요청자 전화번호 자동입력(가운대번호 4자리 암호화)" disabled>
						</div> 
						<br/>      
						<br/>    
					</td>  
				</tr>  
				<tr> 
					<th class="must" >열람목적</th>
					<td> 
						<div class="text-input-wrap long">
							<input class="text-input" type="text" value="{{locationReqView.read_obj}}" id="" placeholder="열람 신청 목적 직접 입력">
						</div>  
					</td>  
				</tr>
			</table> 
			{{/locationReqView}}
			<div class="btn-wrap"> 
				<div class="basic-btn" id="btnSave" on-click="closeLocationReqView" >취소</div>
				<div class="basic-btn" id="btnSave" on-click="saveLocationReqView" >신청</div>
			</div>
		</div>
	</div>

	<div id="coupon-reg-popup" name="tnt-reg-popup" class="popup-wrap">
		<div class="popup-container">
			<!--테넌트 선택 팝업-->
			<div id="coupon-place-setting" class="popup"> 
				<div class="popup-header"><span class="btn-close" on-click="locationUsrClose">&#215;</span></div>
				<h4 class="popup-title red">사용자(요청자) 검색</h4>
				<div class="popup-contents">
					{{#filter}}
					<div class="popup-table-top">
						<div class="select-wrap">
							<select id="sh_text_type" >
								<option value="1">요청자명</option>
								<option value="2">전화번호</option>
							</select> 
						</div>
						<div class="search-input-wrap">
							<input class="search-input" type="text" id="sh_text" placeholder="검색어">
							<div class="basic-btn search" on-click="openRequester">검색</div>
						</div> 
					</div>
					{{/filter}}
					<table class="basic-list">
						<colgroup>
							<col width="78">
							<col width="200">
							<col width="*">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order">선택</th>
								<th class="no-order">사용자명</th>
								<th class="no-order">전화번호</th>
							</tr>
						</thead> 
						<tbody>
							{{#locationUsrList:num}} 
							<tr>
								<td class="ellipsis"><input type="radio" name="ckUsr" id="ckUsr_{{mbr_seq}}" on-click="ckUsr:{{mbr_seq}}" value="{{mbr_nm}},{{mbr_cel_num1}},{{mbr_cel_num2}},{{mbr_cel_num3}},{{mbr_seq}},{{cust_id}}"></td>
								<td class="ellipsis">{{mbr_nm}}</td> 
								<td class="ellipsis">{{mbr_cel_num1}}-****-{{mbr_cel_num3}}</td>
							</tr>
							{{/locationUsrList}}
						</tbody>
					</table>
					{{#paging}} 
					<div class="paging-wrap">
						<ul class="paging inline-list">
							<li class="prev {{#if paging.page_start == 1}}off{{/if}}" 
								{{#if paging.page_start == 1}} on-click="pageMove:{{paging.page_start - 1}}" {{/if}}>PREV</li>
							{{#pages}}
								<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="pageMove:{{this}}">{{this}}</li>
							{{/pages}}
							<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" 
								{{#if paging.page_end == paging.total_page_cnt}} on-click="pageMove:{{paging.page_end + 1}}" {{/if}}>NEXT</li>
						</ul>
         		   </div> 
					{{/paging}} 
					<div class="btn-wrap">
						<div class="basic-btn" on-click="locationUsrClose">닫기</div>
						<div class="basic-btn" on-click="locationUsrSave">적용</div>
					</div>
				</div>
			</div>
			<!--//테넌트 선택 팝업-->
		</div>
	</div>

	</script> 
	</div > 
	<script src="/js/app/ITO/APLIW004D.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>