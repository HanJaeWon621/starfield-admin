
"use strict"
$.support.cors = true;

//IE9 fix
if(!window.console) {
	window.console = {log: function() {}};
}

var app = app || {};
//이미지 업로드
app.imageUpload = function(el, bcn_cd, callback) {
	var $el = $(el);
	var beforStart = function(e, data, cb) {
   		
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
	
	var fail = function(r) {
		if(typeof callback === 'function') {
			callback(r, null, el);
        }
	};
	
	var done = function(r) {
		if(typeof callback === 'function') {
			callback(null, r, el);
        }
	};
	
	var params = {
			url : '/rest/' + bcn_cd + '/file/images', //upload url
		 	beforeStart : beforStart, // called before start uploading
		 	fail : fail,
		 	done : done, // called with result json object when uploading is done 
		 	progress : function(){} // called with current progress (Experimental)
	};
 	
	$el.fileInputButton(params);
}

function _ajax(url, method, jsonStr, cbSuccess, cbFailed, dataType) {
	$.ajax({
		beforeSend : function(xhr){
			xhr.setRequestHeader("AJAX", "true");
			xhr.setRequestHeader("ReturnURI", window.location.pathname);
		},
	    url : url,
	    type : method,
	    headers : {
	      'Accept' : 'application/json',
	      'Content-Type' : 'application/json' 
	    },
	    crossDomain : true,
	    xhrFields: {
	      withCredentials: true
	    },
	    dataType : dataType || 'json',
	    data : jsonStr,
	    success : function(data) {
	    	
	    	if(typeof cbSuccess == 'function') {
	    		cbSuccess(data);
	    	}
	     
	    },
	    error : function(jqXHR, textStatus) {
	    	
	    	switch(jqXHR.status) {
	    		case 12001 :
	    			alert('권한이 없습니다.\n담당 관리자에게 문의하세요.');
	    			return false;
	    		case 12008 :
	    			window.location.href ="https://www.starfield.co.kr/";
	    			return false;
	    	}
	    	  	
	    	if(typeof cbFailed == 'function') {
	    		cbFailed(jqXHR.responseText);
	    	}
	    }
	}); 
}


var REST = {
	get : function(url, jsonData, cbSuccess, cbFailed) {
		_ajax(url, 'GET', jsonData, cbSuccess, cbFailed);
	},
	post : function(url, jsonData, cbSuccess, cbFailed) {
		_ajax(url, 'POST', JSON.stringify(jsonData), cbSuccess, cbFailed);
	},
	put : function(url, jsonData, cbSuccess, cbFailed) {
		_ajax(url, 'PUT', JSON.stringify(jsonData), cbSuccess, cbFailed);
	},
	del : function(url, jsonData, cbSuccess, cbFailed) {
		_ajax(url, 'DELETE', JSON.stringify(jsonData), cbSuccess, cbFailed);
	}
};

var Global = {
	ScreenLock : {
		lock : function() {
			$('#screen-lock').css('display', '');
			$('#screen-lock').removeClass('unlock');
			$('#screen-lock').addClass('lock');
		},
		unlock : function() {
			$('#header').css('display', '');
			$('#contents').css('display', '');
			$('#screen-lock').removeClass('lock');
			$('#screen-lock').addClass('unlock');
			setTimeout(function() {
				$('#screen-lock').css('display', 'none');
			}, 1000);
		}
	},
	Navigation : {
		goTo : function(hashLoc) {
			window.location.hash = hashLoc;
		}
	},
	
	isEmpty : function(s) {
		if(s == null || s == undefined || s == '')
			return true;
		else
			return false;
	},
	
	isNumber : function(n){
		var num=/^[0-9]*$/;
		if(num.test(n))
			return false;
		else
			return true;
	},
	
	params : function(){
		var obj = {};
		var arr = [];
		
		return {
			clear : function(){
				obj = {};
				arr = [];
			},
			
			pushArr : function(pobj){
				arr.push(pobj);
			},
			
			getArr : function(){
				return arr;
			},
			
			setObj : function(pobj){
				obj = pobj;
			},
			
			getObj : function(){
				return obj;
			}
		};
	},
	
	isLoginMember : function() {
		
		var checkParams = ['email', 'mem_identifi_id', 'mem_name', 'mem_nickname', 'mem_phone_number'];
		
		var isLoginMember = true;
		
		$.each(checkParams, function(){
			
			if(!window.profile[this]) {
				
				isLoginMember = false;
				
				return false;
			} 
			
		});
		
		return isLoginMember;
	},
	
	// paging 설정
	initPaging : function(offset, limit, page_limit, totCnt, cb) {

		var paging = {
			totCnt : null,
			list_limit : null,
			page_limit : null,
			cur_page : null,
			total_page_cnt: null,
			page_start: null,
			page_end: null,
			pages: []
		}
	
		paging.totCnt = totCnt;
		paging.list_limit = limit;
		paging.page_limit = page_limit;
		paging.cur_page = (offset / paging.list_limit) + 1;
		paging.total_page_cnt = (totCnt / paging.list_limit) + ((totCnt % paging.list_limit) >= 1 ? 1 : 0);
		paging.total_page_cnt = Math.floor(paging.total_page_cnt);
	
		paging.page_start = Math.floor((paging.cur_page - 1) / paging.page_limit) * paging.page_limit + 1;
	
		paging.page_end = paging.page_start + paging.page_limit - 1;
		paging.page_end = paging.page_end > paging.total_page_cnt ? paging.total_page_cnt : paging.page_end;
	
		for(var i = paging.page_start; i <= paging.page_end; ++i) {
			paging.pages.push(i);
		}
	
		if(typeof cb == 'function') {
	
			cb(paging);
		}
	}
	
};

var log = function() {
	console.log.apply(console, arguments);
}

var Const = {
	Notification : {
		Type : {
			NEW_GURU_COMES : 1
		}
	}	
};

var Common = {
	log : log,
	Const : Const,
	REST : REST,
	Global : Global,
};

// rest uri split (pathname : /emps/7936, arrUriValue = [ "", "emps", "7936" ])
var getUriArrVal = function() {
	var arrUriValue = window.location.pathname.split("/"); 

	return arrUriValue;
}



var datepicker = function() {
	$( "input[name='datepicker']" ).datepicker({
		  //showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
		  //buttonImage: "/application/db/jquery/images/calendar.gif", // 버튼 이미지
		  //buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다.
		  //buttonText: "달력", // 버튼 툴팁 
		  changeMonth: true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
		  changeYear: true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
		  //minDate: '-100y', // 현재날짜로부터 100년이전까지 년을 표시한다. 
		  nextText: '다음 달', // next 아이콘의 툴팁.
		  prevText: '이전 달', // prev 아이콘의 툴팁.
		  numberOfMonths: [1,1], // 한번에 얼마나 많은 월을 표시할것인가. 
		  //stepMonths: 3, // next, prev 버튼을 클릭했을때 얼마나 많은 월을 이동하여 표시하는가. 
		  //yearRange: 'c-100:c+10', // 년도  셀렉트박스에서 현재년도에서 이전, 이후로 얼마의 범위를 표시
		  //showButtonPanel: true, // 캘린더 하단에 버튼 패널을 표시한다. 
		  //currentText: '오늘 날짜' , // 오늘 날짜로 이동하는 버튼 패널
		  closeText: '닫기',  // 닫기 버튼 패널 
		  dateFormat: "yy-mm-dd", // 텍스트 필드에 입력되는 날짜 형식.
		  //showAnim: "slide", //애니메이션을 적용한다.
		  showMonthAfterYear: true , // 월, 년순의 셀렉트 박스를 년,월 순으로 바꿔준다. 
		  dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'], // 요일
		  monthNames : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		  monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
	});
} 


$(function() {
	$(document).on("keyup", "input:text[numberOnly]", function() {
		$(this).val( $(this).val().replace(/[^0-9]/gi,"") );
	});
}); 
 
var today = function() {

    var now = new Date();
    var year= now.getFullYear();
    var mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
    var day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();
            
    var resultDate = year + '-' + mon + '-' + day;
    return resultDate;
}





function monthAgo(date){
	//return;
    var selectDate = date.split("-");
    var changeDate = new Date();
    changeDate.setFullYear(selectDate[0], selectDate[1]-1);
    
    var year = changeDate.getFullYear();
    
    var cd = changeDate.getMonth();
    var cd2 = changeDate.getMonth();
    //alert('cd>>'+cd);
    if(cd==0){
    	//alert('cd>>'+cd);
    	cd2=12;
    	year=year-1;
    }else{
    	if(cd>9){
    		cd2=cd;
    	}else{
    		cd2='0'+cd;
    	}
    }
    //alert(year);
    //var mon = changeDate.getMonth()>9 ? ''+changeDate.getMonth() : '0'+changeDate.getMonth();
    var mon = cd2;
    var day = changeDate.getDate()>9 ? ''+changeDate.getDate() : '0'+changeDate.getDate();
    
    var resultDate = year + '-' + mon + '-' + day;
    
    return resultDate;
}

function monthAgo2(date){
	//alert(date);
    var selectDate = date.split("-");
    var changeDate = new Date();
    changeDate.setFullYear(selectDate[0], selectDate[1]-1);
    
    var year = changeDate.getFullYear();
    var mon = changeDate.getMonth()>9 ? ''+changeDate.getMonth() : '0'+changeDate.getMonth();
    var day = changeDate.getDate()>9 ? ''+changeDate.getDate() : '0'+changeDate.getDate();
    
    var resultDate = year + '.' + mon + '.' + day;
    
    return resultDate;
}

var messageRouter = (function() {

    var targets = {};

    var on = function(name, el, handler) {

        targets[name] = targets[name] || [];
        targets[name].push({
            element : el,
            handler : handler
        });
    };

    var off = function(name) {

        var arr = targets[name];
        if(arr) {
            arr.splice(0, arr.length);
            delete targets[name];
        }
    };

    var trigger = function(name, elem, params) {

        if(targets[name] && targets[name].length > 0) {

            if(elem) {

                for(var i = 0; i < targets[name].length; ++i) {

                    var target = targets[name][i];

                    if(target.element == elem) {
						target.handler.call(targets[name][i].element, params);
                        break;
                    }
                }

            } else {

                for(var i = 0; i < targets[name].length; ++i) {
                    targets[name][i].handler.call(targets[name][i].element, params);
                }
            }
        }
    }

    return {
        on : on,
        off : off,
        trigger : trigger
    }

})();


var leadingZeros = function (date, num) {
	var zero = '';
	date = date.toString();
	
	if (date.length < num) {
		for (var i = 0 ; i < num - date.length; i++){
		  zero += '0';
		}
	}
	return zero + date;
}


function JSONtoString(object) {
  var results = [];
  for (var property in object) {
      var value = object[property];
      if (value)
          results.push(property.toString() + ': ' + value);
      }
              
      return '{' + results.join(', ') + '}';
}
String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};
Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "YYYY": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "YY": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "DD": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};

