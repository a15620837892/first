package com.lanqiao.dao;

import com.lanqiao.entity.SharesTable;
import com.lanqiao.entity.SharesTableExample;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SharesTableMapper {
    int countByExample(SharesTableExample example) throws SQLException;

    int deleteByExample(SharesTableExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    int insert(SharesTable record) throws SQLException;

    int insertSelective(SharesTable record) throws SQLException;

    List<SharesTable> selectByExample(SharesTableExample example) throws SQLException;

    SharesTable selectByPrimaryKey(Integer id) throws SQLException;

    int updateByExampleSelective(@Param("record") SharesTable record, @Param("example") SharesTableExample example) throws SQLException;

    int updateByExample(@Param("record") SharesTable record, @Param("example") SharesTableExample example) throws SQLException;

    int updateByPrimaryKeySelective(SharesTable record) throws SQLException;

    int updateByPrimaryKey(SharesTable record) throws SQLException;
}