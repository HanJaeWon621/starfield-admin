<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/map-header.jsp" %>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-1"></div>
		<div class="col-sm-8">
		<h4>지도 관리 > 층별 블록(존) 관리</h4>
		</div>
	</div>
	<div class=row>
	<div class="col-sm-1"></div>	
	<div  class="col-sm-12">
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
                          <button type="button" on-click='close'>닫기</button>
                          <button type="button"  on-click="regSave">저장</button>
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
		

	<script src="/js/app/map/FloorBlockSingle.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
