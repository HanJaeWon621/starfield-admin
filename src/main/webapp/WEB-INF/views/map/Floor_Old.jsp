<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/map-header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
<style type="text/css">
.date-picker2 {
	display: inline-block;
	width: 80px;
	height: 30px;
	border: 1px solid #7b6d65;
	background-color: #ffffff;
	position: relative;
	vertical-align: middle; 
}
.date-picker2 input {
	position: absolute;
	top: 0;
	left: 0;
	border: none;
	outline: none;
	height: 100%;
	width: 100%;
	padding-left: 5px;
	z-index: 4;
}
</style> 
		<div id="style-list" class="contents">
		<script id="tmpl-style-list" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">맵 관리 > Floor 설정</h1>
			<div class="search-box">
			<input id="copy_cp_seq" type="hidden">
			{{#couponFilter}}
				층 : 
				<div class="select-wrap"> 
					<select id="sh_fl"  name="sh_fl" value="{{sh_fl}}">
						<option value="">전체</option>
						<option value="1">1층</option>
						<option value="2">2층</option>
						<option value="3">3층</option>
					</select> 
				</div> 
				<span on-click="shCoupon" style="cursor: pointer">검색</span>

			{{/couponFilter}} 
			</div> 
			<div class="list-top">
				<ul class="inline-list">
					<li on-click=openPop:{{'Reg'}},{{'N'}} style="cursor: pointer">등록</li>
				</ul>
			</div>
			<table id="event-mng-list" class="basic-list">
				<colgroup>
					<col width="30">
					<col width="30">
					<col width="30">
					<col width="30">
					<col width="40">
					<col width="50">
					<col width="50">
					<col width="50">
					<col width="50">
					<col width="50">
					<col width="50">
				</colgroup>
				<thead>
					<tr>
						<th class="no-order">층</th>
						<th class="no-order">층명</th>
						<th class="no-order">기본블록색</th>
						<th class="no-order">기본블록높이</th>
						<th class="no-order">기초판사용여부</th>
						<th class="no-order">기초판높이</th>
						<th class="no-order">기초판색</th>
						<th class="no-order">기초판offset</th>
						<th class="no-order">기본블록패딩</th>
						<th class="no-order">기본spray스케일</th>
						<th on-click=listSort data-order-key="Action">Action</th>
					</tr>
				</thead>
				<tbody>
					{{#styleList:num}}
					<tr >                  
						<td>{{fl}}</td>              
						<td>{{fl_nm}}</td>                    
						<td class="ellipsis ta-l">{{dft_block_clr}}</td>
						<td class="ellipsis ta-r" >{{dft_block_hgt}}</td>              
						<td>{{base_plate_yn}}</td>                    
						<td class="ellipsis ta-r">{{bs_plate_hgt}}</td>
						<td class="ellipsis ta-l" >{{bs_plate_clr}}</td>              
						<td class="ellipsis ta-r" >{{bs_plate_oft}}</td>                    
						<td class="ellipsis ta-r">{{dft_block_pdg}}</td> 
						<td class="ellipsis ta-r">{{dft_spray_sle}}</td>                          
						<td><a href="javascript:;" on-click=openPop:{{'Edit'}},{{fl_seq}}>[Edit]</a><a href="javascript:;" on-click=deleteFloor:{{fl_seq}}>[Del]</a></td>                           
					</tr> 
					{{/styleList}}
					
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

		
		</script>
	</div>
	<!--신규 쿠폰등록2-->
	<div id="style-dtl" class="contents">
		<script id="tmpl-style-dtl" type="text/ractive">
			<div id="tnt-reg-popup2" name="tnt-reg-popup2" class="popup-wrap"><!--place-on 추가시 테넌트 불러오기 팝업 열림-->
		<div class="popup-container">
			{{#formdata}}
			<div id="coupon-place-setting" class="popup"> 
				<div class="popup-header"><span class="btn-close" on-click="closePop" style="cursor: pointer">&#215;</span></div>
				<h4 class="popup-title red" id="formtitl">스타일생성</h4>
				<input class="text-input" id="fl_seq" name="fl_seq" type="hidden" value="{{fl_seq}}">
				<table>
					<tr>
						<td style="background-color:#edebea">층</td>
						<td><div class="text-input-wrap long">
							<input class="text-input" id="fl" name="fl" type="text" value="{{fl}}">
							</div>
						</td>
					</tr>
					<tr>
						<td style="background-color:#edebea">층명</td>
						<td><div class="text-input-wrap long">
							<input class="text-input" id="fl_nm" name="fl_nm" type="text" value="{{fl_nm}}">
							</div>
						</td>
					</tr>
					<tr>
						<td style="background-color:#edebea">기본블럭색</td>
						<td>
							<div class="text-input-wrap long">
							<input class="text-input" id="dft_block_clr" name="dft_block_clr" type="text" value="{{dft_block_clr}}">
							</div>
						</td>
					</tr>
					<tr>
						<td style="background-color:#edebea">기본블럭높이</td>
						<td>
							<div class="text-input-wrap long">
							<input class="text-input" id="dft_block_hgt" name="dft_block_hgt" type="text" value="{{dft_block_hgt}}">
							</div>
						</td>
					</tr>
					<tr>
						<td style="background-color:#edebea">기초판사용여부</td>
						<td>
							<div class="text-input-wrap long">
							<input class="text-input" id="base_plate_yn" name="base_plate_yn" type="text" value="{{base_plate_yn}}">
							</div>
						</td>
					</tr>
					<tr>
						<td style="background-color:#edebea">기초판높이</td>
						<td>
							<div class="text-input-wrap long">
							<input class="text-input" id="bs_plate_hgt" name="bs_plate_hgt" type="text" value="{{bs_plate_hgt}}">
							
							</div>
						</td>
					</tr>
					<tr>
						<td style="background-color:#edebea">기초판색</td>
						<td>
							<div class="text-input-wrap long">
							<input class="text-input" id="bs_plate_clr" name="bs_plate_clr" type="text" value="{{bs_plate_clr}}">
							</div>
						</td>
					</tr>
					<tr>
						<td style="background-color:#edebea">기초판Offset</td>
						<td>
							<div class="text-input-wrap long">
							<input class="text-input" id="bs_plate_oft" name="bs_plate_oft" type="text" value="{{bs_plate_oft}}">
							</div>
						</td>
					</tr>
					<tr>
						<td style="background-color:#edebea">기본블럭Padding</td>
						<td>
							<div class="text-input-wrap long">
							<input class="text-input" id="dft_block_pdg" name="dft_block_pdg" type="text" value="{{dft_block_pdg}}">
							</div>
						</td>
					</tr>
					<tr>
						<td style="background-color:#edebea">기본Spray스케일</td>
						<td>
							<div class="text-input-wrap long">
							<input class="text-input" id="dft_spray_sle" name="dft_spray_sle" type="text" value="{{dft_spray_sle}}">
							</div>
						</td>
					</tr>
				</table>
				
				<br/>
				<div class="btn-wrap">
					<div class="basic-btn" on-click="closePop">닫기</div>
					<div class="basic-btn" on-click="regSave">저장</div> 
					
				</div>
				
			</div>
			{{/formdata}}
			<!--//테넌트 선택 팝업--> 
		</div>
		</div>

		</script>
	</div>
	<script src="/js/app/map/Floor.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
