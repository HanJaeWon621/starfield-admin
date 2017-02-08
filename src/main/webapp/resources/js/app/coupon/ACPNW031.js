
////////////////////////////////////////////////////////
// global variables

var tenantData  = {
	tenant : {
		tnt_seq : null, 
		busi_tnt_seq : null,
		busi_tnt_cd : null, 
		bcn_cd : '01',
		tnt_nm_ko : '',
		tenant_type : '0',
		sh_text_type:'1',
		mapping_type : 'N',
		//coct_strt_prid : null,
		//coct_end_prid : null
	},
	tenantList : null,
	salesTenantList : null,
	
};


var rtvTenant = new Ractive({
	el : '#tenant-list',
	template : '#tmpl-tenant-list',
	data : tenantData
});
 
rtvTenant.on({
	'modifyBusiTntSeq' : function(o) { 
		modifyBusiTntSeq(tenantData.tenant,1);
	},
	'deleteBusiTntSeq' : function(o) { 
		modifyBusiTntSeq(tenantData.tenant,2);
	},
	'search' : function(o) { 
		getTenantList();
	},
	'search_init' : function(o) { 
		getTenantList("init");
	}
	
});
 



var modifyBusiTntSeq = function(data,type){
	if($(":input:radio[name=tntSeq]:checked").val()){
		
		if(type == 1){
			if(!$(":input:radio[name=salesTntSeq]:checked").val()){
				alert("영업 테넌트롤 선택해주세요.");
				return false;
			}
			 
			data.tnt_seq = $(":input:radio[name=tntSeq]:checked").val();
			data.busi_tnt_cd = $(":input:radio[name=salesTntSeq]:checked").val().split('/')[0];
			data.busi_tnt_seq = $(":input:radio[name=salesTntSeq]:checked").val().split('/')[1];
		}else{
			data.tnt_seq = $(":input:radio[name=tntSeq]:checked").val();
			data.busi_tnt_cd = null;
			data.busi_tnt_seq = null;
		}
		 
		var restUri = window.location.origin + '/rest/01/tenant/mapping';
		Common.REST.post(restUri, data, function(data) { 
			
			if(data.code == '-1'){
				alert(data.desc);
				return false; 
			}
			
			alert('등록완료!!');
			getTenantList();
		}, function(data) {
			console.log('fail data is ', data);
		});
		
		
	}else{
		alert("테넌트롤 선택해주세요.");
		
	}
}


var getTenantList = function(div_cd) {
	var restUri = window.location.origin + '/rest/01/tenant/mappings';
	//alert(div_cd);
	if(div_cd=='init'){
		tenantData.tenant.bcn_cd= '01';
		tenantData.tenant.tnt_nm_ko= '';
		tenantData.tenant.tenant_type= '0';
		//tenantData.tenant.sh_text_type= '1';
		tenantData.tenant.mapping_type= 'N';
	}
	restUri += '?';
	restUri += 'bcn_cd=' + tenantData.tenant.bcn_cd;
	restUri += '&tnt_nm_ko=' + tenantData.tenant.tnt_nm_ko;
	restUri += '&tenant_type=' + tenantData.tenant.tenant_type;
	//restUri += '&sh_text_type=1';
	restUri += '&mapping_type=' + tenantData.tenant.mapping_type;
	  
	Common.REST.get(restUri, {}, function(data) {

		//$("#tot_cnt").text(data.tenantlist[0].tot_cnt);
		//$("#busi_tnt_cnt").text(data.tenantlist[0].busi_tnt_cnt);
		//mv.addObject("data", cplist);
		//mv.addObject("data", all_busi_tnt_cnt);
		//mv.addObject("data", all_zone_cnt);
		//mv.addObject("data", all_faci_cnt);
		//map.put("all_busi_tnt_cnt", "전체테넌트 " + all_tot_cnt + "개 매핑테넌트  " + all_busi_tnt_cnt +"개");
		//map.put("all_zone_cnt", "전체테넌트 " +  all_tot_cnt    + "개 매핑테넌트  " + all_zone_cnt +"개");
		//map.put("all_faci_cnt", "전체편의시설 " + all_tot_cnt    + "개 매핑편의시설  " + all_faci_cnt +"개");
		//alert(data.data.all_busi_tnt_cnt);
		$("#mt").text("스타필드 테넌트 정보("+data.data.all_busi_tnt_cnt +")");
		tenantData.tenantList = data.data.tenantlist; 
		tenantData.salesTenantList = data.data.salestenantlist; 
		rtvTenant.set(tenantData); 
 		 
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};



$(function() {
	
	getTenantList();
});


