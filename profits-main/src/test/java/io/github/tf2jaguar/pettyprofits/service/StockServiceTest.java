package io.github.tf2jaguar.pettyprofits.service;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import io.github.tf2jaguar.pettyprofits.dao.StockKlineDao;
import io.github.tf2jaguar.pettyprofits.entity.StockKlineEntity;
import io.github.tf2jaguar.pettyprofits.enums.MarketFsEnum;
import io.github.tf2jaguar.pettyprofits.util.DateUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author : zhangguodong
 * @since : 2022/7/1 21:19
 */
public class StockServiceTest extends BaseTest {

    @Autowired
    private StockService stockService;
    @Resource
    private StockKlineDao stockKlineDao;

    @Test
    public void refreshStockBase() {
        stockService.refreshStockBase(MarketFsEnum.hu_shen_a);
    }

    @Test
    @Ignore
    public void writeStockKLines() {
        stockService.writeStockKLines();
//        StockBaseEntity stockBase = StockBaseEntity.builder().stockCode("000001").stockName("平安银行").marketType(0).build();
//        stockService.writeStockKLines(stockBase,"20000101","20220708");
    }

    @Test
    @Ignore
    public void writeStockKLinesAuto() {
        StockKlineEntity lastKline = stockKlineDao.selectLastDayKline();
        Date previousDayOf = DateUtils.previousDayOf(lastKline.getDealTime(), -1);
        String begDate = DateUtils.date2String(DateUtils.PATTERN_NO_HOUR_NO_MINUS, previousDayOf);
        String endDate = DateUtils.date2String(DateUtils.PATTERN_NO_HOUR_NO_MINUS, new Date());

        stockService.writeStockKLines(begDate, endDate);
    }

    @Test
    public void refreshStockRps() {
        Date endDate = DateUtils.string2date(DateUtils.PATTERN_NO_HOUR, "2022-07-13");
        stockService.refreshStockRps(new int[]{50}, endDate);
    }
}