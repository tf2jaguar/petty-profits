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
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
        String beg = "19900101";
        String end = DateUtils.date2String(DateUtils.PATTERN_NO_HOUR_NO_MINUS, new Date());
        writeStockKLines(beg, end);
    }

    public void writeStockKLines(String beg, String end) {
        List<StockBaseEntity> stockBaseEntities = stockBaseDao.selectAll();
        Set<Integer> resultSet = stockBaseEntities.parallelStream()
                .map(s -> writeStockKLines(s, beg, end))
                .collect(Collectors.toSet());
        log.info("result: {}", resultSet);
    }

    public int writeStockKLines(StockBaseEntity s, String beg, String end) {
        String codeWithMarket = s.getMarketType() + "." + s.getStockCode();
        log.info("get {} {} {} klines", s.getMarketType(), s.getStockCode(), s.getStockName());
        List<StockKlineEntity> stockKlines = efWrapper.historyDayFq1(codeWithMarket, beg, end);
        if (CollectionUtils.isEmpty(stockKlines)) {
            return 1;
        }
        // 按年批处理
        String preYear = null;
        List<StockKlineEntity> preSave = new LinkedList<>();
        for (StockKlineEntity kline : stockKlines) {
            String dealTimeStr = DateUtils.date2String(DateUtils.PATTERN_NO_MONTH, kline.getDealTime());
            if (preYear == null) {
                preYear = dealTimeStr;
            }
            if (dealTimeStr.equals(preYear)) {
                preSave.add(kline);
            } else if (!CollectionUtils.isEmpty(preSave)) {
                trySave(preSave);
                preYear = null;
                preSave = new LinkedList<>();
            }
        }
        if (!CollectionUtils.isEmpty(preSave)) {
            trySave(preSave);
        }
        return 0;
    }

    private void trySave(List<StockKlineEntity> preSave) {
        try {
            int batchInsert = stockKlineDao.batchInsert(preSave);
            if (batchInsert < 0) {
                log.error("批量插入数据异常: {}", JSONObject.toJSONString(preSave));
            }
        } catch (
                Exception e) {
            log.error("出现异常:{}", JSONObject.toJSONString(preSave), e);
        }
    }

    public List<StockInfoDTO> listStockKline(Integer period, Date endDate) {
        int previousDays = (int) Math.round(period + period * 0.667);
        Date startDate = DateUtils.previousDayOf(endDate, previousDays);

        List<StockBaseEntity> stockBaseEntities = stockBaseDao.selectAll();
        return stockBaseEntities.parallelStream()
                .map(st -> {
                    List<StockKlineEntity> klines = stockKlineDao.selectClosePriceByTimeRange(st.getStockCode(), startDate, endDate);
                    return new StockInfoDTO(st.getStockCode(), st.getStockName(), st.getMarketType(), klines);
                }).collect(Collectors.toList());
    }

}
