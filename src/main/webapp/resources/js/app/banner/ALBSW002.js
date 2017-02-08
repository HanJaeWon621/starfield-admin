
////////////////////////////////////////////////////////
// global variables

var bannerData  = {
	banner : {
		bn_post_type : 1,
		ord_seq : ''
	}, 
	couponList : null,
	eventList : null,
	paging : {}, 
	filter : {}
	
};


var rtvBanner= new Ractive({
	el : '#banner-detail',
	template : '#tmpl-banner-detail',
	data : bannerData 
});
 

rtvBanner.on({
	'getLinkPg' : function() {
		if(bannerData.banner.bn_exp_yn == 'Y'){
			return false;
		} 
		
		if(bannerData.banner.bn_post_type == '1'){
			getEvents(1,10);
		}else{
			getCoupons(1,10);
		}
	},
	'cpPageMove' : function(o, page) {
		if(page > 0 && page <= bannerData.paging.total_page_cnt && page != bannerData.paging.cur_page) {
			var offset = (page - 1) * bannerData.paging.list_limit + 1; 
			getCoupons(offset, bannerData.paging.list_limit);
		}
	},
	'evtPageMove' : function(o, page) {
		if(page > 0 && page <= bannerData.paging.total_page_cnt && page != bannerData.paging.cur_page) {
			var offset = (page - 1) * bannerData.paging.list_limit + 1; 
			getEvents(offset, bannerData.paging.list_limit);
		}
	},
	'closeCoupon' : function() {
		if(bannerData.banner.cp_seq != null && bannerData.banner.cp_seq != ''){
			$('input[value='+bannerData.banner.cp_seq+']').prop('checked',true);  
		}
		$("#couponP").hide();   
	},
	'selCoupon' : function() {   
		bannerData.banner.evt_seq = "";
		bannerData.banner.cp_seq = $('input:radio[name="cp_ck"]:checked').val();
		$("#linkPg").val($("td[id="+ bannerData.banner.cp_seq+"]").text()); 
		$("#couponP").hide();   
	}, 
	'closeEvent' : function() {
		if(bannerData.banner.evt_seq != null && bannerData.banner.evt_seq != ''){
			$('input[value='+bannerData.banner.evt_seq+']').prop('checked',true);  
		}
		$("#eventP").hide();   
	},
	'selEvent' : function() {   
		bannerData.banner.cp_seq = "";
		bannerData.banner.evt_seq = $('input:radio[name="evt_ck"]:checked').val();
		$("#linkPg").val($("td[id="+ bannerData.banner.evt_seq+"]").text()); 
		$("#eventP").hide();   
	}, 
	'saveBanner' : function() {   
		saveBanner(bannerData.banner);
	},
	'regBannerUnPosting' : function() {
		regPosting(bannerData.banner,"N");
	},
	'regBannerPosting' : function() {
		getOrdCnt(bannerData.banner);
	},
	'deleteImg' : function(o) { 
		$(o.node).parent().parent('div').attr('class','img-uploader before');
		var objId = $(o.node).parent().parent('div').attr('id');
		
		if(objId){
			bannerData.banner[objId] = '';
			$("img[id="+objId+"_uri]").attr('src','');
		}  
		rtvBanner.set(bannerData);
	},
	'chType' : function(o) {
		chType(); 
	},
	'preview' : function(o){
		preview();
	},
	'pv_close' : function(o){
		pv_close();
	}
});

var pv_close = function(){
	$("#banner-display-popup").hide();
} 

var preview = function(){
	if($("#bn_all_img_seq_uri").attr('src') && $("#bn_bi_img_seq_uri").attr('src')){
		$("#pv_imgf2").show();
		$("#pv_imgf1").hide();
		$("#pv_textf").hide();
		$("#pv_img2").attr('src',$("#bn_all_img_seq_uri").attr('src'));
	}else if($("#bn_all_img_seq_uri").attr('src')){
		$("#pv_imgf2").show();
		$("#pv_imgf1").hide();
		$("#pv_textf").hide(); 
		$("#pv_img2").attr('src',$("#bn_all_img_seq_uri").attr('src'));
	}else if($("#bn_bi_img_seq_uri").attr('src') || ( !$("#bn_all_img_seq_uri").attr('src') && !$("#bn_bi_img_seq_uri").attr('src') )){
		$("#pv_imgf2").hide(); 
		$("#pv_imgf1").show(); 
		$("#pv_textf").show();
		$("#pv_img1").attr('src',$("#bn_bi_img_seq_uri").attr('src'));
		
		if(bannerData.banner.bn_titl != null && bannerData.banner.bn_titl != ''){
			 
			$("#pv_title").text(bannerData.banner.bn_titl);
		}
		
		if($("#bn_post_strt_dt").val() != null && $("#bn_post_strt_dt").val() != '' &&
				$("#bn_post_end_dt").val() != null && $("#bn_post_end_dt").val() != ''){
			var bn_post_strt_dt = $("#bn_post_strt_dt").val();
			var bn_post_end_dt = $("#bn_post_end_dt").val();
			if($("#bn_post_strt_dt").val().length == 8){  
				bn_post_strt_dt = $("#bn_post_strt_dt").val().substr(0,4) + "." + $("#bn_post_strt_dt").val().substr(4,2) + "." + $("#bn_post_strt_dt").val().substr(6);
				bn_post_end_dt = $("#bn_post_end_dt").val().substr(0,4) + "." + $("#bn_post_end_dt").val().substr(4,2) + "." + $("#bn_post_end_dt").val().substr(6);
			}
			  
			var text = "기간 " + bn_post_strt_dt + " - " +bn_post_end_dt
			$("#pv_date").text(text);
		}
	}
	
	
	$("#banner-display-popup").show();
}

