<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/map-header2.jsp" %>
<div class="container-fluid">

	<div class="row">
	  <div class="col-sm-12"><%@ include file="../common/map_tnb.jsp" %></div>	
	</div>
	<div class="row">
		<div class="col-sm-2"></div>
		<div class="col-sm-7">
				<h4>기본 설정 > 스타일 관리</h4>
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
						  	<div class="col-xs-2"><label for="ex1">스타일이름</label></div>
						  	<div class="col-xs-3">
						  	 <input type="text" class="form-control input-sm" name="sh_style_nm" id="sh_style_nm" value="{{sh_style_nm}}">
						  	</div>
                          
						  <div class="col-xs-1">
						  	<button type="button" class="btn btn-primary btn-sm" on-click="shCoupon">검색</button>
						  </div>
						  
						  </td>
						  <td>
						  	<div class="col-xs-9 navbar-right">                        
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
                        <th>Style Name</th>
						<th>Description</th>
						<th>Background-Color</th>
						<th>Border-Size</th>
						<th>Border-Color</th>
						<th>Extra Style</th>
						<th>Important</th>
						<th>Action</th>
                        </tr>
                      </thead>
                      <tbody>
						{{#styleList:num}}
                        <tr>
						<td class="ellipsis ta-l" >{{style_nm}}</td>              
						<td class="ellipsis ta-l" >{{style_comment}}</td>       
						<td class="ellipsis ta-l" >{{bg_color}}</td>     
						<td class="ellipsis ta-l" >{{bd_size}}</td>              
						<td class="ellipsis ta-l" >{{bd_color}}</td>          
						<td class="ellipsis ta-l" >{{ext_style}}</td>                
						<td>{{important_cd}}</td>                           
						<td><a  class="btn btn-info btn-sm"  data-toggle="modal" data-target="#myModal" on-click=openPop:{{'Edit'}},{{style_set_seq}}>수정</a>&nbsp;<a class="btn btn-info btn-sm"  on-click=deleteStyle:{{style_set_seq}}>삭제</a></td>                           
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
			  <input class="text-input" id="style_set_seq" name="style_set_seq" type="hidden" value="{{style_set_seq}}">
			  <input class="text-input" id="bcn_cd" name="bcn_cd" type="hidden" value="{{bcn_cd}}">			
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">					
                  <div class="x_content  well">
                    <br />
                    <form class="form-horizontal form-label-left">
					  <div class="form-group">
                        <label class=" control-label col-sm-3">Style Name</label>
                        <div class="col-sm-5">
                          <input type="text" class="form-control" name="style_nm" id="style_nm" value="{{style_nm}}">
                        </div>
                      </div>	
					  <div class="form-group">
                        <label class="control-label col-sm-3">Description</label>
                        <div class="col-sm-9">
                          <input type="text" class="form-control" name="style_comment" id="style_comment" value="{{style_comment}}">
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">Background-Color</label>
                        <div class="col-sm-4">
					      <div class="input-group demo2">
                          <input type="text" class="form-control" name="bg_color" id="bg_color" value={{bg_color}}>
						  <span class="input-group-addon" id="bg_color_out"></span>
						  </div> 	
                        </div>
                      </div>
						<div class="form-group">
                        <label class="control-label col-sm-3">Border-Size</label>
                        <div class="col-sm-3">
						  
                          <input type="text" class="form-control" name="bd_size" id="bd_size" value="{{bd_size}}">
						  
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">Border-Color</label>
                        <div class="col-sm-4">
					       <div class="input-group bd_color">
                          <input type="text" class="form-control" name="bd_color" id="bd_color" value="{{bd_color}}">
							<span class="input-group-addon" id="bd_color_out"></span>
					      </div>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">Important</label>
                        <div class="col-sm-2">
						  <select class="form-control input-sm" id="important_cd" name="important_cd" value={{important_cd}}>
							<option value=N>N</option>
						    <option value=Y>Y</option>			
        				  </select>	
                        </div>
							
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">Extra Style</label>
                        <div class="col-sm-9">
                          <textarea class="form-control input-sm" id='ext_style' name='ext_style' rows="5"  value="{{ext_style}}"></textarea>
                        </div>
                      </div>	
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5"></label>
                        <div class="col-md-5 col-sm-5 col-xs-5">
                          <button type="button" data-dismiss="modal" class="btn btn-success">닫기</button>
                          <button type="button" class="btn btn-success" data-dismiss="modal" on-click="regStyle">저장</button>
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
	<script src="/js/app/style/StyleSets.js" type="text/javascript"></script>
	<script src="/js/lib/jquery.min.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <script src="/js/lib/common.js" type="text/javascript"></script>
    <script src="/js/lib/bootstrap-colorpicker.min.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
