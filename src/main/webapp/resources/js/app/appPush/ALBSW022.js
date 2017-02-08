
////////////////////////////////////////////////////////
// global variables
var movingTntIdx = "";
var memb_tot_cnt = "";
var ckMemSeq = [];
var arrDelTenantNum = [];
var arrtenantNum = [];
var appPushData  = {
	scenario : {
		delMemSeq : [],
		app_tgt_age_all : 'Y',
		app_tgt_age_20 : null,
		app_tgt_age_30 : null,
		app_tgt_age_40 : null,
		app_tgt_age_50 : null,
		push_time_div_cd : '1',
		scn_otb_div_cd : getUriArrVal()[3],
		selTenantList : []
	},
	applyCouponList : '',
	categoryList : '',
	pushMembList : '',
	abstMstList : '',
	couponList : '',
	tenantList : '',
	tenantList2 : '',
	paging : {},
	filter :{}
}; 

 
var rtvAppPush = new Ractive({ 
	el : '#scenarioPush-detail',
	template : '#tmpl-scenarioPush-detail',
	data : appPushData
});


rtvAppPush.on({
	'getPushMembs' : function(o) { 
		getPushMembs(appPushData.scenario);
	},
	'allTarget' : function(o) {
		allTarget();
	},
	'getApplyCoupons' : function(o) { 
		getApplyCoupons();
	},
	'changeMbr' : function(o) {
		changeMbr($(o.node).val());
	},
	'ckTgtAge' : function(o,type) {
		ckTgtAge($(o.node),type);
	},
	'ckMemb' : function(o, cust_id) {
		ckMemb($(o.node), cust_id);
	},
	'resetDelMemSeq' : function(o) {
		resetDelMemSeq();
	},
	'saveMem' : function(o) {
		saveMem();
	},
	'closeMem' : function(o) {
		closeMem();
	},
	'getCoupons' : function() { 
		getCoupons(1,10);
	},
	'cpPageMove' : function(o,page) {
		if(page > 0 && page <= appPushData.paging.total_page_cnt && page != appPushData.paging.cur_page) {
			var offset = (page - 1) * appPushData.paging.list_limit + 1;
			getCoupons(offset, appPushData.paging.list_limit);
		}
	},
	'selCoupon' : function() {  
		appPushData.scenario.cp_seq = $('input:radio[name="cp_ck"]:checked').val();
		$("#acp").val($("td[id="+ appPushData.scenario.cp_seq+"]").text());
		appPushData.filter.cp_titl = '';
		$("#cp_titl").val('');
		$("#couponP").hide();  
	},
	'closeCoupon' : function() {
		appPushData.filter.cp_titl = '';
		$("#cp_titl").val('');
		$("#couponP").hide();
	},
	'opentenant' : function() {
		opentenant(0, 10);
	},
	'moveTntPage' : function(o, page) {
		if(page > 0 && page <= appPushData.paging.total_page_cnt && page != appPushData.paging.cur_page) {
			var offset = (page - 1) * appPushData.paging.list_limit + 1;
			opentenant(offset, appPushData.paging.list_limit);
		}
	},
	'tenantChk' : function(o,val1,val2,val3,val4,val5,val6) { 
		tenantChk(val1,val2,val3,val4,val5,val6);
	},
	'saveTenant' : function() { 
		saveTenant();
	},
	'closeTenant' : function() { 
		closeTenant();
	},
	'openSeltenant' : function(o) {
		openSeltenant();
	},
	'deletetenant' : function(o,num) {
		deletetenant(num);
	},
	'selTenantSave' : function(o) {
		selTenantSave();
	},
	'selTenantClose' : function(o) {
		selTenantClose();
	},
	'openTenant2' : function(o, idx) {
		openTenant2(0, 10, idx);
	},
	'movetnt2Page' : function(o, page) {
		if(page > 0 && page <= appPushData.paging.total_page_cnt && page != appPushData.paging.cur_page) {
			var offset = (page - 1) * appPushData.paging.list_limit + 1;
			openTenant2(offset, appPushData.paging.list_limit, '');
		}
	},
	'movingTntSave' : function(o) {
		movingTntSave();
	},
	'movingTntChk' : function(o, tnt_seq, tnt_nm, busi_tnt_cd, zone_id){
		movingTntChk(tnt_seq, tnt_nm, busi_tnt_cd, zone_id);
	},
	'movingTntClose' : function(o){
		movingTntClose();
	},
	'delTenant' : function(o, idx){
		delTenant(idx);
	},
	'delAppCoupon' : function(o){
		delAppCoupon();
	}
	,
	'saveScenario' : function(o){
		saveScenario(appPushData.scenario);
	}, 
	'chPushTimeDiv' : function(o,type) {
		chPushTimeDiv(type);
	},
	'regScenarioUnPosting' : function(o) {
		regScenarioUnPosting(appPushData.scenario);
	},
	'regScenarioPosting' : function(o) {
		regScenarioPosting(appPushData.scenario);
	},
	'selChange' : function(o,text) {
		selChange($(o.node).val(), text);
	},
	'membPageMove' : function(o, page) {
		if(page > 0 && page <= appPushData.paging.total_page_cnt && page != appPushData.paging.cur_page) {
			var offset = (page - 1) * appPushData.paging.list_limit + 1; 
			getPushMembs(appPushData.scenario, offset, appPushData.paging.list_limit);
		}
	}, 
	'shMemb' : function(o, page) { 
		getPushMembs(appPushData.scenario);
	}, 
	'shCp' : function(o) { 
		getCoupons(0, 10);
	}, 
	'allTenant' : function(o) { 
		allTenant();
	},
	'deleteImg' : function(o) {
		$(o.node).parent().parent('div').attr('class','img-uploader before');
		var objId = $(o.node).parent().parent('div').attr('id');
		if(objId){
			appPushData.scenario[objId] = '';
			$("#bi_img_seq_uri").attr('src','');
		}  
		
	},
	'preview' : function(o) { 
		preview();
	},
	'btn-close' : function(o) { 
		$("#coupon-push-display-popup").hide();
	},
	'ckAllMemb' : function(o) {
		ckAllMemb();
	}
}); 

 
var ckUnAllMemb = function(cust_id){
	
	if(arrDelTenantNum.indexOf(arrtennant) != -1){
		arridx = arrDelTenantNum.indexOf(arrtennant);
		arrDelTenantNum.splice(arridx,1);
	}else{
		arrDelTenantNum.push(arrtennant);
	}
	
	if(arrtenantNum.indexOf(arrtennant) != -1){
		arridx = arrtenantNum.indexOf(arrtennant);
		arrtenantNum.splice(arridx,1);
	}
} 

