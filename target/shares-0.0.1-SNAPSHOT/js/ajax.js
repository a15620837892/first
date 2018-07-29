$(function(){
	//获取sid
	var param = parseURL(window.location.href);
	if(param == null){
		sid = 1;
	}else{
		sid = param.sid;
	}
	
	//保存股票列表数据
	sharesList = [];
	
	//获取用户是否登陆
	$.ajax({
		url:"/shares/user/getLoginUser.action",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data != null){
				$("#loginUl").html('<li><a href="/shares/index.jsp">'+data.username+'</a></li><li><a href="javascript:getsession()">退出</a></li>');
				$('#mySurplusMoney').html(data.money);
			}else{
				$("#loginUl").html('<li><a href="/shares/index/index.html">'+登录+'</a></li><li><a href="/shares/index/loginandregist.html">注册</a></li>');
			}
			//$("#loginUl li:eq(0) a")
		}
	});
	
	//获取用户当前股票信息
	$.ajax({
		url:"/shares/userShares/getUserCurrentSharesInfo.action",
		type:"get",
		data:{"sid":sid},
		dataType:"json",
		success:function(data){
			if(data != null){
				$('#mySurplusShares').html(data.number);
			}
		}
	});
	
	//获取股票列表
	$.ajax({
		url:"/shares/shares/getSharesList.action",
		type:"get",
		dataType:"json",
		success:function(data){
			sharesList = data;
			var content = '';
			$.each(data, function(index, val) {
				var percent = (val.newprice - val.oldprice) / val.oldprice * 100;
				percent = percent.toFixed(2);
				if(percent > 0){
					var newprice = val.newprice + ' ↑';
				}else if(percent < 0){
					var newprice = val.newprice + ' ↓';
				}else{
					var newprice = val.newprice;
				}
				
				//更新当前页面股票信息
				if(val.id == sid){
					$('#currentSharesInfoTitle').html(val.name);
					$('#currentSharesNewPriceSpan').html(newprice);
				}
				
				content += 
			          '<tr>'+
			            '<td><a href="index.html?sid='+val.id+'">'+val.name+'</a></td>'+
			            '<td align="right">'+newprice+'</td>'+
			            '<td align="right">'+percent+'%</td>'+
			          '</tr>';
			});
			$('#sharesTable').append(content);
		}
	});
	//获取成交列表
	$.ajax({
		url:"/shares/bargain/getBargainList.action",
		type:"get",
		data:{'sid':sid},
		dataType:"json",
		success:function(data){
			var content = '';
			$.each(data, function(index, val) {
				var date = new Date(val.date);
				var showDate = date.getFullYear();
				showDate += "-"+((date.getMonth()+1) < 10 ? "0" : "")+(date.getMonth()+1);
				showDate += '-'+(date.getDate() < 10 ? "0" : "")+date.getDate();
				showDate += '&nbsp;&nbsp;'+(date.getHours() < 10 ? "0" : "")+date.getHours();
				showDate += ':'+(date.getMinutes() < 10 ? "0" : "")+date.getMinutes();
				showDate += ':'+(date.getSeconds() < 10 ? "0" : "")+date.getSeconds();

				content += 
			          '<tr>'+
			            '<td>'+showDate+'</td>'+
			            '<td>'+val.price+'</td>'+
			            '<td>'+val.number+'</td>'+
			            '<td>'+val.total+'</td>'+
			          '</tr>';
				if(index > 9){
					return false;
				}
			});
			$('#bargainTable').append(content);
		}
	});
	//获取委托列表
	$.ajax({
		url:"/shares/entrust/getEntrustList.action",
		type:"get",
		data:{'sid':sid},
		dataType:"json",
		success:function(data){
			var content = '';
			$.each(data, function(index, val) {
				var date = new Date(val.date);
				var showDate = date.getFullYear();
				showDate += "-"+((date.getMonth()+1) < 10 ? "0" : "")+(date.getMonth()+1);
				showDate += '-'+(date.getDate() < 10 ? "0" : "")+date.getDate();
				showDate += '&nbsp;&nbsp;'+(date.getHours() < 10 ? "0" : "")+date.getHours();
				showDate += ':'+(date.getMinutes() < 10 ? "0" : "")+date.getMinutes();
				showDate += ':'+(date.getSeconds() < 10 ? "0" : "")+date.getSeconds();

				content += 
			          '<tr>'+
			            '<td>'+showDate+'</td>'+
			            '<td>'+(val.type == 0 ? "买入" : "卖出")+'</td>'+
			            '<td>'+val.price+'</td>'+
			            '<td>'+val.number+'</td>'+
			            '<td><a href="javascript:void(0);" onclick="removeEntrust('+val.id+', this)">撤回</a></td>'+
			          '</tr>';
			});
			$('#entrustTable').append(content);
		}
	});
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
	//为买入股票绑定事件
	$('#buyShares').click(function(event) {
		var price = $('#buySharesForm input[name="price"]').val();
		var number = $('#buySharesForm input[name="number"]').val();
		var total = $('#buySharesForm input[name="total"]').val();
		var canBuyNumber = $('#canBuyNumber').html();
		if($("#loginUl li:eq(0) a").html() == '登录'){
			window.location.href="/shares/index/index.html";
		}
		if(Number(number) > Number(canBuyNumber) || number <= 0){
			alert("您的余额不足以购买，请充值后继续操作！");
		}else{
			$.ajax({
				url:"/shares/entrust/buyShares.action",
				type:"post",
				data:{'sid':sid,'price':price,'number':number},
				dataType:"text",
				success:function(data){
					if(data == 'true'){
						alert('买入委托成功');
						//更新我的余额
						var newSurplusMoney = $('#mySurplusMoney').html() - total;
						newSurplusMoney = newSurplusMoney.toFixed(2);
						$('#mySurplusMoney').html(newSurplusMoney);
					}else if(data == 'false'){
						alert('买入委托失败');
					}else{
						//重定向到登陆界面
						window.location.href="/shares/index/index.html";
					}
					getMyEntrustList();
				}
			});
		}
	});
	//为卖出股票绑定事件
	$('#sellShares').click(function(event) {
		var price = $('#sellSharesForm input[name="price"]').val();
		var number = $('#sellSharesForm input[name="number"]').val();
		var total = $('#sellSharesForm input[name="total"]').val();
		var mySurplusShares = $('#mySurplusShares').html();
		if($("#loginUl li:eq(0) a").html() == '登录'){
			window.location.href="/shares/index/index.html";
		}
		if(Number(number) > Number(mySurplusShares) || number <= 0){
			alert('您持有的股票数量不足，无法完成操作！');
		}else{
			$.ajax({
				url:"/shares/entrust/sellShares.action",
				type:"post",
				data:{'sid':sid,'price':price,'number':number},
				dataType:"text",
				success:function(data){
					if(data == 'true'){
						alert('卖出委托成功');
						//更新我的股票余额
						var newSurplusShares = $('#mySurplusShares').html() - number;
						newSurplusShares = newSurplusShares.toFixed(0);
						$('#mySurplusShares').html(newSurplusShares);
					}else if(data == 'false'){
						alert('卖出委托失败');
					}else{
						//重定向到登陆界面
						window.location.href="/shares/index/index.html";
					}
					getMyEntrustList();
				}
			});
		}
	});
	
});

