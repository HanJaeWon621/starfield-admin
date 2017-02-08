<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="push-reg" class="contents-area">
	<script id="tmpl-push-reg" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">APP Push - APP Push 관리 - 등록 / 수정</h1>

			<table id="push-reg-list" class="input-list {{#if push.push_mng_seq}}mod{{/if}} {{#if push.send_dttm}}pushed{{/if}}"><!--reg: 신규 등록, mod: 수정 / 발송된 경우 pushed class 추가-->
				<tr class="before-send-only">
					<th>발송구분</th>
					<td>
						<div class="basic-box">
							<c:forEach var="option" items="${sendType}">
								<label><input type="radio" name="{{push.send_type.codeCd}}" value="${option.codeCd}" <c:if test="${option.defaultValue}"> checked="checked"</c:if>>${option.codeNm}</label>
							</c:forEach>
						</div>
						<div>
							<div class="reserve-only">
								<div class="date-picker">
									<input type="text" name="datepicker" placeholder="YYYY.MM.DD" value="{{push.reserve_dt}}">
								</div>
								<div class="select-wrap time">
									<select class="time-selector" value="{{push.reserve_hour}}">
										{{#hourList}}
										<option value="{{.cd}}">{{.nm}}</option>
										{{/hourList}}
									</select>
								</div>
								<div class="select-wrap time">
									<select class="time-selector" value="{{push.reserve_minute}}">
										{{#minuteList}}
										<option value="{{.cd}}">{{.nm}}</option>
										{{/minuteList}}
									</select>
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr class="mod-only">
					<th>발송일시</th>
					<td>
						<span class="comment pushed-only pushed-date">{{push.send_dttm}}</span>
						<span class="comment not-pushed">-</span>
					</td>
				</tr>
				<tr class="mod-only">
					<th>발송상태</th>
					<td>
						<span class="comment">{{push.send_yn.codeNm}}</span>
					</td>
				</tr>
				<tr class="before-send-only">
					<th>Push 메시지</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="Push 메시지를 입력하세요" value="{{push.push_msg}}">
						</div>
					</td>
				</tr>
			</table>
			<div class="btn-wrap">
				{{#if !push.send_dttm}}
				<div class="basic-btn gold" on-click="cancel">취소</div>
				<div class="basic-btn gold" on-click="regPush">저장</div>
				{{else}}
				<div class="basic-btn gold" on-click="confirm">확인</div>
				{{/if}}
			</div>
		</div>
	</script>
	</div>
<script type="text/javascript">
var push_mng_seq = '${pushMngSeq}' || '';
</script>
<script src="/js/app/appPush/APSHW002.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>