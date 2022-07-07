package io.github.tf2jaguar.pettyprofits.service.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer rank;
    private Double score;
    private Integer rps;

    public StockRpsBO(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
