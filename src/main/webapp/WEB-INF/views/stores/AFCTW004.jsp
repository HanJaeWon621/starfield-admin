<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="facility-reg" class="contents-area">
		<div class="contents-wrap" id="facility-reg-wrap">
		<script id="tmpl-facility-reg-wrap" type="text/ractive">
			<h1 class="menu-title">매장 관리 - 편의시설 관리 - 편의시설 등록 / 수정</h1>
			<table id="facility-reg-list" class="input-list">
				<tr>
					<th>편의시설 그룹</th>
					<td>{{facilityGroup.conv_faci_nm_ko}}</td>
				</tr>
				<tr>
					<th>편의시설명</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설명을 입력하세요" value="{{facility.conv_faci_nm_ko}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>층수</th>
					<td>
						<div class="select-wrap">
							<select value="{{facility.fl}}">
								<option value="4F">4F</option>
								<option value="3F">3F</option>
								<option value="2F">2F</option>
								<option value="1F" selected>1F</option>
								<option value="B1">B1</option>
								<option value="B2">B2</option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<th>호수</th>
					<td>
						<div class="text-input-wrap">
							<input on-change="checkByte:10" class="text-input" type="text" placeholder="호수를 입력하세요" value="{{facility.room_num}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>ZONE ID</th>
					<td>{{facility.zone_id}}</td>
				</tr>
				<tr>
					<th>MAP ID</th>
					<td>{{facility.map_id}}</td>
				</tr>
				<tr>
					<th>X/Y 좌표</th>
					<td>{{facility.x_ctn_cord}} / {{facility.y_ctn_cord}}</td>
				</tr>
				<tr>
					<th>POI 레벨</th>
					<td>
						{{#if facilityGroup.faci_type.codeCd == 'N'}}
						<div class="select-wrap">
							<select value="{{facility.poi_lev}}">
								<option value="1" selected>1</option>
								<option value="2">2</option>
								<option value="3">3</option>
							</select>
						</div>
						{{else}}
						사용안함
						{{/if}}
					</td>
				</tr>
				<tr>
					<th>노출여부</th>
					<td  on-change="changeSts">
						<label><input type="radio" name="{{facility.sts}}" value="0" checked="checked">미노출</label>
						<label><input type="radio" name="{{facility.sts}}" value="1">노출</label>
					</td>
				</tr>
			</table>
			<div class="btn-wrap">
				<div class="basic-btn gold" on-click="cancel">취소</div>
				<div class="basic-btn gold" on-click="regFacility">저장</div>
			</div>
			</script>
		</div>
	</div>
	<script src="/js/app/stores/AFCTW004.js" type="text/javascript"></script>
<script type="text/javascript">
var conv_faci_seq = '${conv_faci_seq}' || '';
var conv_faci_dtl_seq = '${conv_faci_dtl_seq}' || '';
</script>
<%@ include file="../common/footer.jsp" %>