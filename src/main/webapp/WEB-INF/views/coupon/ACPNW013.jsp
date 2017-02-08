<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>쿠폰 등록</title>

	<script src="/js/lib/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <script src="/js/lib/common.js" type="text/javascript"></script>
    
    <script src="/js/lib/file-input-button.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.iframe-transport.js" type="text/javascript"></script>
    
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="/css/common.css">
</head>
<body>  
	<div id="coupon-reg" class="contents" >
		<script id="tmpl-coupon-reg" type="text/ractive">
		<h3>일반쿠폰 수정</h3>
		
		{{#coupon}} 
			<table>
				<tr> 
					<td>
						쿠폰ID :
					</td>  
					<td> 
						{{bcn_cd+yymm+mst_seq}}
					</td> 
					</tr>
					<tr>
						<td>
							*종류 :
						</td>
						<td> 
							<input id="cp_kind_cd2" type="radio" name="{{cp_kind_cd}}" value="2">할인 쿠폰테넌트
						</td>
					</tr> 
					<tr> 
						<td>
							*발급 타입 선택 : 
						</td>
						<td> 
							<input id="cp_iss_type_cd1" type="radio" name="{{cp_iss_type_cd}}" value="1" checked>일반 쿠폰 
							<input id="cp_iss_type_cd2" type="radio" name="{{cp_iss_type_cd}}" value="2" >웰컴 메시지용
							<input id="cp_iss_type_cd3" type="radio" name="{{cp_iss_type_cd}}" value="3" >시나리오 푸시용
						</td>
					</tr>
					<tr>
						<td>
							*타이틀 :
						</td>
						<td> 
							<input id="cp_titl" type="text" value="{{cp_titl}}" style="width:350px;">
						</td>
					</tr>
					<tr>
						<td>*사용처설정 :</td>
						<td> 
							 <input id="opentenantBtn" type="button" value="테넌트불러오기" on-click="opentenant" /> 	 
							 <input id="openSeltenantBtn" type="button" value="" on-click="openSeltenant" /> 
						</td>
					</tr> 
					<tr>			
						<td> 
							*발급일  :
						</td>
						<td>
							시작일 <input id="cp_iss_strt_dt" name="datepicker" type="text" value="{{cp_iss_strt_dt}}" style="width:70px;"> ~ 
							종료일 <input id="cp_iss_end_dt" name="datepicker" type="text" value="{{cp_iss_end_dt}}" style="width:70px;">
						</td>
					</tr>
					<tr>
						<td>
							*유효기간설정  :
						</td>
						<td>
							시작일 <input id="cp_act_strt_dt" name="datepicker" type="text" value="{{cp_act_strt_dt}}" style="width:70px;"> ~ 
							종료일 <input id="cp_act_end_dt" name="datepicker" type="text" value="{{cp_act_end_dt}}" style="width:70px;">
						</td>
					</tr>
					<tr>
						<td>
							*할인방식설정 : 
						</td> 
						<td>      
							 <input  type="radio" id="cp_sale_div_cd1" name="{{cp_sale_div_cd}}" value="2" 
								on-click="changeCpSumSaleMethDiv:true"> 소계 할인형 
							 <input id="cp_sum_sale_rt"  type="text" value="{{cp_sum_sale_rt}}"  placeholder="숫자만 입력" ><font id="giho1" size=3 >%</font><br>
							 <input id="cp_max_sale_amt"  type="text" value="{{cp_max_sale_amt}}"  placeholder="숫자만 입력" ><font id="giho1" size=3 >%</font><br> 
							 
							<input type="radio" id="cp_sale_div_cd2" name="{{cp_sale_div_cd}}" value="1"  
								on-click="changeCpSumSaleMethDiv:false" >금액 차감형
							 <input id="cp_ded_amt" type="text" value="{{cp_ded_amt}}"  placeholder="숫자만 입력" disabled="disabled"><font id="giho2" size=3 >\</font>
						</td>
					</tr>
					<tr> 
						<td>
							*발급수량설정 :
						</td>
						<td> 
							 <input id="cp_iss_cnt" type="text" value="{{cp_iss_cnt}}"  placeholder="숫자만 입력" >장<br>
						</td>
					</tr>
					<tr>
						<td>*사용조건설정 :</td>
						<td>  
							 <input id="cp_use_cond_amt" type="text" value="{{cp_use_cond_amt}}" placeholder="숫자만 입력" >
						</td> 
					</tr> 
										<tr>
						<td>
							*BI 이미지:
						</td>
						<td>
							BI 이미지
							<div class="pic-upload-wrap">
	    						<div id="title-image-upload-button1" class="pic-edit-wrap" on-click="imgBtnT:{{'1'}}">이미지</div>
							</div>
							<br/>  
							<br/> 
							<br/>
							<img id="webBiImg" src="" style="width: 50px;height: 50px">
						</td>
					</tr>
					<tr>
						<td>
							*BI 이미지2:
						</td>
						<td>
							BI 이미지
							<div class="pic-upload-wrap">
	    						<div id="title-image-upload-button2" class="pic-edit-wrap" on-click="imgBtnT:{{'1'}}">이미지</div>
							</div>
							<br/> 
							<br/> 
							<br/>
							<img id="moblBiImg" src="" style="width: 50px;height: 50px">
						</td>
					</tr>
					<tr>
						<td>
							쿠폰 상세(내용) : 
						</td>
						<td>
							<input id="cp_dtl_cont" type="text" value="{{cp_dtl_cont}}">
						</td>
					</tr>
					<tr>
						<td>유의 사항 : </td>
						<td> <input id="cp_att_part_cont" type="text" value="{{cp_att_part_cont}}"></td>
					</tr>
					<tr>
						<td>web</td>
						<td> 
							<input type="radio" id="webBg1" name="webBg" value="1" on-click="setWebBg:{{'img'}}" 
								{{#if web_bg_type == 'N'}}checked{{/if}} > 
							<div class="pic-upload-wrap" id="webBgFile" {{#if web_bg_type == 'Y'}}style="display: none;" {{/if}}> 
	    						<div id="title-image-upload-button3" class="pic-edit-wrap" >이미지</div>
							</div>
							<br/>
							<br/> 
							<br/> 
							<br/> 
							<input type="radio" id="webBg2" name="webBg" value="2" on-click="setWebBg:{{'color'}}"
								{{#if web_bg_type == 'Y'}}checked{{/if}}>
						      현재색[<span id="webNowColor">
								{{#if web_bg_clr == '1'}}검정{{/if}}
								{{#if web_bg_clr == '2'}}빨강{{/if}}
								{{#if web_bg_clr == '3'}}노랑{{/if}} 	
								</span>]
								<span id="bgColor1" on-click="setWebBgColor:{{'1'}}">검정</span>
								<span id="bgColor2" on-click="setWebBgColor:{{'2'}}">빨강</span>
								<span id="bgColor3" on-click="setWebBgColor:{{'3'}}">노랑</span>
						</td>
					</tr>

					<tr>
						<td>mobile(APP)</td>
						<td>  
							<input type="radio" id="appBg1" name="appBg" value="1" on-click="setAppBg:{{'img'}}" 
								{{#if app_bg_type == 'N'}}checked{{/if}} >
							<div class="pic-upload-wrap" id="appBgFile" {{#if app_bg_type == 'Y'}} style="display: none;" {{/if}}> 
	    						<div id="title-image-upload-button4" class="pic-edit-wrap" >이미지</div>
							</div>
							<br/>
							<br/> 
							<br/> 
							<input type="radio" id="appBg2" name="appBg" value="2" on-click="setAppBg:{{'color'}}"
								{{#if app_bg_type == 'Y'}}checked{{/if}} >
						      현재색[<span id="appNowColor">
								{{#if app_bg_clr == '1'}}검정{{/if}}
								{{#if app_bg_clr == '2'}}빨강{{/if}}
								{{#if app_bg_clr == '3'}}노랑{{/if}}  
								</span>]
								<span id="bgColor1" on-click="setAppBgColor:{{'1'}}">검정</span>
								<span id="bgColor2" on-click="setAppBgColor:{{'2'}}">빨강</span>
								<span id="bgColor3" on-click="setAppBgColor:{{'3'}}">노랑</span>
						</td>
					</tr>
					<tr> 
						<td>
							<input type="button" value="등록" on-click="regCoupon" />
							<input type="button" value="취소" on-click="resetCoupon"/>
							<input type="button" value="목록" on-click="couponList"/>
						</td>
					</tr>
				</table>
		{{/coupon}}
		</script> 
	</div>
	
	
	<div id="dialog-detail" class="contents"  >
		<script id="tmpl-tennant-list" type="text/ractive">
		{{#filter}}	
		t<input id="tnt_nm_ko" type="text" value="{{tnt_nm_ko}}">
 		c<select id="cate_seq" value="{{cate_seq}}">
			<option value="">선택</option>
			{{#categoryList}}  	
				<option value="{{cate_seq}}">{{cate_nm_ko}}</option> 
			{{/categoryList}}  
		</select>
		{{/ftext}}	 
		<input type="button" value="검색" on-click="searchTennant">		
	
		<div id="emp" class="emp-list-wrap">
		<ul class="">
			<li class="list-header">
				<span style="width: 10%"><input id="tenantAllChk" type="checkbox" name="tenantAllChk"></span>
				<span style="width: 25%">카테고리</span>
				<span style="width: 25%">테넌트명</span>
				<span style="width: 25%">호수</span>
				
          	</li>
		{{#tenantList:num}}
			<li class="list-body">
				<span style="width: 10%"><input id="tennant_{{tnt_seq}}" name="tennant_seq" type="radio"   
					on-click="tennantChk:{{tnt_seq}},{{cate_nm_ko}},{{tnt_nm_ko}},{{room_num}}
					,{{busi_tnt_cd}},{{img_main_bg_web_uri}},{{img_main_bg_mobi_uri}}">
				</span>
				<span style="width: 25%">{{cate_nm_ko}}</span>
				<span style="width: 25%">{{tnt_nm_ko}}</span>
				<span style="width: 25%">{{room_num}}</span>
			</li> 
		{{/tenantList}}
		</ul>  
		{{#paging}} 
			<div class="paging-wrap">
				{{#if page_start > 1}}
					<img class="btn-prev" src="/images/btn-prev.png" on-click="movePage:{{paging.page_start - 1}}">
				{{/if}}
                <ul class="paging">
					{{#pages}}
                    <li {{#if paging.cur_page === this}} class="cur-page" {{/if}} on-click="movePage:{{this}}">{{this}}</li>
					{{/pages}}
                </ul>
				{{#if paging.page_end < paging.total_page_cnt}}
                	<img class="btn-next" src="/images/btn-next.png" on-click="movePage:{{paging.page_end + 1}}">
				{{/if}}
            </div>
		{{/paging}}	

        </div>

		<input type="button" value="저장" on-click="tennantSave"/>
		<input type="button" value="닫기" on-click="tennantClose"/>
		</script> 
	</div > 
	 <div id="dialog-detail2" class="contents"  >
		<script id="tmpl-seltenant-list2" type="text/ractive">
		<div id="emp" class="emp-list-wrap">
		{{#coupon}} 		
		<ul class=""> 
			<li class="list-header">
				<span style="width: 25%">카테고리</span>
				<span style="width: 25%">테넌트명</span>
				<span style="width: 25%">호수</span>
				<span style="width: 25%">삭제</span>
          	</li> 
		{{#selTenantList:num}}
			<li class="list-body" id="selTenant_{{num}}"> 
				<span style="width: 25%">{{cate_nm_ko}}</span>
				<span style="width: 25%">{{tnt_nm_ko}}</span>
				<span style="width: 25%">{{room_num}}</span>
				<span style="width: 25%" on-click="deletetenant:{{num}}">x</span> 
			</li>
		{{/selTenantList}}
		</ul> 
		{{/coupon}}
        </div>
		<input type="button" value="저장" on-click="selTennantSave"/>
		<input type="button" value="닫기" on-click="selTennantClose"/>
		</script> 
	</div > 
	<script src="/js/app/coupon/ACPNW013.js" type="text/javascript"></script>
</body>
</html>