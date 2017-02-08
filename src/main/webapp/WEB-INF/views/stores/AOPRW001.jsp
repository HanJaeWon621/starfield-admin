<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="operation" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-operation" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">점포 관리 - 스타필드 운영정보 관리</h1>
		<h2>기본정보</h2>
		<table id="operation-basic-info" class="input-list">
			<tr>
				<th>지점</th>
				<td>스타필드 하남</td>
			</tr>
			<tr class="transparent"></tr>
			<tr>
				<th>운영시간(평일)</th>
				<td>
					<div class="select-wrap time">
						<select class="time-selector" value="{{operation.norm_day_open_hr_min}}">
							<option value="0000">00:00</option>
							<option value="0030">00:30</option>
							<option value="0100">01:00</option>
							<option value="0130">01:30</option>
							<option value="0200">02:00</option>
							<option value="0230">02:30</option>
							<option value="0300">03:00</option>
							<option value="0330">03:30</option>
							<option value="0400">04:00</option>
							<option value="0430">04:30</option>
							<option value="0500">05:00</option>
							<option value="0530">05:30</option>
							<option value="0600">06:00</option>
							<option value="0630">06:30</option>
							<option value="0700">07:00</option>
							<option value="0730">07:30</option>
							<option value="0800">08:00</option>
							<option value="0830">08:30</option>
							<option value="0900">09:00</option>
							<option value="0930">09:30</option>
							<option value="1000">10:00</option>
							<option value="1030">10:30</option>
							<option value="1100">11:00</option>
							<option value="1130">11:30</option>
							<option value="1200">12:00</option>
							<option value="1230">12:30</option>
							<option value="1300">13:00</option>
							<option value="1330">13:30</option>
							<option value="1400">14:00</option>
							<option value="1430">14:30</option>
							<option value="1500">15:00</option>
							<option value="1530">15:30</option>
							<option value="1600">16:00</option>
							<option value="1630">16:30</option>
							<option value="1700">17:00</option>
							<option value="1730">17:30</option>
							<option value="1800">18:00</option>
							<option value="1830">18:30</option>
							<option value="1900">19:00</option>
							<option value="1930">19:30</option>
							<option value="2000">20:00</option>
							<option value="2030">20:30</option>
							<option value="2100">21:00</option>
							<option value="2130">21:30</option>
							<option value="2200">22:00</option>
							<option value="2230">22:30</option>
							<option value="2300">23:00</option>
							<option value="2330">23:30</option>
						</select>
					</div>
					<span class="dash">~</span>
					<div class="select-wrap time">
						<select class="time-selector" value="{{operation.norm_day_end_hr_min}}">
							<option value="0000">00:00</option>
							<option value="0030">00:30</option>
							<option value="0100">01:00</option>
							<option value="0130">01:30</option>
							<option value="0200">02:00</option>
							<option value="0230">02:30</option>
							<option value="0300">03:00</option>
							<option value="0330">03:30</option>
							<option value="0400">04:00</option>
							<option value="0430">04:30</option>
							<option value="0500">05:00</option>
							<option value="0530">05:30</option>
							<option value="0600">06:00</option>
							<option value="0630">06:30</option>
							<option value="0700">07:00</option>
							<option value="0730">07:30</option>
							<option value="0800">08:00</option>
							<option value="0830">08:30</option>
							<option value="0900">09:00</option>
							<option value="0930">09:30</option>
							<option value="1000">10:00</option>
							<option value="1030">10:30</option>
							<option value="1100">11:00</option>
							<option value="1130">11:30</option>
							<option value="1200">12:00</option>
							<option value="1230">12:30</option>
							<option value="1300">13:00</option>
							<option value="1330">13:30</option>
							<option value="1400">14:00</option>
							<option value="1430">14:30</option>
							<option value="1500">15:00</option>
							<option value="1530">15:30</option>
							<option value="1600">16:00</option>
							<option value="1630">16:30</option>
							<option value="1700">17:00</option>
							<option value="1730">17:30</option>
							<option value="1800">18:00</option>
							<option value="1830">18:30</option>
							<option value="1900">19:00</option>
							<option value="1930">19:30</option>
							<option value="2000">20:00</option>
							<option value="2030">20:30</option>
							<option value="2100">21:00</option>
							<option value="2130">21:30</option>
							<option value="2200">22:00</option>
							<option value="2230">22:30</option>
							<option value="2300">23:00</option>
							<option value="2330">23:30</option>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<th>운영시간(주말 - 금,토,일)</th>
				<td>
					<div class="select-wrap time">
						<select class="time-selector" value="{{operation.weekend_open_hr_min}}">
							<option value="0000">00:00</option>
							<option value="0030">00:30</option>
							<option value="0100">01:00</option>
							<option value="0130">01:30</option>
							<option value="0200">02:00</option>
							<option value="0230">02:30</option>
							<option value="0300">03:00</option>
							<option value="0330">03:30</option>
							<option value="0400">04:00</option>
							<option value="0430">04:30</option>
							<option value="0500">05:00</option>
							<option value="0530">05:30</option>
							<option value="0600">06:00</option>
							<option value="0630">06:30</option>
							<option value="0700">07:00</option>
							<option value="0730">07:30</option>
							<option value="0800">08:00</option>
							<option value="0830">08:30</option>
							<option value="0900">09:00</option>
							<option value="0930">09:30</option>
							<option value="1000">10:00</option>
							<option value="1030">10:30</option>
							<option value="1100">11:00</option>
							<option value="1130">11:30</option>
							<option value="1200">12:00</option>
							<option value="1230">12:30</option>
							<option value="1300">13:00</option>
							<option value="1330">13:30</option>
							<option value="1400">14:00</option>
							<option value="1430">14:30</option>
							<option value="1500">15:00</option>
							<option value="1530">15:30</option>
							<option value="1600">16:00</option>
							<option value="1630">16:30</option>
							<option value="1700">17:00</option>
							<option value="1730">17:30</option>
							<option value="1800">18:00</option>
							<option value="1830">18:30</option>
							<option value="1900">19:00</option>
							<option value="1930">19:30</option>
							<option value="2000">20:00</option>
							<option value="2030">20:30</option>
							<option value="2100">21:00</option>
							<option value="2130">21:30</option>
							<option value="2200">22:00</option>
							<option value="2230">22:30</option>
							<option value="2300">23:00</option>
							<option value="2330">23:30</option>
						</select>
					</div>
					<span class="dash">~</span>
					<div class="select-wrap time">
						<select class="time-selector" value="{{operation.weekend_end_hr_min}}">
							<option value="0000">00:00</option>
							<option value="0030">00:30</option>
							<option value="0100">01:00</option>
							<option value="0130">01:30</option>
							<option value="0200">02:00</option>
							<option value="0230">02:30</option>
							<option value="0300">03:00</option>
							<option value="0330">03:30</option>
							<option value="0400">04:00</option>
							<option value="0430">04:30</option>
							<option value="0500">05:00</option>
							<option value="0530">05:30</option>
							<option value="0600">06:00</option>
							<option value="0630">06:30</option>
							<option value="0700">07:00</option>
							<option value="0730">07:30</option>
							<option value="0800">08:00</option>
							<option value="0830">08:30</option>
							<option value="0900">09:00</option>
							<option value="0930">09:30</option>
							<option value="1000">10:00</option>
							<option value="1030">10:30</option>
							<option value="1100">11:00</option>
							<option value="1130">11:30</option>
							<option value="1200">12:00</option>
							<option value="1230">12:30</option>
							<option value="1300">13:00</option>
							<option value="1330">13:30</option>
							<option value="1400">14:00</option>
							<option value="1430">14:30</option>
							<option value="1500">15:00</option>
							<option value="1530">15:30</option>
							<option value="1600">16:00</option>
							<option value="1630">16:30</option>
							<option value="1700">17:00</option>
							<option value="1730">17:30</option>
							<option value="1800">18:00</option>
							<option value="1830">18:30</option>
							<option value="1900">19:00</option>
							<option value="1930">19:30</option>
							<option value="2000">20:00</option>
							<option value="2030">20:30</option>
							<option value="2100">21:00</option>
							<option value="2130">21:30</option>
							<option value="2200">22:00</option>
							<option value="2230">22:30</option>
							<option value="2300">23:00</option>
							<option value="2330">23:30</option>
						</select>
					</div>
				</td>
			</tr>
		</table>
		<h2>비정기운영일 개별 운영시간 관리</h2>
		<table id="operation-holiday-year" class="input-list">
			<tr>
				<th>연도</th>
				<td>
					<div class="select-wrap">
						<select id="holiday-year-selector" value="{{year}}">
							<option value="2016">2016</option>
							<option value="2017">2017</option>
							<option value="2018">2018</option>
							<option value="2019">2019</option>
							<option value="2020">2020</option>
							<option value="2021">2021</option>
							<option value="2022">2022</option>
							<option value="2023">2023</option>
							<option value="2024">2024</option>
							<option value="2025">2025</option>
							<option value="2026">2026</option>
						</select>
					</div>
				</td>
			</tr>
		</table>
		<div class="row">
			<div class="operation-holiday-month-wrap">
				<h3>월별 비정기운영일현황</h3>
				<table id="operation-holiday-month" class="basic-list">
					<colgroup>
						<col width="70">
						<col width="*">
					</colgroup>
					<thead>
						<tr>
							<th class="no-order">구분</th>
							<th class="no-order">비정기운영일</th>
						</tr>
					</thead>
					<tbody>
						{{#monthList:i}}
						<tr>
							<td>{{i+1}}월</td>
							<td>{{#starFieldHolidayList[year][monthList[i]]:j}}{{#if j != 0}},&nbsp{{/if}}{{#if open_hr_min == operation.weekend_open_hr_min && end_hr_min == operation.weekend_end_hr_min}}{{day}}{{else}}<span class="red">{{day}}</span>{{/if}}{{/starFieldHolidayList[year][monthList[i]]}}&nbsp</td>
						</tr>
						{{/monthList}}
					</tbody>
				</table>
			</div>
			<div class="operation-holiday-day-wrap">
				<h3>비정기운영일 개별 운영시간</h3>
				<table id="operation-holiday-day" class="basic-list">
					<colgroup>
						<col width="70">
						<col width="100">
						<col width="*">
					</colgroup>
					<thead>
						<tr>
							<th class="no-order">구분</th>
							<th class="no-order">비정기운영일</th>
							<th class="no-order">운영시간</th>
						</tr>
					</thead>
				</table>
				<div class="operation-holiday-time-wrap">
					<table id="operation-holiday-time" class="basic-list">
						<colgroup>
							<col width="70">
							<col width="100">
							<col width="*">
						</colgroup>
						<tbody>
							{{#monthList:i}}
							{{#starFieldHolidayList[year][monthList[i]]:j}}
							<tr>
								{{#if j == 0}}
									<td class="month" rowspan="{{starFieldHolidayList[year][monthList[i]].length}}">{{i+1}}월</td>
								{{/if}}
								
								<td>{{#if open_hr_min == operation.weekend_open_hr_min && end_hr_min == operation.weekend_end_hr_min}}{{day}}일{{else}}<span class="red">{{day}}일</span>{{/if}}</td>
								<td>
									<div class="select-wrap time">
										<select class="time-selector" value="{{open_hr_min}}">
											<option value="0000">00:00</option>
											<option value="0030">00:30</option>
											<option value="0100">01:00</option>
											<option value="0130">01:30</option>
											<option value="0200">02:00</option>
											<option value="0230">02:30</option>
											<option value="0300">03:00</option>
											<option value="0330">03:30</option>
											<option value="0400">04:00</option>
											<option value="0430">04:30</option>
											<option value="0500">05:00</option>
											<option value="0530">05:30</option>
											<option value="0600">06:00</option>
											<option value="0630">06:30</option>
											<option value="0700">07:00</option>
											<option value="0730">07:30</option>
											<option value="0800">08:00</option>
											<option value="0830">08:30</option>
											<option value="0900">09:00</option>
											<option value="0930">09:30</option>
											<option value="1000">10:00</option>
											<option value="1030">10:30</option>
											<option value="1100">11:00</option>
											<option value="1130">11:30</option>
											<option value="1200">12:00</option>
											<option value="1230">12:30</option>
											<option value="1300">13:00</option>
											<option value="1330">13:30</option>
											<option value="1400">14:00</option>
											<option value="1430">14:30</option>
											<option value="1500">15:00</option>
											<option value="1530">15:30</option>
											<option value="1600">16:00</option>
											<option value="1630">16:30</option>
											<option value="1700">17:00</option>
											<option value="1730">17:30</option>
											<option value="1800">18:00</option>
											<option value="1830">18:30</option>
											<option value="1900">19:00</option>
											<option value="1930">19:30</option>
											<option value="2000">20:00</option>
											<option value="2030">20:30</option>
											<option value="2100">21:00</option>
											<option value="2130">21:30</option>
											<option value="2200">22:00</option>
											<option value="2230">22:30</option>
											<option value="2300">23:00</option>
											<option value="2330">23:30</option>
										</select>
									</div>
									<span>~</span>
									<div class="select-wrap time">
										<select class="time-selector" value="{{end_hr_min}}">
											<option value="0000">00:00</option>
											<option value="0030">00:30</option>
											<option value="0100">01:00</option>
											<option value="0130">01:30</option>
											<option value="0200">02:00</option>
											<option value="0230">02:30</option>
											<option value="0300">03:00</option>
											<option value="0330">03:30</option>
											<option value="0400">04:00</option>
											<option value="0430">04:30</option>
											<option value="0500">05:00</option>
											<option value="0530">05:30</option>
											<option value="0600">06:00</option>
											<option value="0630">06:30</option>
											<option value="0700">07:00</option>
											<option value="0730">07:30</option>
											<option value="0800">08:00</option>
											<option value="0830">08:30</option>
											<option value="0900">09:00</option>
											<option value="0930">09:30</option>
											<option value="1000">10:00</option>
											<option value="1030">10:30</option>
											<option value="1100">11:00</option>
											<option value="1130">11:30</option>
											<option value="1200">12:00</option>
											<option value="1230">12:30</option>
											<option value="1300">13:00</option>
											<option value="1330">13:30</option>
											<option value="1400">14:00</option>
											<option value="1430">14:30</option>
											<option value="1500">15:00</option>
											<option value="1530">15:30</option>
											<option value="1600">16:00</option>
											<option value="1630">16:30</option>
											<option value="1700">17:00</option>
											<option value="1730">17:30</option>
											<option value="1800">18:00</option>
											<option value="1830">18:30</option>
											<option value="1900">19:00</option>
											<option value="1930">19:30</option>
											<option value="2000">20:00</option>
											<option value="2030">20:30</option>
											<option value="2100">21:00</option>
											<option value="2130">21:30</option>
											<option value="2200">22:00</option>
											<option value="2230">22:30</option>
											<option value="2300">23:00</option>
											<option value="2330">23:30</option>
										</select>
									</div>
								</td>
							</tr>
							{{/starFieldHolidayList[year][monthList[i]]}}
							{{/monthList}}
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="comment">* 공통 영업시간(주말)이 적용되지 않은 비정기운영일은 빨간색으로 출력됩니다.</div>
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="save">저장</div>
		</div>
	</div>
	</script>
</div>
<script src="/js/app/stores/AOPRW001.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>