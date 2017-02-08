var app = app || {};

app.ractive = app.ractive || {};
app.ractive.Holiday = null;
app.ractive.HolidaySelect = null;
app.ractive.HolidayDelete = null;

app.data = app.data || {};
app.data.Holiday = {};
app.data.tempHolidayList = [];
app.data.SelectDateList = [];
app.data.DeleteDateList = [];

function HolidaySection() {
	var $holiday = $('#holiday');
	var bcn_cd = $holiday.data('bcn_cd');
	var year = new Date().getFullYear() + '';
	function getHoliday() {
		
		Common.REST.get('/admin/rest/' + bcn_cd + '/holidays', {}, function(result) {
			console.log()
			Holiday(result);
		}, function(data) {
			alert('비정기운영일 불러오기 실패');
		});
	}
	
	function Holiday(result){
		app.data.Holiday = {};
		for(var i = 0, iMax = result.list.length; i < iMax; i++) {
			app.data.Holiday[result.list[i].year] = app.data.Holiday[result.list[i].year] || {};
			app.data.Holiday[result.list[i].year][result.list[i].mont] = app.data.Holiday[result.list[i].year][result.list[i].mont] || [];
			app.data.Holiday[result.list[i].year][result.list[i].mont].push(result.list[i].day);
		}
		
		var totalCount = 0;
		for(var key in app.data.Holiday[year] ) {
			totalCount += app.data.Holiday[year][key].length;
		}
		
		if(!app.ractive.Holiday) {
			app.ractive.Holiday = new Ractive({
				el : '#holiday',
				template : '#tmpl-holiday',
				data : {
					holiday : app.data.Holiday,
					year : year,
					totalCount : totalCount
				}
			});
			app.ractive.Holiday.on({
				'addHoliday' : function(e) {
					HolidaySelectSection(app.ractive.Holiday.get('year'));
				},
				'deleteHoliday' : function(e) {
					HolidayDeleteSection(app.ractive.Holiday.get('year'));
				}
			});
			app.ractive.Holiday.observe( 'year', function ( newValue, oldValue, keypath ) {
				var _totalCount = 0;
				for(var key in app.data.Holiday[newValue] ) {
					_totalCount += app.data.Holiday[newValue][key].length;
				}
				app.ractive.Holiday.set({
					totalCount : _totalCount
				});
			});
		} else {
			app.ractive.Holiday.reset();
			app.ractive.Holiday.set({
				holiday : app.data.Holiday,
				year : year,
				totalCount : totalCount
			});
		}
	}
	
	getHoliday();
}

function HolidaySelectSection(year) {
	app.data.SelectDateList = [];
	
	var $holiday = $('#holiday');
	var bcn_cd = $holiday.data('bcn_cd');
	var calendar;
	
	function addHoliday() {
		
		app.ractive.HolidaySelect = new Ractive({
			el : '#holiday-reg-popup',
			template : '#tmpl-holiday-reg-popup',
			data : {
				dateList : app.data.SelectDateList
			}
		});
		
		app.ractive.HolidaySelect.on({
			'add' : function(e) {
				var addDate = $('#holiday-calendar').datepicker('getDate').format('yyyy.MM.dd');
				for(var i = 0, iMax = app.data.SelectDateList.length; i < iMax; i++) {
					if(app.data.SelectDateList[i] == addDate) {
						return;
					}
				}
				
				app.data.SelectDateList.push(addDate);
				
			},
			'remove' : function(e) {
				var deleteDateList = $('input:checkbox[name="deleteCheckbox"]:checked').map(function(){
					return $(this).val();
				}).get();
				
				for(var i = 0, iMax = deleteDateList.length; i < iMax; i++) {
					for(var j = 0, jMax = app.data.SelectDateList.length; j < jMax; j++) {
						if(deleteDateList[i] == app.data.SelectDateList[j]) {
							app.data.SelectDateList.splice(j, 1);
						}
					}
				}
			},
			'cancel' : function(e) {
				if(confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
					$('#holiday-reg-popup').css('display', 'none');
				}
			},
			'save' : function(e) {
				var holidayList = [];
				if(app.data.SelectDateList.length < 1) {
					return alert('추가된 비정기운영일이 없습니다. 비정기운영일을 추가해주세요.');
				} else if(confirm('입력한 내용으로 저장을 하시겠습니까?')){
					for(var i = 0, iMax = app.data.SelectDateList.length; i < iMax; i++) {
						var selectDateSplit = app.data.SelectDateList[i].split('.');
						holidayList[i] = {
							year : selectDateSplit[0],
							mont : selectDateSplit[1],
							day : selectDateSplit[2]
						}
					}
					Common.REST.post('/admin/rest/' + bcn_cd + '/holidays', holidayList, function(result) {
						if(result.code == 0) {
							$('#holiday-reg-popup').css('display', 'none');
							
							HolidaySection();
						} else {
							alert('저장실패');
						}
					}, function(data) {
						alert('저장실패');
					});
				}
			}
		});
		
		var defaultDate = new Date();
		defaultDate.setFullYear(year);
		
		calendar = $('#holiday-calendar').datepicker({
			autoSize : true,
			yearRange : year + ':' + year,
			defaultDate: defaultDate,
			onChangeMonthYear : function(year, month, inst) {
				console.log(year, month, inst);
			}
		});
		
		$('#holiday-reg-popup').css('display', 'block');
	}
	
	addHoliday();
	
}

