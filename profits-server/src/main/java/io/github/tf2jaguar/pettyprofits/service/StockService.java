package io.github.tf2jaguar.pettyprofits.service;

import com.alibaba.fastjson.JSONObject;
import io.github.tf2jaguar.pettyprofits.bo.StockInfoDTO;
import io.github.tf2jaguar.pettyprofits.dao.StockBaseDao;
import io.github.tf2jaguar.pettyprofits.entity.StockBaseEntity;
import io.github.tf2jaguar.pettyprofits.enums.MarketFsEnum;
import io.github.tf2jaguar.pettyprofits.service.bo.StockRpsBO;
import io.github.tf2jaguar.pettyprofits.third.wrapper.EFWrapper;
import io.github.tf2jaguar.pettyprofits.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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

    public void refreshStockRps() {
        // 计算周期
        int[] periods = new int[]{50};
        Map<Integer, List<StockRpsBO>> periodsMap = new HashMap<>();

        String endDay = DateUtils.date2String(DateUtils.PATTERN_NO_HOUR_NO_MINUS, new Date());
        String startDay = DateUtils.previousDayOf(new Date(), 80, DateUtils.PATTERN_NO_HOUR_NO_MINUS);

        List<StockBaseEntity> stockBaseEntities = stockBaseDao.selectAll();
        List<StockInfoDTO> stockInfoKLines = stockBaseEntities.parallelStream()
                .map(s -> {
                    String code = s.getMarketType() + "." + s.getStockCode();
                    log.info("get {} {} klines", s.getStockCode(), s.getStockName());
                    return efWrapper.historyDayFq1(code, startDay, endDay);
                }).collect(Collectors.toList());

        for (int period : periods) {
            List<StockRpsBO> withScoreStocks = stockInfoKLines.parallelStream()
                    .map(s -> {
                        int size = s.getKlineList().size();
                        if (size < period) {
                            return StockRpsBO.builder().code(s.getCode()).name(s.getName()).score(null).build();
                        }
                        double curClosePrice = s.getKlineList().get(size - 1).getClosePrice();
                        double periodClosePrice = s.getKlineList().get(size - period).getClosePrice();
                        double score = curClosePrice - periodClosePrice;
                        return StockRpsBO.builder().code(s.getCode()).name(s.getName()).score(score).build();
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
    }
}
