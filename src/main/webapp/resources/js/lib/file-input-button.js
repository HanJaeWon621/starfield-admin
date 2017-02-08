/**
 * 
 */
$.fn.fileInputButton = function(params) {
	
	this.append('<input type="file" id="file" name="file">');

	var $upload = $(this.find('input')[0]);
	
	$upload.fileupload({
		// dataType: 'json', //text/plain will be returned for old browser
       	url : params.url, 
       	add: function (e, data) {
       		
            if(params.beforeStart) {
            	params.beforeStart(e, data, function() {
            		data.submit();
            	});
            }
        },
        fail : function(e, data) {
        	params.fail(data.jqXHR.responseJSON);
        },
        done: function (e, data) {
        	
        	var result;
        	
        	if(typeof data.result == 'object') {
        		result = data.result;
        		params.done(result);
        		return;
        	} else if(typeof data.result == 'string') {
        		result = data.result;
        	} else {
        		//for IE8,9 using IFrame transfer
        		result = $( 'pre', data.result ).text();	
        	}
        	 
             if(result != null && $.trim( result ) != '' ) {
            	 var o = JSON.parse(result);
                 if(params.done) {
                	 params.done(o);
                 }
            	 
             } else {
            	 alert('A file transfer is successful but cannot get the result.');
             }
        },
        progress: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
        }
    });
	
	return this;
};