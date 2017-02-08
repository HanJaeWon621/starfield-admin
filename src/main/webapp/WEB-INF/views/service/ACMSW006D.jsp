<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>

<div id="faq-mng" class="contents-area" data-bcn_cd="${bcn_cd}" data-cate_seq="${cate_seq}">
	<script id="tmpl-faq-mng" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">고객센터 관리 - FAQ 관리 - FAQ 목록</h1>
		<div class="list-top">
			<div class="basic-btn w155" on-click="back">FAQ 카테고리 관리</div>
			<ul class="inline-list">
				<li on-click="detail">게시글 추가</li><li on-click="delete">선택 삭제</li>
			</ul>
		</div>
		<table id="faq-cate-info-reg" class="input-list free-width">
			<tr>
				<th>국문 카테고리명</th>
				<td>{{category.cate_nm_ko}}</td>
			</tr>
			<tr>
				<th>영문 카테고리명</th>
				<td>{{category.cate_nm_en}}</td>
			</tr>
			<tr>
				<th>중문 카테고리명</th>
				<td>{{category.cate_nm_cn}}</td>
			</tr>
			<tr>
				<th>일문 카테고리명</th>
				<td>{{category.cate_nm_jp}}</td>
			</tr>
			<tr>
				<th>노출여부</th>
				<td>{{#if category.sts == 1}}노출{{else}}미노출{{/if}}</td>
			</tr>
		</table>
		
		<div id="faq-mng-list" class="list-table">
			<div class="t-row list-head">
				<div class="t-cell">NO</div>
				<div class="t-cell">질문글</div>
				<div class="t-cell">답글</div>
				<div class="t-cell">노출여부</div>
				<div class="t-cell">등록일</div>
				<div class="t-cell">선택</div>
			</div>
			<ul class="list-body sort">
				{{#FAQList}}
				<li class="t-row cursor-m t-row-h42" data-sort_ord="{{sort_ord}}">
					<div class="t-cell">{{sort_ord}}</div>
					<div class="t-cell ellipsis cursor-p ta-l" on-click="detail:{{.}}">{{faq_titl_ko}}</div>
					<div class="t-cell ellipsis cursor-p ta-l" on-click="detail:{{.}}">{{faq_cont_ko}}</div>
					<div class="t-cell cursor-d {{#if sts == 1}}blue{{else}}red{{/if}}"><!-- .red : 미노출 / .blue: 노출--><div class="basic-btn" on-click="changeStatus:{{.}}">{{#if sts == 1}}노출{{else}}미노출{{/if}}</div></div>
					<div class="t-cell">{{reg_dttm}}</div>
					<div class="t-cell cursor-d"><input name="groupCheckBox" type="checkbox" value="{{sort_ord}}"></div>
				</li>
				{{/FAQList}}
			</ul>
		</div>
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="cancel">취소</div>
			<div class="basic-btn gold" on-click="save">저장</div>
		</div>
	</div>
	</script>
</div>

<!--FAQ 등록/수정 팝업-->
<div id="faq-reg-popup" class="popup-wrap">
	<script id="tmpl-faq-reg-popup" type="text/ractive">
	<div class="popup-container">
		<div class="popup">
			<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
			<h4 class="popup-title red">게시글 등록 / 수정</h4>
			<div class="popup-contents">
				<table id="faq-reg" class="input-list">
					<tr>
						<th>카테고리</th>
						<td>
							{{category.cate_nm_ko}}
						</td>
					</tr>
					<tr class="transparent"></tr>
					<tr>
						<th>국문 제목</th>
						<td>
							<div class="text-input-wrap long">
								<input class="text-input" type="text" lang="ko" placeholder="국문 제목을 입력하세요" value="{{faq.faq_titl_ko}}">
							</div>
						</td>
					</tr>
					<tr>
						<th>영문 제목</th>
						<td>
							<div class="text-input-wrap long">
								<input class="text-input" type="text" lang="en" placeholder="영문 제목을 입력하세요" value="{{faq.faq_titl_en}}">
							</div>
						</td>
					</tr>
					<tr>
						<th>중문 제목</th>
						<td>
							<div class="text-input-wrap long">
								<input class="text-input" type="text" lang="zh" placeholder="중문 제목을 입력하세요" value="{{faq.faq_titl_cn}}">
							</div>
						</td>
					</tr>
					<tr>
						<th>일문 제목</th>
						<td>
							<div class="text-input-wrap long">
								<input class="text-input" type="text" lang="ja" placeholder="일문 제목을 입력하세요" value="{{faq.faq_titl_jp}}">
							</div>
						</td>
					</tr>
					<tr class="transparent"></tr>
					<tr class="h92">
						<th>국문 답변</th>
						<td>
							<textarea lang="ko" value="{{faq.faq_cont_ko}}"></textarea>
						</td>
					</tr>
					<tr class="h92">
						<th>영문 답변</th>
						<td>
							<textarea lang="en" value="{{faq.faq_cont_en}}"></textarea>
						</td>
					</tr>
					<tr class="h92">
						<th>중문 답변</th>
						<td>
							<textarea lang="zh" value="{{faq.faq_cont_cn}}"></textarea>
						</td>
					</tr>
					<tr class="h92">
						<th>일문 답변</th>
						<td>
							<textarea lang="ja" value="{{faq.faq_cont_jp}}"></textarea>
						</td>
					</tr>
				</table>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="close">취소</div>
					<div class="basic-btn gold" on-click="save">저장</div>
				</div>
			</div>
		</div>
	</div>
	</script>
</div>
<!--//FAQ 등록/수정 팝업-->

<script src="/js/lib/jquery-sortable.js" type="text/javascript"></script>
<script src="/js/app/service/ACMSW006D.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>