<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/map-header2.jsp" %>
<style type="text/css">

.before .file-uploader-wrap {
	display: inline-block;
	vertical-align: middle;
}

</style> 
<div class="container-fluid">
	<div class="row">
	  <div class="col-sm-12"><%@ include file="../common/map_tnb.jsp" %></div>	
	</div>
	<div class="row">
		<div class="col-sm-2"></div>
		<div class="col-sm-10">
		<h4>맵 관리 > 맵파일 일괄 등록</h4>
		</div>
	</div>
	<div class=row>
	<div class="col-sm-2"><%@ include file="../common/map_lnb.jsp" %></div>	
	<div  class="col-sm-10">
		<div id="style-list" class="contents">
<div id="coupon-reg" class="contents" >
	<script id="tmpl-coupon-reg" type="text/ractive">
		<!-- form input mask -->
		<div id="ex1" name="tnt-reg-popup2" class="popup-wrap" >	
			  {{#coupon}}
			  <input class="text-input" id="fl_seq" name="fl_seq" type="hidden" value="{{fl_seq}}">
			  <input class="text-input" id="file_seq" name="file_seq" type="hidden" value="{{file_seq}}"> 		
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">					
                  <div class="x_content  well">
                    <br />
                    <form class="form-horizontal form-label-left">
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">층</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
							<select class="form-control" id="fl"  name="fl" value="{{fl}}">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
							</select>
                        </div>
                      </div> 	
					  
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5">맵파일</label>
                        <div class="col-md-2 col-sm-2 col-xs-2">
							<div class="img-uploader before" id="img_seq">
							<div class="img-preview">  
								<div class="icon-delete" on-click="deleteImg"></div>
								<img id="img_seq_uri" src="{{img_uri}}"> 
							</div> 
							<div class="file-uploader-wrap"> 
								<div class="basic-btn gold">파일선택</div>
							</div> 
						</div>
                        </div>
                      </div>
					  <div class="form-group">
                        <label class="control-label col-md-5 col-sm-5 col-xs-5"></label>
                        <div class="col-md-5 col-sm-5 col-xs-5">
                          <button type="button" class="btn btn-success" on-click="list">목록</button>
						  <button type="button" id="btnSave" class="btn btn-success" on-click="regCoupon">저장</button>
						  <button type="button" id="btnSave1" class="btn btn-success" on-click="regFloorBlockFromMapFile">블록정보일괄등록</button>		
                        </div>
                      </div>	
                      <div class="ln_solid"></div>
                    </form>
                  </div>
                </div>
              </div>
			  {{/coupon}}
</div>	
		</script>
	</div>
	</div>
</div>
	<script src="/js/lib/jquery.min.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/js/lib/ractive.js" type="text/javascript"></script>
    <script src="/js/lib/common.js" type="text/javascript"></script>
    <script src="/js/lib/file-input-button.js" type="text/javascript"></script>
    <script src="/js/3rd-party/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
	<script src="/js/app/map/FloorBlockUpload.js" type="text/javascript"></script>
	
<%@ include file="../common/footer.jsp" %>