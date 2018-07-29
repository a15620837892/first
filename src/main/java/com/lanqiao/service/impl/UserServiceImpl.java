package com.lanqiao.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanqiao.dao.UserMapper;
import com.lanqiao.entity.User;
import com.lanqiao.service.UserService;
import com.lanqiao.vo.UserInfo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserByID(Integer id) throws SQLException {
		return userMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public User login(User user) throws SQLException {
		return userMapper.login(user);
	}

	@Override
	public int insert(User record) throws SQLException {
		return userMapper.insert(record);
	}

	@Override
	public User login(String username, String password) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("username", username) ;
		map.put("password", password) ;
		
		return userMapper.zhangLogin(map);
	}

	@Override
	public int upPassword(int id) throws Exception {
		return userMapper.upPassword(id);
	}

	@Override
	public UserInfo getUserInfo(Integer id) throws SQLException {
		return userMapper.getUserInfo(id);
	}

	@Override
	public void updateUserById(User user) throws SQLException {
		userMapper.updateByPrimaryKeySelective(user);
	}

}
