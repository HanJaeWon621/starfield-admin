<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<script src="/js/lib/beemap-indoor-1.0.min.js"></script>
<style>
 .my-image { 
    max-width: 100%;
    height: auto;
}   
</style>
<script src="/js/lib/heatmap.js"></script>
<div id="heatmap" class="contents">
<script id="tmpl-heatmap" type="text/ractive">
<div id="dashboard-app-user" class="contents-area">
	<div class="contents-wrap">
		<h1 class="menu-title">DASHBOARD - 스타필드 내 앱 사용자 분포도</h1>
		<div class="list-top">
			<button class="basic-btn" type="button" onclick="analytics();">Google Analytics</button>
			<div class="inline-list">
				<a class="basic-btn" href="/${bcn_cd}/dashboard">SUMMARY</a>
				<a class="basic-btn" href="/${bcn_cd}/dashboard/join">회원가입자</a>
				<a class="basic-btn" href="/${bcn_cd}/dashboard/appdown">앱다운로드</a>
				<a class="basic-btn" href="/${bcn_cd}/dashboard/appuser">앱 사용자현황</a>
				<a class="basic-btn selected" href="/${bcn_cd}/dashboard/visitor/L1/${cur_date}">방문자 히트맵</a>
			</div>
		</div>
		<div class="search-box lh50">
			<div class="comment"><b>현재 시간</b> <span id="current-time">2016년 7월 11일 17시 17분</span></div>
			<div class="graph-search-box">
				<span>층</span>
				<div class="select-wrap">
					<select id="sh_floor" on-change="changeFloor" value="{{filter.sh_floor}}">
						<option value="L1">L1</option>
						<option value="L2">L2</option>
						<option value="L3">L3</option> 
						<option value="L4">L4</option>
						<option value="B1">B1</option>
						<option value="B2">B2</option>
						<option value="B3">B3</option>
					</select>
				</div> 
				<span >조회일자</span>
				<div class="date-picker" > 
					<input type="text" name="datepicker" id="heat_dt" value="{{filter.sh_dt}}">
				</div>  
				<button class="basic-btn gold" type="button" on-click="search">조회</button>
			</div>  
		</div>  
		<div class="row graph-area1" on-click="zoom" id="heatmapContainer"  >
			<img class="my-image" id="floor_img" src="/images/L1.png"/>
		</div> 
	</div> 
</div> 
</script>
</div>
<script src="/js/app/dashboard/ASATW005.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>