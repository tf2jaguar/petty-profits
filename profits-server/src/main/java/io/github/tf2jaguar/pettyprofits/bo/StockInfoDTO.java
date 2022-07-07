package io.github.tf2jaguar.pettyprofits.bo;

import lombok.Data;

import java.util.List;

/**
 * @author : zhangguodong
 * @since : 2022/7/4 19:54
 */
@Data
public class StockInfoDTO {
    private String code;
    private Integer market;
    private String name;
    private Integer decimal;
    private Integer dktotal;
    private Double preKprice;
    // "2022-01-10,12.05,12.56,12.74,11.92,130992,163007539.00,6.81,4.32,0.52,2.22",
    // "2022-01-11,12.63,12.55,12.70,12.48,59685,75049244.00,1.75,-0.08,-0.01,1.01"
    private List<String> klines;
    private List<KLine> klineList;

}
