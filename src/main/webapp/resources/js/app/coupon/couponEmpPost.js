
////////////////////////////////////////////////////////
// global variables

var empData  = {
	deptList : null,
	emp : {
		ename : null,
		job : null,
		sal : null,
		deptno : null
	}
};


////////////////////////////////////////////////////////
// global functions

////////////////////////////////////////////////////////
//initialize

var rtvEmp = new Ractive({
	el : '#emp-reg',
	template : '#tmpl-emp-reg',
	data : empData
});

rtvEmp.on({
	'regEmp' : function() {
		console.log('emp is ', empData.emp);
		regEmp(empData.emp);
	},
	
	'resetEmp' : function() {
		resetEmpForm();
	},
	
	'empList' : function() {
		console.log('목록이동');
		location.href='/emps';
	}
});


var getDeptList = function() {
	//Common.REST.get('http://localhost:7777/rest/depts', {}, function(data) {
	Common.REST.get(window.location.origin + '/rest/depts', {}, function(data) {
		console.log('success data is ', data);
		
		empData.deptList = data;
		rtvEmp.set(empData);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
};

var regEmp = function(emp) {
	//Common.REST.post('http://localhost:7777/rest/emps', emp, function(data) {
	Common.REST.post(window.location.origin + '/rest/emps', emp, function(data) {
		console.log('success data is ', data);
		alert('등록완료!!');
		
		rtvEmp.set(empData);
		resetEmpForm();
		
	}, function(data) {
		console.log('fail data is ', data);
	});
	
};

var resetEmpForm = function() {
	var emp = {
		ename : null,
		job : null,
		sal : null,
		deptno : empData.deptList[0].deptno
	}
	
	empData.emp = emp;
	rtvEmp.set(empData);
};

$(function() {
	
	getDeptList();
	
});

///////////////////////////////



////////////////////////////////////////////////////////
//global variables

var arrtenantNum = [];
var arrtenantText = [];
var couponData  = {
tenantChkList : null,
fileType : null,
coupon : {
cp_seq : null, //쿠폰순번
img_seq : null, //쿠폰BI이미지순번
bcn_cd : null, //지점코드
yymm : null, //년월
mst_seq : null, //마스터순번
cp_div_cd : null, //쿠폰구분코드
cp_kind_cd : null, //쿠폰종류코드
cp_iss_type_cd : null, //쿠폰발급타입코드
cp_titl : null, //쿠폰타이틀
cp_iss_strt_dt : null, //쿠폰발급시작일
cp_iss_end_dt : null, //쿠폰발급종료일
cp_act_strt_dt : null, //쿠폰유효시작일
cp_act_end_dt : null, //쿠폰유효종료일
cp_sale_meth_div_cd : null, //쿠폰할인방식구분코드
cp_sale_div_cd : null, //쿠폰소계할인구분코드
cp_sum_sale_rt : null, //쿠폰소계할인금액
cp_amt_ded_div_cd : null, //쿠폰금액차감구분코드
cp_ded_amt : null, //쿠폰차감금액
cp_iss_cnt : null, //쿠폰발급수량
cp_use_cond_div_cd : null, //쿠폰사용조건구분코드
cp_use_cond_amt : null, //쿠폰사용조건금액
cp_dtl_cont : null, //쿠폰상세내용
cp_att_part_cont : null, //쿠폰유의사항내용
inf_sts_cd : null, //인터페이스상태코드
sale_evt_cd : null, //영업행사코드
reg_dttm : null, //등록일시
mod_dttm : null, //수정일시
reg_usr : null, //등록자
cp_exp_yn : null,
cp_max_sale_amt : null
}, 
tenantList  : null,
paging : {},
filter: {
tnt_nm_ko : null,
cate_seq : null,
categoryList : null
}
};


////////////////////////////////////////////////////////
//global functions

////////////////////////////////////////////////////////
//initialize

var rtvCoupon = new Ractive({
el : '#coupon-reg',
template : '#tmpl-coupon-reg',
data : couponData
});

var rtvTenant = new Ractive({
el : '#dialog-detail',
template : '#tmpl-tennant-list',
data : couponData
});


rtvCoupon.on({
'imgBtnT' : function(o,type) { 
couponData.fileType = type;

},
'opentenant' : function() {
opentenant(0, 10);

},
'regCoupon' : function(o) {
regCoupon(couponData.coupon);

},
'changeCpSumSaleMethDiv' : function(o,flag) {
changeCpSumSaleMethDiv(flag);

},
'changeCpUsePlceSetCd' : function(o,flag) {
changeCpUsePlceSetCd(flag);

}

});


var changeCpSumSaleMethDiv = function(flag){
if(flag){
$("#cp_sum_sale_rt").attr("disabled",false);
$("#cp_max_sale_amt").attr("disabled",false);
$("#cp_ded_amt").attr("disabled",true);
couponData.coupon.cp_ded_amt = '';
}else{ 
$("#cp_sum_sale_rt").attr("disabled",true);
$("#cp_max_sale_amt").attr("disabled",true);
$("#cp_ded_amt").attr("disabled",false);
couponData.coupon.cp_sum_sale_rt = '';
couponData.coupon.cp_max_sale_amt = '';
} 
rtvCoupon.set(couponData);
}


var changeCpUsePlceSetCd = function(flag){
if(flag){
$("#opentenantBtn").attr("disabled",true);
}else{
$("#opentenantBtn").attr("disabled",false);
}
}

var regCoupon = function(data){
couponData.coupon.cp_iss_strt_dt           = 	$("#cp_iss_strt_dt").val().split('-').join("");
couponData.coupon.cp_iss_end_dt            = 	$("#cp_iss_end_dt").val().split('-').join("");
couponData.coupon.cp_act_strt_dt           = 	$("#cp_act_strt_dt").val().split('-').join("");
couponData.coupon.cp_act_end_dt            = 	$("#cp_act_end_dt").val().split('-').join("");

Common.REST.post(window.location.origin + '/rest/BCN001/modifyCoupon', data, function(data) {
console.log('success data is ', data);
//$("img[id=imgv]").attr('src','');
alert('수정완료!!');

//rtvCoupon.set(rtvCoupon);
//resetEmpForm();

}, function(data) {
console.log('fail data is ', data);
});


} 

//이미지 업로드
var initTitleImgUploadButton = function() {
var file_name = "";
var beforStart = function(e, data, cb) {
//var $upload = $(e.find('input')[0]);
//alert(data);
//alert(data.files[0].parent().attr('id')); 
file_name = data.files[0].name; //실제 파일명
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
console.log("resultParam " +
": " + resultParam.desc);

//alert('result.file_name)=='+resultParam.file_name); //업로드된 파일명
//alert('result.file_storage_path)=='+resultParam.file_storage_path); //풀경로+파일명 
if(resultParam) {
//couponData.coupon.cp_bi_img_seq = resultParam.file_url;
//$("input[name=img_seq"+couponData.fileType+"]").val(resultParam.file_url);
//$("input[name=img_name"+couponData.fileType+"]").val(file_name);
}  
}; 

var params = {
url : '/rest/BCN001/file/images',
beforeStart : beforStart, // called before start uploading
done : done, // called with result json object when uploading is done 
progress : function(){} // called with current progress (Experimental)
}; 

$('#title-image-upload-button').fileInputButton(params);

}


var getCoupon = function(cp_seq){
var restUri = window.location.origin + '/rest/getCoupon/' + cp_seq;
Common.REST.get(restUri, {}, function(data) {
couponData.coupon = data; 
rtvCoupon.set(couponData);

}, function(data) {
console.log('fail data is ', data);
});
};
//////////팝업/////////////


rtvTenant.on({
'tennantChk' : function(o,snum,text) { 
tennantChk(snum,text);
},
'tennantSave' : function() { 
tennantSave();
},
'tennantClose' : function() { 
$( "#dialog-detail" ).dialog( "close" );
},
'searchTennant' : function() { 
opentenant(1, 10);
}

});

var opentenant =  function(offset, limit){
$("#dialog-detail").dialog("open");

var offset = offset || 1;
var limit = limit || 20;
var restUri = window.location.origin + '/rest/bcnCd/getTenants';
restUri += '?'; 
restUri += 'offset=' + offset;
restUri += '&limit=' + limit;
restUri += '&cate_seq=' + couponData.filter.cate_seq;
restUri += '&tnt_nm_ko=' + couponData.filter.tnt_nm_ko;
Common.REST.get(restUri, {}, function(data) {
couponData.tenantList = data.list;
couponData.paging = data.paging;

couponData.paging.pages = [];

for(var i = couponData.paging.page_start; i <= couponData.paging.page_end; ++i) {
couponData.paging.pages.push(i);
}
rtvTenant.set(couponData);

getCategorys(); 
//파일
}, function(data) {

console.log('fail data is ', data);

}); 

/*$("input[id^=tennant_]").prop("checked",false);  
for(var i = 0 ; i < arrtenantNum.length ; i++){
$("input[id=tennant_"+arrtenantNum[i]+"]").prop("checked",true);
} */
}; 



var getCategorys =  function(){

var restUri = window.location.origin + '/rest/getCategorys';
Common.REST.get(restUri, {}, function(data) {
//couponData.tenantList = data.list;
couponData.filter.categoryList = data.list;
rtvTenant.set(couponData); 
}, function(data) { 

console.log('fail data is ', data);

}); 

}; 


var tennantChk = function(snum,text){
if(arrtenantNum.indexOf(snum) != -1){
var arridx = arrtenantNum.indexOf(snum);
arrtenantNum.splice(arridx,1);
arrtenantText.splice(arridx,1);

}else{
arrtenantNum.push(snum);
arrtenantText.push(text);
}
alert(arrtenantNum); 
};


var tennantSave = function(){
$("#appendtenant").children().remove();
alert(arrtenantNum.length);
alert(arrtenantNum);
var src = "";
for(var i = 0 ; i < arrtenantNum.length ; i++){
src = "<div onclick=\"deletetenant(this,\'"+ arrtenantNum[i] +"\');\"><label>"+ arrtenantText[i] +"</label> <label>X</label></div>";
$("#appendtenant").append(src);
}
$( "#dialog-detail" ).dialog( "close" );
};


/////////////////////////////


$(function() {
initTitleImgUploadButton();
dialog2();
datepicker(); 
getCoupon(getUriArrVal()[3]);

}); 


var deletetenant = function(obj,snum){
var arridx = arrtenantNum.indexOf(snum);
arrtenantNum.splice(arridx,1);
arrtenantText.splice(arridx,1);
$(obj).remove();     

}


/*
var regCoupon = function(data){
couponData.coupon.cp_iss_strt_dt           = 	$("#cp_iss_strt_dt").val().split('-').join("");
couponData.coupon.cp_iss_end_dt            = 	$("#cp_iss_end_dt").val().split('-').join("");
couponData.coupon.cp_act_strt_dt           = 	$("#cp_act_strt_dt").val().split('-').join("");
couponData.coupon.cp_act_end_dt            = 	$("#cp_act_end_dt").val().split('-').join("");
couponData.coupon.cp_div_cd                =   $(':radio[name="cp_div_cd"]:checked').val();
couponData.coupon.cp_kind_cd               =   $(':radio[name="cp_kind_cd"]:checked').val();
couponData.coupon.cp_sale_div_cd  =   $(':radio[name="cp_sale_div_cd"]:checked').val();
couponData.coupon.cp_use_set_div_cd        =   $(':radio[name="cp_use_set_div_cd"]:checked').val();
rtvCoupon.set(couponData);
alert(couponData.coupon.cp_sum_sale_rt)
return false; 
if($("#cp_iss_cnt_gen_exp_yn").is(":checked")){
couponData.coupon.cp_iss_cnt_gen_exp_yn = "Y";
}else{
couponData.coupon.cp_iss_cnt_gen_exp_yn = "N";
}

if($("#cp_iss_cnt_push_exp_yn").is(":checked")){
couponData.coupon.cp_iss_cnt_push_exp_yn = "Y";
}else{
couponData.coupon.cp_iss_cnt_push_exp_yn = "N";
}


alert(
'구분 cp_div_cd = ' + couponData.coupon.cp_div_cd +	
'\n종류 cp_kind_cd = ' + couponData.coupon.cp_kind_cd +
'\n타이틀 cp_title = ' + couponData.coupon.cp_titl +	
'\nbi 이미지 cp_bi_seq = ' + couponData.coupon.cp_bi_img_seq +	
'\n상세 이미지 cp_dtl_seq = ' + couponData.coupon.cp_dtl_img_seq +	
'\n발급 시작일 cp_iss_strt_dt = ' + couponData.coupon.cp_iss_strt_dt +	
'\n발급 종료일 cp_iss_end_dt = ' + couponData.coupon.cp_iss_end_dt +	
'\n유효기간 시작일 cp_act_strt_dt = ' + couponData.coupon.cp_act_strt_dt +	
'\n유효기간 종료일 cp_act_end_dt = ' + couponData.coupon.cp_act_end_dt +	
'\n--------------------'+
'\n할인방식 설정 radio cp_sale_meth_div_cd = ' + couponData.coupon.cp_sale_div_cd +
'\n할인방식 설정 소계 할인형 select cp_sum_sale_div_cd = ' + couponData.coupon.cp_sale_meth_div_cd +
'\n할인방식 설정 소계 할인형 text cp_sum_sal_amt = ' + couponData.coupon.cp_sum_sale_rt +
'\n할인방식 설정 금액 차감형 select cp_amt_ded_div_cd = ' + couponData.coupon.cp_amt_ded_div_cd +
'\n할인방식 설정 금액 차감형 text cp_amt_ded_amt = ' + couponData.coupon.cp_ded_amt +
'\n--------------------'+
'\n발급수량설정 radio cp_gen_exp_yn = ' + couponData.coupon.cp_iss_cnt_gen_exp_yn +
'\n발급수량설정 일반노출 select cp_gen_exp_div_cd = ' + couponData.coupon.cp_iss_cnt_gen_exp_div_cd +
'\n발급수량설정 일반노출 cp_gen_exp_cnt = ' + couponData.coupon.cp_iss_cnt_gen_exp_cnt +
'\n발급수량설정 푸시노출 select cp_push_exp_div_cd = ' + couponData.coupon.cp_iss_cnt_push_exp_div_cd +
'\n발급수량설정 푸시노출 text cp_push_exp_cnt = ' + couponData.coupon.cp_iss_cnt_push_exp_cnt +
'\n--------------------'+
'\n사용조건설정 radio cp_use_set_div_cd = ' + couponData.coupon.cp_use_set_div_cd +
'\n사용조건설정 직접입력 cp_use_set_cts = ' + couponData.coupon.cp_use_set_cont +
'\n사용조건설정 특정급액이상사용 select cp_use_spc_amt_div_cd = ' + couponData.coupon.cp_use_spc_amt_div_cd +
'\n사용조건설정 특정급액이상사용 text cp_use_spt_amt = ' + couponData.coupon.cp_use_spt_amt +
'\n--------------------'+
'\n사용처설정 radio cp_use_place_div_cd = ' + couponData.coupon.cp_use_plce_div_cd 
//'\n테넌트지정 cp_use_place_set_cd = ' + couponData.coupon.cp_use_place_set_cd 
);

if(getUriArrVal()[3]){
couponData.coupon.cp_seq = getUriArrVal()[3];
}

Common.REST.post(window.location.origin + '/coupon/saveCoupon', data, function(data) {
console.log('success data is ', data);
$("img[id=imgv]").attr('src','');
alert('등록완료!!');

rtvCoupon.set(rtvCoupon);
//resetEmpForm();

}, function(data) {
console.log('fail data is ', data);
});

} */





