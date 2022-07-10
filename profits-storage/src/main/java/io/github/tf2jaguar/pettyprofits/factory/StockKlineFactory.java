package io.github.tf2jaguar.pettyprofits.factory;

import io.github.tf2jaguar.pettyprofits.entity.StockKlineEntity;
import io.github.tf2jaguar.pettyprofits.util.DateUtils;

import java.math.BigDecimal;

/**
 * @author : zhangguodong
 * @since : 2022/7/8 15:10
 */
public class StockKlineFactory {
    public static StockKlineEntity createStockKlineEntity(String stockCode, String stockName, Integer markType,
                                                          Integer kPeriod, Integer kFq, String klines) {
        String[] splits = klines.split(",");
        StockKlineEntity stockKline = new StockKlineEntity(stockCode, stockName, markType);
        stockKline.setKPeriod(kPeriod);
        stockKline.setKFq(kFq);
        stockKline.setDealTime(DateUtils.string2date(DateUtils.PATTERN_NO_HOUR, splits[0]));
        stockKline.setOpenPrice(new BigDecimal(splits[1]));
        stockKline.setClosePrice(new BigDecimal(splits[2]));
        stockKline.setHighestPrice(new BigDecimal(splits[3]));
        stockKline.setLowestPrice(new BigDecimal(splits[4]));
        stockKline.setTradingVolume(new BigDecimal(splits[5]));
        stockKline.setTradingAmount(new BigDecimal(splits[6]));
        stockKline.setAmplitude(splits[7]);
        stockKline.setUdVolume(splits[8]);
        stockKline.setUdAmount(new BigDecimal(splits[9]));
        stockKline.setTurnoverRate(new BigDecimal(splits[10]));
        return stockKline;
    }
}
