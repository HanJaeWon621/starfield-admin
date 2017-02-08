<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
<div id="account-reg" class="contents-area">
	<script id="tmpl-account-reg" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">계정 관리 - 관리자 계정 생성</h1>
		<table id="account-reg" class="input-list">
			<tr>
				<th>아이디</th>
				<td>
					<div class="text-input-wrap">
						<input class="text-input" type="text" value="{{adm_id}}" on-keydown="on-keydown-check-overlap" placeholder="아이디를 입력하세요">
					</div>
					<div class="basic-btn gold" on-click="check-overlap-id">ID 중복검사</div>
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<div class="text-input-wrap">
						<input class="text-input" type="text" value="{{adm_pw}}" placeholder="임시비밀번호 생성 버튼을 클릭하세요" disabled>
					</div>
					<div class="basic-btn gold" on-click="make-temp-password">임시비밀번호 생성</div>
				</td>
			</tr>
			<tr>
				<th>권한</th>
				<td>
					<div class="select-wrap">
						<select id="authority-selector" value={{role_seq}}>
							<optgroup label="접근권한">
								{{#roles}}
								<option value="{{role_seq}}">{{role_nm}}</option>
								{{/}}
							</optgroup>
						</select>
					</div>
				</td>
			</tr>
		</table>
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="do-cancel">취소</div>
			<div class="basic-btn gold" on-click="reg-account">계정생성</div>
		</div>
	</div>
	</script>
</div>
<script src="/js/app/account/AACT005.js" type="text/javascript"></script>
</body>
</html>