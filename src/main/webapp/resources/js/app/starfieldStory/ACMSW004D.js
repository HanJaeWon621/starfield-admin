var app = app || {};

app.ractive = app.ractive || {};
app.ractive.news = null;

app.data = app.data || {};
app.data.news = {};

function NewsSection() {
	var $news_reg = $('#news-reg');
	var bcn_cd = $news_reg.data('bcn_cd');
	var news_seq = $news_reg.data('news_seq');
	
	
	function getNewsDetail(news_seq) {
		Common.REST.get('/admin/rest/' + bcn_cd + '/news/category', {}, function(cateogryList) {
			if(news_seq == 'create') {
				var news = {
					bcn_cd : bcn_cd,
					news_seq : 'create',
					news_titl : '',
					news_cont : '',
					cate_seq : '',
					cate_nm_ko : '',
					img_seq : '',
					img_uri : '',
					sts : 1
				};
				NewsDetail(news, cateogryList);
			} else {
				Common.REST.get('/admin/rest/' + bcn_cd + '/news/' + news_seq, {}, function(result) {
					NewsDetail(result, cateogryList);
				}, function(data) {
					alert('뉴스 불러오기 실패');
				});
			}
		}, function(data) {
			alert('카테고리 불러오기 실패');
		});
	}
	
	function NewsDetail(result, cateogryList){
		app.data.news = result
		if(!app.ractive.news) {
			app.ractive.news = new Ractive({
				el : '#news-reg',
				template : '#tmpl-news-reg',
				data : {
					news : app.data.news,
					cateogryList : cateogryList
				}
			});
			app.ractive.news.on({
				'cancel' : function() {
					if(confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
						location.href = '/' + bcn_cd + '/starfieldStory/news';
					}
				},
				'save' : function() {
					if(!app.data.news.cate_seq) {
						return alert('구분을 선택해주세요.');
					}
					
					if(!app.data.news.news_titl) {
						return alert('제목을 입력해주세요.');
					}
					
					if(!app.data.news.news_cont) {
						return alert('내용을 입력해주세요.');
					}
					
					if(confirm('입력한 내용으로 저장을 하시겠습니까?')) {
						if(news_seq == 'create') {
							Common.REST.post('/admin/rest/' + bcn_cd + '/news', app.data.news, function(result) {
								if(result && result.code == 0) {
									location.href = '/' + bcn_cd + '/starfieldStory/news';
								} else {
									alert('저장 실패');
								}
							}, function(data) {
								alert('저장 실패');
							});
						} else {
							Common.REST.put('/admin/rest/' + bcn_cd + '/news/' + news_seq, app.data.news, function(result) {
								if(result && result.code == 0) {
									location.href = '/' + bcn_cd + '/starfieldStory/news';
								} else {
									alert('저장 실패');
								}
							}, function(data) {
								alert('저장 실패');
							});
						}
					}
				},
				'image-delete' : function(e) {
					app.data.news.img_seq = null;
					app.data.news.img_uri = null;
					app.ractive.news.update();
				}
			});
			
			
			app.imageUpload('#image_upload', bcn_cd, function(err, image, el){
				if(err) {
					return alert('이미지 업로드 실패');
				}
				app.data.news.img_seq = image.img_seq;
				app.data.news.img_uri = image.img_uri;
				app.ractive.news.update();
			});
			
		} else {
			app.ractive.news.reset();
			app.ractive.news.set({
				news : app.data.news,
				cateogryList : cateogryList
			});
		}
	}
	
	getNewsDetail(news_seq);
}

$(function() {
	new NewsSection();

});