var app = app || {};

app.ractive = app.ractive || {};
app.ractive.notice = null;

app.data = app.data || {};
app.data.notice = {};

function NoticeSection() {
	var $notice_reg = $('#notice-reg');
	var bcn_cd = $notice_reg.data('bcn_cd');
	var noti_seq = $notice_reg.data('noti_seq');
	
	
	function getNoticeLDetail(noti_seq) {
		if(noti_seq == 'create') {
			var notice = {
				bcn_cd : bcn_cd,
				noti_seq : 'create',
				noti_titl : '',
				noti_cont : '',
				sts : 1
			};
			NoticeDetail(notice);
		} else {
			Common.REST.get('/admin/rest/' + bcn_cd + '/notices/' + noti_seq, {}, function(result) {
				NoticeDetail(result);
			}, function(data) {
				alert('불러오기 실패');
			});
		}
	}
	
	function NoticeDetail(result){
		app.data.notice = result
		if(!app.ractive.notice) {
			app.ractive.notice = new Ractive({
				el : '#notice-reg',
				template : '#tmpl-notice-reg',
				data : {
					notice : app.data.notice
				}
			});
			app.ractive.notice.on({
				'cancel' : function() {
					if(confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
						location.href = '/' + bcn_cd + '/service/notice';
					}
				},
				'save' : function() {
					if(!app.data.notice.noti_titl) {
						return alert('제목을 입력해주세요.');
					}
					
					if(!app.data.notice.noti_cont) {
						return alert('내용을 입력해주세요.');
					}
					
					if(confirm('입력한 내용으로 저장을 하시겠습니까?')) {
						if(noti_seq == 'create') {
							Common.REST.post('/admin/rest/' + bcn_cd + '/notices', app.data.notice, function(result) {
								if(result && result.code == 0) {
									location.href = '/' + bcn_cd + '/service/notice';
								} else {
									alert('저장 실패');
								}
							}, function(data) {
								alert('저장 실패');
							});
						} else {
							Common.REST.put('/admin/rest/' + bcn_cd + '/notices/' + noti_seq, app.data.notice, function(result) {
								if(result && result.code == 0) {
									location.href = '/' + bcn_cd + '/service/notice';
								} else {
									alert('저장 실패');
								}
							}, function(data) {
								alert('저장 실패');
							});
						}
					}
				}
			});
		} else {
			app.ractive.notice.reset();
			app.ractive.notice.set({
				notice : app.data.notice
			});
		}
	}
	
	getNoticeLDetail(noti_seq);
}

$(function() {
	new NoticeSection();

});