var preview = function(){ 
	
	var period = $("#cp_act_strt_dt").val() + "~" + $("#cp_act_end_dt").val();
	if($("#cp_act_strt_dt").val().length == 8){
		var start = $("#cp_act_strt_dt").val().substr(0,4) + "." + $("#cp_act_strt_dt").val().substr(4,2) + "." + $("#cp_act_strt_dt").val().substr(6);
		var end = $("#cp_act_end_dt").val().substr(0,4) + "." + $("#cp_act_end_dt").val().substr(4,2) + "." + $("#cp_act_end_dt").val().substr(6);
		period = start +" ~ "+end;
	}  
	
	$("#pv_titl_text").text($("#scn_cp_push_titl").val());
	$("#pv_titl_detail").text($("#scn_push_msg").val());
 
	if($("#cp_act_strt_dt").val()==''){
		period="";
		$("#di_go").hide(); 
	}else{  
		$("#di_go").show();
	}
	$("#pv_period").text(period);
	if($("#bi_img_seq_uri").attr('src')){
		$("#pv_img").attr('src',$("#bi_img_seq_uri").attr('src'));
	}else{
		$("#pv_img").attr('src',"");
	}
	$("#coupon-push-display-popup").show();
}

var allTenant = function(){
	$("input[name='alltnt']").each(function(idx){
		var tntInfo = $("input[name='alltnt']").eq(idx).val().split(",");
		if($("#all_tenant_seq").is(":checked")){
			tenantAllChk(tntInfo[0],tntInfo[1],tntInfo[2],tntInfo[3],tntInfo[4],tntInfo[5]);
		}else{
			tenantAllUnChk(tntInfo[0],tntInfo[1],tntInfo[2],tntInfo[3],tntInfo[4],tntInfo[5]);
			
		}
	});
	
	if($("#all_tenant_seq").is(":checked")){
		$("input[name='tenant_seq']").prop('checked',true);
	}else{
		$("input[name='tenant_seq']").prop('checked',false);
	}
	
}
var allTarget = function(){   
	$("input[name='tenantInfo']").each(function(idx){
		var tenantInfo = $("input[name='tenantInfo']").eq(idx).val().split(",");
		if($("#allTenant").is(":checked")){
			ckAllTenant(tenantInfo[0],tenantInfo[1]);
		}else{
			ckUnAllTenant(tenantInfo[0],tenantInfo[1]);
		} 
	});   
	 
	if($("#allTenant").is(":checked")){
		$("input[name^='tenant_']").prop('checked',true);
	}else{
		$("input[name^='tenant_']").prop('checked',false);
	}
	
	var tot_cnt = 0;
	var push_cnt = 0;
	//if(appPushData.pushMembList.length > 0){
	tot_cnt = $("#all_zone_cnt").val();
		//push_cnt = 0;
		//if(appPushData.scenario.delMemSeq != null){
	push_cnt = ckeckTenant.length;
		//}
	//}
	//alert(push_cnt+","+tot_cnt);
	$("#zone_cnt").text("선택 "+push_cnt+"개  / 전체웰컴존 "+tot_cnt+"개");
} 
var getApplyCoupons = function(){
	if(appPushData.scenario.exp_yn == 'Y'){
		return false;
	}
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/getApplyCoupons';
	restUri += '?';
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit; 
	Common.REST.get(restUri, {}, function(data) {
		alert(data);
		appPushData.applyCouponList  = data.list;
		appPushData.paging = data.paging; 
 
		appPushData.paging.pages = [];
		  
		for(var  i = appPushData.paging.page_start; i <= appPushData.paging.page_end; ++i) {
			appPushData.paging.pages.push(i);
        }
		rtvAppPush.set(appPushData);
		
		$("#dialog-detail").dialog("open");
	}, function(data) {
		console.log('fail data is ', data);
	});
	
}


var ckAllMemb = function(){ 
	$("input[name='ck_mem']").each(function(idx){
		var cust_id = $("input[name='ck_mem']").eq(idx).attr("id");
		if(appPushData.scenario.app_tgt_mbr_div_cd != 4){
			if($("#all_ck").is(":checked")){
				unAllMemb(cust_id);
			}else{ 
				allMemb(cust_id); 
			}  
		}else{
			if($("#all_ck").is(":checked")){
				allMemb(cust_id); 
			}else{  
				unAllMemb(cust_id);
			}
		}
	});
	
	var tot_cnt = 0;
	var push_cnt = 0;
	if(appPushData.pushMembList.length > 0){
		tot_cnt = appPushData.pushMembList[0].tot_cnt;
		push_cnt = 0;
		if(appPushData.scenario.delMemSeq != null){
			push_cnt = appPushData.scenario.delMemSeq.length;
		}
	
		if(appPushData.scenario.app_tgt_mbr_div_cd == "1" || appPushData.scenario.app_tgt_mbr_div_cd == "3"){
			push_cnt = tot_cnt - push_cnt;
		} 
	}
	
	$("#push_tgt_cnt").html("푸쉬대상 "+push_cnt+"명  / 전체회원 "+tot_cnt+"명");
	
	 
	if($("#all_ck").is(":checked")){
		$("input[name='ck_mem']").prop('checked',true);
	}else{  
		$("input[name='ck_mem']").prop('checked',false);
	}
}    


var allMemb = function(cust_id){
	ckMemSeq.push(cust_id); 
	if(appPushData.scenario.app_tgt_mbr_div_cd != 4){
		if($("input[id="+cust_id+"]").is(":checked")){//체크된 경우
			if(appPushData.scenario.delMemSeq.indexOf(cust_id) == -1){
				appPushData.scenario.delMemSeq.push(cust_id);
			}
		}else{    
			if(appPushData.scenario.delMemSeq.indexOf(cust_id) == -1){//체크되지 아닌 경우
				appPushData.scenario.delMemSeq.push(cust_id);
			}   
		}	  
	}else{//vip회원인경우 
		if($("input[id="+cust_id+"]").is(":checked")){//체크된 경우
			if(appPushData.scenario.delMemSeq.indexOf(cust_id) == -1){
				appPushData.scenario.delMemSeq.push(cust_id);
			}
		}else{    
			if(appPushData.scenario.delMemSeq.indexOf(cust_id) == -1){//체크되지 아닌 경우
				appPushData.scenario.delMemSeq.push(cust_id);
			}   
		}	
	} 
	 
	
	
}   


