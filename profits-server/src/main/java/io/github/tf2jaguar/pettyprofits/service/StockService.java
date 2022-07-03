package io.github.tf2jaguar.pettyprofits.service;

import io.github.tf2jaguar.pettyprofits.dao.StockBaseDao;
import io.github.tf2jaguar.pettyprofits.entity.StockBase;
import io.github.tf2jaguar.pettyprofits.enums.MarketFsEnum;
import io.github.tf2jaguar.pettyprofits.third.wrapper.EFWrapper;
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
        List<StockBase> stockBases = efWrapper.listAllStockBase(marketFsEnum);
        Set<String> stockCodeSet = stockBases.parallelStream().map(StockBase::getStockCode).collect(Collectors.toSet());
        List<StockBase> dbStocks = stockBaseDao.selectByStockCodes(stockCodeSet);
        Map<String, StockBase> dbStockCodeMap = dbStocks.parallelStream()
                .collect(Collectors.toMap(StockBase::getStockCode, v -> v));

        List<StockBase> preInsert = new LinkedList<>();
        List<StockBase> preUpdate = new LinkedList<>();
        for (StockBase stockBase : stockBases) {
            if (dbStockCodeMap.containsKey(stockBase.getStockCode())) {
                preUpdate.add(StockBase.builder().id(dbStockCodeMap.get(stockBase.getStockCode()).getId())
                        .stockName(stockBase.getStockName()).build());
                continue;
            }
            preInsert.add(StockBase.builder().stockName(stockBase.getStockName()).stockCode(stockBase.getStockCode())
                    .marketType(stockBase.getMarketType()).build());
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

}
