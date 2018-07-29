package com.lanqiao.service;

import java.sql.SQLException;
import java.util.List;

import com.lanqiao.entity.SharesTable;

public interface SharesTableService {
	
	public List<SharesTable> getSharesTable(Integer sid) throws SQLException;

}
