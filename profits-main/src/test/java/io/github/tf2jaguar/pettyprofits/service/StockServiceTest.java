package io.github.tf2jaguar.pettyprofits.service;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import io.github.tf2jaguar.pettyprofits.enums.MarketFsEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        stockService.writeStockKLines("20220707", "20220708");
    }

    @Test
    public void refreshStockRps() {
        stockService.refreshStockRps(new int[]{50});
    }
}