package io.github.tf2jaguar.pettyprofits.service;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import io.github.tf2jaguar.pettyprofits.entity.StockBaseEntity;
import io.github.tf2jaguar.pettyprofits.entity.StockKlineEntity;
import io.github.tf2jaguar.pettyprofits.enums.MarketFsEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        stockService.refreshStockRps(new int[]{50});
    }
}