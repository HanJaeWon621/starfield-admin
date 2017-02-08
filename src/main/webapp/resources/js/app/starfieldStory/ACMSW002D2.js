var token = location.hash.split('=')[1];

var $create_token = $('#create-token');
var bcn_cd = $create_token.data('bcn_cd');

Common.REST.put('/admin/rest/' + bcn_cd + '/starfieldStory/token', token,function(result) {
	
	if(result.code == 0) {
		alert('인스타그램 토큰 재발급 완료');
		opener.window.location.href= '/' + bcn_cd + '/starfieldStory/instagram';
	} else {
		alert('인스타그램 토큰 업데이트 실패');
	}
	self.close();
}, function(data) {
	alert('인스타그램 토큰 업데이트 실패');
	self.close();
});