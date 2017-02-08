<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="news-reg" class="contents-area" data-bcn_cd="${bcn_cd}" data-news_seq="${news_seq}">
	<script id="tmpl-news-reg" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">고객센터 관리 - NEWS 관리 - NEWS 등록 / 수정</h1>
		<table id="news-reg-list" class="input-list enter-mode"><!--입점소식 check일 때 enter-mode 클래스, 언론보도 check일 때 press-mode 클래스 추가-->
			<tr>
				<th>NEWS 구분</th>
				<td>
					{{#cateogryList}}
						<label><input type="radio" name="{{news.cate_seq}}" value="{{cate_seq}}">{{cate_nm_ko}}</label>
					{{/cateogryList}}
				</td>
			</tr>
			<tr class="transparent"></tr>
			<tr>
				<th>NEWS 제목</th>
				<td>
					<div class="text-input-wrap long">
						<input class="text-input" type="text" placehold="NEWS 제목을 입력하세요" value="{{news.news_titl}}">
					</div>
				</td>
			</tr>
			<tr class="h92">
				<th>이미지</th>
				<td>
					<div class="img-uploader {{#if news.img_seq}}after{{else}}before{{/if}}"><!-- before: 업로드전/ after: 업로드 후-->
						<div class="img-preview">
							<div class="icon-delete" on-click="image-delete"></div>
							{{#if(news.img_uri)}}<img src="{{news.img_uri}}">{{/if}}
						</div>
						<div class="file-uploader-wrap">
							<div id="image_upload" class="basic-btn gold">파일선택</div>
							<span class="comment">선택된 파일 없음</span>
						</div>
					</div>
				</td>
			</tr>
			<tr class="h92">
				<th>NEWS 내용</th>
				<td style="height: 500px;">
					<textarea value="{{news.news_cont}}"></textarea>
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
<script src="/js/app/starfieldStory/ACMSW004D.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>