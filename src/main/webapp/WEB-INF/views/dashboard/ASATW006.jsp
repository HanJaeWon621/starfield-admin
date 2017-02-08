<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>STARFIELD HANAM ADMIN</title>
	<script src="/js/lib/jquery.min.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <script src="/js/lib/common.js" type="text/javascript"></script>
    <script src="/js/lib/file-input-button.js" type="text/javascript"></script>
	<script src="/js/lib/async.min.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.iframe-transport.js" type="text/javascript"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui-base-1.12.0.min.css">
    <%-- <link rel="stylesheet" type="text/css" href="/css/common.css"> --%>
	<link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="/css/admin.css">
<script src="/js/lib/beemap-indoor-1.0.min.js"></script>
	<!--[if lt IE 9]>
		<link rel="stylesheet" type="text/css" href="/css/ie9.css" />
	<![endif]-->
</head>
<style>
 .my-image { 
    max-width: 100%;
    height: auto;
}    
</style>
<script src="/js/lib/heatmap.js"></script>
<div id="heatmap" class="contents">
<script id="tmpl-heatmap" type="text/ractive"> 
<input type="hidden" id="sh_dt" value="${sh_dt}">
<input type="hidden" id="sh_floor" value="${sh_floor}"> 
<div class=""  id="heatmapContainer" on-click="big_heatMap">
	<img class="my-image" id="floor_img" src="/images/L1.png"/>
</div> 
</script> 
</div>
<script src="/js/app/dashboard/ASATW006.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>