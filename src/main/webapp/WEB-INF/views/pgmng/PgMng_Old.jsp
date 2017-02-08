<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/map-header.jsp" %>
	<%@ include file="../common/gnb.jsp" %> 
<div class=row>
		<div class="col-md-2 col-sm-2 col-xs-2 form-group">
			<%@ include file="../common/map_gnb.jsp" %>
		</div>
		<div  class="col-md-10 col-sm-10 col-xs-10 form-group">
		<div id="style-list" class="contents">
		<script id="tmpl-style-list" type="text/ractive">
		<div class="row">
			<div class="col-md-6">
				<h3>프로그램 관리</h3>
			</div>
		</div>
			{{#couponFilter}}
			<div class="well" style="overflow: auto">
				<form class="form-horizontal form-label-left">
				 <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-2">프로그램명</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <input type="text" class="form-control" name="sh_pg_nm" id="sh_style_nm" value="{{sh_pg_nm}}">
                        </div>
						<div class="col-md-1 col-sm-1 col-xs-1">
					   	<button type="button" class="btn btn-primary" on-click="shCoupon">검색</button>
                 		</div>
						<div class="col-md-7 col-sm-7 col-xs-7" style="text-align: right;display: inline-block;">
					   	
						<a href="#ex1" rel="modal:open"><button type="button" class="btn"  on-click=openPop:{{'Reg'}},{{'N'}},{{'N'}}>등록</button></a>
                 		</div>
                 </div>	
				 
				 </form>
            </div>
			{{/couponFilter}}
			<div class="row">
					<div class="col-md-5 col-sm-5 col-xs-5">		
			<div class="x_content">
					
                    <table class="table table-striped">
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
						<td><a href="#ex2" rel="modal:open" on-click=openPop:{{'View'}},{{pg_info_seq}}>[View]</a><a href="#ex1" rel="modal:open" on-click=openPop:{{'Edit'}},{{pg_info_seq}}>[Edit]</a><a href="javascript:;" on-click=deletePgMng:{{pg_info_seq}}>[Del]</a></td>                           
						</tr> 
						{{/styleList}}
                      </tbody>
                    </table>
					
             </div>
			{{#paging}}
			<div class=" col-md-12 col-xs-12 dataTables_paginate paging_simple_numbers" style="text-align: center;display: inline-block;">
				<ul class="pagination">
				<li class="paginate_button previous {{#if paging.page_start == 1}}disabled{{/if}}" on-click="pageMove:{{paging.page_start - 1}}">PREV</li>
				{{#pages}}
				<li class="paginate_button {{#if cur_page == this}}active{{/if}}" on-click="pageMove:{{this}}">{{this}}</li>
				{{/pages}}
				<li class="paginate_button next {{#if paging.page_end == paging.total_page_cnt}}disabled{{/if}}" on-click="pageMove:{{paging.page_end + 1}}">NEXT</li>
				</ul>
            </div>
			{{/paging}}
			</div>
			</div>
		</script>
	</div>
	<div id="style-dtl" class="main_container">
		<script id="tmpl-style-dtl" type="text/ractive">
		<!-- form input mask -->
		<div id="ex1" name="tnt-reg-popup2" class="popup-wrap" style="display:none">	
			  {{#formdata}}
			  <input class="text-input" id="pg_info_seq" name="pg_info_seq" type="hidden" value="{{pg_info_seq}}">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title" style="text-align: center;">
					<ul class="nav navbar-right panel_toolbox">
                    </ul>
                    <div class="clearfix"></div>
					
                    <h2 id="formtitl">생성</h2>
                  </div>
					
                  <div class="x_content  well">
                    <br />
                    <form class="form-horizontal form-label-left">
					  <div class="form-group">
                        <label class=" control-label col-md-5 col-sm-5 col-xs-5">프로그램명</label>
                        <div class="col-md-4 col-sm-4 col-xs-4">
                          <input type="text" class="form-control" name="pg_nm" id="pg_nm" value="{{pg_nm}}">
                        </div>
                      </div>	
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">표시순서/계층/메뉴여부/구분</label>
                        <div class="col-md-1 col-sm-1 col-xs-1">
                          <input type="text" class="form-control" name="disp_sn" id="disp_sn" value="{{disp_sn}}">
                        </div>
						<div class="col-md-2 col-sm-2 col-xs-2">
						  <select class="form-control" id="disp_depth" name="disp_depth" value="{{disp_depth}}">						
							<option value=1>1</option>
							<option value=2>2</option>
							<option value=3>3</option>
						  </select>	
                        </div>
						<div class="col-md-2 col-sm-2 col-xs-2">
					      <select class="form-control" id="menu_yn" name="menu_yn" value={{menu_yn}}>						
							<option value=Y>예</option>
							<option value=N>아니오</option>
						  </select>
                        </div>
						<div class="col-md-2 col-sm-2 col-xs-2">
						  <select class="form-control" id="pg_div" name="pg_div" value={{pg_div}}>						
							<option value=ADM>ADMIN</option>
							<option value=API>API</option>
							<option value=BAT>배치</option>
						  </select>	
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">기능개요</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <textarea id='func_desc' name='func_desc' rows="2" cols="80" value="{{func_desc}}"></textarea>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">관련소스</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <textarea id='rel_src_items' name='rel_src_items' rows="3" cols="80" value="{{rel_src_items}}"></textarea>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">기능목록</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <textarea id='func_list_desc' name='func_list_desc' rows="3" cols="80" value="{{func_list_desc}}"></textarea>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">DB사용객체</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <textarea id='use_db_object' name='use_db_object' rows="2" cols="80" value="{{use_db_object}}"></textarea>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">특이사항</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <textarea id='etc_desc' name='etc_desc' rows="2" cols="80" value="{{etc_desc}}"></textarea>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">프로그램URL</label>
                        <div class="col-md-5 col-sm-5 col-xs-5">
                          <input type="text" class="form-control" name="pg_url" id="pg_url" value={{pg_url}}>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5"></label>
                        <div class="col-md-5 col-sm-5 col-xs-5">
                           <a href="#ex1" rel="modal:close"><button type="button" class="btn btn-success">닫기</button></a>	
                          <button type="button" class="btn btn-success" on-click="regSave">저장</button>
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
	
	<div id="dtl2" class="main_container">
		<script id="tmpl-dtl2" type="text/ractive">
		<!-- form input mask -->
		<div id="ex2" name="tnt-reg-popup3" class="popup-wrap" style="display:none">	
			  {{#formdata}}
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title" style="text-align: center;">
					<ul class="nav navbar-right panel_toolbox">
                    </ul>
                    <div class="clearfix"></div>
					
                    <h2 id="formtitl">생성</h2>
                  </div>
					
                  <div class="x_content  well">
                    <br />
                    <form class="form-horizontal form-label-left">
					  <div class="form-group">
                        <label class=" control-label col-md-5 col-sm-5 col-xs-5">프로그램명</label>
                        <div class="col-md-4 col-sm-4 col-xs-4">
                          <input type="text" class="form-control" name="pg_nm" id="pg_nm" value="{{pg_nm}}">
                        </div>
                      </div>	

					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">설명</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
                          <textarea id='expl' name='expl' rows="20" cols="80" value="{{expl}}"></textarea>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5"></label>
                        <div class="col-md-5 col-sm-5 col-xs-5">
                           <a href="#ex2" rel="modal:close"><button type="button" class="btn btn-success">닫기</button></a>	
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
</div>
	<script src="/js/app/pgmng/PgMng.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
