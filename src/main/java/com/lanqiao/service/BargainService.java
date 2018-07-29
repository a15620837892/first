package com.lanqiao.service;

import java.sql.SQLException;
import java.util.List;

import com.lanqiao.entity.Bargain;

public interface BargainService {
	
	public List<Bargain> getBargainListBySid(Integer sid) throws SQLException;

}
