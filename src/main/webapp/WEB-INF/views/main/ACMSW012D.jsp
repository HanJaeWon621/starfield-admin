<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="main-news-group-reg" class="contents-area" data-bcn_cd="${bcn_cd}" data-what_group_seq="${what_group_seq}">
		<script id="tmpl-main-news-group-reg" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">메인 관리 - STARFIELD NOW 관리 - 그룹 등록 / 수정</h1>
			<div class="search-box">
				<span class="select-title">그룹명</span>
				<div class="text-input-wrap">
					<input class="search-input" type="text" placeholder="Group Title을 입력하세요" value="{{whatsNewGroup.what_group_titl}}">
				</div>
				<div class="date-selector-wrap">
					<span class="select-title">게시시작</span>
					<div class="date-picker">
						<input id="what_post_strt_date" type="text" name="datepicker" placeholder="시작일자" value="{{whatsNewGroup.what_post_strt_date}}">
					</div>
					<div class="select-wrap time">
						<select id="what_post_strt_time" class="time-selector" value={{whatsNewGroup.what_post_strt_time}}>
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
					<span class="select-title">게시종료</span>
					<div class="date-picker">
						<input id="what_post_end_date" type="text" name="datepicker" placeholder="종료일자" value="{{whatsNewGroup.what_post_end_date}}">
					</div>
					<div class="select-wrap time">
						<select id="what_post_end_time" class="time-selector" value={{whatsNewGroup.what_post_end_time}}>
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
				</div>
			</div>
			<div class="list-top">
				<ul class="inline-list">
					<li on-click="copy">그룹 복사</li><li on-click="whatsNewDetail">컨텐츠 추가</li><li on-click="delete">선택 삭제</li>
				</ul>
			</div>
			
			<div id="main-news-group-reg-list" class="list-table">
				<div class="t-row list-head">
					<div class="t-cell">NO</div>
					<div class="t-cell">유형</div>
					<div class="t-cell">유형명</div>
					<div class="t-cell">타이틀</div>
					<div class="t-cell">기간</div>
					<div class="t-cell">상태</div>
					<div class="t-cell">선택</div>
				</div>
				<ul class="list-body sort">
					{{#whatsNewGroup.whatsNewList}}
					<li class="t-row cursor-m" data-sort_ord={{sort_ord}}>
						<div class="t-cell">{{sort_ord}}</div>
						<div class="t-cell">{{#if(div=='ET')}}EVENT{{elseif (div=='CP')}}COUPON{{else}}TENANT{{/if}}</div>
						<div class="t-cell ellipsis">{{cont_titl}}</div>
						<div class="t-cell ellipsis cursor-p" on-click="whatsNewDetail:{{this}}">{{title_main_text}} {{title_sub_text}}</div>
						<div class="t-cell">{{#if(start_dt)}}{{start_dt}} - {{end_dt}}{{/if}}</div>
						<div class="t-cell green">{{cont_sts}}</div>
						<div class="t-cell checkbox cursor-d"><input name="whatNewCheckBox" type="checkbox" value="{{sort_ord}}"></div>
					</li>
					{{/whatsNewGroup.whatsNewList}}
				</ul>
			</div>
			<div class="btn-wrap">
				<div class="basic-btn gold" on-click="cancel">취소</div>
				<div class="basic-btn gold" on-click="preview">미리보기</div>
				<div class="basic-btn gold" on-click="save">저장</div>
			</div>
		</div>
		</script>
	</div>
	
	<!--컨텐츠 배치 순서 안내 팝업-->
	<div id="main-news-display-popup" class="popup-wrap">
		<script id="tmpl-main-news-display-popup" type="text/ractive">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
				<h4 class="popup-title red">컨텐츠 배치 순서 안내</h4>
				<div class="popup-contents">
					<div class="responsible-box">
						<a id="event6" class="cube big">
							<span class="bg-img" style="
								{{#if(whatsNewGroup.whatsNewList[0].web_bg_img_type == 1)}}
								background-color: {{whatsNewGroup.whatsNewList[0].web_bg_colr}};
								{{else}}
								background-image: url('{{whatsNewGroup.whatsNewList[0].thumb_image_uri}}');
								{{/if}}
								"></span>
							<div>
								<span class="dept" style="color: {{whatsNewGroup.whatsNewList[0].web_txt_colr}}">{{whatsNewGroup.whatsNewList[0].title_head_text}}</span>
								<span class="middle-bar"></span>
								<span class="event-title ko" style="color: {{whatsNewGroup.whatsNewList[0].web_txt_colr}}">{{whatsNewGroup.whatsNewList[0].title_main_text}}<br>{{whatsNewGroup.whatsNewList[0].title_sub_text}}</span>
								<span class="event-desc ko" style="color: {{whatsNewGroup.whatsNewList[0].web_txt_colr}}">{{#if(whatsNewGroup.whatsNewList[0].div != 'TT')}}{{whatsNewGroup.whatsNewList[0].start_dt}} - {{whatsNewGroup.whatsNewList[0].end_dt}}{{/if}}</span>
							</div>
						</a>
					</div>
					<div class="responsible-box">
						<a id="event2" class="cube">
							<span class="bg-img" style="
								{{#if(whatsNewGroup.whatsNewList[1].web_bg_img_type == 1)}}
								background-color: {{whatsNewGroup.whatsNewList[1].web_bg_colr}};
								{{else}}
								background-image: url('{{whatsNewGroup.whatsNewList[1].thumb_image_uri}}');
								{{/if}}
								"></span>
							<div>
								<span class="dept" style="color: {{whatsNewGroup.whatsNewList[1].web_txt_colr}}">{{whatsNewGroup.whatsNewList[1].title_head_text}}</span>
								<span class="middle-bar"></span>
								<span class="event-title ko" style="color: {{whatsNewGroup.whatsNewList[1].web_txt_colr}}">{{whatsNewGroup.whatsNewList[1].title_main_text}}<br>{{whatsNewGroup.whatsNewList[1].title_sub_text}}</span>
								<span class="event-desc ko" style="color: {{whatsNewGroup.whatsNewList[1].web_txt_colr}}">{{#if(whatsNewGroup.whatsNewList[1].div != 'TT')}}{{whatsNewGroup.whatsNewList[1].start_dt}} - {{whatsNewGroup.whatsNewList[1].end_dt}}{{/if}}</span>
							</div>
						</a>
						<a id="event3" class="cube">
							<span class="bg-img" style="
								{{#if(whatsNewGroup.whatsNewList[2].web_bg_img_type == 1)}}
								background-color: {{whatsNewGroup.whatsNewList[2].web_bg_colr}};
								{{else}}
								background-image: url('{{whatsNewGroup.whatsNewList[2].thumb_image_uri}}');
								{{/if}}
								"></span>
							<div>
								<span class="dept" style="color: {{whatsNewGroup.whatsNewList[2].web_txt_colr}}">{{whatsNewGroup.whatsNewList[2].title_head_text}}</span>
								<span class="middle-bar"></span>
								<span class="event-title ko" style="color: {{whatsNewGroup.whatsNewList[2].web_txt_colr}}">{{whatsNewGroup.whatsNewList[2].title_main_text}}<br>{{whatsNewGroup.whatsNewList[2].title_sub_text}}</span>
								<span class="event-desc ko" style="color: {{whatsNewGroup.whatsNewList[2].web_txt_colr}}">{{#if(whatsNewGroup.whatsNewList[2].div != 'TT')}}{{whatsNewGroup.whatsNewList[2].start_dt}} - {{whatsNewGroup.whatsNewList[2].end_dt}}{{/if}}</span>
							</div>
						</a>
						<a id="event4" class="cube">
							<span class="bg-img" style="
								{{#if(whatsNewGroup.whatsNewList[3].web_bg_img_type == 1)}}
								background-color: {{whatsNewGroup.whatsNewList[3].web_bg_colr}};
								{{else}}
								background-image: url('{{whatsNewGroup.whatsNewList[3].thumb_image_uri}}');
								{{/if}}
								"></span>
							<div>
								<span class="dept" style="color: {{whatsNewGroup.whatsNewList[3].web_txt_colr}}">{{whatsNewGroup.whatsNewList[3].title_head_text}}</span>
								<span class="middle-bar"></span>
								<span class="event-title ko" style="color: {{whatsNewGroup.whatsNewList[3].web_txt_colr}}">{{whatsNewGroup.whatsNewList[3].title_main_text}}<br>{{whatsNewGroup.whatsNewList[3].title_sub_text}}</span>
								<span class="event-desc ko" style="color: {{whatsNewGroup.whatsNewList[3].web_txt_colr}}">{{#if(whatsNewGroup.whatsNewList[3].div != 'TT')}}{{whatsNewGroup.whatsNewList[3].start_dt}} - {{whatsNewGroup.whatsNewList[3].end_dt}}{{/if}}</span>
							</div>
						</a>
						<a id="event5" class="cube">
							<span class="bg-img" style="
								{{#if(whatsNewGroup.whatsNewList[4].web_bg_img_type == 1)}}
								background-color: {{whatsNewGroup.whatsNewList[4].web_bg_colr}};
								{{else}}
								background-image: url('{{whatsNewGroup.whatsNewList[4].thumb_image_uri}}');
								{{/if}}
								"></span>
							<div>
								<span class="dept" style="color: {{whatsNewGroup.whatsNewList[4].web_txt_colr}}">{{whatsNewGroup.whatsNewList[4].title_head_text}}</span>
								<span class="middle-bar"></span>
								<span class="event-title ko" style="color: {{whatsNewGroup.whatsNewList[4].web_txt_colr}}">{{whatsNewGroup.whatsNewList[4].title_main_text}}<br>{{whatsNewGroup.whatsNewList[4].title_sub_text}}</span>
								<span class="event-desc ko" style="color: {{whatsNewGroup.whatsNewList[4].web_txt_colr}}">{{#if(whatsNewGroup.whatsNewList[4].div != 'TT')}}{{whatsNewGroup.whatsNewList[4].start_dt}} - {{whatsNewGroup.whatsNewList[4].end_dt}}{{/if}}</span>
							</div>
						</a>
					</div>
				</div>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="close">확인</div>
				</div>
			</div>
		</div>
		</script>
	</div>
	<!--//컨텐츠 배치 순서 안내 팝업-->
	
	<!--Group 복사 팝업-->
	<div id="main-news-group-copy-popup" class="popup-wrap">
		<script id="tmpl-main-news-group-copy-popup" type="text/ractive">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
				<h4 class="popup-title red">Group 복사</h4>
				<div class="popup-contents">
					<div class="search-box">
						<span class="select-title">그룹명</span>
						<div class="text-input-wrap">
							<input class="search-input" type="text" placeholder="Group Title을 입력하세요" value="{{copy_whatsNewGroup.what_group_titl}}">
						</div>
						<span class="date-picker-wrap">
							<span class="select-title">게시시작</span>
							<div class="date-picker">
								<input type="text" name="datepicker_copy" placeholder="시작일자" value="{{copy_whatsNewGroup.what_post_strt_date}}">
							</div>
							<div class="select-wrap time">
								<select id="what_post_strt_time" class="time-selector" value={{copy_whatsNewGroup.what_post_strt_time}}>
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
							<span class="select-title">게시종료</span>
							<div class="date-picker">
								<input type="text" name="datepicker_copy" placeholder="시작일자" value="{{copy_whatsNewGroup.what_post_end_date}}">
							</div>
							<div class="select-wrap time">
								<select id="what_post_end_time" class="time-selector" value={{copy_whatsNewGroup.what_post_end_time}}>
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
						</span>
					</div>
					<h4 class="popup-title sub">컨텐츠 구성 목록</h4>
					<table class="basic-list">
						<colgroup>
							<col width="70">
							<col width="150">
							<col width="260">
							<col width="*">
							<col width="200">
							<col width="100">
						</colgroup>
						<thead>
							<tr>
								<th class="no no-order">NO</th>
								<th class="no-order">유형</th>
								<th class="no-order">유형명</th>
								<th class="no-order">타이틀</th>
								<th class="no-order">기간</th>
								<th class="no-order green">상태</th>
							</tr>
						</thead>
						<tbody>
							{{#copy_whatsNewGroup.whatsNewList}}
							<tr>
								<td>{{sort_ord}}</td>
								<td>{{#if(div=='ET')}}EVENT{{elseif (div=='CP')}}COUPON{{else}}TENANT{{/if}}</td>
								<td class="ellipsis">{{cont_titl}}</td>
								<td>{{title_main_text}} {{title_sub_text}}</td>
								<td>{{#if(start_dt)}}{{start_dt}} - {{end_dt}}{{/if}}</td>
								<td class="red">{{cont_sts}}</td>

							</tr>
							{{/copy_whatsNewGroup.whatsNewList}}
						</tbody>
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
	<!--//Group 복사 팝업-->
	
	<!--컨텐츠 등록 팝업-->
	<div id="main-news-cont-reg-popup" class="popup-wrap">
		<script id="tmpl-main-news-cont-reg-popup" type="text/ractive">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
				<h4 class="popup-title red">컨텐츠 등록 / 수정</h4>
				<div class="popup-contents">
					<table id="main-news-cont-reg-list" class="input-list main-news-cont-reg-list">
						<tr>
							<th>컨텐츠 유형</th>
							<td>
								<div class="select-wrap">
									<select id="content-type" on-change="changeContent" value="{{whatsNew.div}}">
										<option value="ET">이벤트</option>
										<option value="CP">쿠폰</option>
										<option value="TT">매장</option>
									</select>
								</div>
							</td>
						</tr>
						<tr class="transparent"></tr>
						
						{{#if(whatsNew.div == 'ET')}}
						<!--이벤트 자리-->
						<tr>
							<th>대상 이벤트</th>
							<td>
								<div class="search-input-wrap">
									<input class="search-input" type="text" placeholder="이벤트명을 입력하세요" value="{{whatsNew.cont_titl}}" readonly>
									<div class="basic-btn search gold" on-click="selectPopup:{{whatsNew.div}}">검색</div>
								</div>
							</td>
						</tr>
						<tr>
							<th>이벤트 기간</th>
							<td>{{#if(whatsNew.start_dt)}}{{whatsNew.start_dt}} - {{whatsNew.end_dt}}{{/if}}</td>
						</tr>
						<!--//이벤트 자리-->
						{{/if}}

						{{#if(whatsNew.div == 'CP')}}
						<!--쿠폰 자리-->
						<tr>
							<th>대상 쿠폰</th>
							<td>
								<div class="search-input-wrap">
									<input class="search-input" type="text" placeholder="쿠폰명을 입력하세요" value="{{whatsNew.cont_titl}}" readonly>
									<div class="basic-btn search gold" on-click="selectPopup:{{whatsNew.div}}">검색</div>
								</div>
							</td>
						</tr>
						<tr>
							<th>쿠폰 유효 기간</th>
							<td>{{#if(whatsNew.start_dt)}}{{whatsNew.start_dt}} - {{whatsNew.end_dt}}{{/if}}</td>
						</tr>
						<tr>
							<th>쿠폰 발급 기간</th>
							<td>{{whatsNew.etc_txt}}</td>
						</tr>
						<!--//쿠폰 자리-->
						{{/if}}
						
						{{#if(whatsNew.div == 'TT')}}
						<!--매장 자리-->
						<tr>
							<th>매장명</th>
							<td>
								<div class="search-input-wrap">
									<input class="search-input" type="text" placeholder="매장명을 입력하세요" value="{{whatsNew.cont_titl}}" readonly>
									<div class="basic-btn search gold" on-click="selectPopup:{{whatsNew.div}}">검색</div>
								</div>
							</td>
						</tr>
						<tr>
							<th>매장 위치</th>
							<td>{{whatsNew.etc_txt}}</td>
						</tr>
						<!--//매장 자리-->
						{{/if}}
						
						<tr>
							<th>메인 타이틀1 (국문)</th>
							<td>
								<div class="text-input-wrap">
									<input class="text-input" type="text" value="{{whatsNew.title_main_text}}" placeholder="메인 타이틀(국문)을 입력하세요">
								</div>
							</td>
						</tr>
						<tr>
							<th>메인 타이틀2 (국문)</th>
							<td>
								<div class="text-input-wrap">
									<input class="text-input" type="text" value="{{whatsNew.title_sub_text}}" placeholder="메인 타이틀(국문)을 입력하세요">
								</div>
							</td>
						</tr>
						
						<tr class="transparent"></tr>
						<tr class="h92">
							<th>목록 노출 이미지 (WEB)</th>
							<td>
								<label><input type="radio" name="{{whatsNew.web_bg_img_type}}" value="1">색상
								<div class="color-picker">
									<span class="bg-red {{#if whatsNew.web_bg_colr == '#C31531'}}selected{{/if}}" on-click="web_bg_colr:#C31531"></span>
									<span class="bg-gold {{#if whatsNew.web_bg_colr == '#B49759'}}selected{{/if}}" on-click="web_bg_colr:#B49759"></span>
									<span class="bg-darkgray {{#if whatsNew.web_bg_colr == '#7B6D65'}}selected{{/if}}" on-click="web_bg_colr:#7B6D65"></span>
									<span class="bg-lightgray {{#if whatsNew.web_bg_colr == '#887D76'}}selected{{/if}}" on-click="web_bg_colr:#887D76"></span>
								</div>
								</label>
								<label><input type="radio" name="{{whatsNew.web_bg_img_type}}" value="2">이미지
								<div id="bg_image"class="img-uploader {{#if whatsNew.thumb_image_seq}}after{{else}}before{{/if}}"><!-- before: 업로드전/ after: 업로드 후-->
									<div class="img-preview">
										<div class="icon-delete" on-click="webImageDelete"></div>
										{{#if(whatsNew.thumb_image_uri)}}<img src="{{whatsNew.thumb_image_uri}}">{{/if}}
									</div>
									<div class="file-uploader-wrap">
										<div id="web_image_upload" class="basic-btn gold">파일선택</div>
										<span class="comment">선택된 파일 없음</span>
									</div>
								</div>
								</label>
							</td>
						</tr>
						<tr class="h92">
							<th>텍스트 색상 (WEB)</th>
							<td>
								<div class="color-picker">
									<span class="bg-red {{#if whatsNew.web_txt_colr == '#C31531'}}selected{{/if}}" on-click="web_txt_colr:#C31531"></span>
									<span class="bg-gold {{#if whatsNew.web_txt_colr == '#B49759'}}selected{{/if}}" on-click="web_txt_colr:#B49759"></span>
									<span class="bg-darkgray {{#if whatsNew.web_txt_colr == '#7B6D65'}}selected{{/if}}" on-click="web_txt_colr:#7B6D65"></span>
									<span class="bg-lightgray {{#if whatsNew.web_txt_colr == '#887D76'}}selected{{/if}}" on-click="web_txt_colr:#887D76"></span>
									<span class="bg-black {{#if whatsNew.web_txt_colr == '#000000'}}selected{{/if}}" on-click="web_txt_colr:#000000"></span>
									<span class="bg-white {{#if whatsNew.web_txt_colr == '#FFFFFF'}}selected{{/if}}" on-click="web_txt_colr:#ffffff"></span>
								</div>
							</td>
						</tr>
						<tr class="h92">
							<th>목록 노출 이미지 (APP)</th>
							<td>
								<label><input type="radio" name="{{whatsNew.mobi_bg_img_type}}" value="1">색상
								<div class="color-picker">
									<span class="bg-red {{#if whatsNew.mobi_bg_colr == '#C31531'}}selected{{/if}}" on-click="mobi_bg_colr:#C31531"></span>
									<span class="bg-gold {{#if whatsNew.mobi_bg_colr == '#B49759'}}selected{{/if}}" on-click="mobi_bg_colr:#B49759"></span>
									<span class="bg-darkgray {{#if whatsNew.mobi_bg_colr == '#7B6D65'}}selected{{/if}}" on-click="mobi_bg_colr:#7B6D65"></span>
									<span class="bg-lightgray {{#if whatsNew.mobi_bg_colr == '#887D76'}}selected{{/if}}" on-click="mobi_bg_colr:#887D76"></span>
								</div>
								</label>
								<label><input type="radio" name="{{whatsNew.mobi_bg_img_type}}" value="2">이미지
								<div id="bg_image"class="img-uploader {{#if whatsNew.mobi_thumb_image_seq}}after{{else}}before{{/if}}"><!-- before: 업로드전/ after: 업로드 후-->
									<div class="img-preview">
										<div class="icon-delete" on-click="appImageDelete"></div>
										{{#if(whatsNew.mobi_thumb_image_uri)}}<img src="{{whatsNew.mobi_thumb_image_uri}}">{{/if}}
									</div>
									<div class="file-uploader-wrap">
										<div id="app_image_upload"class="basic-btn gold">파일선택</div>
										<span class="comment">선택된 파일 없음</span>
									</div>
								</div>
								</label>
							</td>
						</tr>
							<tr class="h92">
							<th>텍스트 색상 (APP)</th>
							<td>
								<div class="color-picker">
									<span class="bg-white {{#if whatsNew.mobi_txt_colr == '#FFFFFF'}}selected{{/if}}" on-click="mobi_txt_colr:#FFFFFF"></span>
								</div>
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
	<!--//컨텐츠 등록 팝업-->
<%@ include file="../common/event-popup.jsp" %>
<%@ include file="../common/coupon-popup.jsp" %>
<%@ include file="../common/tenant-popup.jsp" %>
<script src="/js/lib/jquery-sortable.js" type="text/javascript"></script>
<script src="/js/app/main/ACMSW012D.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>