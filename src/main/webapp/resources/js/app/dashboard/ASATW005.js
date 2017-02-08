
var IMAGE_WIDTH = 6492; 
var IMAGE_HEIGHT = 3171; 
 
var IMAGE_WIDTH_L1 = 6492;
var IMAGE_HEIGHT_L1 = 3171;

var IMAGE_WIDTH_L2 = 6597;
var IMAGE_HEIGHT_L2 = 3215;

var IMAGE_WIDTH_L3 = 6622;
var IMAGE_HEIGHT_L3 = 3161;

var IMAGE_WIDTH_L4 = 6500;
var IMAGE_HEIGHT_L4 = 3192;

var IMAGE_WIDTH_B1 = 7787;
var IMAGE_HEIGHT_B1 = 3595;

var IMAGE_WIDTH_B2 = 7785;
var IMAGE_HEIGHT_B2 = 3769;

var IMAGE_WIDTH_B3 = 7785;
var IMAGE_HEIGHT_B3 = 3771;

var HEATMAP_RADIUS = 10; 
 
var FLOOR_L1 = "/images/L1New.png";
var FLOOR_L2 = "/images/L2New.png";
var FLOOR_L3 = "/images/L3New.png";
var FLOOR_L4 = "/images/L4.png";
var FLOOR_B1 = "/images/B1New.png";
var FLOOR_B2 = "/images/B2New.png";
var FLOOR_B3 = "/images/B3New.png";

var heatmapData  = {
	heatmapList : null,
	filter : {}
};

var rtvHeatmap = new Ractive({
	el : '#heatmap', 
	template : '#tmpl-heatmap',
	data : heatmapData
	
});

rtvHeatmap.on({
	'changeFloor' : function() {
		//changeFloor();
		var floor = $("#sh_floor").val();
		floor = heatmapData.filter.sh_floor;
		//alert(floor);
		//alert("/${bcn_cd}/dashboard/visitor");
		heatmapData.filter.sh_floor=floor;
		rtvHeatmap.set(heatmapData); 
		var bcn_cd = getUriArrVal()[1];
		//var floor = getUriArrVal()[4];
		var sh_dt = getUriArrVal()[5];
		location.href="/"+bcn_cd +"/dashboard/visitor/"+floor+"/"+sh_dt;
		//getHeatmapList();
	}, 
	'search' : function() {
		var bcn_cd = getUriArrVal()[1];
		var floor = getUriArrVal()[4];
		var sh_dt = getUriArrVal()[5];
		sh_dt = heatmapData.filter.sh_dt.split('-').join('');
		//alert(sh_dt);
		location.href="/"+bcn_cd +"/dashboard/visitor/"+floor+"/"+sh_dt;
		//getHeatmapList();
	}, 
	'zoom' : function() {
		zoom(); 
	} 
	 
});
 
var zoom = function(){ 
	return;
	var newWindow = window.open("about:blank");   
	newWindow.location.href = '/01/dashboard/big/visitor?sh_dt='+heatmapData.filter.sh_dt.split('-').join('')+'&sh_floor='+heatmapData.filter.sh_floor;
} 

var changeFloor = function(){
	//alert("aaa");
	var floor = $("#sh_floor").val();
	if(floor == "L1"){
		$("#floor_img").attr("src",FLOOR_L1);
		IMAGE_WIDTH = IMAGE_WIDTH_L1;
		IMAGE_HEIGHT = IMAGE_HEIGHT_L1;
	}else if(floor == "L2"){
		$("#floor_img").attr("src",FLOOR_L2);
		IMAGE_WIDTH = IMAGE_WIDTH_L2;
		IMAGE_HEIGHT = IMAGE_HEIGHT_L2;
	}else if(floor == "L3"){
		$("#floor_img").attr("src",FLOOR_L3);
		IMAGE_WIDTH = IMAGE_WIDTH_L3;
		IMAGE_HEIGHT = IMAGE_HEIGHT_L3;
	}else if(floor == "L4"){
		$("#floor_img").attr("src",FLOOR_L4);
		IMAGE_WIDTH = IMAGE_WIDTH_L4;
		IMAGE_HEIGHT = IMAGE_HEIGHT_L4;
	}else if(floor == "B1"){
		$("#floor_img").attr("src",FLOOR_B1);
		IMAGE_WIDTH = IMAGE_WIDTH_B1;
		IMAGE_HEIGHT = IMAGE_HEIGHT_B1;
	}else if(floor == "B2"){
		$("#floor_img").attr("src",FLOOR_B2);
		IMAGE_WIDTH = IMAGE_WIDTH_B2;
		IMAGE_HEIGHT = IMAGE_HEIGHT_B2;
	}else if(floor == "B3"){
		$("#floor_img").attr("src",FLOOR_B3);
		IMAGE_WIDTH = IMAGE_WIDTH_B3;
		IMAGE_HEIGHT = IMAGE_HEIGHT_B3;
	} 
}