var chType = function(){
	bannerData.banner.evt_seq = "";
	bannerData.banner.cp_seq = "";
	$("#linkPg").val('');
}

var regPosting = function(param,flag){
	var restUri = '/rest/01/regBannerPosting/'+flag;
	Common.REST.post(window.location.origin + restUri, param, function(data) {
		location.href='/01/modifyBanner/'+ param.bn_seq;
		 
	}, function(data) {
		console.log('fail data is ', data);
		 
	});
}

var getCoupons =  function(offset, limit){
	bannerData.filter.cp_titl = '';
	$("#cp_titl").val('');
	
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/1/coupons';
	restUri += '?'; 
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit;
	restUri += '&cp_titl=' + bannerData.filter.cp_titl;
	Common.REST.get(restUri, {}, function(data) {
		bannerData.couponList = data.list;
		bannerData.paging = data.paging;
				
		bannerData.paging.pages = [];
		
		for(var i = bannerData.paging.page_start; i <= bannerData.paging.page_end; ++i) {
			bannerData.paging.pages.push(i);
        }
		rtvBanner.set(bannerData);
		
		$("#couponP").show();

	}, function(data) {
		 
		console.log('fail data is ', data);
		
	}); 
};


var getEvents = function(offset, limit){
	bannerData.filter.evt_titl = '';
	$("#evt_titl").val('');
	
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getEvents';
	restUri += '?'; 
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit;
	restUri += '&evt_titl=' + bannerData.filter.evt_titl;
	Common.REST.get(restUri, {}, function(data) {
		bannerData.eventList = data.list;
		bannerData.paging = data.paging;
				
		bannerData.paging.pages = [];
		
		for(var i = bannerData.paging.page_start; i <= bannerData.paging.page_end; ++i) {
			bannerData.paging.pages.push(i);
        }
		rtvBanner.set(bannerData);
		
		$("#eventP").show();

	}, function(data) {
		 
		console.log('fail data is ', data);
		
	}); 
}


var saveBanner = function(param){
	param.bn_post_strt_dt    = 	$("#bn_post_strt_dt").val().split('-').join("");
	param.bn_post_end_dt     = 	$("#bn_post_end_dt").val().split('-').join("");
  
	if(!validation(param)){
		return false;
	} 
	
	var restUri = '/rest/01/regBanner/C';
	if(getUriArrVal()[3]){ 
		restUri = '/rest/01/regBanner/U';
	}
	Common.REST.post(window.location.origin + restUri, param, function(data) {
		location.href='/01/modifyBanner/'+ data.extra;
		
	}, function(data) {
		console.log('fail data is ', data);
		
	}); 
}


var getBanner = function(bn_seq){
	
	var restUri = '/rest/01/banner/'+bn_seq;
	Common.REST.get(restUri, {}, function(data) {
		bannerData.banner = data; 
		
		rtvBanner.set(bannerData);
		  
		$("#linkPg").val(data.link_pg_titl);

		if(data.bn_exp_yn == 'N'){
			$("#btnPostion").show(); 
			$("#btnSave").show(); 
		}else{
			$("#btnSave").hide(); 
			$("#btnPostion").hide();
			$("#btnUnPostion").show();  
			$('input').attr("disabled",true); 
			$('select').attr("disabled",true); 
		}
		 
	}, function(data) { 
		console.log('fail data is ', data);
		
	});
}


