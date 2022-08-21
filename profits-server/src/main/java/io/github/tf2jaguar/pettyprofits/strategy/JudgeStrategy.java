package io.github.tf2jaguar.pettyprofits.strategy;

import java.math.BigDecimal;

/**
 * @author : zhangguodong
 * @since : 2022/8/21 14:05
 */
public class JudgeStrategy {

    /**
     * 是否为 ST及其他具有退市标签 的股票
     *
     * @param stockName 股票名称
     * @return boolean
     */
    public boolean isStStock(String stockName) {
        return (stockName == null || "".equals(stockName)) ||
                stockName.startsWith("ST") ||
                stockName.contains("*") ||
                stockName.contains("退");
    }

    /**
     * 是否为 停牌 的股票
     *
     * @param stockPrice 股票价格
     * @return boolean
     */
    public boolean isSuspendedStock(BigDecimal stockPrice) {
        return (stockPrice == null || "".equals(stockPrice.toString())) ||
                stockPrice.toString().contains("-");
    }

    /**
     * 是否为 创业板 的股票
     *
     * @param stockCode 股票代码
     * @return boolean
     */
    public boolean isGemStock(String stockCode) {
        return (stockCode == null || "".equals(stockCode)) ||
                stockCode.startsWith("30");
    }

    /**
     * 是否为 科创板 的股票
     *
     * @param stockCode 股票代码
     * @return boolean
     */
    public boolean isStarStock(String stockCode) {
        return (stockCode == null || "".equals(stockCode)) ||
                stockCode.startsWith("688");
    }
}
