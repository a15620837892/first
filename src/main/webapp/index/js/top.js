


function isBlank(str){  
			    if(str == null ||str ==""){  
			    	str = null
			        return true;  
			    }  
			    return false;  
			};  




function getsession(value) {
	$.ajax({				
		url:'/shares/user/getsession.action',
		type:'post',
		data:{
			"type":value
		},
		async:false,
		success:function(data){
			if (!isBlank(data)) {
					$("#topuser li:eq(0)").html(data.username);
		    		$("#topuser li:eq(2)").html("<a href=\"javascript:getsession()\">退出</a>");
			}else{
					$("#topuser li:eq(0)").html("<a href=\"loginandregist.html\">注册</a>");
		    		$("#topuser li:eq(2)").html("<a href=\"loginandregist.html\">登录</a>");
			}
	    	
		}
		
	})
}

getsession(1);









