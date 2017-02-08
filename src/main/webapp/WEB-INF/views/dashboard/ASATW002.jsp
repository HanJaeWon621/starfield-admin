<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="dashboard-member" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-dashboard-member" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">DASHBOARD - 회원 가입자 현황</h1>
		<div class="list-top">
			<button class="basic-btn" type="button" onclick="analytics();">Google Analytics</button>
			<div class="inline-list">
				<a class="basic-btn" href="/${bcn_cd}/dashboard">SUMMARY</a>
				<a class="basic-btn selected" href="/${bcn_cd}/dashboard/join">회원가입자</a>
				<a class="basic-btn" href="/${bcn_cd}/dashboard/appdown">앱다운로드</a>
				<a class="basic-btn" href="/${bcn_cd}/dashboard/appuser">앱 사용자현황</a>
				<a class="basic-btn" href="/${bcn_cd}/dashboard/visitor/L1/${cur_date}">방문자 히트맵</a>
			</div>
		</div>
		<div class="search-box lh50">
			<div class="comment"><b>현재 시간</b> <span id="current-time"><!--2016년 7월 11일 17시 17분--></span></div>
			<div class="graph-search-box">
				<label><input type="radio" name="{{type}}" value="YYYY.MM">월</label>
				<label><input type="radio" name="{{type}}" value="YYYY.MM.W">주</label>
				<label><input type="radio" name="{{type}}" value="YYYY.MM.DD">일</label>
				<span>조회기간</span>
				<div class="date-picker">
					<input type="text" name="datepicker" value="{{strt_date}}">
				</div>
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" value="{{end_date}}">
				</div>
				<button class="basic-btn gold" type="button" on-click="search">조회</button>
			</div>
		</div>
		<div class="list-top">
			<ul class="inline-list">
				<li class="dashboard-result red">기간내 <span class="red">{{acc_count}}</span>명 증가</li>
				<li><button type="button" on-click="download">엑셀다운</button></li>
			</ul>
		</div>
		<div class="graph-area">
			<div id="chart-join" class="chart"> 
		</div>
	</div>
	</script>
</div>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script src="/js/app/dashboard/ASATW002.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>