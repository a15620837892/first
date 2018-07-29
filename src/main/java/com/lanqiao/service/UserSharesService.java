package com.lanqiao.service;

import java.sql.SQLException;

import com.lanqiao.entity.UserShares;

public interface UserSharesService {
	
	public UserShares getUserCurrentSharesInfo(Integer uid, Integer sid) throws SQLException;

}
