<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="event-reg" class="contents-area">
		<div class="contents-wrap" id="event-reg-wrap">
		<script id="tmpl-event-reg" type="text/ractive">
			<h1 class="menu-title">이벤트 관리 - 이벤트 등록 / 수정</h1>
			<table id="event-reg" class="input-list">
				<tr>
					<th>이벤트 유형</th>
					<td>
						<div class="basic-box">
							<c:forEach var="option" items="${eventType}">
								<label><input type="radio" name="{{event.evt_type.codeCd}}" value="${option.codeCd}" <c:if test="${option.defaultValue}"> checked="checked"</c:if>>${option.codeNm}</label>
							</c:forEach>
						</div>
					</td>
				</tr>
				<tr>
					<th>이벤트 주체</th>
					<td>
						<div class="select-wrap">
							<select id="event-host-selector" value="{{event.evt_dvi.codeCd}}">
							<c:forEach var="option" items="${eventDvi}">				
								<option value="${option.codeCd}">${option.codeNm}</option>
							</c:forEach>
							</select>
						</div>
					</td>
				</tr>
				<tr id="tnt-seq">
					<th>진행 브랜드명</th>
					<td>
						<div class="search-input-wrap">
							<input id="input_tnt_nm" class="search-input" type="text" placeholder="브랜드명" value="{{event.tnt_nm_ko}}" disabled>
						</div>
						<div class="basic-btn gold" on-click="openTenantPopup">설정</div>
					</td>
				</tr>
				<tr>
					<th>국문 타이틀</th>
					<td>
						<div class="text-input-wrap">
							<input class="text-input" type="text" placeholder="이벤트 타이틀을 입력하세요" value="{{event.evt_titl}}">
						</div>
					</td>
				</tr>
				<tr>
					<th>이벤트 기간</th>
					<td>
						<div class="date-picker">
							<input type="text" name="datepicker" id="evt_strt_dt" value="{{event.evt_strt_dt}}"><!-- chrome only. please add date-picker for ie-->
						</div>
						<span class="dash"s>~</span>
						<div class="date-picker">
							<input type="text" name="datepicker" id="evt_end_dt" value="{{event.evt_end_dt}}"><!-- chrome only. please add date-picker for ie-->
						</div>
					</td>
				</tr>
				<tr id="event-pick-plan-dt">
					<th>당첨자 발표일</th>
					<td>
						<div class="date-picker">
							<input type="text" name="datepicker" value="{{event.evt_pick_plan_dt}}"><!-- chrome only. please add date-picker for ie-->
						</div>
					</td>
				</tr>
				<tr>
					<th>게시 일정</th>
					<td>
						<div class="date-picker">
							<input type="text" name="datepicker" value="{{event.evt_post_strt_dt}}"><!-- chrome only. please add date-picker for ie-->
						</div>
						<div class="select-wrap time">
							<select class="time-selector" value="{{event.evt_post_strt_hour}}">
								{{#hourList}}
								<option value="{{this}}">{{this}}</option>
								{{/hourList}}
							</select>
						</div>
						<span class="dash">~</span>
						<div class="date-picker">
							<input type="text" name="datepicker" value="{{event.evt_post_end_dt}}"><!-- chrome only. please add date-picker for ie-->
						</div>
						<div class="select-wrap time">
							<select class="time-selector" value="{{event.evt_post_end_hour}}">
								{{#hourList}}
								<option value="{{this}}">{{this}}</option>
								{{/hourList}}
							</select>
						</div>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr class="h92">
					<th>이미지 750px</th>
					<td>
						<div class="img-uploader {{#if event.evt_img_uri}} after {{else}} before {{/if}}" id="evt_img"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="imgDelete:'event.evt_img_uri'"></div>
								<img src="{{event.evt_img_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
					</td>
				</tr>
				<tr id="event-link">
					<th>링크 URL</th>
					<td>
						<label><input type="radio" name="event-link" value="N" {{#if !event.evt_lnk_url}} checked="checked" {{/if}}>N</label>
						<label><input type="radio" name="event-link" value="Y" {{#if event.evt_lnk_url}} checked="checked" {{/if}}>Y </label>
						<label>링크 URL
							<div class="text-input-wrap">
								<input class="text-input" type="text" placeholder="링크 URL을 입력하세요" value="{{event.evt_lnk_url}}">
							</div>
						</label>
						<label>링크 버튼명
							<div class="text-input-wrap">
								<input class="text-input" type="text" placeholder="링크 버튼명을 입력하세요" value="{{event.evt_lnk_btn}}">
							</div>
						</label>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr class="h92">
					<th>내용</th>
					<td>
						<textarea value="{{event.evt_cont}}"></textarea>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr class="h92" id="prvc-acq">
					<th>개인정보 활용 동의</th>
					<td>
						<textarea value="{{event.prvc_agre_term}}"></textarea>
					</td>
				</tr>
				<tr class="h92" id="opr-acq">
					<th>운영방침 동의</th>
					<td>
						<textarea value="{{event.opr_info_agre_term}}"></textarea>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr class="h92">
					<th>목록 노출 이미지(WEB)</th>
					<td>
						<label><input type="radio" value="1" checked="checked" name="{{event.web_list_open_type}}">색상
						<div class="color-picker">
							<span class="bg-red {{#if event.web_list_open_colr == '1'  || !event.web_list_open_colr}} selected {{/if}}" on-click="color:'1','event.web_list_open_colr'"></span>
							<span class="bg-gold {{#if event.web_list_open_colr == '2'}} selected {{/if}}" on-click="color:'2','event.web_list_open_colr'"></span>
							<span class="bg-darkgray {{#if event.web_list_open_colr == '3'}} selected {{/if}}" on-click="color:'3','event.web_list_open_colr'"></span>
							<span class="bg-lightgray  {{#if event.web_list_open_colr == '4'}} selected {{/if}}" on-click="color:'4','event.web_list_open_colr'"></span>
						</div>
						</label>
						<label><input type="radio" value="2"  name="{{event.web_list_open_type}}">이미지
						<div class="img-uploader {{#if event.web_list_open_img_uri}} after {{else}} before {{/if}}" id="web_list_open_img"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="imgDelete:'event.web_list_open_img_uri'"></div>
								<img src="{{event.web_list_open_img_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
						</label>
					</td>
				</tr>
				<tr class="h92">
					<th>목록 노출 이미지(MOBILE)</th>
					<td>
						<label><input type="radio" value="1" checked="checked" name="{{event.mobi_list_open_type}}">색상
						<div class="color-picker">
							<span class="bg-red {{#if event.mobi_list_open_colr == '1' || !event.mobi_list_open_colr}} selected {{/if}}" on-click="color:'1','event.mobi_list_open_colr'"></span>
							<span class="bg-gold {{#if event.mobi_list_open_colr == '2'}} selected {{/if}}" on-click="color:'2','event.mobi_list_open_colr'"></span>
							<span class="bg-darkgray {{#if event.mobi_list_open_colr == '3'}} selected {{/if}}" on-click="color:'3','event.mobi_list_open_colr'"></span>
							<span class="bg-lightgray {{#if event.mobi_list_open_colr == '4'}} selected {{/if}}" on-click="color:'4','event.mobi_list_open_colr'"></span>
						</div>
						</label>
						<label><input type="radio" value="2" name="{{event.mobi_list_open_type}}">이미지
						<div class="img-uploader {{#if event.mobi_list_open_img_uri}} after {{else}} before {{/if}}" id="mobi_list_open_img"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<div class="icon-delete" on-click="imgDelete:'event.mobi_list_open_img_uri'"></div>
								<img src="{{event.mobi_list_open_img_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<div class="basic-btn gold">파일선택</div>
								<span class="comment">선택된 파일 없음</span>
							</div>
						</div>
						</label>
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr>
					<th>노출 여부</th>
					<td>
						<div class="basic-box">
							<label><input type="radio" name="{{event.evt_open_yn}}" value="Y" checked="checked">노출</label>
							<label><input type="radio" name="{{event.evt_open_yn}}" value="N">미노출</label>
						</div>
					</td>
				</tr>
			</table>
			<div class="btn-wrap">	
				<div class="basic-btn gold" on-click="cancel">취소</div>
				<div class="basic-btn gold" on-click="preview">미리보기</div>
				{{#if event.sts != 1 }}
				<div class="basic-btn gold" on-click="regEvent">저장</div>
				{{/if}}
			</div>
			</script>
		</div>
	</div>

	<!--이벤트 미리보기 팝업-->
	<div id="event-display-popup" class="popup-wrap">
		<script id="tmpl-event-display-popup" type="text/ractive">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
				<h4 class="popup-title red">이벤트 미리보기</h4>
				<div class="popup-contents">
					<h5 class="event-detail-display-wrap-title">이벤트 상세페이지</h5>
					<div class="event-display-wrap event-detail">
						<div class="s-container">
							<div class="title">EVENT</div>
							<div class="middle-bar"></div>
							<h3 class="section-title ko">{{event.evt_titl}}</h3>
							<span class="period">{{event.evt_strt_dt}} - {{event.evt_end_dt}}</span>
							<div class="event-contents-wrap">
								<div class="top-btn-wrap">
									<button id="interested-event" class="btn-view btn-gold"><i class="fa fa-heart"></i>관심이벤트</button>
									<div class="btn-view btn-gold btn-auto share">
										<a href="javascript:void(0);" class="btn-kakao">
											<span class="share-kakao"></span>
										</a><a href="javascript:void(0);" class="btn-fb">
											<i class="fa fa-facebook-f"></i>
										</a>
									</div>
								</div>
								<div class="image-wrap">
									<img class="" src="{{event.evt_img_uri}}" alt="스타필드 오픈이벤트 이미지">
								</div>
								<div class="event-contents {{#if event.evt_type.codeCd != 1 && !event.pick_dt && pickDtYn }} type5 {{elseif event.evt_stat.codeCd == '3'}} type4 {{elseif event.evt_type.codeCd == '1'}} type1 {{elseif event.evt_type.codeCd == '2' }} type3 {{elseif event.evt_type.codeCd == '3'}} type2 {{/if}}"><!-- type1 공지형, type2 응모형, type3 개인정보동의 type4 종료된 이벤트 type5 결과발표-->
									<p>{{{brReplace(event.evt_cont)}}}</p>
									<dl>
										<dt>이벤트 기간</dt>
										<dd>{{event.evt_strt_dt}} - {{event.evt_end_dt}}</dd>
									</dl>
									{{#if event.evt_pick_plan_dt}}
									<dl>
										<dt>당첨자 발표</dt>
										<dd>{{event.evt_pick_plan_dt}} 홈페이지 공지 </dd>
									</dl>
									{{/if}}
									{{#if event.evt_lnk_btn}}
									<button id="btn-event-link" class="btn-view" title="{{ event.evt_lnk_btn }}" onclick="window.open('{{event.evt_lnk_url}}', '_blank');">{{event.evt_lnk_btn}}</button>
									{{/if}}
									{{#if event.evt_type.codeCd != 9}}
									<ul class="agreement">
										<li class="closed">
											<span>개인정보 활용 약관</span>
											<div>
												<p>{{event.prvc_agre_term}}</p>
												<label><input class="term-check" type="checkbox" {{#if event.evt_type.codeCd == '3'}} checked="checked" {{/if}}>개인정보 활용 약관에 동의합니다</label>
											</div>
										</li><li class="closed"><!--close class가 추가되면 닫힘-->
											<span>운영방침 약관</span>
											<div>
												<p>{{event.opr_info_agre_term}}</p>
												<label><input class="term-check" type="checkbox" {{#if event.evt_type.codeCd == '3'}} checked="checked" {{/if}}>운영방침 약관에 동의합니다</label>
											</div>
										</li>
									</ul>
									<button id="btn-event-entry" class="btn-view" data-entry="N" title="이벤트 응모하기">응모하기</button>
									{{/if}}
									<div id="end-event" class="btn-view btn-auto">이벤트가 종료되었습니다.</div>
									<div id="event-result">
										<div class="event-result-title">
											<img src="/images/text_congrat.png" alt="congratulation!">
											<h3>{{event.evt_titl}} 당첨자발표</h3>
										</div>
									</div>
								</div>
								<button id="go-to-list-btn" class="btn-view btn-gold btn-auto" href="">이벤트 리스트 보기</button><!--go-to-list class removed-->
							</div>
						</div>
					</div>
					<div class="fbox">
						<h5 class="event-detail-display-wrap-title">이벤트 목록(WEB)</h5>
						<div id="event-display-wrap-web" class="event-display-wrap event-list-web">
							<div class="responsible-box full-width">
								<a id="event1" class="cube {{#if event.web_list_open_type == '1'}}
															{{#if event.web_list_open_colr == '1'}} bg-red {{elseif event.web_list_open_colr == '2'}} bg-gold {{elseif event.web_list_open_colr == '3'}} bg-darkgray {{elseif event.web_list_open_colr == '4'}} bg-lightgray
															{{elseif event.web_list_open_colr == '5'}} bg-black {{elseif event.web_list_open_colr == '6'}} bg-white {{/if}}{{/if}}">
									<span class="bg-img" {{#if event.web_list_open_type == '2'}} style="background-image: url('{{event.web_list_open_img_uri}}');" {{/if}}></span>

									<div>
										<span class="dept">{{#if event.evt_dvi.codeCd == 'S'}}EVENT{{else}}{{event.tnt_nm_ko}}{{/if}}</span>
										<span class="middle-bar white"></span>
										<span class="event-title ko">{{event.evt_titl}}</span>
										<span class="event-desc ko">{{event.evt_strt_dt}} - {{event.evt_end_dt}}</span>
									</div>
								</a>
							</div>
						</div>
						<h5 class="event-detail-display-wrap-title">이벤트 목록(MOBILE)</h5>
						<div id="event-display-wrap-mobile" class="event-display-wrap event-list-mobile">
							<div class="responsible-box full-width">
								<a id="event1" class="cube {{#if event.mobi_list_open_type == '1'}}
															{{#if event.mobi_list_open_colr == '1'}} bg-red {{elseif event.mobi_list_open_colr == '2'}} bg-gold {{elseif event.mobi_list_open_colr == '3'}} bg-darkgray {{elseif event.mobi_list_open_colr == '4'}} bg-lightgray
															{{elseif event.mobi_list_open_colr == '5'}} bg-black {{elseif event.mobi_list_open_colr == '6'}} bg-white {{/if}}{{/if}}">
									<span class="bg-img" {{#if event.mobi_list_open_type == '2'}} style="background-image: url('{{event.mobi_list_open_img_uri}}');" {{/if}}></span>
									<div>
										<span class="dept">{{#if event.evt_dvi.codeCd == 'S'}}EVENT{{else}}{{event.tnt_nm_ko}}{{/if}}</span>
										<span class="middle-bar white"></span>
										<span class="event-title ko">{{event.evt_titl}}</span>
										<span class="event-desc ko">{{event.evt_strt_dt}} - {{event.evt_end_dt}}</span>
									</div>
								</a>
							</div>
						</div>
					</div>
					<div class="fbox">
						<h5 class="event-detail-display-wrap-title">이벤트 목록(앱 메인, iPhone 5S 기준)</h5>
						<div id="event-display-wrap-app-main" class="event-display-wrap event-list-app-main">
							<div class="responsible-box full-width">
								<a id="event1" class="cube {{#if event.mobi_list_open_type == '1'}}
															{{#if event.mobi_list_open_colr == '1'}} bg-red {{elseif event.mobi_list_open_colr == '2'}} bg-gold {{elseif event.mobi_list_open_colr == '3'}} bg-darkgray {{elseif event.mobi_list_open_colr == '4'}} bg-lightgray
															{{elseif event.mobi_list_open_colr == '5'}} bg-black {{elseif event.mobi_list_open_colr == '6'}} bg-white {{/if}}{{/if}}">
									<span class="bg-img" {{#if event.mobi_list_open_type == '2'}} style="background-image: url('{{event.mobi_list_open_img_uri}}');" {{/if}}></span>
									<div>
										<span class="dept">EVENT</span>
										<span class="event-title ko">{{event.evt_titl}}</span>
										<span class="event-desc ko">{{event.evt_strt_dt}} - {{event.evt_end_dt}}</span>
									</div>
								</a>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="close">확인</div>
				</div>
			</div>
		</div>
		</script>
	</div>
	<!--//이벤트 미리보기 팝업-->
	<%@ include file="../common/tenant-popup.jsp" %>
<script type="text/javascript">
var evt_seq = '${evtSeq}' || '';
</script>
<script src="/js/app/event/AEVTW003.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>