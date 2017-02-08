<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
<div id="admin-account-modify" class="contents-area" data-seq="${adm_seq}">
	<script id="tmpl-admin-account-form" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">계정 관리 - 관리자 계정 관리</h1>
			<table id="member-mod-list" class="input-list">
				<tr>
					<th>아이디</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text" value="{{adm_id}}" disabled>
						</div>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<div class="basic-btn gold" on-click="reset-password:{{adm_seq}}">비밀번호 초기화</div>
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
				<tr>
					<th>계정상태</th>
					<td>
						<div class="select-wrap">
							<select id="authority-selector" value={{sts}}>
								<optgroup label="계정상태">
									{{#status_list}}
									<option value="{{sts}}">{{sts_desc}}</option>
									{{/}}
								</optgroup>
							</select>
						</div>
					</td>
				</tr>


				<tr class="transparent"></tr>
				<tr>
					<th>이름</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" value="{{adm_nm}}" placeholder="이름을 입력하세요" disabled>
						</div>
					</td>
				</tr>
				<tr>
					<th>소속</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" value="{{adm_dept}}" placeholder="소속을 입력하세요" disabled>
						</div>
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>
						<div class="tel-input-wrap">
							<input class="tel-input" type="text" value="{{adm_tel_num1}}" numberOnly="true" maxlength="6" disabled>
							<div>-</div>
							<input class="tel-input" type="text" value="{{adm_tel_num2}}" numberOnly="true" maxlength="6" disabled>
							<div>-</div>
							<input class="tel-input" type="text" value="{{adm_tel_num3}}" numberOnly="true" maxlength="6" disabled>
						</div>
					</td>
				</tr>
				<tr>
					<th>휴대전화번호</th>
					<td>
						<div class="tel-input-wrap">
							<input class="tel-input" type="text" value="{{adm_cel_num1}}" numberOnly="true" maxlength="6" disabled>
							<div>-</div>
							<input class="tel-input" type="text" value="{{adm_cel_num2}}" numberOnly="true" maxlength="6" disabled>
							<div>-</div>
							<input class="tel-input" type="text" value="{{adm_cel_num3}}" numberOnly="true" maxlength="6" disabled>
						</div>
					</td>
				</tr>
				<tr>
					<th>이메일 주소</th>
					<td>
						<div class="text-input-wrap">
							<input id="txt-email-address" class="text-input" type="text" value="{{adm_email}}" placeholder="이메일주소를 입력하세요" disabled>
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr id="member-agree" class="h-free {{#if term_agre_yn == "Y"}}agreed{{/if}}"><!--동의 후에는 agreed 클래스 추가-->
					<th>개인정보 활용동의</th>
					<td>
						<div class="comment with-checkbox">미동의</div>
						<div class="comment agreed-msg">동의완료 / {{getYYYYMMDD(term_agre_date)}}</div>
					</td>
				</tr>
			</table>
			<div class="btn-wrap">
				<div class="basic-btn gold" on-click="cancel-modify">취소</div>
				<div class="basic-btn gold" on-click="do-save">저장</div>
			</div>
		</div>
	</script>
	
</div>
<script src="/js/app/account/AACT006.js" type="text/javascript"></script>
</body>
</html>