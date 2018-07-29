package com.lanqiao.dao;

import com.lanqiao.entity.Bargain;
import com.lanqiao.entity.BargainExample;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BargainMapper {
    int countByExample(BargainExample example) throws SQLException;

    int deleteByExample(BargainExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    int insert(Bargain record) throws SQLException;

    int insertSelective(Bargain record) throws SQLException;

    List<Bargain> selectByExample(BargainExample example) throws SQLException;

    Bargain selectByPrimaryKey(Integer id) throws SQLException;

    int updateByExampleSelective(@Param("record") Bargain record, @Param("example") BargainExample example) throws SQLException;

    int updateByExample(@Param("record") Bargain record, @Param("example") BargainExample example) throws SQLException;

    int updateByPrimaryKeySelective(Bargain record) throws SQLException;

    int updateByPrimaryKey(Bargain record) throws SQLException;
}