<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="event-reg" class="contents-area">
		<div class="contents-wrap" id="event-reg-wrap">
		<script id="tmpl-event-reg" type="text/ractive">
			<h1 class="menu-title">이벤트 관리 - 이벤트 상세보기</h1>
			<table id="event-reg" class="input-list">
				<tr>
					<th>이벤트 유형</th>
					<td>{{event.evt_type.codeNm}}</td>
				</tr>
				<tr>
					<th>이벤트 주체</th>
					<td>{{event.evt_dvi.codeNm}}</td>
				</tr>
				{{#if event.evt_dvi.codeCd == 'B'}}
				<tr>
					<th>진행 브랜드명</th>
					<td>{{event.tnt_nm_ko}}</td>
				</tr>
				{{/if}}
				<tr>
					<th>국문 타이틀</th>
					<td>{{event.evt_titl}}</td>
				</tr>
				<tr>
					<th>이벤트 기간</th>
					<td>{{event.evt_strt_dt}} ~ {{event.evt_end_dt}}</td>
				</tr>
				{{#if event.evt_type.codeCd != '1'}}
				<tr>
					<th>당첨자 발표일</th>
					<td>{{event.evt_pick_plan_dt}}</td>
				</tr>
				{{/if}}
				<tr>
					<th>게시 일정</th>
					<td>{{event.evt_post_strt_dt}} {{event.evt_post_strt_hour}} ~ {{event.evt_post_end_dt}} {{event.evt_post_end_hour}}</td>
				</tr>
				<tr class="transparent"></tr>
				<tr class="h92">
					<th>이미지 750px</th>
					<td>
						<div class="img-uploader {{#if event.evt_img_uri}} after {{else}} before {{/if}}" id="evt_img"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<img src="{{event.evt_img_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<span class="comment">파일 없음</span>
							</div>
						</div>
					</td>
				</tr>
				{{#if event.evt_lnk_url}}
				<tr>
					<th>링크 URL</th>
					<td>{{event.evt_lnk_url}}</td>
				</tr>
				<tr>
					<th>링크 버튼명</th>
					<td>{{event.evt_lnk_btn}}</td>
				</tr>
				{{/if}}
				<tr class="transparent"></tr>
				<tr class="h92">
					<th>내용</th>
					<td>
						<textarea value="{{event.evt_cont}}" readonly="true"></textarea>
					</td>
				</tr>
				{{#if event.evt_type.codeCd == 2}}
				<tr class="transparent"></tr>
				<tr class="h92">
					<th>개인정보 활용 동의</th>
					<td>
						<textarea value="{{event.prvc_agre_term}}" readonly="true"></textarea>
					</td>
				</tr>
				<tr class="h92">
					<th>운영방침 동의</th>
					<td>
						<textarea value="{{event.opr_info_agre_term}}" readonly="true"></textarea>
					</td>
				</tr>
				{{/if}}
				<tr class="transparent"></tr>
				<tr class="h92 detail-mode">
					<th>목록 노출 이미지(WEB)</th>
					<td>
						{{#if event.web_list_open_type == 1 }} 
						<div class="color-picker">
							<span class="bg-red {{#if event.web_list_open_colr == '1'  || !event.web_list_open_colr}} selected {{/if}}" on-click="color:'1','event.web_list_open_colr'"></span>
							<span class="bg-gold {{#if event.web_list_open_colr == '2'}} selected {{/if}}" on-click="color:'2','event.web_list_open_colr'"></span>
							<span class="bg-darkgray {{#if event.web_list_open_colr == '3'}} selected {{/if}}" on-click="color:'3','event.web_list_open_colr'"></span>
							<span class="bg-lightgray  {{#if event.web_list_open_colr == '4'}} selected {{/if}}" on-click="color:'4','event.web_list_open_colr'"></span>
							<span class="bg-black  {{#if event.web_list_open_colr == '5'}} selected {{/if}}" on-click="color:'5','event.web_list_open_colr'"></span>
							<span class="bg-white  {{#if event.web_list_open_colr == '6'}} selected {{/if}}" on-click="color:'6','event.web_list_open_colr'"></span>
						</div>
						{{else}}
						<div class="img-uploader {{#if event.web_list_open_img_uri}} after {{else}} before {{/if}}" id="web_list_open_img"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<img src="{{event.web_list_open_img_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<span class="comment">파일 없음</span>
							</div>
						</div>
						{{/if}}
					</td>
				</tr>
				<tr class="h92 detail-mode">
					<th>목록 노출 이미지(MOBILE)</th>
					<td>
						{{#if event.mobi_list_open_type == 1 }} 
						<div class="color-picker">
							<span class="bg-red {{#if event.mobi_list_open_colr == '1' || !event.mobi_list_open_colr}} selected {{/if}}" on-click="color:'1','event.mobi_list_open_colr'"></span>
							<span class="bg-gold {{#if event.mobi_list_open_colr == '2'}} selected {{/if}}" on-click="color:'2','event.mobi_list_open_colr'"></span>
							<span class="bg-darkgray {{#if event.mobi_list_open_colr == '3'}} selected {{/if}}" on-click="color:'3','event.mobi_list_open_colr'"></span>
							<span class="bg-lightgray {{#if event.mobi_list_open_colr == '4'}} selected {{/if}}" on-click="color:'4','event.mobi_list_open_colr'"></span>
							<span class="bg-black {{#if event.mobi_list_open_colr == '5'}} selected {{/if}}" on-click="color:'5','event.mobi_list_open_colr'"></span>
							<span class="bg-white {{#if event.mobi_list_open_colr == '6'}} selected {{/if}}" on-click="color:'6','event.mobi_list_open_colr'"></span>
						</div>
						{{else}}
						<div class="img-uploader {{#if event.mobi_list_open_img_uri}} after {{else}} before {{/if}}" id="mobi_list_open_img"><!-- before: 업로드전/ after: 업로드 후-->
							<div class="img-preview">
								<img src="{{event.mobi_list_open_img_uri}}">
							</div>
							<div class="file-uploader-wrap">
								<span class="comment">파일 없음</span>
							</div>
						</div>
						{{/if}}
					</td>
				</tr>
				<tr class="transparent"></tr>
				<tr>
					<th>노출 여부</th>
					<td>{{#if event.evt_open_yn == 'Y'}}노출{{else}}미노출{{/if}}</td>
				</tr>
				<tr>
					<th>승인일</th>
					<td>
						<div class="confirm-date {{#if event.sts == 1 }} after  {{else}} before {{/if}}"><!--before:승인전 / after:승인후-->
							<div id="btn-confirm" class="basic-btn gold" on-click="confirm">승인하기</div>
							<div id="confirm-date" class="text-input-wrap no-border">
								<input class="text-input" type="text" value="{{event.evt_app_dt}} / {{event.evt_app_id}}" disabled>
							</div>
						</div>
					</td>
				</tr>
			</table>
			{{#if event.evt_seq}}
				<h2>이벤트 현황</h2>
				<table id="event-status" class="input-list">
					<tr>
						<th>이벤트 조회수</th>
						<td>{{event.evt_hits}}</td>
					</tr>
					{{#if event.evt_type.codeCd != 1}}
						<tr>
							{{#if event.evt_type.codeCd != 9}}
								<th>이벤트 응모수</th>
								<td>
									<span>{{event.aply_cnt}}</span>
									<div class="basic-btn gold" on-click="viewAplyInfo">응모자 정보</div>
								</td>
							{{else}}
								<th>이벤트 응모자 정보</th>
								<td>
									<div class="basic-btn gold" on-click="viewAplyInfo">스탬프 교환 정보</div>
								</td>
							{{/if}}
						</tr>
						{{#if event.evt_type.codeCd != 9}}
						<tr>
							<th>이벤트 참여율</th>
							<td>{{#if event.evt_hits != 0}} {{event.aply_cnt/event.evt_hits*100}} {{else}} 0 {{/if}}%</td>
						</tr>
						<tr>
							<th>추첨상태</th>
							<td>
								<span class="{{event.evt_stat.cssClass}}">{{#if event.evt_stat.codeCd != '3' }} 이벤트 {{event.evt_stat.codeNm}} {{elseif event.evt_pick_dt}} 추점완료 {{else}} 추첨전 {{/if}}</span><!-- green: 종료/  gray 진행전-->
								{{#if event.evt_stat.codeCd == 3 && !event.evt_pick_dt}}
									<div class="basic-btn gold" on-click="goPick">추첨하기</div>
								{{elseif event.evt_stat.codeCd == 3 && event.evt_pick_dt}}
									<div class="basic-btn gold" on-click="viewWonInfo">당첨자 정보</div>
								{{/if}}
							</td>
						</tr>
						{{/if}}
					{{/if}}
				</table>
			{{/if}}
			<div class="btn-wrap">	
				<div class="basic-btn gold" on-click="goList">목록</div>
				<div class="basic-btn gold" on-click="preview">미리보기</div>
				{{#if event.sts != 1 }}
				<div class="basic-btn gold" on-click="goModify">수정하기</div>
				{{/if}}
			</div>
			</script>
		</div>
	</div>

		<!--응모자 목록 팝업-->
	<div id="entry-list" class="popup-wrap">
		<script id="tmpl-entry-list" type="text/ractive">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
			{{#if codeCd == 9}}
				<h4 class="popup-title red">이벤트 응모자 목록<span class="sub"> : {{paging.total_cnt}}</span></h4>
			{{else}}
				<h4 class="popup-title red">이벤트 응모자 목록</h4>
			{{/if}}
				<div class="popup-contents">
					<table class="basic-list">
						<colgroup>
							<col width="10%">
							<col width="*">
							<col width="*">
							<col width="*">
							<col width="*">
						</colgroup>
						<thead>
							<tr>
								<th class="no no-order">NO</th>
								<th on-click="search" data-order-key="mbr_nm">성명</th>
								<th on-click="search" data-order-key="cust_id">ID</th>
								<th on-click="search" data-order-key="aply_dttm">응모일</th>
								<th class="no-order">휴대전화번호</th>
								<th class="no-order">상태</th>
							</tr>
						</thead>
						<tbody>
							{{#members}}
							<tr>
								<td>{{no}}</td>
								<td>{{mbr_nm}}</td>
								<td class="ellipsis">{{cust_id}}</td>
								<td>{{aply_dttm}}</td>
								<td>{{getTel(mbr_cel_num1,mbr_cel_num2,mbr_cel_num3)}}</td>
								<td>{{mbr_status}}{{#if mbr_mod_dttm}}/{{mbr_mod_dttm}} {{/if}}</td>
							</tr>
							{{/members}}
						</tbody>
					</table>
					{{#paging}}
					<div class="paging-wrap">
					<ul class="paging inline-list">
					<li class="prev {{#if paging.page_start == 1}}off{{/if}}" on-click="pageMove:{{paging.page_start - 1}}">PREV</li>
					{{#pages}}
					<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="pageMove:{{this}}">{{this}}</li>
					{{/pages}}
					<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" on-click="pageMove:{{paging.page_end + 1}}">NEXT</li>
					</ul>
            		</div>
					{{/paging}}
				</div>
			</div>
		</div>
		</script>
	</div>
	<!--//응모자 목록 팝업-->

	<!--당첨자 추첨 팝업-->
	<div id="draw-popup" class="popup-wrap type1">
		<script id="tmpl-draw-popup" type="text/ractive">
		<div class="popup-container"><!--type1 일반추첨 , type2 다중추첨-->
			<div class="popup">
				<div class="popup-header"><span class="btn-close">&#215;</span></div>
				<h4 class="popup-title red">이벤트 당첨자 추첨</h4>
				<div class="popup-contents">
					<ul class="draw-wrap">
						<li>
							<div class="label-box">추첨 구분</div>
							<div class="select-wrap">
								<select id="draw-type-select" value="{{pick_info.pick_div}}">
									<option value="1">일반추첨</option>
									<option value="2">다중추첨</option>
								</select>
							</div>
						</li>
						<li>
							<div class="label-box">추첨자 수</div>
							<div class="text-input-wrap">
								<input class="text-input" type="text" value="{{pick_info.pick_count}}"><!-- 숫자만 입력 가능하도록-->
							</div>
							<span class="comment">명</span>
						</li>
						<li>
							<div class="label-box">추첨 구분 수</div>
							<div class="select-wrap">
								<select id="draw-type-select" value="{{pick_info.pick_div_cnt}}">
									{{#multCntList}}
									<option value="{{this}}">{{this}}개</option>
									{{/multCntList}}
								</select>
							</div>
						</li>
						<li>
							<div class="label-box">구분명/추첨자 수</div>
							<ul>
								{{#pick_info.mult_pick_info}}
								<li>
									<div class="text-input-wrap sort">
										<input class="text-input" type="text" value="{{this.win_item}}">
									</div>
									<div class="text-input-wrap number">
										<input class="text-input" type="text" value="{{this.pick_count}}"><!-- 숫자만 입력 가능하도록-->
									</div>
									<span class="comment">명</span>
								</li>
								{{/pick_info.mult_pick_info}}
							</ul>
						</li>
					</ul>
					<div class="btn-wrap">
						<div class="basic-btn gold" on-click="pick">추첨하기</div>
					</div>
				</div>
			</div>
		</div>
		</script>
	</div>
	<!--//당첨자 추첨 팝업-->

	<!--당첨자 결과 팝업-->
	<div id="result-popup" class="popup-wrap">
		<script id="tmpl-result-popup" type="text/ractive">
		<div class="popup-container {{#if pick_div == '1'}} type1 {{else}} type2 {{/if}}"><!--type1 , type2 (구분, 추첨자 수 포함)-->
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
				<h4 class="popup-title red">이벤트 당첨자 목록</h4>
				<div class="popup-contents">
					<table class="basic-list">
						<thead>
							<tr>
								<th class="no no-order">NO</th>
								<th on-click="search" data-order-key="win_item" class="sort">구분</th>
								<th on-click="search" data-order-key="mbr_nm">성명</th>
								<th on-click="search" data-order-key="cust_id">ID</th>
								<th on-click="search" data-order-key="aply_dttm">응모일</th>
								<th class="no-order">휴대전화번호</th>
								<th class="no-order">상태</th>
							</tr>
						</thead>
						<tbody>
							{{#members}}
							<tr>
								<td class="no">{{no}}</td>
								<td class="sort ellipsis">{{win_item}}</td>
								<td>{{mbr_nm}}</td>
								<td class="ellipsis">{{cust_id}}</td>
								<td>{{aply_dttm}}</td>
								<td>{{getTel(mbr_cel_num1,mbr_cel_num2,mbr_cel_num3)}}</td>
								<td>{{mbr_status}}{{#if mbr_mod_dttm}}/{{mbr_mod_dttm}} {{/if}}</td>
							</tr>
							{{/members}}
						</tbody>
					</table>
					{{#paging}}
					<div class="paging-wrap">
					<ul class="paging inline-list">
					<li class="prev {{#if paging.page_start == 1}}off{{/if}}" on-click="pageMove:{{paging.page_start - 1}}">PREV</li>
					{{#pages}}
					<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="pageMove:{{this}}">{{this}}</li>
					{{/pages}}
					<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" on-click="pageMove:{{paging.page_end + 1}}">NEXT</li>
					</ul>
            		</div>
					{{/paging}}
				</div>
			</div>
		</div>
		</script>
	</div>
	<!--//당첨자 결과 팝업-->
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
<script type="text/javascript">
var evt_seq = '${evtSeq}' || '';
</script>
<script src="/js/app/event/AEVTW002.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>