var unAllMemb = function(cust_id){
	ckMemSeq.push(cust_id); 
	if(appPushData.scenario.app_tgt_mbr_div_cd != 4){
		if($("input[id="+cust_id+"]").is(":checked")){//체크된 경우
			if(appPushData.scenario.delMemSeq.indexOf(cust_id) == -1){
				arridx = appPushData.scenario.delMemSeq.indexOf(cust_id);
				appPushData.scenario.delMemSeq.splice(arridx,1);
			}   
		}else{    
			if(appPushData.scenario.delMemSeq.indexOf(cust_id) != -1){//체크되지 아닌 경우
				arridx = appPushData.scenario.delMemSeq.indexOf(cust_id);
				appPushData.scenario.delMemSeq.splice(arridx,1);
			}   
		}	  
	}else{//vip회원인경우 
		if($("input[id="+cust_id+"]").is(":checked")){//체크된 경우
			if(appPushData.scenario.delMemSeq.indexOf(cust_id) != -1){
				arridx = appPushData.scenario.delMemSeq.indexOf(cust_id);
				appPushData.scenario.delMemSeq.splice(arridx,1);
			}
		}else{ 
			if(appPushData.scenario.delMemSeq.indexOf(cust_id) != -1){//체크되지 아닌 경우
				arridx = appPushData.scenario.delMemSeq.indexOf(cust_id);
				appPushData.scenario.delMemSeq.splice(arridx,1);
			}   
		}
		
	}
} 

var getPushMembs = function(data, offset, limit) {
	if(appPushData.scenario.exp_yn == 'Y' || appPushData.scenario.app_tgt_mbr_div_cd == '2'
			|| appPushData.scenario.app_tgt_mbr_div_cd == '0'){
		return false;
	}
	
	ckMemSeq = [];
	var tgt_age = [];
	for(var i = 0 ; i < $("input[id^='tgtAge']").length ; i ++){
		if($("input[id=tgtAge"+i+"]").is(":checked")){
			tgt_age.push($("input[id=tgtAge"+i+"]").val());
		}
	}
	//alert("data.app_tgt_sex>>"+data.app_tgt_sex);
	//alert("data.tgt_age>>"+tgt_age);
	var offset = offset || 1;
	var limit = limit || 10; 
	var restUri = window.location.origin + '/rest/01/getPushMembs';
	restUri += '?';
	restUri += 'offset=' + offset; 
	restUri += '&limit=' + limit; 
	restUri += '&app_tgt_mbr_div_cd=' + data.app_tgt_mbr_div_cd;
	restUri += '&app_tgt_sex=' + data.app_tgt_sex;
	restUri += '&tgt_age=' + tgt_age; 
	restUri += '&memb_nm_ko=' + appPushData.filter.memb_nm_ko;

	if(data.app_tgt_mbr_div_cd == "4"){ 
		restUri += '&abst_no=' + $("#abst_no").val();
	} 
	Common.REST.get(restUri, {}, function(data) {
		appPushData.pushMembList  = data.list;
		
		appPushData.paging = data.paging; 
 
		appPushData.paging.pages = [];
		
		for(var  i = appPushData.paging.page_start; i <= appPushData.paging.page_end; ++i) {
			appPushData.paging.pages.push(i);
        } 

		
		//$("#push_cnt").text(appPushData.pushMembList[0].tot_cnt+"명");
		      
		rtvAppPush.set(appPushData);
		if(appPushData.scenario.app_tgt_mbr_div_cd != 4){
			$("input[name='ck_mem']").prop('checked',true);
			$("#selVip").hide();
		}else{
			$("#selVip").show();
		}
		
		if(appPushData.scenario.delMemSeq != null){
			for(var i = 0 ; i < appPushData.scenario.delMemSeq.length ; i ++){
				var cust_id = appPushData.scenario.delMemSeq[i];
				if(appPushData.scenario.app_tgt_mbr_div_cd != 4){
					
					$("input[id="+cust_id+"]").prop('checked',false);
				}else{ 
					$("input[id="+cust_id+"]").prop('checked',true);
				}
			} 
		}  
		
		
		if($("input[name=ck_mem]:checkbox:checked").length != 0 
				&& $("input[name=ck_mem]:checkbox:checked").length == $("input[name='ck_mem']").length){
			$("#all_ck").prop("checked",true); 
		}else{  
			$("#all_ck").prop("checked",false);
		} 
		 
		
		var tot_cnt = 0;
		var push_cnt = 0;
		if(appPushData.pushMembList.length > 0){
			tot_cnt = appPushData.pushMembList[0].tot_cnt;
			push_cnt = 0;
			if(appPushData.scenario.delMemSeq != null){
				push_cnt = appPushData.scenario.delMemSeq.length;
			}
		
			if(appPushData.scenario.app_tgt_mbr_div_cd == "1" || appPushData.scenario.app_tgt_mbr_div_cd == "3"){
				push_cnt = tot_cnt - push_cnt;
			} 
		}
		
		$("#push_tgt_cnt").html("푸쉬대상 "+push_cnt+"명  / 전체회원 "+tot_cnt+"명");
		
		$("#entry-list").show();
		
	}, function(data) {
		console.log('fail data is ', data);
	});
	
	
}


var changeMbr = function(o){
	resetDelMemSeq(); 
	if(o == "2" || o == "0"){ 
		$("#app_tgt_sex").attr("disabled",true);
		$("input[name^='app_tgt_age']").attr("disabled",true); 
	} else {  
		$("#selVip").hide();
		if(o == "4"){
			$("#selVip").show();
		} 
		$("#app_tgt_sex").attr("disabled",false);  
		$("input[name^='app_tgt_age']").attr("disabled",false);
	} 
	
	var restUri = window.location.origin + '/rest/01/pushMembCnt/'+o;
	Common.REST.get(restUri, {}, function(data) {//적용 대상 건수
		memb_tot_cnt = data;
		if(o == "1" || o == "3"){//회원, 관심 등록 고객 
			$("#push_memb_cnt").html("푸쉬대상 "+data+"명 <br/>전체회원 "+data+"명");
			$("#push_tgt_cnt").text("푸쉬대상 "+data+"명 / 전체회원 "+ data +"명");
		}else if(o == "4"){//vip
			$("#push_memb_cnt").html("푸쉬대상 0명 <br/>전체회원 "+data+"명");
			$("#push_tgt_cnt").text("푸쉬대상 0명 / 전체회원 "+ data +"명");
		}else{//전체 비회원
			$("#push_memb_cnt").html("");
		}
		 
		
		
	}, function(data) {
		console.log('fail data is ', data);
	});
} 


