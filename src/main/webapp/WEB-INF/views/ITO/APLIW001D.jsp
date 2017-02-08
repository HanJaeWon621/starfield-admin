<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="location-reg" class="contents-area" data-bcn_cd="${bcn_cd}" data-plid_mng_seq="${plid_mng_seq}">
	<script id="tmpl-location-reg" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">APP 개인 위치정보 삭제접수 관리 - 등록/수정</h1>
		<h2>삭제 요청자 정보</h2>
		<table id="location-ask-list" class="input-list">
			<tr>
				<th>고객이름</th>
				<td>
					<div class="text-input-wrap">
						<input class="text-input" type="text" value="" placeholder="고객이름을 입력하세요" value="{{locationInfo.name}}" {{#if locationInfo.req_del_dttm}}disabled{{/if}} maxlength="10">
					</div>
				</td>
			</tr>
			<tr>
				<th>고객전화번호</th>
				<td>
					<div class="text-input-wrap">
						<input class="text-input" type="text" value="" placeholder="고객전화번호를 입력하세요" value="{{locationInfo.phone_num}}"  onkeyPress="if ((event.keyCode<48) || (event.keyCode>57)) event.returnValue=false;" {{#if locationInfo.req_del_dttm}}disabled{{/if}} maxlength="12">
					</div>
				</td>
			</tr>
			{{#locationInfo.req_del_dttm}}
			<tr>
				<th>삭제 요청일시</th>
				<td>
					<div class="text-input-wrap no-border">
						<input class="text-input" type="text" value="{{locationInfo.req_del_dttm}}" disabled>
					</div>
				</td>
			</tr>
			{{/locationInfo.req_del_dttm}}
		</table>
		<h2>조치 결과</h2>
		<table id="location-result-reg" class="input-list">
			<tr>
				<th>조치 여부</th>
				<td>
					<div class="select-wrap">
						<select id="location-result-measure-selector" value="{{locationInfo.act_yn}}" {{#if locationInfo.act_dttm}}disabled{{/if}}>
							<option value="Y">Y</option>
							<option value="N">N</option>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<th>조치 결과</th>
				<td>
					<div class="select-wrap">
						<select id="location-result-result-selector" value="{{locationInfo.sts}}">
							<option value="0">접수</option>
							<option value="1">확인중</option>
							<option value="9">삭제완료</option>
							<option value="8">삭제불가</option>
						</select>
					</div>
				</td>
			</tr>
			{{#locationInfo.act_dttm}}
			<tr>
				<th>조치 일시</th>
				<td>
					<div class="text-input-wrap no-border">
						<input class="text-input" type="text" value="{{locationInfo.act_dttm}}" disabled>
					</div>
				</td>
			</tr>
			{{/if}}
		</table>
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="cancel">취소</div>
			<div class="basic-btn gold" on-click="save">저장</div>
		</div>
	</div>
	</script>
</div>
<script src="/js/app/ITO/APLIW001D.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
