
////////////////////////////////////////////////////////
// global variables
var arrtenantNum = [];
var couponData  = {
	tenantChkList : null,
	fileType : null,
	coupon : {
		fl : '',
		file_seq : null,
		fl_seq : null
	}, 
	tenantList : null,
	paging : {},
	filter: {
  		tnt_nm_ko : null,
 		cate_seq : null,
 		categoryList : null
	}
};


////////////////////////////////////////////////////////
// global functions

////////////////////////////////////////////////////////
//initialize

var rtvCoupon = new Ractive({
	el : '#coupon-reg',
	template : '#tmpl-coupon-reg',
	data : couponData
});


rtvCoupon.on({
	'imgBtnT' : function(o,type) { 
		couponData.fileType = type;
	},
	'list' : function(o) {
		var bcn_cd = getUriArrVal()[1];	
		location.href=window.location.origin + "/map/floorblocks";	
	},
	'regCoupon' : function(o) {
		regCoupon(couponData.coupon);
	},
	'chgFloor' : function(o) {
		regCoupon(couponData.coupon);	
	},
	'regFloorBlockFromMapFile' : function(o) {
		regFloorBlockFromMapFile(couponData.coupon);
	}
});


var regCoupon = function(data){
	
	if(!validation(couponData.coupon)){
		return false;
	}
	
	
	//alert(couponData.coupon.fl);
	//alert(couponData.coupon.file_seq);
	var bcn_cd = getUriArrVal()[1];
	var url = "/rest/modifyFloorFileSeq";
	
	Common.REST.post(window.location.origin + url, data, function(data) {
		alert('등록완료!!');  
		//location.href='/admin/modifyCoupon/nm/' + data.extra;
		
	}, function(data) {
		console.log('fail data is ', data);
	});
} 
var regFloorBlockFromMapFile = function(data){
	
	if(!validation(couponData.coupon)){
		return false;
	}
	
	
	//alert(couponData.coupon.fl);
	//alert(couponData.coupon.file_seq);
	var bcn_cd = getUriArrVal()[1];
	var url = "/rest/regFloorBlockFromMapFile";
	
	Common.REST.post(window.location.origin + url, data, function(data) {
		alert('등록완료!!');  
		//location.href='/admin/modifyCoupon/nm/' + data.extra;
		
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
    /* 
   	var extension = file_name.substring(file_name.lastIndexOf('.')+1).toLowerCase();

      var clear_extensions = 'jpeg/jpg/png';

      if(clear_extensions.search(extension) === -1) {
          alert('이미지는 jpg, png만 가능합니다.');
          return;
      }
	*/
      if(typeof cb === 'function') {
          cb();
      }
      
	};
	
	var done = function(r) {
		var $targetParent = $target.parents(".img-uploader");
		//$targetParent.removeClass("before").addClass("after");
		//alert("맵파일 등록이 성공했습니다." + r.img_seq);
		$("#btnSave").show();
		couponData.coupon.file_seq = r.img_seq;
		rtvCoupon.set(couponData);
		//regFloorBlockFromMapFile(couponData.coupon);
		//$("img[id="+ $targetParent.attr("id")+"_uri" +"]").attr('src',r.img_uri);
	}; 
	var bcn_cd = getUriArrVal()[1]; 
	var params = {
			url : '/rest/'+bcn_cd+'/file/files', //upload url
		 	beforeStart : beforStart, // called before start uploading
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	};
	
	$('.file-uploader-wrap > .basic-btn').each(function(){
		$(this).fileInputButton(params);
	});
	
}

var getFloorMapFile = function(fl){
	var bcn_cd = getUriArrVal()[1];
	//alert(bcn_cd+","+fl);
	var restUri = window.location.origin + '/rest/'+bcn_cd+'/getFloorMapFile/' + fl;
	Common.REST.get(restUri, {}, function(data) {
		couponData.coupon = data; 
		
		rtvCoupon.set(couponData);
		//alert("data.file_seq>>"+data.file_seq);
		if(data.file_seq==null){
			$("#btnSave").show(); 
		}else{
			$("#btnSave").hide();
		}
						
	}, function(data) {
		console.log('fail data is ', data);
	});
};


var validation = function(data){
	
	if(data.file_seq == null || data.file_seq == '') {
		alert("맵파일을  등록 하세요.");
		return false;
	}
	
	return true;
}


$(function() {
	
	//alert(getUriArrVal()[4]);
	couponData.coupon.fl=getUriArrVal()[4];
	getFloorMapFile(getUriArrVal()[4]);
	rtvCoupon.set(couponData);
	
	initTitleImgUploadButton();
});
   


