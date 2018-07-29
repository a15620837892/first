package com.lanqiao.dao;

import com.lanqiao.entity.UserShares;
import com.lanqiao.entity.UserSharesExample;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserSharesMapper {
    int countByExample(UserSharesExample example) throws SQLException;

    int deleteByExample(UserSharesExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    int insert(UserShares record) throws SQLException;

    int insertSelective(UserShares record) throws SQLException;

    List<UserShares> selectByExample(UserSharesExample example) throws SQLException;

    UserShares selectByPrimaryKey(Integer id) throws SQLException;

    int updateByExampleSelective(@Param("record") UserShares record, @Param("example") UserSharesExample example) throws SQLException;

    int updateByExample(@Param("record") UserShares record, @Param("example") UserSharesExample example) throws SQLException;

    int updateByPrimaryKeySelective(UserShares record) throws SQLException;

    int updateByPrimaryKey(UserShares record) throws SQLException;
}