$(function() {
	initTable();

	setInterval(function() {
		initTable();
	}, 60000);

});

function initTable() {
	Highcharts.setOptions({
		global: {
			useUTC: true
		}
	});

	Highcharts.stockChart('myHighstockTable', {
		chart: {
			//renderTo:'myHighstockTable',
			//设置放大的方向 可以是x或者y     
			zoomType: 'xy',
			//设置绘图区域边框颜色和宽度 
			plotBorderColor: '#008800',
			plotBorderWidth: 0,
			shadow: true,
			//设置动画效果                  
			animation: Highcharts.svg,
			//左边距和右边距                  
			marginRight: 40,
			borderColor: '#eee',
			borderWidth: 1,
			events: {
				load: function() {
					//alert('图标加载完成');
				}
			}
		},
		title: {
			text: '股票走势图'
		},
		tooltip: {
			crosshairs: true,
			split: false,
			shared: true

		},
		rangeSelector: {
			selected: 2
		},
		exporting: {
			enabled: false
		},
		xAxis: {
			tickInterval: 60 * 1000, // 坐标轴刻度间隔为一分钟
			type: 'datatime',
			title: {
				text: '时间'
			},
			labels: {
				Step: 5
			},
			dateTimeLabelFormats: {
				second: '%H:%M:%S',
				minute: '%H:%M',
				hour: '%H:%M'
			}

		},

		yAxis: [{
			title: {
				text: '价格/元'
			},
			opposite: true
		}, {
			title: {
				text: '销量/股',
			},
			opposite: false,
		}],

		plotOptions: {
			series: {
				showInLegend: true
			}
		},
		series: [{
			id: 'jg',
			name: '价格',
			type: 'line',
			yAxis: 0,
			data: (function() {
				return getDateOfPrice();
			}())
		}, {
			id: 'xl',
			name: '销量',
			type: 'line',
			yAxis: 1,
			data: (function() {
				return getDateOfGartainNumber();
			}())
		}]

	});
}

function getDateOfPrice() {
	var da = [];
	$.ajax({
		url: '/shares/sharesTable/getSharesTable.action?sid=' + sid,
		type: 'post',
		async: false,
		dataType: 'json',
		success: function(data) {
			$.each(data, function(i, v) {
				var date = new Date(v.date);
				var showDate = Date.UTC(date.getFullYear(), date.getMonth(), date.getDate(),
					date.getHours(), date.getMinutes(), date.getSeconds());
				da.push([showDate, v.price]);
			})
		}
	});
	return da;
}

function getDateOfGartainNumber() {
	var da = [];
	$.ajax({
		url: '/shares/sharesTable/getSharesTable.action?sid=' + sid,
		type: 'post',
		async: false,
		dataType: 'json',
		success: function(data) {
			$.each(data, function(i, v) {
				var date = new Date(v.date);
				var showDate = Date.UTC(date.getFullYear(), date.getMonth(), date.getDate(),
					date.getHours(), date.getMinutes(), date.getSeconds());
				da.push([showDate, v.number]);
			})
		}
	});
	return da;
}