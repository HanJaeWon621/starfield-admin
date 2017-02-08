<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="admin-main-news" class="contents-area" data-bcn_cd="${bcn_cd}">
		<script id="tmpl-main-news" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">메인 관리 - STARFIELD NOW 관리 - 목록</h1>
			<div class="list-top">
				<ul class="inline-list">
					<li on-click="create">그룹 등록</li><li on-click="delete">선택 삭제</li><li on-click="redis">즉시 반영</li>
				</ul>
			</div>
			<table id="admin-main-news-list" class="basic-list">
				<colgroup>
					<col width="80">
					<col width="150">
					<col width="150">
					<col width="120">
					<col width="150">
					<col width="*">
					<col width="80">
				</colgroup>
				<thead>
					<tr>
						<th {{#if(hashObject.sort_name == 'rnum')}}class="selected"{{/if}} on-click="sort:rnum">NO</th>
						<th {{#if(hashObject.sort_name == 'what_post_strt')}}class="selected"{{/if}} on-click="sort:what_post_strt">게시시작</th>
						<th {{#if(hashObject.sort_name == 'what_post_end')}}class="selected"{{/if}} on-click="sort:what_post_end">게시종료</th>
						<th {{#if(hashObject.sort_name == 'what_sts')}}class="selected"{{/if}} on-click="sort:what_sts">게재상태</th>
						<th {{#if(hashObject.sort_name == 'what_group_titl')}}class="selected"{{/if}} on-click="sort:what_group_titl">Title</th>
						<th {{#if(hashObject.sort_name == 'what_titl')}}class="selected"{{/if}} on-click="sort:what_titl">Brands</th>
						<th class="no-order">선택</th>
					</tr>
				</thead>
				<tbody>
					{{#list}}
					<tr class="cursor-p">
						<td on-click="detail:{{what_group_seq}}">{{rnum}}</td>
						<td on-click="detail:{{what_group_seq}}">{{what_post_strt}}</td>
						<td on-click="detail:{{what_group_seq}}">{{what_post_end}}</td>
						<td on-click="detail:{{what_group_seq}}"><span class="green {{#if(what_sts == 1)}}on{{/if}}">게재중</span></td>
						<td class="ellipsis ta-l" on-click="detail:{{what_group_seq}}">{{what_group_titl}}</td>
						<td class="ellipsis ta-l" on-click="detail:{{what_group_seq}}">{{what_titl}}</td>
						<td class="cursor-d"><input name="groupCheckBox" type="checkbox" value="{{what_group_seq}}"></td>
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
<script src="/js/app/main/ACMSW012.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>