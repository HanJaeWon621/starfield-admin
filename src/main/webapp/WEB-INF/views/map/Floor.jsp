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
		<h4>기본설정 > 층관리</h4>
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
						  	<div class="col-xs-1 navbar-right">                        
						  		<button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal" on-click=openPop:{{'Reg'}},{{'N'}},{{'N'}}>등록</button>
						  	</div>
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
                          <th class="no-order">층</th>
						<th class="no-order">층명</th>
						<th class="no-order">기본블록색</th>
						<th class="no-order">기본블록높이</th>
						<th class="no-order">기초판<br>사용여부</th>
						<th class="no-order">기초판높이</th>
						<th class="no-order">기초판색</th>
						<th class="no-order">기초판<br>offset</th>
						<th class="no-order">기본블록패딩</th>
						<th class="no-order">기본spray스케일</th>
						<th>Action</th>
                        </tr>
                      </thead>
                      <tbody>
						{{#styleList:num}}
                        <tr>
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
						<td><a  class="btn btn-info btn-sm"  data-toggle="modal" data-target="#myModal"  on-click=openPop:{{'Edit'}},{{bcn_cd}},{{fl_seq}}>수정</a>&nbsp;<a class="btn btn-info btn-sm"  on-click=deleteFloor:{{bcn_cd}},{{fl_seq}}>삭제</a>
						
					    </td>
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
	
	
	<div id="myModal" class="modal fade" role="dialog">
  		<div class="modal-dialog">

    	<!-- Modal content-->
    	<div class="modal-content">
	      	<div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title" id="formtitl">Modal Header</h4>
	      	</div>
	      	<div class="modal-body">
	        <p><!--등록폼 style="text-align: center; <label class="control-label col-md-1 col-sm-1 col-xs-1 right">층</label>"-->
				<div id="style-dtl" class="contents">
				<script id="tmpl-style-dtl" type="text/ractive">
				<div id="ex1" name="tnt-reg-popup2" class="popup-wrap" >	
			  {{#formdata}}
			  <input class="text-input" id="fl_seq" name="fl_seq" type="hidden" value="{{fl_seq}}">
			  <input class="text-input" id="bcn_cd" name="bcn_cd" type="hidden" value="{{bcn_cd}}">		
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">					
                  <div class="x_content  well">
                    <br />
                    <form class="form-horizontal form-label-left">
					  <div class="form-group">
                        <label class=" control-label col-md-5 col-sm-5 col-xs-5">층</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="fl" id="fl" value="{{fl}}">
                        </div>
                      </div>	
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">층명</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="fl_nm" id="fl_nm" value="{{fl_nm}}">
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">기본블럭색</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="dft_block_clr" id="dft_block_clr" value="{{dft_block_clr}}">
                        </div>
                      </div> 	
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">기본블럭높이</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="dft_block_hgt" id="dft_block_hgt" value="{{dft_block_hgt}}">
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">기초판사용여부</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="base_plate_yn" id="base_plate_yn" value="{{base_plate_yn}}">
                        </div>
                      </div>	
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">기초판높이</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="bs_plate_hgt" id="bs_plate_hgt" value="{{bs_plate_hgt}}">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">기초판색</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="bs_plate_clr" id="bs_plate_clr" value="{{bs_plate_clr}}">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">기초판Offset</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="bs_plate_oft" id="bs_plate_oft" value="{{bs_plate_oft}}">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">기본블럭Padding</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="dft_block_pdg" id="dft_block_pdg" value="{{dft_block_pdg}}">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">기본Spray스케일</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="dft_spray_sle" id="dft_spray_sle" value="{{dft_spray_sle}}">
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
				</div></p>
	      	</div>
	      	<div class="modal-footer">
	        
	      </div>
	    </div>
	
	  </div>
	</div>
	<!-- 모달끝 -->
</div>
	<script src="/js/app/map/Floor.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
