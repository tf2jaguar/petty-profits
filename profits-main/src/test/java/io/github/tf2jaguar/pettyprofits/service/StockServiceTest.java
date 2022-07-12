package io.github.tf2jaguar.pettyprofits.service;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import io.github.tf2jaguar.pettyprofits.enums.MarketFsEnum;
import io.github.tf2jaguar.pettyprofits.util.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author : zhangguodong
 * @since : 2022/7/1 21:19
 */
public class StockServiceTest extends BaseTest {

    @Autowired
    private StockService stockService;

    @Test
    public void refreshStockBase() {
        stockService.refreshStockBase(MarketFsEnum.hu_shen_a);
    }

    @Test
    public void writeStockKLines() {
        stockService.writeStockKLines();
//        StockBaseEntity stockBase = StockBaseEntity.builder().stockCode("000001").stockName("平安银行").marketType(0).build();
//        stockService.writeStockKLines(stockBase,"20000101","20220708");
    }

    @Test
    public void refreshStockRps() {
        Date endDate = DateUtils.string2date(DateUtils.PATTERN_NO_HOUR, "2022-07-04");
        stockService.refreshStockRps(new int[]{50}, endDate);
    }
}