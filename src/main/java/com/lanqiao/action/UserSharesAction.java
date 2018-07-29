package com.lanqiao.action;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanqiao.entity.User;
import com.lanqiao.entity.UserShares;
import com.lanqiao.service.UserSharesService;

@Controller
@RequestMapping("/userShares")
public class UserSharesAction {
	
	@Autowired
	private UserSharesService userSharesService;
	
	@RequestMapping("/getUserCurrentSharesInfo.action")
	public @ResponseBody UserShares getUserCurrentSharesInfo(Integer sid, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return null;
		}else {
			try {
				UserShares userShares = userSharesService.getUserCurrentSharesInfo(user.getId(), sid);
				return userShares;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

}
