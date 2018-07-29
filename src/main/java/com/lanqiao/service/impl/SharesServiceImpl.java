package com.lanqiao.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanqiao.dao.SharesMapper;
import com.lanqiao.dao.SharesTableMapper;
import com.lanqiao.entity.Shares;
import com.lanqiao.entity.SharesExample;
import com.lanqiao.entity.SharesTable;
import com.lanqiao.service.SharesService;
import com.lanqiao.vo.Stable;

@Service
public class SharesServiceImpl implements SharesService {
	
	@Autowired
	private SharesMapper sharesMapper;
	
	@Autowired
	private SharesTableMapper sharesTableMapper;

	@Override
	public List<Shares> getSharesList() throws SQLException {
		SharesExample example = new SharesExample();
		return sharesMapper.selectByExample(example);
	}

	@Override
	public void updateOldPrice() throws SQLException {
		sharesMapper.updateOldPrice();
	}

	@Override
	//生成股票趋势图表并且股票成交量清零
	public void UpdateNewPriceAndBargaincount() throws SQLException {
//		Random random = new Random();
		//获得所有股票并且遍历
		SharesExample sharesExample = new SharesExample();
		List<Shares> SharesList = sharesMapper.selectByExample(sharesExample);
		for (Shares shares : SharesList) {
			//生成股票趋势图表数据
			SharesTable sharesTable = new SharesTable();
			sharesTable.setDate(new Date());
			sharesTable.setSid(shares.getId());
			sharesTable.setPrice(shares.getNewprice());
			sharesTable.setNumber(shares.getBargaincount());
			
//			sharesTable.setPrice(shares.getNewprice() + random.nextDouble() * 5);
//			sharesTable.setNumber(random.nextInt(50));
			
			sharesTableMapper.insert(sharesTable);
			//股票成交量清零
			shares.setBargaincount(0);
			sharesMapper.updateByPrimaryKey(shares);
		}
	}
	
	@Override
	public List<Stable> getTop20sTable(Stable s) throws SQLException {
		//System.out.println("ser:"+s.getName());
		return sharesMapper.getTop20sTable(s);
	}

	@Override
	public List<Shares> getAllShares() throws Exception {
		return sharesMapper.selectByExample(null);
	}

}
