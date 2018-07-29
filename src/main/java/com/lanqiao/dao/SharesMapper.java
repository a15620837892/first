package com.lanqiao.dao;

import com.lanqiao.entity.Shares;
import com.lanqiao.entity.SharesExample;
import com.lanqiao.vo.Stable;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SharesMapper {
    int countByExample(SharesExample example) throws SQLException;

    int deleteByExample(SharesExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    int insert(Shares record) throws SQLException;

    int insertSelective(Shares record) throws SQLException;

    List<Shares> selectByExample(SharesExample example) throws SQLException;

    Shares selectByPrimaryKey(Integer id) throws SQLException;

    int updateByExampleSelective(@Param("record") Shares record, @Param("example") SharesExample example) throws SQLException;

    int updateByExample(@Param("record") Shares record, @Param("example") SharesExample example) throws SQLException;

    int updateByPrimaryKeySelective(Shares record) throws SQLException;

    int updateByPrimaryKey(Shares record) throws SQLException;
    
    void updateOldPrice() throws SQLException;
    
    List<Stable> getTop20sTable(Stable s) throws SQLException;
}