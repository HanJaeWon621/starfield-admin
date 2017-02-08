var app = app || {};

app.ractive = app.ractive || {};
app.ractive.QNADetail = null;

app.data = app.data || {};
app.data.QNA = {};

function QNASection() {
	var $inquiry_detail = $('#inquiry-detail');
	var bcn_cd = $inquiry_detail.data('bcn_cd');
	var qna_seq = $inquiry_detail.data('qna_seq');
	
	
	function getQNALDetail(qna_seq) {
		Common.REST.get('/admin/rest/' + bcn_cd + '/service/qna/' + qna_seq, {}, function(result) {
			QNADetail(result);
		}, function(data) {
			alert('QNA 불러오기 실패');
		});
	}
	
	function QNADetail(result){
		app.data.QNA = result
		if(!app.ractive.QNADetail) {
			app.ractive.QNADetail = new Ractive({
				el : '#inquiry-detail',
				template : '#tmpl-inquiry-detail',
				data : {
					qna : app.data.QNA,
				}
			});
			app.ractive.QNADetail.on({
				'list' : function() {
					location.href = '/' + bcn_cd + '/service/qna';
				},
				'cancel' : function() {
					if(confirm('답변등록을 취소합니다. 종료하시겠습니까? ')) {
						location.href = '/' + bcn_cd + '/service/qna';
					}
					
				},
				'save' : function() {
					if(!app.data.QNA.reply_cont) {
						return alert('답변 내용을 입력해주세요.');
					}
					
					if(confirm('입력한 내용으로 저장을 하시겠습니까?')) {
						Common.REST.put('/admin/rest/' + bcn_cd + '/service/qna/' + qna_seq, app.data.QNA, function(result) {
							if(result && result.code == 0) {
								history.go(0);
							} else {
								alert('저장 실패');
							}
						}, function(data) {
							alert('저장 실패');
						});
					}
					
					
				}
			});
		} else {
			app.ractive.QNADetail.reset();
			app.ractive.QNADetail.set({
				qna : app.data.QNA,
			});
		}
	}
	
	getQNALDetail(qna_seq);
}

$(function() {
	new QNASection();

});