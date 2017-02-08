<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="lang-reg" class="contents-area" data-bcn_cd="${bcn_cd}" data-pg_id="${pg_id}">
	<script id="tmpl-lang-reg" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">다국어 PAGE 관리 - PAGE 등록 / 수정</h1>
		<h2>다국어 적용 화면정보</h2>
		<table id="lang-page-info" class="input-list">
			<tbody><tr>
				<th>Page ID</th>
				<td>
					<div class="text-input-wrap">
						<input class="text-input" type="text" placeholder="/about" value="{{pg_id}}">
					</div>
				</td>
			</tr>
		</tbody></table>
		<h2 class="h40">다국어 번역 정보</h2>
		<div class="lang-translate-info-wrap">
			<div class="basic-btn add-btn gold" on-click="add">항목추가</div>
			{{#languageList:i}}
			{{#if sts == 1}}
			<table class="input-list lang-translate-info-table">
				<tbody><tr>
					<th>항목명</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="항목명" value="{{txt_symb}}" {{#if pg_id != "create"}}readonly{{/if}}>
						</div>
						<div class="basic-btn gold lang-del-btn" on-click="delete:{{i}}">삭제</div>
					</td>
				</tr>

				<tr>
					<th>ID 설명</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="ID 설명" value="{{txt_desc}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>국문</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="스타필드 하남" value="{{txt_ko}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>영문</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="STARFIELD HANAM" value="{{txt_en}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>중문</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="星域 河南" value="{{txt_cn}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>일문</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="スターフィールド 河南" value="{{txt_jp}}">
						</div>
					</td>
				</tr>
			</tbody></table>
			{{/if}}
			{{/languageList}}
		</div>
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="cancel">취소</div>
			<div class="basic-btn gold" on-click="save">저장</div>
		</div>
	</div>
	</script>
</div>
<script src="/js/app/language/ASYSW002D.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>