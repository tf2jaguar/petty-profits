package io.github.tf2jaguar.pettyprofits.strategy;

import io.github.tf2jaguar.pettyprofits.bo.StockInfoDTO;
import io.github.tf2jaguar.pettyprofits.service.bo.StockRpsBO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RpsStrategy {

    public static List<StockRpsBO> refreshStockRps(int period, List<StockInfoDTO> stockInfoKLines) {
        List<StockRpsBO> withScoreStocks = stockInfoKLines.parallelStream()
                .map(st -> {
                    StockRpsBO val = StockRpsBO.builder().code(st.getCode()).name(st.getName()).klineList(st.getKlineList()).build();
                    int size = st.getKlineList().size();
                    if (size < period) {
                        return val;
                    }
                    BigDecimal curClosePrice = st.getKlineList().get(size - 1).getClosePrice();
                    BigDecimal periodClosePrice = st.getKlineList().get(size - period).getClosePrice();
                    if (Objects.isNull(periodClosePrice) || periodClosePrice.compareTo(new BigDecimal("0.0")) == 0) {
                        return val;
                    }
                    // 涨幅: 某支股票昨日收盘价为 100，今日收盘价为 110，计算公式为 (110-100)/100=10%。
                    BigDecimal divide = curClosePrice.subtract(periodClosePrice)
                            .divide(periodClosePrice, 5, RoundingMode.HALF_UP);
                    val.setIncPercent(divide.doubleValue());
                    return val;
                })
                .filter(s -> Objects.nonNull(s.getIncPercent()))
                .sorted(Comparator.comparing(StockRpsBO::getIncPercent).reversed())
                .collect(Collectors.toList());

        // rank by dense
        int idx = 0;
        double last = -1;
        double minIncPercent = 0, maxIncPercent = 0;
        for (StockRpsBO st : withScoreStocks) {
            if (Double.compare(last, st.getIncPercent()) != 0) {
                last = st.getIncPercent();
                idx++;
            }
            st.setRank(idx);

            minIncPercent = Math.min(minIncPercent, st.getIncPercent());
            maxIncPercent = Math.max(maxIncPercent, st.getIncPercent());
        }

        // normalization
        //rps = (1-涨幅排名/总数量)*100=90。
        DecimalFormat df = new DecimalFormat("0.00000");
        for (StockRpsBO st : withScoreStocks) {
            double rps = 100 * (1 - Double.parseDouble(df.format((float) st.getRank() / withScoreStocks.size())));
            st.setRps(rps);
        }

        return withScoreStocks;
    }
}
