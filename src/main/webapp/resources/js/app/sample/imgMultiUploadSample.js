
var data = {
	images : []
};


var rtvSample = new Ractive({
	el : '#sample-image-upload',
	template : '#tmpl-sample-image-upload',
	data : data
});


//이미지 업로드 버튼 설정 
var initTitleImgUploadButton = function() {
	
	var beforStart = function(e, data, cb) {
   		
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
	
	var fail = function(r) {
		alert('image upload file!! \n' + r.errMsg);
	};
	
	var done = function(r) {
		alert(1);
		data.images.push(r);
	};
	
	var params = {
			url : '/rest/01/file/images', //upload url
		 	beforeStart : beforStart, // called before start uploading
		 	fail : fail,
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	};
 	
	$('#title-image-upload-button').fileInputButton(params);
	
}

$(function() {

	initTitleImgUploadButton();

});
