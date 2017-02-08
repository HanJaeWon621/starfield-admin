var app = app || {};

app.ractive = app.ractive || {};
app.ractive.Operation = null;

app.data = app.data || {};
app.data.Operation = {};
app.data.HolidayList = {};
app.data.StarFieldHolidayList = {};
app.data.monthList = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];

function OperationSection() {
	var $operation = $('#operation');
	var bcn_cd = $operation.data('bcn_cd');
	var year = new Date().getFullYear() + '';
	
	function getOperation() {
		
		async.parallel([
			function(asyncCallback) {
				Common.REST.get('/admin/rest/' + bcn_cd + '/stores/operations', {}, function(result) {
					asyncCallback(null, result);
				}, function(data) {
					asyncCallback(data);
				});
			},
			function(asyncCallback) {
				Common.REST.get('/admin/rest/' + bcn_cd + '/holidays/ir/bcn', {}, function(result) {
					var starfieldHolidayList = {};
					for(var i = 0, iMax = result.list.length; i < iMax; i++) {
						starfieldHolidayList[result.list[i].year] = starfieldHolidayList[result.list[i].year] || {};
						starfieldHolidayList[result.list[i].year][result.list[i].mont] = starfieldHolidayList[result.list[i].year][result.list[i].mont] || [];
						starfieldHolidayList[result.list[i].year][result.list[i].mont].push({day : result.list[i].day, open_hr_min : result.list[i].open_hr_min, end_hr_min : result.list[i].end_hr_min});
					}
					asyncCallback(null, starfieldHolidayList);
				}, function(data) {
					asyncCallback(data);
				});
			}
		], function(err, result){
			console.log(result);
			app.data.Operation = result[0];
			app.data.StarFieldHolidayList = result[1];
			
			Operation();
		});
	}
	
	
	
	function Operation(){
		
		
		
		if(!app.ractive.Operation) {
			app.ractive.Operation = new Ractive({
				el : '#operation',
				template : '#tmpl-operation',
				data : {
					year : year,
					operation : app.data.Operation,
					starFieldHolidayList : app.data.StarFieldHolidayList,
					monthList : app.data.monthList
				}
			});
			app.ractive.Operation.on({
				'save' : function() {
					console.log(app.data.Operation);
					console.log(app.data.StarFieldHolidayList);
					var starFieldHolidayList = [];
					for(var year in app.data.StarFieldHolidayList) {
						for(var month in app.data.StarFieldHolidayList[year]) {
							for(var i = 0, iMax = app.data.StarFieldHolidayList[year][month].length; i < iMax; i++) {
								starFieldHolidayList.push({
									bcn_cd : bcn_cd,
									year : year,
									mont : month,
									day : app.data.StarFieldHolidayList[year][month][i].day,
									open_hr_min : app.data.StarFieldHolidayList[year][month][i].open_hr_min,
									end_hr_min : app.data.StarFieldHolidayList[year][month][i].end_hr_min
								});
							}
						}
					}
					
					
					if(confirm('입력한 내용으로 저장을 하시겠습니까?')) {
						Common.REST.put('/admin/rest/' + bcn_cd + '/operations', {
							operation : app.data.Operation,
							starFieldHolidayList : starFieldHolidayList	
						}, function(data) {
							alert('저장 완료');
						}, function(data) {
							alert('저장 실패');
						});
					}
				}
			});
		} else {
			app.ractive.Operation.reset();
			app.ractive.Operation.set({
				year : year,
				operation : app.data.Operation,
				starFieldHolidayList : app.data.StarFieldHolidayList,
				monthList : app.data.monthList
			});
		}
	}
	
	getOperation();
}


$(function() {
	new OperationSection();

});