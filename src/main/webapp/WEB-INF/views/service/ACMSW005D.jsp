<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="notice-reg" class="contents-area" data-bcn_cd="${bcn_cd}" data-noti_seq="${noti_seq}">
	<script id="tmpl-notice-reg" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">고객센터 - 공지사항 관리 - 등록 / 수정</h1>
		<table id="notice-reg" class="input-list">
			<tr>
				<th>제목</th>
				<td>
					<div class="text-input-wrap long">
						<input class="text-input" type="text" placeholder="제목을 입력하세요" value="{{notice.noti_titl}}">
					</div>
				</td>
			</tr>
			<tr class="h92">
				<th>내용</th>
				<td style="height: 200px;">
					<textarea value="{{notice.noti_cont}}"></textarea>
				</td>
			</tr>
		</table>
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="cancel">취소</div>
			<div class="basic-btn gold" on-click="save">저장</div>
		</div>
	</div>
	</script>
</div>
<script src="/js/app/service/ACMSW005D.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>