var abstMsts = function(){
	var restUri = window.location.origin + '/rest/01/getAbstMsts';
	Common.REST.get(restUri, {}, function(data) {
		appPushData.abstMstList  = data; 
  
		rtvAppPush.set(appPushData);
		
	}, function(data) {
		console.log('fail data is ', data);
	});
}


var ckTgtAge = function(obj,type){
	
	resetDelMemSeq();  
	if($(obj).val() == 'all'){
		if($(obj).is(":checked")){
			$("input:checkbox[id='tgtAge1']").attr('checked',false);  
			$("input:checkbox[id='tgtAge2']").attr('checked',false);  
			$("input:checkbox[id='tgtAge3']").attr('checked',false);  
			$("input:checkbox[id='tgtAge4']").attr('checked',false);
			
			appPushData.scenario.app_tgt_age_20 = null;
			appPushData.scenario.app_tgt_age_30 = null;
			appPushData.scenario.app_tgt_age_40 = null;
			appPushData.scenario.app_tgt_age_50 = null;
			appPushData.scenario.app_tgt_age_all = 'Y';
		}else{
			appPushData.scenario.app_tgt_age_all = null;
		}
	}else{
		if($(obj).is(":checked")){
			$("#tgtAge0").prop('checked',false); 
			appPushData.scenario.app_tgt_age_all = "";
			appPushData.scenario[$(obj).attr("name")] = $(obj).val(); 
		}else{ 
			appPushData.scenario[$(obj).attr("name")] = null;
		}
	}
	
}
//삭제 회원
var ckMemb2 = function(obj, cust_id){
	ckMemSeq.push(cust_id);
	if(!$(obj).is(":checked")){
		if(appPushData.scenario.delMemSeq.indexOf(cust_id) != -1){
			arridx = appPushData.scenario.delMemSeq.indexOf(cust_id);
			appPushData.scenario.delMemSeq.splice(arridx,1);
		}
		
	}else{//체크된 경우
		appPushData.scenario.delMemSeq.push(cust_id);
	}
	
	var tot_cnt = 0;
	var push_cnt = 0;
	if(appPushData.pushMembList.length > 0){
		tot_cnt = appPushData.pushMembList[0].tot_cnt;
		push_cnt = 0;
		if(appPushData.scenario.delMemSeq != null){
			push_cnt = appPushData.scenario.delMemSeq.length;
		}
	
		if(appPushData.scenario.app_tgt_mbr_div_cd == "1" || appPushData.scenario.app_tgt_mbr_div_cd == "3"){
			push_cnt = tot_cnt - push_cnt;
		} 
	}
	
	$("#push_tgt_cnt").html("푸쉬대상 "+push_cnt+"명  / 전체회원 "+tot_cnt+"명");
	
	
} 
//삭제 회원
var ckMemb = function(obj, cust_id){
	ckMemSeq.push(cust_id);
	if(appPushData.scenario.app_tgt_mbr_div_cd != 4){//vip회원인경우
		if($(obj).is(":checked")){//체크된 경우
			if(appPushData.scenario.delMemSeq.indexOf(cust_id) != -1){
				arridx = appPushData.scenario.delMemSeq.indexOf(cust_id);
				appPushData.scenario.delMemSeq.splice(arridx,1);
			}
			 
		}else{//체크되지 아닌 경우 
			//alert(cust_id);
			appPushData.scenario.delMemSeq.push(cust_id);
		}	
	}else{//vip회원인경우 
		if(!$(obj).is(":checked")){
			if(appPushData.scenario.delMemSeq.indexOf(cust_id) != -1){
				arridx = appPushData.scenario.delMemSeq.indexOf(cust_id);
				appPushData.scenario.delMemSeq.splice(arridx,1);
			}
			
		}else{//체크된 경우
			appPushData.scenario.delMemSeq.push(cust_id);
		}
	}
	
	var tot_cnt = 0;
	var push_cnt = 0;
	if(appPushData.pushMembList.length > 0){
		tot_cnt = appPushData.pushMembList[0].tot_cnt;
		push_cnt = 0;
		if(appPushData.scenario.delMemSeq != null){
			push_cnt = appPushData.scenario.delMemSeq.length;
		}
	
		if(appPushData.scenario.app_tgt_mbr_div_cd == "1" || appPushData.scenario.app_tgt_mbr_div_cd == "3"){
			push_cnt = tot_cnt - push_cnt;
		} 
	}
	
	$("#push_tgt_cnt").html("푸쉬대상 "+push_cnt+"명  / 전체회원 "+tot_cnt+"명");
	
	
} 


var saveMem = function(){
	$("#entry-list").hide();
	ckMemSeq = [];

	var tot_cnt = 0;
	var push_cnt = 0;
	if(appPushData.pushMembList.length > 0){
		tot_cnt = appPushData.pushMembList[0].tot_cnt;
		push_cnt = 0;
		if(appPushData.scenario.delMemSeq != null){
			push_cnt = appPushData.scenario.delMemSeq.length;
		}
	
		if(appPushData.scenario.app_tgt_mbr_div_cd == "1" || appPushData.scenario.app_tgt_mbr_div_cd == "3"){
			push_cnt = tot_cnt - push_cnt;
		} 
	}  
  
	$("#push_memb_cnt").html("푸쉬대상 "+push_cnt+"명 <br/>전체회원 "+tot_cnt+"명");
	
	
}

var closeMem = function(){
	$("#entry-list").hide();
	for(var i = 0 ; i < ckMemSeq.length ; i ++){
		var mem_seq = ckMemSeq[i];
		if(appPushData.scenario.delMemSeq.indexOf(mem_seq) != -1){
			arridx = appPushData.scenario.delMemSeq.indexOf(mem_seq);
			appPushData.scenario.delMemSeq.splice(arridx,1);
		}else{ 
			appPushData.scenario.delMemSeq.push(mem_seq);
		}
	} 
	ckMemSeq = [];
	
	appPushData.filter.memb_nm_ko = '';
	$("#memb_nm_ko").val(''); 
}