/**
 * Byte 제한
 * @param str : 문자열 
 * @param lengths : 제한 byte
 * @returns {String}
 */
function fnCut(str,lengths){
	var KO_BYTE = 3;
	
	var len = 0;
	var newStr = '';
	
	for (var i=0;i<str.length; i++) {
		var n = str.charCodeAt(i); // charCodeAt : String개체에서 지정한 인덱스에 있는 문자의 unicode값을 나타내는 수를 리턴한다.
		// 값의 범위는 0과 65535사이이여 첫 128 unicode값은 ascii문자set과 일치한다.지정한 인덱스에 문자가 없다면 NaN을 리턴한다.
		
		var nv = str.charAt(i); // charAt : string 개체로부터 지정한 위치에 있는 문자를 꺼낸다.
		
		if ((n>= 0)&&(n<256)) len ++; // ASCII 문자코드 set.
		else len += KO_BYTE; // 한글이면 3byte로 계산한다. UTF-8
		
		if (len>lengths) {
			alert("한글 "+(Math.floor(lengths/KO_BYTE))+"자 / 영문(숫자) "+lengths+"자를 초과 입력할 수 없습니다.");
			break; // 제한 문자수를 넘길경우.
		}
		else newStr = newStr + nv;
	}
	return newStr;
}



function getSortFilter(o,data,o1,o2) {
	var order_key = $(o.node).data("order-key");
	var order_type = "asc";

	if(order_key) {	
		$(o.node).siblings().removeClass("selected");
		$(o.node).addClass("selected");
		
		if($(o.node).attr("order-type") == "asc") {
			order_type = "desc";
			$(o.node).attr("order-type", "desc");
		} else {
			order_type = "asc"; 
			$(o.node).attr("order-type", "asc");
		}
	}

	var order_key = $("[data-order-key].selected").data("order-key");
	var order_type = $("[data-order-key].selected").attr("order-type");
	if(order_key) {
		data[o1] = order_key;
		data[o2] = order_type;	
	}
	
	//return q.join(',');
}





/**
 * 쿠키 삭제
 * @param cookieName 삭제할 쿠키명
 */
function deleteCookie( cookieName )
{
 var expireDate = new Date();
 
 //어제 날짜를 쿠키 소멸 날짜로 설정한다.
 expireDate.setDate( expireDate.getDate() - 1 );
 document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
}