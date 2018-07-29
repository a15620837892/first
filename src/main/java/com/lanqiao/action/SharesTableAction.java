package com.lanqiao.action;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanqiao.entity.SharesTable;
import com.lanqiao.service.SharesTableService;

@Controller
@RequestMapping("/sharesTable")
public class SharesTableAction {
	
	@Autowired
	private SharesTableService sharesTableService;
	
	@RequestMapping("/getSharesTable.action")
	public @ResponseBody List<SharesTable> getSharesTable(Integer sid) throws SQLException {
		List<SharesTable> list = sharesTableService.getSharesTable(sid);
		return list;
	}

}