//회원 삭제 초기화
var resetDelMemSeq = function(){
	appPushData.scenario.delMemSeq = [];
}
     
var getCoupons =  function(offset, limit){      
	if(appPushData.scenario.exp_yn == 'Y' ||  appPushData.scenario.push_time_div_cd == '6'){
		return false; 
	} 
	var offset = offset || 1;
	var limit = limit || 10;
	var restUri = window.location.origin + '/rest/01/3/coupons';
	restUri += '?'; 
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit;
	restUri += '&cp_titl=' + appPushData.filter.cp_titl;
	Common.REST.get(restUri, {}, function(data) {
		//alert(data.count);
		$("#cp_cnt").text(data.srch_cnt);
		appPushData.couponList = data.list;
		appPushData.paging = data.paging;
		
		//appPushData.couponList = data.list;
		//appPushData.paging = data.paging;
		
		appPushData.paging.pages = [];
		
		for(var i = appPushData.paging.page_start; i <= appPushData.paging.page_end; ++i) {
			appPushData.paging.pages.push(i);
        }
		rtvAppPush.set(appPushData);
		 
		$('input:radio[name=cp_ck]:input[value='+appPushData.scenario.cp_seq+']').prop("checked", true);
		if($("#acp").val() == ''){
			$("input[name='cp_ck']").prop('checked',false); 
		}
		//파일
	}, function(data) {
		 
		console.log('fail data is ', data);
		
	}); 
	 
	$("#couponP").show();

};

var opentenant =  function(offset, limit){
	if(appPushData.scenario.exp_yn == 'Y' || appPushData.scenario.push_time_div_cd != 2){
		return false;
	}
	var offset = offset || 1;
	var limit = limit || 20;
	var restUri = window.location.origin + '/rest/01/getSnTenants';
	restUri += '?'; 
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit;
	restUri += '&cate_seq=' + appPushData.filter.cate_seq;
	restUri += '&tnt_nm_ko=' + appPushData.filter.tnt_nm_ko;
	Common.REST.get(restUri, {}, function(data) {
		appPushData.tenantList = data.list;
		appPushData.paging = data.paging;
				
		appPushData.paging.pages = [];
		
		for(var i = appPushData.paging.page_start; i <= appPushData.paging.page_end; ++i) {
			appPushData.paging.pages.push(i);
        }
		rtvAppPush.set(appPushData);

		$("input[id^='tenant_']").prop('checked',false);
		
		
		if(appPushData.scenario.selTenantList != null){  
			for(var i = 0 ; i < appPushData.scenario.selTenantList.length ; i ++){
				var idx = appPushData.scenario.selTenantList[i].tnt_seq;
				$("input[id=tenant_"+idx+"]").prop('checked',true);  
			}  
		}
		
		if(arrtenantNum != null){  
			for(var i = 0 ; i < arrtenantNum.length ; i ++){
				var idx = arrtenantNum[i].split('///')[0];
				$("input[id=tenant_"+idx+"]").prop('checked',true);  
			}  
		}
		
		getCategorys();
		$("input[id='all_tenant_seq']").prop('checked',false);
		$("div[name='tnt-reg-popup']").show();
	}, function(data) {
		 
		console.log('fail data is ', data);
		
	}); 
};

var getCategorys =  function(){
	var restUri = window.location.origin + '/rest/01/getCategorys';
	Common.REST.get(restUri, {}, function(data) {
		appPushData.categoryList = data.list;
		rtvAppPush.set(appPushData); 
	}, function(data) { 
		  
		console.log('fail data is ', data);
		
	}); 
	
}; 

var tenantChk = function(val1,val2,val3,val4,val5,val6){
	var arridx = "";
	var arrtenant = val1+"///"+val2+"///"+val3+"///"+val4+"///"+val5+"///"+val6;

	
	if(arrDelTenantNum.indexOf(arrtenant) != -1){
		arridx = arrDelTenantNum.indexOf(arrtenant);
		arrDelTenantNum.splice(arridx,1);
	}else{
		arrDelTenantNum.push(arrtenant);
	}
	 
	if(arrtenantNum.indexOf(arrtenant) != -1){
		arridx = arrtenantNum.indexOf(arrtenant);
		arrtenantNum.splice(arridx,1);
	}else{
		arrtenantNum.push(arrtenant);
	}
	
};

var tenantAllChk = function(val1,val2,val3,val4,val5,val6){
	var arridx = "";
	var arrtenant = val1+"///"+val2+"///"+val3+"///"+val4+"///"+val5+"///"+val6;
	
	if(arrDelTenantNum.indexOf(arrtenant) != -1){
		arridx = arrDelTenantNum.indexOf(arrtenant);
		arrDelTenantNum.splice(arridx,1);
	}else{
		arrDelTenantNum.push(arrtenant);
	} 
	    
	if(arrtenantNum.indexOf(arrtenant) != -1){
		//arridx = arrtenantNum.indexOf(arrtenant);
		//arrtenantNum.splice(arridx,1);
	}else{
		arrtenantNum.push(arrtenant);
	}
	
};

var tenantAllUnChk = function(val1,val2,val3,val4,val5,val6){
	var arridx = "";
	var arrtenant = val1+"///"+val2+"///"+val3+"///"+val4+"///"+val5+"///"+val6;
	
	if(arrDelTenantNum.indexOf(arrtenant) != -1){
		arridx = arrDelTenantNum.indexOf(arrtenant);
		arrDelTenantNum.splice(arridx,1);
	}else{
		arrDelTenantNum.push(arrtenant);
	}  
	
	if(arrtenantNum.indexOf(arrtenant) != -1){
		arridx = arrtenantNum.indexOf(arrtenant);
		arrtenantNum.splice(arridx,1);
	}else{
		//arrtenantNum.push(arrtenant);
	}
	
};

var saveTenant = function(){
	$("#tnt_nm_ko").val('');
	$("#cate_seq").val(''); 
	appPushData.filter.tnt_nm_ko = '';
	appPushData.filter.cate_seq = '';
	arrDelTenantNum = []; 
	var arr = new Array();
	var idx = arrtenantNum.length; 
	for(var i = 0 ; i < arrtenantNum.length ; i ++){
		arr.push({
		   'tnt_seq' : arrtenantNum[i].split('///')[0] ,
		   'tnt_nm_ko' : arrtenantNum[i].split('///')[1],
		   'busi_tnt_cd' : arrtenantNum[i].split('///')[2],
		   'zone_id' : arrtenantNum[i].split('///')[3],
		   'cate_nm_ko' : arrtenantNum[i].split('///')[4],
		   'room_num' : arrtenantNum[i].split('///')[5],
		});
	}  

	appPushData.scenario.selTenantList = arr; 
	rtvAppPush.set(appPushData);
	
	var btnText = ""; 
	if(arrtenantNum.length > 0){
		btnText = arrtenantNum[0].split('///')[1];
		if(idx > 1){ 
			btnText +=  " 외"+ (idx-1) +"개 테넌트 적용";
		} 
	}
	$("#openSeltenantBtn").val(btnText);	
	$("div[name='tnt-reg-popup']").hide();
};

