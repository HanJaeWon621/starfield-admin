<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="blog-reg" class="contents-area" data-bcn_cd="${bcn_cd}" data-blog_seq="${blog_seq}">
	<script id="tmpl-blog-reg" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">스타필드 스토리 관리 - 블로그 관리 - 블로그 등록 / 수정</h1>
		<table id="blog-reg" class="input-list">
			<tr>
				<th>블로그 타이틀</th>
				<td>
					<div class="text-input-wrap">
						<input class="text-input" type="text" placeholder="블로그 타이틀을 입력하세요" value="{{blog.blog_titl}}">
					</div>
				</td>
			</tr>
			<tr class="h92">
				<th>APP 폰트 컬러</th>
				<td class="type-img">
					<div class="color-picker">
						<span class="bg-red {{#if blog.txt_colr_cd == '#C31531'}}selected{{/if}}" on-click="txt_colr_cd:#C31531"></span>
						<span class="bg-gold {{#if blog.txt_colr_cd == '#B49759'}}selected{{/if}}" on-click="txt_colr_cd:#B49759"></span>
						<span class="bg-darkgray {{#if blog.txt_colr_cd == '#7B6D65'}}selected{{/if}}" on-click="txt_colr_cd:#7B6D65"></span>
						<span class="bg-lightgray {{#if blog.txt_colr_cd == '#887D76'}}selected{{/if}}" on-click="txt_colr_cd:#887D76"></span>
						<span class="bg-black {{#if blog.txt_colr_cd == '#000000'}}selected{{/if}}" on-click="txt_colr_cd:#000000"></span>
						<span class="bg-white {{#if blog.txt_colr_cd == '#FFFFFF'}}selected{{/if}}" on-click="txt_colr_cd:#FFFFFF"></span>
					</div>
				</td>
			</tr>
			<tr>
				<th>블로그 링크 URL</th>
				<td>
					<div class="text-input-wrap">
						<input class="text-input" type="text" placeholder="https://www.starfield.co.kr/" value="{{blog.blog_url}}">
					</div>
				</td>
			</tr>
			<tr class="h92">
				<th>배경 이미지</th>
				<td>
					<div class="img-uploader {{#if blog.img_seq}}after{{else}}before{{/if}}"><!-- before: 업로드전/ after: 업로드 후-->
						<div class="img-preview">
							<div class="icon-delete" on-click="image-delete"></div>
							{{#if(blog.img_uri)}}<img src="{{blog.img_uri}}">{{/if}}
						</div>
						<div class="file-uploader-wrap">
							<div id="image_upload" class="basic-btn gold">파일선택</div>
							<span class="comment">선택된 파일 없음</span>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<th>노출 여부</th>
				<td>
					<div class="basic-box">
						<label><input type="radio" name="{{blog.sts}}" value="0">미노출</label>
						<label><input type="radio" name="{{blog.sts}}" value="1">노출</label>
					</div>
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
<script src="/js/app/starfieldStory/ACMSW001D.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>