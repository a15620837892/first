package com.lanqiao.action;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanqiao.entity.Bargain;
import com.lanqiao.service.BargainService;

@Controller
@RequestMapping("/bargain")
public class BargainAction {
	
	@Autowired
	private BargainService bargainService;
	
	@RequestMapping(value="/getBargainList.action")
	public @ResponseBody List<Bargain> getBargainList(Integer sid) {
		List<Bargain> bargainList = null;
		try {
			bargainList = bargainService.getBargainListBySid(sid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bargainList;
	}

}