function HolidayDeleteSection(year) {
app.data.DeleteDateList = [];
	
	var $holiday = $('#holiday');
	var bcn_cd = $holiday.data('bcn_cd');

	app.data.tempHolidayList = [];
	
	var length = 0;
	
	var holiday = JSON.parse(JSON.stringify(app.data.Holiday));
	
	
	for(var month in holiday[year]) {
		app.data.tempHolidayList.push({
			month : month,
			day : holiday[year][month]
		});
		length += holiday[year][month].length;
	}
	
	app.data.tempHolidayList.sort(function(a,b){return a.month - b.month;});
	
	
	function deleteHoliday() {
		app.ractive.HolidayDelete = new Ractive({
			el : '#holiday-del-popup',
			template : '#tmpl-holiday-del-popup',
			data : {
				year : year,
				dateList : app.data.tempHolidayList,
				length : length
			}
		});
		
		app.ractive.HolidayDelete.on({
			'delete' : function(e) {
				var date_list = $('input:checkbox[name="groupCheckBox"]:checked').map(function(){
					return $(this).val();
				}).get();
				
				if(date_list.length < 1) {
					return alert('삭제할 비정기운영일을 1개 이상 선택해주세요.');
				}
				
				if(confirm('선택한 비정기운영일을 삭제하시겠습니까?')) {
					for(var i = 0, iMax = date_list.length; i < iMax; i++) {
						var dateSplit = date_list[i].split('.');
						app.data.DeleteDateList.push({
							year : dateSplit[0],
							mont : dateSplit[1],
							day : dateSplit[2],
							bcn_cd : bcn_cd
						});
						
						for(var j = 0; j < app.data.tempHolidayList.length; j ++) {
							if(app.data.tempHolidayList[j] && app.data.tempHolidayList[j].month == dateSplit[1]) {
								for(var k = 0; k < app.data.tempHolidayList[j].day.length; k++) {
									if(app.data.tempHolidayList[j].day[k] && app.data.tempHolidayList[j].day[k] == dateSplit[2]) {
										app.data.tempHolidayList[j].day.splice(k ,1);
										break;
									}
								}
								if(app.data.tempHolidayList[j].day.length < 1) {
									app.data.tempHolidayList.splice(j, 1);
								}
								break;
							}
						}
						
						app.ractive.HolidayDelete.update();
					}
				}
			},
			'cancel' : function(e) {
				if(confirm('입력/수정 정보를 저장하지 않고 종료합니다. 종료하시겠습니까?')) {
					$('#holiday-del-popup').css('display', 'none');
				}
			},
			'save' : function(e) {
				if(app.data.DeleteDateList.length < 1) {
					return alert('삭제된 비정기운영일이 없습니다.');
				} else if(confirm('해당 내용으로 저장을 하시겠습니까?')){
					
					Common.REST.del('/admin/rest/' + bcn_cd + '/holidays', app.data.DeleteDateList, function(result) {
						if(result.code == 0) {
							$('#holiday-del-popup').css('display', 'none');
							
							HolidaySection()
						} else {
							alert('저장실패');
						}
					}, function(data) {
						alert('저장실패');
					});
				}
			}
		});
		
		
		$('#holiday-del-popup').css('display', 'block');
	}
	
	deleteHoliday();
}

$(function() {
	new HolidaySection();

});