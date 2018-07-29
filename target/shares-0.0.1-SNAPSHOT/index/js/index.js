





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
					//$(".topuser li:eq(0)").html(data.username);
					$(".topuser li:eq(0)").html('<a href="/shares/index.jsp">'+data.username+'</a>');
		    		$(".topuser li:eq(2)").html("<a href=\"javascript:getsession()\">退出</a>");
		    		$("#usermsg p span:eq(1)").html(data.username);
		    		$("#usermsg p span:eq(2)").html(data.id);
		    		$("#usermsg p span:eq(3)").html(data.money);
		    		$("#login").css("display","none");
		    		$("#usermsg").css("display","block");
			}else{	
					$(".topuser li:eq(0)").html("");
					$(".topuser li:eq(2)").html('<a href="/shares/index/loginandregist.html">登录 / 注册</a>');
		    		
		    		
		    		$("#usermsg").css("display","none");
		    		$("#login").css("display","block");
			}
	    	
		}
		
	})
}







		var rgb = [ ['rgb(150, 37, 54)','','切肤之痛激发理性自强','“任何通往光明未来的道路都不是笔直的”，突破核心技术肯定会带来阵痛，但在关键领域、卡脖子的地方下大功夫，是为了用现在的短痛换来长远的主动权-中兴事件之痛。']
					,['rgb(17, 35, 45)','','股票交易有风险，请理性炒股','股票的交易存在风险，价格受到新闻事件，各国政策，市场需求等多种因素影响，浮动很大。我们强烈建议您事先调查了解，在自身所能承受的风险范围内参与交易。']
					,['rgb(0, 154, 180)','','中美贸易战持续发酵','面对美国对华采取限制措施，中国已经做好充分准备，中国后续可能对美国关税征收进一步采取措施。继宣布第一批中止减让产品清单之后，中国正在研究第二批、第三批清单，比如飞机、芯片领域。受此影响，芯片盘块持续走强，北京君正连续两日涨停。']
					,['rgb(17, 60, 95)','股票交易平台，提供优质服务！','','']
					,['rgb(84, 46, 99)','秒充秒提，体验前所未有！','','']
		];
			var a;
			/*
			 * 轮播背景
			 */
			function changergb(value){
				index = value;
				if (value >= rgb.length) {
					value = value%rgb.length;
				}
				$("#mid ul a").css("color","white");
				$("#"+value+"").css("color","#d45858");
				$("#mid div div h2").html(rgb[value][1]);
				$("#mid div div h1").html(rgb[value][2]);
				$("#mid div div p").html(rgb[value][3]);
				$("#mid").animate({ backgroundColor: rgb[value][0] }, 1500, function() {     
					// 动画完成，所有浏览器下有效
					}); 
				
				value++;
				clearTimeout(a);
				a = setTimeout(function(){changergb(value);},5000);
			}


		/*	
		 * 股票表单
		 */
			var search ="";
			function addstable(){
				$.ajax({				
					url:'/shares/shares/getTop20sTable.action',
					type:'post',
					data:{
						"name":search
					},
					async:false,
					dataType:'json',
					success:function(data){
						if (!isBlank(data)) {
							$("#shares").html("");
							$.each(data,function(i,v){
					    		var str = "";
					    		str+="<tr>";
					    		str+='<th><a href="/shares/index.html?sid='+v.id+'">'+v.name+"</a></th>";
					    		str+="<th>"+v.newprice+"</th>";
					    		str+="<th>"+v.price+"</th>";
					    		str+="<th>"+v.date+"</th>";
					    		str+="<th>"+v.number+"</th>";
					    		str+="<th>"+v.dhal+"</th>";
					    		str+="</tr>";
					    		$("#shares").append(str);
					    	})
						}else{
							$("#shares").html("无结果");
						}
				    	
					}
					
				})
			}
			
			
			/*
			 * 搜索
			 */	
			function isBlank(str){  
			    if(str == null ||str ==""){  
			    	str = null
			        return true;  
			    }  
			    return false;  
			};  
			
			
			
			
			
			$(function(){
				$("#search").bind('input propertychange',function(){
					search= $('#search').val();
					if (!isBlank(search)) {
						addstable();
					}else {
						addstable();
					}
				});
				
			});
			
		/*
		 * 登录
		*/
		function login(){
			var username = $('.username').val();
			var password = $('.password').val();
			var code = $('.code').val();
			if (isBlank(username)) {
				alert("请输入用户名！");
			}else if (isBlank(password)) {
				alert("请输入密码！");
			}else if (isBlank(code)) {
				alert("请输入验证码！");
			}else {
				$.ajax({
					url:'/shares/user/login.action',
					type:'post',
					data:{
						"username":username,
						"password":password,
						"code":code,
					},		
					async:false,
					success:function(data){
						if (data=="1") {
							window.location.href="/shares/index.jsp";
						}else if(data=="2"){
							alert("用户名密码错误");
						}else if(data=="3"){
							alert("验证码错误");
						}else {
							alert("未知错误");
						}
					}
				})
			}
		
		}
		
	
		
		  // 刷新图片  
	    function changeImg() {  
	        var imgSrc = $("#imgObj");  
	        var src = imgSrc.attr("src");
	        imgSrc.attr("src", changeUrl(src));  
	    }  
	    function changeImg1() {  
	        var imgSrc = $("#imgObj1");  
	        var src = imgSrc.attr("src");
	        imgSrc.attr("src", changeUrl(src));  
	    }  
	    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳  
	    function changeUrl(url) {  
	        var timestamp = (new Date()).valueOf();  
	        var index = url.indexOf("?",url);  
	        if (index > 0) {  
	            //url = url.substring(0, url.indexOf(url, "?"));
	        	url = url.substring(0, index);
	        }  
	        if ((url.indexOf("&") >= 0)) {  
	            url = url + "×tamp=" + timestamp;  
	        } else {  
	            url = url + "?timestamp=" + timestamp;  
	        }  
	        return url;  
	    }  	
		
	
		
		

	    $(document).ready(function(){
			getsession(1);

			addstable();

				

			$.each(rgb,function(i,v){
				var str = "";
				str+="<li>";
				str+="<a href=\"javascript:changergb("+i+");\"  id=\""+i+"\">●</a>";
				str+="</li>";
				$("#point").append(str);
			})

			changergb(0);
			
		
			
	});
		

	    
	