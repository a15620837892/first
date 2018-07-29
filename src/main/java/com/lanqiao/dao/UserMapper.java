package com.lanqiao.dao;

import com.lanqiao.entity.User;
import com.lanqiao.entity.UserExample;
import com.lanqiao.vo.UserInfo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int countByExample(UserExample example) throws SQLException;

    int deleteByExample(UserExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    int insert(User record) throws SQLException;

    int insertSelective(User record) throws SQLException;

    List<User> selectByExample(UserExample example) throws SQLException;

    User selectByPrimaryKey(Integer id) throws SQLException;

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example) throws SQLException;

    int updateByExample(@Param("record") User record, @Param("example") UserExample example) throws SQLException;

    int updateByPrimaryKeySelective(User record) throws SQLException;

    int updateByPrimaryKey(User record) throws SQLException;
    
    //龙
    User login(User user) throws SQLException;
    
    //张
    //登陆
    public User zhangLogin(Map<String,String> map) throws SQLException ;
    
    //修改用户密码
    public int upPassword(int id) throws Exception;
    
    public UserInfo getUserInfo(Integer id) throws SQLException;
}