var closeTenant = function(){
	for(var i = 0 ; i < arrDelTenantNum.length ; i ++){
		if(appPushData.scenario.selTenantList == null){ 
			arrtenantNum = [];
			break; 
		}   
		var tnt = arrDelTenantNum[i];
		arridx = arrtenantNum.indexOf(tnt);
		if(arridx > 0){ 
			arrtenantNum.splice(arridx,1);
		}else{ 
			arrtenantNum.push(arrDelTenantNum[i]);
		} 
	} 
	arrDelTenantNum = [];
	  
	$("#tnt_nm_ko").val('');
	$("#cate_seq").val(''); 
	appPushData.filter.tnt_nm_ko = '';
	appPushData.filter.cate_seq = '';
	
	$("input[id='all_tenant_seq']").prop('checked',false);
	$("div[name='tnt-reg-popup']").hide();
}

var openSeltenant = function(){
	if(appPushData.scenario.exp_yn == 'Y' || appPushData.scenario.push_time_div_cd != 2){
		return false;
	}
	$("div[name='tnt-reg-popup2']").show(); 
} 

var deletetenant = function(num){
	$("tr[id=selTenant_"+num+"]").hide();     
} 

var selTenantSave = function(){
	$("div[name='tnt-reg-popup2']").hide();
	for(var i = $("tr[id^='selTenant_']").length ; i >= 0 ; --i){
		if($("tr[id^='selTenant_']").eq(i).css("display") == "none"){
			arrtenantNum.splice(i,1);
		}
	}  
	
	$("tr[id^='selTenant_']").show();
	saveTenant();
	
}
 
var selTenantClose = function(){
	$("tr[id^='selTenant_']").show();  
	$("div[name='tnt-reg-popup2']").hide();
} 

var openTenant2 = function(offset, limit, idx){
	if(appPushData.scenario.exp_yn == 'Y' || appPushData.scenario.push_time_div_cd != 3){
		return false; 
	}
	if(idx != "" && idx != undefined){
		movingTntIdx = idx;
	} 

	$("input[id^='tenant2_']").prop("checked",false);
	var offset = offset || 1;
	var limit = limit || 10; 
	var restUri = window.location.origin + '/rest/01/getSnTenants';
	restUri += '?'; 
	restUri += 'offset=' + offset;
	restUri += '&limit=' + limit;
	restUri += '&cate_seq=' + appPushData.filter.cate_seq;
	restUri += '&tnt_nm_ko=' + appPushData.filter.tnt_nm_ko;
	Common.REST.get(restUri, {}, function(data) {
		appPushData.tenantList2 = data.list;
		appPushData.paging = data.paging;
				
		appPushData.paging.pages = [];
		
		for(var i = appPushData.paging.page_start; i <= appPushData.paging.page_end; ++i) {
			appPushData.paging.pages.push(i);
        }
		rtvAppPush.set(appPushData);
		
		var zone_id = $("input[id=movingZoneId"+idx+"]").val();
		if(zone_id != null && zone_id != ''){ 
			$("input[id=tenant2_"+zone_id+"]").prop("checked",true); 
		}
		
		getCategorys();
		$("div[name='tnt-reg-popup3']").show();
	}, function(data) {
		 
		console.log('fail data is ', data);
		
	});  
}

var movingTntSave = function(o) {
	$("#tnt_nm_ko").val('');
	$("#cate_seq").val(''); 
	appPushData.filter.tnt_nm_ko = '';
	appPushData.filter.cate_seq = '';
	var idx = movingTntIdx;
	var v_ch = 0;
	$("input[name='movingTntZoneId']").each(function(idx){
		if($("input[id=movingZoneId"+idx+"]").val() == arrtenantNum[0].split('///')[3]){
			v_ch = 1;
		}
	});
	if(v_ch == 1){
		alert('이미 선택된 존입니다. 다른 존을 선택 하세요.');
		return false;
	}
	
	$("input[id=movingZoneId"+idx+"]").val(arrtenantNum[0].split('///')[3]); 
	$("input[id=movingBusiCd"+idx+"]").val(arrtenantNum[0].split('///')[2]); 
	$("div[id=movingTnt"+idx+"]").text(arrtenantNum[0].split('///')[1]);
	 
	rtvAppPush.set(appPushData);
	$("div[name='tnt-reg-popup3']").hide();
}

 
var movingTntChk = function(tnt_seq, tnt_nm, busi_tnt_cd, zone_id) {
	arrtenantNum = [];
	tenantChk(tnt_seq, tnt_nm, busi_tnt_cd, zone_id);
} 

var movingTntClose = function() {
	$("#tnt_nm_ko2").val(''); 
	$("#cate_seq2").val(''); 
	appPushData.filter.tnt_nm_ko = '';
	appPushData.filter.cate_seq = '';
	$("div[name='tnt-reg-popup3']").hide(); 
}
   
var delTenant = function(idx) {
	if(appPushData.scenario.exp_yn == 'Y' || appPushData.scenario.push_time_div_cd != 3){
		return false;
	}
	$("div[id=movingTnt"+idx+"]").text(''); 
	$("input[id=movingZoneId"+idx+"]").val(''); 
	$("input[id=movingBusiCd"+idx+"]").val('');  
}
var delAppCoupon = function() {
	if(appPushData.scenario.exp_yn == 'Y'){
		return false;
	}
	appPushData.scenario.cp_seq = "";
	appPushData.filter.cp_titl = '';
	$("input[id=acp]").val(''); 
	  
}

