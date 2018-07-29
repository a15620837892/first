

function isBlank(str){  
			    if(str == null ||str ==""){  
			    	str = null
			        return true;  
			    }  
			    return false;  
			};  











function setbutton() {
	if ($("#agree").is(':checked')) {
		  $(".regist").removeAttr("disabled");
	}else {		   
	  
	    $(".regist").attr("disabled","disabled");
	}
}














function change(v) {
	if (v==1) {
		
		$(".regist").fadeOut(500,function(){
			$(".login").fadeIn(500); 
			changeImg();
			});
		
	}else {
		
		$(".login").fadeOut(500,function(){
			$(".regist").fadeIn(500); 
			changeImg1();
		});
	}
}



$(document).ready(function(){
	
	getsession(1);
})








/*
		 * 注册
		*/
		function regist(){
			var username = $('.rusername').val();
			var password = $('.rpassword').val();
			var sex = $("input[name='sex']").val();
			var birthday = $('.rbirthday').find("input").val();
			var code = $('.rcode').val();
			if (isBlank(username)) {
				alert("请输入用户名！");
			}else if (isBlank(password)) {
				alert("请输入密码！");
			}else if (isBlank(sex)) {
				alert("请选择性别！");
			}else if (isBlank(birthday)) {
				alert("请输入生日！");
			}else if (isBlank(code)) {
				alert("请输入验证码！");
			}else {
				$.ajax({
					url:'/shares/user/regist.action',
					type:'post',
					data:{
						"username":username,
						"password":password,
						"sex":sex,
						"birthday":birthday,
						"code":code,
					},		
					async:false,
					success:function(data){
						if (data=="1") {
							alert("注册成功！");
							window.location.href="/shares/index/index.html";
						}else if(data=="2"){
							alert("注册失败！");
						}else if(data=="3"){
							alert("验证码错误！");
						}else {
							alert("未知错误");
						}
					}
				})
			}
		
		}












	function psdnts(){
		var password = $('.rpassword').val();
		var password2 = $('.rpassword2').val();
		if (password==password2) {
			$('#psdnts').html("");
		}else {
			$('#psdnts').css("color","red");
			$('#psdnts').html("两次密码不一致！");
		}
	}	


	










