package com.lanqiao.service;

import java.sql.SQLException;

import com.lanqiao.entity.User;
import com.lanqiao.vo.UserInfo;

public interface UserService {
	
	public User getUserByID(Integer id) throws SQLException;
	
	//龙
	User login(User user) throws SQLException;
	
	int insert(User record) throws SQLException;
	
	//张
	//登陆
	public User login(String username, String password) throws Exception;

	//修改密码
	public int upPassword(int id) throws Exception;
	
	//获取用户信息
	public UserInfo getUserInfo(Integer id) throws SQLException;
	
	public void updateUserById(User user) throws SQLException;
}
