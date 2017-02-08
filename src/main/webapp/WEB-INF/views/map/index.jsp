
<%@ include file="../common/map-header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <title>IndoorMap sample</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <script type="text/javascript" src="/js/lib/jquery-3.1.0.min.js"></script>
        <script type="text/javascript" src="/js/lib/IndoorMap.min.js"></script>
        <script type="text/javascript" src="/js/lib/jquery.mobile-1.4.5.js"></script>
     	
     	<!--  
     	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.0a2/jquery.mobile-1.0a2.min.css" />
		<script src="http://code.jquery.com/jquery-1.4.4.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.0a2/jquery.mobile-1.0a2.min.js"></script>
		 -->
        <script> 

            var myIndoorMap = null;
			var map_path="";
			var arrUriValue ="";
            $(document).ready(function() {
            	arrUriValue = window.location.pathname.split("/");
            	//alert(window.location.pathname);
        		//'/js/app/map/'+arrUriValue[1]+'/mapData.json'
        		map_path = '/js/app/map/'+arrUriValue[1]+'/mapData.json';
        		map_path = '/json/'+arrUriValue[1]+'/mapData.json';
        		//alert("map_path>>"+map_path);
                myIndoorMap = IndoorMap($('#IndoorMapContainer'),{
                    backgroundColor: '0xbdb6b2',
                    onFloorChanged: function(_floor) {
                        setFloorTenantMarkers(_floor);
                    },
//                    onBlockMouseOver:function(blockData){
//                        console.log('onBlockMouseOver:',blockData);
//                        if(blockData) {
//                            $('#IndoorMapContainer').css('cursor','pointer');
//                        }
//                    },
//                    onBlockMouseOut:function(blockData){
//                        console.log('onBlockMoueOut:', blockData);
//                        if(blockData) {
//                            $('#IndoorMapContainer').css('cursor','default');
//                        }
//                    },
                    onBlockClick:function(blockData){
//                        console.log('onBlockClick:',blockData);
						var a=blockData.split('|');
						if(arrUriValue[4]=='edit'){
							
					    	//if(blockData !=undefined) alert(""+a[1]);
					    	//window open 함수 사용하자.
					    	//block정보 입력시 data는 직접 입력하지 말고 영업시간 필드롤 추가로 입력한다.
					    	var url= window.location.origin + "/map/floorblocksingle/"+a[1];
					    	//window.showModalDialog(url,'dialogWidth:600px; dialogHeight:400px;');
					    	window.open(url, "_blank", "width=800,height=500");//모달로 띄우고 새창 크기
						}else{
							//$.mobile.pageLoading();
							
							// hide
							//$.mobile.pageLoading(true);
							//기타정보 뿌려줄것 
							if(a[2] !=undefined) alert(""+a[2]);
						}
                    }
                });
                myIndoorMap.load(map_path)
//                    .delay(300)
//                    .setView(200,[2000,1000],500)
//                    .done(function(next){
//                        console.log("done loading mapData & changing view");
//                        next();
//                    })
//                    .rotate(-30,null,300)
                    .setTiltAngle(70,2000)
                    .done(function(next){
                        //this.setPolyline([[200,3000],[600,3000]]);

                        //setFloorTenantMarkers(1);

/*                        this.setSpray([1700,2800,100],{
                            image:'images/smoking-area.png',
                            size:[100,100]
                        });*/
                        next();
                    })
                    //.delay(200)
                    .done(function(next){
                        var myBlock = this.getBlock('baskinRobbins');
                        myBlock.on('mouseover',function(blockData){
                            console.log('mouseover');
                            $('#IndoorMapContainer').css('cursor','pointer');
                            this.setColor('0xFFFFFF',100);
                            this.setHeight(300,100);

                            this.setOpacity(0.5, 300);
                        });
                        myBlock.on('mouseout',function(blockData){
                            console.log('mouseout');
                            $('#IndoorMapContainer').css('cursor','default');
                            this.restoreColor(100);
                            this.restoreHeight(100);

                            this.restoreOpacity(300);
                        });
                        myBlock.on('click',function(blockData){
                            console.log('onBlockClick:',blockData);
                            if(blockData !=undefined) alert(">"+blockData);
                        });
//                        this.removePolylinesOn();
//                        this.removeSpraysOn();
                        next();

/*                        var myIcon = myIndoorMap.icon({
                            image:"images/svgPin.svg",
                            size:[30,30],
                            anchor:[15,30]
                        });
                        this.setMarker([3500,2000,200],{
                            type:"icon",
                            icon:myIcon
                        });*/

                        $('.setAngleButton')
                            .animate({
                                opacity: 1
                            }, 200)
                            .click(function() {
                                var angle = $(this).attr('data-val');
                                myIndoorMap.setTiltAngle(angle, 200);
                                return false;
                            });
                    });
            });


          var activeMarker = null;
		  
          function setFloorTenantMarkers(_floor) {
               if(typeof _floor === 'undefined') _floor = 1;
               //var tenant = tenants[_floor-1];//infoData.js --> json형태로 가져온다.
               //var len = tenant.length;
			   //Tenents
			   
               var restUri = window.location.origin + '/rest/'+arrUriValue[1] +'/getTenents/'+_floor;

	           	Common.REST.get(restUri, {}, function(data) {
	           		for(var i = 0; i < data.tot_cnt; i++) {//poiData.js(배열)--> json형태로 가져올것
	           			myIndoorMap.setMarker([data.list[i].x_cord,data.list[i].y_cord,100], {
	                         type: 'html',
	                         html: '<div style="font-size: .8em;">'+ data.list[i].tnt_nm +'</div>',
	                         mouseover: function() {
	                              this.$htmlElem.css('cursor', 'pointer');
	                         },
	                         mouseout: function() {
	                              this.$htmlElem.css('cursor', 'default');
	                         },
	                         click: function() {
	                              var me = this;

	                              if(activeMarker != null && activeMarker == me) return;

	                              me.$htmlElem.css('font-weight', 'bold');
	                              me.setClustering(false);

	                              if(activeMarker) {
	                                   activeMarker.setClustering(true);
	                                   activeMarker.$htmlElem.css('font-weight', 'normal');
	                              }

	                              activeMarker = me;
	                         }
	                    });
	                  }
	           		
	           	}, function(data) {
	           		console.log('fail data is ', data);
	           	});
	           	
	           //POI	
               var myIcon = null;
              
               var restUri = window.location.origin + '/rest/'+arrUriValue[1]+'/getPois/'+_floor;

	           	Common.REST.get(restUri, {}, function(data) {
	           		for(var i = 0; i < data.tot_cnt; i++) {//poiData.js(배열)--> json형태로 가져올것
	           			myIcon = myIndoorMap.icon({
	                           image: "/images/indoormap/"+ data.list[i].icon,
	                           size: [22,22],
	                           anchor: [11,11]
	                      });
	
	                      myIndoorMap.setMarker([data.list[i].x_ctn_cord,data.list[i].y_ctn_cord,100], {
	                           type: 'icon',
	                           icon: myIcon
	                      });
	                  }
	           		
	           	}, function(data) {
	           		console.log('fail data is ', data);
	           	}); 
          }
        </script>
        <style>
            #IndoorMapContainer {
                position: absolute;
                width:100%;
                height:100%;
                z-index: 1;
            }

            body {
                padding:0; margin:0;
            }

            button {
                background-color: #efefef;
                border-radius: 4px;
                padding: 7px 15px;
                cursor: pointer;
                opacity: 0;
            }
        </style>
    </head>
    <body>
        <div id="IndoorMapContainer"></div>
		
        
    </body>
</html>
