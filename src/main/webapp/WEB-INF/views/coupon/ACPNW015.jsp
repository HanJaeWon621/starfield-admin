<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>쿠폰 등록</title>

	<script src="/js/lib/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <script src="/js/lib/common.js" type="text/javascript"></script>
    
    <script src="/js/lib/file-input-button.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.iframe-transport.js" type="text/javascript"></script>
    
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="/css/common.css">
</head>
<body>  
	<div id="coupon-reg" class="contents" >
		<script id="tmpl-coupon-reg" type="text/ractive">
		<h3>교환 쿠폰 상세</h3> 
		{{#coupon}}
			<table>
					<tr> 
					<td>
						쿠폰ID :
					</td>  
					<td> 
						{{bcn_cd+yymm+mst_seq}}
					</td> 
					</tr>
					<tr>
						<td>
							*타이틀 :
						</td>
						<td> 
							<input id="cp_titl" type="text" value="{{cp_titl}}" style="width:350px;">
						</td>
					</tr>
					<tr>			
						<td> 
							*노출 기간 설정  :
						</td>
						<td>
							시작일 <input id="cp_iss_strt_dt" name="datepicker" type="text" value="{{cp_iss_strt_dt}}" style="width:70px;"> ~ 
							종료일 <input id="cp_iss_end_dt" name="datepicker" type="text" value="{{cp_iss_end_dt}}" style="width:70px;">
						</td>
					</tr>
					<tr>
						<td>
							*사용기간설정  :
						</td>
						<td>
							시작일 <input id="cp_act_strt_dt" name="datepicker" type="text" value="{{cp_act_strt_dt}}" style="width:70px;"> ~ 
							종료일 <input id="cp_act_end_dt" name="datepicker" type="text" value="{{cp_act_end_dt}}" style="width:70px;">
						</td>
					</tr>
					<tr>
						<td>
							*BI 이미지:
						</td>
						<td>
							BI 이미지
							<div class="pic-upload-wrap">
	    						<div id="title-image-upload-button" class="pic-edit-wrap" on-click="imgBtnT:{{'1'}}">이미지</div>
							</div>
							<br/> 
							<br/> 
							<br/>
							<img id="imgurl" src="\cdn\barcode\images\201606\5000\0120160601000000044.jpg" style="width: 50px;height: 50px">
						</td>
					</tr>
					<tr>
						<td>
							쿠폰 상세(내용) : 
						</td>
						<td>
							<input id="cp_dtl_cont" type="text" value="{{cp_dtl_cont}}">
						</td>
					</tr>
					<tr> 
						<td>
							<input type="button" value="취소" on-click="resetCoupon"/>
							<input type="button" value="미리보기" on-click=""/>
							<input type="button" value="저장" on-click="regCoupon" />
							{{cp_exp_yn}} 
							{{#if cp_exp_yn == 'N'}}
								<input type="button" value="게시" on-click="updatePosting:{{cp_seq}}"/>
							{{/if}}						
						</td>
					</tr>
				</table>
		{{/coupon}}
		</script> 
	</div> 
	
	<script src="/js/app/coupon/ACPNW015.js" type="text/javascript"></script>
</body>
</html>