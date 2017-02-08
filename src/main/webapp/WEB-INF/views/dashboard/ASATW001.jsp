<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="dashboard-summary" class="contents-area" data-bcn_cd="${bcn_cd}">
	<div class="contents-wrap">
		<h1 class="menu-title">DASHBOARD - SUMMARY</h1>
		<div class="list-top">
			<button class="basic-btn" type="button" onclick="analytics();">Google Analytics</button>
			<div class="inline-list">
				<a class="basic-btn selected" href="/${bcn_cd}/dashboard">SUMMARY</a>
				<a class="basic-btn" href="/${bcn_cd}/dashboard/join">회원가입자</a>
				<a class="basic-btn" href="/${bcn_cd}/dashboard/appdown">앱다운로드</a>
				<a class="basic-btn" href="/${bcn_cd}/dashboard/appuser">앱 사용자현황</a>
				<a class="basic-btn" href="/${bcn_cd}/dashboard/visitor/L1/${cur_date}">방문자 히트맵</a>
			</div>
		</div>
		<div class="search-box lh50">
			<div class="comment"><b>현재 시간</b> <span id="current-time"><!-- 2016년 7월 11일 17시 17분 --></span></div>
		</div>
		<div class="row">
			<div class="box">
				<div class="title-area">금일 회원 가입자 현황</div>
				<div id="join-today" class="count-area"></div>
			</div>
			<div class="box">
				<div class="title-area">당월 회원 가입자 현황</div>
				<div id="join-month" class="count-area"></div>
			</div>
			<div class="box">
				<div class="title-area">누적 회원 가입자 현황</div>
				<div id="join-accum" class="count-area"></div>
			</div>
			<div class="box">
				<div class="title-area">앱 다운로드 현황(누적)</div>
				<div id="app-down-accum" class="count-area"></div>
			</div>
		</div>
	</div>
</div>
<script src="/js/app/dashboard/ASATW001.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>