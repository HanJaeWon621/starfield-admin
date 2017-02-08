
var data = {
};


var rtvSample = new Ractive({
	el : '#sample-excel-upload',
	template : '#tmpl-sample-excel-upload',
	data : data
});


//이미지 업로드 버튼 설정 
var initTitleImgUploadButton = function() {
	
	var beforStart = function(e, data, cb) {
   		
     	var file_name = data.files[0].name;
        var extension = file_name.substring(file_name.lastIndexOf('.')+1).toLowerCase();

        var clear_extensions = 'xls/xlsx';

        if(clear_extensions.search(extension) === -1) {
            alert('엑셀은 xls, xlsx만 가능합니다.');
            return;
        }

        if(typeof cb === 'function') {
            cb();
        }
	};
	
	var done = function(r) {
		
		console.log(r);
		
		if(r && r.code == 0) {
			alert('엑셀 업로드 완료');
		} else {
			alert('엑셀 업로드 실패');
		}
	};
	
	var params = {
			url : '/excel/samples', //upload url
		 	beforeStart : beforStart, // called before start uploading
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	};
	
	$('#title-excel-upload-button').fileInputButton(params);
	
}

$(function() {

	initTitleImgUploadButton();

});
