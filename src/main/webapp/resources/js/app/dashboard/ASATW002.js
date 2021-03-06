var app = app || {};

app.ractive = app.ractive || {};
app.ractive.Stats = null;

app.data = app.data || {};
app.data.memberCountList = {};

function MemberSection() {
	
	var $dashboard_member = $('#dashboard-member');
	var bcn_cd = $dashboard_member.data('bcn_cd');

	function getJoinCount(date_type, start_date, end_date) {
		Common.REST.get('/admin/rest/' + bcn_cd + '/dashboard/join', {
			date_type : date_type, //YYYY.MM.DD 일, YYYY.MM.W 주, YYYY.MM 월
			start_date : start_date,
			end_date : end_date
		},function(result) {
			MemberStats(result, date_type, start_date, end_date);
		}, function(data) {
			alert('가입자 수 불러오기 실패');
		});
	}
	
	function MemberStats (result, date_type, start_date, end_date) {
		app.data.memberCountList = result;
		
		var acc_count = 0
		if(app.data.memberCountList.length > 0) {
			//alert("aaa1111");
			acc_count = app.data.memberCountList[app.data.memberCountList.length-1].acc_count;
		}
		
		
		
		if(!app.ractive.Stats) {
			app.ractive.Stats = new Ractive({
				el : '#dashboard-member',
				template : '#tmpl-dashboard-member',
				data : {
					type : date_type, // date, week, month
					strt_date : start_date,
					end_date : end_date,
					acc_count : acc_count
				}
			});
			
			app.ractive.Stats.on({
				'search' : function(e) {
					getJoinCount(app.ractive.Stats.get('type'), app.ractive.Stats.get('strt_date'), app.ractive.Stats.get('end_date'));
					todayC();
				},
				'download' : function(e) {
					location.href = '/' + bcn_cd +  '/dashboard/join/download?date_type=YYYY.MM.DD&start_date=' + app.ractive.Stats.get('strt_date') + '&end_date=' + app.ractive.Stats.get('end_date');
				}
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
		
		chart(date_type, app.data.memberCountList);
	}
	
	
	function chart(date_type, jsonList) {
		
		var type;
		var xAxisType;
		var dataList = [];
		
		switch(date_type) {
			case 'YYYY.MM' :
				type = '월';
				xAxisType = 'string';
				for (var i in jsonList){
					dataList.push([jsonList[i].join_date.slice(0,4) + '년 ' + jsonList[i].join_date.slice(5,7) + '월', jsonList[i].acc_count]);
				}
				break;
			case 'YYYY.MM.W' :
				type = '주';
				xAxisType = 'string';
				for (var i in jsonList){
					dataList.push([jsonList[i].join_date.slice(0,4) + '년 ' + jsonList[i].join_date.slice(5,7) + '월 ' + jsonList[i].join_date.slice(8) +'주', jsonList[i].acc_count]);
				}
				break;
			default :
				type = '일';
				xAxisType = 'string';
				for (var i in jsonList) {
					dataList.push([jsonList[i].join_date.slice(0,4) + '-' + jsonList[i].join_date.slice(5,7) + '-' + jsonList[i].join_date.slice(8,10), jsonList[i].acc_count]);
				}
				break;
		}
		
			
		
			
		
		google.charts.load('current', {packages: ['corechart', 'line']});
		google.charts.setOnLoadCallback(function(){
			var data = new google.visualization.DataTable();
			data.addColumn(xAxisType, '날짜');
			data.addColumn('number', '누적가입자');
			data.addRows(dataList);

			var chart = new google.visualization.LineChart(document.getElementById('chart-join'));
			chart.draw(data, {
				titleTextStyle: {
					color: '#b49759',
					color: '#bbbbbb'	
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
					title: '누적가입자'
				},
				height: 390,
				legend: {
					position: 'none'
				},
				pointSize: 8
			});
		});
	}
	
	
	
	getJoinCount('YYYY.MM.DD', monthAgo2(today()), new Date().format('YYYY.MM.dd'));
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
	new MemberSection();
});