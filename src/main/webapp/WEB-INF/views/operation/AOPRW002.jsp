<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="holiday" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-holiday" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">운영 관리 - 스타필드 비정기운영일 관리</h1>
		<table id="holiday-list" class="input-list">
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
			<tr>
				<th>비정기운영일 현황</th>
				<td>
					<table class="holiday-status">
						<colgroup>
							<col width="70px">
							<col width="*">
							<col width="70px">
						</colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>비정기운영일</th>
								<th>비정기운영일 수</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1월</td>
								<td>{{#holiday[year]['01']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['01']}}&nbsp</td>
								<td>{{#if holiday[year]['01']}}{{holiday[year]['01'].length}}{{else}}0{{/if}}일</td>
							</tr>
							<tr>
								<td>2월</td>
								<td>{{#holiday[year]['02']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['02']}}&nbsp</td>
								<td>{{#if holiday[year]['02']}}{{holiday[year]['02'].length}}{{else}}0{{/if}}일</td>
							</tr>
							<tr>
								<td>3월</td>
								<td>{{#holiday[year]['03']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['03']}}&nbsp</td>
								<td>{{#if holiday[year]['03']}}{{holiday[year]['03'].length}}{{else}}0{{/if}}일</td>
							</tr>
							<tr>
								<td>4월</td>
								<td>{{#holiday[year]['04']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['04']}}&nbsp</td>
								<td>{{#if holiday[year]['04']}}{{holiday[year]['04'].length}}{{else}}0{{/if}}일</td>
							</tr>
							<tr>
								<td>5월</td>
								<td>{{#holiday[year]['05']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['05']}}&nbsp</td>
								<td>{{#if holiday[year]['05']}}{{holiday[year]['05'].length}}{{else}}0{{/if}}일</td>
							</tr>
							<tr>
								<td>6월</td>
								<td>{{#holiday[year]['06']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['06']}}&nbsp</td>
								<td>{{#if holiday[year]['06']}}{{holiday[year]['06'].length}}{{else}}0{{/if}}일</td>
							</tr>
							<tr>
								<td>7월</td>
								<td>{{#holiday[year]['07']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['07']}}&nbsp</td>
								<td>{{#if holiday[year]['07']}}{{holiday[year]['07'].length}}{{else}}0{{/if}}일</td>
							</tr>
							<tr>
								<td>8월</td>
								<td>{{#holiday[year]['08']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['08']}}&nbsp</td>
								<td>{{#if holiday[year]['08']}}{{holiday[year]['08'].length}}{{else}}0{{/if}}일</td>
							</tr>
							<tr>
								<td>9월</td>
								<td>{{#holiday[year]['09']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['09']}}&nbsp</td>
								<td>{{#if holiday[year]['09']}}{{holiday[year]['09'].length}}{{else}}0{{/if}}일</td>
							</tr>
							<tr>
								<td>10월</td>
								<td>{{#holiday[year]['10']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['10']}}&nbsp</td>
								<td>{{#if holiday[year]['10']}}{{holiday[year]['10'].length}}{{else}}0{{/if}}일</td>
							</tr>
							<tr>
								<td>11월</td>
								<td>{{#holiday[year]['11']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['11']}}&nbsp</td>
								<td>{{#if holiday[year]['11']}}{{holiday[year]['11'].length}}{{else}}0{{/if}}일</td>
							</tr>
							<tr>
								<td>12월</td>
								<td>{{#holiday[year]['12']:i}}{{#if i != 0}},{{/if}} {{this}}{{/holiday[year]['12']}}&nbsp</td>
								<td>{{#if holiday[year]['12']}}{{holiday[year]['12'].length}}{{else}}0{{/if}}일</td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<th colspan="2">총 비정기운영일 수</th>
								<td>{{totalCount}}일</td>
							</tr>
						</tfoot>
					</table>
				</td>
			</tr>
		</table>
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="addHoliday">비정기운영일 추가</div>
			<div class="basic-btn gold" on-click="deleteHoliday">비정기운영일 삭제</div>
		</div>
	</div>
	</script>
</div>

<!--휴일 추가 팝업-->
<div id="holiday-reg-popup" class="popup-wrap">
</div>
<script id="tmpl-holiday-reg-popup" type="text/ractive">
	<div class="popup-container">
		<div class="popup">
			<div class="popup-header"><span class="btn-close" on-click="cancel">&#215;</span></div>
			<h4 class="popup-title red">스타필드 비정기운영일 추가</h4>
			<div class="popup-contents">
				<table class="basic-list">
					<colgroup>
						<col width="440">
						<col width="100">
						<col width="*">
					</colgroup>
					<thead>
						<tr>
							<th>비정기운영일 선택</th>
							<th class="no-border"></th>
							<th>추가 비정기운영일</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div id="holiday-calendar" class="calendar"></div></td>
							<td class="no-border">
								<div class="basic-btn" on-click="add"><i class="fa fa-caret-right" aria-hidden="true"></i></div>
								<div class="basic-btn" on-click="remove"><i class="fa fa-caret-left" aria-hidden="true"></i></div>
							</td>
							<td class="add-holiday">
								<div class="holiday-month-list">
									{{#dateList}}
										<label><input type="checkbox" name="deleteCheckbox" value="{{this}}">{{this}}</label>
									{{/dateList}}
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="cancel">취소</div>
					<div class="basic-btn gold" on-click="save">저장</div>
				</div>
			</div>
		</div>
	</div>
</script>


<!--휴일 삭제 팝업-->
<div id="holiday-del-popup" class="popup-wrap">
</div>
<script id="tmpl-holiday-del-popup" type="text/ractive">
	<div class="popup-container">
		<div class="popup">
			<div class="popup-header"><span class="btn-close" on-click="cancel">&#215;</span></div>
			<h4 class="popup-title red">스타필드 비정기운영일 삭제</h4>
			<div class="popup-contents">
				<table class="basic-list">
					<colgroup>
						<col width="*">
						<col width="*">
						<col width="*">
						<col width="70">
					</colgroup>
					<thead>
						<tr>
							<th class="no-order">년도</th>
							<th class="no-order">월</th>
							<th class="no-order">휴일</th>
							<th class="no-order">선택</th>
						</tr>
					</thead>
					<tbody>
						{{#dateList:i}}
						{{#dateList[i].day:j}}
						<tr>
							{{#if i == 0 && j == 0}}<td rowspan="{{length}}">{{year}}년</td>{{/if}}
							{{#if j == 0}}<td rowspan="{{dateList[i].day.length}}">{{dateList[i].month}}월</td>{{/if}}
							<td>{{dateList[i].day[j]}}일</td>
							<td><input name="groupCheckBox" type="checkbox" value="{{year}}.{{dateList[i].month}}.{{dateList[i].day[j]}}.{{i}}.{{j}}"></td>
						</tr>
						{{/dateList[i].day}}
						{{/dateList}}
					</tbody>
				</table>
				<div class="basic-btn gold" on-click="delete">선택 삭제</div>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="cancel">취소</div>
					<div class="basic-btn gold" on-click="save">저장</div>
				</div>
			</div>
		</div>
	</div>
	</script>
<!--//휴일 삭제 팝업-->
<script src="/js/app/operation/AOPRW002.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>