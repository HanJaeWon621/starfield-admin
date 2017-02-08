var app = app || {};

function DashboardSection() {
	
	var $dashboard_summary = $('#dashboard-summary');
	var bcn_cd = $dashboard_summary.data('bcn_cd');

	function getDashboardStats() {
		Common.REST.get('/admin/rest/' + bcn_cd + '/dashboard/stats', {},function(result) {
			Stats(result);
		}, function(data) {
			alert('대쉬보드 통계 불러오기 실패');
		});
	}
	
	function Stats(param) {
		$('#join-today').text(param.todayJoinCount);
		$('#join-month').text(param.monthJoinCount);
		$('#join-accum').text(param.totalJoinCount);
		$('#app-down-accum').text(param.appDownCount);
	}
	
	
	var today = new Date()
	var todayString = today.getFullYear() + '년 '
		+ (today.getMonth() + 1) + '월 '
		+ today.getDate() + '일 '
		+ today.getHours() + '시 '
		+ today.getMinutes() + '분';
	$('#current-time').text(todayString);
	
	getDashboardStats();
}

function analytics(){
	window.open('https://analytics.google.com/analytics/web/?authuser=2#realtime/rt-overview/a76556333w122446219p128116144/');
}

$(function() {
	new DashboardSection();
});