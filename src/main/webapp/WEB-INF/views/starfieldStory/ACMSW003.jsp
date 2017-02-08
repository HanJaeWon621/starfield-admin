<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="blog-mng" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-blog-mng" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">스타필드 스토리 관리 - 추천테마 관리 - 추천테마 목록</h1>
		<div class="list-top">
			<ul class="inline-list">
				<li on-click="download">엑셀다운</li><li on-click="create">추가</li><li on-click="delete">삭제</li>
			</ul>
		</div>
		<table id="blog-mng-list" class="basic-list">
			<colgroup>
				<col width="70">
				<col width="*">
				<col width="120">
				<col width="100">
				<col width="140">
				<col width="100">
				<col width="70">
			</colgroup>
			<thead>
				<tr>
					<th {{#if(hashObject.sort_name == 'rnum')}}class="selected"{{/if}} on-click="sort:rnum">NO</th>
					<th {{#if(hashObject.sort_name == 'thme_titl')}}class="selected"{{/if}} on-click="sort:thme_titl">타이틀</th>
					<th class="no-order">배경 이미지</th>
					<th class="no-order">미리보기</th>
					<th {{#if(hashObject.sort_name == 'sts')}}class="selected"{{/if}} on-click="sort:sts">노출여부</th>
					<th {{#if(hashObject.sort_name == 'reg_dttm')}}class="selected"{{/if}} on-click="sort:reg_dttm">등록일</th>
					<th class="no-order">선택</th>
				</tr>
			</thead>
			<tbody>
				{{#list}}
				<tr>
					<td>{{rnum}}</td>
					<td on-click="detail:{{thme_seq}}" class="ellipsis ta-l cursor-p">{{thme_titl}}</td>
					<td class="bg-img-cell">
						<span class="img-wrap"><img src="{{img_uri}}"></span>
					</td>
					<td class="preview-cell">
						<img class="icon-preview" src="/images/icon_preview.png" alt="미리보기 이미지" on-click="link:{{thme_link_url}}">
						<span class="text">X</span>
					</td>
					<td class="btn-cell">
						<div class="basic-btn {{#if sts == 1}}blue{{else}}red{{/if}}" on-click="change_sts:{{.}}">{{#if sts == 1}}노출{{else}}미노출{{/if}}</div><!-- .red : 미노출 / .blue: 노출 -->
					</td>
					<td>{{reg_dttm}}</td>
					<td><input name="groupCheckBox" type="checkbox" value="{{thme_seq}}"></td>
				</tr>
				{{/list}}
			</tbody>
		</table>
		{{#paging}}
		<div class="paging-wrap">
			<ul class="paging inline-list">
			<li class="prev {{#if paging.page_start == 1}}off{{/if}}" on-click="pageMove:{{paging.page_start - 1}}">PREV</li>
			{{#pages}}
			<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="pageMove:{{this}}">{{this}}</li>
			{{/pages}}
			<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" on-click="pageMove:{{paging.page_end + 1}}">NEXT</li>
			</ul>
		</div>
		{{/paging}}
	</div>
	</script>
</div>
<script src="/js/app/starfieldStory/ACMSW003.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>