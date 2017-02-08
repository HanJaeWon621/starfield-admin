<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!--쿠폰 목록 팝업-->
	<div id="coupon-list-popup" class="popup-wrap">
		<script id="tmpl-coupon-list-popup" type="text/ractive">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
				<h4 class="popup-title red">쿠폰 목록</h4>
				<div class="popup-contents">
					<table class="basic-list">
						<colgroup>
							<col width="250">
							<col width="*">
							<col width="150">
							<col width="150">
							<col width="70">
						</colgroup>
						<thead>
							<tr>
								<th class="no-order">매장명</th>
								<th class="no-order">타이틀</th>
								<th class="no-order">발급기간</th>
								<th class="no-order">유효기간</th>
								<th class="no-order">노출현황</th>
							</tr>
						</thead>
						<tbody>
							{{#couponList}}
							<tr>
								<td class="ta-l" on-click="select:{{.}}">{{tnt_nm_ko}}</td>
								<td class="ellipsis ta-l" on-click="select:{{.}}">{{cp_titl}}</td>
								<td on-click="select:{{.}}">{{cp_iss_strt_dt}}~{{cp_iss_end_dt}}</td>
								<td on-click="select:{{.}}">{{cp_act_strt_dt}}~{{cp_act_end_dt}}</td>
								<td class="green" on-click="select:{{.}}">Y</td>
							</tr>
							{{/couponList}}
						</tbody>
					</table>
					<div class="btn-wrap"></div>
				</div>
			</div>
		</div>
		</script>
	</div>
	<!--//쿠폰 목록 팝업-->
<script src="/js/app/common/coupon-popup.js" type="text/javascript"></script>