package com.lanqiao.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanqiao.dao.UserSharesMapper;
import com.lanqiao.entity.UserShares;
import com.lanqiao.entity.UserSharesExample;
import com.lanqiao.service.UserSharesService;

@Service
public class UserSharesServiceImpl implements UserSharesService {
	
	@Autowired
	private UserSharesMapper userSharesMapper;

	@Override
	public UserShares getUserCurrentSharesInfo(Integer uid, Integer sid) throws SQLException {
		UserSharesExample userSharesExample = new UserSharesExample();
		userSharesExample.createCriteria().andUidEqualTo(uid).andSidEqualTo(sid);
		List<UserShares> list = userSharesMapper.selectByExample(userSharesExample);
		if(list.size() != 0) {
			return list.get(0);
		}else {
			return null;
		}
	}

}
