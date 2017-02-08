<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="main-hero-reg" class="contents-area" data-bcn_cd="${bcn_cd}" data-bn_group_seq="${bn_group_seq}">
		<script id="tmpl-main-hero-reg" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">메인 관리 - 상단 히어로 배너 관리 - 그룹 등록 / 수정</h1>
			<div class="list-top">
				<ul class="inline-list">
					<li on-click="bannerDetail">배너 추가</li>
					<li on-click="delete">선택 삭제</li>
				</ul>
			</div>
			<table id="main-hero-reg-list" class="input-list">
				<tr>
					<th>적용 구분</th>
					<td>
						<label>
							<input type="radio" name="{{bannerGroup.bn_div}}" value="1" {{#if(bannerGroup.bannerList.length > 0)}}disabled{{/if}}>WEB
						</label>
						<label>
							<input type="radio" name="{{bannerGroup.bn_div}}" value="2" {{#if(bannerGroup.bannerList.length > 0)}}disabled{{/if}}>APP
						</label>
					</td>
				</tr>
				<tr>
					<th>그룹타이틀</th>
					<td>
						<div class="text-input-wrap">
							<input id="bn_group_titl" class="text-input" type="text" placeholder="그룹타이틀을 입력하세요" value="{{bannerGroup.bn_group_titl}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>게시기간</th>
					<td>
						<div class="date-picker">
							<input id="bn_post_strt_date" type="text" name="datepicker" placeholder="시작일자" value="{{bannerGroup.bn_post_strt_date}}">
						</div>
						<div class="select-wrap time">
							<select id="bn_post_strt_time" class="time-selector" value={{bannerGroup.bn_post_strt_time}}>
								<option value="00:00">00:00</option>
								<option value="01:00">01:00</option>
								<option value="02:00">02:00</option>
								<option value="03:00">03:00</option>
								<option value="04:00">04:00</option>
								<option value="05:00">05:00</option>
								<option value="06:00">06:00</option>
								<option value="07:00">07:00</option>
								<option value="08:00">08:00</option>
								<option value="09:00">09:00</option>
								<option value="10:00">10:00</option>
								<option value="11:00">11:00</option>
								<option value="12:00">12:00</option>
								<option value="13:00">13:00</option>
								<option value="14:00">14:00</option>
								<option value="15:00">15:00</option>
								<option value="16:00">16:00</option>
								<option value="17:00">17:00</option>
								<option value="18:00">18:00</option>
								<option value="19:00">19:00</option>
								<option value="20:00">20:00</option>
								<option value="21:00">21:00</option>
								<option value="22:00">22:00</option>
								<option value="23:00">23:00</option>
							</select>
						</div>
						<span class="dash">~</span>
						<div class="date-picker">
							<input id="bn_post_end_date" type="text" name="datepicker" placeholder="종료일자" value="{{bannerGroup.bn_post_end_date}}">
						</div>
						<div class="select-wrap time">
							<select id="bn_post_end_time" class="time-selector" value={{bannerGroup.bn_post_end_time}}>
								<option value="00:00">00:00</option>
								<option value="01:00">01:00</option>
								<option value="02:00">02:00</option>
								<option value="03:00">03:00</option>
								<option value="04:00">04:00</option>
								<option value="05:00">05:00</option>
								<option value="06:00">06:00</option>
								<option value="07:00">07:00</option>
								<option value="08:00">08:00</option>
								<option value="09:00">09:00</option>
								<option value="10:00">10:00</option>
								<option value="11:00">11:00</option>
								<option value="12:00">12:00</option>
								<option value="13:00">13:00</option>
								<option value="14:00">14:00</option>
								<option value="15:00">15:00</option>
								<option value="16:00">16:00</option>
								<option value="17:00">17:00</option>
								<option value="18:00">18:00</option>
								<option value="19:00">19:00</option>
								<option value="20:00">20:00</option>
								<option value="21:00">21:00</option>
								<option value="22:00">22:00</option>
								<option value="23:00">23:00</option>
							</select>
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
			</table>

			<div id="main-hero-group-reg-list" class="list-table">
				<div class="t-row list-head">
					<div class="t-cell selected">NO</div>
					<div class="t-cell">브랜드명 / 매장명</div>
					<div class="t-cell">대 타이틀1</div>
					<div class="t-cell">대 타이틀2</div>
					<div class="t-cell no-order">선택</div>
				</div>
				<ul class="list-body sort" >
					{{#bannerGroup.bannerList}}
					<li class="t-row" data-sort_ord={{sort_ord}}>
						<div class="t-cell cursor-m">{{sort_ord}}</div>
						<div class="t-cell ellipsis cursor-p" on-click="bannerDetail:{{this}}">{{bn_titl}}</div>
						<div class="t-cell ellipsis cursor-m ta-l">{{great_titl1}}</div>
						<div class="t-cell ellipsis cursor-m ta-l">{{great_titl2}}</div>
						<div class="t-cell checkbox"><input name="bannerCheckBox" type="checkbox" value="{{sort_ord}}"></div>
					</li>
					{{/bannerGroup.bannerList}}
				</ul>
			</div>
	
			<div class="btn-wrap">
				<button class="basic-btn gold" type="button" on-click="cancel">취소</button>
				<button class="basic-btn gold" type="button" on-click="save:{{bn_group_seq}}">저장</button>
			</div>
		</div>
		
		</script>
	</div>
	
	<!--배너 등록/수정 팝업-->
	<div id="main-hero-reg-popup" class="popup-wrap">
		<script id="tmpl-main-hero-reg-popup" type="text/ractive">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
				<h4 class="popup-title red">상단 히어로 배너 등록 / 수정</h4>
				<div class="popup-contents">
					<table id="main-hero-reg-list" class="input-list">
						<tr class="transparent"></tr>
						<tr>
							<th>컨텐츠 유형</th>
							<td>
								<div class="select-wrap">
									<select id="content-type" value="{{banner.div}}" on-change="changeContent">
										<option value="INTR">없음</option>
										<option value="ET">이벤트</option>
										<option value="TT">매장</option>
									</select>
								</div>
							</td>
						</tr>
						{{#if banner.div != 'INTR'}}
						<tr>
							<th>컨텐츠 명</th>
							<td>
								<div class="text-input-wrap">
									<input class="text-input" type="text" placeholder="컨텐츠를 검색하세요" value="{{banner.cont_titl}}" readonly>
								</div>
								<div class="basic-btn gold" on-click="selectPopup:{{banner.div}}">검색</div>
							</td>
						</tr>
						{{/if}}
						
						<tr class="transparent"></tr>
						<tr>
							<th>브랜드명/매장명</th>
							<td>
								<div class="text-input-wrap">
									<input class="text-input" type="text" placeholder="브랜드명 또는 매장명을 입력하세요" value="{{banner.bn_titl}}">
								</div>
							</td>
						</tr>
						<tr>
							<th>대 타이틀 1</th>
							<td>
								<div class="text-input-wrap">
									<input class="text-input" type="text" placeholder="대 타이틀 1을 입력하세요" value="{{banner.great_titl1}}">
								</div>
							</td>
						</tr>
						<tr>
							<th>대 타이틀 2</th>
							<td>
								<div class="text-input-wrap">
									<input class="text-input" type="text" placeholder="대 타이틀 2를 입력하세요" value="{{banner.great_titl2}}">
								</div>
							</td>
						</tr>
						<tr>
							<th>소 타이틀</th>
							<td>
								<div class="text-input-wrap">
									<input class="text-input" type="text" placeholder="소 타이틀을 입력하세요" value="{{banner.small_titl}}">
								</div>
							</td>
						</tr>
						<tr class="transparent"></tr>
						
						<tr>
							<th>텍스트 색상</th>
							<td>
								<div class="color-picker">
									{{#if bn_div == 1}}
									<span class="bg-red {{#if banner.txt_colr_cd == '#C31531'}}selected{{/if}}" on-click="txt_colr_cd:#C31531"></span>
									<span class="bg-gold {{#if banner.txt_colr_cd == '#B49759'}}selected{{/if}}" on-click="txt_colr_cd:#B49759"></span>
									<span class="bg-darkgray {{#if banner.txt_colr_cd == '#7B6D65'}}selected{{/if}}" on-click="txt_colr_cd:#7B6D65"></span>
									<span class="bg-lightgray {{#if banner.txt_colr_cd == '#887D76'}}selected{{/if}}" on-click="txt_colr_cd:#887D76"></span>
									<span class="bg-black {{#if banner.txt_colr_cd == '#000000'}}selected{{/if}}" on-click="txt_colr_cd:#000000"></span>
									{{/if}}
									<span class="bg-white {{#if banner.txt_colr_cd == '#FFFFFF'}}selected{{/if}}" on-click="txt_colr_cd:#FFFFFF"></span>
									
								</div>
							</td>
						</tr>
						
						<tr class="h92">
							<th>배경 이미지</th>
							<td>
								
								<label><input type="radio" name="{{banner.bg_img_type}}" value="1" {{#if bn_div == 2}}disabled{{/if}}>색상
								<div class="color-picker">
									<span class="bg-red {{#if banner.bg_colr_cd == '#C31531'}}selected{{/if}}" on-click="bg_colr_cd:#C31531"></span>
									<span class="bg-gold {{#if banner.bg_colr_cd == '#B49759'}}selected{{/if}}" on-click="bg_colr_cd:#B49759"></span>
									<span class="bg-darkgray {{#if banner.bg_colr_cd == '#7B6D65'}}selected{{/if}}" on-click="bg_colr_cd:#7B6D65"></span>
									<span class="bg-lightgray {{#if banner.bg_colr_cd == '#887D76'}}selected{{/if}}" on-click="bg_colr_cd:#887D76"></span>
								</div>
								</label>
								<label><input type="radio" name="{{banner.bg_img_type}}" value="2">이미지
								<div id="bg_image"class="img-uploader {{#if banner.bn_img_seq}}after{{else}}before{{/if}}"><!-- before: 업로드전/ after: 업로드 후-->
									<div class="img-preview">
										<div class="icon-delete" on-click="image-delete"></div>
										{{#if(banner.bn_img_uri)}}<img src="{{banner.bn_img_uri}}">{{/if}}
									</div>
									<div class="file-uploader-wrap">
										<div id="bg_image_upload"class="basic-btn gold">파일선택</div>
										<span class="comment">선택된 파일 없음</span>
									</div>
								</div>
								</label>
							</td>
						</tr>
					</table>
				</div>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="close">취소</div>
					<div class="basic-btn gold" on-click="save">저장</div>
				</div>
			</div>
		</div>
		</script>
	</div>
	<!--//배너 등록/수정 팝업-->
	
<%@ include file="../common/event-popup.jsp" %>
<%@ include file="../common/coupon-popup.jsp" %>
<%@ include file="../common/tenant-popup.jsp" %>
<script src="/js/lib/jquery-sortable.js" type="text/javascript"></script>
<script src="/js/app/main/ACMSW010D.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>