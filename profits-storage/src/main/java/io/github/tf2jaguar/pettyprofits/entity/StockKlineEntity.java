package io.github.tf2jaguar.pettyprofits.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : zhangguodong
 * @since : 2022/7/8 14:54
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_stock_kline")
public class StockKlineEntity {

    // 股票代码
    private String stockCode;
    // 股票名称
    private String stockName;
    // 市场类型
    private Integer marketType;
    // k周期 1:1分; 5:5分; 15; 30; 60; 120分; 101:日k 102:周k 103:月k 104:季k 105:半年k 106:年k 60:小时
    private Integer kPeriod;
    // 复权 1: 前复权 2: 后复权 0: 不复权
    private Integer kFq;

    // 0 交易日期
    private Date dealTime;
    // 1 开盘
    private BigDecimal openPrice;
    // 2 收盘
    private BigDecimal closePrice;
    // 3 最高
    private BigDecimal highestPrice;
    // 4 最低
    private BigDecimal lowestPrice;
    // 5 成交量
    private BigDecimal tradingVolume;
    // 6 成交额
    private BigDecimal tradingAmount;
    // 7 振幅%
    private BigDecimal amplitude;
    // 8 涨跌幅%
    private BigDecimal udVolume;
    // 9 涨跌额
    private BigDecimal udAmount;
    // 10 换手率%
    private BigDecimal turnoverRate;

    private Date createTime;
    private Date updateTime;

    public StockKlineEntity(String stockCode, String stockName, Integer marketType) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.marketType = marketType;
    }
}
