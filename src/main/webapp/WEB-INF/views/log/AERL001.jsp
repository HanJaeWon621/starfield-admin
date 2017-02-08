<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/gnb.jsp"%>
<script src="/js/lib/jquery-2.1.4.min.js" type="text/javascript"></script>
<div id="errorLog">
	<script id="errorLogTemplate" type="text/ractive">

		<div class="contents-wrap">
			<h1 class="menu-title">스타필드 - ERROR LOG VIEW</h1>
			<div class="list-top">
		</div>
		<div class="basic-btn" id="auto" status="off" on-click="autoRefresh:{{paging.cur_page}}">데이터 자동 갱신</div>
		<br />
		<br />
		<div id="status-message"></div>
		<br />
		<br />
		<div>	
		</div>
		<div class="select-wrap">
			<select id="severity-type">
				<optgroup label="심각도등급">
					<option value="0"selected="selected">SEVERITY ALL</option>
					<option value="1" selected="selected">SEVERITY 1</option>
					<option value="2" selected="selected">SEVERITY 2</option>
					<option value="3" selected="selected">SEVERITY 3</option>
					<option value="4" selected="selected">SEVERITY 4</option>
				</optgroup>
			</select>
		</div>

		<div class="search-input-wrap">
			<input class="search-input" type="text" name="SERVER_NM" id="search-server-name" placeholder="SERVER NAME" />
		</div>
		<div class="search-input-wrap">
			<input class="search-input" type="text" name="MODULE_NM" id="search-module-name" placeholder="MODULE NAME" />
		</div>
		<div class="search-input-wrap">
			<input class="search-input" type="text" name="METHOD_NM" id="search-method-name" placeholder="METHOD NAME" />
		</div>
		<br />
		<br />
	<div class="date-picker">
		<input id="sDate" name="datepicker" type="date">
	</div>
	<span class="dash">~</span>
		<span class="comment front gray"></span>
	<div class="date-picker">
		<input id="eDate" name="datepicker" type="date">
	</div>
		<span>
			<div class="basic-btn" on-click="goToFind">검색</div>
		</span>
	
		<br />
		<br />
		<table class="basic-list">
		<tr>
			<th on-click="sortColumn:rnum" order-type="desc">NO</th>
			<th on-click="sortColumn:DATETIME">DATETIME</th>
			<th on-click="sortColumn:SEVERITY" order-type="desc">SEVERITY</th>
			<th>ERR_CD</th>
			<th>SERVER_NM</th>
			<th>MODULE_NM</th>
			<th>METHOD_NM</th>
			<th>ERR_MSG</th>
		</tr>
		{{#errorList}}
		<tr class="word-spacing">
			<td>{{rnum}}</td>
			<td>{{DATETIME}}</td>
			<td>{{SEVERITY}}</td>
			<td>{{ERR_CD}}</td>
			<td>{{SERVER_NM}}</td>
			<td>{{MODULE_NM}}</td>
			<td>{{METHOD_NM}}</td>
			<td>{{ERR_MSG}}</td>
		</tr>
		<tr>
			<th colspan="8">STACK_TRACE</th>
		<tr/>
		<tr class="stack-trace">
			<td colspan="8">{{STACK_TRACE}}</td>
		</tr>
		<tr class="transparent">
			<td colspan="8"></td>
		</tr>
		{{/errorList}}
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
		</script>
</div>
<script src="/js/app/log/AERL001.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp"%>