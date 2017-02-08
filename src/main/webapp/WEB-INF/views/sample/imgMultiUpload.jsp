<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<script src="/js/lib/jquery.min.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <!-- <script src="/js/lib/common.js" type="text/javascript"></script> -->
    <script src="/js/lib/file-input-button.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.iframe-transport.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/sample.css">
    
</head>
<body>

	<div id="sample-image-upload" class="contents">
	
		<script id="tmpl-sample-image-upload" type="text/ractive">

			<div id="image-info-wrap">

				<div id="title-image-upload-button" class="pic-edit-wrap">사진업로드</div>
				{{#images}}
					<img class="" src="{{img_thmb_uri}}">
				{{/images}}
				
				<!--
				<ul class="image-list">
					<li class="list-header">
						<span class="list-empno">이미지순번</span>
						<span class="list-ename">점포코드</span>
						<span class="list-job">이미지 물리 주소</span>
						<span class="list-sal">이미지 uri</span>
						<span class="list-hiredate">썸네일 물리 주소</span>
						<span class="list-button">썸네일 uri</span>
		          	</li>
		
				{{#images:num}}
					<li class="list-body {{#if (num % 2 != 1) }}odd{{/if}}">
						<span class="list-img_seq">{{img_seq}}</span>
						<span class="list-bcn_cd">{{bcn_cd}}</span>
						<span class="list-img_pys_loc">{{img_pys_loc}}</span>
						<span class="list-img_uri">{{img_uri}}</span>
						<span class="list-img_thmb">{{img_thmb}}</span>
						<span class="list-img_thmb_uri">{{img_thmb_uri}}</span>
					</li>
				{{/empList}}
				
	          
				</ul>
				-->
			
			</div>

			

		</script>
		
	</div>

	

	<script src="/js/app/sample/imgMultiUploadSample.js" type="text/javascript"></script>
	
</body>
</html>