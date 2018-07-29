$(function(){
	var host = window.location.host;
	//alert(host);
	var webSocket = new WebSocket("ws://"+window.location.host+"/shares/ws.action?sid=" + sid);

	webSocket.onopen = function(event) {
		console.log("连接成功");
	};
	webSocket.onerror = function(event) {
		console.log("连接失败");
	};
	webSocket.onclose = function(event) {
		console.log("Socket连接断开");
	};
	webSocket.onmessage = function(event) {
		sleep(300);
		var data = event.data;
		data = $.parseJSON(data);
		//更新成交单
		if(data.bargainList.length != 0){
			$.each(data.bargainList, function(index, val) {
				var date = new Date(val.date);
				var showDate = date.getFullYear();
				showDate += "-"+((date.getMonth()+1) < 10 ? "0" : "")+(date.getMonth()+1);
				showDate += '-'+(date.getDate() < 10 ? "0" : "")+date.getDate();
				showDate += '&nbsp;&nbsp;'+(date.getHours() < 10 ? "0" : "")+date.getHours();
				showDate += ':'+(date.getMinutes() < 10 ? "0" : "")+date.getMinutes();
				showDate += ':'+(date.getSeconds() < 10 ? "0" : "")+date.getSeconds();

				var content = '';
				content += 
			          '<tr>'+
			            '<td>'+showDate+'</td>'+
			            '<td>'+val.price+'</td>'+
			            '<td>'+val.number+'</td>'+
			            '<td>'+val.total+'</td>'+
			          '</tr>';
				$('#bargainTable tr:eq(0)').after(content);
			});
			var sharesTalbe = $('#bargainTable tr:not(:eq(0))');
			if(sharesTalbe.length > 10){
				$('#bargainTable tr:gt(10)').remove();
			}
			//alert('更新成交列表');
		}
		if(data.shares != null){
			var currentShares = data.shares;
			$.each(sharesList, function(index, val) {
				 if (val.id == currentShares.id) {
					val.newprice = currentShares.newprice;

					var percent = (val.newprice - val.oldprice) / val.oldprice * 100;
					percent = percent.toFixed(2);
					var newprice = 0;
					if (percent > 0) {
						newprice = val.newprice + ' ↑';
					} else if (percent < 0) {
						newprice = val.newprice + ' ↓';
					} else {
						newprice = val.newprice;
					}

					// 更新当前页面股票信息
					if (val.id == sid) {
						$('#currentSharesNewPriceSpan').html(newprice);
					}
				}
			});
			$("#searchInput").trigger("change");
			//alert('更新股票最新价格');
		}
		if(data.entrust == true){
			$('#sellPriceTable tr:not(:eq(0))').remove();
			$('#buyPriceTable tr:not(:eq(0))').remove();
			updateEntrust();
			//alert('更新买入卖出列表');
		}
	}
});

function updateEntrust(){
	//获取卖出价格列表
	$.ajax({
		url:"/shares/entrust/getSellEntrustGroupByPriceList.action",
		type:"get",
		data:{'sid':sid},
		dataType:"json",
		success:function(data){
			var content = '';
			var total = 0;
			$.each(data, function(index, val){
				total = val.price * val.number;
				total = total.toFixed(2);
				content += 
			          '<tr onclick="getPrice(this)">'+
			            '<td>卖'+(data.length - index)+'</td>'+
			            '<td>'+val.price+'</td>'+
			            '<td>'+val.number+'</td>'+
			            '<td>'+total+'</td>'+
			          '</tr>';
			});
			$('#sellPriceTable').append(content);
			$('#sellPriceList').scrollTop($('#sellPriceList')[0].scrollHeight);
		}
	});
	//获取买入价格列表
	$.ajax({
		url:"/shares/entrust/getBuyEntrustGroupByPriceList.action",
		type:"get",
		data:{'sid':sid},
		dataType:"json",
		success:function(data){
			var content = '';
			var total = 0;
			$.each(data, function(index, val){
				total = val.price * val.number;
				total = total.toFixed(2);
				content += 
			          '<tr onclick="getPrice(this)">'+
			            '<td>买'+(index + 1)+'</td>'+
			            '<td>'+val.price+'</td>'+
			            '<td>'+val.number+'</td>'+
			            '<td>'+total+'</td>'+
			          '</tr>';
			});
			$('#buyPriceTable').append(content);
		}
	});
}

//休眠函数
function sleep(n) {
    var start = new Date().getTime();
    while (true){
    	if (new Date().getTime() - start > n) 
    		break;
    } 
}  