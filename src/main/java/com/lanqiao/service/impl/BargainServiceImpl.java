package com.lanqiao.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanqiao.dao.BargainMapper;
import com.lanqiao.entity.Bargain;
import com.lanqiao.entity.BargainExample;
import com.lanqiao.service.BargainService;

@Service
public class BargainServiceImpl implements BargainService {
	
	@Autowired
	private BargainMapper bargainMapper;

	@Override
	public List<Bargain> getBargainListBySid(Integer sid) throws SQLException {
		BargainExample example = new BargainExample();
		example.createCriteria().andSidEqualTo(sid);
		example.setOrderByClause("date desc");
		return bargainMapper.selectByExample(example);
	}

}
