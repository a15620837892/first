package com.lanqiao.service;

import java.sql.SQLException;
import java.util.List;

import com.lanqiao.entity.Shares;
import com.lanqiao.vo.Stable;

public interface SharesService {
	
	public List<Shares> getSharesList() throws SQLException;
	
	public void updateOldPrice() throws SQLException;
	
	public void UpdateNewPriceAndBargaincount() throws SQLException;
	
	List<Stable> getTop20sTable(Stable s) throws SQLException;
	
	//得到所有股票信息
	public List<Shares> getAllShares() throws Exception;

}