//alert(today());
$("#heat_dt").val(today());
todayA(); 
var getHeatmapList = function(){
	//alert(getUriArrVal()[1]);
	var floor = getUriArrVal()[4];
	var sh_dt = getUriArrVal()[5];
	//alert(sh_dt);
	//$("#sh_floor").val(floor);
	heatmapData.filter.sh_floor=floor;
	setFloor(floor);
	heatmapData.filter.sh_floor = floor;
	heatmapData.filter.sh_dt = sh_dt;
	var restUri = window.location.origin + '/admin/rest/01/getHeatmapList';
	restUri += '?';   
	restUri += 'sh_dt=' + heatmapData.filter.sh_dt.split('-').join('');
	restUri += '&sh_floor=' + heatmapData.filter.sh_floor;
	Common.REST.get(restUri, {}, function(data) {
		heatmapData.heatmapList = data.heatMapList; 
		rtvHeatmap.set(heatmapData); 
 		
		var imgWidth = $(".my-image").width();	
		var imgHeight = $(".my-image").height();	
		
		var result  = imgWidth / IMAGE_WIDTH * 100; //비율
		var result2  = imgHeight / IMAGE_HEIGHT * 100; // 비율
		//var result3 = imgWidth * result / 100; 
		//var result4 = imgHeight * result2 / 100;
		//rtvHeatmap.clear();
		for(var i = 0 ; i < heatmapData.heatmapList.length ; i ++){
			heatmapData.heatmapList[i].x =   Math.floor(heatmapData.heatmapList[i].x * result / 100); 
			heatmapData.heatmapList[i].y =   Math.floor(heatmapData.heatmapList[i].y * result2 / 100);
		} 
		    
		var heatmap = h337.create({
			container: document.getElementById('heatmapContainer')
	    });    
		window.h = heatmap;      
	    var d = heatmapData.heatmapList;     
	    //d=null;   			  
	    h.setData({
	    	min: 1,
	        max: data.max,  
	        data: d
	    });
	    
		
	}, function(data) {
		console.log('fail data is ', data);
	}); 
	
	/*var heatmap = h337.create({
		container: document.getElementById('heatmapContainer')
    });    
	window.h = heatmap;      
	  var d = [
               { x: 100, y: 200, value: 3, radius: 10 },
               { x: 110, y: 200, value: 3, radius: 10 },
               { x: 120, y: 200, value: 4, radius: 10 },
               { x: 130, y: 200, value: 4, radius: 10 },
               { x: 140, y: 200, value: 5, radius: 10 },
               { x: 150, y: 200, value: 4, radius: 10 },
               { x: 160, y: 200, value: 4, radius: 10 },
               { x: 170, y: 200, value: 6, radius: 10 },
               { x: 180, y: 200, value: 8, radius: 10 },
               { x: 190, y: 200, value: 8, radius: 10 },
               { x: 210, y: 200, value: 9, radius: 10 },
               { x: 220, y: 200, value: 9, radius: 10 },
               { x: 230, y: 200, value: 9, radius: 10 },
               { x: 240, y: 200, value: 9, radius: 10 },
               { x: 250, y: 200, value: 10, radius: 10 },
               { x: 260, y: 200, value: 10, radius: 10 },
               { x: 270, y: 200, value: 1, radius: 10 },
               { x: 280, y: 200, value: 1, radius: 10 },
               { x: 290, y: 200, value: 1, radius: 10 },
               { x: 300, y: 200, value: 1, radius: 10 },
               { x: 100, y: 200, value: 1, radius: 10 },
               { x: 100, y: 200, value: 1, radius: 10 },
               { x: 100, y: 200, value: 1, radius: 10 },
               { x: 100, y: 200, value: 1, radius: 10 },
               { x: 100, y: 200, value: 1, radius: 10 },
               { x: 100, y: 200, value: 1, radius: 10 },
               { x: 800, y: 150, value: 1, radius: 50 }
               ]; 
       			  
    h.setData({
    	min: 1,
        max: 10,  
        data: d
    });*/
}; 
function todayA() {
	var today = new Date()
	var todayString = today.getFullYear() + '년 '
		+ (today.getMonth() + 1) + '월 '
		+ today.getDate() + '일 '
		+ today.getHours() + '시 '
		+ today.getMinutes() + '분';
	$('#current-time').text(todayString);
};

$(function() {
	$("[name='datepicker']" ).datepicker({
		dateFormat: "yy-mm-dd",
		onSelect: function() { 
			rtvHeatmap.set('filter.search_option_yn', true);
			rtvHeatmap.updateModel();
		}
	});
	getHeatmapList();
	//getHeatmapList();
	
});

function setFloor(floor) {
	//var floor = $("#sh_floor").val();
	if(floor == "L1"){
		$("#floor_img").attr("src",FLOOR_L1);
		IMAGE_WIDTH = IMAGE_WIDTH_L1;
		IMAGE_HEIGHT = IMAGE_HEIGHT_L1;
	}else if(floor == "L2"){
		$("#floor_img").attr("src",FLOOR_L2);
		IMAGE_WIDTH = IMAGE_WIDTH_L2;
		IMAGE_HEIGHT = IMAGE_HEIGHT_L2;
	}else if(floor == "L3"){
		$("#floor_img").attr("src",FLOOR_L3);
		IMAGE_WIDTH = IMAGE_WIDTH_L3;
		IMAGE_HEIGHT = IMAGE_HEIGHT_L3;
	}else if(floor == "L4"){
		$("#floor_img").attr("src",FLOOR_L4);
		IMAGE_WIDTH = IMAGE_WIDTH_L4;
		IMAGE_HEIGHT = IMAGE_HEIGHT_L4;
	}else if(floor == "B1"){
		$("#floor_img").attr("src",FLOOR_B1);
		IMAGE_WIDTH = IMAGE_WIDTH_B1;
		IMAGE_HEIGHT = IMAGE_HEIGHT_B1;
	}else if(floor == "B2"){
		$("#floor_img").attr("src",FLOOR_B2);
		IMAGE_WIDTH = IMAGE_WIDTH_B2;
		IMAGE_HEIGHT = IMAGE_HEIGHT_B2;
	}else if(floor == "B3"){
		$("#floor_img").attr("src",FLOOR_B3);
		IMAGE_WIDTH = IMAGE_WIDTH_B3;
		IMAGE_HEIGHT = IMAGE_HEIGHT_B3;
	} 
}