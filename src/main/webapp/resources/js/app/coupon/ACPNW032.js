
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
		sh_text_type : '1',
		//x_ctn_cord : null,
		//y_ctn_cord : null,
	},
	tenantList : null,
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
			}else if($(":input:radio[name='tntZone']:checked").val().split('/')[1]
				!= $(":input:radio[name='lbsZone']:checked").val().split('/')[3]){
				alert("같은 호수만 매핑 가능 합니다.");
				return false;
			} 
			data.tnt_seq = $(":input:radio[name='tntZone']:checked").val().split('/')[0];
			data.zone_id = $(":input:radio[name='lbsZone']:checked").val().split('/')[0];
			data.x_ctn_cord = $(":input:radio[name='lbsZone']:checked").val().split('/')[1];
			data.y_ctn_cord = $(":input:radio[name='lbsZone']:checked").val().split('/')[2];
			
		}else{ 
			data.tnt_seq = $(":input:radio[name='tntZone']:checked").val().split('/')[0];
			data.zone_id = null;
			data.x_ctn_cord = null; 
			data.y_ctn_cord = null;
		}

		var restUri = window.location.origin + '/rest/01/lbsZone/mapping';
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
	var restUri = window.location.origin + '/rest/01/lbsZone/mappings';
	
	if(cond=='init'){
		tenantData.tenant.tnt_nm_ko="";
		tenantData.tenant.tenant_type="1";
		tenantData.tenant.sh_text_type="1";
		tenantData.tenant.mapping_type="N";
	}
	
	restUri += '?';
	restUri += 'bcn_cd=' + tenantData.tenant.bcn_cd;
	restUri += '&tnt_nm_ko=' + tenantData.tenant.tnt_nm_ko;
	restUri += '&tenant_type=' + tenantData.tenant.tenant_type;
	restUri += '&sh_text_type=' + tenantData.tenant.sh_text_type;
	restUri += '&mapping_type=' + tenantData.tenant.mapping_type;
	
	Common.REST.get(restUri, {}, function(data) {
		
		$("#mt").text("스타필드 테넌트 정보("+data.data.all_zone_cnt +")");
		$("#tot_cnt").text(data.data.tenantlist[0].tot_cnt);
		$("#zone_cnt").text(data.data.tenantlist[0].zone_cnt);
		
	//	alert(data.tenantlist.length);  
		tenantData.tenantList = data.data.tenantlist; 
		tenantData.lbsList = data.data.lbslist;
   
		  
		rtvTenant.set(tenantData);
	}, function(data) {
		console.log('fail data is ', data);
	}); 
};



$(function() {
	
	getTenantList();
});