//撤销委托单
function removeEntrust(eid, obj){
	$.ajax({
		url:"/shares/entrust/removeEntrustById.action",
		type:"post",
		data:{'eid':eid},
		dataType:"text",
		success:function(data){
			if(data == 'true'){
				alert('撤销成功');
				$(obj).parent().parent().remove();
			}else if(data == 'false'){
				alert('撤销失败');
			}
		}
	});
}
//解析url返回参数对象
function parseURL(url){
    var url = url.split("?")[1];
    if(url == null){
    	return null;
    }else{
	    var para = url.split("&");
	    var len = para.length;
	    var res = {};
	    var arr = [];
	    for(var i=0;i<len;i++){
	        arr = para[i].split("=");
	        res[arr[0]] = arr[1];
	    }
	    return res;
    }
}
//计算买入的总价
function calcBuyTotal(){
	var price = $('#buySharesForm input[name="price"]').val();
	var number = $('#buySharesForm input[name="number"]').val();
	
	var result = Number(price) * Number(number);
	result = result.toFixed(2);
	$('#buySharesForm input[name="total"]').val(result);
	//计算手续费
	var serviceCharge = Number(result) * 0.002;
	serviceCharge = serviceCharge.toFixed(5);
	$('#buyServiceCharge').html(serviceCharge);
}
//计算可买入数量
function calcCanBuyNumber(){
	var mySurplusMoney = $('#mySurplusMoney').html();
	var price = $('#buySharesForm input[name="price"]').val();
	
	var canBuyNumber = Number(mySurplusMoney) / Number(price);
	canBuyNumber = Math.floor(canBuyNumber);
	if(canBuyNumber == null){
		$('#canBuyNumber').html(0);
	}else{
		$('#canBuyNumber').html(canBuyNumber);
	}
	
}
//计算卖出的总价
function calcSellTotal(){
	var price = $('#sellSharesForm input[name="price"]').val();
	var number = $('#sellSharesForm input[name="number"]').val();
	var result = Number(price) * Number(number);
	result = result.toFixed(2);
	$('#sellSharesForm input[name="total"]').val(result);
	//计算手续费
	var serviceCharge = Number(number) * 0.002;
	serviceCharge = serviceCharge.toFixed(3);
	$('#sellServiceCharge').html(serviceCharge);
}
//计算可兑换额
function canExchangeMondy(){
	var mySurplusShares = $('#mySurplusShares').html();
	var price = $('#sellSharesForm input[name="price"]').val();
	var canExchangeMondy = Number(mySurplusShares) * Number(price);
	canExchangeMondy = canExchangeMondy.toFixed();
	$('#canExchangeMondy').html(canExchangeMondy);
}

