<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="admin-main-hero" class="contents-area" data-bcn_cd="${bcn_cd}">
		<script id="tmpl-admin-main-hero" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">메인 관리 - 상단 히어로 배너 관리 - 그룹 목록</h1>
			<div class="list-top">
				<ul class="inline-list">
					<li on-click="create">그룹 추가</li>
					<li on-click="delete">선택 삭제</li>
					<li on-click="redis">즉시 반영</li>
				</ul>
			</div>
			<table id="admin-main-hero-group-list" class="basic-list">
				<colgroup>
					<col width="50">
					<col width="150">
					<col width="150">
					<col width="100">
					<col width="70">
					<col width="*">
					<col width="*">
					<col width="50">
				</colgroup>
				<thead>
					<tr>
						<th {{#if(hashObject.sort_name == 'rnum')}}class="selected"{{/if}} on-click="sort:rnum">NO</th>
						<th {{#if(hashObject.sort_name == 'bn_post_strt')}}class="selected"{{/if}} on-click="sort:bn_post_strt">게시시작</th>
						<th {{#if(hashObject.sort_name == 'bn_post_end')}}class="selected"{{/if}} on-click="sort:bn_post_end">게시종료</th>
						<th {{#if(hashObject.sort_name == 'bn_div')}}class="selected"{{/if}} on-click="sort:bn_div">게재구분</th>
						<th {{#if(hashObject.sort_name == 'bn_sts')}}class="selected"{{/if}} on-click="sort:bn_sts">게재상태</th>
						<th {{#if(hashObject.sort_name == 'bn_group_titl')}}class="selected"{{/if}} on-click="sort:bn_group_titl">그룹타이틀</th>
						<th {{#if(hashObject.sort_name == 'bn_titl')}}class="selected"{{/if}} on-click="sort:bn_titl">브랜드명 / 매장명</th>
						<th class="no-order">선택</th>
					</tr>
				</thead>
				<tbody>
					{{#list}}
						<tr class="cursor-p">
							<td on-click="detail:{{bn_group_seq}}">{{rnum}}</td>
							<td on-click="detail:{{bn_group_seq}}">{{bn_post_strt}}</td>
							<td on-click="detail:{{bn_group_seq}}">{{bn_post_end}}</td>
							<td on-click="detail:{{bn_group_seq}}">{{#if(bn_div == 1)}}WEB{{else}}APP{{/if}}</td>
							<td class="green" on-click="detail:{{bn_group_seq}}">{{#if(bn_sts == 1)}}게재중{{/if}}</td>
							<td class="ellipsis ta-l" on-click="detail:{{bn_group_seq}}">{{bn_group_titl}}</td>
							<td class="ellipsis ta-l" on-click="detail:{{bn_group_seq}}">{{bn_titl}}</td>
							<td class="cursor-d"><input name="groupCheckBox" type="checkbox" value="{{bn_group_seq}}"></td>
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
<script src="/js/app/main/ACMSW010.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>