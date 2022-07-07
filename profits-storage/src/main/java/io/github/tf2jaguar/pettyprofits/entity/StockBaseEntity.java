package io.github.tf2jaguar.pettyprofits.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author : zhangguodong
 * @since : 2022/7/2 09:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_stock_base")
public class StockBaseEntity {
    // 市场类型
    private String marketType;
    // 股票代码
    private String stockCode;
    // 股票名称
    private String stockName;
    private Date createTime;
    private Date updateTime;

}
