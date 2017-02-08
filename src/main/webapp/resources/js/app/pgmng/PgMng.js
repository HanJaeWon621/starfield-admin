
////////////////////////////////////////////////////////
// global variables

var styleData  = {
	filter : {
		sh_pg_nm : '',
		sh_style_nm : '',
	}
	
};

var dataList  = {
		styleList : null,
		paging : {}
		
	};
var dtlData  = {
		formdata : {
			pg_info_seq : '',
			pg_nm : '',
			disp_sn : '',
			disp_depth : '1',
			func_desc : '',
			rel_src_items : '',
			func_list_desc : '',
			use_db_object : '',
			etc_desc : '',
			pg_url : '',
			pg_div : '',
			menu_yn : 'Y'
			
		}
	};

var dtlData2  = {
		formdata : {
			expl : '',
			pg_nm : ''
						
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
	'openPop' : function(o,mode, pg_info_seq){
			$("#formtitl").text("프로그램등록");
			dtlData.formdata.pg_info_seq ='',
			dtlData.formdata.pg_nm ='';
			dtlData.formdata.disp_sn ='';
			dtlData.formdata.disp_depth ='1';
			dtlData.formdata.func_desc ='';
			dtlData.formdata.rel_src_items ='';
			dtlData.formdata.func_list_desc ='';
			dtlData.formdata.use_db_object ='';
			dtlData.formdata.etc_desc ='';
			dtlData.formdata.pg_url ='';
			dtlData.formdata.pg_div ='ADM';
			dtlData.formdata.menu_yn ='Y';
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
	'deletePgMng' : function(o, pg_info_seq) {//삭제
		deletePgMng(pg_info_seq);
	},
	'openPop' : function(o,mode, pg_info_seq){
		if(mode=='View'){
			getDataDtl(mode, pg_info_seq);
			getPgMngsAPI();
		}else if(mode=='Edit'){
			getDataDtl(mode, pg_info_seq);
			$("#formtitl").text("프로그램수정");
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
	var url = "/rest/regPgMng";
	//if(getU
	if($("#formtitl").text()=="프로그램수정")
	{
		url = "/rest/modifyPgMng";
		
	}else{
		url = "/rest/regPgMng";
	}

	Common.REST.post(window.location.origin + url, data, function(data) {
		if($("#formtitl").text()=="프로그램수정")
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
var deletePgMng = function(pg_info_seq){
	
	var restUri = window.location.origin + "/rest/deletePgMng";
	restUri += '?';
	restUri += 'sh_pg_info_seq=' + pg_info_seq; 
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
	if(data.pg_nm == null || data.pg_nm == '') {
		alert("프로그램명을 입력 하세요.");
		return false;
	}
	
	return true;
}
//
var getDataList = function(offset, limit,sortColumn) {

	var offset = offset || 1;  
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/getPgMngs';
	restUri += '?'; 
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit;
	restUri += '&sh_pg_nm=' + styleData.filter.sh_pg_nm;
	
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
	var restUri = window.location.origin + '/getPgMngs/excel';
	restUri += '?'; 
	restUri += 'sh_pg_nm=' + styleData.filter.sh_pg_nm;
	
	location.href=restUri;
};
var getDataDtl = function(mode, pg_info_seq) {
	var restUri = window.location.origin + '/rest/getPgMng';
	//alert(restUri);
	restUri += '?';
	restUri += 'sh_pg_info_seq=' + pg_info_seq; 
	
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

var getPgMngsAPI = function() {
	var restUri = window.location.origin + '/rest/getPgMngsAPI';
	
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


