package io.github.tf2jaguar.pettyprofits.service.bo;

import io.github.tf2jaguar.pettyprofits.entity.StockKlineEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : zhangguodong
 * @since : 2022/7/4 20:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockRpsBO {
    private String code;
    private String name;
    // 排名
    private Integer rank;
    //涨幅
    private Double incPercent;
    private Double rps;
    private List<StockKlineEntity> klineList;

    public StockRpsBO(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