var getOrdCnt = function(param){ 
	param.bn_post_strt_dt    = 	$("#bn_post_strt_dt").val().split('-').join("");
	param.bn_post_end_dt     = 	$("#bn_post_end_dt").val().split('-').join("");
	
	var restUri = '/rest/01/banner/ord';
	Common.REST.post(window.location.origin + restUri, param, function(data) {
		if(data > 0){ 
			if(!confirm(param.ord_seq+"번째 정렬 순서에는 이미 등록된 배너가 있습니다. 등록 하시겠습니까?")){
				return false;
			}else{ 
				regPosting(bannerData.banner,"Y");
			}
		}else{ 
			regPosting(bannerData.banner,"Y");
		} 
	}, function(data) { 
		console.log('fail data is ', data);
		
	}); 
	
}


//이미지 업로드 버튼 설정 
var initTitleImgUploadButton = function() {
	var $target = null;
	
	var beforStart = function(e, data, cb) {
		$target = $(e.target);
   		
     	var file_name = data.files[0].name;
        var extension = file_name.substring(file_name.lastIndexOf('.')+1).toLowerCase();

        var clear_extensions = 'jpeg/jpg/png';

        if(clear_extensions.search(extension) === -1) {
            alert('이미지는 jpg, png만 가능합니다.');
            return;
        }

        if(typeof cb === 'function') {
            cb();
        }
	};
	
	var done = function(r) {

		var $targetParent = $target.parents(".img-uploader");
		$targetParent.removeClass("before").addClass("after");
		bannerData.banner[$targetParent.attr("id")] = r.img_seq;
		$("img[id="+ $targetParent.attr("id")+"_uri" +"]").attr('src',r.img_uri);
	};
	
	
	var fail = function(e){
		alert("이미지 등록 오류");
	};
	
	var params = {
			url : '/rest/01/file/images', //upload url
		 	beforeStart : beforStart, // called before start uploading
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	};
 	
	$('.file-uploader-wrap > .basic-btn').each(function(){
		$(this).fileInputButton(params);
	});
}



var validation = function(data){
	bannerData.banner.bn_post_strt_dt           = 	$("#bn_post_strt_dt").val().split('-').join("");
	bannerData.banner.bn_post_end_dt            = 	$("#bn_post_end_dt").val().split('-').join("");
	
	if(data.bn_post_type == null || data.bn_post_type == '') {
		alert("필수입력 내용이 누락되었습니다.필수입력 내용이 누락될 경우 저장 되지않습니다.");
		return false;
	}else if(data.bn_titl == null || data.bn_titl == '') {
		alert("필수입력 내용이 누락되었습니다.필수입력 내용이 누락될 경우 저장 되지않습니다.");
		return false; 
	}else if(data.ord_seq == null || data.ord_seq == '') {
		alert("필수입력 내용이 누락되었습니다.필수입력 내용이 누락될 경우 저장 되지않습니다.");
		return false;
	}else if(data.bn_post_strt_dt == null || data.bn_post_strt_dt == '') {
		alert("필수입력 내용이 누락되었습니다.필수입력 내용이 누락될 경우 저장 되지않습니다.");
		return false;
	}else if(data.bn_post_end_dt == null || data.bn_post_end_dt == '') {
		alert("필수입력 내용이 누락되었습니다.필수입력 내용이 누락될 경우 저장 되지않습니다.");
		return false;
	}else if(data.bn_post_strt_dt > data.bn_post_end_dt) {
		alert("필수입력 내용이 누락되었습니다.필수입력 내용이 누락될 경우 저장 되지않습니다.");
		return false;
	}else if((data.bn_bi_img_seq == null || data.bn_bi_img_seq == '')
				&& (data.bn_all_img_seq == null || data.bn_all_img_seq == '')) {
		alert("필수입력 내용이 누락되었습니다.필수입력 내용이 누락될 경우 저장 되지않습니다.");
		return false;
	}else if(data.bn_post_type == '1' && (data.evt_seq == '' || data.evt_seq == null)) {
		alert("필수입력 내용이 누락되었습니다.필수입력 내용이 누락될 경우 저장 되지않습니다.");
		return false;
	}else if(data.bn_post_type == '2' && (data.cp_seq == '' || data.cp_seq == null)) {
		alert("필수입력 내용이 누락되었습니다.필수입력 내용이 누락될 경우 저장 되지않습니다.");
		return false;
	}else if(data.bn_dtl_cts == null || data.bn_dtl_cts == '') {
		alert("필수입력 내용이 누락되었습니다.필수입력 내용이 누락될 경우 저장 되지않습니다.");
		return false;
	}
	
	return true;
}


$(function() {  
	initTitleImgUploadButton();
	datepicker();
	if(getUriArrVal()[3]){ 
		getBanner(getUriArrVal()[3]);
	} 
});