var saveScenario = function(param){
	if(!validation(appPushData.scenario)){
		return false;
	} 
	
	var arr = new Array();
	$("input[name='movingTntZoneId']").each(function(idx){    
		var zone_id = $("input[name='movingTntZoneId']:eq(" + idx + ")").val();
		var tnt_busi_cd = $("input[name='movingTntBusiCd']:eq(" + idx + ")").val();
		if(zone_id != ''){
			arr.push({
			   'busi_tnt_cd' : tnt_busi_cd,
			   'zone_id' : zone_id,
			});
			appPushData.scenario.selTenantList = arr; 
		}
	}); 
	var tgt_age = [];
	for(var i = 0 ; i < $("input[id^='tgtAge']").length ; i ++){
		if($("input[id=tgtAge"+i+"]").is(":checked")){
			tgt_age.push($("input[id=tgtAge"+i+"]").val());
		}
	}

	appPushData.scenario.tgt_age = tgt_age;
	param.push_strt_dt    = 	$("#push_strt_dt").val().split('-').join("");
	param.push_end_dt     = 	$("#push_end_dt").val().split('-').join("");
	
	
	var type = getUriArrVal()[3];
	var restUri = '/rest/01/regScenario/C';
	if(getUriArrVal()[5]){ 
		restUri = '/rest/01/regScenario/U';
		type = getUriArrVal()[4];
	} 
	Common.REST.post(window.location.origin + restUri, param, function(data) {
		location.href='/01/'+getUriArrVal()[2]+'/push/'+ type +'/' + data.extra;
		
	}, function(data) {  
		console.log('fail data is ', data);
		
	});  
}

var chPushTimeDiv = function(type,type2){
	if(type2 != 'get'){
		appPushData.scenario.selTenantList = [];
		arrtenantNum = [];
		$("#openSeltenantBtn").val('');
		$("div[id^='movingTnt']").text('');
		$("input[id^=movingZoneId]").val(''); 
		rtvAppPush.set(appPushData);
	} 
	
	if(type == '1'){  
		$("[id^=stay_time]").attr("disabled",true); 
	}else if(type == '2' || type == '6'){ 
		$("#stay_time2").attr("disabled",true);
		$("#stay_time_cd2").attr("disabled",true);
		$("#stay_time").attr("disabled",false);
		$("#stay_time_cd").attr("disabled",false);
		$("#stay_time2").val("");
		$("#stay_time_cd2").val("0");
	}else if(type == '4'){ 
		$("#day_cnt1").attr("disabled",false);
		$("#day_cnt2").attr("disabled",true);
		$("#link_url").attr("disabled",true);
		$("#day_cnt2").val("");
		$("#link_url").val("");
	}else if(type == '5'){
		$("#day_cnt1").attr("disabled",true);
		$("#day_cnt2").attr("disabled",false);
		$("#link_url").attr("disabled",true);
		$("#link_url").val("");
		$("#day_cnt1").val("");
	}else if(type == '7'){
		$("#day_cnt1").attr("disabled",true);
		$("#day_cnt2").attr("disabled",true);
		$("#link_url").attr("disabled",false);
		$("#day_cnt2").val("");
		$("#day_cnt1").val(""); 
	}else{  
		$("#stay_time").attr("disabled",true);
		$("#stay_time_cd").attr("disabled",true);
		$("#stay_time2").attr("disabled",false); 
		$("#stay_time_cd2").attr("disabled",false);
		$("#stay_time").val("");
		$("#stay_time_cd").val("0");   
	}
	
	 
	if(type == '6'){
		$("#acp").val('');
		applyCouponList = null;
	}
} 

var getScenario = function(data){
	var restUri = '/rest/01/getScenario/'+data;
	Common.REST.get(restUri, {}, function(data) {
		appPushData.scenario = data; 
		$("#acp").val(data.cp_titl);
		var push_cnt = data.push_cnt;
		chPushTimeDiv(appPushData.scenario.push_time_div_cd,'get');
		if(data.push_time_div_cd == '2'){
			setTenant(data); 
			selChange(data.stay_time_cd,'stay_time');
		}else if(data.push_time_div_cd == '6'){ 
			selChange(data.stay_time_cd,'stay_time'); 
		}else if(data.push_time_div_cd == '3') {
			setMovingTenant(appPushData.scenario.selTenantList);  
			selChange(data.stay_time_cd2,'stay_time2');
		}else if(data.push_time_div_cd == '5'){
			appPushData.scenario.day_cnt2 = data.day_cnt;
			appPushData.scenario.day_cnt = '';
		} 
		
		if(data.app_tgt_mbr_div_cd == "1" || data.app_tgt_mbr_div_cd == "3"){
			push_cnt = data.memb_cnt - data.push_cnt;
			$("#push_memb_cnt").html("푸쉬대상 "+push_cnt+"명 <br/>전체회원 "+data.memb_cnt+"명");
		} 
		   
		
		 
		if(data.exp_yn == 'N'){ 
			$("#btnPostion").show(); 
			$("#btnSave").show(); 
		}else{
			$("#btnSave").hide(); 
			$("#btnPostion").hide();
			$("#btnUnPostion").show();  
			$('input').attr("disabled",true); 
			$('select').attr("disabled",true); 
		} 
		
		rtvAppPush.set(appPushData);
	}, function(data) { 
		console.log('fail data is ', data);
		
	});
}

var getScnObImg =  function(){
	var type = getUriArrVal()[3];
	//alert(type);
	var restUri = window.location.origin + '/rest/01/getScnObImg/'+type;
	Common.REST.get(restUri, {}, function(data) {

		if(data != null){
			if(data.img_uri != null){
				$("#bi_img_seq").attr("class","img-uploader after");
				$("#bi_img_seq_uri").attr('src',data.img_uri);
				appPushData.scenario.bi_img_seq_uri = data.img_seq;
			}

		}
		
	}, function(data) { 
		console.log('fail data is ', data);
		
	}); 
	
	//$("#dialog-detail").dialog("open");

}

var setTenant = function(data){
	for(var i = 0 ; i < data.selTenantList.length ; i++){
		tenantChk(data.selTenantList[i].tnt_seq, 
				data.selTenantList[i].tnt_nm_ko,
				data.selTenantList[i].busi_tnt_cd,     
				data.selTenantList[i].zone_id,
				data.selTenantList[i].cate_nm_ko,
				data.selTenantList[i].room_num);
		
	}
	  
	var btnText = "";  
	if(arrtenantNum.length > 0){
		btnText = data.selTenantList[0].tnt_nm_ko;
		if(data.selTenantList.length > 1){   
			btnText +=  " 외"+ (data.selTenantList.length-1) +"개 테넌트 적용";
		} 
	}
	$("#openSeltenantBtn").val(btnText);
}


