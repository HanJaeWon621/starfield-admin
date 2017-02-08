<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="tenant-reg" class="contents-area">
		<div class="contents-wrap" id="tenant-reg-wrap">
			<script id="tmpl-tenant-reg" type="text/ractive">
			<h1 class="menu-title">점포 관리 - 매장 관리 - 매장 등록 / 수정</h1>
			<table id="tenant-reg" class="input-list">
				<tr>
					<th>매장 구분</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text" value="{{#if tenant.tnt_type.codeNm}} {{tenant.tnt_type.codeNm}} {{else}} ${tenantType[0].codeNm} {{/if}}" disabled>
						</div>
					</td>
				</tr>
				<tr>
					<th>국문 매장명</th>
					<td>
						<div class="text-input-wrap dept-read-only">
							<input class="text-input" type="text" placeholder="국문 매장명을 입력하세요" value="{{tenant.tnt_nm_ko}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>영문 매장명</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="영문 매장명을 입력하세요" value="{{tenant.tnt_nm_en}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>국문 서브 타이틀</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="국문 서브 타이틀을 입력하세요" value="{{tenant.tnt_desc_ko}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>영문 서브 타이틀</th>
					<td class="list-input">
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="영문 서브 타이틀을 입력하세요" value="{{tenant.tnt_desc_en}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>층</th>
					<td>
						{{#if tenant.tnt_type.codeCd != 2}}
						<div class="select-wrap dept-read-only">
							<select id="tenant-floor-selector" value="{{tenant.fl}}">
								<option value="4F">4F</option>
								<option value="3F">3F</option>
								<option value="2F">2F</option>
								<option value="1F" selected>1F</option>
								<option value="B1">B1</option>
								<option value="B2">B2</option>
							</select>
						</div>
						{{else}}
						<div class="text-input-wrap dept-read-only">
							<input class="text-input" type="text" placeholder="층을 입력하세요" value="{{tenant.fl}}">
						</div>
						{{/if}}
					</td>
				</tr>
				<tr>
					<th>호수</th>
					<td>
						<div class="text-input-wrap register-no dept-read-only">
							<input on-change="checkByte:10" class="text-input" type="text" placeholder="호수를 입력하세요" value="{{tenant.room_num}}">
						</div>
						<span class="comment">호</span>
					</td>
				</tr>
				<tr>
					<th>운영상태</th>
					<td>
						<div class="select-wrap" on-change="changeOprSts">
							<select id="operating-status-selector" value="{{tenant.opr_sts.codeCd}}">
								<c:forEach var="option" items="${operationStatus}">
								<option value="${option.codeCd}">${option.codeNm}</option>
								</c:forEach>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<th>노출여부</th>
					<td>
						<div class="basic-box" on-change="changeSts">
							<label><input type="radio" name="{{tenant.sts}}" value="0" checked="checked">미노출</label>
							<label><input type="radio" name="{{tenant.sts}}" value="1">노출</label>
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				{{#if tenant.tnt_seq}}
				<tr class="reg-mode-only">
					<th>테넌트 코드</th>
					<td>
						<div class="text-input-wrap no-border">
							<input class="text-input" type="text" value="{{tenant.busi_tnt_cd}}" disabled>
						</div>
					</td>
				</tr>
				<tr>
					<th>계약기간</th>
					<td>{{tenant.coct_strt_prid}} - {{tenant.coct_end_prid}}</td>
				</tr>
				<tr>
					<th>ZONE ID</th>
					<td>{{tenant.zone_id}}</td>
				</tr>
				<tr>
					<th>MAP ID</th>
					<td>{{tenant.map_id}}</td>
				</tr>
				<tr>
					<th>X/Y 좌표</th>
					<td>{{tenant.x_ctn_cord}} / {{tenant.y_ctn_cord}}</td>
				</tr>
				<tr>
					<th>POI 레벨</th>
					<td>{{#if tenant.tnt_type.codeCd == 1}} {{tenant.poi_lev}} {{else}} 사용안함 {{/if}}</td>
				</tr>
				{{/if}}
				<tr>
					<th>중요 POI 레벨 여부</th>
					<td>
						<div class="basic-box dept-read-only">
							<label><input type="radio" name="{{tenant.reps_exp_poi_lev_yn}}" value="N" checked="checked">N</label>
							<label><input type="radio" name="{{tenant.reps_exp_poi_lev_yn}}" value="Y">Y</label>
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr>
					<th>전화번호</th>
					<td>
						<div class="tel-input-wrap dept-read-only">
							<input on-change="checkByte:3" class="tel-input" type="text" value="{{tenant.tnt_tel_num1}}">
							<div>-</div>
							<input on-change="checkByte:4" class="tel-input" type="text" value="{{tenant.tnt_tel_num2}}">
							<div>-</div>
							<input on-change="checkByte:4" class="tel-input" type="text" value="{{tenant.tnt_tel_num3}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>운영시간(평일)</th>
					<td>
						<div class="select-wrap time">
							<select class="time-selector" value="{{tenant.open_hr_min}}">
								{{#hourList}}
								<option value="{{.cd}}">{{.nm}}</option>
								{{/hourList}}
							</select>
						</div>
						<span class="dash">~</span>
						<div class="select-wrap time">
							<select class="time-selector" value="{{tenant.end_hr_min}}">
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
							<select class="time-selector" value="{{tenant.irgu_open_hr_min}}">
								{{#hourList}}
								<option value="{{.cd}}">{{.nm}}</option>
								{{/hourList}}
							</select>
						</div>
						<span class="dash">~</span>
						<div class="select-wrap time">
							<select class="time-selector" value="{{tenant.irgu_end_hr_min}}">
								{{#hourList}}
								<option value="{{.cd}}">{{.nm}}</option>
								{{/hourList}}
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<th>카테고리</th>
					<td>
						{{#cate_list:idx}}
						<div class="tenant-category-wrap">
							<div class="select-wrap">
								<select value="{{tenant.cate_list[idx].mama_cate_seq}}" on-change="changeFirstCate:{{idx}}">
									<option value="">1차 카테고리 선택</option>
									{{#cate_list[idx].firstCategories}}
									<option value="{{this.cate_seq}}">{{this.cate_nm_ko}}</option>
									{{/cate_list[idx].firstCategories}}
								</select>
							</div>
							<div class="select-wrap">
								<select id="tenant-2nd-category-selector" value="{{tenant.cate_list[idx].cate_seq}}"  on-change="changeSecondCate:{{idx}}">
									<option value="">2차 카테고리 선택</option>
									{{#cate_list[idx].secondCategories}}
									<option value="{{this.cate_seq}}">{{this.cate_nm_ko}}</option>
									{{/cate_list[idx].secondCategories}}
								</select>
							</div>
							<div class="basic-btn gold" on-click="delCate:{{idx}}">삭제</div>
						</div>
						{{/cate_list}}
						<div class="btn-wrap">
							<button class="basic-btn gold" type="button" on-click="addCate">카테고리 추가</button>
						</div>
					</td>
				</tr>
				<tr class="h-free">
					<th>매장 키워드 입력</th>
					<td>
						<div class="tenant-keyword-input"><!-- before: 키워드 등록 전 / after: 키워드 등록 후, input tag에 disabled attr 추가되어야 함-->
							{{#tenant.tnt_tag_list:idx}}
							<div class="search-input-wrap after">
								<input class="search-input basic-color" type="text" value="{{this}}" placeholder="키워드 입력" disabled>
								<div class="icon-delete" on-click="delTag:{{idx}}"></div>
							</div>
							{{/tenant.tnt_tag_list}}
							<div class="search-input-wrap before">
								<input class="search-input basic-color" type="text" placeholder="키워드 입력" id="input-tag-box">
								<div class="icon-delete" on-click="delTag:{{idx}}"></div>
							</div>
							<button class="basic-btn gold tenant-keyword-btn" type="button" on-click="addTag">추가</button>
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr class="h92">
					<th>웹 상단 이미지<br>1920 x 650 px</th>
					<td>
						<div class="img-uploader {{#if tenant.img_main_bg_web_uri}} after {{else}} before {{/if}}" id="img_main_bg_web"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="imgDelete:'tenant.img_main_bg_web_uri'"></div>
								<img src="{{tenant.img_main_bg_web_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
					</td>
				</tr>
				<tr class="h92">
					<th>모바일 상단 이미지<br>375 x 546 px</th>
					<td>
						<div class="img-uploader {{#if tenant.img_main_bg_mobi_uri}} after {{else}} before {{/if}}" id="img_main_bg_mobi"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="imgDelete:'tenant.img_main_bg_mobi_uri'"></div>
								<img src="{{tenant.img_main_bg_mobi_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
					</td>
				</tr>
				<tr class="h92">
					<th>투명 BI <br>334 x 175 px</th>
					<td>
						<div class="img-uploader {{#if tenant.img_main_bg_logo_uri}} after {{else}} before {{/if}}" id="img_main_bg_logo"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="imgDelete:'tenant.img_main_bg_logo_uri'"></div>
								<img src="{{tenant.img_main_bg_logo_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
					</td>
				</tr>
				<tr class="h92">
					<th>기준 BI 1 <br>560 x 560 px</th>
					<td>
						<div class="img-uploader {{#if tenant.img_logo_uri}} after {{else}} before {{/if}}" id="img_logo"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="imgDelete:'tenant.img_logo_uri'"></div>
								<img src="{{tenant.img_logo_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
					</td>
				</tr>
				<tr class="h92">
					<th>기준 BI 2 <br>334 x 175px</th>
					<td>
						<div class="img-uploader {{#if tenant.img_thmb_uri}} after {{else}} before {{/if}}" id="img_thmb"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="imgDelete:'tenant.img_thmb_uri'"></div>
								<img src="{{tenant.img_thmb_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
					</td>
				</tr>
				<tr class="h92">
					<th>APP 저용량 BI <br>250 x 250 px</th>
					<td>
						<div class="img-uploader {{#if tenant.img_mobi_logo_uri}} after {{else}} before {{/if}}" id="img_mobi_logo"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="imgDelete:'tenant.img_mobi_logo_uri'"></div>
								<img src="{{tenant.img_mobi_logo_uri}}">
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
				<div class="basic-btn gold" on-click="regTenant">저장</div>
			</div>
		</script>
		</div>
	</div>
<script type="text/javascript">
var tnt_seq = '${tntSeq}' || '';
</script>
<script src="/js/app/stores/ATNTW002.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>