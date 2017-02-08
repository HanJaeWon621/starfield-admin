<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>사원 목록</title>
</head>
<body>
 
<h3>영업 테넌트 매핑</h3>
<div id="tenant-list" style="width:100%; border:1px;" >
<script id="tmpl-tenant-list" type="text/ractive">
	<div id="operation" class="contents-area">
		<div class="contents-wrap">
			<h1 class="menu-title">지도 관리 _ LBS Zone 매핑 _ 테넌트</h1>
			<div class="search-box">
				{{#tenant}}
				지점
				<div class="select-wrap" >
				<select id="bcn_cd" name="bcn_cd" value="{{bcn_cd}}">
					<option value="01">하남</option>
				</select>  
				</div>   
				매핑여부 <div class="select-wrap" >
				<select id="mapping_type" value="{{mapping_type}}">
					<option value="0">전체</option>
					<option value="Y">매핑완료</option>
					<option value="N">매핑미처리</option>
				</select>  
				</div>  
				<br/>
				테넌트명 검색
				<div class="select-wrap">
				<select id="tenant_type" name="tenant_type" value="{{tenant_type}}">
					<option value="0">전체</option>
					<option value="1">테넌트</option>
					<option value="2">LBS테넌트</option> 
				</select>
				</div>
				<div class="select-wrap">
				<select id="text_type" name="sh_text_type" value="{{sh_text_type}}">
					<option value="1">테넌트명</option> 
					<option value="2">호수</option>
				</select>
				</div>
				<div class="date-picker">
					
					<input type="text" name="tnt_nm_ko" id="tnt_nm_ko" value="{{tnt_nm_ko}}" placeholder="테넌트명 입력">
				</div>

				<span on-click="search" style="cursor: pointer">검색</span>
				<span on-click="search_init" style="cursor: pointer">초기화</span>  
				{{/tenant}}
			</div>  
			<div class="row">
				<div class="operation-holiday-month-wrap">
					<h3 id="mt">스타필드 테넌트 정보</h3>
					<table id="operation-holiday-month2" class="basic-list">
						<colgroup>
							<col width="100">
							<col width="100"> 
							<col width="190">
							<col width="*">
						</colgroup>
						<thead>  
							<tr>
								<th class="no-order">테넌트명</th>
								<th class="no-order">호수</th>
								<th class="no-order">X/Y좌표</th>
								<th class="no-order">선택</th>
							</tr>
						</thead>
					</table>
					<div class="operation-holiday-time-wrap">
						<table id="operation-holiday-time" class="basic-list">
						<colgroup>
								<col width="100">
								<col width="100"> 
								<col width="190">
								<col width="*">    
							</colgroup>
						<tbody>
							{{#tenantList:num}}
							<tr>
								<td>{{tnt_nm_ko}}</td>
								<td>{{room_num}}</td>
								<td>{{x_ctn_cord}}/<br/>{{y_ctn_cord}}</td>
								<td><input name="tntZone" type="radio" value="{{tnt_seq}}/{{room_num}}"></td>
							</tr>
							{{/tenantList}}
						</tbody>
					</table>
					</div>	
				</div>	
				<div class="operation-holiday-month-wrap3">
					<table id="operation-holiday-month" class="basic-list">
						<tbody>
							<tr>
								<td on-click="modifyzoneId"> 선택 Zone 매핑 </td>
							</tr> 
							<tr>
								<td on-click="deletezoneId"> 선택 Zone 매핑 정보 초기화 </td>
							</tr>
						</tbody> 
					</table> 
				</div> 
				<div class="operation-holiday-day-wrap2">
					<h3>LBS 테넌트 정보</h3>
					<table id="operation-holiday-day" class="basic-list">
						<colgroup>
							<col width="50"> 
							<col width="80">
							<col width="60">
							<col width="130">
							<col width="80">
							<col width="*">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order">선택</th> 
								<th class="no-order">테넌트 명</th>
								<th class="no-order">ZONE_ID</th>
								<th class="no-order">X/Y좌표</th>
								<th class="no-order">호수</th>  
								<th class="no-order">매핑여부</th>  
							</tr>
						</thead>
					</table>
					<div class="operation-holiday-time-wrap">
						<table id="operation-holiday-time" class="basic-list">
							<colgroup>
								<col width="50">
								<col width="80"> 
								<col width="60">
								<col width="130">
								<col width="80">
								<col width="*">  
							</colgroup>
							<tbody>
							{{#lbsList:num}}
								<tr>
									<td><input name="lbsZone" type="radio" value="{{zone_id}}/{{x_ctn_cord}}/{{y_ctn_cord}}/{{room_num}}"></td>
									<td>{{tnt_nm_ko}}</td>
									<td>{{zone_id}}</td>
									<td>{{x_ctn_cord}}/<br/>{{y_ctn_cord}}</td>
									<td>{{room_num}}</td>
									<td>{{check1}}</td>
								</tr>
							{{/lbsList}}
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
 </script>
</div>
	
	<script src="/js/app/coupon/ACPNW032.js" type="text/javascript"></script>
</body>
</html>