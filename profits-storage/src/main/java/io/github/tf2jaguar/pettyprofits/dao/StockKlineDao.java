package io.github.tf2jaguar.pettyprofits.dao;

import io.github.tf2jaguar.pettyprofits.entity.StockKlineEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Date;
import java.util.List;

/**
 * @author : zhangguodong
 * @since : 2022/7/8 15:35
 */
public interface StockKlineDao extends BaseMapper<StockKlineEntity> {

    @Insert({"<script>",
            "INSERT INTO t_stock_kline (`stock_code`, `stock_name`, `market_type`, `k_period`, `k_fq`, `deal_time`, `open_price`, `close_price`, `higest_price`, `lowest_price`, `trading_volume`, `trading_amount`, `amplitude`, `ud_volume`, `ud_amount`, `turnover_rate`) VALUES ",
            "<foreach item='item' index='index' collection='stocks' separator=','>",
            "(#{item.stockCode},#{item.stockName},#{item.marketType},#{item.kPeriod},#{item.kFq},#{item.dealTime},#{item.openPrice},#{item.closePrice},#{item.highestPrice},#{item.lowestPrice},#{item.tradingVolume},#{item.tradingAmount},#{item.amplitude},#{item.udVolume},#{item.udAmount},#{item.turnoverRate})</foreach>",
            "</script>"})
    int batchInsert(@Param("stocks") List<StockKlineEntity> preInsert);

    @Select("select stock_code,close_price from t_stock_kline where stock_code=#{stockCode} and deal_time between #{startDay} and #{endDay}")
    List<StockKlineEntity> selectClosePriceByTimeRange(@Param("stockCode") String stockCode, @Param("startDay") Date startDay, @Param("endDay") Date endDay);

    @Select("select * FROM t_stock_kline_2022 order by deal_time desc limit 1")
    StockKlineEntity selectLastDayKline();
}
