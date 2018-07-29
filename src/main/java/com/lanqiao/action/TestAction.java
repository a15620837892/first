package com.lanqiao.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanqiao.dao.UserMapper;
import com.lanqiao.entity.Bargain;
import com.lanqiao.entity.User;
import com.lanqiao.vo.BargainAndSharesAndEntrustInfo;
import com.lanqiao.vo.MyUserShares;
import com.lanqiao.vo.UserInfo;
import com.lanqiao.websocket.MyTextWebSocketHandler;

@Controller
public class TestAction {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MyTextWebSocketHandler myTextWebSocketHandler;
	
	@RequestMapping("/test.action")
	public String test(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null) {
			try {
				user = userMapper.selectByPrimaryKey(13);
				session.setAttribute("user", user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return "index.html";
	}
	
	@RequestMapping("/send.action")
	public @ResponseBody String send(String sid) {
		
		BargainAndSharesAndEntrustInfo info = new BargainAndSharesAndEntrustInfo();
		Bargain bargain = new Bargain();
		bargain.setId(55);
		bargain.setNumber(99);
		info.getBargainList().add(bargain);
		
		try {
			myTextWebSocketHandler.sendMessageToUsersBySid(sid, info);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "success !!!!!!";
	}
	
	//测试查询用户所拥有的股票信息
	@RequestMapping("/getUserInfo.action")
	public @ResponseBody String getUserInfo(String uid) {
		try {
			UserInfo userInfo = userMapper.getUserInfo(Integer.parseInt(uid));
			List<MyUserShares> list = userInfo.getUserSharesList();
			for (MyUserShares m : list) {
				System.out.println(m.getName() + " : " + m.getNumber());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success !!!!!!";
	}

}
