<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="admin-app-ver" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-admin-app-ver" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">운영 관리 - APP 버전 관리</h1>
		<div class="list-top">
			<ul class="inline-list">
				<li on-click="download">엑셀 다운</li><li on-click="create">추가</li><li on-click="delete">선택 삭제</li>
			</ul>
		</div>
		<table id="admin-app-ver-list" class="basic-list">
			<colgroup>
				<col width="100">
				<col width="250">
				<col width="150">
				<col width="*">
				<col width="*">
				<col width="100">
			</colgroup>
			<thead>
				<tr>
					<th {{#if(hashObject.sort_name == 'rnum')}}class="selected"{{/if}} on-click="sort:rnum">NO</th>
					<th {{#if(hashObject.sort_name == 'pltf_type')}}class="selected"{{/if}} on-click="sort:pltf_type">OS 구분</th>
					<th {{#if(hashObject.sort_name == 'version')}}class="selected"{{/if}} on-click="sort:version">버전</th>
					<th {{#if(hashObject.sort_name == 'reg_dttm')}}class="selected"{{/if}} on-click="sort:reg_dttm">등록일</th>
					<th {{#if(hashObject.sort_name == 'apply_dttm')}}class="selected"{{/if}} on-click="sort:apply_dttm">적용일</th>
					<th class="no-order">선택</th>
				</tr>
			</thead>
			<tbody>
				{{#list}}
				<tr>
					<td class="cursor-p" on-click="detail:{{.}}">{{rnum}}</td>
					<td class="cursor-p" on-click="detail:{{.}}">{{#if pltf_type == 1}}iOS{{else}}Android{{/if}}</td>
					<td class="cursor-p" on-click="detail:{{.}}">{{version}}</td>
					<td class="app-reg-date">{{reg_dttm}}</td>
					<td class="app-apply-cell {{#if sts == 1}}applied{{/if}}"><!--applied class 추가시 버튼 사라지고 날짜 나타남-->
						<span class="basic-btn" on-click="applyVersion:{{.}}">적용</span>
						<span class="app-apply-date">{{apply_dttm}}</span>
					</td>
					<td>{{#if sts == 0}}<input name="groupCheckBox" type="checkbox" value="{{amav_seq}}">{{/if}}</td>
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

<!--APP 버전 등록/수정 팝업-->
<div id="app-ver-reg-popup" class="popup-wrap">
	<script id="tmpl-app-ver-reg-popup" type="text/ractive">
	<div class="popup-container">
		<div class="popup">
			<div class="popup-header"><span class="btn-close" on-click="close:{{appVer.sts}}">&#215;</span></div>
			<h4 class="popup-title red">APP 버전 등록 / 수정</h4>
			<div class="popup-contents">
				<ul class="draw-wrap">
					<li>
						<div class="label-box">OS 구분</div>
						<div class="select-wrap">
							<select id="os-selector" value="{{appVer.pltf_type}}" {{#if appVer.sts == 1}}disabled{{/if}}>
								<option value="2">Android</option>
								<option value="1">iOS</option>
							</select>
						</div>
					</li>
					<li>
						<div class="label-box">최종 버전</div>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text" value="{{#if appVer.pltf_type == 1}}{{iOS_ver}}{{else}}{{Android_ver}}{{/if}}" disabled>
						</div>
					</li>
					<li>
						<div class="label-box">신규 버전</div>
						<div class="text-input-wrap">
							<input class="text-input" type="text" value="{{appVer.version}}" {{#if appVer.sts == 1}}disabled{{/if}} maxlength="7">
						</div>
					</li>
				</ul>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="close:{{appVer.sts}}">취소</div>
					{{#if appVer.sts == 0}}<div class="basic-btn gold" on-click="save">저장</div>{{/if}}
				</div>
			</div>
		</div>
	</div>
	</script>
</div>
<!--//APP 버전 등록/수정 팝업-->
<script src="/js/app/operation/AMAVW001.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>