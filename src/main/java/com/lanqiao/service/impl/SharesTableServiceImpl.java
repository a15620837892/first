package com.lanqiao.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanqiao.dao.SharesTableMapper;
import com.lanqiao.entity.SharesTable;
import com.lanqiao.entity.SharesTableExample;
import com.lanqiao.service.SharesTableService;

@Service
public class SharesTableServiceImpl implements SharesTableService {
	
	@Autowired
	private SharesTableMapper sharesTalbeMapper;

	@Override
	public List<SharesTable> getSharesTable(Integer sid) throws SQLException {
		SharesTableExample example = new SharesTableExample();
		example.createCriteria().andSidEqualTo(sid);
		example.setOrderByClause("date asc");
		List<SharesTable> list = sharesTalbeMapper.selectByExample(example);
		return list;
	}

}
