<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<script src="/js/lib/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <script src="/js/lib/common.js" type="text/javascript"></script>
    <script src="/js/lib/file-input-button.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.iframe-transport.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/sample.css">
    
</head>
<body>

	<div id="sample-excel-upload" class="contents">
	
		<script id="tmpl-sample-excel-upload" type="text/ractive">

			<div id="image-info-wrap">

				<div id="title-excel-upload-button" class="pic-edit-wrap">엑셀업로드</div>
			
			</div>

		</script>
		
	</div>

	

	<script src="/js/app/sample/excelUploadSample.js" type="text/javascript"></script>
	
</body>
</html>