<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>

<style type="text/css">
</style> 
	<div id="coupon-reg" class="contents" >
	<script id="tmpl-coupon-reg" type="text/ractive">
	<div id="coupon-reg" class="contents-area">
		<div class="contents-wrap">
			<h1 class="menu-title">맵파일 등록</h1>
			{{#coupon}}
			<table id="coupon-reg-input" class="input-list">
				<tr>
					<th class="must" >층</th>
					<td class="coupon-setting">
						<label>
							<div class="select-wrap"> 
							<select id="fl"  name="fl" value="{{fl}}">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
							</select> 
							</div> 
							<span class="comment">층</span>
							<input class="text-input" id="file_seq" name="file_seq" type="hidden" value="{{file_seq}}">
						</label>
					</td>
				</tr>
				<tr>
					<th>맵 파일등록</th> 
					<td class="coupon-img-reg">
						<div class="img-uploader {{#if img_uri != null}}after{{else}}before{{/if}}" id="img_seq">
							<div class="img-preview">  
								<div class="icon-delete" on-click="deleteImg"></div>
								<img id="img_seq_uri" src="{{img_uri}}"> 
							</div> 
							<div class="file-uploader-wrap"> 
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div> 
						</div>
					</td> 
				</tr>
			</table>
			{{/coupon}}
			<div class="btn-wrap"> 
				<div class="basic-btn" id="btnList" on-click="list" >목록</div>
				<div class="basic-btn" id="btnSave" on-click="regCoupon" >저장</div>
				<div class="basic-btn" id="btnSave1" on-click="regFloorBlockFromMapFile" >블록정보일괄등록</div>
			</div>
		</div>
	</div>


	</script> 
	</div > 
	<script src="/js/app/map/FloorBlockUpload.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>