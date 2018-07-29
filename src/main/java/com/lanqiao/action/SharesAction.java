package com.lanqiao.action;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanqiao.entity.Shares;
import com.lanqiao.service.SharesService;
import com.lanqiao.vo.Stable;

@Controller
@RequestMapping("/shares")
public class SharesAction {
	
	@Autowired
	private SharesService sharesService;
	
	@RequestMapping(value="/getSharesList.action")
	public @ResponseBody List<Shares> getSharesList() {
		List<Shares> sharesList = null;
		try {
			sharesList = sharesService.getSharesList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sharesList;
	}
	
	//龙
	@RequestMapping("/getTop20sTable.action")
	public @ResponseBody List<Stable> getTop20sTable(Stable s) throws Exception {
		
//		System.out.println("action:"+s.getName());
		List<Stable> list = sharesService.getTop20sTable(s) ;
	
		return list;
	}
	
	//张
	//得到所有股票信息
	@RequestMapping("/getAllShares.action")
	public @ResponseBody List<Shares> getAllShares() throws Exception{
		
		List<Shares> list = sharesService.getAllShares();
		return list;
		
	} 
	

}
