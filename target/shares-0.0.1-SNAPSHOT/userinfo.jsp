<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的信息</title>
<link href="/shares/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" />
<link href="/shares/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
<link href="/shares/css/bootstrap-select.min.css" rel="stylesheet" />
<script src="/shares/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="/shares/js/bootstrap-select.js" type="text/javascript"></script>
<script src="/shares/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="/shares/bootstrap-3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/shares/js/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>

<script type="text/javascript">
$('.btn-info').on('shown.bs.modal', function () {
	  $('.modal').focus()
	})
	
$("#date").datetimepicker();


//设置日期时间控件
    $("#datetimepicker1").datetimepicker({
        format: "dd MM yyyy - hh:ii",
        autoclose: true,
        todayBtn: true,
        startDate: "2013-02-14 10:00",
        minuteStep: 10
    });   
</script>
</head>
<body>
	<table class="table"><h3 style="margin-left: 500px;"><span>我的信息</span></h2></table>
	<div style="margin-top: 50px;height: 150px;width: 800px;margin-left: 200px;">
		<table class="table">
			<thead>
				<tr>
					<td>用户ID</td>
					<td>姓名</td>
					<td>性别</td>
					<td>生日</td>
					<td>资金</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${user.id}</td>
					<td>${user.username}</td>
					<td>${user.sex == 0? "男":"女" }</td>
					<td><fmt:formatDate value="${user.birthday }" pattern="yyyy年MM月dd日"/></td>
					<td>${user.money}</td>
					<td>
					<button type="button" class="btn btn-primary btn-lg btn-info" data-toggle="modal" data-target="#xiugai">  
					  修改 
					</button> 
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	

<div class="modal fade" id="xiugai" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="gridSystemModalLabel">信息修改</h4>
      </div>
      <div class="modal-body">
		
		
		<!-- 修改用户信息 -->
		<form action="">
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon1">姓名</span>
			  <input type="text" class="form-control" placeholder="姓名" aria-describedby="basic-addon1" id="name">
			</div>
			<br>
			<div class="input-group">
				<select class="selectpicker" id="sex">
				  <option value="0">男</option>
				  <option value="1">女</option>
				</select>
		    </div><!-- /input-group -->
			<br>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon1">生日</span>
				<a class='input-group date' id='datetimepicker1' >
	                <input type='text' class="form-control" id='nowdate' />
	                	<span class="add-on"><i class="icon-remove"></i></span>
		                <span class="input-group-addon">
		                <span class="glyphicon glyphicon-calendar"></span>
	                	</span>
            	</a>
			</div>
		</form>


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" id="closeEdit">关闭</button>
        <button type="button" class="btn btn-primary" onclick="editUser()">修改</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->	

<script type="text/javascript">
	function editUser(){
		var name = $('#name').val();
		var sex = $('#sex').val();
		var nowdate = $('#nowdate').val();
		$.ajax({
			url:"/shares/user/editUser.action",
			type:"post",
			data:{
				'username':name,
				'sex':sex,
				'birthday':nowdate
			},
			dataType:"text",
			success:function(data){
				alert(data);
				$('#closeEdit').trigger('click');
			}
		});
	}
</script>
	

	
	<!-- 获取股票信息 -->
    <script type="text/javascript">  
        $(function (){        
              
            $.ajax({  
                type:"GET",       
                url:"/shares/user/getUserInfo.action",            
                dataType:"json",  
                async:false,  
                success:function(data){               
                    var obj=eval(data.userSharesList);  
                    var tbody=$('<tbody></tbody>');  
                    $(obj).each(function (index){  
	                    var val=obj[index];  
	                    var tr=$('<tr></tr>');  
	                    tr.append('<td>'+ val.sid + '</td>' + '<td>'+ val.name + '</td>' +'<td>'+ val.number + '</td>');  
	                    tbody.append(tr);  
                    });  
                    $('#tableuserlist tbody').replaceWith(tbody);  
                }  
                  
            });  
        }); 
        
    </script> 
	
	<!-- 显示股票 -->
	<table class="table table-bordered table-hover table-striped" id="tableuserlist">
		<thead>
			<tr>
				<th>股票ID</th>
				<th>股票</th>
				<th>数量</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
</body>
</html>