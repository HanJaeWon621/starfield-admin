<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>

<div id="faq-cate-mng" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-faq-cate-mng" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">고객센터 관리 - FAQ 관리 - FAQ 카테고리 목록</h1>
		<div class="list-top">
			<ul class="inline-list">
				<li on-click="download">엑셀다운</li><li on-click="showPopup">카테고리 추가</li><li on-click="delete">선택 삭제</li>
			</ul>
		</div>
		
		<div id="faq-cate-mng-list" class="list-table">
			<div class="t-row list-head">
				<div class="t-cell">순서</div>
				<div class="t-cell">국문 카테고리명</div>
				<div class="t-cell">영문 카테고리명</div>
				<div class="t-cell">중문 카테고리명</div>
				<div class="t-cell">일문 카테고리명</div>
				<div class="t-cell">게시글 수</div>
				<div class="t-cell">노출여부</div>
				<div class="t-cell">등록일</div>
				<div class="t-cell">선택</div>
			</div>
			<ul class="list-body sort">
				{{#faqCateList}}
				<li class="t-row cursor-m t-row-h42" data-sort_ord="{{sort_ord}}">
					<div class="t-cell">{{sort_ord}}</div>
					<div class="t-cell" lang="ko" on-click="modifyPopup:{{.}}">{{cate_nm_ko}}</div>
					<div class="t-cell" lang="en" on-click="modifyPopup:{{.}}">{{cate_nm_en}}</div>
					<div class="t-cell ellipsis" lang="zh" on-click="modifyPopup:{{.}}">{{cate_nm_cn}}</div>
					<div class="t-cell ellipsis" lang="ja" on-click="modifyPopup:{{.}}">{{cate_nm_jp}}</div>
					<div class="t-cell cursor-d {{#if count < 1}}red{{else}}blue{{/if}}"><!-- .red : 0 / .blue: 1 이상--><div class="basic-btn" on-click="faqList:{{.}}">{{count}}</div></div>
					<div class="t-cell cursor-d {{#if sts == 0}}red{{else}}blue{{/if}}"><!-- .red : 미노출 / .blue: 노출--><div class="basic-btn" on-click="changeStatus:{{.}}">{{#if sts == 0}}미노출{{else}}노출{{/if}}</div></div>
					<div class="t-cell">{{reg_dttm}}</div>
					<div class="t-cell cursor-d"><input name="groupCheckBox" type="checkbox" value="{{sort_ord}}"></div>
				</li>
				{{/faqCateList}}
			</ul>
		</div>
	<div class="btn-wrap">
		<div class="basic-btn gold" on-click="cancel">취소</div>
		<div class="basic-btn gold" on-click="save">저장</div>
	</div>
	</div>
	
	<!--FAQ 등록/수정 팝업-->
<div id="faq-cate-reg-popup" class="popup-wrap">
	<div class="popup-container">
		<div class="popup">
			<div class="popup-header"><span class="btn-close" on-click="hidePopup">&#215;</span></div>
			<h4 class="popup-title red">FAQ 카테고리 등록 / 수정</h4>
			<div class="popup-contents">
				<table id="faq-cate-reg" class="input-list">
					<tr>
						<th>국문 카테고리명</th>
						<td>
							<div class="text-input-wrap">
								<input id="cate_nm_ko" class="text-input" type="text" lang="ko" placeholder="국문 카테고리명을 입력하세요" maxlength="30">
							</div>
						</td>
					</tr>
					<tr>
						<th>영문 카테고리명</th>
						<td>
							<div class="text-input-wrap">
								<input id="cate_nm_en" class="text-input" type="text" lang="en" placeholder="영문 카테고리명을 입력하세요" maxlength="30">
							</div>
						</td>
					</tr>
					<tr>
						<th>중문 카테고리명</th>
						<td>
							<div class="text-input-wrap">
								<input id="cate_nm_cn" class="text-input" type="text" lang="zh" placeholder="중문 카테고리명을 입력하세요" maxlength="30">
							</div>
						</td>
					</tr>
					<tr>
						<th>일문 카테고리명</th>
						<td>
							<div class="text-input-wrap">
								<input id="cate_nm_jp" class="text-input" type="text" lang="ja" placeholder="일문 카테고리명을 입력하세요" maxlength="30">
							</div>
						</td>
					</tr>
				</table>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="hidePopup">취소</div>
					<div class="basic-btn gold" on-click="add">추가</div>
				</div>
			</div>
		</div>
	</div>
</div>
	</script>
</div>


<!--//FAQ 등록/수정 팝업-->
<script src="/js/lib/jquery-sortable.js" type="text/javascript"></script>
<script src="/js/app/service/ACMSW006.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>