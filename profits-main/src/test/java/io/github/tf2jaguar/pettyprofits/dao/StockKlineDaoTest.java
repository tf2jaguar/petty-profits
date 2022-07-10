package io.github.tf2jaguar.pettyprofits.dao;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import io.github.tf2jaguar.pettyprofits.entity.StockKlineEntity;
import io.github.tf2jaguar.pettyprofits.util.DateUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author : zhangguodong
 * @since : 2022/7/9 22:46
 */
public class StockKlineDaoTest extends BaseTest {

    @Resource
    private StockKlineDao stockKlineDao;

    @Test
    public void test() {
        StockKlineEntity stockKlineEntity = new StockKlineEntity();
        stockKlineEntity.setDealTime(DateUtils.string2date(DateUtils.PATTERN_NO_HOUR, "2022-01-01"));
        int i = stockKlineDao.selectCount(stockKlineEntity);
        System.out.println("nums: " + i);
    }

    @Test
    public void selectClosePriceByTimeRange() {
        Date startDay = DateUtils.string2date(DateUtils.PATTERN_NO_HOUR, "2019-11-01");
        Date endDay = DateUtils.string2date(DateUtils.PATTERN_NO_HOUR, "2021-06-01");
        List<StockKlineEntity> stockKlineEntities = stockKlineDao.selectClosePriceByTimeRange("000001", startDay, endDay);
        System.out.println(stockKlineEntities.size());
    }
}