var setMovingTenant = function(selTenantList){
	for(var i = 0 ; i < selTenantList.length ; i++){
		movingTntChk(selTenantList[i].tnt_seq, 
				selTenantList[i].tnt_nm_ko,
				selTenantList[i].busi_tnt_cd,     
				selTenantList[i].zone_id);
		 
		$("input[id=movingZoneId"+(i+1)+"]").val(selTenantList[i].zone_id); 
		$("input[id=movingBusiCd"+(i+1)+"]").val(selTenantList[i].busi_tnt_cd); 
		$("div[id=movingTnt"+(i+1)+"]").text(selTenantList[i].tnt_nm_ko);
	}
}  


var regScenarioUnPosting = function(param){
	var restUri = '/rest/01/regScenarioUnPosting/'+getUriArrVal()[5];
	Common.REST.post(window.location.origin + restUri, param, function(data) {
		location.href='/01/'+getUriArrVal()[2]+'/push/'+ getUriArrVal()[4] +'/' + getUriArrVal()[5];
		
	}, function(data) {
		console.log('fail data is ', data);
		
	}); 
}

 
var regScenarioPosting = function(param){ 
	var restUri = '/rest/01/regScenarioPosting/'+getUriArrVal()[5];
	Common.REST.post(window.location.origin + restUri, param, function(data) {
		location.href='/01/'+getUriArrVal()[2]+'/push/'+ getUriArrVal()[4] +'/' + getUriArrVal()[5];
	}, function(data) {
		console.log('fail data is ', data);
		
	}); 
}
 
var selChange = function(o, text){
	if(o == '0'){
		$("input[id="+text+"]").attr("disabled",false);
	}else{    
		$("input[id="+text+"]").attr("disabled",true);
		$("input[id="+text+"]").val(o);
		if(text == 'stay_time2'){
			appPushData.scenario.stay_time2 = o;
		}else{
			appPushData.scenario.stay_time = o;
		}
	}

} 


var validation = function(data){
	appPushData.scenario.push_strt_dt           = 	$("#push_strt_dt").val().split('-').join("");
	appPushData.scenario.push_end_dt            = 	$("#push_end_dt").val().split('-').join("");
 
	if(data.scn_cp_push_titl == null || data.scn_cp_push_titl == '') {
		alert("타이틀을 입력 하세요.");
		return false;
	}else if(data.app_tgt_mbr_div_cd == '4' && data.delMemSeq == 0) {
		alert("vip회원 push적용 대상을 선택 하세요.");
		return false;
	}else if(data.push_strt_dt == null || data.push_strt_dt == '') {
		alert("푸시 기간 시작일을 입력 하세요.");
		return false;
	}else if(data.push_end_dt == null || data.push_end_dt == '') {
		alert("푸시 기간 종료일을 입력 하세요.");
		return false;
	}else if(data.push_strt_dt > data.push_end_dt) {
		alert("푸시 기간을 확인하세요");
		return false;
	}else if(data.bi_img_seq_uri == null || data.bi_img_seq_uri == ''){
		alert("이미지가 누락되었습니다. 이미지 등록 누락시 게시되지않습니다");
		return false;
	}else if(data.push_time_div_cd == '2' || data.push_time_div_cd == '6') {
		if((!data.selTenantList > 0 || data.selTenantList == '') 
				&& data.push_time_div_cd == '2'){
			alert("특정 zone 방문 및 체류 시간 zone을 선택하세요.");
			return false; 
		}else if(data.stay_time == null || data.stay_time == ''){
			alert("체류 시간을 설정 하세요.");
			return false;
		} 
	}else if(data.push_time_div_cd == '3' ) {
		var v_ck = 0;
		$("input[name='movingTntZoneId']").each(function(idx){
			var zone_id = $("input[name='movingTntZoneId']:eq(" + idx + ")").val();
			if(zone_id != null && zone_id != ''){
				v_ck = 1;
			}
		});
		if(v_ck == 0){
			alert("동선별 푸시 zone을 선택하세요.");
			return false;
		}else if(data.stay_time2 == null || data.stay_time2 == ''){
			alert("체류 시간을 설정 하세요..");
			return false;
		}
	}else if(data.push_time_div_cd == '4' && (data.day_cnt == '' || data.day_cnt == null)) {
		alert("특정기간 스타필드 미방문 고객 경과일을 입력 해주세요.");
		return false;
	}else if(data.push_time_div_cd == '5' && (data.day_cnt2 == '' || data.day_cnt2 == null)) {
		alert("특정기간 앱 미사용 고객 경과일을 입력 해주세요.");
		return false; 
	} 
	

	if(data.scn_push_msg == null || data.scn_push_msg == '') {
		alert("푸시메세지를 입력 하세요.");
		return false;
	} 
	
	return true;
}


//이미지 업로드 버튼 설정 
var initTitleImgUploadButton = function() {
	var $target = null;
	
	var beforStart = function(e, data, cb) {
		$target = $(e.target);
 		
   	var file_name = data.files[0].name;
      var extension = file_name.substring(file_name.lastIndexOf('.')+1).toLowerCase();

      var clear_extensions = 'jpeg/jpg/png';

      if(clear_extensions.search(extension) === -1) {
          alert('이미지는 jpg, png만 가능합니다.');
          return;
      }

      if(typeof cb === 'function') {
          cb();
      }
	};
	
	var done = function(r) {
		var $targetParent = $target.parents(".img-uploader");
		$targetParent.removeClass("before").addClass("after");
		appPushData.scenario[$targetParent.attr("id")+"_uri"] = r.img_seq;
		appPushData.scenario[$targetParent.attr("id")] = r.img_seq;
		$("img[id="+ $targetParent.attr("id")+"_uri" +"]").attr('src',r.img_uri);
	}; 
	 
	var params = {
			url : '/rest/01/file/images', //upload url
		 	beforeStart : beforStart, // called before start uploading
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	};
	
	$('.file-uploader-wrap > .basic-btn').each(function(){
		$(this).fileInputButton(params);
	});
	
}


$(function() {
	abstMsts(); 
	changeMbr('0');
	datepicker(); 
	initTitleImgUploadButton();
	//alert("aaa");
	//getScnObImg();
	if(getUriArrVal()[3] == '2'){ 
		appPushData.scenario.push_time_div_cd = '4';
	}
	
	if(getUriArrVal()[5]){ 
		getScenario(getUriArrVal()[5]);
	} 
});


