package com.lanqiao.dao;

import com.lanqiao.entity.Entrust;
import com.lanqiao.entity.EntrustExample;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EntrustMapper {
    int countByExample(EntrustExample example) throws SQLException;

    int deleteByExample(EntrustExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    int insert(Entrust record) throws SQLException;

    int insertSelective(Entrust record) throws SQLException;

    List<Entrust> selectByExample(EntrustExample example) throws SQLException;

    Entrust selectByPrimaryKey(Integer id) throws SQLException;

    int updateByExampleSelective(@Param("record") Entrust record, @Param("example") EntrustExample example) throws SQLException;

    int updateByExample(@Param("record") Entrust record, @Param("example") EntrustExample example) throws SQLException;

    int updateByPrimaryKeySelective(Entrust record) throws SQLException;

    int updateByPrimaryKey(Entrust record) throws SQLException;
    
    //获取卖出价格表
    public List<Entrust> getSellEntrustGroupByPriceList(Integer sid) throws SQLException;
    
    //获取买入价格表
    public List<Entrust> getBuyEntrustGroupByPriceList(Integer sid) throws SQLException;
    
    //获取卖出委托中的最低价格
    public Double getSharesMinSellPriceBySid(Integer sid) throws SQLException;
    
    //获取买入委托中的最低价格
    public Double getSharesMaxBuyPriceBySid(Integer sid) throws SQLException;
}