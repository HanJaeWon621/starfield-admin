<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>STARFIELD HANAM ADMIN</title>
	<script src="/js/lib/jquery.min.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <script src="/js/lib/common.js" type="text/javascript"></script>
    <script src="/js/lib/file-input-button.js" type="text/javascript"></script>
    <script src="/js/lib/jquery.extends.js" type="text/javascript"></script>
    <script src="/js/lib/jquery.form.js" type="text/javascript"></script>
	<script src="/js/lib/async.min.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.iframe-transport.js" type="text/javascript"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui-base-1.12.0.min.css">
    <%-- <link rel="stylesheet" type="text/css" href="/css/common.css"> --%>
	<link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="/css/admin.css"> 
	<!--[if lt IE 9]>
		<link rel="stylesheet" type="text/css" href="/css/ie9.css" />
	<![endif]-->
</head>
<body>
	<div id="header">
		<div class="wrap">
			<a class="img-logo" href="/${bcn_cd}/dashboard"></a>
			<div id="header-contents">
				<div class="select-wrap">
					<select id="branch-selector">
						<option value="01">하남점</option>
					</select>
				</div>
				<ul class="inline-list">
					<li>
						<div class="h-menu">${adm_id}</div>
					</li>
					<li>
						<a class="h-menu" href="/${bcn_cd}/account/self">회원정보</a>
					</li>
					<li>
						<a class="h-menu" href="/logout">Logout</a>
					</li>
					<li>
						<a class="h-menu" href="https://www.starfield.co.kr/" target="_blank">Web</a>
					</li>
				</ul>
			</div>
		</div>
	</div>