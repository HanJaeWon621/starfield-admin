
////////////////////////////////////////////////////////
// global variables

var styleData  = {
	filter : {
		sh_work_nm : ''
	}
	
};

var dataList  = {
		styleList : null,
		paging : {}
		
	};
var dtlData  = {
		formdata : {
			to_do_seq: '',
	        sys_cd: '',
	        work_cls_cd: '',
	        work_cd: '',
	        work_nm: '',
	        work_strt_dt: '',
	        work_end_dt: '',
	        work_dtl: '',
	        work_issue: '',
	        priority: '',
	        difficulty: '',
	        rel_pg_seq: ''	
			
		}
	};

var dtlData2  = {
		formdata : {
			expl : '',
			work_nm : ''
						
		}
	};
var rtvCoupon = new Ractive({
	el : '#style-list',
	template : '#tmpl-style-list',
	data : styleData
});

var rtvDataList = new Ractive({
	el : '#data-list',
	template : '#tmpl-data-list',
	data : dataList
});


var rtvCoupon2 = new Ractive({
	el : '#style-dtl',
	template : '#tmpl-style-dtl',
	data : dtlData
});
var rtvData3 = new Ractive({
	el : '#dtl2',
	template : '#tmpl-dtl2',
	data : dtlData2
});
rtvCoupon.on({
	'shCoupon' : function(){
		shCoupon();
	},
	'getDataExcelList' : function(){
		getDataExcelList();
	},
	'openPop' : function(o,mode, to_do_seq){
			$("#formtitl").text("등록");
			dtlData.formdata.to_do_seq='';
	        dtlData.formdata.sys_cd='';
	        dtlData.formdata.work_cls_cd='';
	        dtlData.formdata.work_cd='';
	        dtlData.formdata.work_nm='';
	        dtlData.formdata.work_strt_dt='';
	        dtlData.formdata.work_end_dt='';
	        dtlData.formdata.work_dtl='';
	        dtlData.formdata.work_issue='';
	        dtlData.formdata.priority='1';
	        dtlData.formdata.difficulty='1';
	        dtlData.formdata.rel_pg_seq='';	

			//dtlData.formdata.bcn_cd = styleData.filter.sh_bcn_cd;
			rtvCoupon2.set(dtlData);
		//$("#tnt-reg-popup2").show();
	}
});

rtvDataList.on({
	'pageMove' : function(o, page) {
		if(page > 0 
				&& page <= dataList.paging.total_page_cnt
				&& page != dataList.paging.cur_page) {

			var offset = (page - 1) * dataList.paging.list_limit + 1; 
			getDataList(offset, dataList.paging.list_limit);
		}
		
	}, 	
	'deleteTodoMng' : function(o, seq) {//삭제
		deleteTodoMng(seq);
	},
	'openPop' : function(o,mode, seq){
		if(mode=='View'){
			getDataDtl(mode, seq);
			getTodoMngsAPI();
		}else if(mode=='Edit'){
			getDataDtl(mode, seq);
			$("#formtitl").text("수정");
		}
	}
});

rtvCoupon2.on({ 
	'regSave' : function(o) {//등록/수정
		//$("#delBtn").hide();
		regSave(dtlData.formdata);
	},
	'closePop' : function(o,tnt_seq){
		$("div[name='tnt-reg-popup2']").hide();
	}
});
var shCoupon = function(){

	getDataList(1,10);

}
var getDataExcelList = function(){

	getDataExcelList();

}
var regSave = function(data){
	
	if(!validation(data)){
		return false;
	}
	var url = "/rest/regTodoMng";
	//if(getU
	if($("#formtitl").text()=="수정")
	{
		url = "/rest/modifyTodoMng";
		
	}else{
		url = "/rest/regTodoMng";
	}

	Common.REST.post(window.location.origin + url, data, function(data) {
		if($("#formtitl").text()=="수정")
		{
			alert('수정완료!!');  
		}else{
			alert('등록완료!!');  
		}

		getDataList(1, 10);
		//$("div[name='tnt-reg-popup2']").hide();
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
} 
//
var deleteTodoMng = function(to_do_seq){
	
	var restUri = window.location.origin + "/rest/deleteTodoMng";
	restUri += '?';
	restUri += 'sh_to_do_seq=' + to_do_seq; 
	Common.REST.get(restUri, {}, function(data) {

		alert('삭제완료!!');  
		getDataList(1, 10);
		$("div[name='tnt-reg-popup2']").hide();
		//alert(data.extra);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}

var validation = function(data){ 
	if(data.work_nm == null || data.work_nm == '') {
		alert("작업명을 입력 하세요.");
		return false;
	}
	
	return true;
}
//
var getDataList = function(offset, limit,sortColumn) {

	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/getTodoMngs';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_work_nm=' + styleData.filter.sh_work_nm;
	
	Common.REST.get(restUri, {}, function(data) {
		dataList.styleList = data.list; 
		dataList.paging = data.paging; 
		dataList.paging.pages = [];
		 
		for(var  i = dataList.paging.page_start; i <= dataList.paging.page_end; ++i) {
			dataList.paging.pages.push(i);
        }
		rtvDataList.set(dataList);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};
var getDataExcelList = function() {
	var restUri = window.location.origin + '/getTodoMngs/excel';
	restUri += '?'; 
	restUri += 'sh_work_nm=' + styleData.filter.sh_work_nm;
	
	location.href=restUri;
};
var getDataDtl = function(mode, to_do_seq) {
	var restUri = window.location.origin + '/rest/getTodoMng';
	//alert(restUri);
	restUri += '?';
	restUri += 'sh_to_do_seq=' + to_do_seq; 
	
	Common.REST.get(restUri, {}, function(data) {
		if(mode=='Edit'){
			dtlData.formdata = data;
			rtvCoupon2.set(dtlData);
		}else{
			dtlData2.formdata = data;
			rtvData3.set(dtlData2);
		}
		
		
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};

var getTodoMngsAPI = function() {
	var restUri = window.location.origin + '/rest/getTodoMngsAPI';
	
	Common.REST.get(restUri, {}, function(data) {
		console.log(data.length);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};
$(function() {
		
	getDataList(1, 10);
	//달력  
	
	
});


