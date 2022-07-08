package io.github.tf2jaguar.pettyprofits.third.wrapper;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import io.github.tf2jaguar.pettyprofits.bo.KLine;
import io.github.tf2jaguar.pettyprofits.bo.StockInfoDTO;
import io.github.tf2jaguar.pettyprofits.entity.StockBaseEntity;
import io.github.tf2jaguar.pettyprofits.entity.StockKlineEntity;
import io.github.tf2jaguar.pettyprofits.enums.MarketFsEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author : zhangguodong
 * @since : 2022/7/3 15:42
 */
public class EFWrapperTest extends BaseTest {

    @Autowired
    private EFWrapper efWrapper;

    @Test
    public void listAllStockBase() {
        List<StockBaseEntity> stockBaseEntities = efWrapper.listAllStockBase(MarketFsEnum.hu_a);
        System.out.println(stockBaseEntities);
    }

    @Test
    public void history() {
        List<StockKlineEntity> klines = efWrapper.historyDayFq1("0.000603", "20220706", "20220708");
        System.out.println(klines);
        System.out.println(klines.get(klines.size() - 1).getClosePrice().subtract(klines.get(klines.size() - 50).getClosePrice()));
    }
}