
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
		zone_id : null,
		mapping_type : 'N',
		x_ctn_cord : null,
		y_ctn_cord : null,
		//conv_faci_nm_ko : null,
		//conv_faci_dtl_seq : null,
	},
	facilist : null,
	lbsList : null,
	
};


var rtvTenant = new Ractive({
	el : '#tenant-list',
	template : '#tmpl-tenant-list',
	data : tenantData
});
 
rtvTenant.on({
	'modifyzoneId' : function(o) { 
		modifyzoneId(tenantData.tenant,1);
	},
	'deletezoneId' : function(o) { 
		modifyzoneId(tenantData.tenant,2);
	},
	'search' : function(o) { 
		getTenantList();
	},
	'search_init' : function(o) {
		getTenantList("init");
	}
	
}); 
 

var modifyzoneId = function(data, type){
	if($(":input:radio[name=tntZone]:checked").val()){
		
		if(type == 1){
			if(!$(":input:radio[name=lbsZone]:checked").val()){
				alert("LBS 존을 선택하세요.");
				return false;
				
			}else if(!$(":input:radio[name='tntZone']:checked").val() ||
				 !$(":input:radio[name='lbsZone']:checked").val()){
				alert("매핑할 LBS존 편의시설을 선택하세요.");
				return false;
				
			}else if($(":input:radio[name='tntZone']:checked").val() ||
				 $(":input:radio[name='lbsZone']:checked").val()){
				
				var tnt_nm = $(":input:radio[name='tntZone']:checked").val().split('/')[1];
				var lbs_tnt_nm = $(":input:radio[name='lbsZone']:checked").val().split('/')[3];
				if(tnt_nm.indexOf(lbs_tnt_nm) == -1 && lbs_tnt_nm.indexOf(tnt_nm) == -1){
					if(!confirm("테넌트가 일치 하지 않습니다 계속 진행 하시겠습니까?")){
						return false;
					}
				}
			}
			
			data.conv_faci_dtl_seq = $(":input:radio[name='tntZone']:checked").val().split('/')[0];
			data.zone_id = $(":input:radio[name='lbsZone']:checked").val().split('/')[0];
			data.x_ctn_cord = $(":input:radio[name='lbsZone']:checked").val().split('/')[1];
			data.y_ctn_cord = $(":input:radio[name='lbsZone']:checked").val().split('/')[2];
		}else{ 
			data.conv_faci_dtl_seq = $(":input:radio[name='tntZone']:checked").val().split('/')[0];
			data.zone_id = null;
			data.x_ctn_cord = null; 
			data.y_ctn_cord = null;
		}

		var restUri = window.location.origin + '/rest/01/faci/mapping';
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


var getTenantList = function(cond) {
	var restUri = window.location.origin + '/rest/01/faci/mappings';
	if(cond=='init'){
		tenantData.tenant.tnt_nm_ko="";
		tenantData.tenant.tenant_type="0";
		tenantData.tenant.mapping_type="N";
	}
	restUri += '?';
	restUri += 'bcn_cd=' + tenantData.tenant.bcn_cd;
	restUri += '&tnt_nm_ko=' + tenantData.tenant.tnt_nm_ko;
	restUri += '&tenant_type=' + tenantData.tenant.tenant_type;
	restUri += '&mapping_type=' + tenantData.tenant.mapping_type;
	  
	Common.REST.get(restUri, {}, function(data) {
		 
		tenantData.facilist = data.facilist; 
		tenantData.lbsList = data.lbslist;
   
		  
		rtvTenant.set(tenantData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};



$(function() {
	
	getTenantList();
});


