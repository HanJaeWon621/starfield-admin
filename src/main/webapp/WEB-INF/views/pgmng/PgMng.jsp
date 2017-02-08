<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/map-header.jsp" %>
<div class="container-fluid">
	<div class="row">
	  <div class="col-sm-12"><%@ include file="../common/map_tnb.jsp" %></div>	
	</div>
	<div class="row">
		<div class="col-sm-2"></div>
		<div class="col-sm-10">
		<h4>시스템관리 > 프로그램관리</h4>
		</div>
	</div>
	<div class=row>
	<div class="col-sm-2"><%@ include file="../common/map_lnb.jsp" %></div>	
	<div  class="col-sm-10">
		<div id="style-list" class="contents">
		<script id="tmpl-style-list" type="text/ractive">
			{{#filter}}
			<div>
				 <table class="table table-striped">
                      <tbody>
                        <tr>
						  <td>
						  	<div class="col-xs-2"><label for="ex1">프로그램명</label></div>
						  	<div class="col-xs-3">
						  	 <input type="text" class="form-control input-sm" name="sh_pg_nm" id="sh_style_nm" value="{{sh_pg_nm}}">
						  	</div>
                          
						  <div class="col-xs-1">
						  	<button type="button" class="btn btn-primary btn-sm" on-click="shCoupon">검색</button>
						  </div>
						  
						  </td>
						  <td>
						  	<div class="col-xs-7 navbar-right">                        
						  		<button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal" on-click=openPop:{{'Reg'}},{{'N'}},{{'N'}}>등록</button>
								<button type="button" class="btn btn-info btn-sm" on-click=getDataExcelList>엑셀다운로드</button>
						  	</div>
						  </td>
						</tr>
                      </tbody>
                 </table>	
            </div>
			{{/filter}}
			</script>
			</div>
			<div class="row">
			   <div class="col-md-5 col-sm-5 col-xs-5">				
			<div id="data-list" class="contents">
			<script id="tmpl-data-list" type="text/ractive">
					
                 <div class="x_content">
                   <table class="table">
                     <thead>
                       <tr>
                       <th>프로그램명</th>
                 	<th>Action</th>
                       </tr>
                     </thead>
                     <tbody>
                 	{{#styleList:num}}
                       <tr>
                 	<td class="ellipsis ta-l" >{{pg_nm}}</td>                                             
                 	<td><a  class="btn btn-info btn-sm" on-click=openPop:{{'View'}},{{pg_info_seq}}>상세조회</a>
						
						<a  class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal" on-click=openPop:{{'Edit'}},{{pg_info_seq}}>수정</a>&nbsp;
						
						<a  class="btn btn-info btn-sm" on-click=deletePgMng:{{pg_info_seq}}>삭제</a></td>                           
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
			</div>
			<div class="col-md-7 col-sm-7 col-xs-7">
				
				<div id="dtl2" class="main_container">
				<script id="tmpl-dtl2" type="text/ractive">
		<!-- form input mask -->
        	<div id="ex2" name="tnt-reg-popup3" class="popup-wrap">	
        		  {{#formdata}}
                  <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_panel">
                      <div class="x_content  well">
                        <form class="form-horizontal form-label-left">
        				  <div class="form-group">
                            <label class=" control-label col-sm-3">프로그램명</label>
                            <div class="col-sm-5">
                              <input type="text" class="form-control" name="pg_nm" id="pg_nm" value="{{pg_nm}}">
                            </div>
                          </div>	
        
        				  <div class="form-group">
                            <label class="control-label col-sm-3">설명</label>
                            <div class="col-sm-9">
                              <textarea class="form-control input-sm" id='expl' name='expl' rows="15"  value="{{expl}}"></textarea>
                            </div>
                          </div>		
                        </form>
                      </div>
                    </div>
                  </div>
        		  {{/formdata}}
        	</div>	
        	</script>
	        </div>
				
				</div>
			</div>
	
	</div>
</div>
<!-- Trigger the modal with a button -->


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
        <div id="style-dtl" class="main_container">
		<script id="tmpl-style-dtl" type="text/ractive">
		<!-- form input mask -->
		<div id="ex1" name="tnt-reg-popup2" class="popup-wrap" style="display:block">	
			  {{#formdata}}
			  <input class="text-input" id="pg_info_seq" name="pg_info_seq" type="hidden" value="{{pg_info_seq}}">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
					
                  <div class="x_content  well">
                    <form class="form-horizontal form-label-left">
					  <div class="form-group">
    					<label class="control-label col-sm-3">프로그램명</label>
                        <div class="col-sm-9">
                        <input type="text" class="form-control input-sm" name="pg_nm" id="pg_nm" value="{{pg_nm}}">
                        </div>
					  </div>	
					  <div class="form-group">
                        <label class="control-label col-sm-3">표시순서/계층</label>
                        <div class="col-sm-2">
                          <input type="text" class="form-control" name="disp_sn" id="disp_sn" value="{{disp_sn}}">
                        </div>
						<div class="col-sm-2">
						  <select class="form-control" id="disp_depth" name="disp_depth" value="{{disp_depth}}">						
							<option value=1>1</option>
							<option value=2>2</option>
							<option value=3>3</option>
						  </select>	
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">메뉴여부/구분</label>
						<div class="col-sm-3">
					      <select class="form-control" id="menu_yn" name="menu_yn" value={{menu_yn}}>						
							<option value=Y>예</option>
							<option value=N>아니오</option>
						  </select>
                        </div>
						<div class="col-sm-3">
						  <select class="form-control" id="pg_div" name="pg_div" value={{pg_div}}>						
							<option value=ADM>ADMIN</option>
							<option value=API>API</option>
							<option value=BAT>배치</option>
						  </select>	
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">기능개요</label>
                        <div class="col-sm-9">
                          <textarea class="form-control input-sm" id='func_desc' name='func_desc' data-parsley-trigger="keyup" data-parsley-minlength="20" data-parsley-maxlength="100" value="{{func_desc}}"></textarea>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">관련소스</label>
                        <div class="col-sm-9">
                          <textarea class="form-control input-sm" id='rel_src_items' name='rel_src_items' value="{{rel_src_items}}"></textarea>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">기능목록</label>
                        <div class="col-sm-9">
                          <textarea class="form-control input-sm" id='func_list_desc' name='func_list_desc'  value="{{func_list_desc}}"></textarea>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">DB사용객체</label>
                        <div class="col-sm-9">
                          <textarea class="form-control input-sm" id='use_db_object' name='use_db_object'  value="{{use_db_object}}"></textarea>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-sm-3">특이사항</label>
                        <div class="col-xs-9">
                          <textarea class="form-control input-sm" id='etc_desc' name='etc_desc'  rows="3"  value="{{etc_desc}}"></textarea>
                        </div>
                      </div>
					  <div class="form-group">
                      <label class="control-label col-sm-3" for="pg_url">프로그램 URL</label>
                      <div class="col-sm-9"> 
						<input type="text" class="form-control input-sm" name="pg_url" placeholder="Url을 입력하세요." id="pg_url" value={{pg_url}}>
                      </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5"></label>
                        <div class="col-md-5 col-sm-5 col-xs-5">
                           <a href="#ex1" rel="modal:close">
							<button type="button" class="btn btn-success" data-dismiss="modal">닫기</button>
							</a>	
                          <a href="javascript:;" ><button type="button" class="btn btn-success" data-dismiss="modal" on-click="regSave">저장</button></a>
                        </div>
                      </div>		
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
	<script src="/js/app/pgmng/PgMng.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
