package com.lanqiao.service;

import java.sql.SQLException;
import java.util.List;

import com.lanqiao.entity.Entrust;

public interface EntrustService {
	
	public int insertEntrustAndBuyShares(Entrust entrust) throws Exception;
	
	public int insertEntrustAndSellShares(Entrust entrust) throws Exception;
	
	public List<Entrust> getEntrustListByUidAndSid(Integer uid,Integer sid) throws SQLException;
	
	public List<Entrust> getSellEntrustGroupByPriceList(Integer sid) throws SQLException;
	
	public List<Entrust> getBuyEntrustGroupByPriceList(Integer sid) throws SQLException;
	
	public int deleteReomveEntrustById(Integer eid) throws Exception;

}
