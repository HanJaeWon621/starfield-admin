<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="facility-group-reg" class="contents-area">
		<div class="contents-wrap" id="facility-group-reg-wrap">
		<script id="tmpl-facility-group-reg" type="text/ractive">
			<h1 class="menu-title">매장 관리 - 편의시설 관리 - 그룹 등록 / 수정</h1>
			<table id="facility-group-reg-list" class="input-list">
				<tr class="normal-show">
					<th>시설구분</th>
					<td>
						<div class="select-wrap">
							<select value="{{facilityGroup.faci_type.codeCd}}">
								<c:forEach var="option" items="${facilityType}">				
								<option value="${option.codeCd}">${option.codeNm}</option>
								</c:forEach>
							</select>
						</div>
					</td>
				</tr>
				<tr class="normal-show">
					<th>편의시설 그룹명(국문)</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 그룹명(국문)을 입력하세요" value="{{facilityGroup.conv_faci_nm_ko}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>편의시설 그룹명(영문)</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 그룹명(영문)을 입력하세요" value="{{facilityGroup.conv_faci_nm_en}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>편의시설 그룹명(중문)</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 그룹명(중문)을 입력하세요" value="{{facilityGroup.conv_faci_nm_cn}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>편의시설 그룹명(일문)</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 그룹명(일문)을 입력하세요" value="{{facilityGroup.conv_faci_nm_jp}}">
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr>
					<th>이용안내 대표(국문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 대표(국문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_ko}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 대표(영문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 대표(영문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_en}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 대표(중문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 대표(중문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_cn}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 대표(일문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 대표(일문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_jp}}">
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr>
					<th>이용안내 서브1(국문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브1(국문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_ko1}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브1(영문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브1(영문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_en1}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브1(중문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브1(중문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_cn1}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브1(일문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브1(일문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_jp1}}">
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr>
					<th>이용안내 서브2(국문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브2(국문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_ko2}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브2(영문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브2(영문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_en2}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브2(중문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브2(중문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_cn2}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브2(일문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브2(일문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_jp2}}">
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr>
					<th>이용안내 서브3(국문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브3(국문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_ko3}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브3(영문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브3(영문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_en3}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브3(중문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브3(중문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_cn3}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브3(일문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브3(일문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_jp3}}">
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr>
					<th>이용안내 서브4(국문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브4(국문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_ko4}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브4(영문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브4(영문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_en4}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브4(중문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브4(중문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_cn4}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브4(일문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브4(일문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_jp4}}">
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr>
					<th>이용안내 서브5(국문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브5(국문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_ko5}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브5(영문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브5(영문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_en5}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브5(중문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브5(중문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_cn5}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이용안내 서브5(일문)</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="편의시설 이용안내 서브5(일문)을 입력하세요" value="{{facilityGroup.conv_faci_desc_dtl_jp5}}">
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr>
					<th>운영시간(평일)</th>
					<td>
						<div class="select-wrap time">
							<select class="time-selector" value="{{facilityGroup.reps_open_hr_min}}">
								{{#hourList}}
								<option value="{{.cd}}">{{.nm}}</option>
								{{/hourList}}
							</select>
						</div>
						<span>~</span>
						<div class="select-wrap time">
							<select class="time-selector" value="{{facilityGroup.reps_end_hr_min}}">
								{{#hourList}}
								<option value="{{.cd}}">{{.nm}}</option>
								{{/hourList}}
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<th>운영시간(휴일)</th>
					<td>
						<div class="select-wrap time">
							<select class="time-selector" value="{{facilityGroup.irgu_open_hr_min}}">
								{{#hourList}}
								<option value="{{.cd}}">{{.nm}}</option>
								{{/hourList}}
							</select>
						</div>
						<span>~</span>
						<div class="select-wrap time">
							<select class="time-selector" value="{{facilityGroup.irgu_end_hr_min}}">
								{{#hourList}}
								<option value="{{.cd}}">{{.nm}}</option>
								{{/hourList}}
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<th>대표전화</th>
					<td>
						<div class="tel-input-wrap">
							<input on-change="checkByte:3" class="tel-input" type="text" value="{{facilityGroup.reps_tel_num1}}">
							<div>-</div>
							<input on-change="checkByte:4" class="tel-input" type="text" value="{{facilityGroup.reps_tel_num2}}">
							<div>-</div>
							<input on-change="checkByte:4" class="tel-input" type="text" value="{{facilityGroup.reps_tel_num3}}">
						</div>
					</td>
				</tr>
				<tr class="normal-show">
					<th>노출여부</th>
					<td>
						<label><input type="radio" name="{{facilityGroup.sts}}" value="0" checked="checked">미노출</label>
						<label><input type="radio" name="{{facilityGroup.sts}}" value="1">노출</label>
					</td>
				</tr>
				<tr class="h92 normal-show">
					<th>기본 아이콘 <br>104 x 104px</th>
					<td>
						<div class="img-uploader  {{#if facilityGroup.img_seq_icon_uri}} after {{else}} before {{/if}}" id="img_seq_icon"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview circle">
								<div class="icon-delete" on-click="imgDelete:'facilityGroup.img_seq_icon_uri'"></div>
								<img src="{{facilityGroup.img_seq_icon_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
					</td>
				</tr>
				<tr class="h92">
					<th>APP용 아이콘 <br>104 x 104px</th>
					<td>
						<div class="img-uploader  {{#if facilityGroup.img_seq_facl_image_uri}} after {{else}} before {{/if}}" id="img_seq_facl_image"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="imgDelete:'facilityGroup.img_seq_facl_image_uri'"></div>
								<img src="{{facilityGroup.img_seq_facl_image_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<div class="btn-wrap">
				<div class="basic-btn gold" on-click="cancel">취소</div>
				<div class="basic-btn gold" on-click="regFacilityGroup">저장</div>
			</div>
			</script>
		</div>
	</div>
<script type="text/javascript">
var conv_faci_seq = '${conv_faci_seq}' || '';
</script>
<script src="/js/app/stores/AFCTW002.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>