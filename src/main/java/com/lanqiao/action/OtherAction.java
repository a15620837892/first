package com.lanqiao.action;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanqiao.utils.ValidateCode;

@Controller
@RequestMapping("/other")
public class OtherAction {
	
	//获取验证码
	@RequestMapping(value="/validateCode.action")  
	public String validateCode(HttpSession session,HttpServletResponse response) throws Exception{  
	    // 设置响应的类型格式为图片格式  
	    response.setContentType("image/jpeg");  
	    //禁止图像缓存。  
	    response.setHeader("Pragma", "no-cache");  
	    response.setHeader("Cache-Control", "no-cache");  
	    response.setDateHeader("Expires", 0);     
	    ValidateCode vCode = new ValidateCode(120,40,5,100);  
	    session.setAttribute("code", vCode.getCode());  
	    vCode.write(response.getOutputStream());  
	    return null;  
	}  
	
	
}
