<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!--이벤트 목록 팝업-->
	<div id="event-list-popup" class="popup-wrap">
		<script id="tmpl-event-list-popup" type="text/ractive">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
				<h4 class="popup-title red">이벤트 목록</h4>
				<div class="popup-contents">
					<table class="basic-list event-mode">
						<colgroup>
							<col width="250">
							<col width="*">
							<col width="100">
							<col width="100">
							<col width="70">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order">매장명</th>
								<th class="no-order">타이틀</th>
								<th class="no-order">시작일</th>
								<th class="no-order">종료일</th>
								<th class="no-order">상태</th>
							</tr>
						</thead>
						<tbody>
							{{#eventList}}
								<tr class="cursor-p">
									<td class="ta-l" on-click="select:{{.}}">{{tnt_nm_ko}}</td>
									<td class="ellipsis ta-l" on-click="select:{{.}}">{{evt_titl}}</td>
									<td on-click="select:{{.}}">{{evt_strt_dt}}</td>
									<td on-click="select:{{.}}">{{evt_end_dt}}</td>
									<td class="green" on-click="select:{{.}}">진행</td>
								</tr>
							{{/eventList}}
						</tbody>
					</table>
					<div class="btn-wrap"></div>
				</div>
			</div>
		</div>
		</script>
	</div>
	<!--//이벤트 목록 팝업-->
<script src="/js/app/common/event-popup.js" type="text/javascript"></script>