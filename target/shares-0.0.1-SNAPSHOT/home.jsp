<%@page import="com.lanqiao.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎页面</title>
<link rel="stylesheet" href="/shares/bootstrap-3.3.7/css/bootstrap.min.css">
<script type="text/javascript" src="/shares/js/jquery-3.2.1.min.js"></script>
<style type="text/css">
	.user-style{
		color: #e45959;
		font-family: 宋体;
		font-size: 25px;
		text-align: center;
	}
	

	thead tr th{
	
	text-align: center;
}

 p{
 	text-align: center;
 	color: #e45959;
 }
</style>
</head>
<body>


    <script type="text/javascript">  
        $(function (){        
              
            $.ajax({  
                type:"GET",       
                url:"/shares/shares//getAllShares.action",            
                dataType:"json",  
                async:false,  
                success:function(data){               
                    var obj=eval(data);  
                    var tbody=$('<tbody></tbody>');  
                    $(obj).each(function (index){  
                    var val=obj[index];  
                    var tr=$('<tr></tr>');  
                    //<td>'+ val.name + '</td>' +'<td>
                    tr.append('<td>'+ val.id + '</td>' + '<td><a href="/shares/index.html?sid='+val.id+'" target="_top">'+val.name+'</a></td>' +'<td>'+ val.oldprice + '</td>' + '<td>'+ val.newprice + '</td>'+'<td>'+ val.bargaincount + '</td>');  
                    tbody.append(tr);  
                    });  
                    $('#tableuserlist tbody').replaceWith(tbody);  
                }  
                  
            });  
        }); 
        
    </script>  

	<p>欢迎使用股票交易系统</p><br><br>	
	<table class="table">
		<thead>
			<tr>
				<th>我的用户名</th>
				<th>我的资金</th>
				<th>交易费折扣</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th class="user-style">${user.username}</th>
				<th class="user-style">${user.money}</th>
				<th class="user-style">无</th>
			</tr>
		</tbody>
	</table>
	<hr>
	<!-- 显示股票 -->
	<span>股票列表</span>
	<table class="table table-bordered table-hover table-striped" id="tableuserlist">
		<thead>
			<tr>
				<th>股票ID</th>
				<th>股票</th>
				<th>历史价格</th>
				<th>当前价格</th>
				<th>成交量</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
</body>
</html>