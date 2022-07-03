package io.github.tf2jaguar.pettyprofits.third.wrapper;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import io.github.tf2jaguar.pettyprofits.entity.StockBase;
import io.github.tf2jaguar.pettyprofits.enums.MarketFsEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author : zhangguodong
 * @since : 2022/7/3 15:42
 */
public class EFWrapperTest extends BaseTest {

    @Autowired
    private EFWrapper efWrapper;

    @Test
    public void listAllStockBase() {
        List<StockBase> stockBases = efWrapper.listAllStockBase(MarketFsEnum.hu_a);
        System.out.println(stockBases);
    }

    @Test
    public void history() {
        String history = efWrapper.historyDayFq1("0.000603", "20220110", "20220701");
        System.out.println(history);
    }
}