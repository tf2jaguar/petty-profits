package io.github.tf2jaguar.pettyprofits.service;

import com.alibaba.fastjson.JSONObject;
import io.github.tf2jaguar.pettyprofits.bo.StockInfoDTO;
import io.github.tf2jaguar.pettyprofits.dao.StockBaseDao;
import io.github.tf2jaguar.pettyprofits.dao.StockKlineDao;
import io.github.tf2jaguar.pettyprofits.entity.StockBaseEntity;
import io.github.tf2jaguar.pettyprofits.entity.StockKlineEntity;
import io.github.tf2jaguar.pettyprofits.enums.MarketFsEnum;
import io.github.tf2jaguar.pettyprofits.service.bo.StockRpsBO;
import io.github.tf2jaguar.pettyprofits.third.wrapper.EFWrapper;
import io.github.tf2jaguar.pettyprofits.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : zhangguodong
 * @since : 2022/7/3 14:25
 */
@Slf4j
@Service
public class StockService {

    @Autowired
    private EFWrapper efWrapper;
    @Resource
    private StockBaseDao stockBaseDao;
    @Resource
    private StockKlineDao stockKlineDao;

    public void refreshStockBase(MarketFsEnum marketFsEnum) {
        List<StockBaseEntity> stockBaseEntities = efWrapper.listAllStockBase(marketFsEnum);
        Set<String> stockCodeSet = stockBaseEntities.parallelStream().map(StockBaseEntity::getStockCode).collect(Collectors.toSet());
        List<StockBaseEntity> dbStocks = stockBaseDao.selectByStockCodes(stockCodeSet);
        Map<String, StockBaseEntity> dbStockCodeMap = dbStocks.parallelStream()
                .collect(Collectors.toMap(StockBaseEntity::getStockCode, v -> v));

        List<StockBaseEntity> preInsert = new LinkedList<>();
        List<StockBaseEntity> preUpdate = new LinkedList<>();
        for (StockBaseEntity stockBaseEntity : stockBaseEntities) {
            if (dbStockCodeMap.containsKey(stockBaseEntity.getStockCode())) {
                preUpdate.add(StockBaseEntity.builder()
                        .stockCode(stockBaseEntity.getStockCode())
                        .stockName(stockBaseEntity.getStockName()).build());
                continue;
            }
            preInsert.add(StockBaseEntity.builder()
                    .stockCode(stockBaseEntity.getStockCode())
                    .stockName(stockBaseEntity.getStockName())
                    .marketType(stockBaseEntity.getMarketType()).build());
        }

        if (!CollectionUtils.isEmpty(preInsert)) {
            int batchInsert = stockBaseDao.batchInsert(preInsert);
            log.info("批量写入stock基础信息: {}条", batchInsert);
        }
        if (!CollectionUtils.isEmpty(preUpdate)) {
            int batchUpdate = stockBaseDao.batchUpdate(preUpdate);
            log.info("批量更新stock基础信息: {}条", batchUpdate);
        }
    }

    public void writeStockKLines() {
        String beg = "19000101";
        String end = DateUtils.date2String(DateUtils.PATTERN_NO_HOUR_NO_MINUS, new Date());
        writeStockKLines(beg, end);
    }

    public void writeStockKLines(String beg, String end) {
        List<StockBaseEntity> stockBaseEntities = stockBaseDao.selectAll();
        Set<Integer> resultSet = stockBaseEntities.parallelStream().map(s -> {
            String codeWithMarket = s.getMarketType() + "." + s.getStockCode();
            log.info("get {} {} klines", s.getStockCode(), s.getStockName());
            List<StockKlineEntity> stockKlines = efWrapper.historyDayFq1(codeWithMarket, beg, end);
            if (CollectionUtils.isEmpty(stockKlines)) {
                return 1;
            }
            int batchInsert = stockKlineDao.batchInsert(stockKlines);
            if (batchInsert < 0) {
                log.error("批量插入数据异常: {} {}", s.getStockCode(), s.getStockName());
            }
            return 0;
        }).collect(Collectors.toSet());
        log.info("批量插入数据:{}", resultSet);
    }

    public Map<Integer, List<StockRpsBO>> refreshStockRps(int[] periods) {
        // 默认计算周期: 50
        int maxPeriods = Arrays.stream(periods).max().orElse(50);
        int previousDays = (int) Math.round(maxPeriods + maxPeriods * 0.667);
        String endDay = DateUtils.date2String(DateUtils.PATTERN_NO_HOUR, new Date());
        String startDay = DateUtils.previousDayOf(new Date(), previousDays, DateUtils.PATTERN_NO_HOUR);
        Map<Integer, List<StockRpsBO>> periodsMap = new HashMap<>();

        List<StockBaseEntity> stockBaseEntities = stockBaseDao.selectAll();
        List<StockInfoDTO> stockInfoKLines = stockBaseEntities.parallelStream()
                .map(st -> {
                    List<StockKlineEntity> klines = stockKlineDao.selectClosePriceByTimeRange(st.getStockCode(), startDay, endDay);
                    return new StockInfoDTO(st.getStockCode(), st.getStockName(), st.getMarketType(), klines);
                }).collect(Collectors.toList());

        for (int period : periods) {
            List<StockRpsBO> withScoreStocks = stockInfoKLines.parallelStream()
                    .map(st -> {
                        int size = st.getKlineList().size();
                        if (size < period) {
                            return StockRpsBO.builder().code(st.getCode()).name(st.getName()).score(null).build();
                        }
                        BigDecimal curClosePrice = st.getKlineList().get(size - 1).getClosePrice();
                        BigDecimal periodClosePrice = st.getKlineList().get(size - period).getClosePrice();
                        BigDecimal score = curClosePrice.subtract(periodClosePrice);
                        return StockRpsBO.builder().code(st.getCode()).name(st.getName()).score(score.doubleValue()).build();
                    })
                    .filter(s -> Objects.nonNull(s.getScore()))
                    .sorted(Comparator.comparing(StockRpsBO::getScore).reversed())
                    .collect(Collectors.toList());

            // rank by dense
            // s_rank = (h.iloc[-1]/h.iloc[-n]).sort_values(ascending=False).rank(method='dense')
            int idx = 0;
            double lastScore = -1;
            double minScore = 0, maxScore = 0;
            for (StockRpsBO st : withScoreStocks) {
                if (Double.compare(lastScore, st.getScore()) != 0) {
                    lastScore = st.getScore();
                    idx++;
                }
                st.setRank(idx);

                minScore = Math.min(minScore, st.getScore());
                maxScore = Math.max(maxScore, st.getScore());
            }

            // normalization
            //rps = (1-涨幅排名/总数量)*100=90。
            // s_rps = ( 100*(s_rank - s_rank.min()) )/(s_rank.max()-s_rank.min())
            for (StockRpsBO st : withScoreStocks) {
                double rps = (1000 * (st.getScore() - minScore) / (maxScore - minScore));
                st.setRps(rps);
            }

            periodsMap.put(period, withScoreStocks);

            // print 30 limit
            withScoreStocks.stream()
                    .sorted(Comparator.comparing(StockRpsBO::getRps).reversed()).limit(30)
                    .forEach(s -> System.out.println(JSONObject.toJSONString(s)));
        }
        return periodsMap;
    }
}
