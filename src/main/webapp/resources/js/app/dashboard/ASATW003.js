var app = app || {};

app.ractive = app.ractive || {};
app.ractive.Stats = null;

app.data = app.data || {};
app.data.memberCountList = {};
	

	function MemberSection() {
	
	var $dashboard_member = $('#dashboard-down');
	var bcn_cd = $dashboard_member.data('bcn_cd');

	function getJoinCount(date_type, start_date, end_date, chart_type) {
		if(chart_type == 'line') {
			// 선 그래프
			
			Common.REST.get('/admin/rest/' + bcn_cd + '/dashboard/down', {
				date_type : date_type, //YYYY.MM.DD 일, YYYY.MM.W 주, YYYY.MM 월
				start_date : start_date,
				end_date : end_date
			},function(result) {
				MemberStats(result, date_type, start_date, end_date, chart_type);
			}, function(data) {
				alert('앱다운로드수 불러오기 실패');
			});
		} else if(chart_type == 'pie') {
			// 원 그래프
			// 전체, ios, android
			
			console.log('원 그래프 그리기');
			MemberStats({acc_count : 2500, ios : 1000, android : 1500}, date_type, start_date, end_date, chart_type);
		}
		
		
	}
	
	function MemberStats (result, date_type, start_date, end_date, chart_type) {
		app.data.memberCountList = result;
		
		var acc_count = 0
		
		if(chart_type == 'line') {
			if(app.data.memberCountList.length > 0) {
				acc_count = app.data.memberCountList[app.data.memberCountList.length-1].acc_count;
			}
		} else if(chart_type == 'pie'){
			acc_count = app.data.memberCountList.acc_count;
		}
		
		if(!app.ractive.Stats) {
			//alert("aaaa");
			app.ractive.Stats = new Ractive({
				el : '#dashboard-down',
				template : '#tmpl-dashboard-down',
				data : {
					type : date_type, // date, week, month end_date
					strt_date : start_date,
					end_date : end_date,
					acc_count : acc_count,
					chart_type : chart_type
				}
			});
			
			app.ractive.Stats.on({
				'search' : function(e) {
					//alert("bbbb");
					getJoinCount(app.ractive.Stats.get('type'), app.ractive.Stats.get('strt_date'), app.ractive.Stats.get('end_date'), app.ractive.Stats.get('chart_type'));
					todayC();
				},
				'download' : function(e) {
					
					location.href = '/' + bcn_cd +  '/dashboard/join/download?date_type=YYYY.MM.DD&start_date=' + app.ractive.Stats.get('strt_date') + '&end_date=' + app.ractive.Stats.get('end_date');
				},'excel' : function(e) {
					 
					var type =app.ractive.Stats.get('type');
					var strt_date = app.ractive.Stats.get('strt_date'); 
					var end_date  = app.ractive.Stats.get('end_date');
					var restUri   = window.location.origin + '/01/dashboard/appdown/excel';
					restUri += '?'; 					
					restUri += 'date_type=' + type;
					restUri += '&start_date=' + strt_date;
					restUri += '&end_date=' + end_date; 

		
					
					location.href = restUri;
					//location.href = '/' + bcn_cd +  '/dashboard/join/download?date_type=YYYY.MM.DD&start_date=' + app.ractive.Stats.get('strt_date') + '&end_date=' + app.ractive.Stats.get('end_date');
				}
			});
			
			app.ractive.Stats.observe('chart_type', function(newValue, oldValue, keypath){
				getJoinCount(app.ractive.Stats.get('type'), app.ractive.Stats.get('strt_date'), app.ractive.Stats.get('end_date'), newValue);
				todayC();
			});
			
			$("[name='datepicker']" ).datepicker({
				dateFormat: "yy.mm.dd",
				onSelect: function() {
					app.ractive.Stats.updateModel();
				}
			});
		} else {
			app.ractive.Stats.set({
				type : date_type, // date, week, month
				strt_date : start_date,
				end_date : end_date,
				acc_count : acc_count
			});
		}
		
		todayC();
		
		if(chart_type == 'line') {
			//alert("bbbb");
			lineChart(date_type, app.data.memberCountList);
		} else if(chart_type == 'pie'){
			pieChart(app.data.memberCountList.ios, app.data.memberCountList.android);
		}
		
	}
	
	
	function lineChart(date_type, jsonList) {
		
		var type;
		var xAxisType;
		var dataList = [];
		//alert("aaaaa");
		switch(date_type) {
			
			case 'YYYY.MM' :
				type = '월';
				xAxisType = 'string';
				for (var i in jsonList){
					if(i==0){
						//alert(jsonList[i].tot_count);
						$("#tot_down_cnt").text(jsonList[i].tot_count);
					}
					//날짜, total, iOS, android 이부분 값 넣어주세요
					
					dataList.push([jsonList[i].join_date.slice(0,4) + '년 ' + jsonList[i].join_date.slice(5,7) + '월', jsonList[i].acc_count, jsonList[i].ios_count, jsonList[i].and_count]);
				}
				break;
			case 'YYYY.MM.W' :
				type = '주';
				xAxisType = 'string';
				for (var i in jsonList){
					if(i==0){
						//alert(jsonList[i].tot_count);
						$("#tot_down_cnt").text(jsonList[i].tot_count);
					}
					//날짜, total, iOS, android 이부분 값 넣어주세요
					dataList.push([jsonList[i].join_date.slice(0,4) + '년 ' + jsonList[i].join_date.slice(5,7) + '월 ' + jsonList[i].join_date.slice(8) +'주', jsonList[i].acc_count, jsonList[i].ios_count, jsonList[i].and_count]);
				}
				break;
			default :
				type = '일';
				xAxisType = 'string';
				for (var i in jsonList) {
					//날짜, total, iOS, android 이부분 값 넣어주세요
					if(i==0){
						//alert(jsonList[i].tot_count);
						$("#tot_down_cnt").text(jsonList[i].tot_count);
					}
					dataList.push([jsonList[i].join_date.slice(0,4) + '-' + jsonList[i].join_date.slice(5,7) + '-' + jsonList[i].join_date.slice(8,10), jsonList[i].acc_count, jsonList[i].ios_count, jsonList[i].and_count]);
				}
				break;
		}
		
			
		google.charts.load('current', {packages: ['corechart', 'line']});
		google.charts.setOnLoadCallback(function(){
			//alert("bbbb");
			var data = new google.visualization.DataTable();
			
			data.addColumn(xAxisType, '날짜');
			data.addColumn('number', 'Total');
			data.addColumn('number', 'iOS');
			data.addColumn('number', 'android');
			data.addRows(dataList);

			var chart = new google.visualization.LineChart(document.getElementById('chart-down'));
			chart.draw(data, {
				titleTextStyle: {
					color: '#b49759'
				},
				chartArea: {
					left: 130,
					width: '80%'
				},
				hAxis: {
					title: type,
					gridlines: {
						count: 5
					}
				},
				vAxis: {
					title: '다운로드수'
				},
				height: 390,
				legend: {
					position: 'none'
				},
				pointSize: 8
			});
		});
	}
	
	function pieChart(iOS_count, android_count) {

		google.charts.load('current', {'packages':['corechart']});
		google.charts.setOnLoadCallback(function(){
			var data = google.visualization.arrayToDataTable([
              ['앱 다운로드 현황', 'count'],
              ['iOS',     iOS_count],
              ['android',      android_count]
            ]);

			var chart = new google.visualization.PieChart(document.getElementById('chart-down'));
			chart.draw(data, {
				height: 390,
			});
		});
	}
	
	getJoinCount('YYYY.MM.DD', monthAgo2(today()), new Date().format('YYYY.MM.dd'), 'line'); 
}

function analytics(){
	window.open('https://analytics.google.com/analytics/web/?authuser=2#realtime/rt-overview/a76556333w122446219p128116144/');
}

function todayC() {
	var today = new Date()
	var todayString = today.getFullYear() + '년 '
		+ (today.getMonth() + 1) + '월 '
		+ today.getDate() + '일 '
		+ today.getHours() + '시 '
		+ today.getMinutes() + '분';
	$('#current-time').text(todayString);
};

$(function() {
	//alert("bbbb");
	new MemberSection();
});