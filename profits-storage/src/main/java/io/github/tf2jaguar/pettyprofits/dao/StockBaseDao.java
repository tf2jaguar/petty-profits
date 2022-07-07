package io.github.tf2jaguar.pettyprofits.dao;

import io.github.tf2jaguar.pettyprofits.entity.StockBaseEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Collection;
import java.util.List;

/**
 * @author : zhangguodong
 * @since : 2022/7/3 12:18
 */
public interface StockBaseDao extends BaseMapper<StockBaseEntity> {

    @Select({"<script>",
            "select stock_code, stock_name, market_type from t_stock_base ",
            "where stock_code in ",
            "<foreach item='item' index='index' collection='stockCodes' open='(' separator=',' close=')'>#{item}</foreach>",
            "</script>"})
    List<StockBaseEntity> selectByStockCodes(@Param("stockCodes") Collection<String> stockCodeSet);

    @Insert({"<script>",
            "insert into t_stock_base (stock_code, stock_name, market_type) values ",
            "<foreach item='item' index='index' collection='stocks' separator=','>(#{item.stockCode},#{item.stockName},#{item.marketType})</foreach>",
            "</script>"})
    int batchInsert(@Param("stocks") List<StockBaseEntity> preInsert);

    @Update({"<script>",
            "<foreach item='item' index='index' collection='stocks' separator=';'>",
            "update t_stock_base set stock_name=#{item.stockName} where stock_code=#{item.stockCode}</foreach>",
            "</script>"})
    int batchUpdate(@Param("stocks") List<StockBaseEntity> preUpdate);
}
