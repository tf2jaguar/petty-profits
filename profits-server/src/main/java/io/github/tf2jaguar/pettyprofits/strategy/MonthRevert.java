package io.github.tf2jaguar.pettyprofits.strategy;

import io.github.tf2jaguar.pettyprofits.bo.StockInfoDTO;
import io.github.tf2jaguar.pettyprofits.entity.StockKlineEntity;
import io.github.tf2jaguar.pettyprofits.service.StockService;
import io.github.tf2jaguar.pettyprofits.service.bo.StockRpsBO;
import io.github.tf2jaguar.pettyprofits.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class MonthRevert {
    @Autowired
    private StockService stockService;


    public void monthRevert61() {
        Date endDate = DateUtils.string2date(DateUtils.PATTERN_NO_HOUR, "2022-08-13");
        List<StockInfoDTO> stockKline = stockService.listStockKline(50, endDate);
        List<StockRpsBO> withRpsStocks = RpsStrategy.refreshStockRps(50, stockKline);
        for (StockRpsBO st : withRpsStocks) {
            boolean big87 = st.getRps() > 87;
            boolean big90 = st.getRps() > 90;
            int big90Days = matchFunctionNums(st.getKlineList(),
                    k -> k.getClosePrice().compareTo(new BigDecimal(90)) > 0);
        }
    }

    public int matchFunctionNums(List<StockKlineEntity> klineList, Predicate<StockKlineEntity> function) {
        return (int) klineList.stream().filter(function).count();
    }

    public StockKlineEntity nDayCloseMax(List<StockKlineEntity> klineList, int n) {
        return klineList.subList(klineList.size() - n, klineList.size()).stream()
                .max(Comparator.comparing(StockKlineEntity::getClosePrice)).orElse(null);
    }
    public StockKlineEntity nDayHighestMax(List<StockKlineEntity> klineList, int n) {
        return klineList.subList(klineList.size() - n, klineList.size()).stream()
                .max(Comparator.comparing(StockKlineEntity::getHighestPrice)).orElse(null);
    }
}
