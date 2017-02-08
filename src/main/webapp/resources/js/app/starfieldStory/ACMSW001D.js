var app = app || {};

app.ractive = app.ractive || {};
app.ractive.blog = null;

app.data = app.data || {};
app.data.blog = {};

function BlogSection() {
	var $blog_reg = $('#blog-reg');
	var bcn_cd = $blog_reg.data('bcn_cd');
	var blog_seq = $blog_reg.data('blog_seq');
	
	
	function getBlogDetail(blog_seq) {
		if(blog_seq == 'create') {
			var blog = {
				bcn_cd : bcn_cd,
				blog_seq : 'create',
				blog_titl : '',
				blog_url : '',
				img_seq : '',
				img_uri : '',
				txt_colr_cd : '#FFFFFF',
				sts : 0
			};
			BlogDetail(blog);
		} else {
			Common.REST.get('/admin/rest/' + bcn_cd + '/blogs/' + blog_seq, {}, function(result) {
				BlogDetail(result);
			}, function(data) {
				alert('불러오기 실패');
			});
		}
	
	}
	
	function BlogDetail(result, cateogryList){
		app.data.blog = result
		if(!app.ractive.blog) {
			app.ractive.blog = new Ractive({
				el : '#blog-reg',
				template : '#tmpl-blog-reg',
				data : {
					blog : app.data.blog
				}
			});
			app.ractive.blog.on({
				'txt_colr_cd' : function(e, colorCode) {
					app.data.blog.txt_colr_cd = colorCode;
					app.ractive.blog.update();
				},
				'cancel' : function() {
					if(confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
						location.href = '/' + bcn_cd + '/starfieldStory/blog';
					}
				},
				'save' : function() {
					if(!app.data.blog.blog_titl) {
						return alert('블로그 타이틀을 입력해주세요.');
					}
					
					if(!app.data.blog.blog_url) {
						return alert('블로그 링크 URL을 입력해주세요');
					}
					
					if(!(app.data.blog.blog_url.indexOf('http://') == 0  || app.data.blog.blog_url.indexOf('https://') == 0)) {
						return alert('블로그 링크 URL은 http:// 또는 https:// 를 포함해서 입력해주세요.');
					}
					
					if(!app.data.blog.img_seq) {
						return alert('배경 이미지를 업로드해주세요.');
					}
					
					if(confirm('입력한 내용으로 저장을 하시겠습니까?')) {
						if(blog_seq == 'create') {
							Common.REST.post('/admin/rest/' + bcn_cd + '/blogs', app.data.blog, function(result) {
								if(result && result.code == 0) {
									location.href = '/' + bcn_cd + '/starfieldStory/blog';
								} else {
									alert('저장 실패');
								}
							}, function(data) {
								alert('저장 실패');
							});
						} else {
							Common.REST.put('/admin/rest/' + bcn_cd + '/blogs/' + blog_seq, app.data.blog, function(result) {
								if(result && result.code == 0) {
									location.href = '/' + bcn_cd + '/starfieldStory/blog';
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
					app.data.blog.img_seq = null;
					app.data.blog.img_uri = null;
					app.ractive.blog.update();
				}
			});
			
			
			app.imageUpload('#image_upload', bcn_cd, function(err, image, el){
				if(err) {
					return alert('이미지 업로드 실패');
				}
				app.data.blog.img_seq = image.img_seq;
				app.data.blog.img_uri = image.img_uri;
				app.ractive.blog.update();
			});
			
		} else {
			app.ractive.blog.reset();
			app.ractive.blog.set({
				blog : app.data.blog
			});
		}
	}
	
	getBlogDetail(blog_seq);
}

$(function() {
	new BlogSection();

});