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
@Table(name = "t_stock_deal_log")
public class StockDealLogEntity {

    // 股票代码
    private Long id;
    // 股票代码
    private String stockCode;
    // 股票名称
    private String stockName;
    // 市场类型
    private Integer marketType;

    // 交易时间
    private Date dealTime;
    // 交易单价
    private BigDecimal dealPrice;
    // 交易数量
    private BigDecimal dealVolume;
    // 当前总数量
    private String curVolume;
    // 剩余资金
    private BigDecimal leftMoney;
    // 版本号
    private String dealVersion;

    public StockDealLogEntity(String stockCode, String stockName, Integer marketType) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.marketType = marketType;
    }
}
