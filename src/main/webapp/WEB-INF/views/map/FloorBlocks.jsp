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
		<h4>지도 관리 > 층별 블록(존) 관리</h4>
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
		<div id="style-list" class="contents">
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
                    		<option value="">층선택</option>
						    {{#floorList}}
							<option value={{fl}}>{{fl_nm}}</option>
							{{/floorList}}
                  			</select>	
						  	</div>
                          
						  <div class="col-xs-1" ><label for="ex1">호수</label></div>
						  	<div class="col-xs-2">
						  	<input type="text" class="form-control input-sm" name="sh_room_num" id="sh_room_num" value="{{sh_room_num}}">
						   </div>
					       <div class="col-xs-1">
						  	<button type="button" class="btn btn-primary btn-sm" on-click="shCoupon">검색</button>
						  </div>	
						  
						  
		
						  </td>
						  <td>
							  <button type="button" class="btn  btn-info btn-sm" data-toggle="modal" data-target="#myModal" on-click=openPop:{{'Reg'}},{{'N'}},{{'N'}}>등록</button>
						  	  <button type="button" class="btn  btn-info btn-sm" on-click=moveFile>맵파일업로드</button>
							  <button type="button" class="btn  btn-info btn-sm" on-click=deleteAllFloorBlock>층별블록전체삭제</button>
							  
						  </td>
						 
						</tr>
                      </tbody>
                 </table>	
            </div>
			{{/couponFilter}}
			
			<div class="x_content">
                    <table class="table">
                      <thead>
                        <tr>
					    <th>층</th>
                        <th>호수</th>
					    <th>중복여부</th>
						<th>중복카운트</th>
						<th>맵좌표</th>
						<th>Action</th>
                        </tr>
                      </thead>
                      <tbody>
						{{#styleList:num}}
                        <tr>
					    <td>{{fl}}</td>
						<td>{{room_num}}</td>
						<td>{{dup_yn}}</td>
						<td>{{dup_disp_cnt}}</td>                 
						<td class="ellipsis ta-l">{{point_cord}}</td>                           
						<td><a class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal" on-click=openPop:{{'Edit'}},{{bcn_cd}},{{fb_seq}}>수정</a>&nbsp;<a class="btn btn-info btn-sm" on-click=deleteFloorBlock:{{bcn_cd}},{{fb_seq}}>삭제</a></td>
						</tr>
						{{/styleList}}
                      </tbody>
                    </table>
					
             </div>
			{{#paging}}
			<div class=" col-md-11 col-xs-11 dataTables_paginate paging_simple_numbers" style="text-align: center;display: inline-block;">
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
			  <input class="text-input" id="fb_seq" name="fb_seq" type="hidden" value="{{fb_seq}}">
				<input class="text-input" id="fl" name="fl" type="hidden" value="{{fl}}">
				<input class="text-input" id="fl_seq" name="fl_seq" type="hidden" value="{{fl_seq}}">
				<input type="hidden" class="form-control" name="data" id="data" value="{{data}}">		
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">					
                  <div class="x_content  well">
                    <br />
                    <form class="form-horizontal form-label-left">
					  <div class="form-group">
                        <label class=" control-label  col-sm-3 ">spray</label>
                        <div class="col-sm-9">
                          <input type="text" class="form-control" name="spray" id="spray" value="{{spray}}">
                        </div>
                      </div>	
					  <div class="form-group">
                        <label class="control-label col-sm-3">height</label>
                        <div class="col-sm-2">
                          <input type="text" class="form-control" name="height" id="height" value="{{height}}">
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">spray_offset</label>
                        <div class="col-sm-2">
							<input type="text" class="form-control" name="spray_oft_x" id="spray_oft_x" value="{{spray_oft_x}}">
                        </div>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="spray_oft_y" id="spray_oft_y" value="{{spray_oft_y}}">
                        </div>
                      </div>
						<div class="form-group">
                        <label class="control-label col-sm-3">spray_scale</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="spray_sle" id="spray_sle" value="{{spray_sle}}">
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">맵좌표셋</label>
                        <div class="col-sm-9">
                          <input type="text" class="form-control" name="point_cord" id="point_cord" value="{{point_cord}}">
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">호수</label>
                        <div class="col-sm-2">
                          <input type="text" class="form-control" name="room_num" id="room_num" value="{{room_num}}">
                        </div>
                      </div>	
					  <div class="form-group">
                        <label class="control-label col-sm-3">기타</label>
                        <div class="col-sm-9">
                          <input type="text" class="form-control" name="etc" id="etc" value="{{etc}}">
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
	      <div class="modal-footer">
	        
	      </div>
	    </div>
	
	  </div>
	</div>
	<!-- 모달끝 -->
</div>
		

	<script src="/js/app/map/FloorBlocks.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
