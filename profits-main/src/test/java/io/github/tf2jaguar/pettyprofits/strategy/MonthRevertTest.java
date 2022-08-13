package io.github.tf2jaguar.pettyprofits.strategy;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import io.github.tf2jaguar.pettyprofits.entity.StockKlineEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MonthRevertTest extends BaseTest {

    @Autowired
    private MonthRevert monthRevert;

    @Test
    public void monthRevert61() {
        // 5, 8, 13, 16, 21
        List<StockKlineEntity> list = new ArrayList<>();
        list.add(StockKlineEntity.builder().stockName("13").closePrice(new BigDecimal(13)).build());
        list.add(StockKlineEntity.builder().stockName("5").closePrice(new BigDecimal(5)).build());
        list.add(StockKlineEntity.builder().stockName("21").closePrice(new BigDecimal(21)).build());
        list.add(StockKlineEntity.builder().stockName("16").closePrice(new BigDecimal(16)).build());
        list.add(StockKlineEntity.builder().stockName("8").closePrice(new BigDecimal(8)).build());

        StockKlineEntity nums = monthRevert.nDayCloseMax(list,0);
        System.out.println("match nums:" + nums);
    }
}