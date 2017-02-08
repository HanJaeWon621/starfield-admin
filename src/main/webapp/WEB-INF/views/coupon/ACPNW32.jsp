<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>사원 목록</title>
	<!-- <script src="/js/lib/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <script src="/js/lib/common.js" type="text/javascript"></script> -->
 <!--    <link rel="stylesheet" type="text/css" href="/css/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="/css/common.css"> -->
</head>
<body>
 
<h3>영업 테넌트 매핑</h3>
<div id="tenant-list" style="width:100%; border:1px;" >
<script id="tmpl-tenant-list" type="text/ractive">
<div class="contents-wrap">
검색 조건
{{#tenant}}
<select id="tenant_type" name="tenant_type" value="{{tenant_type}}">
	<option value="0">전체</option>
	<option value="1">테넌트</option>
	<option value="2">영업테넌트</option>
</select> 
<br/>
테넌트 명<input type="text" id="tnt_nm_ko" name="tnt_nm_ko" value="{{tnt_nm_ko}}">
지점<select id="bcn_cd" name="bcn_cd" value="{{bcn_cd}}">
	<option value="01">하남</option>
</select>      
<input type="button"  value="검색" on-click="search">
{{/tenant}}   
<br/>
총수<span id="tot_cnt"></span>매핑수<span id="busi_tnt_cnt"></span>
<div style="width:100%;">
	<div style="width:43%; float:left; border:0px solid #333; overflow:scroll; height:800px;">
	<table width=800 height=90 border=1 cellspacing=0>
		<th>테넌트명</th>
		<th>호수</th>
		<th>영업테넌트코드</th>
		<th>선택</th>
		{{#tenantList:num}}
			<tr>
				<td>{{tnt_nm_ko}}</td>
				<td>{{room_num}}</td>
				<td>{{busi_tnt_cd}}</td>
				<td><input name="tntSeq" type="radio" value="{{tnt_seq}}"></td>
			</tr>
		{{/tenantList}}
	</table>
	</div>
    <div style="width:9%; float:left; border:0px solid #333; margin-top:380px;">
	<table width=80 height=50 border=1 cellspacing=0>
		<tr>
			<td on-click="modifyBusiTntSeq">선택테넌트매핑</td>
		</tr>
		<tr> 
			<td on-click="deleteBusiTntSeq">선택테넌트 매핑정보 초기화</td> 
		</tr>
	</table>
	</div>
    <div style="width:43%; float:left; border:0px solid #333; overflow:scroll; height:800px;">
	<table width=790 height=90 border=1 cellspacing=0>
		<th>선택</th> 
		<th>영업테넌트코드</th>
		<th>테넌트명</th>
		<th>호수</th>
		<th>매핑여부</th>  
		{{#salesTenantList:num}}
			<tr>
				<td><input name="salesTntSeq" type="radio" value="{{busi_tnt_cd}}/{{busi_tnt_seq}}"></td>
				<td>{{busi_tnt_cd}}</td>
				<td>{{busi_tnt_nm_ko}}</td>
				<td>{{room_num}}</td>
				<td>{{check2}}</td>
			</tr> 
		{{/salesTenantList}}
	</table>
	</div>
</div>
</div>
 </script>
</div>
	
	<script src="/js/app/coupon/ACPNW031.js" type="text/javascript"></script>
</body>
</html>