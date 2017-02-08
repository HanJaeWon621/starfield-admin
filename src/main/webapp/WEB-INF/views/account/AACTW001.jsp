<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>STARFIELD HANAM ADMIN</title>
	<script src="/js/lib/jquery.min.js" type="text/javascript"></script>
	<script src="/js/lib/common.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
	
	<link rel="stylesheet" type="text/css" href="../css/admin.css">
</head>
<body>
<div id="login" class="contents-area">
	<script id="tmpl-login-form" type="text/ractive">
	<div class="contents-wrap">
		<div class="logo-wrap"><img src="../images/logo_only.png"></div>
		<p class="bold">스타필드 홈페이지 관리 페이지에 오신 것을 환영합니다.</p>
		<ul id="login-input">
			<li>
				<div class="basic-btn no-hover">아이디</div><div class="text-input-wrap">
					<input class="text-input adm_id" type="text" value="{{adm_id}}" placeholder="아이디를 입력하세요" autofocus>
				</div>
			</li>
			<li>
				<div class="basic-btn no-hover">비밀번호</div><div class="text-input-wrap">
					<input class="text-input adm_pw" type="password" value="{{adm_pw}}" placeholder="비밀번호를 입력하세요" on-keydown="on-keydown">
				</div>
			</li>
		</ul>
		<button class="basic-btn gold login-btn" type="button" on-click="do-login">로그인</button>
		<p class="login-bottom"><span class="bold">관리시스템 문의</span> 시스템 관리자 : 02-1111-1111  |  스타필드 관리자 : 02-2222-222</p>
	</div>
	</script>
</div>
<%@ include file="../common/loading-popup.jsp" %>
<script src="/js/app/account/AACT001.js" type="text/javascript"></script>
</body>
</html>