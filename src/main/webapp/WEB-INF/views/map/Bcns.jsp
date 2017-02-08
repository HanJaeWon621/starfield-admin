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
		<h4>맵 관리 > 지점관리</h4>
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
                        <th class="navbar-center">지점코드</th>
						<th>지점명</th>
						<th>지점주소</th>
						<th>지점전화번호</th>
						<th>Action</th>
                        </tr>
                      </thead>
                      <tbody>
						{{#styleList:num}}
                        <tr>
						
						<td><div class="col-xs-1">{{bcn_cd}}</div></td>
						
						<td><div class="col-xs-10">{{bcn_nm}}</div></td>                    
						<td class="ellipsis ta-l">{{bcn_addr}}</td>
						<td>{{bcn_tel}}</td>                           
						<td><a  class="btn btn-info btn-sm"  data-toggle="modal" data-target="#myModal"   on-click=openPop:{{'Edit'}},{{bcn_seq}}>수정</a>&nbsp;<a class="btn btn-info btn-sm"  on-click=deleteBcn:{{bcn_seq}}>삭제</a>
						<a class="btn btn-info btn-sm"  href="/{{bcn_cd}}/map/indoor/view" target=_blank>미리보기</a>
						<a class="btn btn-info btn-sm"  href="/{{bcn_cd}}/map/indoor/edit" target=_blank>편집보기</a>
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
            
			{{/paging}}
		</script>
		</div>	
	</div>
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
			        <div id="ex2" name="tnt-reg-popup2" class="popup-wrap">	
					  {{#formdata}}
					  <input class="text-input" id="bcn_seq" name="bcn_seq" type="hidden" value="{{bcn_seq}}">	
		              <div class="col-md-12 col-sm-12 col-xs-12">
		                <div class="x_panel">							
		                  <div class="x_content  well">
		                    <form class="form-horizontal form-label-left">
							  <div class="form-group">
		                        <label class=" control-label col-sm-5">지점코드</label>
		                        <div class="col-sm-2">
		                          <input type="text" class="form-control" name="bcn_cd" id="bcn_cd" value="{{bcn_cd}}">
		                        </div>
		                      </div>	
							  <div class="form-group">
		                        <label class="control-label col-sm-5">지점명</label>
		                        <div class="col-sm-7">
		                          <input type="text" class="form-control" name="bcn_nm" id="bcn_nm" value="{{bcn_nm}}">
		                        </div>
		                      </div>
							 
							  <div class="form-group">
		                        <label class="control-label col-sm-5">지점주소</label>
		                        <div class="col-sm-7">
		                          <input type="text" class="form-control" name="bcn_addr" id="bcn_addr" value="{{bcn_addr}}">
		                        </div>
		                      </div>
							  <div class="form-group">
		                        <label class="control-label col-sm-5">지점전화번호</label>
		                        <div class="col-sm-5">
		                          <input type="text" class="form-control input-sm" name="bcn_tel" id="bcn_tel" value="{{bcn_tel}}">
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
		

	<script src="/js/app/map/Bcns.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
