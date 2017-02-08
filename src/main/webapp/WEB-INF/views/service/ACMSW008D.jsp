<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="inquiry-detail" class="contents-area" data-bcn_cd="${bcn_cd}" data-qna_seq="${qna_seq}">
		<script id="tmpl-inquiry-detail" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">고객센터 - 1:1 이메일 문의 관리 - 문의 상세 / 답변 등록</h1>
			<h2>문의 정보</h2>
			<table id="inquiry-info-list" class="input-list">
				<tr>
					<th>고객 성명</th>
					<td>{{qna.cust_nm}}</td>
				</tr>
				<tr>
					<th>고객 이메일</th>
					<td>{{qna.cust_email}}</td>
				</tr>
				<tr class="transparent"></tr>
				<tr>
					<th>문의 제목</th>
					<td>{{qna.qutn_titl}}</td>
				</tr>
				<tr class="h92">
					<th>문의 내용</th>
					<td class="free-text">{{qna.qutn_cont}}</td>
				</tr>
				<tr>
					<th>문의 등록일</th>
					<td>{{qna.qutn_dttm}}</td>
				</tr>
			</table>
			<h2>답변 정보</h2>
			<table id="inquiry-answer-info" class="input-list">
				<tr>
					<th>관리자 ID</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text" value="{{qna.reply_usr}}" disabled>
						</div>
					</td>
				</tr>
				<tr class="h92">
					<th>답변 내용</th>
					<td>
						<textarea {{#if(qna.reply_yn == 'Y')}}disabled{{/if}} value="{{qna.reply_cont}}"></textarea>
					</td>
				</tr>
				{{#if(qna.reply_yn == 'Y')}}
				<tr>
					<th>답변 등록일</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text" value="{{qna.reply_dttm}}" disabled>
						</div>
					</td>
				</tr>
				{{/if}}
			</table>
			<div class="btn-wrap">
				{{#if(qna.reply_yn == 'N')}}
				<div class="basic-btn gold" on-click="cancel">취소</div>
				<div class="basic-btn gold" on-click="save">저장</div>
				{{else}}
				<div class="basic-btn gold" on-click="list">목록</div>
				{{/if}}
			</div>
		</div>
		</script>
	</div>
<script src="/js/app/service/ACMSW008D.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>