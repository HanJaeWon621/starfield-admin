<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
<div id="admin-pw" class="contents-area">
	<script id="tmpl-change-pass-form" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">관리자 비밀번호 변경</h1>
		<table id="admin-pw-list" class="input-list">
			<tr>
				<th>기존 비밀번호</th>
				<td>
					<div class="text-input-wrap">
						<input class="text-input" type="password" value="{{org_pw}}">
					</div>
				</td>
			</tr>
			<tr>
				<th>새로운 비밀번호</th>
				<td>
					<div class="text-input-wrap">
						<input class="text-input" type="password" value="{{new_pw}}">
					</div>
					<div class="comment">※ 영문, 숫자, 특수문자 조합 6~12자리</div>
				</td>
			</tr>
			<tr>
				<th>비밀번호 재확인</th>
				<td>
					<div class="text-input-wrap">
						<input class="text-input" type="password" value="{{re_new_pw}}" on-keydown="on-keydown">
					</div>
				</td>
			</tr>
		</table>
		<div class="btn-wrap">
			<div class="basic-btn" on-click="do-cancel">취소</div>
			<div class="basic-btn" on-click="do-save">저장</div>
		</div>
	</div>
	</script>
</div>
<%@ include file="../common/loading-popup.jsp" %>
<script src="/js/app/account/AACT002.js" type="text/javascript"></script>
</body>
</html>