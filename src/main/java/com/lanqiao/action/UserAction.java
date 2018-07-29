package com.lanqiao.action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanqiao.entity.User;
import com.lanqiao.service.UserService;
import com.lanqiao.vo.UserInfo;

@Controller
@RequestMapping("/user")
public class UserAction {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/getLoginUser.action")
	public @ResponseBody User getLoginUser(HttpSession session) {
		User user = (User) session.getAttribute("user");
		return user;
	}
	
	//龙
	
	/*登录*/
	@RequestMapping("/login.action")
	public@ResponseBody String login(String username,String password,String code,HttpSession session) throws Exception {
		
		String code1 = (String) session.getAttribute("code");  
		System.out.println("d:"+code);
		System.out.println("q："+code1);
		System.out.println("u："+username);
		if (code1.equalsIgnoreCase(code)) {  //忽略验证码大小写  
			User user = new User();	
			user.setUsername(username);
			user.setPassword(password);
			User u = userService.login(user);
			if (u!=null) {
				session.setAttribute("user", u);
				return "1" ;
			}else{
				return "2";
			}
		}  else {
			return "3"; 
		}
	}
	
	/*注册*/
	@RequestMapping("/regist.action")
	public@ResponseBody String regist(String username,String password,String birthday,int sex,String code,HttpSession session) throws Exception {
		
		String code1 = (String) session.getAttribute("code");  
		System.out.println("d:"+code);
		System.out.println("q："+code1);
		System.out.println("u："+username);
		System.out.println("b："+birthday);
		if (code1.equalsIgnoreCase(code)) {  //忽略验证码大小写  
			User user = new User();
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
			user.setUsername(username);
			user.setPassword(password);
			user.setSex(sex);
			user.setBirthday(date);
			user.setMoney(0.0);
			int u = userService.insert(user);
			if (u==1) {				
				return "1" ;				
			}else{
				return "2";
			}
		}  else {
			return "3"; 
		}
	}	

	/*判断sessin*/
	@RequestMapping("/getsession.action")
	public@ResponseBody User getsession(String type,HttpSession session) throws Exception {
		if (type==null) {
			 session.setAttribute("user", null);
		}
		User u = (User) session.getAttribute("user");
		return u;
	}
	
	//张
	
	//登陆               修改前端映射路径          ？？？？？？？？？？？？  改
	@RequestMapping("/zhangLogin.action")  
    public String login(String username,String password,HttpSession session) throws Exception{
		
		User user = userService.login(username, password);
		if (user != null) {
			session.setAttribute("user", user);
			return "redirect:/index.jsp";
		} else {
			
			return "redirect:/login.jsp";
		}
		 
    }
	
	//获取用户信息
	@RequestMapping("/getUserInfo.action")
	public @ResponseBody UserInfo getUserInfo(HttpSession session) {
		User user = (User) session.getAttribute("user");
		UserInfo userInfo = null;
		try {
			userInfo = userService.getUserInfo(user.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userInfo;
	}
	
	//修改用户
	@RequestMapping("/editUser.action")
	public @ResponseBody String editUser(User user, HttpSession session) {
		User u = (User) session.getAttribute("user");
		user.setId(u.getId());
		try {
			userService.updateUserById(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success!!!";
	}
	
	//修改密码
	@RequestMapping("/editUserPassword.action")
	public @ResponseBody String editUserPassword(String oldPwd, String newPwd, HttpSession session) {
		User u = (User) session.getAttribute("user");
		if(u.getPassword().equals(oldPwd)) {
			try {
				User user = new User();
				user.setId(u.getId());
				user.setPassword(newPwd);
				userService.updateUserById(user);
				return "true";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			return "false";
		}
		return "false";
	}

}
