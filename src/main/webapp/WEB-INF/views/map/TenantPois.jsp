<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/map-header.jsp" %>
<div class="container-fluid">
	<div class="row">
	  <div class="col-sm-12"><%@ include file="../common/map_tnb.jsp" %></div>	
	</div>
	<div class="row">
		<div class="col-sm-2"></div>
		<div class="col-sm-7">
		<h4>지도관리>POI정보 관리</h4>
		</div>
		<div class="col-sm-3">
		<div id="common-list" class="contents">
			<script id="tmpl-common-list" type="text/ractive">
			{{#commonFilter}}
			<select class="form-control input-sm" id="sh_bcn_cd" name="sh_bcn_cd" value={{sh_bcn_cd}}  on-change="chgBcn">
			{{#bcnList:num}}
			<option value={{bcn_cd}}>{{cond}}</option>
			{{/bcnList}}
        	</select>
			{{/commonFilter}}
			</script>
		</div>
        </div>
	</div>
	<div class=row>
	<div class="col-sm-2"><%@ include file="../common/map_lnb.jsp" %></div>	
	<div  class="col-sm-10">
		<div id="style-list">
		<script id="tmpl-style-list" type="text/ractive">
			{{#couponFilter}}			
			<div>
				 <table class="table table-striped">
                      <tbody>
                        <tr>
						  <td>
						  	<div class="col-xs-1"><label for="ex1">층</label></div>
						  	<div class="col-xs-2">
						  	<select class="form-control input-sm" id="sh_fl" name="sh_fl" value="{{sh_fl}}">
                    		<option value="">전체</option>
						    {{#floorList}}
							<option value={{fl}}>{{fl_nm}}</option>
							{{/floorList}}
                  			</select>	
						  	</div>
                          <div class="col-md-2 col-sm-2 col-xs-2">	
                  			<select class="form-control" id="sh_div_cd"  name="sh_div_cd" value="{{sh_div_cd}}">
							<option value="">전체</option>
							<option value="T">테넌트</option>
							<option value="P">편의시설</option>
							</select>    
				 			</div>	
					       <div class="col-xs-1">
						  	<button type="button" class="btn btn-primary btn-sm" on-click="shCoupon">검색</button>
						  </div>	
						   	
						  
		
						  </td>
							
						  <td>
						  	<div class="col-xs-4 navbar-right">
							    <div class="btn-group">
									<button type="button" class="btn  btn-info btn-sm" data-toggle="modal" data-target="#myModal" on-click=openPop:{{'Reg'}},{{'N'}},{{'N'}}>등록</button>
								</div>				                        
						  		
						  	</div>
						  </td>
						</tr>
                      </tbody>
                 </table>	
            </div>
			{{/couponFilter}}
				<div class="x_content">
                    <table class="table table-striped">
                      <thead>
                        <tr>
                        <th>테넌트/편의시설구분</th>
						<th>테넌트/편의시설명</th>
						<th>아이콘</th>
						<th>호수</th>
						<th>x좌표</th>
						<th>y좌표</th>
						<th>편의시설유형</th>
						<th>Action</th>
                        </tr>
                      </thead>
                      <tbody>
						{{#styleList:num}}
                        <tr>
						<td>{{div_cd}}</td>                  
						<td class="ellipsis ta-l" >{{tenant_poi_nm}}</td>              
						<td class="ellipsis ta-l" >{{icon}}</td>
						<td>{{room_num}}</td>                    
						<td>{{x_cord}}</td>
						<td class="ellipsis ta-r" >{{y_cord}}</td>              
						<td>{{poi_type}}</td>                           
						<td><a class="btn btn-info btn-sm"  data-toggle="modal" data-target="#myModal" on-click=openPop:{{'Edit'}},{{bcn_cd}},{{tenant_poi_seq}}>수정</a>&nbsp;<a  class="btn btn-info btn-sm" on-click=deleteTenantPoi:{{bcn_cd}},{{tenant_poi_seq}}>삭제</a></td>
						</tr>
						{{/styleList}}
                      </tbody>
                    </table>
					
                  </div>
			{{#paging}}
			<div class=" col-md-12 col-xs-12 dataTables_paginate paging_simple_numbers" style="text-align: center;display: inline-block;">
				<ul class="pagination pagination-sm">
				<li class="{{#if paging.page_start == 1}}disabled{{/if}}" on-click="pageMove:{{paging.page_start - 1}}"><a>PREV</a></li>
				{{#pages}}
				<li class="{{#if cur_page == this}}active{{/if}}" on-click="pageMove:{{this}}"><a>{{this}}</a></li>
				{{/pages}}
				<li class="{{#if paging.page_end == paging.total_page_cnt}}disabled{{/if}}" on-click="pageMove:{{paging.page_end + 1}}"><a>NEXT</a></li>
				</ul>
            </div>
			{{/paging}}
		</script>
	</div>
	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title" id="formtitl">Modal Header</h4>
	      </div>
	      <div class="modal-body">
	          <div id="style-dtl" class="contents">
			<script id="tmpl-style-dtl" type="text/ractive">
		<!-- form input mask -->
			<div id="ex1" name="tnt-reg-popup2" class="popup-wrap">	
			  {{#formdata}}
			  <input class="text-input" id="tenant_poi_seq" name="tenant_poi_seq" type="hidden" value="{{tenant_poi_seq}}">
				<input class="text-input" id="bcn_cd" name="bcn_cd" type="hidden" value="{{bcn_cd}}">
				<input class="text-input" id="fl_seq" name="fl_seq" type="hidden" value="{{fl_seq}}">		
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">					
                  <div class="x_content  well">
                    <br />
                    <form class="form-horizontal form-label-left">
					  <div class="form-group">
                        <label class=" control-label col-sm-4">테넌트/편의시설명</label>
                        <div class="col-sm-7">
                          <input type="text" class="form-control" name="tenant_poi_nm" id="tenant_poi_nm" value="{{tenant_poi_nm}}">
                        </div>
                      </div>	
					  <div class="form-group">
                        <label class="control-label col-sm-4">아이콘</label>
                        <div class="col-sm-7">
                          <input type="text" class="form-control" name="icon" id="icon" value="{{icon}}">
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-4">호수</label>
                        <div class="col-sm-7">
                          <input type="text" class="form-control" name="room_num" id="room_num" value="{{room_num}}">
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label  col-sm-4">테넌트/편의시설구분</label>
                        <div class="col-sm-5">
							<select class="form-control" id="div_cd"  name="div_cd" value="{{div_cd}}">
							<option value="">전체</option>
							<option value="T">테넌트</option>
							<option value="P">편의시설</option>
							</select>
                        </div>
                      </div> 	
					  <div class="form-group">
                        <label class="control-label col-sm-4">편의시설유형</label>
                        <div class="col-sm-5">
                          <select class="form-control" id="poi_type"  name="poi_type" value="{{poi_type}}">
							<option value="">선택</option>	
							<option value="info">안내</option>
							<option value="baby">유아</option>
							<option value="smoke">흡연실</option>
							<option value="toilet">화장실</option>
							<option value="elv">엘리베이터</option>
							<option value="esc">에스칼레이터</option>
							</select>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-4">좌표(X,Y)</label>
                        <div class="col-sm-2">
							<input type="text" class="form-control" name="x_cord" id="x_cord" value="{{x_cord}}">
                        </div>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="y_cord" id="y_cord" value="{{y_cord}}">
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5"></label>
                        <div class="col-md-5 col-sm-5 col-xs-5">
                          <button type="button" data-dismiss="modal" class="btn btn-success">닫기</button>
                          <button type="button" class="btn btn-success" data-dismiss="modal" on-click="regSave">저장</button>
                        </div>
                      </div>		
                      <div class="ln_solid"></div>
                    </form>
                  </div>
                </div>
              </div>
			  {{/formdata}}
			</div>	
			</script>
			</div>
	      </div>
	      <!-- Modal 본문 -->
	      <div class="modal-footer">
	        
	      </div>
	    </div>
	
	  </div>
	</div>
	<!-- 모달끝 -->
</div>
	<script src="/js/app/map/TenantPois.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
