package io.github.tf2jaguar.pettyprofits.service;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import io.github.tf2jaguar.pettyprofits.StockService;
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
    public void history() {
        String history = stockService.historyDayFq1("0.000603", "20220110", "20220701");
        System.out.println(history);
    }
}