//获取买入或者卖出价格
function getPrice(obj){
	var price = $(obj).find('td:eq(1)').html();
	
	$('#sellSharesForm input[name="price"]').val(price);
	
	$('#buySharesForm input[name="price"]').val(price);
	
	calcCanBuyNumber();
	
	canExchangeMondy();
}

//股票搜索
function searchShares(){
	var searchStr = $('#searchInput').val();
	if(searchStr == ''){
		$('#sharesTable tr:not(:eq(0))').remove();
		var content = '';
		$.each(sharesList, function(index, val) {
			var percent = (val.newprice - val.oldprice) / val.oldprice * 100;
			percent = percent.toFixed(2);
			if(percent > 0){
				var newprice = val.newprice + ' ↑';
			}else if(percent < 0){
				var newprice = val.newprice + ' ↓';
			}else{
				var newprice = val.newprice;
			}			
			content += 
		          '<tr>'+
		            '<td><a href="index.html?sid='+val.id+'">'+val.name+'</a></td>'+
		            '<td align="right">'+newprice+'</td>'+
		            '<td align="right">'+percent+'%</td>'+
		          '</tr>';
		});
		$('#sharesTable').append(content);
	}else{
		$('#sharesTable tr:not(:eq(0))').remove();
		var content = '';
		$.each(sharesList, function(index, val) {
			if(val.name.indexOf(searchStr) >= 0){
				var percent = (val.newprice - val.oldprice) / val.oldprice * 100;
				percent = percent.toFixed(2);
				var newprice = 0;
				if(percent > 0){
					newprice = val.newprice + ' ↑';
				}else if(percent < 0){
					newprice = val.newprice + ' ↓';
				}else{
					newprice = val.newprice;
				}			
				content += 
					'<tr>'+
					'<td><a href="index.html?sid='+val.id+'">'+val.name+'</a></td>'+
					'<td align="right">'+newprice+'</td>'+
					'<td align="right">'+percent+'%</td>'+
					'</tr>';
			}
		});
		$('#sharesTable').append(content);
	}
}

//获取我的委托列表
function getMyEntrustList(){
	$.ajax({
		url:"/shares/entrust/getEntrustList.action",
		type:"get",
		data:{'sid':sid},
		dataType:"json",
		success:function(data){
			$('#entrustTable tr:not(:eq(0))').remove();
			var content = '';
			$.each(data, function(index, val) {
				var date = new Date(val.date);
				var showDate = date.getFullYear();
				showDate += "-"+((date.getMonth()+1) < 10 ? "0" : "")+(date.getMonth()+1);
				showDate += '-'+(date.getDate() < 10 ? "0" : "")+date.getDate();
				showDate += '&nbsp;&nbsp;'+(date.getHours() < 10 ? "0" : "")+date.getHours();
				showDate += ':'+(date.getMinutes() < 10 ? "0" : "")+date.getMinutes();
				showDate += ':'+(date.getSeconds() < 10 ? "0" : "")+date.getSeconds();

				content += 
			          '<tr>'+
			            '<td>'+showDate+'</td>'+
			            '<td>'+(val.type == 0 ? "买入" : "卖出")+'</td>'+
			            '<td>'+val.price+'</td>'+
			            '<td>'+val.number+'</td>'+
			            '<td><a href="javascript:void(0);" onclick="removeEntrust('+val.id+', this)">撤回</a></td>'+
			          '</tr>';
			});
			$('#entrustTable').append(content);
		}
	});
}


//龙   注销
function getsession(value) {
	$.ajax({				
		url:'/shares/user/getsession.action',
		type:'post',
		data:{
			"type":value
		},
		async:false,
		success : function(data) {
			$("#loginUl").html('<li><a href="/shares/index/index.html">登陆</a></li><li><a href="/shares/index/loginandregist.html">注册</a></li>');
		}
	})
}