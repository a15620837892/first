package com.lanqiao.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanqiao.entity.Entrust;
import com.lanqiao.entity.User;
import com.lanqiao.service.EntrustService;
import com.lanqiao.service.UserService;

@Controller
@RequestMapping("/entrust")
public class EntrustAction {
	
	@Autowired
	private EntrustService entrustService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/buyShares.action")
	public @ResponseBody String buyShares(Entrust entrust, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return "login";
		}
		entrust.setDate(new Date());
		entrust.setUid(user.getId());
		entrust.setType(0);
		int re = 0;
		try {
			re = entrustService.insertEntrustAndBuyShares(entrust);
			user = userService.getUserByID(user.getId());
			session.setAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(re == 1) {
			return "true";
		}else {
			return "false";
		}
	}
	
	@RequestMapping(value="/sellShares.action")
	public @ResponseBody String sellShares(Entrust entrust, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return "login";
		}
		entrust.setDate(new Date());
		entrust.setUid(user.getId());
		entrust.setType(1);
		int re = 0;
		try {
			re = entrustService.insertEntrustAndSellShares(entrust);
			user = userService.getUserByID(user.getId());
			session.setAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(re == 1) {
			return "true";
		}else {
			return "false";
		}
	}
	
	@RequestMapping(value="getEntrustList.action")
	public @ResponseBody List<Entrust> getEntrustList(Integer sid, HttpSession session){
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return null;
		}
		List<Entrust> list = null;
		try {
			list = entrustService.getEntrustListByUidAndSid(user.getId(), sid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value="getSellEntrustGroupByPriceList.action")
	public @ResponseBody List<Entrust> getSellEntrustGroupByPriceList(Integer sid){
		List<Entrust> list = null;
		try {
			list = entrustService.getSellEntrustGroupByPriceList(sid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value="getBuyEntrustGroupByPriceList.action")
	public @ResponseBody List<Entrust> getBuyEntrustGroupByPriceList(Integer sid){
		List<Entrust> list = null;
		try {
			list = entrustService.getBuyEntrustGroupByPriceList(sid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value="removeEntrustById.action")
	public @ResponseBody String reomveEntrustById(Integer eid, HttpSession session){
		try {
			entrustService.deleteReomveEntrustById(eid);
			User user = (User) session.getAttribute("user");
			user = userService.getUserByID(user.getId());
			session.setAttribute("user", user);
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
}
