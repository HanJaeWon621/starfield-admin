<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="sns-mng" class="contents-area" data-bcn_cd="${bcn_cd}" data-token="${token}" data-client_id="${client_id}">
	<script id="tmpl-sns-mng" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">스타필드 스토리 관리 - 인스타그램 관리 - 태그 목록</h1>
		<div class="list-top">
			<ul class="inline-list">
				<li on-click="addTagPopup">태그 등록</li><li on-click="delete">삭제</li><li on-click="token">인스타그램 토큰 재발급</li>
			</ul>
		</div>
		
		<div id="sns-mng-list" class="list-table">
			<div class="t-row list-head">
				<div class="t-cell">NO</div>
				<div class="t-cell">태그명</div>
				<div class="t-cell">이미지 수</div>
				<div class="t-cell">노출여부</div>
				<div class="t-cell">등록일</div>
				<div class="t-cell">선택</div>
			</div>
			<ul class="list-body sort">
				{{#instagramTagList}}
				<li class="t-row cursor-m t-row-h42" data-sort_ord="{{sort_ord}}">
					<div class="t-cell">{{sort_ord}}</div>
					<div class="t-cell cursor-p" on-click="detail:{{.}}">{{insta_tag_nm}}</div>
					<div class="t-cell cursor-p" on-click="detail:{{.}}">{{img_count}}</div>
					<div class="t-cell cursor-d">
						<div class="basic-btn {{#if sts == 0}}red{{else}}blue{{/if}}" on-click="changeStatus:{{.}}">{{#if sts == 0}}미노출{{else}}노출{{/if}}</div><!--blue: 노출 red: 미노출-->
					</div>
					<div class="t-cell">{{reg_dttm}}</div>
					<div class="t-cell cursor-d"><input name="groupCheckBox" type="checkbox" value="{{sort_ord}}"></div>
				</li>
				{{/instagramTagList}}
			</ul>
		</div>
		
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="cancel">취소</div>
			<div class="basic-btn gold" on-click="save">저장</div>
		</div>
	</div>
	<!--태그 등록 팝업-->
	<div id="sns-tag-reg-popup" class="popup-wrap">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="closePopup">&#215;</span></div>
				<h4 class="popup-title red">태그 등록</h4>
				<div class="popup-contents">
					<ul class="draw-wrap">
						<li>
							<div class="label-box">태그명</div>
							<div class="text-input-wrap">
								<input id="addTag" class="text-input" type="text">
							</div>
						</li>
					</ul>
					<div class="btn-wrap">
						<div class="basic-btn gold" on-click="closePopup">취소</div>
						<div class="basic-btn gold" on-click="addTag">저장</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--//태그 등록 팝업-->

	</script>
</div>


<script src="/js/lib/jquery-sortable.js" type="text/javascript"></script>
<script src="/js/app/starfieldStory/ACMSW002.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>