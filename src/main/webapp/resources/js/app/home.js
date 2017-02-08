
//이미지 업로드
var initTitleImgUploadButton = function() {
	
	var beforStart = function(e, data, cb) {
   		
     	var file_name = data.files[0].name;
        var extension = file_name.substring(file_name.lastIndexOf('.')+1).toLowerCase();

        var clear_extensions = 'jpg/png';

        if(clear_extensions.search(extension) === -1) {
            alert('이미지는 jpg, png만 가능합니다.');
            return;
        }

        if(typeof cb === 'function') {
            cb();
        }
	};
	
	var done = function(resultParam) {
		console.log("resultParam : " + resultParam.code);
		console.log("resultParam : " + resultParam.desc);
		
		if(resultParam) {
			alert("등록완료");
		}
	};
	
	var params = {
			url : '/file/imgFileUpload', //upload url
		 	beforeStart : beforStart, // called before start uploading
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	};
	
	$('#title-image-upload-button').fileInputButton(params);
	
}

$(function() {

	initTitleImgUploadButton();

});
