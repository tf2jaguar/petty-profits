package io.github.tf2jaguar.pettyprofits.third.wrapper;

import io.github.tf2jaguar.pettyprofits.bo.CListResponse;
import io.github.tf2jaguar.pettyprofits.bo.EfResult;
import io.github.tf2jaguar.pettyprofits.bo.StockInfoDTO;
import io.github.tf2jaguar.pettyprofits.entity.StockBaseEntity;
import io.github.tf2jaguar.pettyprofits.enums.MarketFsEnum;
import io.github.tf2jaguar.pettyprofits.third.EfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : zhangguodong
 * @since : 2022/7/3 15:07
 */
@Slf4j
@Component
public class EFWrapper {

    @Autowired
    private EfService efService;

    public List<StockBaseEntity> listAllStockBase(MarketFsEnum marketFs) {
        String fields = "f12,f13,f14";
        EfResult<CListResponse> marketResponse = efService.market(marketFs.getCode(), fields);
        if (Objects.isNull(marketResponse) || Objects.isNull(marketResponse.getData())) {
            log.warn("获取市场stock基础信息异常: {} {}", marketFs, marketResponse);
            return Collections.emptyList();
        }
        return marketResponse.getData().getDiff().parallelStream()
                .map(c -> StockBaseEntity.builder().marketType(c.getF13()).stockCode(c.getF12()).stockName(c.getF14()).build())
                .collect(Collectors.toList());
    }

    public StockInfoDTO historyDayFq1(String code, String begin, String end) {
        EfResult<StockInfoDTO> history = efService.history(code, begin, end, 101, 1);
        return history.getData();
    }

}
