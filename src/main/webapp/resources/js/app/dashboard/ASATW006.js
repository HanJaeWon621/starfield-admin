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

var FLOOR_L1 = "/images/L1.png";
var FLOOR_L2 = "/images/L2.png";
var FLOOR_L3 = "/images/L3.png";
var FLOOR_L4 = "/images/L4.png";
var FLOOR_B1 = "/images/B1.png";
var FLOOR_B2 = "/images/B2.png";
var FLOOR_B3 = "/images/B3.png";

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
		changeFloor();
	}, 
	'search' : function() {
		getHeatmapList();
	}, 
	'zoom' : function() {
		zoom(); 
	} 
	
});

 
var zoom = function(){ 
	location.href = '/01/dashboard/big/visitor?sh_dt='+heatmapData.filter.sh_dt.split('-').join('')+'&sh_floor='+heatmapData.filter.sh_floor;
}

var changeFloor = function(){
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

 
var getHeatmapList = function(){
	changeFloor();
	var restUri = window.location.origin + '/admin/rest/01/getHeatmapList';
	restUri += '?';    
	restUri += 'sh_dt=' + $("#sh_dt").val();
	restUri += '&sh_floor=' + $("#sh_floor").val();
	Common.REST.get(restUri, {}, function(data) {
		heatmapData.heatmapList = data.heatMapList; 
		rtvHeatmap.set(heatmapData); 
 		
		var imgWidth = $(".my-image").width();	
		var imgHeight = $(".my-image").height();	
		
		var result  = imgWidth / IMAGE_WIDTH * 100; //비율
		var result2  = imgHeight / IMAGE_HEIGHT * 100; // 비율
	
		for(var i = 0 ; i < heatmapData.heatmapList.length ; i ++){
			heatmapData.heatmapList[i].x =   Math.floor(heatmapData.heatmapList[i].x * result / 100); 
			heatmapData.heatmapList[i].y =   Math.floor(heatmapData.heatmapList[i].y * result2 / 100);
		}  
 		 
		var heatmap = h337.create({
			container: document.getElementById('heatmapContainer')
	    });     
		window.h = heatmap;      
	    var d = heatmapData.heatmapList;     
	       			  
	    h.setData({ 
	    	min: 1,
	        max: data.max,  
	        data: d
	    });
		
		
	}, function(data) {
		console.log('fail data is ', data);
	}); 
}; 
 

$(function() {
	getHeatmapList();
	
});